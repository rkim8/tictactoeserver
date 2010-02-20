package is.ru.cs.tsam.consoletictactoe;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * A server for a console version of a TicTacToe game
 * 
 * @author Verkefnah�pur � T�lvusamskiptum
 *
 */
public class Server {

	private ServerSocket mainSocket;
	private ArrayList<Game> game;

	public Server() {
		game = new ArrayList<Game>();
		try {
			mainSocket = new ServerSocket(55000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts the server and waits for connections from clients
	 * and creates a new {@link Game} thread for every 2 players
	 */
	public void start() {
		int nGame = 0;
		while(true)
		{
			Game g = new Game();
			game.add(g);
			try
			{
				game.get(nGame).addPlayer(mainSocket.accept());
				game.get(nGame).addPlayer(mainSocket.accept());
			}
			catch( IOException e )
			{
				e.printStackTrace(  );
				System.exit( 1 );
			}  
			game.get(nGame).start();
			nGame++;
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}

}
