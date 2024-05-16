//import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.List;
import java.util.Queue;

public class Solver {
	
//	private int nLocations;
//	private int nRoads;
//	List<Integer>[] enterList, leaveList;
	int[][] leaveArr;
	private int min;
	private int vault, safeHouse;
	
	public Solver(int nLocations, int nRoads) {
//		this.min = Math.min(nLocations, nRoads*2) + 1;
		
		this.min = nLocations + 1;
		
//		enterList = (ArrayList<Integer>[]) new ArrayList<?>[min];
//		leaveList = (ArrayList<Integer>[]) new ArrayList<?>[min];
//		initLists();
		
		//enterArr = new int[min][min];
		leaveArr = new int[min][min];
		
	}
	
//	private void initLists() {
//		for(int i = 1; i < min; i++) {
//			enterList[i] = new ArrayList<>(min);
//			leaveList[i] = new ArrayList<>(min);
//			
//			for (int j = 0; j <= min; j++)
//				enterList[i].add(0);
//				leaveList[i].add(0);
//		}
//	}
	
	
	public void addRoad(int l1, int l2) {

//		if(enterList[l1] == null) {
//			enterList[l1] = new ArrayList<>();
//			leaveList[l1] = new ArrayList<>();
//		}
//		
//		if(enterList[l2] == null) {
//			enterList[l2] = new ArrayList<>();
//			leaveList[l2] = new ArrayList<>();
//		}
//		
//		enterList[l1].add(l2);
//		leaveList[l1].add(l2);
//		
//		enterList[l2].add(l1);
//		leaveList[l2].add(l1);
		
		leaveArr[l1][l2] = 1;
		leaveArr[l2][l1] = 1;
		
	}
	
	public void setFirstAndLast(int vault, int safeHouse) {
		this.vault = vault;
		this.safeHouse = safeHouse;
		
		leaveArr[vault][vault] 			= Integer.MAX_VALUE;
		leaveArr[safeHouse][safeHouse]  = Integer.MAX_VALUE;
//		enterList[vault] = null;
//		leaveList[safeHouse] = null;
	}

	public int solve() {
		
		int[][] flow = new int[min][min]; // inicializar o flow de cada vertice a zero

		int[] via = new int[min];
		int flowValue = 0;
		int increment;
		
		while( (increment = findPath(flow, via)) != 0 ) {
			flowValue += increment;
			// Update flow.
			
			int node = safeHouse;
			while (node != vault) {
				int origin = via[node];
				flow[origin][node] += increment;
				flow[node][origin] -= increment;
				node = origin;
			}
		}
		return flowValue;
		
		//return 0;
	}
	
	private int findPath(int[][] flow, int[] via) {
		Queue<Integer> waiting = new LinkedList<>();
		boolean[] found = new boolean[min]; // java initializes the matrix with false

		int[] pathIncr = new int[min];
		waiting.add(vault);
		found[vault] = true;
		via[vault] = vault;
		pathIncr[vault] = Integer.MAX_VALUE; // pensar
		
		do {
			int origin = waiting.remove(); // certa
			//List<Integer> originList = network.get(origin);

			int[] originArr = leaveArr[origin];
			
			int destin = 0; //destin ta quase certo
			
			for (Integer capacity : /*originList*/ originArr) {
				if(capacity == 0) continue;
				
				int residue = capacity - flow[origin][destin];
				
				if (!found[destin] && residue > 0) {
					via[destin] = origin;
					pathIncr[destin] = Math.min(pathIncr[origin], residue);
					
					if (destin == safeHouse)
						return pathIncr[destin];
					
					waiting.add(destin);
					found[destin] = true;
				}
				destin++; //destin ta quase certo
			}
		} 
		while (!waiting.isEmpty());
		
		
		return 0;
	}
	
}
