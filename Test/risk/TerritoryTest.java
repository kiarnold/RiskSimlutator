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
	private Territory territory;
	private String name = "testTerritory";
	private GameBoard board;
	
	@Before
	public void setUp() throws Exception {
		territory = new Territory(name);
		
		// Bootstrap a blank board
		String boardJson = "{\"territories\":[], \"players\":[]}";
		board = BoardIO.getGameBoardFromJson(boardJson);
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
		// Add two territories
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		
		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		
		// Add two connections
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		
		// Add a player to one territory and troops
		Player jimmy = new Player("Jimmy", Colors.BLUE);
		jimmy.setActive(true);
		
		board.addPlayer(jimmy);
		canada.setOwnerName(jimmy.getName());
		canada.setTroops(0);
		
		// Pre-assert the board is in the expected state
		assertNotEquals(canada.getOwnerName(), unitedStates.getOwnerName());
		assertEquals(0, canada.getTroops());
		assertEquals(0, unitedStates.getTroops());
		
		// Call attack on one territory
		canada.moveTo(unitedStates, 90);
		
		// Assert the attack was un-successful and the board has not changed
		assertNotEquals(canada.getOwnerName(), unitedStates.getOwnerName());
		assertEquals(0, canada.getTroops());
		assertEquals(0, unitedStates.getTroops());
	}
	
	//TODO: Not Yet Implemented
	@Test
	public void moveTo_EmptyTarget_Success() {
		
		// Add two territories
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		
		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		
		// Add two connections
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		
		// Add a player to one territory and troops
		Player jimmy = new Player("Jimmy", Colors.BLUE);
		jimmy.setActive(true);
		
		board.addPlayer(jimmy);
		canada.setOwnerName(jimmy.getName());
		canada.setTroops(100);
		
		// Pre-Assert the board is in the expected state
		assertEquals(100, canada.getTroops());
		assertEquals(0, unitedStates.getTroops());
		assertNotEquals(canada.getOwnerName(), unitedStates.getOwnerName());
		
		// Call attack on one territory
		canada.moveTo(unitedStates, 90);
		
		// Assert the attack was successful
		assertNotEquals(100, canada.getTroops());
		assertNotEquals(0, unitedStates.getTroops());
		assertEquals(canada.getOwnerName(), unitedStates.getOwnerName());
	}
	
	// TODO: Not Yet Implemented
	@Test
	public void moveTo_noConnection_fail() {
		// Test that territories without connections cannot call a move to another territory.
		
		// Setup board
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));

		// Add troops to a territory
		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		canada.addTroops(10);
		unitedStates.addTroops(10);
		
		// Pre assert that there are no connections
		assert(canada.getConnections().isEmpty());
		assert(unitedStates.getConnections().isEmpty());
		
		// Call a moveTo
		canada.moveTo(unitedStates, 1);
		
		// Assert the board state is the same
		assert(canada.getConnections().isEmpty());
		assert(unitedStates.getConnections().isEmpty());
		
		assertEquals(10, canada.getTroops());
		assertEquals(10, unitedStates.getTroops());
	}
	
	// TODO: Not Yet Implemented
	@Test
	public void moveTo_attack_success() {
		// Test that territories will attack and cause troops to be eliminated.
		
		// Setup board
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		Player alice = new Player("Alice", Colors.BLACK);
		Player bob = new Player("Bob", Colors.BLUE);

		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		canada.addTroops(10);
		unitedStates.addTroops(10);
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		canada.setOwnerName(alice.getName());
		unitedStates.setOwnerName(bob.getName());
		
		// Pre-assert that there is a connection and the total troops are a set number.
		assertEquals("United States", canada.getConnections().get(0));
		assertEquals(20, canada.getTroops() + unitedStates.getTroops());
		
		// Call a moveTo
		canada.moveTo(unitedStates, 5);
		// Assert the board has fewer troops.
		assertNotEquals(20, canada.getTroops() + unitedStates.getTroops());
	}
	
	// TODO: Not Yet Implemented
	@Test
	public void moveTo_attackWithOne_success() {
		// Test that when attacking with only 1 unit, the total units only go down by 1
		
		// Setup board
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		Player alice = new Player("Alice", Colors.BLACK);
		Player bob = new Player("Bob", Colors.BLUE);

		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		canada.addTroops(10);
		unitedStates.addTroops(10);
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		canada.setOwnerName(alice.getName());
		unitedStates.setOwnerName(bob.getName());
		
		// Pre-assert that there is a connection and the total troops are a set number.
		assertEquals("United States", canada.getConnections().get(0));
		assertEquals(20, canada.getTroops() + unitedStates.getTroops());
		
		// Call a moveTo
		canada.moveTo(unitedStates, 1);
		
		// Assert the board has 1 fewer troops total
		assertEquals(19, canada.getTroops() + unitedStates.getTroops());
	}
	
	// TODO: Not Yet Implemented
	@Test
	public void moveTo_attackWithTwo_success() {
		// Test that when attacking with only 2 troops, the total units only go down by 2

		// Setup board
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		Player alice = new Player("Alice", Colors.BLACK);
		Player bob = new Player("Bob", Colors.BLUE);

		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		canada.addTroops(10);
		unitedStates.addTroops(10);
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		canada.setOwnerName(alice.getName());
		unitedStates.setOwnerName(bob.getName());
		
		// Pre-assert that there is a connection and the total troops are a set number.
		assertEquals("United States", canada.getConnections().get(0));
		assertEquals(20, canada.getTroops() + unitedStates.getTroops());
		
		// Call a moveTo
		canada.moveTo(unitedStates, 2);
		
		// Assert the board has 2 fewer troops total
		assertEquals(18, canada.getTroops() + unitedStates.getTroops());
	}
	
	// TODO: Not Yet Implemented
	@Test
	public void moveTo_attackWithThree_success() {
		// Test that when attacking with 3, only two troops are committed.
		
		// Setup board
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));

		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		canada.addTroops(10);
		unitedStates.addTroops(10);
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		
		// Pre-assert that there is a connection and the total troops are a set number.
		assertEquals("United States", canada.getConnections().get(0));
		assertEquals(20, canada.getTroops() + unitedStates.getTroops());
		
		// Call a moveTo
		canada.moveTo(unitedStates, 10);
		
		// Assert the board has only 2 fewer troops total
		assertNotEquals(18, canada.getTroops() + unitedStates.getTroops());
	}
	
	// TODO: Not Yet Implemented
	@Test
	public void moveTo_attackAndMove_success() {
		// Test that, when moving and attacking successfully (so that the enemy troops are reduced to 0), 
		// the commited troops will move into the territory.
		
		// Setup board
		board.addTerritory(new Territory("Canada"));
		board.addTerritory(new Territory("United States"));
		Player alice = new Player("Alice", Colors.BLACK);
		Player bob = new Player("Bob", Colors.BLUE);

		Territory canada = board.getTerritories().get(0);
		Territory unitedStates = board.getTerritories().get(1);
		canada.addTroops(10);
		unitedStates.addTroops(0);
		canada.addConnection(unitedStates);
		unitedStates.addConnection(canada);
		canada.setOwnerName(alice.getName());
		unitedStates.setOwnerName(bob.getName());
		
		// Pre-assert that there is a connection and the total troops are a set number.
		assertEquals("United States", canada.getConnections().get(0));
		assertEquals(10, canada.getTroops());
		assertEquals(0, unitedStates.getTroops());
		assertNotEquals(canada.getOwnerName(), unitedStates.getOwnerName());
		
		// Call a moveTo
		canada.moveTo(unitedStates, 2);
		
		// Assert the troops have moved from one to another and owner has changed.
		assertEquals(8, canada.getTroops());
		assertEquals(2, unitedStates.getTroops());
		assertEquals(canada.getOwnerName(), unitedStates.getOwnerName());
	}
	
	@Test
	public void testGettersandSetters() throws Exception {
		Territory terra2 = new Territory("terra2");
		int numTroops = 42;
		territory.setTroops(numTroops);
		territory.setOwnerName("Blue");
		territory.addConnection(terra2);
		
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
