import java.util.Arrays;

public class State {
	
	public int[] arr = new int[9];
	
	public int blankIndex;
	private int g, h;
	private State previous;
	
	
	
	public State(int[] input) {
	    this.arr = input;
	    this.blankIndex = getIndex(input, 0);
	    this.previous = null;
	    this.g = 0;
	    this.h = Puzzle.getHeuristic(this.arr);
	  }
	
	 public State(State previous, int blankIndex) {
		    this.arr = Arrays.copyOf(previous.arr, previous.arr.length);
		    this.arr[previous.blankIndex] = this.arr[blankIndex];
		    this.arr[blankIndex] = 0;
		    this.blankIndex = blankIndex;
		    this.g = previous.g + 1;
		    this.h = Puzzle.getHeuristic(this.arr);
		    this.previous = previous;
		  }
	 
	public static int getIndex(int[] arr, int value) {
		for (int i=0; i< arr.length; i++) {
			if (arr[i]==value) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isSolved() {
		int[] arr1 = this.arr;
//		for (int i=1; i<arr.length; i++) {
//			if (arr1[i-1]>arr1[i]) {
//				return false;
//			}
//		}
//		return true;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]!=i) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
	    int[] state = this.arr;
	    String s = "\n\n";
	    for(int i = 0; i < state.length; i++) {
	      if(i % 3 == 0 && i != 0) s += "\n";
	      s += (state[i] != 0) ? String.format("%d ", state[i]) : "  ";
	    }
	    return s;
	  }
	 public String allSteps() {
		    StringBuilder sb = new StringBuilder();
		    if (this.previous != null) sb.append(previous.allSteps());
		    sb.append(this.toString());
		    return sb.toString();
		  }
	 
	 public String solutionMessage(long startTime) {
		    long solveTime = System.currentTimeMillis() - startTime;
		    StringBuilder sb = new StringBuilder();
		    sb.append("Here are the steps to the goal state:");
		    sb.append(this.allSteps());
		    sb.append("\n\nGiven puzzle is SOLVED!");
		    sb.append("\nSolution took " + solveTime + "ms and " + this.g + " steps.\n");
		    return sb.toString();
		  }
	 
	 public int g() {
		    return this.g;
		  }
	 
	 public int h() {
		    return this.h;
		  }
	 
	 public int f() {
		    return g() + h();
		  }
	 
	  public State getPrevious() {
		    return this.previous;
		  }

}
