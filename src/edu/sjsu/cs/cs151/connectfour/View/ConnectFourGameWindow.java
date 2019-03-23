package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.Model.*;

/** ConnectFourGameWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Game window for Connect Four. Creates UI elements.
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 3/18/2019
 * 
 * TODO:
 *   Add timer for Player 1/Player 2 turns. 
 *   Indicate who's turn it is. 
 *   checkTie() 
 */
public class ConnectFourGameWindow extends JFrame implements ActionListener {

	
	/**
	 * initializes game window
	 * @postcondition window is created with blank board
	 */
	public ConnectFourGameWindow() {
		// initializes variables
		buttons = new ArrayList<ArrayList<Button>>();
		game = new ConnectFourGame();
		
		// sets game window
		setTitle("Connect Four");
	    setSize(new Dimension(1600, 900));
	    getContentPane().setBackground(Color.WHITE);
	    
		ImageIcon icon = new ImageIcon(cwd + "\\images\\window_icon.png");
	    setIconImage(icon.getImage());
	    
	    JLabel background = new JLabel();
	    background.setIcon(new ImageIcon(cwd + "\\images\\ConnectFourGameWindow_background.png"));
	    setContentPane(background);
	    
	    GridBagLayout layout = new GridBagLayout();
	    setLayout(layout);
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    // initialize the grid of Buttons
	    for (int x = 0; x < Board.getColumns(); x++) {
	    	buttons.add(new ArrayList<Button>());
	    	
	    	for (int y = Board.getRows() - 1; y >= 0; y--) {
	    		gbc.gridx = x;
	    		gbc.gridy = y;
	    		
	    		Button add_button = new Button(x, y);
	    		
	    		add_button.setPreferredSize(new Dimension(100, 100)); 
	            add_button.setOpaque(false);
	            add_button.setContentAreaFilled(false);
	            add_button.setBorderPainted(false);
	            
	            add_button.addActionListener(this);
	            buttons.get(x).add(add_button);
	            
	            add(add_button, gbc);
	    	}
	    }
	    
	    setVisible(true);
	}
	
	
	/**
	 * deals w/ player input whenever a button is pressed
	 */
	public void actionPerformed(ActionEvent event) {
		Button button = (Button) event.getSource();
		
		int x_coord = button.getXCoord();
		int y_coord;
		String current_player = game.getCurrentPlayer();
		
		String result = game.oneTurn(current_player, x_coord);
		
		if (result.substring(0, 12).equals("piece placed")) {
			y_coord = Integer.parseInt(result.substring(19));
			drawNewPiece(current_player, x_coord, y_coord);
		}
		
		else if (result.substring(0, 3).equals("win")) {
			y_coord = Integer.parseInt(result.substring(13));
			drawNewPiece(current_player, x_coord, y_coord);
			
			winnerDialogBox();
		}
	}
	
	
	
	private ArrayList<ArrayList<Button>> buttons;
	private String cwd = System.getProperty("user.dir");
	private ConnectFourGame game;
	
	
	// draws a piece that's just been placed
	private void drawNewPiece(String current_player, int x_coord, int y_coord) {
		String icon_path;
		Button button = buttons.get(x_coord).get(y_coord);
		
		if (current_player.equals(ConnectFourGame.getPlayerOne()))
	        icon_path = "\\images\\button_red.png";
		else
	        icon_path = "\\images\\button_black.png";
		
		button.setIcon(new ImageIcon(cwd + icon_path));
	}
	
	
	// displays message after someone wins
	// after closing dialog box, prompts for new game
	private void winnerDialogBox() {
		Font message_font = new Font("Arial", Font.BOLD, 60);
		
		JLabel winner_message = new JLabel(game.getCurrentPlayer() + " wins!");
	    winner_message.setFont(message_font);
	    JOptionPane.showMessageDialog(this, winner_message);
		
	    JLabel play_again_message = new JLabel("Play again?");
	    play_again_message.setFont(message_font);
	    int play_again = JOptionPane.showConfirmDialog(this, play_again_message, null, JOptionPane.YES_NO_OPTION);
	    
	    if (play_again == JOptionPane.YES_OPTION) {
	        game.newGame();
	        for (int x = 0; x < Board.getColumns(); x++) {
		    	for (int y = 0; y < Board.getRows(); y++) {
		    		Button currentButton = buttons.get(x).get(y);
		    		currentButton.setIcon(new ImageIcon());
		    	}
	        }
	    }
	    else
	    	dispose();
	}
	
}
