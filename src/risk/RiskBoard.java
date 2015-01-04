package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiskBoard {
	private List<Territory> territories;
	
	// A new board will be blank, with no territories and no connections
	public RiskBoard(){ 
		territories = new ArrayList<Territory>();
	}

	/**
	 * Will attempt to setup the board based on an input file. Territories 
	 * will be added first and then routes will be set up.
	 * <p>
	 * A valid file should contain Regions in the format "Region: Name".
	 * followed by the names of territories. After all the territories 
	 * in that region should be a blank line. (You can have as many regions as you like.)
	 * Following all the regions and territories, the routes are set up by starting 
	 * a line with "Routes:". This should be followed by a list of each connection 
	 * (routes are all bi-directional) in the format "Place-Place2".
	 * 
	 * @param fileName 	the name of a file containing valid board information
	 **/
	public void setup(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			while(br.ready()){
				String input = br.readLine();
				
				if (input.contains("Region: ")){
					// setup regions
					this.setupRegions(br);
				}else if(input.contains("Routes:")){
					// setup routes
					this.setupRoutes(br);
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found: ");
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("File read error: ");
			e.printStackTrace();
		}
	}

	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds connection to territory objects in the territories list until 
	 * it reaches a blank line or end of file. The accepted format is:
	 * "Territory1-Terrotory2";
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	private void setupRoutes(BufferedReader br) throws IOException {
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
	private void setupRegions(BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) return;
			else territories.add(new Territory(input));
		}
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
	 * Will return the name of the faction holding the territroy given.
	 * 
	 * @param territory	the name of the territory to look up
	 * @return 		a string with the name of the faction in control
	 **/
	public String getFaction(String territory) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				return terra.getFaction();
			}
		}
		return "None";
	}
	
	
	/**
	 * Sets the name of the faction for a partuicular territory.
	 * 
	 * @param territory	the name of the territory
	 * @param faction	the name of the faction to change it to
	 **/
	public void setFaction(String territory, String faction) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				terra.setFaction(faction);
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
	
	public void attack(String attacker, String defender) {
		attack(attacker, defender, 3);
	}
	
	private int[] getRolls(int num) {
		int[] rolls = new int[num];
		
		for(int i = 0; i< num; i++){
			rolls[i] = rollDice(6);
		}
		
		Arrays.sort(rolls);
		return rolls;
	}

	private int rollDice(int i) {
		return (int) (Math.random()*i) + 1;
	}

}
