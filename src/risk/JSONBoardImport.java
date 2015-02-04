package risk;

import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.*;


public final class JSONBoardImport {
	private static JSONObject jsonObject;
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
			JSONArray continents = (JSONArray) obj;
			
			for(Object continent : continents) {
				JSONArray territories = (JSONArray) continent;
				
				for(Object territory : territories) {
					// TODO: Set up territories for board.
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
