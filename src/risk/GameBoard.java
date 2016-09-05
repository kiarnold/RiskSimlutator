package risk;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class GameBoard {
	private List<Territory> territories;
	private List<Player> players;
	
	// Private constructor. Use factory methods in BoardIO to instantiate.
	private GameBoard() {}
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result
				+ ((territories == null) ? 0 : territories.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GameBoard)) {
			return false;
		}
		GameBoard other = (GameBoard) obj;
		if (players == null) {
			if (other.players != null) {
				return false;
			}
		} else if (!CollectionUtils.isEqualCollection(players, other.players)) {
			return false;
		}
		if (territories == null) {
			if (other.territories != null) {
				return false;
			}
		} else if (!CollectionUtils.isEqualCollection(territories, other.territories)) {
			return false;
		}
		return true;
	}
	
}
