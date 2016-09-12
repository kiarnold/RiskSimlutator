package risk;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import risk.BoardUtils.Colors;

public class TerritoryTest {
	Territory territory;
	String name = "testTerritory";
	
	@Before
	public void setUp() throws Exception {
		territory = new Territory(name);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenericTerritory() throws Exception {
		List<Territory> newList = new ArrayList<>();
		assertEquals(name,  territory.getName());
		assertEquals(newList,  territory.getConnections());
		assertEquals("", territory.getOwnerName());
		assertEquals(0, territory.getTroops());
	}
	
	@Test
	public void moveTo_EmptyTarget_NotEnough() {
		// Bootstrap a blank board
		String boardJson = "{\"territories\":[], \"players\":[]}";
		GameBoard board = BoardIO.getGameBoardFromJson(boardJson);
		
		// Add two territories
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		
		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		
		// Add two connections
		canada.addConnection(unitedStates.getName());
		unitedStates.addConnection(canada.getName());
		
		// Add a player to one territory and troops
		Player jimmy = new Player("Jimmy");
		jimmy.setActive(true);
		jimmy.setPlayerColor(Colors.BLUE);
		
		board.addPlayer(jimmy);
		canada.setOwnerName(jimmy.getName());
		canada.setTroops(0);
		
		// Call attack on one territory
		MoveResult result = canada.moveTo(unitedStates, 90);
		
		// Assert the attack was un-successful (not enough troops)
		assertNotNull(result);
		assertEquals(false, result.isSuccess());
		assertEquals("Not enough troops.", result.getReason());
		assertNotEquals(canada.getOwnerName(), unitedStates.getOwnerName());
		assertEquals(0, canada.getTroops());
		assertEquals(0, unitedStates.getTroops());
	}
	
	@Test
	public void moveTo_EmptyTarget_Success() {
		// Bootstrap a blank board
		String boardJson = "{\"territories\":[], \"players\":[]}";
		GameBoard board = BoardIO.getGameBoardFromJson(boardJson);
		
		// Add two territories
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		
		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		
		// Add two connections
		canada.addConnection(unitedStates.getName());
		unitedStates.addConnection(canada.getName());
		
		// Add a player to one territory and troops
		Player jimmy = new Player("Jimmy");
		jimmy.setActive(true);
		jimmy.setPlayerColor(Colors.BLUE);
		
		board.addPlayer(jimmy);
		canada.setOwnerName(jimmy.getName());
		canada.setTroops(100);
		
		// Call attack on one territory
		canada.moveTo(unitedStates, 90);
		
		// Assert the attack was successful
		assertEquals(canada.getOwnerName(), unitedStates.getOwnerName());
	}
	
	@Test
	public void testGettersandSetters() throws Exception {
		Territory terra2 = new Territory("terra2");
		int numTroops = 42;
		territory.setTroops(numTroops);
		territory.setOwnerName("Blue");
		territory.addConnection(terra2.getName());
		
		assertEquals(numTroops, territory.getTroops());
		assertEquals("Blue", territory.getOwnerName());
		assertEquals(1, territory.getConnections().size());
		assertEquals("terra2", territory.getConnections().get(0));
	}
	
	@Test
	public void testToString() throws Exception {
		String expected = "Territory: testTerritory\n"
				+ "Troops: 42\t" 
				+"Blue\n";
		
		territory.setOwnerName("Blue");
		territory.setTroops(42);
		
		assertEquals(expected, territory.toString());
	}
	
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(Territory.class).verify();
	}

}
