import java.util.ArrayList;

public class Test {

	public static ArrayList<Integer> myFunction(ArrayList<Integer> arr) {
		ArrayList<Integer> toRet =  new ArrayList<Integer>(arr);
		toRet.add(123);
		return toRet;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> a =  new ArrayList<Integer>();
		a.add(0);
		a.add(1);
		a.add(2);
		
		ArrayList<Integer> b = myFunction(a);
		
		System.out.println(a);
		System.out.println(b);
	}

}
