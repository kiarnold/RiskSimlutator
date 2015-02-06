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
		
		RiskBoard textBoard = new RiskBoard(textFile);
		RiskBoard jsonBoard = JSONBoardImport.newBoard(jsonFile);
		
		System.out.println(textBoard.getTerritories());
		
		assertSame(textBoard.getTerritories(),jsonBoard.getTerritories());
		
	}


}
