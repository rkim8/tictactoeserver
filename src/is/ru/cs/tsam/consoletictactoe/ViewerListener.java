package is.ru.cs.tsam.consoletictactoe;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

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

	public void sendResponse(int nGame, SocketAddress sAdd) {
		try {
			String gameStatus = gameList.get(nGame).getGame().toString();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeUTF(gameStatus);
			outgoing = baos.toByteArray();
			packet = new DatagramPacket(outgoing, outgoing.length, sAdd);
			viewerSocket.send(packet);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}

