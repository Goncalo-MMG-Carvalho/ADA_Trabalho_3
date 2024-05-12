import java.util.LinkedList;
import java.util.List;

public class Solver {
	
//	private int nLocations;
//	private int nRoads;
	List<Integer>[] prevAdj;
	List<Integer>[] posAdj;
	
	@SuppressWarnings("unchecked")
	public Solver(int nLocations, int nRoads) {
		int min = Math.min(nLocations, nRoads*2);
		
		prevAdj = (LinkedList<Integer>[]) new LinkedList<?>[min];
		prevAdj = (LinkedList<Integer>[]) new LinkedList<?>[min];
	}

	public void addRoad(int int1, int int2) {
		// TODO Auto-generated method stub
		
	}

	public int solve() {
		// TODO Auto-generated method stub
		return 0;
	}
}
