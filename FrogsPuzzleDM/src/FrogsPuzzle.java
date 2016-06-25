import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FrogsPuzzle {
	
	public static Integer[] array;
	
	public static void initArray(int n){
		array = new Integer[2*n+1];
		int length = array.length;
		for (int i = 0; i < length/2; i++) {
			array[i] = 1;
		}
		for (int i = (length/2)+1; i < length; i++) {
			array[i]=2;
		}
		array[length/2]=0;
	}
	public static boolean isFinal(Integer[] state){
		for (int i = 0; i < state.length/2; i++) {
			if (state[i]!=2) {
				return false;
			}
		}
		for (int i = (state.length/2)+1; i < state.length; i++) {
			if (state[i]!=1) {
				return false;
			}
		}
		if (state[state.length/2]!=0) {
			return false;
		}
		return true;
	}
	static void swap(Integer[] state, int i, int j) {
	      int temp = state[i];
	      state[i] = state[j];
	      state[j] = temp;
	}
	public static boolean areEqual(Integer[] array1,Integer[] array2){
		if ((array1==null) || (array1==null)) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i]!=array2[i]) {
				return false;
			}
		}
		return true;
	}
	public static boolean contains(List<Integer[]> visited, Integer[] state){
		for (Integer[] array : visited) {
			if (areEqual(array, state)) {
				return true;
			}
		}
		return false;
	}
	public static boolean nFrogsDFS(Integer[] state,
			List<Integer[]> visited,
			int zeroIndex){
		visited.add(Arrays.copyOf(state, state.length));
		boolean isSuccessful=false;
		if (isFinal(state)) {
			printState(state);
			return true;
		} else {
			if (zeroIndex-1>=0 && state[zeroIndex-1] == 1) {
				swap(state,zeroIndex,zeroIndex-1);
				if(!contains(visited,state)){
					isSuccessful = nFrogsDFS(state, visited, zeroIndex-1);
				}
				swap(state,zeroIndex,zeroIndex-1);
				if (isSuccessful) {
					printState(state);
					return true;
				}
			}
			if (zeroIndex-2>=0 && state[zeroIndex - 2] == 1) {
				swap(state,zeroIndex,zeroIndex-2);
				if (!contains(visited,state)) {
					isSuccessful = nFrogsDFS(state, visited, zeroIndex-2);
				}
				swap(state,zeroIndex,zeroIndex-2);
				if (isSuccessful) {
					printState(state);
					return true;
				}
			}
			if (zeroIndex+1<state.length && state[zeroIndex+1] == 2) {
				swap(state,zeroIndex,zeroIndex+1);
				if (!contains(visited,state)) {
					isSuccessful = nFrogsDFS(state, visited, zeroIndex+1);
				}
				swap(state,zeroIndex,zeroIndex+1);
				if (isSuccessful) {
					printState(state);
					return true;
				}
			}
			if (zeroIndex+2<state.length && state[zeroIndex + 2] == 2) {
				swap(state,zeroIndex,zeroIndex+2);
				if (!contains(visited,state)) {
					isSuccessful = nFrogsDFS(state, visited, zeroIndex+2);
				}				
				swap(state,zeroIndex,zeroIndex+2);
				if (isSuccessful) {
					printState(state);
					return true;
				}
			}
		}
		return false;
	}
	public static void printState(Integer[] state){
		for (int i = 0; i < state.length; i++) {
			System.out.print(state[i]+" ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		System.out.println("Enter the number of frogs: ");
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		initArray(n);
		List<Integer[]> visited = new LinkedList<>();
		int zeroIndex = array.length/2;
		nFrogsDFS(array, visited, zeroIndex);
		
	}
}