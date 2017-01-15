package ca.mkp.connect4.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import ca.mkp.connect4.server.session.C4ServerSession;
/**
 * This class is a threaded class that allows multiple C4ServerSession
 * to run at once so that multiple clients can connect and play
 * simultaneously.
 * 
 * @author Michael McMahon, Philip Kyres, Mark Parenteau
 *
 */
public class C4ServerThread implements Runnable 
{
	private Socket client;
	
	/**
	 * 
	 * Runs a new instance of the C4 server allowing
	 * a client to connect to it.
	 * 
	 * The server allows the client to play one or 
	 * more game(s) of connect 4.
	 */
	@Override
	public void run() 
	{
		System.out.println("Client has connected....");
		System.out.println("Client IP is: " + client.getInetAddress().getHostAddress() + " at port: " + client.getPort());
		// creates an instance of C4ServerSession which runs itself
		// make sure the client is not null.
		if(client != null)
		{
			try
			{
				C4ServerSession session= new C4ServerSession(client);
				session.play();
			}
			catch(SocketException se)
			{
				System.out.println(se.getMessage());
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}

		}

		try 
		{
			client.close();
			System.out.println("Client has disconnected....");
		} 
		catch (IOException e) 
		{
			System.out.println("Client has lost connection....");
		}

	}
	
	/**
	 * Sets the client socket.
	 * @param client
	 */
	public void setClient(Socket client)
	{
		this.client = client;
	}

}
