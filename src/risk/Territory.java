package risk;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public final class Territory {
	private final String name;
	private int troops;
	private List<Territory> connections;
	private String playerName;

	/**
	 * Constructor builds the object with name initialized, 
	 * troops set to 0, and an empty connections list.
	 * 
	 * @param name	the name of the territory.
	 **/
	public Territory(String name) {
		troops = 0;
		this.name = name;
		connections = new ArrayList<Territory>();
		playerName = ""; // TODO: Assign default player?
	}
	
	/* Getters and Setters */

	public int getTroops() {
		return troops;
	}
	
	public void setTroops(int num) {
		this.troops = num;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void addConnection(Territory territory) {
		connections.add(territory);
	}
	
	public void setConnections(List<Territory> connections) {
		this.connections = connections;
	}

	public List<Territory> getConnections() {
		return connections;
	}
	
	/* Hash/Equals/ToString: Equality is based on all fields being the same. */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		// Name
		sb.append("Territory: ");
		sb.append(name);
		sb.append("\n");
		
		// Troop Count
		sb.append("Troops: ");
		sb.append(troops);
		sb.append("\t");
		
		// Faction Control
		sb.append(playerName);
		sb.append("\n");
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((connections == null) ? 0 : connections.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + troops;
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
		if (!(obj instanceof Territory)) {
			return false;
		}
		Territory other = (Territory) obj;
		if (connections == null) {
			if (other.connections != null) {
				return false;
			}
		} else if (!CollectionUtils.isEqualCollection(connections, other.connections)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (playerName == null) {
			if (other.playerName != null) {
				return false;
			}
		} else if (!playerName.equals(other.playerName)) {
			return false;
		}
		if (troops != other.troops) {
			return false;
		}
		return true;
	}
	
}
