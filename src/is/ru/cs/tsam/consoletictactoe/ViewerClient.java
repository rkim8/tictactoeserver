package is.ru.cs.tsam.consoletictactoe;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ViewerClient {
	
	private DatagramSocket viewerSocket;
	private DatagramPacket packet;
	private byte[] incoming;
	private byte[] outgoing;
	private InetAddress address;
	private int port;
	
	public ViewerClient(String add) {
		try {
			address = InetAddress.getByName(add);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		port = 56000;
		incoming = new byte[100];
		outgoing = new byte[1];
		try {
			viewerSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public String getGame(int nGame) {
		String result = "";
		try {
		outgoing[0] = (byte)nGame;
		packet = new DatagramPacket(outgoing, outgoing.length, address, port);
		viewerSocket.send(packet);
		packet = new DatagramPacket(incoming, incoming.length);
		viewerSocket.receive(packet);
		ByteArrayInputStream bais = new ByteArrayInputStream(incoming);
		DataInputStream dis = new DataInputStream(bais);
		result = dis.readUTF();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return result;		
	}

	public static void main(String[] args) {
		ViewerClient client = new ViewerClient(args[0]);
		int n = Integer.parseInt(args[1]);
		System.out.println(client.getGame(n));
	}

}
