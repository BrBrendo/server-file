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
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

	private int port;
	private ServerSocket listenSocket;
	private ClientConnection clientConnection;
	

	public TCPServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {

		try {
			listenSocket = new ServerSocket(port);
			for (;;) {
				Socket nextThreadSocket = listenSocket.accept();
				clientConnection = new ClientConnection(nextThreadSocket, 0);
				clientConnection.start();
			}
		} catch (IOException e) {
			System.out.println("Error in TCPServer: " + e.toString());
		} finally {
			try {
				listenSocket.close();

			} catch (IOException e) {
				System.out.println("Error in TCPServer: " + e.toString());
			}
		}

	}

	// Class responsible for handling connections
	class ClientConnection extends Thread {
		static final int BUFFERSIZE = 1000;
		int receivedMessageSize;
		byte[] buffer;
		String message;

		Socket socket;
		InputStream in;

		// Constructor to perform connection handover
		public ClientConnection(Socket socket, int threadID) throws IOException {
			this.socket = socket;
			in = this.socket.getInputStream();

		}

		@Override
		public void run() {

			buffer = new byte[BUFFERSIZE];
			message = "";

			try {
				while ((receivedMessageSize = in.read(buffer)) != -1)
					message = message + new String(buffer);

					System.out.println("Servidor TCP: " + message);

			} catch (IOException e) {
				System.out.println("Error in TCPServer: " + e.toString());
			} finally {
				try {
					socket.close();

				} catch (IOException e) {

					System.out.println("Error in TCPServer: " + e.toString());
				}

			}

		}

	}

}
