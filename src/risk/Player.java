package risk;

import risk.BoardUtils.Colors;

public class Player {
	private Colors playerColor;
	private int reserveTroops;
	private boolean isActive;
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	/* Getters and Setters */
	
	/**
	 * @return the playerColor
	 */
	public Colors getPlayerColor() {
		return playerColor;
	}
	/**
	 * @param playerColor the playerColor to set
	 */
	public void setPlayerColor(Colors playerColor) {
		this.playerColor = playerColor;
	}
	/**
	 * @return the reserveTroops
	 */
	public int getReserveTroops() {
		return reserveTroops;
	}
	/**
	 * @param reserveTroops the reserveTroops to set
	 */
	public void setReserveTroops(int reserveTroops) {
		this.reserveTroops = reserveTroops;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* HashCode/Equals: Equality is based on all fields being the same */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((playerColor == null) ? 0 : playerColor.hashCode());
		result = prime * result + reserveTroops;
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
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player) obj;
		if (isActive != other.isActive) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (playerColor != other.playerColor) {
			return false;
		}
		if (reserveTroops != other.reserveTroops) {
			return false;
		}
		return true;
	}

}
