package risk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.parser.JSONParser;


public class JSONImport {
	private JSONObject jsonObject;
	
	public JSONImport(String fileName){
		try {
			Object obj = parser.parse(new FileReader(fileName));
			jsonObject = (JSONObject) obj;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
