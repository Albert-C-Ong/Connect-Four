package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;   
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.Model.*;
import edu.sjsu.cs.cs151.connectfour.View.Button;

/** GamePanel.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * UI for Connect Four game
 * (renamed from ConnectFourGameWindow)
 * 
 * @author Albert Ong, Holly Lind
 * @since 09.05.2019
 */
public class GamePanel extends JPanel implements ActionListener {

  
  public GamePanel(BlockingQueue<Message> queue, boolean localGame) {
    
    //if it's a local game, then start with player one
    //if it's a network game, player needs to be set in client/server
    player = Model.getPlayerOne();
    
    // Initializes variables
    this.queue = queue;
    this.localGame = localGame;
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
        URL url = View.class.getResource("/button_white.png");
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
    URL url_restart_select = View.class.getResource("/game_button_restart_select.png");
    URL url_restart_deselect = View.class.getResource("/game_button_restart_deselect.png");
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
      
    URL url_quit_select = View.class.getResource("/game_button_quit_select.png");
    URL url_quit_deselect = View.class.getResource("/game_button_quit_deselect.png");
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
  
  
  /**
   * Draws the background (gray Connect Four board)
   */
  public void paintComponent(Graphics g) {
    URL url = View.class.getResource("/GamePanel_background.png");
    Image background = new ImageIcon(url).getImage();
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
    Button button = buttons.get(newX).get(newY);
      URL url_icon;
      URL url_player_text;
    
    //choose images to use for button and player turn label
    if (newColor.equals(Board.getPlayerOneColor())) {
        url_icon = View.class.getResource("/button_red.png");
        url_player_text = View.class.getResource("/GamePanel_player_2_turn_text.png");
    }
    else {
        url_icon = View.class.getResource("/resources/button_black.png");
        url_player_text = View.class.getResource("/GamePanel_player_1_turn_text.png");
    }
    
    //set new button and player turn label
    //automatically swaps player here b/c assumes one computer for both players
    button.setIcon(new ImageIcon(url_icon));
    player_text.setIcon(new ImageIcon(url_player_text));
    
    //if it isn't an online game, then swap players
    if (!(Controller.SERVER.getActiveStatus() || Controller.CLIENT.getActiveStatus())) {
      if (player.equals(Model.getPlayerOne()))
        player = Model.getPlayerTwo();
      else
        player = Model.getPlayerOne();
    }
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
          queue.put(new QuitGameMessage(!(Controller.SERVER.getActiveStatus() || Controller.CLIENT.getActiveStatus())));
        }
        catch (InterruptedException exception) {
          exception.printStackTrace();
        }
    }
    
    //if decided to play again
    else {
        try {
          queue.put(new RestartGameMessage(player, !(Controller.SERVER.getActiveStatus() || Controller.CLIENT.getActiveStatus())));
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
          queue.put(new QuitGameMessage(!(Controller.SERVER.getActiveStatus() || Controller.CLIENT.getActiveStatus())));
        }
        catch (InterruptedException exception) {
          exception.printStackTrace();
        }
    }
    
    //player confirmed that they wanted to restart
    else {
        try {
          queue.put(new RestartGameMessage(player, !(Controller.SERVER.getActiveStatus() || Controller.CLIENT.getActiveStatus())));
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
      URL url_player_text = View.class.getResource("/GamePanel_player_1_turn_text.png");
      player_text.setIcon(new ImageIcon(url_player_text));
      
      //if it's a local game, reset to player 1's turn
      player = Controller.CLIENT.getActiveStatus() ? Model.getPlayerTwo() : Model.getPlayerOne();
      
    //gets rid of tiles on board
    for (int x = 0; x < Board.getColumns(); x++) {
      for (int y = 0; y < Board.getRows(); y++) {
            Button currentButton = buttons.get(x).get(y);
            URL url_icon = View.class.getResource("/button_white.png");
            currentButton.setIcon(new ImageIcon(url_icon));
      }
    }
  }
  
  
  public void setPlayer(String player) {
    this.player = player;
  }
  
  
  public Font getMessageFont() {
    return message_font;
  }
  
  
  public Color getMessageColor() {
    return message_color;
  }
  
  private ArrayList<ArrayList<Button>> buttons;
  URL url_initial_player_text = View.class.getResource("/GamePanel_player_1_turn_text.png");
  private JLabel player_text = new JLabel(new ImageIcon(url_initial_player_text));
  private Font message_font = new Font("Arial", Font.BOLD, 48);
  private Color message_color = new Color(255, 42, 42);
  private BlockingQueue<Message> queue;
  private String player;
  private boolean localGame;
}
