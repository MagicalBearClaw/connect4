package ca.mkp.connect4.common.packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * This class contains utility methods for sending and receiving packets
 * 
 * @author Mark Parenteau, Michael Mc Mahon, Philip Kyres
 *
 *	This class is responsible for sending and receiving a packet.
 *	its uses bit packet in order to efficiently send data over the
 *	network.
 */
public class C4PacketManager 
{
	/*
	 *  last 4 bits are user for header information (0 to 15)
	 *  the next 3 bits at for  the column index (0 to 7)
	 */
	
	// header messages.
	// max number of message type is 16 (0 to 15)
	public static final byte GAME_STARTED = 0;
	public static final byte GAME_WON =  1;
	public static final byte GAME_LOST =  2;
	public static final byte CLIENT_CONNECTED = 3;
	public static final byte CLIENT_DISCONNECTED = 4;
	public static final byte CLIENT_LOST_CONNECTION = 5;
	public static final byte SERVER_LOST_CONNECTION = 6;
	public static final byte PLAYER_MOVE = 7;
	public static final byte AI_MOVE = 8;
	public static final byte INVALID_MOVE = 9;
	public static final byte GAME_TIE = 10;
	public static final byte GAME_ABORT = 11;
	
	
	public static final int PACKET_SIZE = 1;
	
	private InputStream input;
	private OutputStream output;

	public C4PacketManager(InputStream inputNetStream, OutputStream outputNetStream) 
	{
		this.input = inputNetStream;
		this.output = outputNetStream;
	}

	/**
	 * This method is responsible for sending a packet
	 * 
	 * @throws IOException *
	 */
	public void sendPacket(byte column, byte msg) throws IOException 
	{
	    byte packet[] = new byte[PACKET_SIZE];
	    packet[0] =  packPacket(column, msg);;
		output.write(packet, 0, PACKET_SIZE);
	}

	/**
	 * This method is responsible for receiving a packet 
	 *  
	 * @throws IOException **
	 */

	public byte[] receivePacket() throws IOException, SocketException 
	{
	    int totalBytesRcvd = 0;						
	    int bytesRcvd;
	    byte packet[] = new byte[PACKET_SIZE];
	    
	    while (totalBytesRcvd < PACKET_SIZE)
	    {
	    	bytesRcvd = input.read(packet, totalBytesRcvd, PACKET_SIZE - totalBytesRcvd);
	    	totalBytesRcvd += bytesRcvd;
	    }
	    return unpackByte(packet[0]);
	}
	/*
	 *  packets the header and column bytes into one packed byte.
	 */
	private byte packPacket(byte column, byte msg)
	{
		byte packed = 0;
		
		packed |= msg;
		packed |= (column << 4);
		return packed;
	}
	
	/*
	 *  unpacks the header and column from the packed byte into a byte array.
	 */
	
	private byte[] unpackByte(byte packed)
	{
		byte header = 0;
		byte column = 0;
		
		header = (byte) (packed & 0xf);
		column = (byte)(packed >> 4 & 0xf);
		
		byte unpacked[] =  new byte[2];
		unpacked[0] = header;
		unpacked[1] = column;
		return unpacked;
	}
}
