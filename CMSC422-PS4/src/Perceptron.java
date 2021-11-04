import java.util.ArrayList;

//Used for 
public class Perceptron {
	
	public static int dotProduct(ArrayList<Integer> a, ArrayList<Integer> b) {
		if(a.size() != b.size()) {
			throw new UnsupportedOperationException("Vectors were of difference size");
		}
		
		int result = 0;
		
		for(int i = 0; i < a.size(); i++) {
			result += a.get(i) * b.get(i);
		}
		
		return result;
	}
	
	public static Object[] perceptronTrain(int maxIter, ArrayList<ArrayList<Integer>> D, ArrayList<Integer> labels, boolean printIntermediateSteps){
		//Initialize weights
		ArrayList<Integer> weights = new ArrayList<Integer>();
		
		for(int i = 0; i < D.get(0).size(); i++) {
			weights.add(0);
		}
		
		//Initialize bias
		Integer b = 0;
		
		int currentIteration = 1;
		for( ; currentIteration <= maxIter; currentIteration++) {
			for(int i = 0; i < D.size(); i++) {
				ArrayList<Integer> x = D.get(i);
				int y = labels.get(i);
				
				int a = dotProduct(x, weights) + b; //compute activation
				
				if(y * a <= 0) {
					//Update weights
					for(int d=0; d<weights.size(); d++) {
						weights.set(d, weights.get(d) + y*x.get(d));
					}
					
					//Update bias
					b = b + y;
				}
			}
		}
		
		return new Object[] {weights, b};
	}
	
	public static Object[] simplifiedPerceptron()

	public static void main(String[] args) {
		
		
		
	}

}
