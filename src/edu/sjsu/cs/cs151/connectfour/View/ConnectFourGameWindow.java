/** ConnectFourGameWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Game window for Connect Four. Creates UI elements.
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 03.04.2019
 * 
 * TODO:
 *   Indicate who's turn it is. 
 */

package edu.sjsu.cs.cs151.connectfour.View;

import edu.sjsu.cs.cs151.connectfour.Model.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class ConnectFourGameWindow extends JPanel implements ActionListener {

  // A variable that stores the parent window. 
  private ConnectFourMainWindow parent; 
  
  // A two-dimensional array that stores the grid of buttons. 
  private ArrayList<ArrayList<Button>> buttons;
  
  // Retrieves the current working directory. 
  // This is used primarily for accessing image files. 
  private String cwd = System.getProperty("user.dir");
  
  // Creates a game object. 
  private ConnectFourGame game;
  
  private int x_coord;
  private int y_coord;
  
  /** Constructor for the ConnectFourGameWindow. 
   * initializes game window
   * @postcondition window is created with blank board
   */
  public ConnectFourGameWindow(ConnectFourMainWindow parent) {
    
    // Initializes variables
    this.parent = parent;
    buttons = new ArrayList<ArrayList<Button>>();
    game = new ConnectFourGame();
    
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
    
    // Makes the window visible. 
    setVisible(true);
  }
  
  
  /* Draws the background image. */
  public void paintComponent(Graphics g) {
    Image background = new ImageIcon(cwd + "\\images\\ConnectFourGameWindow_background.png").getImage();
    g.drawImage(background, 0, -18, null);
  }
  
  
  /* The method that activates whenever a button is pressed. */
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button that was pressed. 
    Button button = (Button) event.getSource();
    
    x_coord = button.getXCoord();
    y_coord = button.getYCoord();
    String current_player = game.getCurrentPlayer();
    
    String result = game.oneTurn(current_player, x_coord);
    
    if (result.startsWith("piece placed")) {
      y_coord = Integer.parseInt(result.substring(19));
      drawNewPiece(current_player, x_coord, y_coord);
    }
    
    // If a player wins...
    else if (result.startsWith("win")) {
      y_coord = Integer.parseInt(result.substring(13));
      drawNewPiece(current_player, x_coord, y_coord);
      
      openDialogBox(game.getCurrentPlayer() + " wins!", "Winner");
    }
    
    // If the game ends in a tie...
    else if (result.startsWith("tie")) {
      y_coord = Integer.parseInt(result.substring(10, 11));
      drawNewPiece(current_player, x_coord, y_coord);
      
      openDialogBox("Tie!", "Tie");
    }
    
    // If the game ends in a tie...
    else if (result.startsWith("tie")) {
      System.out.println("The game is a tie!"); // need to implement functionality later.
    }
  }
  
  
  /* Draws a piece that's just been placed */
  public void drawNewPiece(String current_player, int x_coord, int y_coord) {
    String icon_path;
    Button button = buttons.get(x_coord).get(y_coord);
    
    if (current_player.equals(ConnectFourGame.getPlayerOne()))
          icon_path = "\\images\\button_red.png";
    else
          icon_path = "\\images\\button_black.png";
    
    button.setIcon(new ImageIcon(cwd + icon_path));
  }
  
  
  /** Displays message after game ends 
   * after closing dialog box, prompts for new game
   */
  public void openDialogBox(String message, String messageLabel) {
    
    // Styles the message font and color. 
    Font message_font = new Font("Arial", Font.BOLD, 60);
    Color message_color = new Color(255, 42, 42);
    
    // Applies the style to the winner message. 
    JLabel initial_message = new JLabel(message);
    initial_message.setFont(message_font);
    initial_message.setForeground(message_color);
    
    // Displays the winner message dialog. 
    JOptionPane.showOptionDialog(this, 
                                 initial_message, 
                                 messageLabel, 
                                 JOptionPane.OK_OPTION, 
                                 JOptionPane.INFORMATION_MESSAGE, 
                                 new ImageIcon(), 
                                 new String[] {"Ok"}, 
                                 null); 
    
    // Applies the style to the play again message.  
    JLabel play_again_message = new JLabel("Play again?");
    play_again_message.setFont(message_font);
    play_again_message.setForeground(message_color);
    
    // Prompts the player to play again. 
    int play_again = 
        JOptionPane.showOptionDialog(this, 
                                     play_again_message, 
                                     "Play again?", 
                                     JOptionPane.YES_NO_OPTION, 
                                     JOptionPane.INFORMATION_MESSAGE, 
                                     new ImageIcon(), 
                                     new String[] {"Yes", "No"}, 
                                     null); 
    
    // Resets the game logic. 
    game.newGame();
    
    // Clears all of the button icons. 
    for (int x = 0; x < Board.getColumns(); x++) {
      for (int y = 0; y < Board.getRows(); y++) {
        Button currentButton = buttons.get(x).get(y);
        currentButton.setIcon(new ImageIcon());
      }
    }
    
    // If the no button was pressed...
    if (play_again == JOptionPane.NO_OPTION){
      this.parent.viewMenuWindow(); // Returns to the menu window. 
    }
  }
  
  public ConnectFourGame getGame() {
	  return game;
  }
  
  public int getXCoord() {
	  return x_coord;
  }
  
  public int getYCoord() {
	  return y_coord;
  }  
}
