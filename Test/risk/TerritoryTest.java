package risk;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		assertEquals(BoardUtils.Colors.NONE, territory.getFaction());
		assertEquals(0, territory.getTroops());
	}
	
	@Test
	public void testGettersandSetters() throws Exception {
		Territory terra2 = new Territory("terra2");
		int numTroops = 42;
		territory.setTroops(numTroops);
		territory.setFaction(BoardUtils.Colors.BLUE);
		territory.addConnection(terra2);
		
		assertEquals(numTroops, territory.getTroops());
		assertEquals(BoardUtils.Colors.BLUE, territory.getFaction());
		assertEquals(1, territory.getConnections().size());
		assertEquals("terra2", territory.getConnections().get(0).getName());
	}
	
	@Test
	public void testToString() throws Exception {
		//TODO: re-implement when toString is fixed 
//		Territory terra2 = new Territory("terra2");
//		int numTroops = 42;
//		territory.setTroops(numTroops);
//		territory.setFaction(BoardUtils.Colors.BLUE);
//		territory.addConnection(terra2);
//		
//		String expected = "name=testTerritory\n"
//				+ "faction=BLUE\n"
//				+ "troops=42\n"
//				+ "connections=[name=terra2\n"
//				+ "faction=NONE\n"
//				+ "troops=0\n"
//				+ "connections=[]]";
		
		String expected = "testTerritory";
		
		assertEquals(expected, territory.toString());
	}
	
	//TODO: add JUnit extension to test hashcode and equals
	@Test
	public void testEquals() throws Exception {
		Territory terra2 = new Territory(name);
		assertEquals(terra2, territory);
		
		terra2.setTroops(4);
		assertNotEquals(terra2, territory);
		assertEquals(terra2, terra2);
	}

}
