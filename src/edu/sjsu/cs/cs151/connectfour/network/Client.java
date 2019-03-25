package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.View.Button;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;

public class Client extends ConnectFourGameWindow{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public Client(String host){
		super();
		setTitle("Player 2");
		serverIP = host;
//		userText = new JTextField();
//		userText.setEditable(false);
//		userText.addActionListener(
//				new ActionListener(){
//				public void actionPerformed(ActionEvent event){
//					sendMessage(event.getActionCommand());
//					userText.setText("");
//				}
//			}
//		);
//		add(userText, BorderLayout.NORTH);
//		chatWindow = new JTextArea();
//		add(new JScrollPane(chatWindow));
//		setSize(300, 150); //Sets the window size
//		setVisible(true);
		System.out.println("Set Client up Successfully");
	}
	
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		int y_coord = 0;
		int x = ((Button) event.getSource()).getXCoord();
		result = game.oneTurn("Player 2", x);
		System.out.println(result);
		if (result.substring(0, 12).equals("piece placed")) {
	        y_coord = Integer.parseInt(result.substring(19));
	        //drawNewPiece(current_player, x_coord, y_coord);
	      }
	      
	      else if (result.substring(0, 3).equals("win")) {
	        y_coord = Integer.parseInt(result.substring(13));
	        //drawNewPiece(current_player, x_coord, y_coord);
	    	winnerDialogBox();
	    	return;
	    }
		event.setSource(new Button(x, y_coord));
		sendMove((Button) event.getSource());
		ConnectFourGameTest.printBoard(game.board);
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
//		ableToType(true);
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