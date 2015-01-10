package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class RiskBoard {
	private static List<Territory> territories;
	private static List<Colors> players;
	
	/**
	 * Colors of game pieces.
	 **/
	public static enum Colors{
		 BLACK, BLUE, GREEN, PINK, RED, YELLOW, NONE;
		 
		 /**
		  * Returns a random color not currently in the players list
		  * 
		  * @return	A random, unused color 
		  */
		 public static Colors getRandomColor(){
		 	// get the colors not used
		 	List<Colors> colors = new ArrayList<Colors>();
		 	
		 	for(Colors col: Colors.values()){
		 		if(uniquePlayer(col) && !col.equals(Colors.NONE)) colors.add(col);
		 	}
		 	
		 	// roll a random number corresponding each element
		 	int rand = rollDice(colors.size())-1;
		 	
		 	// return the random element
		 	return colors.get(rand);
		 }
	}
	
	// A new board will be blank, with no territories and no connections
	public RiskBoard(){ 
		territories = new ArrayList<Territory>();
		players = new ArrayList<Colors>();
	}

	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds connection to territory objects in the territories list until 
	 * it reaches a blank line or end of file. The accepted format is:
	 * "Territory1-Terrotory2";
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	public void setupRoutes(BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) return;
			else {
				String[] route = input.split("-");
				addConnection(route[0],route[1]);
			}
			
		}
	}
	
	
	/**
	 * Method to add connections to territories. Note: all connections are 2 way.
	 * Will ignore adds where either territory cannot be found.
	 * 
	 * @paramfrom	Territory to start in
	 * @param to	Territory to end in
	 **/
	public void addConnection(String from, String to){
		Territory terraFrom = null; 
		Territory terraTo = null;
		for(Territory terra : territories){
			if (terra.getName().equals(from)){
				terraFrom = terra;
			}
			else if(terra.getName().equals(to)){
				terraTo = terra;
			}
		}
		
		if(terraFrom != null && terraTo != null){
			terraFrom.addConnection(terraTo);
			terraTo.addConnection(terraFrom);
		}
	}
	
	
	/**
	 * Looks up a territory and returns that territory's connections list.
	 * 
	 * @param territory	the territory to look up	
	 * @return			a list of connections from the given territory
	 */
	public List<Territory> getConnections(String territory){
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				return terra.getConnections();
			}
		}
		return null;
	}
	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * creates a territory object, and adds it to the territories list until 
	 * it reaches a blank line or end of file.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	public void setupRegions(BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) return;
			else territories.add(new Territory(input));
		}
	}
	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds players and assigns them a random Colors.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	public void setupPlayers(BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) {
				players = new ArrayList<Colors>();
				return;
			} else {
				int num = Integer.parseInt(input);
				addPlayers(num);
			}
		}
	}
	
	/**
	 * Method that will add the specified number of players using a random color
	 * from the Colors enum.
	 * 
	 * @param num	the number of players to add, min: 0, max: 6
	 **/
	private void addPlayers(int num){
		// error checking for out of bounds
		if (num < 0) num = 0;
		else if (num > 6) num = 6;
		
		// assigning a random, unused color
		for(int i = 0; i<num; i++){
			Colors playerColor = Colors.getRandomColor();
			while(!uniquePlayer(playerColor)){
				playerColor = Colors.getRandomColor();
			}
			players.add(playerColor);
		}
	}
	
	/**
	 * Helper method to check if a given Colors enum is in the player list.
	 * 
	 * @param playerColor	Colors enum in question
	 * @return		true if no other is present, false otherwise
	 **/
	private static boolean uniquePlayer(Colors playerColor){
		for(Colors color : players){
			if(color.equals(playerColor)) return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @return	the list of players	
	 */
	public List<Colors> getPlayerList(){
		return players;
	}

	/**
	 * Get method for territories.
	 * 
	 * @return	the territories in List<Territory> form.
	 **/
	public List<Territory> getTerritories() {return territories;}

	/**
	 * Method to get the number of troops in a particular territory.
	 * Method will return 0 for calls without a valid name.
	 * 
	 * @param territory	the name of the territory to get info from
	 * @return		returns the number of troops in a territory
	 **/
	public int getTroops(String territory) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				return terra.getTroops();
			}
		}
		return 0;
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
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				int troops = terra.getTroops() + num;
				if(troops > 0){
					terra.setTroops(troops);
				} else {
					terra.setTroops(0);
				}
			}
		}
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
		if(getFaction(defender).equals(getFaction(attacker))) return;
		
		// Error check num is not more than available troops.
		if (num > 3) num = 3;
		if (num > attackTroops-1) num = attackTroops-1;
		
		// Check for an empty territory, will end method if true.
		if(defendTroops <= 0) {
			setFaction(defender, getFaction(attacker));
			changeTroops(defender, num);
			return;
		}
		
		// Find max troops available to defend.
		int dNum = 1;
		if (defendTroops > 1) dNum = 2;
		
		//dice rolls
		int[] defendRolls = getRolls(dNum);
		int[] attackRolls = getRolls(num);
		
		// take away troops based on the highest rolls (defender's advantage)
		if (dNum <= num){
			for (int i = 0; i < defendRolls.length; i++){
				if(defendRolls[i] >= attackRolls[i]) changeTroops(attacker, -1); 
				else changeTroops(defender, -1);
			}
		} else {
			for (int i = 0; i < attackRolls.length; i++){
				if(defendRolls[i] >= attackRolls[i]) changeTroops(attacker, -1); 
				else changeTroops(defender, -1);
			}
		}
		
		/* Check for 0 troops in defending territory, if so 
		 * move # of attacking troops into territory and switch faction
		*/ 
		
		if(getTroops(defender) <= 0) {
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
		
		for(int i = 0; i< num; i++){
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
		return (int) (Math.random()*i) + 1;
	}

	/**
	 * Sets up the board with random pieces from each player in the player list.
	 * Will check for minimum players (3) in the player list.
	 **/
	public void randomStart() {
		// Error check for minimum number of players.
		if (players.size() < 3) return;
		
		int count = 0;
		
		// randomize territories list
		Collections.shuffle(territories);
		
		// iterate through the territories and assign each player in turn.
		for (Territory terra : territories) {
			terra.setFaction(players.get(count));
			count++;
			if(count >= players.size()) count = 0;
		}
	}

	
	/*
	 * GETTERS AND SETTERS
	 */
	
	/**
	 * Add a territory to the list.
	 * @param terra	Territory to add
	 */
	public static void addTerritory(Territory terra){ territories.add(terra); }
	
	/**
	 * Sets the name of the faction for a particular territory.
	 * 
	 * @param territory	the name of the territory
	 * @param faction	the name of the faction to change it to
	 **/
	public void setFaction(String territory, Colors faction) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
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
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				return terra.getFaction();
			}
		}
		return Colors.NONE;
	}
}
