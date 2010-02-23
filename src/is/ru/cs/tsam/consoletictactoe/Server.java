package is.ru.cs.tsam.consoletictactoe;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * A server for a console version of a TicTacToe game.
 * Accepts connections from {@link Client} and hands them to a {@link Game} thread.
 * 
 * @author Verkefnahópur í Tölvusamskiptum
 *
 */
public class Server {

	private ServerSocket mainSocket;
	private ArrayList<Game> game;
	private ViewerListener listener;

	/**
	 * Constructs and initializes the server. Runs the viewer listener.
	 */
	public Server() {
		game = new ArrayList<Game>();
		try {
			mainSocket = new ServerSocket(55000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		listener = new ViewerListener(game);
		listener.start();
		System.out.println("Server started, waiting for connections");
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
				System.out.println("New player added");
				game.get(nGame).addPlayer(mainSocket.accept());
				System.out.println("New player added");
			}
			catch( IOException e )
			{
				e.printStackTrace(  );
				System.exit( 1 );
			}
			System.out.println("Starting game #" + game.size());
			game.get(nGame).start();
			nGame++;
		}
	}

	/**
	 * The main method of the server class. Starts the server
	 * 
	 * @param args	None
	 */
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}
}
