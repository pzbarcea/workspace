import java.util.*;

public class Combinations {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		combine(4, 2);
	}
	
	public static List<List<Integer>> combine(int n, int k) {
		ArrayList<ArrayList<Integer>> myList = new ArrayList<>();
		
		int toAdd = 1;
		int currK = 1;
		int index = 0;
		
		//Run k times (to generate a list of size k)
		for(int i=1; i<k; i++) {
			for(int j=n-1; j>=1; j--) {
				//If first pass, just add the lists
				if(i==1) {
					for(int h=1;h<=j; h++) {
						myList.add(new ArrayList<Integer>());
						myList.get(index % myList.size()).add(toAdd);
						index+=1;
					}
					toAdd +=1;
				} else if(i==k) {
					for(int )
				}
			}
		}
		
		
		
		for(ArrayList<Integer> l: myList) {
			for(Integer i: l) {
				System.out.print(i +" ");
			}
			
			System.out.println("");
		}
		
		return null;
	}

}
