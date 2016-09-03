package risk;

import java.util.Hashtable;
import java.util.List;

public class GameBoard {
	private List<Territory> territories;
	private List<Player> players;
	
	// Private constructor. Use factory methods in BoardIO to instantiate.
	private GameBoard() {
		throw new AssertionError(); // Never call this.
	}
	
	/* Getters and Setters */

	/**
	 * @return the territories
	 */
	public List<Territory> getTerritories() {
		return territories;
	}

	/**
	 * @param territories the territories to set
	 */
	public void setTerritories(List<Territory> territories) {
		this.territories = territories;
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	/* Complex getters and setters */
	
	/**
	 * @param player the player to be added to the list of players.
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	/**
	 * @param territory the territory to be added to the list of territories.
	 */
	public void addTerritory(Territory territory) {
		this.territories.add(territory);
	}
	
}
