package edu.sjsu.cs.cs151.connectfour.network;

import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import edu.sjsu.cs.cs151.connectfour.View.Button;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;

public class Network extends ConnectFourGameWindow {

	private String player;
	private ConnectFourMainWindow parent;
	
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;

	public Network(ConnectFourMainWindow parent, String player) {
		
		super(parent);
		
		this.player = player;
		this.parent = parent;
		
		super.setBorder((Border) BorderFactory.createTitledBorder(player));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
	    
	    // Retrieves the button that was pressed. 
	    Button button = (Button)event.getSource();
	    String button_name = button.getName() != null ? button.getName() : "";
	    
	    // If the restart button was pressed...
	    if (button_name.equals("GAME_RESTART")) {

	      JLabel message = new JLabel("Do you want to restart?");
	      message.setFont(getMessageFont());
	      message.setForeground(getMessageColor());
	      
	      // Prompts the player to ensure they want to restart. 
	      int check_restart = 
	          JOptionPane.showOptionDialog(this, 
	                                       message, 
	                                       "Restart", 
	                                       JOptionPane.YES_NO_OPTION, 
	                                       JOptionPane.INFORMATION_MESSAGE, 
	                                       new ImageIcon(), 
	                                       new String[] {"Yes", "No"}, 
	                                       null); 
	      
	      // Restarts the game if the yes option was selected. 
	      if (check_restart == JOptionPane.YES_OPTION) {
	    	sendMove("GAME_RESTART");
	        restart(); 
	      }
	      return;
	    }
	    
	    
	    // If the quit button was pressed...
	    else if (button_name.equals("GAME_QUIT")) {
	      
	      JLabel message = new JLabel("Do you want to quit?");
	      message.setFont(getMessageFont());
	      message.setForeground(getMessageColor());
	      
	      // Prompts the player to ensure they want to quit. 
	      int check_quit = 
	          JOptionPane.showOptionDialog(this, 
	                                       message, 
	                                       "Quit", 
	                                       JOptionPane.YES_NO_OPTION, 
	                                       JOptionPane.INFORMATION_MESSAGE, 
	                                       new ImageIcon(), 
	                                       new String[] {"Yes", "No"}, 
	                                       null); 
	      
	      // Exits to the menu window if the yes option was selected. 
	      if (check_quit == JOptionPane.YES_OPTION) {
	    	  
	        restart();               // Resets the game logic.
	        closeConnection();
	        parent.viewMenuWindow();
	        setVisible(false);// Returns to the menu window. 
	      }
	      return;
	    }
	    
	    
	    // If a tile on the grid was pressed...
	    else {
	    	
	      int x_coord = button.getXCoord();
	      int y_coord = button.getYCoord();

	      String result = getGame().oneTurn(player, x_coord);
	      
	      if (result.startsWith("piece placed")) {
	        y_coord = Integer.parseInt(result.substring(19));
	        drawNewPiece(player, x_coord, y_coord, false);
	      }
	      
	      // If a player wins...
	      else if (result.startsWith("win")) {
	        y_coord = Integer.parseInt(result.substring(13));
	        drawNewPiece(player, x_coord, y_coord, true);
	        sendMove(x_coord+","+y_coord);
	        openDialogBox(getGame().getCurrentPlayer() + " wins!", "Winner");
	        return;
	      }
	      
	      // If the game ends in a tie...
	      else if (result.startsWith("tie")) {
	        y_coord = Integer.parseInt(result.substring(10, 11));
	        drawNewPiece(player, x_coord, y_coord, true);
	        sendMove(x_coord+","+y_coord);
	        openDialogBox("Tie!", "Tie");
	        return;
	      }
	      
	      if(!result.startsWith("wrong"))
	    	  sendMove(x_coord+","+y_coord);
	    }
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
			
			parent.viewMenuWindow();
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

	private void sendMove(String toSend) {

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
