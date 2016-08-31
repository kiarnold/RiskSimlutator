package risk;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JSONImportTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCreateJSONBoard() {
		String jsonFile = "TestBoard.txt";
		String textFile = "TestRisk.txt";
		
		Board textBoard = new Board();
		BoardUtils.setup(textBoard, textFile);
		Board jsonBoard = JSONBoardImport.newBoard(jsonFile);
		
		assertEquals(textBoard.getTerritories(), jsonBoard.getTerritories());
		
	}

	@Test
	public void testSaveLoadGame() {
		String saveFile = "SavedGame.txt";
		String boardSetup = "TestBoard.txt";
		
		Board initial = JSONBoardImport.newBoard(boardSetup);
		
		BoardUtils.randomStart(initial);
		
		JSONBoardImport.saveGame(initial, saveFile);
		
		Board loaded = JSONBoardImport.loadGame(saveFile);
		
		assertEquals(initial.getTerritories(), loaded.getTerritories());
	}
}
