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
		 * Returns a color not currently present in the players list.
		 * 
		 * @return An unused Colors enum value that is not NONE. 
		 * If there are no available colors, returns null.
		 */
		public static Colors getUnusedColor(List<Player> players) {
			Colors[] colors = Colors.values();
			
			// Create a list of unused colors.
			for (Colors color : colors) {
				
			}
			
			return null;
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
	public int[] getRolls(int num) {
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
}
