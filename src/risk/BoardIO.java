package risk;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import risk.BoardUtils.Colors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class BoardIO {
	
	// Private constructor so an instance is never created
	private BoardIO(){
		throw new AssertionError(); // Never call this
	}
	
	/**
	 * Method takes a GameBoard object and return a JSON Object by serializing the object directly.
	 * @param gameBoard the GameBoard to serialize.
	 * @return A JSON object searialized from the GameBoard. Will return null and log if there is any error.
	 */
	public static String getJsonFromGameBoard (GameBoard gameBoard) {
		if (gameBoard == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonBoard;
		try {
			jsonBoard = mapper.writeValueAsString(gameBoard);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null; // Null is returned on any error.
		}
		
		return jsonBoard;
	}
	
	/**
	 * Method takes in a JSON object and returns a Game Board object by de-serializing the object directly.
	 * @param boardJson the JSONObject that matches a GameBoard
	 * @return a GameBoard object de-serialized from the JSONObject. Will return null and log if there is any error.
	 */
	public static GameBoard getGameBoardFromJson(String boardJson) {
		if (boardJson == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		
		
		GameBoard board;
		try {
			board = mapper.readValue(boardJson, GameBoard.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null; // All errors will return null
		}
		
		return board;
	}
	
//	public static Board newBoard(String fileName) {
//		return createBoard(fileName, true);
//	}
//	
//	/**
//	 * Takes a file name, and reads in JSON code to create a risk board.
//	 * 
//	 * @param fileName	the name of the file with valid JSON information
//	 * @param newBoard	True if the call should ignore troop and faction data
//	 * @return		a RiskBoard setup by the file
//	 */
//	private static Board createBoard(String fileName, boolean newBoard){
//		try {
//			Object obj = parser.parse(new FileReader(fileName));
//			
//			// The whole object is the board containing territories
//			JSONObject board = (JSONObject) obj;
//			board = (JSONObject) board.get("board");
//				
//			// The territories are in an array of JSON objects
//			JSONArray territories = (JSONArray) board.get("territories");
//				
//			// TODO: implement difference between load and new
//			// for each territory, pass the info to a parser for territories
//			for(Object ter : territories) {	
//				if(newBoard) {
//					parseNewTerritory((JSONObject) ter);
//				} else {
//					parseSavedTerritory((JSONObject) ter);
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//		return board;
//	}
//
//	/**
//	 * Helper method to parse territories and add them to the board
//	 * 
//	 * @param ter	a JSONObject with a territory
//	 */
//	private static void parseSavedTerritory(JSONObject ter) {
//		
//		// Add a territory with the name based on the JSON name
//		String name = ter.get("name").toString();
//		Territory terra = new Territory(name);
//		
//		// Saved faction data
//		Colors faction = Colors.valueOf((String)ter.get("faction"));
//		terra.setFaction(faction); 
//		// Saved troops data
//		int troops = Integer.parseInt((String) ter.get("troops"));
//		terra.setTroops(troops); 
//		
//		// Create Territory objects and add them to the connections list	
//		for (Object obj : (JSONArray) ter.get("connections")) {
//			String newName = (String) obj;
//			terra.addConnection(new Territory(newName));
//		}
//				
//		board.addTerritory(terra);
//	}
//	
//	/**
//	 * Helper method to parse territories and add them to the board
//	 * 
//	 * @param ter	a JSONObject with a territory
//	 */
//	private static void parseNewTerritory(JSONObject ter) {
//		
//		// Add a territory with the name based on the JSON name
//		String name = ter.get("name").toString();
//		Territory terra = new Territory(name);
//		
//		// Create Territory objects and add them to the connections list	
//		for (Object obj : (JSONArray) ter.get("connections")) {
//			String newName = (String) obj;
//			terra.addConnection(new Territory(newName));
//		}
//				
//		board.addTerritory(terra);
//	}
//	
//	/**
//	 * Method to save the game state in a text file with the JSON format
//	 * @param board	the board to save
//	 */
//	// TODO: Needs to be rewritten to save the board as well.
//	public static void saveGame(Board board, String fileName) {
//		FileWriter file;
//		
//		// TODO: Add writing as continents
//		try {
//			List<Territory> territories = board.getTerritories();
//			file = new FileWriter(fileName);
//			
//			JSONArray territorisJson = new JSONArray();
//			
//			for(Territory terra : territories) {
//				JSONObject obj = new JSONObject();
//				obj.put("name", terra.getName());
//				obj.put("faction", terra.getFaction().name());
//				obj.put("troops", terra.getTroops());
//				
//				JSONArray connections = new JSONArray();
//				for(Territory ter : terra.getConnections()) {
//					connections.add(ter.getName());	
//				}
//				        		
//				obj.put("connections", connections);
//				
//				territorisJson.add(obj);
//			}
//			
//			file.write(territorisJson.toJSONString());
//			file.flush();
//			file.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			
//		}	
//	}
//	
//	/**
//	 * Method to load a saved board state from a file.
//	 * 
//	 * @param fileName	the file name where the board is saved
//	 * @return		the board created by the load
//	 */
//	// TODO: Not working becuase save needs to save to whole board.
//	public static Board loadGame(String fileName) {
//		return createBoard(fileName, false);
//	}
}
