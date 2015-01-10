package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BoardUtils {
		
	/**
	 * Will attempt to setup the board based on an input file. Territories 
	 * will be added first and then routes will be set up.
	 * <p>
	 * A valid file should contain Regions in the format "Region: Name".
	 * followed by the names of territories. After all the territories 
	 * in that region should be a blank line. (You can have as many regions as you like.)
	 * Following all the regions and territories, the routes are set up by starting 
	 * a line with "Routes:". This should be followed by a list of each connection 
	 * (routes are all bi-directional) in the format "Place-Place2".
	 * 
	 * @param fileName 	the name of a file containing valid board information
	 **/
	public static void setup(RiskBoard board,String fileName){
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			while(br.ready()){
				String input = br.readLine();
				
				if (input.contains("Region: ")){
					// setup regions
					setupRegions(board, br);
				}else if(input.contains("Routes:")){
					// setup routes
					board.setupRoutes(br);
				} else if (input.contains("Players: ")){
					// setup number of players
					board.setupPlayers(br);
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found: ");
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("File read error: ");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * creates a territory object, and adds it to the territories list until 
	 * it reaches a blank line or end of file.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	private static void setupRegions(RiskBoard board, BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) return;
			else board.addTerritory(new Territory(input));
		}
	}

}
