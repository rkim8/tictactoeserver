package is.ru.cs.tsam.consoletictactoe;

/**
 * A TicTacToe game which can be used to work in
 * a console environment. Does not include a main method
 * and needs a interface implementation.
 * 
 * @author Verkefnahópur í Tölvusamskiptum
 *
 */
public class TicTacToe {
	
	private final static int SIZE = 3;
	private Square[][] board;
	private boolean playerOneTurn = true;
	private int turns = 0;
	
	public TicTacToe() {
		board = new Square[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				board[i][j] = new Square();
	}
	
	/**
	 * Marks the square with a X or an O depending on the player
	 * 
	 * @param nSquare	The number of the square from 0-8
	 * @param nPlayer	The number of the player, either 0 or 1
	 * @return			True if successful, false otherwise
	 */
	public boolean markSquare( int nSquare, int nPlayer) {
		if (nSquare < 0 || nSquare > 8)
			return false;
		if((playerOneTurn && nPlayer == 0) || !playerOneTurn && nPlayer == 1)
			if(board[nSquare/3][nSquare%3].mark(nPlayer)) {
				playerOneTurn = !playerOneTurn;
				turns++;
				return true;
			}
		return false;
	}

	/**
	 * Goes through the board and finds out if either player has won the game
	 * 
	 * @return	The number of the winner (0 or 1) -2 for stalemate or -1 if game isn't over.
	 */
	public int getWinner() {
		for (int i = 0; i < SIZE; i++)
			if(board[i][0].getOwnedBy() != -1
					&& board[i][0].getOwnedBy() == board[i][1].getOwnedBy()
					&& board[i][1].getOwnedBy() == board[i][2].getOwnedBy())
				return board[i][0].getOwnedBy();
		for (int i = 0; i < SIZE; i++)
			if(board[0][i].getOwnedBy() != -1
					&& board[0][i].getOwnedBy() == board[1][i].getOwnedBy()
					&& board[1][i].getOwnedBy() == board[2][i].getOwnedBy())
				return board[0][i].getOwnedBy();
		if(board[0][0].getOwnedBy() != -1
				&& board[0][0].getOwnedBy() == board[1][1].getOwnedBy()
				&& board[1][1].getOwnedBy() == board[2][2].getOwnedBy())
			return board[0][0].getOwnedBy();
		if(board[0][2].getOwnedBy() != -1
				&& board[0][2].getOwnedBy() == board[1][1].getOwnedBy()
				&& board[1][1].getOwnedBy() == board[2][0].getOwnedBy())
			return board[0][2].getOwnedBy();
		if (turns >= 9) return -2;
		return -1;		
		// TODO Find out if it's a stalemate
	}
	
 	public String toString() {
		String output = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				output += " ";
				output += board[i][j];
				int column = i*3+j;
				if (output.endsWith(" ")) output += "(" + column + ")";
				else output += " ";
				output += " ";
				if (j < 2) output += "|";
			}
			output += "\n";
			if (i < 2) output += "-----------------\n";
		}
		return output;
	}

}
