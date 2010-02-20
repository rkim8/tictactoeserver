package is.ru.cs.tsam.consoletictactoe;

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
	
	public int getOwnedBy() {
		return ownedBy;
	}

	public String toString() {
		switch(ownedBy) {
		case 0: return " X";
		case 1: return " O";
		default: return "";
		}
	}

}