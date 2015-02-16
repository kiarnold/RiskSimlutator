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
		
		RiskBoard textBoard = new RiskBoard();
		BoardUtils.setup(textBoard, textFile);
		RiskBoard jsonBoard = JSONBoardImport.newBoard(jsonFile);
		
		assertEquals(textBoard.getTerritories(), jsonBoard.getTerritories());
		
	}

	@Test
	public void testSaveLoadGame() {
		String saveFile = "SavedGame.txt";
		String boardSetup = "TestBoard.txt";
		
		RiskBoard initial = JSONBoardImport.newBoard(boardSetup);
		
		BoardUtils.randomStart(initial);
		
		JSONBoardImport.saveGame(initial, saveFile);
		
		RiskBoard loaded = JSONBoardImport.loadGame(saveFile);
		
		assertEquals(initial.getTerritories(), loaded.getTerritories());
	}
}
