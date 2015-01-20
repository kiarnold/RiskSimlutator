package risk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.parser.JSONParser;


public class JSONImport {
	private JSONObject jsonObject;
	private board = new RiskBoard();
	
	public JSONImport(String fileName){
		try {
			Object obj = parser.parse(new FileReader(fileName));
			jsonObject = (JSONObject) obj;
			
			parseNewBoard(jsonObject);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses out a fresh baord from a json object.
	 * 
	 * @param json	a jason object with the board setup information
	 **/
	private parseNewBoard(JSONObject json) {
		// TODO: parse json to board
	}
	
	/*
	* Getters and Setters
	*/
	public getBoard() {
		return board;
	}
}
