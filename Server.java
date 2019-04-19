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
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	//constructor
	public Server(ConnectFourMainWindow parent) {
		super(parent);
		System.out.println("Set up Successfully");
	}
	
	public void actionPerformed(ActionEvent event) {
		
	    Button button = (Button) event.getSource();
	    
	    int x_coord = button.getXCoord();
	    int y_coord = button.getYCoord();
	    
	    String result = game.oneTurn("Player 1", x_coord);
	    
	    if (result.startsWith("piece placed")) {
	      y_coord = Integer.parseInt(result.substring(19));
	    }
	    
	    else if(result.startsWith("wrong player; should be other player's turn")) return;
	    
	    // If a player wins...
	    else if (result.startsWith("win")) {
	      y_coord = Integer.parseInt(result.substring(13));
	      openDialogBox(game.getCurrentPlayer() + " wins!", "Winner");
	    }
	    
	    // If the game ends in a tie...
	    else if (result.startsWith("tie")) {
	      y_coord = Integer.parseInt(result.substring(10, 11));
	      openDialogBox("Tie!", "Tie");
	      return;
	    }
	    
	    // If the game ends in a tie...
	    else if (result.startsWith("tie")) {
	      System.out.println("The game is a tie!"); // need to implement functionality later.
	    }
		
		event.setSource(new Button(x_coord, y_coord));
		sendMove((Button) event.getSource());
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
				}catch(EOFException eofException){
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
	
	//during the chat conversation
	private void whilePlaying() throws IOException{
		setVisible(true);
		do{
			try{
				String loc =(String) input.readObject();
				int x = Integer.parseInt(loc.substring(0, loc.indexOf(",")));
				int y = Integer.parseInt(loc.substring(loc.indexOf(",")+1));
				game.oneTurn("Player 2", x);
				showMove(new Button(x, y), "Player 2");
			}catch(ClassNotFoundException classNotFoundException){
				//showMessage("The user has sent an unknown object!");
			}
		}while(true);//end it
	}
	
	public void closeConnection(){
		System.out.println("Closing connections on server...");
//		ableToType(false);
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
	
	//update chatWindow
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