import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;



public class Puzzle {
	
	public State initialState, state;
	
	static final int CAPACITY = 100;
	
	public final PriorityQueue<State> queue = new PriorityQueue<State>(CAPACITY, new Comparator<State>() {
	    @Override
	    public int compare(State o1, State o2) {
	      return o1.f() - o2.f();
	    }
	  });
	
	public final HashSet<State> visited = new HashSet<State>();
	
	 public Puzzle(int[] puzzleInput) {
		    this.initialState = new State(puzzleInput);
		    this.state = this.initialState;
		  }
	 
	 public boolean isSolvable() {
		    int inversions = 0;
		    int[] arr1 = this.state.arr;

		    for(int i = 0; i < arr1.length - 1; i++) {
		      for(int j = i + 1; j < arr1.length; j++)
		        if(arr1[i] > arr1[j]) inversions++;
		      if(arr1[i] == 0 && i % 2 == 1) inversions++;
		    }
		    return (inversions % 2 == 0);
		  }
	
	 public static boolean isValid(String puzzleInput) {
		    if (puzzleInput.length() == 17) {
		      // Check if duplicates exist in the input.
		      HashSet<Integer> lump = new HashSet<Integer>();
		      for(String s : puzzleInput.split(" ")) {
		        int i = Integer.parseInt(s);
		        if (lump.contains(i) || i > 8) return false;
		        lump.add(i);
		      }
		      return true;
		    }
		    return false;
		  }
	 public static int[] getConsoleInput() {
		    System.out.println("\nEnter a valid 8-puzzle below:");
		    Scanner scanner = new Scanner(System.in);
		    

		    String t = handleBlankSpaces(scanner.nextLine());
		    String m = handleBlankSpaces(scanner.nextLine());
		    String b = handleBlankSpaces(scanner.nextLine());

		    return convertToArray(String.format("%s %s %s", t, m, b));
		  }
	 
	 public static String handleBlankSpaces(String row) {
		    row = row.replaceAll("\\s+$", "");

		    if (row.length() == 3) row += " 0";
		    row = row.replace("   ", " 0 ").replace("  ", "0 ");
		    return row.trim();
		  }
	 public static int[] convertToArray(String s) {
		    if (!isValid(s)) {
		      System.out.println("Invalid 8-puzzle entered!");
		      System.exit(0);
		    }

		    int[] arr1 = new int[9];
		    s = s.replace(" ", "");
		    for(int i = 0; i < s.length(); i++)
		      arr1[i] = Integer.parseInt(Character.toString(s.charAt(i)));
		    return arr1;
		  }
	 
	 public static int getHeuristic(int[] array) {
		    int heuristic = 0;

		    for(int i = 0; i < array.length; i++) {
		      if (array[i] != 0)
		        heuristic += getManhattanDistance(i, array[i]);
		    }
		    return heuristic;
		  }
	 
	 public static int getManhattanDistance(int index, int number) {
		    return Math.abs((index / 3) - ((number) / 3)) + Math.abs((index % 3) - ((number) % 3));
		  }
	 
	 private void addToQueue(State nextState) {
		    if(nextState != null && !this.visited.contains(nextState)) this.queue.add(nextState);
		  }
	 
	 public void solve() {
		    // Clear the queue and add the initial state.
		    queue.clear();
		    queue.add(this.initialState);
		    long startTime = System.currentTimeMillis();

		    while(!queue.isEmpty()) {
		      // Get the best next state.
		      this.state = queue.poll();

		      // Check if the state is a solution.
		      if (this.state.isSolved()) {
		         // Print to the console.
		          System.out.println(this.state.solutionMessage(startTime));
		   
		        return;
		      }

		      // Add this state to the visited HashSet so we don't revisit it.
		      visited.add(state);

		      // Add valid moves to the queue.
		      this.addToQueue(Move.up(state));
		      this.addToQueue(Move.down(state));
		      this.addToQueue(Move.left(state));
		      this.addToQueue(Move.right(state));
		    }
		  }

	 public static void main(String[] args) {
		    int[] input;
		    Puzzle puzzle = null;

		   
		    // Retrieve input based on argument length.
		    
		      puzzle = new Puzzle(getConsoleInput());
		    

		    // Check if the puzzle is solvable.
		    if (!puzzle.isSolvable()) {
		      System.out.printf("Given puzzle:%s\n\nis NOT solvable!\n", puzzle.state.toString());
		      System.exit(0);
		    }

		    // Solve the puzzle.
		    puzzle.solve();
		  }

}
