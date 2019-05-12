package edu.sjsu.cs.cs151.connectfour.Model;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.connectfour.Controller.Message;
import edu.sjsu.cs.cs151.connectfour.Controller.SetGameBorderMessage;
import edu.sjsu.cs.cs151.connectfour.View.View;
import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

public class Client extends Network implements Runnable {

	private String serverIP;
	
	/**
	 * Join a server if available.
	 */
	@Override
	public void run() {
		ConnectFour.view.getGamePanel().setPlayer(Model.getPlayerTwo());
		try {
			ConnectFour.queue.put(new SetGameBorderMessage(Model.getPlayerTwo()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			//Connect to Server
			isActive = true;
			serverIP = findServer();
			if(serverIP == null) {
				return;
			}
			connection = new Socket(InetAddress.getByName(serverIP), port);
			super.startRunning();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Find server address.
	 * @return String - Server IP
	 */
	private String findServer() {
		
		// Find the server using UDP broadcast
		try {
			// Open a random port to send the package
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);
			
			byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

			// Try the 255.255.255.255 first
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						InetAddress.getByName("255.255.255.255"), port);
				c.send(sendPacket);
			} catch (Exception e) {
			}

			// Broadcast the message over all the network interfaces
			Enumeration<?> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue; // Don't want to broadcast to the loopback interface
				}

				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) {
						continue;
					}

					// Send the broadcast package!
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, port);
						c.send(sendPacket);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			// Wait for a response
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);

			// Check if received message is correct
			String message = new String(receivePacket.getData()).trim();
			if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
				// DO SOMETHING WITH THE SERVER'S IP
				c.close();
				return receivePacket.getAddress().getHostAddress();
			}
			
			// Close the port!
			c.close();
			
		} catch (IOException ex) {
			System.out.println("Oops");
		}
		return null;
	}
}