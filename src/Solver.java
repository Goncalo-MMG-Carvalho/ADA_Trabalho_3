import java.util.LinkedList;
import java.util.List;

public class Solver {
	
//	private int nLocations;
//	private int nRoads;
	List<Integer>[] prevAdj, posAdj;
	private int first, last;
	
	@SuppressWarnings("unchecked")
	public Solver(int nLocations, int nRoads) {
		int min = Math.min(nLocations, nRoads*2) + 1;
		
		prevAdj = (LinkedList<Integer>[]) new LinkedList<?>[min];
		posAdj = (LinkedList<Integer>[]) new LinkedList<?>[min];
	}
	

	public void addRoad(int l1, int l2) {

		if(prevAdj[l1] == null) {
			prevAdj[l1] = new LinkedList<>();
			posAdj[l1] = new LinkedList<>();
		}
		
		if(prevAdj[l2] == null) {
			prevAdj[l2] = new LinkedList<>();
			posAdj[l2] = new LinkedList<>();
		}
		
		prevAdj[l1].add(l2);
		posAdj[l1].add(l2);
		
		prevAdj[l2].add(l1);
		posAdj[l2].add(l1);
	}
	
	public void setFirstAndLast(int first, int last) {
		this.first = first;
		this.last = last;
		
		prevAdj[first] = null;
		posAdj[last] = null;
	}

	public int solve() {
		// TODO Auto-generated method stub
		return 0;
	}
}
