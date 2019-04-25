
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
  private Model game;
  
  // Initializes the player turn text. 
  private JLabel player_text = new JLabel(new ImageIcon(cwd + "\\images\\game_window_player_1_turn_text.png")); 
  
  // Styles the message font and color for JDialogBox object. 
  private Font message_font = new Font("Arial", Font.BOLD, 48);
  private Color message_color = new Color(255, 42, 42);
  
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
  
  
  /* Draws the background image. */
  public void paintComponent(Graphics g) {
    Image background = new ImageIcon(cwd + "\\images\\ConnectFourGameWindow_background.png").getImage();
    g.drawImage(background, 0, -18, null);
  }
  
  
  /* The method that activates whenever a button is pressed. */
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button that was pressed. 
    Button button = (Button)event.getSource();
    String button_name = button.getName();
    
    // If the restart button was pressed...
    if (button_name == "GAME_RESTART") {
      
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
    else if (button_name == "GAME_QUIT") {
      
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
      x_coord = button.getXCoord();
      y_coord = button.getYCoord();

      String current_player = game.getCurrentPlayer();
      String result = game.oneTurn(current_player, x_coord);
      
      if (result.startsWith("piece placed")) {
        y_coord = Integer.parseInt(result.substring(19));
        drawNewPiece(current_player, x_coord, y_coord, false);
      }
      
      // If a player wins...
      else if (result.startsWith("win")) {
        y_coord = Integer.parseInt(result.substring(13));
        drawNewPiece(current_player, x_coord, y_coord, true);
        
        openDialogBox(game.getCurrentPlayer() + " wins!", "Winner");
      }
      
      // If the game ends in a tie...
      else if (result.startsWith("tie")) {
        y_coord = Integer.parseInt(result.substring(10, 11));
        drawNewPiece(current_player, x_coord, y_coord, true);
        
        openDialogBox("Tie!", "Tie");
      }
    }
  }
  
  
  /* Draws a piece that's just been placed */
  private void drawNewPiece(String current_player, int x_coord, int y_coord, boolean game_over) {

    String icon_path;
    String player_text_path;
    Button button = buttons.get(x_coord).get(y_coord);
    
    if (current_player.equals(Model.getPlayerOne())) {
      icon_path = "\\images\\button_red.png";
      player_text_path = "\\images\\game_window_player_2_turn_text.png";
    }
    else {
      icon_path = "\\images\\button_black.png";
      player_text_path = "\\images\\game_window_player_1_turn_text.png";
    }
    
    button.setIcon(new ImageIcon(cwd + icon_path));
    
    // Changes the player text if the game is not over. 
    if (!game_over) {
      player_text.setIcon(new ImageIcon(cwd + player_text_path));
    }
  }
  
  
  /** Displays message after game ends 
   * after closing dialog box, prompts for new game
   */
  private void openDialogBox(String message, String messageLabel) {
    
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
  private void restart() {
    
    // Resets the game logic and the player text. 
    game.newGame();
    player_text.setIcon(new ImageIcon(cwd + "\\images\\game_window_player_1_turn_text.png"));
    
    // Clears all of the button icons. 
    for (int x = 0; x < Board.getColumns(); x++) {
      for (int y = 0; y < Board.getRows(); y++) {
        
        Button currentButton = buttons.get(x).get(y);
        currentButton.setIcon(new ImageIcon(cwd + "\\images\\button_white.png"));
      }
    }
  }
  
  
  public Model getGame() {
    return game;
  }
  public int getXCoord() {
    return x_coord;
  }
  public int getYCoord() {
    return y_coord;
  }  
}
