import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String[] tokens = in.readLine().split(" ");
		
		int nThieves, nBarsPerBag, nLocations, nRoads;		
		
		nThieves = Integer.parseInt(tokens[0]);
		nBarsPerBag = Integer.parseInt(tokens[1]);
		nLocations = Integer.parseInt(tokens[2]);
		nRoads = Integer.parseInt(tokens[3]);
		
		if (nLocations == 0 || nRoads == 0 || nBarsPerBag == 0 || nThieves == 0)
		{
			System.out.println(0);
			return ;
		}
		
		//nLocations*2 beacause every location as two node a entry node and a exit node
		List<List<Edge>> locations = new ArrayList<>((nLocations*2+1));
		for (int i = 0; i <= nLocations * 2; i++) {
		    List<Edge> location = new ArrayList<>(nLocations); //reduzir os possiveis links 
		    locations.add(location);
		}
		
			
		int local1;
		int local2;
		
		
		//all off the locations that are < nLocations/2 + 1 are the entry nodes
		//local index + n locations are the exit nodes
		//we are building the flow network
		for (int i = 0; i < nRoads; i++) {
			tokens = in.readLine().split(" ");
			local1 = Integer.parseInt(tokens[0]);
			local2 = Integer.parseInt(tokens[1]);
			locations.get(local1+nLocations).add(new Edge(local1+nLocations, local2, 1));
			locations.get(local2).add(new Edge(local2,local1+nLocations, 0));
			
			locations.get(local2+nLocations).add(new Edge(local2+nLocations, local1, 1));
			locations.get(local1).add(new Edge(local1,local2+nLocations, 0));
		}
		
		tokens = in.readLine().split(" ");
		int vault = Integer.parseInt(tokens[0]);
		int destination = Integer.parseInt(tokens[1]);
		if (vault == destination)
		{
			System.out.println(0);
			return ;
		}
	
		locations.get(vault).add(new Edge(vault, vault+nLocations, Integer.MAX_VALUE));
		locations.get(destination).add(new Edge(destination, destination+nLocations, Integer.MAX_VALUE));
		
		//connect the entry node to the exit node with one capacity
		for (int i = 1; i <= nLocations; i++)
		{
			if (!(i == vault || i == destination)) 
			{
				locations.get(i).add(new Edge(i,i+nLocations,1));
				locations.get(i+nLocations).add(new Edge(i+nLocations, i, 0));
			}
		}
		System.out.println(edmondsKarp(locations , vault , destination+nLocations, (nLocations*2)+1, nThieves)*nBarsPerBag);
	}
	
	public static int edmondsKarp(List<List<Edge>> network, int source, int sink, int numNodes, int nThieves) {
		int[][] flow = new int[numNodes][numNodes];
		int[] via = new int[numNodes];
		int flowValue = 0;
		int increment;
		while ((increment = findPath(network, flow, source, sink, via, numNodes)) != 0) {
			flowValue += increment;
			if (flowValue >= nThieves)
				return nThieves;
			int node = sink;
			while (node != source) {
				int origin = via[node];
				flow[origin][node] += increment;
				flow[node][origin] -= increment;
				node = origin;
			}
		}
		return flowValue;
	}

	private static int findPath(List<List<Edge>> network, int[][] flow, int source, int sink, int[] via, int numNodes) {
		Queue<Integer> waiting = new LinkedList<>();
		boolean[] found = new boolean[numNodes];
		int[] pathIncr = new int[numNodes];
		waiting.add(source);
		found[source] = true;
		via[source] = source;
		pathIncr[source] = 1;
		List<Edge> originList;
		do {
			
			int origin = waiting.remove();
			originList = network.get(origin);
			int destin = 0;
			for (Edge e : originList) {
				destin = e.getEnd();
				int residue = e.getWeight() - flow[origin][destin];
				if (!found[destin] && residue > 0) {
					via[destin] = origin;
					pathIncr[destin] = Math.min(pathIncr[origin], residue);
					if (destin == sink)
						return pathIncr[destin];
					waiting.add(destin);
					found[destin] = true;
				}
			}
		} while (!waiting.isEmpty());
		return 0;
	}
}