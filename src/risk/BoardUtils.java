package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardUtils {
	
	// Private constructor to prevent creating BoardUtil objects.
	private BoardUtils(){
		throw new AssertionError(); // Never call this
	}
	
	
	/**
	 * Colors of game pieces.
	 **/
	public static enum Colors {
		BLACK, BLUE, GREEN, PINK, RED, YELLOW, NONE;

		/**
		 * Returns a random color not currently in the players list
		 * 
		 * @return A random, unused color
		 */
		public static Colors getRandomColor(RiskBoard board) {
			// get a list of the colors not used
			List<Colors> colors = new ArrayList<Colors>();

			for (Colors col : Colors.values()) {
				if (uniquePlayer(board, col) && !col.equals(Colors.NONE))
					colors.add(col);
			}

			// roll a random number corresponding each element
			int rand = rollDice(colors.size()) - 1;

			// return the random element
			return colors.get(rand);
		}
	}
		
	//TODO: this input should be a standard format ie. XML, JSON etc...
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
	public static void setup(RiskBoard board, String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			while (br.ready()) {
				String input = br.readLine();

				if (input.contains("Region: ")) {
					// setup regions
					setupRegions(board, br);
				} else if (input.contains("Routes:")) {
					// setup routes
					setupRoutes(board, br);
				} else if (input.contains("Players: ")) {
					// setup number of players
					setupPlayers(board, br);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found: ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File read error: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * creates a territory object, and adds it to the territories list until 
	 * it reaches a blank line or end of file.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	private static void setupRegions(RiskBoard board, BufferedReader br) throws IOException {
		while(br.ready()) {
			String input = br.readLine();
			if(input.equals("")) {
				return;
			} else {
				board.addTerritory(new Territory(input));
			}
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
	private static void setupRoutes(RiskBoard board, BufferedReader br) throws IOException {
		while (br.ready()) {
			String input = br.readLine();
			if (input.equals("")) {
				return;
			} else {
				String[] route = input.split("-");
				addConnection(board, route[0], route[1]);
			}
		}
	}	
	
	/**
	 * Method to add connections to territories. Note: all connections are 2 way.
	 * Will ignore adds where either territory cannot be found.
	 * 
	 * @param from	Territory to start in
	 * @param to	Territory to end in
	 **/
	public static void addConnection(RiskBoard board, String from, String to) {
		Territory terraFrom = null;
		Territory terraTo = null;
		for (Territory terra : board.getTerritories()) {
			if (terra.getName().equals(from)) {
				terraFrom = terra;
			} else if (terra.getName().equals(to)) {
				terraTo = terra;
			}
		}

		if (terraFrom != null && terraTo != null) {
			terraFrom.addConnection(terraTo);
			terraTo.addConnection(terraFrom);
		}
	}

	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds players and assigns them a random Colors.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information
	 **/
	private static void setupPlayers(RiskBoard board, BufferedReader br) throws IOException {
		while (br.ready()) {
			String input = br.readLine();
			if (input.equals("")) {
				board.setPlayerList(new ArrayList<Colors>());
				return;
			} else {
				int num = Integer.parseInt(input);
				addPlayers(board, num);
			}
		}
	}
	
	/**
	 * Method that will add the specified number of players using a random color
	 * from the Colors enum.
	 * 
	 * @param num	the number of players to add, min: 0, max: 6
	 **/
	private static void addPlayers(RiskBoard board,int num){
		// error checking for out of bounds
		if (num < 0) num = 0;
		else if (num > 6) num = 6;
		
		// assigning a random, unused color
		for(int i = 0; i<num; i++){
			Colors playerColor = Colors.getRandomColor(board);
			
			while(!uniquePlayer(board, playerColor)){
				playerColor = Colors.getRandomColor(board);
			}
			
			board.addPlayer(playerColor);
		}
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
	
	/**
	 * Helper method to check if a given Colors enum is in the player list.
	 * 
	 * @param playerColor	Colors enum in question
	 * @return		true if no other is present, false otherwise
	 **/
	private static boolean uniquePlayer(RiskBoard board, Colors playerColor){
		for(Colors color : board.getPlayerList()){
			if(color.equals(playerColor)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets up the board with random pieces from each player in the player list.
	 * Will check for minimum players (3) in the player list.
	 **/
	public static void randomStart(RiskBoard board) {
		// Error check for minimum number of players.
		List<Colors> players = board.getPlayerList();
		List<Territory> territories = board.getTerritories();

		if (players.size() < 3) {
			return;
		}
		
		int count = 0;

		// randomize territories list
		Collections.shuffle(territories);

		// iterate through the territories and assign each player in turn.
		for (Territory terra : territories) {
			board.setFaction(terra.getName(), players.get(count));
			count++;
			
			if (count >= players.size()) {
				count = 0;
			}
		}
	}
}
