import java.net.ServerSocket;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: palmithor
 * Date: Feb 16, 2010
 * Time: 10:33:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class TicTacToeServer
{
    private ServerSocket server;
    private byte board[];
    private int m_nCurPlayer;
    private Player players[];
    private int m_nPort;

    public TicTacToeServer( )
    {
        m_nCurPlayer = 0;
        board = new byte [ 9 ];
        players = new Player[ 2 ];

        // set up ServerSocket
        try
        {
            server = new ServerSocket( m_nPort, 2 );
        }

        catch( IOException e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }



    public void execute( )
    {
        for ( int i = 0; i < players.length; i++)
        {
            try
            {
                players[ i ] = new Player( server.accept(), this, i);
                players[ i ].start( );
            }
            catch( IOException e )
            {
                e.printStackTrace(  );
                System.exit( 1 );
            }
        }

        // Player X is suspended until Player O connects.
        // Resume player X now.
        synchronized ( players[ 0 ] )
        {
            players[0].threadWait = false;
            players[0].notify( );
        }
    }

    public void display( String inString )
    {
        System.out.println( inString + "\n" );
    }

    public synchronized boolean validMove( int inLocation, int inPlayer )
    {
        while ( inPlayer != m_nCurPlayer )
        {
            try
            {
                wait();
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace( );
            }

        }

        if ( isMarked( inLocation ) )
        {
            return false;
        }
        else
        {
            if( m_nCurPlayer == 0 )
            {
                board[ inLocation ] = (byte) 'O';
            }
            else
            {
                board[ inLocation ] = (byte) 'X';
            }
            m_nCurPlayer = (m_nCurPlayer + 1) % 2;

            players[m_nCurPlayer].playerMovement( inLocation );
            notify();
            return true;
        }

    }

    public boolean isMarked( int inLocation )
    {
        if( board[ inLocation ] == 'O' || board[ inLocation ] == 'X' )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // TODO œtf¾ra
    public boolean gameOver()
    {
        return false;
    }

    public static void main( String args[] )
    {
        TicTacToeServer game = new TicTacToeServer();

        game.execute();
    }
}
