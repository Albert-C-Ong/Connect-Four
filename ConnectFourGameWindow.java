
/** ConnectFourGameWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Game window for Connect Four. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 16.02.2019
 * 
 * TODO:
 * - Design and implement the icon of the window
 * - Use the setFilled() to fill each button after being press
 *     Buttons should not change color after being filled
 * - Implement game logic
 *     getColumns()
 *     getViableColumns()
 *     checkWin() (covers both win and tie logic)
 *     
 */

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;


public class ConnectFourGameWindow extends JFrame implements ActionListener {
  
  // A two dimensional ArrayList that represents the board of Connect Four. 
  private static ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
  
  // A string that represents the current player. Defaults to player 1. 
  private static String current_player = "Player 1";
  
  /** Class constructor for CenterSideGameWindow.
   * 
   * Responsible for initializing the game board. 
   */
  public ConnectFourGameWindow() {
    
    // Assigns the title and size of the window. 
    setTitle("Connect Four");
    setSize(new Dimension(1600, 900));
    setPreferredSize(getSize());
    
    GridBagLayout grid = new GridBagLayout();
    setLayout(grid);
    
    GridBagConstraints gbc = new GridBagConstraints();
    
    // gbc.fill = GridBagConstraints.HORIZONTAL;
    
    // Sets the gap between buttons in the layout. 
    gbc.insets = new Insets(4, 4, 4, 4);
    
    GridBagLayout layout = new GridBagLayout();
    this.setLayout(layout);
    
    for (Integer x = 1; x < 8; x++) {
      
      ArrayList<Tile> row = new ArrayList<Tile>();
      
      for (Integer y = 1; y < 7; y++) {
        
        gbc.gridx = x;
        gbc.gridy = y;
        
        Tile add_button = new Tile();
        
        // Sets the size of the button. 
        add_button.setPreferredSize(new Dimension(100, 100)); 
        
        // Assigns the action listener to the button. 
        add_button.addActionListener(this);
        
        row.add(add_button);
        this.add(add_button, gbc);
      }
      
      // Add each row to the board. 
      board.add(row);
    }
    setVisible(true); 
  } 
  
  
  /** A method that activates whenever a button is pressed. 
   * 
   */
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button object that was pressed. 
    Tile button = (Tile)event.getSource();

    String next_player;
    Color new_color = new Color(0, 0, 0);
    
    if (current_player == "Player 1") {
      new_color = Color.RED;
      next_player = "Player 2";
      
    }
    else {
      new_color = Color.BLACK;
      next_player = "Player 1";
    }
    
    current_player = next_player;
    button.setBackground(new_color);
    button.setFilled(true);
  }
}
