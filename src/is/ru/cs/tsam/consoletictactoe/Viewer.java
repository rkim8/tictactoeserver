package is.ru.cs.tsam.consoletictactoe;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Viewer {
	private DatagramSocket socket;
	private final int BOARDSIZE = 9;
	
	public Viewer()  
	{
		try
		{
			socket = new DatagramSocket( 9876 );
		}
		catch( Exception e ) 
		{
			System.out.println( e.toString() + "\n" );
			e.printStackTrace();
		}
	}
	
	public String whichGame()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type in the number of the game:");
		String s = scanner.nextLine();
		return s;
	}
	
	public String setServerAddress()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type in the server IP Address:");
		String s = scanner.nextLine();
		return s;
	}
		
	public void sendPacket()
	{
		String gameNo = whichGame();
		String address = setServerAddress();
		// TODO Athuga hvort að tenging næst við server, lúppa þangað til að svo er.
		try
		{
			byte data[] = gameNo.getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(address),6789);
			socket.send(packet);
			System.out.println("You've sent request for the board of game number: " + gameNo );
		}
		catch( Exception e ) 
		{
			System.out.println( e.toString() + "\n" );
			e.printStackTrace();
		}
	}
	
	public void receivePacket()
	{
		try
		{
			// TODO Ef að vitlaust númer leiks kemur, þá þarf að birta það. 
			// 		
			DatagramPacket packet = new DatagramPacket(new byte[BOARDSIZE], BOARDSIZE);
			socket.receive( packet ); // wait for packet
			String curGameStatus = new String(packet.getData());
			System.out.println("Game status: \n" + curGameStatus);
		}
		catch( IOException e)
		{
			System.out.println( e.toString() + "\n");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		Viewer v = new Viewer();
		v.sendPacket();

		
	}
	

}
	

