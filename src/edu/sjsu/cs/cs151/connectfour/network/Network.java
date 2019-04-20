package edu.sjsu.cs.cs151.connectfour.network;

import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import edu.sjsu.cs.cs151.connectfour.View.Button;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;

public class Network extends ConnectFourGameWindow{

	private String player;
	
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;
	
	public Network(ConnectFourMainWindow parent, String player) {
		super(parent);
		this.player = player;
		
		Border border = BorderFactory.createTitledBorder(player);
		super.setBorder(border);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(getGame().getCurrentPlayer().equals(player)) {
			
			super.actionPerformed(event);

			event.setSource(new Button(getXCoord(), getYCoord()));
			sendMove((Button) event.getSource());
		}
	}
	
	 //Start the server
	public void startRunning(){
		try{
			setupStreams();
			whilePlaying();	
		}catch(EOFException eofException){
			System.out.println("Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	//Establish connections
	public void setupStreams() throws IOException{
		
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}
	
	public void whilePlaying() throws IOException{
		do{
			try{
				
				String loc =(String) input.readObject();
				
				int x = Integer.parseInt(loc.substring(0, loc.indexOf(",")));
				int y = Integer.parseInt(loc.substring(loc.indexOf(",")+1));
				
				String temp = player.equals("Player 1") ? "Player 2" : "Player 1";
				getGame().oneTurn(temp, x);
				
				drawNewPiece(temp, x, y);
			
			}catch(ClassNotFoundException classNotFoundException){
				System.out.println("The user has sent an unknown object!");
			}
		}while(true);//end it
	}
	
	private void sendMove(Button toSend){
		
		try{
			output.writeObject(toSend.getXCoord()+","+toSend.getYCoord());
			output.flush();
		}catch(IOException ioException){
			//chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}
	
	public void closeConnection(){
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
}
