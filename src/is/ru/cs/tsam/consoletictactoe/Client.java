package is.ru.cs.tsam.consoletictactoe;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A client for a console version of a TicTacToe game
 * 
 * @author Verkefnahópur í Tölvusamskiptum
 */
public class Client {

	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;

	/**
	 * Connects the client to a server and starts the communucation protocol.
	 * 
	 * @param address	The hostname or ip address of the server
	 */
	public void connect(String address){
		try {
			connection = new Socket(address, 55000);
			input = new DataInputStream(connection.getInputStream());
			output = new DataOutputStream(connection.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Server not found");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connection successful");

		try {
			int nPlayer = input.readInt();
			if (nPlayer == 0)
				System.out.println("You are player X, waiting for other player");
			else System.out.println("You are player O, please wait for your turn");


			while(input.readBoolean()) {
				System.out.println(input.readUTF());
				System.out.println("Choose a square to mark");
				boolean b = false;
				do {
					Scanner in = new Scanner(System.in);
					try{
						output.writeInt(in.nextInt());
						b = false;
					}
					catch (InputMismatchException e) {
						System.out.println("Please insert a valid number");
						b = true;
					}
				}
				while(b);
				if(input.readBoolean()) {
					System.out.println(input.readUTF());
					System.out.println("Wait for other player");
				}
				else {
					System.out.println("Invalid move try again");
				}
			}
			int winner = input.readInt();
			if (winner == nPlayer) System.out.println("Congratulation you won");
			else System.out.println("You suck");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Client c = new Client();
		c.connect("127.0.0.1");
	}

}
