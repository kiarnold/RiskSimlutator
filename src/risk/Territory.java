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
	
	/**
	 * Method to move (or attack one territory). Specify the territory to move to and the number of troops to move in.
	 * The method will move if owned by the same person, or attack if owned by another player.
	 * @param target The territory to attack
	 * @param numberOfTroops The number of troops to attack with.
	 */
	public void moveTo(Territory target, int numberOfTroops) {
		// Check numberOfTroops
		if (numberOfTroops < 1) {
			return;
		}
		// Check if a connection exists, if not no movement can happen.
		if (!this.getConnections().contains(target.getName())) {
			return;
		}

		// If this is owned by the same player as target, do a move, otherwise do an attack
		if (target.getOwnerName().equals(this.getOwnerName())) {
			// Do move
			this.moveToTerritory(target, numberOfTroops);
		} else {
			// Do attack
			this.attackTerritory(target, numberOfTroops);
		}
	}
	
	
	private void attackTerritory(Territory target, int numberOfTroops) {
		int attackers;
		int defenders;

		// Check attacker
		if (this.getTroops() <= 1) {
			// Check for insufficient troops
			return;
		} else if (this.getTroops() == 2) {
			// numberOfTroops must be greater than 0 at this point and the attacker only has 1 available troop to attack.
			attackers = 1;
		} else {
			// Can only attack with one or two.
			attackers = numberOfTroops >= 2 ? 2 : 1;
		}
		
		// Check defender
		if (target.getTroops() <= 0) {
			// target is empty, no attack, change owner and move. 
			target.setOwnerName(this.getOwnerName());
			moveToTerritory(target, numberOfTroops);
			return;
		} else{
			// Set the max available defenders
			defenders = target.getTroops() >= 3 ? 3 : target.getTroops();
		}
		
		// Roll Dice for both sides
		int[] attackerRolls = BoardUtils.getRolls(attackers);
		int[] defenderRolls = BoardUtils.getRolls(defenders);
		
		// Defender's advantage, remove troops from the low roller
		for (int i = 0; i < attackerRolls.length; i ++) {
			if (attackerRolls[i] <= defenderRolls[i]) {
				attackers --;
				this.setTroops(this.getTroops() - 1);
			} else {
				target.setTroops(target.getTroops() - 1);
			}
		}
		
		// Check if empty, if so move troops in
		if (target.getTroops() <= 0) {
			target.setOwnerName(this.getOwnerName());
			moveToTerritory(target, attackers);
			return;
		}
	}

	private void moveToTerritory(Territory target, int numberOfTroops) {
		// Remove troops here
		this.addTroops(-numberOfTroops);
		// Add troops there
		target.addTroops(numberOfTroops);
	}
	
	/* Adders and Removers */
	
	/**
	 * Method to add troops to a territory. Number given is incremented with the existing troop count. 
	 * @param numberOfTroops The number of troops to add to the territory.
	 * @return The current number of troops after changes.
	 */
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

	public void addConnection(Territory territory) {
		connections.add(territory.getName());
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
