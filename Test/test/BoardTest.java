package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import risk.RiskBoard;
import risk.Territory;

public class BoardTest {

	@Test
	public void testNewBlankBoard() {
		RiskBoard board = new RiskBoard();
		board.setup("TestRisk.txt");
		
		for(Territory territory : board.getTerritories()){
			assertEquals(0,territory.getTroops());
		}
	}

	@Test
	public void testBoardSetup() {
		RiskBoard board = new RiskBoard();
		board.setup("TestRisk.txt");
		
		List<Territory> expected = new ArrayList<>();
		expected.add(new Territory("Alaska"));
		expected.add(new Territory("Alberta"));
		
		expected.add(new Territory("Argentina"));
		expected.add(new Territory("Brazil"));
			
		assertEquals(expected.toString(), board.getTerritories().toString());
	}
	
	@Test
	public void testChangeTroops(){
		RiskBoard board = new RiskBoard();
		board.setup("TestRisk.txt");
		
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
		RiskBoard board = new RiskBoard();
		board.setup("TestRisk.txt");
		
		assertEquals("None", board.getFaction("Alaska"));
		
		board.setFaction("Alaska", "Blue");
		assertEquals("Blue", board.getFaction("Alaska"));
		
		
		board.setFaction("Argentina", "Black");
		board.setFaction("Alaska", "Black");
		assertEquals("Black", board.getFaction("Argentina"));
		assertEquals("Black", board.getFaction("Alaska"));
	}
	
	
}
