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
			
			// The whole object is the array of continents
			JSONArray continents = (JSONArray) obj;
			
			// Each continent is an array of territories
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return board;
	}

}
