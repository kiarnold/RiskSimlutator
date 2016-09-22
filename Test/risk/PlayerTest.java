package risk;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import risk.BoardUtils.Colors;

public class PlayerTest{
	Player player;
	String name = "Jimmy Dean";
	Colors color = BoardUtils.Colors.BLUE;
	
	@Before
	public void setUp() {
		player = new Player(name, color);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGettersAndSetters() {
		Colors newColor = BoardUtils.Colors.GREEN;
		int reserveTroops = 12;
		player.setActive(true);
		player.setName("Boss Hog");
		player.setPlayerColor(newColor);
		player.setReserveTroops(reserveTroops);
		
		assertEquals(true, player.isActive());
		assertEquals("Boss Hog", player.getName());
		assertEquals(newColor, player.getPlayerColor());
		assertEquals(reserveTroops, player.getReserveTroops());
	}
	
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(Player.class).verify();
	}
	
}