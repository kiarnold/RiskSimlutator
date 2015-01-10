package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import risk.RiskBoard.Colors;

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
					setupRoutes(board, br);
				} else if (input.contains("Players: ")){
					// setup number of players
					setupPlayers(board, br);
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
	
	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds connection to territory objects in the territories list until 
	 * it reaches a blank line or end of file. The accepted format is:
	 * "Territory1-Terrotory2";
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	private static void setupRoutes(RiskBoard board, BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) return;
			else {
				String[] route = input.split("-");
				addConnection(board, route[0],route[1]);
			}
			
		}
	}
	
	
	/**
	 * Method to add connections to territories. Note: all connections are 2 way.
	 * Will ignore adds where either territory cannot be found.
	 * 
	 * @param from	Territory to start in
	 * @param to	Territory to end in
	 **/
	public static void addConnection(RiskBoard board, String from, String to){
		Territory terraFrom = null; 
		Territory terraTo = null;
		for(Territory terra : board.getTerritories()){
			if (terra.getName().equals(from)){
				terraFrom = terra;
			}
			else if(terra.getName().equals(to)){
				terraTo = terra;
			}
		}
		
		if(terraFrom != null && terraTo != null){
			terraFrom.addConnection(terraTo);
			terraTo.addConnection(terraFrom);
		}
	}

	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds players and assigns them a random Colors.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	public static void setupPlayers(RiskBoard board, BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) {
				board.setPlayerList(new ArrayList<Colors>());
				return;
			} else {
				int num = Integer.parseInt(input);
				board.addPlayers(num);
			}
		}
	}
}
