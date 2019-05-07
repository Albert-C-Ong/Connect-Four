package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.Model.*;
import edu.sjsu.cs.cs151.connectfour.View.Button;

/** View.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * UI for Connect Four. View is similar to ConnectFourGameWindow, but has no connection
 * to the Model class. 
 * 
 * @author Albert Ong, Holly Lind
 * @since 04.05.2019
 */
public class View extends JPanel implements ActionListener {

	/**
	 * Constructor - initializes the View's UI 
	 * @param parent - menuWindow pointer to return to when View is closed
	 * @param queue - messageQueue to add to whenever user interacts with View
	 */
	public View(ConnectFourMainWindow parent, BlockingQueue<Message> queue) {
		this.queue = queue;
		this.player = Model.getPlayerOne();
		
		// Initializes variables
	    this.parent = parent;
	    buttons = new ArrayList<ArrayList<Button>>();
	    
	    // Sets the button layout. 
	    GridBagLayout layout = new GridBagLayout();
	    setLayout(layout);
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    // Initialize the grid of Buttons
	    for (int x = 0; x < Board.getColumns(); x++) {
	      buttons.add(new ArrayList<Button>());
	      
	      for (int y = Board.getRows() - 1; y >= 0; y--) {
	        gbc.gridx = x;
	        gbc.gridy = y;
	        
	        // Creates a new button. 
	        Button add_button = new Button(x, y);
	        add_button.setPreferredSize(new Dimension(100, 100)); 
	        add_button.setOpaque(false);
	        add_button.setContentAreaFilled(false);
	        add_button.setBorderPainted(false);
	        add_button.setIcon(new ImageIcon(cwd + "\\images\\button_white.png"));
	        add_button.addActionListener(this);
	        
	        buttons.get(x).add(add_button);
	        add(add_button, gbc);
	      }
	    }
	    
	    // Adding the player text below the grid. 
	    gbc.insets = new Insets(30, 0, 0, 0); // Padding on top
	    gbc.gridy = 8; // x coordinate
	    gbc.gridx = 2; // y coordinate
	    gbc.gridwidth = 3; // width of the text object
	    add(player_text, gbc);
	    
	    // Adding the restart and quit buttons
	    Button restart_button = new Button(new ImageIcon(cwd + "\\images\\game_button_restart_deselect.png"));
	    restart_button.setRolloverIcon(new ImageIcon(cwd + "\\images\\game_button_restart_select.png"));
	    restart_button.setPreferredSize(new Dimension(200, 40));
	    restart_button.setName("GAME_RESTART");
	    restart_button.addActionListener(this);
	    restart_button.setBorderPainted(false);
	    gbc.insets = new Insets(20, 0, 0, 0); // Padding on top
	    gbc.gridy = 9;
	    gbc.gridx = 1;
	    gbc.gridwidth = 2;
	    add(restart_button, gbc);
	    
	    Button quit_button = new Button(new ImageIcon(cwd + "\\images\\game_button_quit_deselect.png"));
	    quit_button.setRolloverIcon(new ImageIcon(cwd + "\\images\\game_button_quit_select.png"));
	    quit_button.setPreferredSize(new Dimension(200, 40));
	    quit_button.setName("GAME_QUIT");
	    quit_button.addActionListener(this);
	    quit_button.setBorderPainted(false);
	    gbc.gridx = 4;
	    gbc.gridwidth = 2;
	    add(quit_button, gbc);
	    
	    // Makes the window visible. 
	    setVisible(true);
	}
	
	
	/**
	 * Draws the background (gray Connect Four board)
	 */
	public void paintComponent(Graphics g) {
		Image background = new ImageIcon(cwd + "\\images\\ConnectFourGameWindow_background.png").getImage();
		g.drawImage(background, 0, -18, null);
	}
	
	
	/**
	 * Triggers when a button is pressed
	 */
	public void actionPerformed(ActionEvent event) {
	    // Retrieves the button that was pressed. 
	    Button button = (Button)event.getSource();
	    String button_name = button.getName();
	    
	    //tile was pressed
	    if (button_name == null) {
	    	try {
	    		queue.put(new ColumnSelectedMessage(button.getXCoord(), player));
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
	    }
	    
	    //restart button was pressed
	    else if (button_name.equals("GAME_RESTART")) {
	    	midGameDialogBox("Do you want to restart?", "Restart");
	    }
	    
	    //quit button was pressed
	    else if (button_name.equals("GAME_QUIT")) {
	    	midGameDialogBox("Do you want to quit?", "Quit");
	    }
	}
	
	 
	/**
	 * Draws a button on the board, and sets the player label
	 * @param newX - x coordinate of new button
	 * @param newY - y coordinate of new button
	 * @param newColor - color of new button
	 */
	public void drawNewPiece(int newX, int newY, String newColor) {
		String icon_path;
		String player_text_path;
		Button button = buttons.get(newX).get(newY);
		
		//choose images to use for button and player turn label
		if (newColor.equals(Board.getPlayerOneColor())) {
			icon_path = "\\images\\button_red.png";
			player_text_path = "\\images\\game_window_player_2_turn_text.png";
		}
		else {
			icon_path = "\\images\\button_black.png";
			player_text_path = "\\images\\game_window_player_1_turn_text.png";
		}
		
		//set new button and player turn label
		//automatically swaps player here b/c assumes one computer for both players
		button.setIcon(new ImageIcon(cwd + icon_path));
		player_text.setIcon(new ImageIcon(cwd + player_text_path));
		if (player.equals(Model.getPlayerOne()))
			player = Model.getPlayerTwo();
		else
			player = Model.getPlayerOne();
	}
	
	
	/**
	 * Dialog box used then the game is over (via tie or win)
	 * @param message - the message displayed
	 * @param messageLabel - the dialog box label
	 */
	public void gameOverDialogBox(String message, String messageLabel) {
		//create game over message style
		JLabel initial_message = new JLabel(message);
		initial_message.setFont(message_font);
		initial_message.setForeground(message_color);
		
		//displays game over message
		JOptionPane.showOptionDialog(this, 
									initial_message, 
									messageLabel, 
									JOptionPane.OK_OPTION, 
									JOptionPane.INFORMATION_MESSAGE, 
									new ImageIcon(), 
									new String[] {"Ok"}, 
									null);
		
		//applies same style to play again message
		JLabel play_again_message = new JLabel("Play again?");
		play_again_message.setFont(message_font);
		play_again_message.setForeground(message_color);
		
		//prompts player to play again
		int play_again = JOptionPane.showOptionDialog(this, 
													play_again_message, 
													"Play again?", 
													JOptionPane.YES_NO_OPTION, 
													JOptionPane.INFORMATION_MESSAGE, 
													new ImageIcon(), 
													new String[] {"Yes", "No"},
													null);
		
		//if decided not to play again
		if (play_again == JOptionPane.NO_OPTION) {
	    	try {
	    		queue.put(new QuitGameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
		
		//if decided to play again
		else {
	    	try {
	    		queue.put(new RestartGameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
	}
	
	
	/**
	 * Dialog box used for mid-game messages
	 * @param message - the message displayed
	 * @param messageLabel - the dialog box label
	 */
	public void midGameDialogBox(String message, String messageLabel) {
		//create message style
		JLabel initial_message = new JLabel(message);
		initial_message.setFont(message_font);
		initial_message.setForeground(message_color);
		
		//displays message
		int confirm = JOptionPane.showOptionDialog(this, 
												initial_message, 
												messageLabel, 
												JOptionPane.YES_NO_OPTION, 
												JOptionPane.INFORMATION_MESSAGE, 
												new ImageIcon(), 
												new String[] {"Yes", "No"}, 
												null);

		//if cancelled selection
		if (confirm == JOptionPane.NO_OPTION) {
			//do nothing - just close the dialog box
		}
		
		//player confirmed that they wanted to quit
		else if (messageLabel.equals("Quit")) {
	    	try {
	    		queue.put(new QuitGameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
		
		//player confirmed that they wanted to restart
		else {
	    	try {
	    		queue.put(new RestartGameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
	}
	
	
	/**
	 * Empties the board, and resets the player label to player 1
	 */
	public void clearBoard() {
		//sets current player indicator to player 1
		player_text.setIcon(new ImageIcon(cwd + "\\images\\game_window_player_1_turn_text.png"));
		player = Model.getPlayerOne();
		
		//gets rid of tiles on board
		for (int x = 0; x < Board.getColumns(); x++) {
			for (int y = 0; y < Board.getRows(); y++) {
				Button currentButton = buttons.get(x).get(y);
				currentButton.setIcon(new ImageIcon(cwd + "\\images\\button_white.png"));
			}
		}
	}
	
	
	/**
	 * Returns to the main menu
	 * use when quitting game
	 */
	public void returnToMainMenu() {
		parent.viewMenuWindow();
	}
	
	
	
	private ConnectFourMainWindow parent;  
	private ArrayList<ArrayList<Button>> buttons;
	private String cwd = System.getProperty("user.dir");
	private JLabel player_text = new JLabel(new ImageIcon(cwd + "\\images\\game_window_player_1_turn_text.png")); 
	private Font message_font = new Font("Arial", Font.BOLD, 48);
	private Color message_color = new Color(255, 42, 42);
	private BlockingQueue<Message> queue;
	private String player;
}
