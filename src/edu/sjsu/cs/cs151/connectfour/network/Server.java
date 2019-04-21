package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;

public class Server extends Network {

	private ServerSocket server;
	private DatagramSocket socket;

	// constructor
	public Server(ConnectFourMainWindow parent) {
		super(parent, "Player 1");
	}

	public void startRunning() {
		broadcastMessage();
		try {
			server = new ServerSocket(8888, 2); // 8888 is a dummy port for testing, this can be changed. The 100 is
													// the maximum people waiting to connect.
			while (true) {
				// Trying to connect and have conversation
				waitForConnection();
				super.startRunning();
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	// wait for connection, then display connection information
	private void waitForConnection() throws IOException {
		connection = server.accept();
	}

	private void broadcastMessage() {
		try {
			// Keep a socket open to listen to all the UDP trafic that is destined for this
			// port
			socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			while (true) {
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

				// Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);

				// Packet received
				System.out.println(getClass().getName() + ">>>Discovery packet received from: "
						+ packet.getAddress().getHostAddress());
				System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

				// See if the packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				
				if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
					byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

					// Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
							packet.getPort());
					socket.send(sendPacket);

					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}