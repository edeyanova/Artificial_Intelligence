
public class Move {
	
	private Move() {
		
	}
	//Vryshta nov state s praznoto myasto razmeneno s cifrata nad nego
	public static State up(State state) {
		if (state.blankIndex > 2)
		      return new State(state, state.blankIndex - 3);
		    return null;
	}
	
	public static State down(State state) {
	    if (state.blankIndex < 6)
	      return new State(state, state.blankIndex + 3);
	    return null;
	  }
	
	 public static State left(State state) {
		    if (state.blankIndex % 3 > 0)
		      return new State(state, state.blankIndex - 1);
		    return null;
		  }
	 
	 public static State right(State state) {
		    if (state.blankIndex % 3 < 2)
		      return new State(state, state.blankIndex + 1);
		    return null;
		  }

	
}
