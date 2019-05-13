package edu.sjsu.cs.cs151.connectfour.Model;

import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import edu.sjsu.cs.cs151.connectfour.Controller.Message;
import edu.sjsu.cs.cs151.connectfour.Controller.SetGameBorderMessage;
import edu.sjsu.cs.cs151.connectfour.View.View;
import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

/** Server.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * @author Krish Ghiya
 * @since 12.05.2019
 */
public class Server extends Network implements Runnable {

	private ServerSocket server;
	private DatagramSocket socket;
	
	/**
	 * Starts server and accepts connection request from client.
	 */
	@Override
	public void run() {
		ConnectFour.view.getGamePanel().setPlayer(Model.getPlayerOne());
		isActive = true;
		broadcastMessage();
		
		try {
			ConnectFour.queue.put(new SetGameBorderMessage(Model.getPlayerOne()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			server = new ServerSocket(port, 1); // 8888 is a dummy port for testing, this can be changed. The 2 is
													// the maximum people waiting to connect.
			while (true) {
				// Trying to connect and have match
				connection = server.accept();
				super.startRunning();
			}
		} catch (IOException ioException) {
		}
	}
	
	/**
	 * Closes server
	 */
	@Override
	public void closeConnection() {
		try {
			super.closeConnection();
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch(NullPointerException nullEx) {	
		}
	}
	
	/**
	 * Broadcasts message to all UDP ports and waits for reply from client.
	 */
	private void broadcastMessage() {
		try {
			// Keep a socket open to listen to all the UDP trafic that is destined for this port
			socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			while (true) {
				
				// Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);

				// Receive packet
				socket.receive(packet);
				
				// See if the packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				
				if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
					
					byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

					// Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
							packet.getPort());
					socket.send(sendPacket);
					socket.close();
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
