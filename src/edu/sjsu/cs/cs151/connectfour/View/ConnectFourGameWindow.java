
/** ConnectFourGameWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Game window for Connect Four. Creates UI elements.
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 24.04.2019
 */

package edu.sjsu.cs.cs151.connectfour.View;

import edu.sjsu.cs.cs151.connectfour.Model.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.ClassCastException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;


public class ConnectFourGameWindow extends JPanel implements ActionListener {

  // A variable that stores the parent window. 
  private ConnectFourMainWindow parent; 
  
  // A two-dimensional array that stores the grid of buttons. 
  private ArrayList<ArrayList<Button>> buttons;
  
  // Creates a game object. 
  private Model game;
  
  // Initializes the player turn text. 
  URL url_initial_player_text = ConnectFourMainWindow.class.getResource("/resources/game_window_player_1_turn_text.png");
  private JLabel player_text = new JLabel(new ImageIcon(url_initial_player_text));
  
  // Styles the message font and color for JDialogBox object. 
  private Font message_font = new Font("Arial", Font.BOLD, 48);
  private Color message_color = new Color(255, 42, 42);
  
  /** Constructor for the ConnectFourGameWindow. 
   * initializes game window
   * @postcondition window is created with blank board
   */
  public ConnectFourGameWindow(ConnectFourMainWindow parent) {
    
    // Initializes variables
    this.parent = parent;
    buttons = new ArrayList<ArrayList<Button>>();
    game = new Model();
    
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
        URL url = ConnectFourMainWindow.class.getResource("/resources/button_white.png");
        add_button.setIcon(new ImageIcon(url));
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
    URL url_restart_select = ConnectFourMainWindow.class.getResource("/resources/game_button_restart_select.png");
	URL url_restart_deselect = ConnectFourMainWindow.class.getResource("/resources/game_button_restart_deselect.png");
	Button restart_button = new Button(new ImageIcon(url_restart_deselect));
	restart_button.setRolloverIcon(new ImageIcon(url_restart_select));
    restart_button.setPreferredSize(new Dimension(200, 40));
    restart_button.setName("GAME_RESTART");
    restart_button.addActionListener(this);
    restart_button.setBorderPainted(false);
    gbc.insets = new Insets(20, 0, 0, 0); // Padding on top
    gbc.gridy = 9;
    gbc.gridx = 1;
    gbc.gridwidth = 2;
    add(restart_button, gbc);
    
    URL url_quit_select = ConnectFourMainWindow.class.getResource("/resources/game_button_quit_select.png");
    URL url_quit_deselect = ConnectFourMainWindow.class.getResource("/resources/game_button_quit_deselect.png");
    Button quit_button = new Button(new ImageIcon(url_quit_deselect));
    quit_button.setRolloverIcon(new ImageIcon(url_quit_select));
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
  
  
  /* Draws the background image. */
  public void paintComponent(Graphics g) {
	URL url = ConnectFourMainWindow.class.getResource("/resources/ConnectFourGameWindow_background.png");
    Image background = new ImageIcon(url).getImage();
    g.drawImage(background, 0, -18, null);
  }
  
  
  /* The method that activates whenever a button is pressed. */
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button that was pressed. 
    Button button = (Button) event.getSource();
    String button_name = button.getName() != null ? button.getName() : "";
    
    // If the restart button was pressed...
    if (button_name.equals("GAME_RESTART")) {

      JLabel message = new JLabel("Do you want to restart?");
      message.setFont(message_font);
      message.setForeground(message_color);
      
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
        restart(); 
      }
    }
    
    
    // If the quit button was pressed...
    else if (button_name.equals("GAME_QUIT")) {
      
      JLabel message = new JLabel("Do you want to quit?");
      message.setFont(message_font);
      message.setForeground(message_color);
      
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
      if (check_quit== JOptionPane.YES_OPTION) {
        restart();               // Resets the game logic. 
        parent.viewMenuWindow(); // Returns to the menu window. 
      }
    }
    
    
    // If a tile on the grid was pressed...
    else {
      int x_coord = button.getXCoord();
      int y_coord;

      String current_player = game.getCurrentPlayer();
      GameInfo result = game.oneTurn(current_player, x_coord);
      
      if (result.getMostRecentlyPlacedTile() == null) {
    	  //do nothing - no tile placed
      }
      
      else if (result.getCurrentState() == GameState.PLAYING) {
    	y_coord = result.getMostRecentlyPlacedTile().getYCoord();
        drawNewPiece(current_player, x_coord, y_coord, false);
      }
      
      // If a player wins...
      else if (result.getCurrentState() == GameState.WIN) {
    	y_coord = result.getMostRecentlyPlacedTile().getYCoord();
        drawNewPiece(current_player, x_coord, y_coord, true);
        
        openDialogBox(game.getCurrentPlayer() + " wins!", "Winner");
      }
      
      // If the game ends in a tie...
      else if (result.getCurrentState() == GameState.TIE) {
    	y_coord = result.getMostRecentlyPlacedTile().getYCoord();
        drawNewPiece(current_player, x_coord, y_coord, true);
        
        openDialogBox("Tie!", "Tie");
      }
    }
  }
  
  
  /* Draws a piece that's just been placed */
  public void drawNewPiece(String current_player, int x_coord, int y_coord, boolean game_over) {

    Button button = buttons.get(x_coord).get(y_coord);
    URL url_icon;
    URL url_player_text;
    
    if (current_player.equals(Model.getPlayerOne())) {
      url_icon = ConnectFourMainWindow.class.getResource("/resources/button_red.png");
      url_player_text = ConnectFourMainWindow.class.getResource("/resources/game_window_player_2_turn_text.png");
    }
    else {
      url_icon = ConnectFourMainWindow.class.getResource("/resources/button_black.png");
      url_player_text = ConnectFourMainWindow.class.getResource("/resources/game_window_player_1_turn_text.png");
    }
    
    button.setIcon(new ImageIcon(url_icon));
    
    // Changes the player text if the game is not over. 
    if (!game_over) {
      player_text.setIcon(new ImageIcon(url_player_text));
    }
  }
  
  
  /** Displays message after game ends 
   * after closing dialog box, prompts for new game
   */
  public void openDialogBox(String message, String messageLabel) {
    
    // Creates the winner message.  
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
    restart();
    
    // If the no button was pressed...
    if (play_again == JOptionPane.NO_OPTION) {
      parent.viewMenuWindow(); // Returns to the menu window. 
    }
  }
  
  
  /** Resets the game logic and clears the boad. 
   * 
   * Used in openDialogBox() and actionPerformed();
   */
  public void restart() {
      
    // Resets the game logic and the player text. 
    game.newGame();
    URL url_player_text = ConnectFourMainWindow.class.getResource("/resources/game_window_player_1_turn_text.png");
    player_text.setIcon(new ImageIcon(url_player_text));
    
    // Clears all of the button icons. 
    for (int x = 0; x < Board.getColumns(); x++) {
      for (int y = 0; y < Board.getRows(); y++) {
        
        Button currentButton = buttons.get(x).get(y);
        URL url_icon = ConnectFourMainWindow.class.getResource("/resources/button_white.png");
        currentButton.setIcon(new ImageIcon(url_icon));
      }
    }
  }
  
  
  public Model getGame() {
    return game;
  }
  
  public Font getMessageFont() {
	  return message_font;
  }
  
  public Color getMessageColor() {
	  return message_color;
  }
}