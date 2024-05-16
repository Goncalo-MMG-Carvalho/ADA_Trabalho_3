import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println(System.getProperty("java.version"));
		//first line of input
		String[] tokens = in.readLine().split(" ");
		
		int nThieves, nBarsPerBag, nLocations, nRoads;		
		
		nThieves = Integer.parseInt(tokens[0]);
		nBarsPerBag = Integer.parseInt(tokens[1]);
		nLocations = Integer.parseInt(tokens[2]);
		nRoads = Integer.parseInt(tokens[3]);
		
		Solver sol = new Solver(nLocations, nRoads);
		
		for (int i = 0; i < nRoads; i++) {
			tokens = in.readLine().split(" ");
			
			sol.addRoad(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
		}
		
		tokens = in.readLine().split(" ");
		sol.setFirstAndLast(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
		
		System.out.println(Integer.min(nThieves, sol.solve()) * nBarsPerBag);
	}

}
