import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Queens {
	Random rand = new Random();

	public int[] rows;

	//Creates a new board with a size nxn and randomly fills it with one queen in each column 
	public Queens(int n) {
		rows = new int[n];
	}

	//Fills the board randomly with one queen
	public void fillQueen() {
		for (int i = 0, n = rows.length; i < n; i++) {
			rows[i] = i;
		}
		for (int i = 0, n = rows.length; i < n; i++) {
			int j = rand.nextInt(n);
			int rowToSwap = rows[i];
			rows[i] = rows[j];
			rows[j] = rowToSwap;
		}
	}

     //Returns the number of queens that conflict with (row,col), not counting the queen in column col.
	public int getConflicts(int row, int col) {
		int conflicts = 0;
		for (int c = 0; c < rows.length; c++) {
			if (c == col)
				continue;
			int r = rows[c];
			if (r == row || Math.abs(r - row) == Math.abs(c - col))
				conflicts++;
		}
		return conflicts;
	}
	
	


	//Fills the board with a legal arrangement of queens.
	public void minConflictsSolve() {
		int moves = 0;

		
		List<Integer> queensCandidates = new ArrayList<Integer>();

		while (true) {

			// Find the queen with most conflicts
			int maxConflicts = 0;
			queensCandidates.clear();
			for (int c = 0; c < rows.length; c++) {
				int conflicts = getConflicts(rows[c], c);
				if (conflicts == maxConflicts) {
					queensCandidates.add(c);
				} else if (conflicts > maxConflicts) {
					maxConflicts = conflicts;
					queensCandidates.clear();
					queensCandidates.add(c);
				}
			}

			if (maxConflicts == 0) {
				// Checked each queen and found no conflicts
				return;
			}

			// Pick a random queen from those that had the most conflicts
			int worstQueenColumn = queensCandidates.get(rand.nextInt(queensCandidates.size()));

			// Move her to the place with the least conflicts.
			int minConflicts = rows.length;
			queensCandidates.clear();
			for (int r = 0; r < rows.length; r++) {
				int conflicts = getConflicts(r, worstQueenColumn);
				if (conflicts == minConflicts) {
					queensCandidates.add(r);
				} else if (conflicts < minConflicts) {
					minConflicts = conflicts;
					queensCandidates.clear();
					queensCandidates.add(r);
				}
			}

			if (!queensCandidates.isEmpty()) {
				rows[worstQueenColumn] = queensCandidates.get(rand.nextInt(queensCandidates.size()));
			}

			moves++;
			if (moves == rows.length * 2) {
				// Trying too long so start over.
				fillQueen();
				moves = 0;
			}
		}
	}

	
	public void print(PrintStream stream) {
		for (int r = 0; r < rows.length; r++) {
			stream.print(" ");
			for (int c = 0; c < rows.length; c++) {
				stream.print(" ");
				stream.print(rows[c] == r ? '*' : '_');
			}
			stream.println();
		}
	}

	
	public static void main(String[] args) {
		System.out.println("Enter the number of n: ");
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		Queens board = new Queens(n);
		board.fillQueen();
		board.minConflictsSolve();
		board.print(System.out);
	}
}