package risk;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardIoTest {
	private JSONObject boardJson;
	private GameBoard gameBoard;

	@Before
	public void setUp() throws Exception {
		
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void compatableMethods_FromGameBoard() {
		boardJson = BoardIO.getJsonFromGameBoard(gameBoard);
		GameBoard newGameBoard = BoardIO.getGameBoardFromJson(boardJson);
		
		assertEquals(gameBoard, newGameBoard);
	}
	
	@Test
	public void compatableMethods_FromJson() {
		gameBoard = BoardIO.getGameBoardFromJson(boardJson);
		JSONObject newBoardJson = BoardIO.getJsonFromGameBoard(gameBoard);
		
		assertEquals(boardJson, newBoardJson);
	}

	@Test
	public void getJsonFromGameBoard_EmptyBoard() throws ParseException {
		String emptyBoard = "{\"board\":{}}";
		JSONParser parser = new JSONParser();
		
		JSONObject obj = (JSONObject) parser.parse(emptyBoard);
		gameBoard = BoardIO.getGameBoardFromJson(obj);
		
		assertNotNull(gameBoard);
		assertNull(gameBoard.getTerritories());
		assertNull(gameBoard.getPlayers());
	}

	@Test
	public void getGameBoardFromJson_EmptyBoard() {
		
	}
}
