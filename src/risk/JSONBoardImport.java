package risk;

import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.*;


public class JSONBoardImport {
	private JSONObject jsonObject;
	private JSONParser parser = new JSONParser();
	private RiskBoard board = new RiskBoard();
	
	private final String NAME = "name";
	private final String CONNECTIONS = "connections";
	
	public JSONBoardImport(String fileName){
		
		try {
			Object obj = parser.parse(new FileReader(fileName));
			JSONArray continents = (JSONArray) obj;
			
			for(Object continent : continents) {
				JSONArray territories = (JSONArray) continent;
				
				for(Object territory : territories) {
					System.out.println(territory);
					//addTerritory((JSONObject) territory);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTerritory(JSONObject territory) {
		board.addTerritory(new Territory(territory
				.get("name").toString()));
	}

	/*
	* Getters and Setters
	*/
	public RiskBoard getBoard() {
		return board;
	}
}
