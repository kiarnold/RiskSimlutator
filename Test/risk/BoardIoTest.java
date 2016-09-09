package risk;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BoardIoTest {
	private String boardJson;
	private GameBoard gameBoard;

	@Before
	public void setUp() throws Exception {
		
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void compatableMethods_FromGameBoard_Null() {
		boardJson = BoardIO.getJsonFromGameBoard(gameBoard);
		GameBoard newGameBoard = BoardIO.getGameBoardFromJson(boardJson);
		
		assertEquals(gameBoard, newGameBoard);
	}
	
	@Test
	public void compatableMethods_FromJson_Null() {
		gameBoard = BoardIO.getGameBoardFromJson(boardJson);
		String newBoardJson = BoardIO.getJsonFromGameBoard(gameBoard);
		
		assertEquals(boardJson, newBoardJson);
	}

	@Test
	public void getJsonFromGameBoard_EmptyBoard() {
		String emptyBoard = "{\"territories\":[],\"players\":[]}";
		
		gameBoard = BoardIO.getGameBoardFromJson(emptyBoard);
		
		assertNotNull(gameBoard);
		assertNotNull(gameBoard.getTerritories());
		assertTrue(gameBoard.getTerritories().isEmpty());
		assertNotNull(gameBoard.getPlayers());
		assertTrue(gameBoard.getPlayers().isEmpty());
		
		String newEmptyBoard = BoardIO.getJsonFromGameBoard(gameBoard);
		
		assertEquals(emptyBoard, newEmptyBoard); // Cross compatability test
	}

	@Test
	public void getGameBoardFromJson_EmptyBoard() {
		// Use JSON deserialize to create a new GameBoard object.
		ObjectMapper mapper = new ObjectMapper();
		try {
			gameBoard = mapper.readValue("{}", GameBoard.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(gameBoard);
		gameBoard.addTerritory(null);
		gameBoard.addPlayer(null);
		
		boardJson = BoardIO.getJsonFromGameBoard(gameBoard);
		
		assertNotNull(boardJson);
		assertEquals("{\"territories\":[null],\"players\":[null]}", boardJson);
	}
	
	@Test
	public void saveGame_EmptyBoard() {
		// Create test JSON
		String emptyBoard = "{\"territories\":[null],\"players\":[null]}";
		
		// Create a test board
		GameBoard board = BoardIO.getGameBoardFromJson(emptyBoard);
		
		// Run save
		String fileName = "Test.sav";
		BoardIO.saveGame(board, fileName);
		
		// Read file in.
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String fileInput = "";
			fileInput = reader.readLine();
		
			// Assert same as original JSON
			assertEquals(emptyBoard, fileInput);
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
		
		// Delete file
		File testFile = new File(fileName);
		
		assert(testFile.exists());
		
		Boolean deleted = testFile.delete();
		
		assert(deleted);
	}
	
	@Test
	public void loadGame_EmptyBoard() {
		// Save a board using the same code as saveGame test
		// Create test JSON
		String emptyBoard = "{\"territories\":[null],\"players\":[null]}";
		
		// Create a test board
		GameBoard saveBoard = BoardIO.getGameBoardFromJson(emptyBoard);
		
		// Run save
		String fileName = "Test.sav";
		BoardIO.saveGame(saveBoard, fileName);
		
		// Run load
		GameBoard gameBoard = BoardIO.loadGame(fileName);
		
		// Assert the GameBoard is not null
		assertNotNull(gameBoard);
		
		// Assert the territories list is populated with 1 null;
		List<Territory> territories = gameBoard.getTerritories();
		assertNotNull(territories);
		assertNull(territories.get(0));
		
		// Assert the players list is populated with 1 null
		List<Player> players = gameBoard.getPlayers();
		assertNotNull(players);
		assertNull(players.get(0));
	}
}
