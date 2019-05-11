package edu.sjsu.cs.cs151.connectfour.Model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

public class Network {
	
	protected boolean isActive = false;
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;
	protected int port = 8888;

	// Start the server
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
			System.out.println("Socket closed");
		}
		finally {
			closeConnection();
		}
	}

	// Establish connections
	public void setupStreams() throws IOException {

		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	public void whilePlaying() throws IOException {
		do {
			try {
				GameMoveMessage loc = (GameMoveMessage) input.readObject();
				boolean restart = loc.restart;
				
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
						ConnectFour.queue.put(new ColumnSelectedMessage(loc.x, player));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException classNotFoundException) {
				System.out.println("The user has sent an unknown object!");
			}
		} while (true);// end it
	}

	public void sendMove(GameMoveMessage move) {
		try {
			output.writeObject(move);
			output.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

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
	
	public boolean getActiveStatus() {
		return isActive;
	}

}