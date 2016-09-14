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
