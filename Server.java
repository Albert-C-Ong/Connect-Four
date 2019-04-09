package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;
import edu.sjsu.cs.cs151.connectfour.View.Button;

public class Server extends ConnectFourGameWindow  {
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	private ConnectFourMainWindow parent;
	
	//constructor
	public Server(ConnectFourMainWindow parent) {
		super(parent);
		this.parent = parent;
		System.out.println("Set up Successfully");
	}
	
	public void actionPerformed(ActionEvent event) {
		int x = ((Button) event.getSource()).getXCoord();
		int y_coord = 0;
		
		System.out.println("Button!");
		
		String result = getGame().oneTurn("Player 1", x);
		//System.out.println(result);
		
		if(result.equals("wrong player; should be other player's turn")) return;
		
		boolean won = false;
		
		if (result.startsWith("piece placed")) {
		      y_coord = Integer.parseInt(result.substring(19));
		}
	     
		else if (result.startsWith("win")) {
		      y_coord = Integer.parseInt(result.substring(13));
		}
		
		event.setSource(new Button(x, y_coord));
		sendMove((Button) event.getSource());
		
		if(won) openDialogBox("Player 1" + " wins!", "Winner");
		//ConnectFourGameTest.printBoard(game.board);
	}
	
	public void startRunning(){
		try{
			server = new ServerSocket(6789, 100); //6789 is a dummy port for testing, this can be changed. The 100 is the maximum people waiting to connect.
			while(true){
				try{
					//Trying to connect and have conversation
					waitForConnection();
					setupStreams();
					whilePlaying();
					System.out.println("Started running on Server");
//				}catch(EOFException eofException){
					//showMessage("\n Server ended the connection! ");
				} finally{
					closeConnection(); //Changed the name to something more appropriate
				}
			}
		} catch (IOException ioException){
			ioException.printStackTrace();
		}
	}
	//wait for connection, then display connection information
	private void waitForConnection() throws IOException{
		System.out.println("Waiting for someone to connect to Server");
		connection = server.accept();
		System.out.println("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	//get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		
		System.out.println("Streams are now setup on Server");
	}
	
	//during the game
	private void whilePlaying() throws IOException{
		do{
			try{
				String loc = (String) input.readObject();
				int x = Integer.parseInt(loc.substring(0, loc.indexOf(",")));
				int y = Integer.parseInt(loc.substring(loc.indexOf(",")+1));
				getGame().oneTurn("Player 2", x);
				showMove(new Button(x, y), "Player 2");
			}catch(ClassNotFoundException classNotFoundException){
				//showMessage("The user has sent an unknown object!");
			}
		}while(true);//end it
	}
	
	public void closeConnection(){
		System.out.println("Closing connections on server...");
		try{
			output.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			connection.close(); //Closes the connection between you can the client
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//Send a message to the client
	private void sendMove(Button toSend){
		try{
			output.writeObject(toSend.getXCoord()+","+toSend.getYCoord());
			output.flush();
			showMove(toSend, "Player 1");
		}catch(IOException ioException){
			//chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}
	
	//update board
	private void showMove(Button update, String color){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					drawNewPiece(color, update.getXCoord(), update.getYCoord());
				}
			}
		);
	}
}