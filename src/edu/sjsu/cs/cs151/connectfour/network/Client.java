package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.View.Button;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;

public class Client extends ConnectFourGameWindow{
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public Client(String host, ConnectFourMainWindow parent){
		super(parent);
		serverIP = host;
		System.out.println("Set Client up Successfully");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		Button button = (Button) event.getSource();
	    
	    int x_coord = button.getXCoord();
	    int y_coord = button.getYCoord();
	    
	    String result = game.oneTurn("Player 2", x_coord);
	    
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
	
	//connect to server
	public void startRunning(){
		try{
			connectToServer();
			setupStreams();
			whilePlaying();
			System.out.println("Started running Client");
		}catch(EOFException eofException){
			System.out.println("Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		System.out.println("Waiting for someone to connect to Client");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		System.out.println("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	//set up streams
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		
		System.out.println("Streams are now setup on Client");
	}
	
	//while chatting with server
	private void whilePlaying() throws IOException{
		setVisible(true);
		do{
			try{
				String loc =(String) input.readObject();
				int x = Integer.parseInt(loc.substring(0, loc.indexOf(",")));
				int y = Integer.parseInt(loc.substring(loc.indexOf(",")+1));
				game.oneTurn("Player 1", x);
				showMove(new Button(x, y), "Player 1");
			}catch(ClassNotFoundException classNotFoundException){
				//showMessage("The user has sent an unknown object!");
			}
		}while(true);//end it	
	}
	
	//Close connection
	private void closeConnection(){
		System.out.println("Closing connections on client...");
//		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//send message to server
	private void sendMove(Button toSend){
		try{
			output.writeObject(toSend.getXCoord()+","+toSend.getYCoord());
			output.flush();
			showMove(toSend, "Player 2");
		}catch(IOException ioException){
			//chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}
	
	//update chat window
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