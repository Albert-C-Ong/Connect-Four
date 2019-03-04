
/** ConnectFourGameWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Game window for Connect Four. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 04.03.2019
 * 
 * TODO:
 *   Design and implement the icon of the window
 *   Implement background image 
 *   Implement game logic
 *     checkWin() (covers both win and tie logic)
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ConnectFourGameWindow extends JFrame implements ActionListener {
  
  // A two dimensional ArrayList that represents the board of Connect Four. 
  private static ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
  
  // A string that represents the current player. Defaults to player 1. 
  private static String current_player = "Player 1";
  
  /** Class constructor for CenterSideGameWindow.
   * 
   */
  public ConnectFourGameWindow() {
   
    // Assigns the title and size of the window. 
    setTitle("Connect Four");
    setSize(new Dimension(1600, 900));
    
//    String cwd = System.getProperty("user.dir");
//    String background_path = cwd + "\\images\\background.png";
//    
//    JLabel background = new JLabel(new ImageIcon(background_path));
//    add(background);
    
    // Adds the grid layout. 
    GridBagLayout grid = new GridBagLayout();
    setLayout(grid);
    
    GridBagConstraints gbc = new GridBagConstraints();
    
    // Sets the gap between buttons in the layout. 
    gbc.insets = new Insets(4, 4, 4, 4);
    
    
    for (int y = 0; y < 6; y++) {
      
      ArrayList<Tile> row = new ArrayList<Tile>();
      
      for (int x = 0; x < 7; x++) {
        
        gbc.gridx = x;
        gbc.gridy = y;
        
        Tile add_button = new Tile();
        
        add_button.setXCoord(x);
        add_button.setYCoord(y);
        
        // Sets the size of the button. 
        add_button.setPreferredSize(new Dimension(100, 100)); 
        
        // Assigns the action listener to the button. 
        add_button.addActionListener(this);
        
        row.add(add_button);
        add(add_button, gbc);
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
    
    // Retrieves the x coordinate of the button as well as the 
    // list of viable column coordinates. 
    int x_coord = button.getXCoord();
    ArrayList<Integer> viable_columns = getViableColumns();
    
    // Checks if the current column is viable. 
    boolean is_viable_column = viable_columns.contains(x_coord);
    
    // If a viable column was selected...
    if (is_viable_column) {
    
      // Retrieves the column that was selected. 
      ArrayList<ArrayList<Tile>> columns = getColumns();
      ArrayList<Tile> current_column = columns.get(button.getXCoord());
      
      // Retrieves the last element, or the botton, of the column. 
      Tile current_tile = current_column.get(5);
      
      // Iterates starting from the bottom of the column. 
      for (int index = 5; index >= 0; index--) {
      
        // Checks if the current Tile is not filled. 
        Tile test_tile = current_column.get(index);
        boolean not_filled = !test_tile.getFilled();
        
        // The current tile will be the first tile that is not filled. 
        if (not_filled) {
          current_tile = test_tile;
          break;
        }
      }
      // By the end of this loop, current_tile represents the lowest, 
      // empty tile in the column -or in other words the tile in the column
      // that will be filled. 
      
      // Variables that will be changed in the current tile. 
      String tile_color; 
      Color background_color = new Color(0, 0, 0);
      String next_player;
      
      // The new values if the current player is Player 1. 
      if (current_player == "Player 1") {
        tile_color = "RED";
        background_color = Color.RED;
        next_player = "Player 2";
      }
      
      // The new values if the current player is Player 2. 
      else {
        tile_color = "BLACK";
        background_color = Color.BLACK;
        next_player = "Player 1";
      }
      
      // Changes the color and filled properties to the tile. 
      current_tile.setColor(tile_color);
      current_tile.setBackground(background_color);
      current_tile.setFilled(true);
      
      // Changes the current player to the next player. 
      current_player = next_player;
      
    }
  }
  
  
  /* Returns all of the columns of the board. 
   */
  public ArrayList<ArrayList<Tile>> getColumns() {
    
    // A list of list of tiles that represent the columns on the board. 
    ArrayList<ArrayList<Tile>> columns = new ArrayList<ArrayList<Tile>>();
    
    // Iterates though each column index in the board. 
    for (int index = 0; index < 7; index++) {
      
      ArrayList<Tile> column = new ArrayList<Tile>();
      
      // Iterates through each row on the board and adds the element
      // at a given column index. 
      for (ArrayList<Tile> row : board) {
        column.add(row.get(index));
      }
      
      columns.add(column);
    }
    
    return columns;
  }
  
  
  /* Returns a list of every viable column where a piece can be played. 
   * This method determines where the next player can go next. 
   */
  public ArrayList<Integer> getViableColumns() {
    
    // A list of indices that represent the viable columns on the board. 
    ArrayList<Integer> viable_columns = new ArrayList<Integer>();
    
    // Retrieves the first row in the board. 
    ArrayList<Tile> first_row = board.get(0);
    
    // Iterates through the first row. 
    for (Tile tile : first_row) {
      
      boolean not_filled = !tile.getFilled();
      
      // The column is viable if first element in the column is empty. 
      if (not_filled) {
        viable_columns.add(new Integer(tile.getXCoord()));
      }
    }
    
    return viable_columns;
  }
  
  
  /** Check if either Player 1 or Player 2 has won
   * and returns the winner. 
   */
  public String checkWin() {
    
    String winner = null;
    
    return winner;
  }
}
