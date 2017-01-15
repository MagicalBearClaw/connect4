package ca.mkp.connect4.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import ca.mkp.connect4.server.session.C4ServerSession;


/**
 * This Class will be responsible to keep the server running once the run()
 * method is called
 * 
 * @author Mark Parenteau, Michael Mc Mahon, Philip Kyres
 *
 */
public class C4Server {
	private ServerSocket serverSocket;
	private int serverPort;

	public C4Server(int serverPort) throws IOException
	{
		this.serverPort = serverPort;
		serverSocket = new ServerSocket(serverPort);
	}
	
	/**
	 * This method creates an infinite loop that accepts one client at a time
	 * and service them until connection is closed
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException
	{
		System.out.println("Server Has Started.....");
		System.out.println("Server IP is: " +  InetAddress.getLocalHost().getHostAddress() + " at port: " + serverPort);
		for (;;) 
		{
			Socket client = serverSocket.accept();
			// Creates a new server thread.
			C4ServerThread server =  new C4ServerThread();
			server.setClient(client);
			Thread t =  new Thread(server);
			t.start();
		}
		//Never Reached
	}
}
