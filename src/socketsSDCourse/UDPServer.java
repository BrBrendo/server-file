package socketsSDCourse;

/**
* Federal University of Mato Grosso
* Computer Institute
* 
* Course: Distributed Systems
* 
* @author Prof. PhD. Luís Cézar Darienzo Alves
* @date: 09/02/2023
* 
* Code developed for teaching UDP and TCP Sockets. In this way, many errors and
* error handling were ignored in order to keep the code simple.
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class UDPServer extends Thread {
	static final int BUFFERSIZE = 1000;
	private byte[] buffer;
	private String message;
	private DatagramPacket datagram;
	private DatagramSocket datagramSocket;
	private int port;
	
	public UDPServer(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		
		try {
			datagramSocket = new DatagramSocket(port);
			
			for(;;){
				buffer = new byte[BUFFERSIZE];
				datagram = new DatagramPacket(buffer, buffer.length);
				
				datagramSocket.receive(datagram);
				message = new String(datagram.getData());
				System.out.println("Servidor UDP: " + message);
				
			}
			
		} catch (IOException e) {
			System.out.println("Error in UDP Server: " + e.toString());
			
		} finally {
			datagramSocket.close();
		}
		
		
	}
}
