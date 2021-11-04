
public class Solution {
	
	//Returns true if removing one letter can make the Strings equal
	public static boolean areDerived(String s1, String s2) {	
		if(s1.length() == s2.length())
			return s1.equals(s2);
		
		//Make sure s1 is shorter
		if(s1.length() > s2.length()) {
			String temp = s1;
			s1 = s2;
			s2 = temp;
		}
		
		if(s2.length()-s1.length() > 1) {
			return false;
		}
		
		for(int i=0; i<s1.length(); i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			
			if(c1 != c2) {
				return s1.equals(s2.substring(0, i) + s2.substring(i+1));
			}
		}
		
		return true;
	}
	
	public static int f(int n, int k, int x) {
		System.out.println(x);
		
		if(k==0)
			return 1;
		return f(n, k-1, x+1 ) * (n+k) / k;
	}
	
	public static void main(String[] args) {
		System.out.println('a' - 'A');
		System.out.println('B' + ('a' - 'A'));
		System.out.println((char)98);

	}

}
