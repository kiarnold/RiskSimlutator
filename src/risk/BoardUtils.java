package risk;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardUtils {
	private final static int THREE_PLAYER_PEICE_COUNT = 35;
	private final static int FOUR_PLAYER_PEICE_COUNT = 30;
	private final static int FIVE_PLAYER_PEICE_COUNT = 25;
	private final static int SIX_PLAYER_PEICE_COUNT = 20;
	
	private final static int MIN_PLAYERS = 3;
	private final static int MAX_PLAYERS = 6;
	
	// Private constructor to prevent creating BoardUtil objects.
	private BoardUtils(){
		throw new AssertionError(); // Never call this
	}
	
	/**
	 * Colors represent a player and the player's peices on the board.
	 **/
	public static enum Colors {
		BLACK(Color.BLACK), 
		BLUE(Color.BLUE), 
		GREEN(Color.GREEN), 
		PINK(Color.PINK), 
		RED(Color.RED), 
		YELLOW(Color.YELLOW), 
		NONE(Color.WHITE);
		
		private Color color;
		
		Colors(Color color) {
			this.color = color;
		}

		/**
		 * Returns a random color not currently present in the players list.
		 * 
		 * @return A random, unused Colors enum value that is not NONE
		 */
		public static Colors getRandomColor(Board board) {
			// Create a list of the colors not used and not the NONE color
			List<Colors> colors = new ArrayList<Colors>();
			for (Colors col : Colors.values()) {
				if (isColorUsed(board, col) && !col.equals(Colors.NONE))
					colors.add(col);
			}

			// roll the dic for a random number corresponding each element
			int rand = rollDice(colors.size()) - 1;

			// return the random element
			return colors.get(rand);
		}
		
		public Color getColor() {
			return color;
		}
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
	 * (routes are all bi-directional) in the format "Place1-Place2".
	 * 
	 * @param fileName 	the name of a file containing valid board information
	 **/
	// TODO: There is probably a better way to represent the data in storage.
	// Currently we are using JSON and name values.
	public static void setup(Board board, String fileName) {
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
	private static void setupRegions(Board board, BufferedReader br) throws IOException {
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
	private static void setupRoutes(Board board, BufferedReader br) throws IOException {
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
	// TODO: Is there a faster way to populate this? 
	// What happens when the board is excessively large?
	// Can we use a Map here?
	private static void addConnection(Board board, String from, String to) {
		Territory fromTerritory = null;
		Territory toTerritory = null;
		for (Territory territory : board.getTerritories()) {
			if (territory.getName().equals(from)) {
				fromTerritory = territory;
			} else if (territory.getName().equals(to)) {
				toTerritory = territory;
			}
		}

		if (fromTerritory != null && toTerritory != null) {
			fromTerritory.addConnection(toTerritory);
			toTerritory.addConnection(fromTerritory);
		}
	}

	
	/**
	 * Private method takes a BufferedReader and reads in each line, 
	 * adds players and assigns them a random Colors.
	 * 
	 * @param br 	a BufferedReader object of the file with setup information at a line containing "Players:"
	 * @param baord The board game object that is being set up.
	 **/
	private static void setupPlayers(Board board, BufferedReader br) throws IOException {
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
	 * @param num	the number of players to add, min: 3, max: 6
	 * @param baord	The board game object being set up.
	 **/
	private static void addPlayers(Board board,int num){
		// error checking for out of bounds
		if (num < MIN_PLAYERS) {
			num = MIN_PLAYERS;
		} else if (num > MAX_PLAYERS) {
			num = MAX_PLAYERS;
		}
		
		// assigning a random, unused color
		for(int i = 0; i<num; i++){
			Colors playerColor = Colors.getRandomColor(board);
			
			while(!isColorUsed(board, playerColor)){
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
	 * @param playerColor	Colors enum value to check for
	 * @return		true if no other is present, false otherwise
	 **/
	private static boolean isColorUsed(Board board, Colors playerColor){
		for(Colors color : board.getPlayerList()){
			if(color.equals(playerColor)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets up the board with random territories for each player in the player list,
	 * and adds the correct number of pieces to the player's reserves to begin a game.
	 * Will check for minimum players (3) and maximum players (6) in the player list.
	 **/
	public static void randomStart(Board board) {
		// Error check for minimum number of players.
		List<Colors> players = board.getPlayerList();
		List<Territory> territories = board.getTerritories();

		if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
			return;
		}

		// add the appropriate number to reserves
		startReserves(board, players);
		
		int count = 0;
		
		// randomize territories list
		Collections.shuffle(territories);

		// iterate through the territories and assign each player a territory in turn.
		for (Territory terra : territories) {
			board.setFaction(terra.getName(), players.get(count));
			count++;
			
			//board.removeReserves(players.get(count), 1);
			
			if (count >= players.size()) {
				count = 0;
			}
		}
	}
	
	/**
	 * Helper method to put the correct number of reserve troops for each player
	**/
	private static void startReserves(Board board, List<Colors> players) {
		int num = 0;
		if (players.size() == 3) {
			num = THREE_PLAYER_PEICE_COUNT;
		} else if (players.size() == 4) {
			num = FOUR_PLAYER_PEICE_COUNT;
		} else if (players.size() == 5) {
			num = FIVE_PLAYER_PEICE_COUNT;
		} else if (players.size() == 6) {
			num = SIX_PLAYER_PEICE_COUNT;
		}
		
		for(Colors player : players) {
			board.setPlayerReserves(player, num);
		}
	}
}
