package risk;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import risk.RiskBoard;
import risk.RiskBoard.Colors;
import risk.Territory;

public class BoardTest {
	RiskBoard board;
	
	@Before
	public void setUp(){
		board = new RiskBoard();
		board.setup("TestRisk.txt");
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
		expected.add(new Territory("Alaska"));
		expected.add(new Territory("Alberta"));
		
		expected.add(new Territory("Argentina"));
		expected.add(new Territory("Brazil"));
			
		assertEquals(expected.toString(), board.getTerritories().toString());
	}
	
	@Test
	public void testChangeTroops(){
		board.changeTroops("Argentina", 5);
		assertEquals(5, board.getTroops("Argentina"));
		
		board.changeTroops("Argentina", 2);
		assertEquals(7, board.getTroops("Argentina"));
		
		board.changeTroops("Argentina", -2);
		assertEquals(5, board.getTroops("Argentina"));
		
		board.changeTroops("Argentina", -2);
		assertEquals(3, board.getTroops("Argentina"));
		
		board.changeTroops("Argentina", -2);
		assertEquals(1, board.getTroops("Argentina"));
		
		board.changeTroops("Argentina", -20);
		assertEquals(0, board.getTroops("Argentina"));
	}
	
	@Test
	public void testFactions(){
		assertEquals(Colors.NONE, board.getFaction("Alaska"));
		
		board.setFaction("Alaska", Colors.BLUE);
		assertEquals(Colors.BLUE, board.getFaction("Alaska"));
		
		
		board.setFaction("Argentina", Colors.BLACK);
		board.setFaction("Alaska", Colors.BLACK);
		assertEquals(Colors.BLACK, board.getFaction("Argentina"));
		assertEquals(Colors.BLACK, board.getFaction("Alaska"));
	}
	
	@Test
	public void testRoutes(){
		List<Territory> connections = Arrays.asList(new Territory("Alberta"), new Territory("Argentina"));		
		
		assertEquals(connections.toString(), board.getConnections("Alaska").toString());
	}
	
	@Test
	public void testAttack(){
		board.changeTroops("Alaska", 2);
		board.changeTroops("Alberta", 2);
		
		board.setFaction("Alaska", Colors.PINK);
		board.setFaction("Alberta", Colors.BLACK);
		
		board.attack("Alaska", "Alberta");
		
		assertTrue(board.getTroops("Alaska")!=2 || board.getTroops("Alberta")!=2);
		assertTrue(board.getTroops("Alaska")==1 || board.getTroops("Alberta")==1);
	}
	
	@Test
	public void testTooFewToAttack(){
		board.changeTroops("Alaska", 1);
		board.changeTroops("Alberta", 10);
		
		board.setFaction("Alaska", Colors.PINK);
		board.setFaction("Alberta", Colors.BLACK);
		
		board.attack("Alaska", "Alberta");
		
		assertTrue(board.getTroops("Alaska")==1);
		assertTrue(board.getTroops("Alberta")==10);
	}
	
	@Test
	public void testTakeTerritory() {
		board.changeTroops("Alaska", 1);
		board.changeTroops("Alberta", 100);
		
		board.setFaction("Alaska", Colors.PINK);
		board.setFaction("Alberta", Colors.BLACK);
		
		
		// WARNING: Chances of not taking the territory are very, very small, 
		// but there is a chance this test will fail.
		for(int i=0; i<1000; i++){
			board.attack("Alberta", "Alaska");
		}
		
		assertSame(Colors.BLACK, board.getFaction("Alaska"));
		assertSame(3, board.getTroops("Alaska"));
	}
	
	@Test
	public void testTakeTerritoryWithTwo() {
		board.changeTroops("Alaska", 1);
		board.changeTroops("Alberta", 100);
		
		board.setFaction("Alaska", Colors.PINK);
		board.setFaction("Alberta", Colors.BLACK);
		
		
		// WARNING: Chances of not taking the territory are very, very small, 
		// but there is a chance this test will fail.
		for(int i=0; i<1000; i++){
			board.attack("Alberta", "Alaska", 2);
		}
		
		assertSame(Colors.BLACK, board.getFaction("Alaska"));
		assertSame(2, board.getTroops("Alaska"));
	}
	
	
	@Test
	public void testPlayerList() {
		assertFalse(board.getPlayerList().equals(new ArrayList<Colors>()));
	}
	
	@Test
	public void testAssignRandomTerritories() {
		board.randomStart();
		
		assertTrue(board.getFaction("Alaska") != Colors.NONE);
		assertTrue(board.getFaction("Alberta") != Colors.NONE);
		assertTrue(board.getFaction("Brazil") != Colors.NONE);
		assertTrue(board.getFaction("Argentina") != Colors.NONE);
	}
}
