package risk;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import risk.RiskBoard;
import risk.BoardUtils.Colors;
import risk.Territory;

public class BoardTest {
	RiskBoard board;
	private String alaska = "Alaska";
	private String alberta = "Alberta";
	private String argentina = "Argentina";
	private String brazil = "Brazil";
	
	@Before
	public void setUp(){
		board = new RiskBoard();
		BoardUtils.setup(board, "TestRisk.txt");
	}

	@Test
	public void testNewBlankBoard() {		
		for(Territory territory : board.getTerritories()){
			assertEquals(0,territory.getTroops());
		}
	}

	@Test
	public void testBoardSetup() {
		List<Territory> expected = new ArrayList<>();
		
		expected.add(new Territory(alaska));
		expected.add(new Territory(alberta));
		
		expected.add(new Territory(argentina));
		expected.add(new Territory(brazil));
			
		assertEquals(expected.toString(), board.getTerritories().toString());
	}
	
	@Test
	public void testChangeTroops(){
		board.changeTroops(argentina, 5);
		assertEquals(5, board.getTroops(argentina));
		
		board.changeTroops(argentina, 2);
		assertEquals(7, board.getTroops(argentina));
		
		board.changeTroops(argentina, -2);
		assertEquals(5, board.getTroops(argentina));
		
		board.changeTroops(argentina, -2);
		assertEquals(3, board.getTroops(argentina));
		
		board.changeTroops(argentina, -2);
		assertEquals(1, board.getTroops(argentina));
		
		board.changeTroops(argentina, -20);
		assertEquals(0, board.getTroops(argentina));
	}
	
	@Test
	public void testFactions(){
		assertEquals(Colors.NONE, board.getFaction(alaska));
		
		board.setFaction(alaska, Colors.BLUE);
		assertEquals(Colors.BLUE, board.getFaction(alaska));
		
		
		board.setFaction(argentina, Colors.BLACK);
		board.setFaction(alaska, Colors.BLACK);
		assertEquals(Colors.BLACK, board.getFaction(argentina));
		assertEquals(Colors.BLACK, board.getFaction(alaska));
	}
	
	@Test
	public void testRoutes(){
		List<Territory> connections = new ArrayList<>();//Arrays.asList(new Territory(alberta), new Territory(argentina));
		connections.add(new Territory(alberta));
		connections.add(new Territory(argentina));
		
		assertEquals(connections.toString(), board.getConnections(alaska).toString());
	}
	
	@Test
	public void testAttack(){
		board.changeTroops(alaska, 2);
		board.changeTroops(alberta, 2);
		
		board.setFaction(alaska, Colors.PINK);
		board.setFaction(alberta, Colors.BLACK);
		
		board.attack(alaska, alberta);
		
		assertTrue(board.getTroops(alaska)!=2 || board.getTroops(alberta)!=2);
		assertTrue(board.getTroops(alaska)==1 || board.getTroops(alberta)==1);
	}
	
	@Test
	public void testTooFewToAttack(){
		board.changeTroops(alaska, 1);
		board.changeTroops(alberta, 10);
		
		board.setFaction(alaska, Colors.PINK);
		board.setFaction(alberta, Colors.BLACK);
		
		board.attack(alaska, alberta);
		
		assertTrue(board.getTroops(alaska)==1);
		assertTrue(board.getTroops(alberta)==10);
	}
	
	@Test
	public void testTakeTerritory() {
		board.changeTroops(alaska, 1);
		board.changeTroops(alberta, 100);
		
		board.setFaction(alaska, Colors.PINK);
		board.setFaction(alberta, Colors.BLACK);
		
		
		// WARNING: Chances of not taking the territory are very, very small, 
		// but there is a chance this test will fail.
		for(int i=0; i<1000; i++){
			board.attack(alberta, alaska);
		}
		
		assertSame(Colors.BLACK, board.getFaction(alaska));
		assertSame(3, board.getTroops(alaska));
	}
	
	@Test
	public void testTakeTerritoryWithTwo() {
		board.changeTroops(alaska, 1);
		board.changeTroops(alberta, 100);
		
		board.setFaction(alaska, Colors.PINK);
		board.setFaction(alberta, Colors.BLACK);
		
		
		// WARNING: Chances of not taking the territory are very, very small, 
		// but there is a chance this test will fail.
		//TODO: fix this then, don't introduce an intermittent failure this early and knowingly
		for (int i = 0; i < 1000; i++) {
			board.attack(alberta, alaska, 2);
		}
		
		assertSame(Colors.BLACK, board.getFaction(alaska));
		assertSame(2, board.getTroops(alaska));
	}
	
	
	@Test
	public void testPlayerList() {
		assertFalse(board.getPlayerList().equals(new ArrayList<Colors>()));
	}
	
	@Test
	public void testAssignRandomTerritories() {
		BoardUtils.randomStart(board);
		
		assertTrue(board.getFaction(alaska) != Colors.NONE);
		assertTrue(board.getFaction(alberta) != Colors.NONE);
		assertTrue(board.getFaction(brazil) != Colors.NONE);
		assertTrue(board.getFaction(argentina) != Colors.NONE);
	}
	
}
