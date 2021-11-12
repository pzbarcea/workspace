import java.util.ArrayList;
import java.util.Random;

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
	
	public static ArrayList<Integer> simplifiedPerceptron(int maxIter, ArrayList<ArrayList<Integer>> D, ArrayList<Integer> labels, boolean printIntermediateSteps){
		//Initialize weights
		ArrayList<Integer> weights = new ArrayList<Integer>();
		for(int i = 0; i < D.get(0).size(); i++) {
			weights.add(0);
		}
		
		System.out.println("Initialized weights: " + weights);
		
		int currentIteration = 1;
		for( ; currentIteration <= maxIter; currentIteration++) {
			for(int i = 0; i < D.size(); i++) {
				ArrayList<Integer> x = D.get(i);
				int y = labels.get(i);
				
				int a = dotProduct(x, weights); //compute activation
				
				if(printIntermediateSteps) {
					System.out.println("\td=" + (i+1) + ", a=" + a + ", ya=" + y*a);
				}
					
				if(y * a <= 0) {					
					//Update weights
					for(int d=0; d<weights.size(); d++) {
						weights.set(d, weights.get(d) + y*x.get(d));
					}
				}
			}

			System.out.println("Weights after iteration(epoch) #" + currentIteration + ": " + weights);
			
			if(printIntermediateSteps)
				System.out.println();
			
		}
		
		return weights;
	}
	
	public static ArrayList<Integer> simplifiedPerceptron2(int maxIter, ArrayList<ArrayList<Integer>> D, ArrayList<Integer> labels){
		//Initialize weights
		ArrayList<Integer> weights = new ArrayList<Integer>();
		for(int i = 0; i < D.get(0).size(); i++) {
			weights.add(0);
		}
		
		System.out.println("Initialized weights: " + weights);
		
		int currentIteration = 1;
		for( ; currentIteration <= maxIter; currentIteration++) {
			for(int i = 0; i < D.size(); i++) {
				ArrayList<Integer> x = D.get(i);
				int y = labels.get(i);
				
				int a = dotProduct(x, weights); //compute activation
					
				if(y * a <= 0) {					
					//Update weights
					for(int d=0; d<weights.size(); d++) {
						weights.set(d, weights.get(d) + y*x.get(d));
					}
				}
				
				System.out.println("After processing point x" + (i+1) + " the weights are: " + weights);
			}

			System.out.println("Weights after iteration(epoch) #" + currentIteration + ": " + weights);

			System.out.println();
			
		}
		
		return weights;
	}
	
	public static ArrayList<Integer> simplifiedPerceptron3(int maxIter, ArrayList<ArrayList<Integer>> D, ArrayList<Integer> labels){
		//Initialize weights
		ArrayList<Integer> weights = new ArrayList<Integer>();
		for(int i = 0; i < D.get(0).size(); i++) {
			weights.add(0);
		}
		
		System.out.println("Initialized weights: " + weights);
		
		int currentIteration = 1;
		for( ; currentIteration <= maxIter; currentIteration++) {
			//Randomly permute D before the next iteration/epoch
			Random r = new Random();
			int num = r.nextInt(1);
			
			//If 1 was generated, swap the order of the points and the labels
			if(num == 1) {
				ArrayList<Integer> temp = D.get(0);
				D.set(0, D.get(1));
				D.set(1, temp);
				
				int labelTemp = labels.get(0);
				labels.set(0, labels.get(1));
				labels.set(1, labelTemp);
			}
			
			for(int i = 0; i < D.size(); i++) {
				ArrayList<Integer> x = D.get(i);
				int y = labels.get(i);
				
				int a = dotProduct(x, weights); //compute activation
					
				if(y * a <= 0) {					
					//Update weights
					for(int d=0; d<weights.size(); d++) {
						weights.set(d, weights.get(d) + y*x.get(d));
					}
				}
			}

			System.out.println("Weights after iteration(epoch) #" + currentIteration + ": " + weights);
			
		}
		
		return weights;
	}
	
	public static void question4part1() {
		ArrayList<Integer> x1 = new ArrayList<>();
		ArrayList<Integer> x2 = new ArrayList<>();
		ArrayList<Integer> x3 = new ArrayList<>();
		ArrayList<Integer> x4 = new ArrayList<>();
		ArrayList<Integer> labels = new ArrayList<>();
		
		x1.add(1);
		x1.add(1);
		
		x2.add(1);
		x2.add(4);
		
		x3.add(-1);
		x3.add(4);
		
		x4.add(-1);
		x4.add(1);

		labels.add(1);
		labels.add(1);
		labels.add(-1);
		labels.add(-1);
		
		ArrayList<ArrayList<Integer>> allPoints = new ArrayList<>();
		allPoints.add(x1);
		allPoints.add(x2);
		allPoints.add(x3);
		allPoints.add(x4);
		
		//simplifiedPerceptron(5, allPoints, labels, false);
		simplifiedPerceptron2(1, allPoints, labels);
	}
	
	public static void question5PartA() {
		ArrayList<Integer> x1 = new ArrayList<>();
		ArrayList<Integer> x2 = new ArrayList<>();
		ArrayList<Integer> labels = new ArrayList<>();	
		
		x1.add(1);
		x1.add(20);
		
		x2.add(-1);
		x2.add(3);
		
		labels.add(1);
		labels.add(-1);
		
		ArrayList<ArrayList<Integer>> allPoints = new ArrayList<>();
		allPoints.add(x1);
		allPoints.add(x2);
		
		simplifiedPerceptron(10, allPoints, labels, false);
	}
	
	public static void question5PartB() {
		ArrayList<Integer> x1 = new ArrayList<>();
		ArrayList<Integer> x2 = new ArrayList<>();
		ArrayList<Integer> labels = new ArrayList<>();	
		
		x1.add(1);
		x1.add(20);
		
		x2.add(-1);
		x2.add(3);
		
		labels.add(1);
		labels.add(-1);
		
		ArrayList<ArrayList<Integer>> allPoints = new ArrayList<>();
		allPoints.add(x1);
		allPoints.add(x2);
		
		simplifiedPerceptron3(10, allPoints, labels);
	}
	
	public static void question5PartC() {
		ArrayList<Integer> x1 = new ArrayList<>();
		ArrayList<Integer> x2 = new ArrayList<>();
		ArrayList<Integer> labels = new ArrayList<>();	
		
		x1.add(1);
		x1.add(20);
		
		x2.add(-1);
		x2.add(3);
		
		labels.add(1);
		labels.add(-1);
		
		ArrayList<ArrayList<Integer>> allPoints = new ArrayList<>();
		allPoints.add(x1);
		allPoints.add(x2);
		
		simplifiedPerceptron(10, allPoints, labels, false);
	}
	
	public static void quiz() {
		ArrayList<Integer> x1 = new ArrayList<>();
		ArrayList<Integer> x2 = new ArrayList<>();
		ArrayList<Integer> labels = new ArrayList<>();	
		
		x1.add(1);
		x1.add(20);
		
		x2.add(-1);
		x2.add(3);
		
		labels.add(1);
		labels.add(-1);
		
		ArrayList<ArrayList<Integer>> allPoints = new ArrayList<>();
		allPoints.add(x1);
		allPoints.add(x2);
		
		simplifiedPerceptron(10, allPoints, labels, false);
	}

	public static void main(String[] args) {
		//question4part1();
		//question5PartA();
		//question5PartB();
		
		quiz();
	}

}
