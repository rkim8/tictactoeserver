package is.ru.cs.tsam.consoletictactoe;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * A thread that listens to connections from a remote viewer and
 * handles requests from them
 * 
 * @author Verkefnahópur í Tölvusamskiptum
 */
public class ViewerListener extends Thread {

	private ArrayList<Game> gameList;
	private DatagramSocket viewerSocket;
	private DatagramPacket packet;
	private byte[] incoming;
	private byte[] outgoing;

	public ViewerListener(ArrayList<Game> list) {
		gameList = list;
		incoming = new byte[1];
		try {
			viewerSocket = new DatagramSocket(56000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(true) {
			packet = new DatagramPacket(incoming, incoming.length);
			try {
				viewerSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			sendResponse((int)incoming[0], packet.getSocketAddress());
		}	
	}

	/**
	 * Responds to a request to view a game
	 * 
	 * @param nGame	Number of the game to be viewed
	 * @param sAdd	Address of the remote viewer
	 */
	public void sendResponse(int nGame, SocketAddress sAdd) {
		try {
			String gameStatus;
			System.out.println("Viewer requesting game " + nGame);
			if (nGame < gameList.size()) {
				gameStatus = gameList.get(nGame).getGame().toString();
			}
			else gameStatus = "No such game";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeUTF(gameStatus);
			outgoing = baos.toByteArray();
			packet = new DatagramPacket(outgoing, outgoing.length, sAdd);
			viewerSocket.send(packet);
			System.out.println("Request answered with:\n" + gameStatus);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}

