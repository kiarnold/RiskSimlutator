package risk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class BoardIO {
	
	// Private constructor so an instance is never created
	private BoardIO(){
		throw new AssertionError(); // Never call this
	}
	
	/**
	 * Method takes a GameBoard object and return a JSON Object by serializing the object directly.
	 * @param gameBoard the GameBoard to serialize. Null will return null.
	 * @return a string of the JSON object. Null will be returned on an error.
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
			
			return null; // Null is returned on error.
		}
		
		return jsonBoard;
	}
	
	/**
	 * Method takes in a JSON object and returns a Game Board object by de-serializing the object directly.
	 * @param boardJson the JSON string of an object. Null will return null.
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
			
			return null; // Null is returned on error.
		}
		
		return board;
	}
	
	/**
	 * Method takes in a JSON object and returns a Game Board object by de-serializing the object directly.
	 * @param boardJson the JSON string of an object. Null will return null.
	 * @return a GameBoard object de-serialized from the JSONObject. Will return null and log if there is any error.
	 */
	public static GameBoard getGameBoardFromJson(Reader boardJson) {
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
			
			return null; // Null is returned on error.
		}
		
		return board;
	}
	
	
	/**
	 * Method to save the game state in a text file with the JSON format
	 * @param board	the board to save
	 */
	public static void saveGame(GameBoard board, String fileName) {
		String boardJson = getJsonFromGameBoard(board);
		if (boardJson == null) {
			return; // TODO: How are we handling errors here?
		}
		
		FileWriter file;
		try {
			file = new FileWriter(fileName);
			
			file.write(boardJson);
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}	
	}
	
	/**
	 * Method to load the game state from a JSON formatted text file.
	 * @param fileName
	 * @return
	 */
	public static GameBoard loadGame(String fileName) {
		try {
			// Read file in.
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String fileInput = "";
			while (reader.ready()) {
				fileInput += reader.readLine();
			}
			reader.close();
			

			return getGameBoardFromJson(fileInput);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
