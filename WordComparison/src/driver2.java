import java.util.*;

public class driver2 {
	public static int getFood(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // row, col, #steps
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        
        //Find starting location
        int[] start = new int[3];
        
        outer:
        for(int r=0; r<grid.length; r++){
            for(int c=0; c<grid[0].length; c++){
                if(grid[r][c] == '*'){
                    start[0] = r;
                    start[1] = c;
                    break outer;
                }
            }
        }
        
        q.add(start);
        while(!q.isEmpty()){
            int[] pos = q.poll();
            int r = pos[0];
            int c = pos[1];
            int steps = pos[2];
            
            if(grid[r][c] == '#'){
                return steps;
            }
            
            if(grid[r][c] != 'X' && !visited[r][c]){
                if(r>0)
                    q.add(new int[] {r-1, c, steps+1});
                if(c>0)
                    q.add(new int[] {r, c-1, steps+1});
                if(r<rows-1)
                    q.add(new int[] {r+1, c, steps+1});
                if(c<cols-1)
                    q.add(new int[] {r, c+1, steps+1});
            }
            
            visited[r][c] = true;
        }
        
        return -1;
    }
	
	
	public static void main(String[] args) {
		char[][] grid = new char [4][5];
		grid[0] = new char[] {'X', 'X', 'X', 'X', 'X'};
		grid[1] = new char[] {'X', '*', 'X', 'O', 'X'};
		grid[2] = new char[] {'X', 'O', 'X', '#', 'X'};
		grid[3] = new char[] {'X', 'X', 'X', 'X', 'X'};
		
		int result = getFood(grid);
		System.out.println("Result: " + result);
	}

}
