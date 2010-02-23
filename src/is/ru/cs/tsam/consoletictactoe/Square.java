package is.ru.cs.tsam.consoletictactoe;

/**
 * A class representing one square in a TicTacToe game
 * 
 * @author Verkefnahópur í Tölvusamskiptum
 *
 */
public class Square {

	private int ownedBy = -1;

	/**
	 * Marks the square with a X or an O depending on the player
	 * 
	 * @param nPlayer	The number of the player, either 0 or 1
	 * @return 			True if successful, false otherwise
	 */
	public boolean mark(int nPlayer) {
		if(ownedBy == -1 && (nPlayer == 0 || nPlayer == 1)) {
			ownedBy = nPlayer;
			return true;
		}
		else return false;
	}
	
	/**
	 * Gives up the number of the player that has marked this square.
	 * 
	 * @return	Number of the player who marked this square or -1 if no one has marked it
	 */
	public int getOwnedBy() {
		return ownedBy;
	}

	/**
	 * Returns the mark of the player who has this square or an empty string if no one has marked it.
	 */
	public String toString() {
		switch(ownedBy) {
		case 0: return " X";
		case 1: return " O";
		default: return "";
		}
	}

}