import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

class Node {
    enum Type {
        BOOLEAN, INTEGER, NAME, EQUALS, SEQ
    }

    private final Node.Type type;
    private final String token;
    private final Expression children;

    public Node(Node.Type type, String token, Expression children) {
        this.type = type;
        this.token = token;
        this.children = children;
    }

    public Node(Node.Type type, String token) {
        this(type, token, new Expression(new ArrayList<>()));
    }

    public Expression getChildren() {
        return children;
    }

    public String getToken() {
        return token;
    }

    public Node.Type getType() {
        return type;
    }
    
    public String toString(){
        return "Type:" + type + " Token:" + token + " Children: " + "[" + "]";
    }
}

class Expression {
    private final List<Node> nodes;

    public Expression(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }
    
    public String toString(){
        String toReturn = "";
        for(Node n: nodes){
            toReturn += n.toString() + "\n";
        }
        
        return toReturn;
    }
}

class Program {
    private final Expression expression;
    private final List<Expression> definitions;

    public Program(List<Expression> definitions, Expression expression) {
        this.definitions = definitions;
        this.expression = expression;
    }

    public List<Expression> getDefinitions() {
        return definitions;
    }

    public Expression getExpression() {
        return expression;
    }
}

class Lexer {
    private static final Map<String, Node.Type> FIXED_TOKENS = Map.of("=", Node.Type.EQUALS, "TRUE", Node.Type.BOOLEAN,
            "FALSE", Node.Type.BOOLEAN);

    private static final Map<Character, Character> OPEN_CLOSES = Map.of('(', ')', '[', ']');

    public static Program lexProgram(InputStream in) {
        List<Expression> defines = new ArrayList<>();
        Expression expr = null;

        try (Scanner s = new Scanner(in)) {
            while (s.hasNext()) {
                String line = s.nextLine();

                if (line.isEmpty()) {
                    continue;
                }

                if (expr != null && !expr.getNodes().isEmpty()) {
                    defines.add(expr);
                }

                expr = lexExpression(line);
            }
        }

        return new Program(defines, expr);
    }

    private static Expression lexExpression(String exprText) {
        List<Node> nodes = new ArrayList<>();

        Node.Type type = null;
        String token = "";
        for (int i = 0; i < exprText.length(); i++) {
            char c = exprText.charAt(i);
            if (type != null) {
                if (c == ' ') {
                    nodes.add(new Node(type, token));
                    type = null;
                    token = "";
                } else {
                    token += c;
                }
            } else {
                if (c == ' ') {
                    continue;
                }

                final int i2 = i;
                Optional<Map.Entry<String, Node.Type>> optionalFoundToken = FIXED_TOKENS.entrySet().stream()
                        .filter(e -> lookaheadEquals(exprText, i2, e.getKey())).findFirst();
                if (optionalFoundToken.isPresent()) {
                    Map.Entry<String, Node.Type> foundToken = optionalFoundToken.get();
                    nodes.add(new Node(foundToken.getValue(), foundToken.getKey()));
                    i += foundToken.getKey().length() - 1;
                } else if (Character.isDigit(c) || c == '-') {
                    type = Node.Type.INTEGER;
                    token += c;
                } else if (Character.isAlphabetic(c)) {
                    type = Node.Type.NAME;
                    token += c;
                } else {
                    Optional<Map.Entry<Character, Character>> optionalFoundLeftRight = OPEN_CLOSES.entrySet().stream()
                            .filter(e -> c == e.getKey()).findFirst();
                    if (optionalFoundLeftRight.isPresent()) {
                        Map.Entry<Character, Character> foundLeftRight = optionalFoundLeftRight.get();
                        int matchingRightIdx = lookaheadMatch(foundLeftRight.getKey(), foundLeftRight.getValue(),
                                exprText, i);
                        Expression children = lexExpression(exprText.substring(i + 1, matchingRightIdx));
                        nodes.add(new Node(Node.Type.SEQ, null, children));
                        i = matchingRightIdx + 1;
                    }
                }
            }
        }

        if (type != null) {
            nodes.add(new Node(type, token));
        }

        return new Expression(nodes);
    }

    private static boolean lookaheadEquals(String s, int i, String test) {
        try {
            return s.substring(i, i + test.length()).equals(test);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private static int lookaheadMatch(char open, char close, String line, int start) {
        int openCount = 0;
        for (int i = start; i < line.length(); i++) {
            if (line.charAt(i) == open) {
                openCount += 1;
            } else if (line.charAt(i) == close) {
                openCount -= 1;
                if (openCount == 0) {
                    return i;
                }
            }
        }
        throw new RuntimeException("No match found");
    }
}

public class Solution {
    private static Node add(Node n1, Node n2){
        //Ensure both n1 and n2 are integers, if not, process until they are integers.
        if(n1.getType() != Node.Type.INTEGER){
            n1 = processExpression(n1.getChildren());
        }
        
        if(n2.getType() != Node.Type.INTEGER){
            n2 = processExpression(n2.getChildren());
        }
        
        //Now both are ints. Convert to int and return the Node containing the value
        int i1 = Integer.parseInt(n1.getToken());
        int i2 = Integer.parseInt(n2.getToken());
        int sum = i1 + i2;
        
        return new Node(Node.Type.INTEGER, ""+sum);
    }
    
    private static Node eq(Node n1, Node n2){
        //If n1 is not either a BOOL or an INT, then process until it is
        if(!(n1.getType() == Node.Type.BOOLEAN || n1.getType() == Node.Type.INTEGER)){
            n1 = processExpression(n1.getChildren());
        }
        
        if(!(n2.getType() == Node.Type.BOOLEAN || n2.getType() == Node.Type.INTEGER)){
            n2 = processExpression(n2.getChildren());
        }
        
        boolean result = n1.getToken().equals(n2.getToken());
        return new Node(Node.Type.BOOLEAN, (result) ? "TRUE" : "FALSE");
    }
    
    private static Node lt(Node n1, Node n2){
        //Ensure both n1 and n2 are integers, if not, process until they are integers.
        if(n1.getType() != Node.Type.INTEGER){
            n1 = processExpression(n1.getChildren());
        }
        
        if(n2.getType() != Node.Type.INTEGER){
            n2 = processExpression(n2.getChildren());
        }
        
        //Now both are ints. Convert to int and return the Node containing the value
        int i1 = Integer.parseInt(n1.getToken());
        int i2 = Integer.parseInt(n2.getToken());
        
        return new Node(Node.Type.BOOLEAN, (i1 < i2) ? "TRUE" : "FALSE");
    }
    
    private static Node not(Node n1){
        if(n1.getType() != Node.Type.BOOLEAN){
            n1 = processExpression(n1.getChildren());
        }
        
        return new Node(Node.Type.BOOLEAN, (n1.getToken().equals("FALSE") ? "TRUE" : "FALSE"));
    }
    
    private static Node iff(Node n1, Node n2, Node n3){
        if(n1.getType() != Node.Type.BOOLEAN){
            n1 = processExpression(n1.getChildren());
        }
        
        if(!(n2.getType() == Node.Type.BOOLEAN || n2.getType() == Node.Type.INTEGER)){
            n2 = processExpression(n2.getChildren());
        }
        
        if(!(n3.getType() == Node.Type.BOOLEAN || n3.getType() == Node.Type.INTEGER)){
            n3 = processExpression(n3.getChildren());
        }
        
        if(n1.getToken().equals("TRUE")){
            return new Node(n2.getType(), n2.getToken());
        } else {
            return new Node(n3.getType(), n3.getToken());
        }
    }
    
    public static Node processExpression(Expression exp){
        List<Node> allNodes = exp.getNodes();
        String funcName = allNodes.get(0).getToken();
        
        if(funcName.equals("add")){
            return add(allNodes.get(1), allNodes.get(2));
            
        } else if(funcName.equals("eq")){
            return eq(allNodes.get(1), allNodes.get(2));
            
        } else if(funcName.equals("lt")){
            return lt(allNodes.get(1), allNodes.get(2));
            
        } else if(funcName.equals("not")){
            return not(allNodes.get(1));
            
        } else if(funcName.equals("if")){
            return iff(allNodes.get(1), allNodes.get(2), allNodes.get(3));
            
        } else{
            return null;
        }
    }
    
    public static void main(String[] args) {
        Program program = Lexer.lexProgram(System.in);
        Node result = processExpression(program.getExpression());
        System.out.print(result.getToken());
    }
}