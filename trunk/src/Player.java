import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: palmithor
 * Date: Feb 16, 2010
 * Time: 10:49:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends Thread
{
    private Socket m_connection;
    private TicTacToeServer serverControl;
    private int m_nPlayer;
    private char m_cMark;
    private DataInputStream input;
    private DataOutputStream output;
    public boolean threadWait = true;


    public Player( Socket inSocket, TicTacToeServer inServer, int inPlayerNo )
    {
        serverControl = inServer;
        m_nPlayer = inPlayerNo;
        m_connection = inSocket;

        try
        {
            input = new DataInputStream(m_connection.getInputStream( ) );
            output = new DataOutputStream( m_connection.getOutputStream( ) );
        }
        catch( IOException e )
        {
            e.printStackTrace(  );
            System.exit( 1 );
        }
    }                       

    public void playerMovement( int inLocation )
    {
        try
        {
            //writeUTF writes a string to the outputstream
            output.writeUTF( "Opponent marked at location: " + inLocation);
        }
        catch( IOException e )
        {
            e.printStackTrace( );
            System.exit( 1 );
        }
    }


    public void run( )
    {
        try
        {
            if( m_nPlayer == 0)
            {
                m_cMark = 'O';
                serverControl.display( "Player 1 connected" );
                output.writeUTF( "Your mark is " + m_cMark );
            }
            else
            {
                m_cMark = 'X';
                serverControl.display( "Player 2 connected" );
                output.writeUTF( "Your mark is " + m_cMark );
            }

        // Waiting for other player

            if( m_cMark == 'O' )
            {
                output.writeUTF( "Wait for another player to join the game" );
                try
                {
                    while ( threadWait )
                    {
                        wait();
                    }
                }

                catch( InterruptedException e )
                {
                    e.printStackTrace(  );
                }
            }

            output.writeUTF( "Other player connected, your move" );
            output.writeUTF( "Board locations: " );
            output.writeUTF( " 1 | 2 | 3 \n ---------- \n 4 | 5 | 6 \n ---------- \n 7 | 8 | 9 \n" );

        //Gameplay
            boolean isPlaying = true;
            while( isPlaying )
            {
                output.writeUTF( "Choose where to set your mark: " );
                int markingLoc = input.readInt( );
                if ( serverControl.validMove( markingLoc, m_nPlayer ) )
                {
                    output.writeUTF( "Valid Move" );
                }
                else
                    output.writeUTF( "Invalid move" );

                if ( serverControl.gameOver() )
                    isPlaying = false;
            }
        }
        catch ( IOException e)
        {
            e.printStackTrace( );
            System.exit( 1 );
        }
    }

}
