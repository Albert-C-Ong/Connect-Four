package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;
import edu.sjsu.cs.cs151.connectfour.View.Button;

public class Server extends Network  {
	
	private ServerSocket server;
	
	//constructor
	public Server(ConnectFourMainWindow parent) {
		super(parent, "Player 1");
	}
	
	public void startRunning(){
		try{
			server = new ServerSocket(6789, 100); //6789 is a dummy port for testing, this can be changed. The 100 is the maximum people waiting to connect.
			while(true){
					//Trying to connect and have conversation
					waitForConnection();
					super.startRunning();
			}
		} catch (IOException ioException){
			ioException.printStackTrace();
		}
	}
	//wait for connection, then display connection information
	private void waitForConnection() throws IOException{
		connection = server.accept();
		System.out.println("Now connected to " + connection.getInetAddress().getHostName());
	}
}