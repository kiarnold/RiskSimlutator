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
		String fileName = "TestBoard.txt";
		
		RiskBoard board = JSONBoardImport.newBoard(fileName);
		
	}


}
