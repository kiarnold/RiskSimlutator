package risk;

import java.util.ArrayList;
import java.util.List;

public final class Territory {
	private final String name;
	private int troops;
	private List<String> connections;
	private String ownerName;

	/**
	 * Constructor builds the object with name initialized, 
	 * troops set to 0, and an empty connections list.
	 * 
	 * @param name	the name of the territory.
	 **/
	public Territory(String name) {
		troops = 0;
		this.name = name;
		connections = new ArrayList<String>();
		ownerName = "";
	}
	
	/* Attack/Move Logic*/
	
	public void moveTo(Territory target, int numberOfTroops) {
		if (target.getOwnerName().equals(this.getOwnerName())) {
			// Do move
			this.moveToTerritory(target, numberOfTroops);
		} else {
			// Do attack
			this.attackTerritory(target, numberOfTroops);
		}
	}
	
	
	private void attackTerritory(Territory target, int numberOfTroops) {
		// Find how many troops can attack
		// Find how many troops can defend
		// Roll Dice for both sides
		// Defender's advantage, remove troops from the low roller 
	}

	private void moveToTerritory(Territory target, int numberOfTroops) {
		this.addTroops(-numberOfTroops);
		target.addTroops(numberOfTroops);
	}
	
	/* Adders and Removers */
	
	public int addTroops(int numberOfTroops) {
		troops += numberOfTroops;
		return troops;
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
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName the playerName to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void addConnection(String territory) {
		connections.add(territory);
	}
	
	public void setConnections(List<String> connections) {
		this.connections = connections;
	}

	public List<String> getConnections() {
		return connections;
	}
	
	/* Hash/Equals/ToString: Equality is based on the name field (final) */
	
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
		sb.append(ownerName);
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	
}
