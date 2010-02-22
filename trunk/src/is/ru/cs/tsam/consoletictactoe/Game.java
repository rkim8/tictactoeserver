package is.ru.cs.tsam.consoletictactoe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * A thread object the runs a game of TicTacToe with 2 clients
 * 
 * @author Verkefnahópur í Tölvusamskiptum
 */
public class Game extends Thread {

	private DataInputStream[] input;
	private DataOutputStream[] output;
	private Socket[] player;
	private TicTacToe game;


	public Game() {
		player = new Socket[2];
		input = new DataInputStream[2];
		output = new DataOutputStream[2];
		game = new TicTacToe();
	}

	/**
	 * Adds a new client to the thread
	 * 
	 * @param connection	The socket which is connected to the client
	 */
	public void addPlayer(Socket connection) {
		int i;
		if (player[0] == null)
			i = 0;
		else if (player[1] == null)
			i = 1;
		else return;
		player[i] = connection;
		try{
			input[i] = new DataInputStream(connection.getInputStream());
			output[i] = new DataOutputStream(connection.getOutputStream());
			output[i].writeInt(i);
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void run() {
		boolean isPlaying = true;
		int nPlayerTurn = 0;	

		try {
			output[nPlayerTurn].writeBoolean(true);
			while(isPlaying) {
				output[nPlayerTurn].writeUTF(game.toString());
				int nSquare = input[nPlayerTurn].readInt();
				if (game.markSquare(nSquare, nPlayerTurn)) {
					output[nPlayerTurn].writeBoolean(true);
					output[nPlayerTurn].writeUTF(game.toString());
					int winner = game.getWinner();
					if (winner != -1) {
						isPlaying = false;
						output[nPlayerTurn].writeBoolean(isPlaying);
					}
					nPlayerTurn = (nPlayerTurn +1) % 2;
					output[nPlayerTurn].writeBoolean(isPlaying);
				}
				else {
					output[nPlayerTurn].writeBoolean(false);
					output[nPlayerTurn].writeBoolean(true);				
				}
			}
			output[0].writeInt(game.getWinner());
			output[1].writeInt(game.getWinner());
		}
		catch( IOException e) {
			e.printStackTrace(  );
			System.exit( 1 );
		}
	}

	public TicTacToe getGame() {
		return game;
	}
}
