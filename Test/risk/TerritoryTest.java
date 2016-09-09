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
		assertEquals("", territory.getPlayerName());
		assertEquals(0, territory.getTroops());
	}
	
	@Test
	public void testGettersandSetters() throws Exception {
		Territory terra2 = new Territory("terra2");
		int numTroops = 42;
		territory.setTroops(numTroops);
		territory.setPlayerName("Blue");
		territory.addConnection(terra2);
		
		assertEquals(numTroops, territory.getTroops());
		assertEquals("Blue", territory.getPlayerName());
		assertEquals(1, territory.getConnections().size());
		assertEquals("terra2", territory.getConnections().get(0).getName());
	}
	
	@Test
	public void testToString() throws Exception {
		String expected = "Territory: testTerritory\n"
				+ "Troops: 42\t" 
				+"Blue\n";
		
		territory.setPlayerName("Blue");
		territory.setTroops(42);
		
		assertEquals(expected, territory.toString());
	}
	
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(Territory.class).verify();
	}

}
