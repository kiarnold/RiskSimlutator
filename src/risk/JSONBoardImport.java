package risk;

import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.*;


public final class JSONBoardImport {
	private static JSONParser parser = new JSONParser();
	private static RiskBoard board = new RiskBoard();
	
	// Private constructor so an instance is never created
	private JSONBoardImport(String fileName){
		throw new AssertionError(); // Never call this
	}
	
	/**
	 * Takes a file name, and reads in JSON code to create a new risk board.
	 * 
	 * @param fileName	the name of the file with valid JSON information
	 * @return			a RiskBoard setup by the file
	 */
	public static RiskBoard newBoard(String fileName){
		try {
			Object obj = parser.parse(new FileReader(fileName));
			
			// The whole object is the board containing continents
			JSONObject board = (JSONObject) obj;
			board = (JSONObject) board.get("board");
			
			// The continents are an array of territories
			JSONArray continents = (JSONArray) board.get("continents");
			
			// For each continent specified, parse each territory
			for(Object con : continents) {
				JSONObject continent = (JSONObject) con;
				
				continent.get("name"); // Name of the area, to use later?
				
				// The territories are in an array of JSON objects
				JSONArray territories = (JSONArray) continent.get("territories");
				
				// for each territory, pass the info to a parser for territories
				for(Object ter : territories) {					
					parseTerritory((JSONObject) ter);
				}
				
			}
			
			// Once the territories have all been created, add the connections 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return board;
	}

	/**
	 * Helper method to parse territories and add them to the board
	 * 
	 * @param ter	a JSONObject with a territory
	 */
	private static void parseTerritory(JSONObject ter) {
		
		// Add a territory with the name based on the JSON name
		String name = ter.get("name").toString();
		board.addTerritory(new Territory(name));
		
		// ter.get("faction"); // Saved faction data
		// ter.get("troops"); // Saved troops data
		
		//Pass the connections part to a parser to store till the end
		 addConnections((JSONArray) ter.get("connections"), name);
		
	}

	/**
	 * Helper method to parse out connection information and store that
	 * till it is needed at the end of building a board.
	 * @param name 
	 * 
	 * @param jsonArray
	 */
	private static void addConnections(JSONArray connections, String name) {
		
		//TODO: impliment
	}

}
