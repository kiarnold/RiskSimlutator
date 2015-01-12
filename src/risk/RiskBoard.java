package risk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import risk.BoardUtils.Colors;

public class RiskBoard {
	private List<Territory> territories;
	private List<Colors> players;
	
	
	/**
	 * Default constructor setting up a blank board with 
	 * no territories and no connections.
	 */
	public RiskBoard(){ 
		territories = new ArrayList<Territory>();
		players = new ArrayList<Colors>();
	}	
	
	
	/**
	 * Optional constructor to pass a file name directly to setup a board.
	 * @param fileName	Name of a file with setup information
	 */
	public RiskBoard(String fileName){ 
		territories = new ArrayList<Territory>();
		players = new ArrayList<Colors>();
		
		BoardUtils.setup(this, fileName);
	}

	
	/**
	 * Method takes an attacking territory name and a defending 
	 * territory name, checks if it is a valid attack and rolls 
	 * with the most dice possible (3) if not given. If the defending
	 * territory reaches 0 troops, the attacker invades.
	 * 
	 * @param attacker	the attacking territory
	 * @param defender	the defending territory
	 * @param num		the number of troops to attack with (1 - 3)		
	 */
	public void attack(String attacker, String defender, int num) {
		int defendTroops = getTroops(defender);
		int attackTroops = getTroops(attacker);

		// Error check for same owner
		if (getFaction(defender).equals(getFaction(attacker))) {
			return;
		}
		
		// Error check num is not more than available troops.
		if (num > 3) {
			num = 3;
		}
		
		if (num > attackTroops - 1) {
			num = attackTroops - 1;
		}

		// Check for an empty territory, will end method if true.
		if (defendTroops <= 0) {
			setFaction(defender, getFaction(attacker));
			changeTroops(defender, num);
			return;
		}

		// Find max troops available to defend.
		int dNum = 1;
		
		if (defendTroops > 1) {
			dNum = 2;
		}
		
		// dice rolls
		int[] defendRolls = getRolls(dNum);
		int[] attackRolls = getRolls(num);

		// take away troops based on the highest rolls (defender's advantage)
		if (dNum <= num) {
			for (int i = 0; i < defendRolls.length; i++) {
				if (defendRolls[i] >= attackRolls[i]) {
					changeTroops(attacker, -1);
				} else {
					changeTroops(defender, -1);
				}
			}
		} else {
			for (int i = 0; i < attackRolls.length; i++) {
				if (defendRolls[i] >= attackRolls[i]) {
					changeTroops(attacker, -1);
				} else {
					changeTroops(defender, -1);
				}
			}
		}

		/*
		 * Check for 0 troops in defending territory, if so move # of attacking
		 * troops into territory and switch faction
		 */

		if (getTroops(defender) <= 0) {
			setFaction(defender, getFaction(attacker));
			changeTroops(defender, num);
		}
	}
	
	/**
	 * Overflow method to attack with max troops (3).
	 **/
	public void attack(String attacker, String defender) {
		attack(attacker, defender, 3);
	}
	
	/**
	 * Helper method to roll a number of dice in succession. 
	 * 
	 * @param num	the number of times to roll dice
	 * @return 	an array with the number of dice rolled.
	 **/
	private int[] getRolls(int num) {
		int[] rolls = new int[num];

		for (int i = 0; i < num; i++) {
			rolls[i] = rollDice(6);
		}

		Arrays.sort(rolls);
		return rolls;
	}

	/**
	 * Helper method to roll a die.
	 * 
	 * @param i	the number of sides on the dice.
	 * @return	the dice roll
	 **/
	private static int rollDice(int i) {
		return (int) (Math.random() * i) + 1;
	}
	
	/*
	 * *************************
	 * GETTERS AND SETTERS
	 * *************************
	 */
	
	/**
	 * Add a territory to the list.
	 * @param terra	Territory to add
	 */
	public void addTerritory(Territory terra) {
		territories.add(terra);
	}
	
	/**
	 * Get method for territories.
	 * 
	 * @return	the territories in List<Territory> form.
	 **/
	public List<Territory> getTerritories() {
		return territories;
	}
	
	/**
	 * Sets the name of the faction for a particular territory.
	 * 
	 * @param territory	the name of the territory
	 * @param faction	the name of the faction to change it to
	 **/
	public void setFaction(String territory, Colors faction) {
		for (Territory terra : territories) {
			if (terra.getName().equals(territory)) {
				terra.setFaction(faction);
			}
		}
	}
	
	/**
	 * Will return the name of the faction holding the territroy given.
	 * 
	 * @param territory	the name of the territory to look up
	 * @return 		a string with the name of the faction in control
	 **/
	public Colors getFaction(String territory) {
		for (Territory terra : territories) {
			if (terra.getName().equals(territory)) {
				return terra.getFaction();
			}
		}
		return Colors.NONE;
	}
	
	/**
	 * Method to add (positive number) or subtract (negative number) troops from a given territory.
	 * Any calls without a valid name will be ignored. This method will check to ensure the number 
	 * of troops in a territory does not fall below 0. If it does, the number will be set to 0.
	 * 
	 * @param territory	the name of the territory to add to
	 * @param num		the number of troops to add(or subtract)
	 **/
	public void changeTroops(String territory, int num) {
		for (Territory terra : territories) {
			if (terra.getName().equals(territory)) {
				int troops = terra.getTroops() + num;
				if (troops > 0) {
					terra.setTroops(troops);
				} else {
					terra.setTroops(0);
				}
			}
		}
	}
	
	/**
	 * Method to get the number of troops in a particular territory.
	 * Method will return 0 for calls without a valid name.
	 * 
	 * @param territory	the name of the territory to get info from
	 * @return		returns the number of troops in a territory
	 **/
	public int getTroops(String territory) {
		for (Territory terra : territories) {
			if (terra.getName().equals(territory)) {
				return terra.getTroops();
			}
		}
		return 0;
	}	
	
	/**
	 * Looks up a territory and returns that territory's connections list.
	 * 
	 * @param territory	the territory to look up	
	 * @return			a list of connections from the given territory
	 */
	public List<Territory> getConnections(String territory) {
		for (Territory terra : territories) {
			if (terra.getName().equals(territory)) {
				return terra.getConnections();
			}
		}
		return null;
	}
	
	/**
	 * Sets the player list.
	 * @param players	list to set the player list as.
	 */
	public void setPlayerList(ArrayList<Colors> players) {
		this.players = players;
	}
	
	/**
	 * Gets the player list.
	 * @return	the list of players	
	 */
	public List<Colors> getPlayerList() {
		return players;
	}
	
	/**
	 * Add a player to the player list
	 * @param playerColor
	 */
	public void addPlayer(Colors player) {
		players.add(player);
	}
	
}
