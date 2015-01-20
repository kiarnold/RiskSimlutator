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
			
			board = parseNewBoard(jsonObject);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses out a fresh baord from a json object.
	 * 
	 * @param json	a jason object with the board setup information
	 * @return	a risk board with the infor from the json file
	 **/
	private RiskBoard parseNewBoard(JSONObject json) {
		return null;
	}
}
