package risk;

import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.*;


public class JSONImport {
	private JSONObject jsonObject;
	private JSONParser parser = new JSONParser();
	private RiskBoard board = new RiskBoard();
	
	public JSONImport(String fileName){
		
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	* Getters and Setters
	*/
	public RiskBoard getBoard() {
		return board;
	}
}
