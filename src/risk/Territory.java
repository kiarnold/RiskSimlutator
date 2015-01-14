package risk;

import java.util.ArrayList;
import java.util.List;

import risk.BoardUtils.Colors;

public class Territory {
	private int troops;
	private String name;
	private List<Territory> connections;
	private Colors faction;
	
	/**
	 * Constructor builds the object with name initalized, 
	 * troops set to 0, and an empty connections list.
	 * 
	 * @param name	the name of the territory.
	 **/
	public Territory(String name) {
		troops = 0;
		this.name = name;
		connections = new ArrayList<Territory>();
		faction = Colors.NONE;
	}

	public int getTroops() {
		return troops;
	}

	public String getName() {
		return name;
	}

	public void setTroops(int num) {
		this.troops = num;
	}

	public void setFaction(Colors faction) {
		this.faction = faction;
	}

	public Colors getFaction() {
		return faction;
	}

	public void addConnection(Territory terra) {
		connections.add(terra);
	}

	public List<Territory> getConnections() {
		return connections;
	}
	
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
		sb.append(faction.name());
		sb.append("\n");
		
		return sb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Territory other = (Territory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
