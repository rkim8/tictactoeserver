import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: palmithor
 * Date: Feb 16, 2010
 * Time: 1:25:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicTacToeClient extends Thread
{
    private Socket m_connection;
    private DataInputStream input;
    private DataOutputStream output;
    private Thread m_thread;
    private char m_cMark;
    private boolean m_bMoveAllowed;
    private InetAddress IP;
    private int m_nPort;

    public TicTacToeClient( )
    {

        m_nPort = 5000;
        try
        {
            IP = InetAddress.getLocalHost();
            m_connection = new Socket( IP, m_nPort );
            input = new DataInputStream( m_connection.getInputStream( ) );
            output = new DataOutputStream( m_connection.getOutputStream( ) );
        }

        catch( IOException e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }

        m_thread = new Thread( this );
        m_thread.start( );
        
    }

    public void playGame( )
    {
        myMark = 
    }
}
