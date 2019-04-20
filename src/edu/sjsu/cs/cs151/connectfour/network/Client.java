package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;

public class Client extends Network{
	
	private String serverIP;
	
	//constructor
	public Client(ConnectFourMainWindow parent){
		super(parent, "Player 2");
	}
	
	//connect to server
	public void startRunning(){
		try {
			connectToServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.startRunning();
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
	}	
	
	 public String findServer() {
			// Find the server using UDP broadcast
			  try {
			    //Open a random port to send the package
			    DatagramSocket c = new DatagramSocket();
			    c.setSoTimeout(1500);
			    c.setBroadcast(true);

			    byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

			    //Try the 255.255.255.255 first
			    try {
			      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 6789);
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
			          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
			          c.send(sendPacket);
			        } catch (Exception e) {
			        }
			        }
			    }
			    
			    //Wait for a response
			    byte[] recvBuf = new byte[15000];
			    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			    try {
			    	c.receive(receivePacket);
			    } catch(SocketTimeoutException e) {
			    	c.close();
			    	return "No Host";
			    }

			    //Check if the message is correct
			    String message = new String(receivePacket.getData()).trim();
			    if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
			    	c.close();
			      //DO SOMETHING WITH THE SERVER'S IP
			      return receivePacket.getAddress().getHostAddress();
			    }
			  } catch (IOException ex) {
			    ex.printStackTrace();
			  }
			return null;
		  }
	 
	 public void setServerIP(String serverIP) {
		 this.serverIP = serverIP;
	 }
}