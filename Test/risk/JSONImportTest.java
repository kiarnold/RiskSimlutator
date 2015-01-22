package risk;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JSONImportTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCreateJSONObject() {
		JSONBoardImport json = new JSONBoardImport("TestBoard.txt");
		
		RiskBoard board = new RiskBoard();
		BoardUtils.setup(board, "TestBoard.txt");
		
		assertEquals(board, json.getBoard());
	}


}
