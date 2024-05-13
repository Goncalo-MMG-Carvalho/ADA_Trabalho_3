import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		//first line of input
		String[] tokens = in.readLine().split(" ");
		
		int nThieves, nBarsPerBag, nLocations, nRoads;		
		
		nThieves = Integer.parseInt(tokens[0]);
		nBarsPerBag = Integer.parseInt(tokens[1]);
		nLocations = Integer.parseInt(tokens[2]);
		nRoads = Integer.parseInt(tokens[3]);
		
		//nLocations *2 beacuase of the entry and out nodes for each location
		//we just put +1 to ignore the offset because there is no location 0
		List<List<Integer>> locations = new ArrayList<>((nLocations*2+1));
		
		//Solver sol = new Solver(nLocations, nRoads);
		for (int i = 0; i <= (nLocations*2); i++) { //arranjar forma de nao fazer para nao fazer para o zero
			locations.add(i, new ArrayList<>((nLocations*2) + 1));
			for (int j = 0; j <= nLocations*2; j++)
				locations.get(i).add(0);
		}//aloca espaco para nLocations* 2  + 1 
		
		for (int i = 1; i <= nLocations; i++) 
				locations.get(i).set(i+nLocations, 1);
			
		int local1;
		int local2;
		for (int i = 0; i < nRoads; i++) {
			tokens = in.readLine().split(" ");
			local1 = Integer.parseInt(tokens[0]);
			local2 = Integer.parseInt(tokens[1]);
			locations.get(local1+nLocations).set(local2, 1); //alterado 
			locations.get(local2+nLocations).set(local1, 1); //alterado
			//sol.addRoad(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
		}
		
		tokens = in.readLine().split(" ");
		//sol.setFirstAndLast(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
		int vault = Integer.parseInt(tokens[0]);
		int destination = Integer.parseInt(tokens[1]);
		//locations.get(0).set(vault, Integer.MAX_VALUE);
		//locations.get(index)
		locations.get(vault).set(vault+nLocations, Integer.MAX_VALUE);
		locations.get(destination).set(destination+nLocations, Integer.MAX_VALUE);
		//System.out.println(Integer.min(nThieves, sol.solve()) * nBarsPerBag);
		
		//CORRIGIR
		//Caso em que o numero de ladroes nao e suficiente para satisfazer todas as possiveis direcoes  
		System.out.println(Math.min(nThieves,edmondsKarp(locations , vault , destination, (nLocations*2)+1))*nBarsPerBag);
	}
	
	public static int edmondsKarp(List<List<Integer>> network, int source, int sink, int numNodes) {
		int[][] flow = new int[numNodes][numNodes]; // inicializar o flow de cada vertice a zero
//		for (int i = 0; i <= numNodes; i++) 
//			for (int j = 0; j <= numNodes; i++)
//				flow[i][j] = 0;
		int[] via = new int[numNodes];
		int flowValue = 0;
		int increment;
		while ((increment = findPath(network, flow, source, sink, via, numNodes)) != 0) {
			flowValue += increment;
			// Update flow.
			int node = sink;
			while (node != source) {
				int origin = via[node];
				flow[origin][node] += increment;
				flow[node][origin] -= increment;
				node = origin;
			}
		}
		return flowValue; // return new PairClass<>(flowValue, flow);
	}

	private static int findPath(List<List<Integer>> network, int[][] flow, int source, int sink, int[] via, int numNodes) {
		Queue<Integer> waiting = new LinkedList<>();
		boolean[] found = new boolean[numNodes]; // java initializes the matrix with false
//		for every Node v in network.nodes()
//		found[v] = false;
		int[] pathIncr = new int[numNodes];
		waiting.add(source);
		found[source] = true;
		via[source] = source;
		pathIncr[source] = Integer.MAX_VALUE; // pensar
		do {
			int origin = waiting.remove(); // certa
			List<Integer> originList = network.get(origin);
//			for every Edge<L> e in network.outIncidentEdges(origin) {
//			Node destin = e.secondNode();	
			int destin = 0; //destin ta quase certo
			for (Integer capacity : originList) {
				int residue = /*e.label()*/ capacity - flow[origin][destin];
				if (!found[destin] && residue > 0) {
					via[destin] = origin;
					pathIncr[destin] = Math.min(pathIncr[origin], residue);
					if (destin == sink)
						return pathIncr[destin];
					waiting.add(destin);
					found[destin] = true;
				}
				destin++; //destin ta quase certo
			}
		} while (!waiting.isEmpty());
		return 0;
	}


}