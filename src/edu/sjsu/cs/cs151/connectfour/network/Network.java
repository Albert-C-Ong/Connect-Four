package edu.sjsu.cs.cs151.connectfour.network;

import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.View.*;

public class Network extends GamePanel {

	private View parent;
	private BlockingQueue<Message> queue;
	
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;

	public Network(BlockingQueue<Message> queue, String player) {
		
		super(queue, false);
		
		setPlayer(player);
		this.queue = queue;
		
		super.setBorder((Border) BorderFactory.createTitledBorder(player));
	}

	
	// Start the server
	public void startRunning() {
		try {
			setupStreams();
			setVisible(true);
			whilePlaying();
		} catch (EOFException eofException) {
			
			JOptionPane.showOptionDialog(this, 
                    "Opponent has left the match", 
                    "You win!", 
                    JOptionPane.OK_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    new ImageIcon(), 
                    new String[] {"Main Menu"}, 
                    null);
			
			parent.replacePanel(this, parent.getMenuPanel());
			setVisible(false);
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
				
				String loc = (String) input.readObject();

				int index = loc.indexOf(",");
				Button b = new Button(-1, -1);
				
				if(index != -1) {
					
					int x = Integer.parseInt(loc.substring(0, loc.indexOf(",")));
					int y = Integer.parseInt(loc.substring(loc.indexOf(",") + 1));
					b = new Button(x,y);
				}
				else b.setName(loc);
				
				super.actionPerformed(new ActionEvent(b, 1001, ""));
	
			} catch (ClassNotFoundException classNotFoundException) {
				System.out.println("The user has sent an unknown object!");
			}
		} while (true);// end it
	}

	public void sendMove(String toSend) {

		try {
			output.writeObject(toSend);
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
		}
	}

}