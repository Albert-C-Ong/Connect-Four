package edu.sjsu.cs.cs151.connectfour.Model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

/** Network.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * @author Krish Ghiya
 * @since 12.05.2019
 */
public class Network {
	
	protected boolean isActive = false;
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;
	protected int port = 8888;

	/**
	 * General setup of network and begins match.
	 */
	public void startRunning() {
		try {
			setupStreams();
			isActive = true;
			ConnectFour.view.replacePanel(ConnectFour.view.getLoadingPanel(), ConnectFour.view.getGamePanel());
			whilePlaying();
		} catch (EOFException eofException) {
			try {
				ConnectFour.queue.put(new PlayerLeftMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException ioException) {
		}
		finally {
			closeConnection();
		}
	}

	/**
	 * Initialize input and output streams
	 * @throws IOException
	 */
	public void setupStreams() throws IOException {

		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	/**
	 * Reads input and passes to valve.
	 * @throws IOException
	 */
	public void whilePlaying() throws IOException {
		do {
			try {
				GameMoveMessage loc = (GameMoveMessage) input.readObject();
				boolean restart = loc.getRestart();
				
				if (restart) {
					try {
						ConnectFour.queue.put(new PlayerRestartMessage());
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					String player = this.getClass().getSimpleName().equals("Client") ?
							Model.getPlayerOne() : Model.getPlayerTwo();
					try {
						ConnectFour.queue.put(new ColumnSelectedMessage(loc.getXCoord(), player));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException classNotFoundException) {
				System.out.println("The user has sent an unknown object!");
			}
		} while (true);// end it
	}

	/**
	 * Sends move to other computer.
	 * @param move, the game move to send
	 */
	public void sendMove(GameMoveMessage move) {
		try {
			output.writeObject(move);
			output.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	/**
	 * Closes the input/output streams.
	 */
	public void closeConnection() {
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (NullPointerException nullEx) {
		}
		isActive = false;
	}
	
	/**
	 * Getter for isActive
	 * @return isActive - current status of network
	 */
	public boolean getActiveStatus() {
		return isActive;
	}

}