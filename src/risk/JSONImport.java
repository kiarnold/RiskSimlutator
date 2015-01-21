package risk;

import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.*;


public class JSONImport {
	private JSONObject jsonObject;
	private JSONParser parser;
	private RiskBoard board = new RiskBoard();
	
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
	private void parseNewBoard(JSONObject json) {
		System.out.println(json);
	}
	
	/*
	* Getters and Setters
	*/
	public RiskBoard getBoard() {
		return board;
	}
}
