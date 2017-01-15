package ca.mkp.connect4.server.application;

import java.io.IOException;

import ca.mkp.connect4.server.C4Server;

/**
 * This class will be the application for the server side
 * 
 * @author Mark Parenteau, Michael Mc Mahon, Philip Kyres
 *
 */
public class C4ServerApp {

	public static void main(String[] args) 
	{
		try
		{
			C4Server server =  new C4Server(50000);
			server.run();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
