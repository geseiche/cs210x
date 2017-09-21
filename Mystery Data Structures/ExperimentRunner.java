import java.util.*;
import com.cs210x.*;

/**
  * Class to deduce the identity of mystery data structures.
  */
public class ExperimentRunner {
	
	private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;
	private static final int NUM_TRIALS = 500;
	
	final static Random random = new Random();
	
	private static enum Operation {
		ADD, REMOVE, CONTAINS, REMOVELARGE, CONTAINSLARGE
	}
	public static long timeOperation(Operation o, Collection210X<Integer> struct, int size){
		final int element = random.nextInt(size);
		final int large = size - 1;
		final long start;
		final long end;
		switch(o){
		case ADD:
			start = CPUClock.getNumTicks();
			struct.add(element);
			end = CPUClock.getNumTicks();
			struct.remove(element);
			break;
		case REMOVE:
			start = CPUClock.getNumTicks();
			struct.remove(element);
			end = CPUClock.getNumTicks();
			struct.add(element);
			break;
		case CONTAINS:
			start = CPUClock.getNumTicks();
			struct.contains(element);
			end = CPUClock.getNumTicks();
			break;
		case REMOVELARGE:
			start = CPUClock.getNumTicks();
			struct.remove(large);
			end = CPUClock.getNumTicks();
			struct.add(large);
			break;
		case CONTAINSLARGE:
			start = CPUClock.getNumTicks();
			struct.contains(large);
			end = CPUClock.getNumTicks();
			break;
		default:
			return -1;
		}
		return end - start;
	}
	
	public static Collection210X<Integer> fillStructure(Collection210X<Integer> struct,int newSize){
		for(int i = struct.size(); i<newSize; i++){
			struct.add(new Integer(i));
		}
		return struct;
	}
	
	public static void main (String[] args) {
		final int cs210XTeamIDForProject3 = 1015; // TODO CHANGE THIS TO THE TEAM ID YOU USE TO SUBMIT YOUR PROJECT3 ON INSTRUCT-ASSIST.

		// Fetch the collections whose type you must deduce.
		// Note -- you are free to change the type parameter from Integer to whatever you want. In this
		// case, make sure to replace (over the next 4 lines of code) Integer with whatever class you prefer.
		// In addition, you'll need to pass the method getMysteryDataStructure a "sample" (an instance) of 
		// the class you want the collection to store.
		@SuppressWarnings("unchecked")
		final Collection210X<Integer>[] mysteryDataStructures = (Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
		for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
			mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject3, i, new Integer(0));
		}
		
		final int[] Ns = { 1, 2, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 }; 
		
		System.out.println("Struct\tN\tT add\tT remove\tT contains\tT RemoveLarge");
		
		for(int i=0; i<mysteryDataStructures.length; i++){
			for(int N: Ns){
				fillStructure(mysteryDataStructures[i], N);
				Map<Operation, Long> data = new HashMap<Operation, Long>();
				for(Operation o: Operation.values()){
					data.put(o, new Long(0));
					for(int j=0; j<NUM_TRIALS; j++){
						data.put(o, data.get(o)+timeOperation(o, mysteryDataStructures[i], N));
					}
					data.put(o, data.get(o)/NUM_TRIALS);
				}
				System.out.println(i +"\t"+N+"\t"+data.get(Operation.ADD)
									 +"\t"+data.get(Operation.REMOVE)
									 +"\t"+data.get(Operation.CONTAINS)
									 +"\t"+data.get(Operation.REMOVELARGE)
									 +"\t"+data.get(Operation.CONTAINSLARGE));
			}
		}
	}
}