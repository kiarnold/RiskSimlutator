package risk;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		
	}
}
