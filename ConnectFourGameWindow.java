
/** ConnectFourGameWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Game window for Connect Four. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 09.03.2019
 * 
 * TODO:
 *   Implement background image 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class ConnectFourGameWindow extends JFrame implements ActionListener {
  
  // A two dimensional ArrayList that represents the board of Connect Four. 
  private static ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
  
  // A string that represents the current player. Defaults to player 1. 
  private static String current_player = "Player 1";
  
  //Retrieves the current working directory. 
  private String cwd = System.getProperty("user.dir");
  
  /** Class constructor for CenterSideGameWindow.
   * 
   */
  public ConnectFourGameWindow() {
   
    // Assigns the title, size, and background color of the window. 
    setTitle("Connect Four");
    setSize(new Dimension(1600, 900));
    getContentPane().setBackground(Color.WHITE);
    
    // Retrieves and sets the window icon. 
    ImageIcon icon = new ImageIcon(cwd + "\\images\\window_icon.png");
    setIconImage(icon.getImage());
    
    // Need to implement background later. 
//    JLabel background = new JLabel();
//    background.setIcon(new ImageIcon(cwd + "\\images\\ConnectFourGameWindow_background.png"));
//    setContentPane(background);
  
    // Adds the grid layout. 
    GridBagLayout grid = new GridBagLayout();
    setLayout(grid);
    
    GridBagConstraints gbc = new GridBagConstraints();
    
    // Sets the gap between buttons in the layout. 
    gbc.insets = new Insets(4, 4, 4, 4);
    
    
    // Initializing the grid of buttons. 
    for (int y = 0; y < 6; y++) {
      
      ArrayList<Tile> row = new ArrayList<Tile>();
      
      for (int x = 0; x < 7; x++) {
        
        gbc.gridx = x;
        gbc.gridy = y;
        
        Tile add_button = new Tile(x, y);
        
        // Sets the size of the button. 
        add_button.setPreferredSize(new Dimension(100, 100)); 
        
        add_button.setIcon(new ImageIcon());
        add_button.setBorderPainted(false);
        add_button.setRolloverEnabled(false);
        
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
      String icon_path;
      String next_player;
      
      
      // The new values if the current player is Player 1. 
      if (current_player == "Player 1") {
        tile_color = "RED";
        icon_path = "\\images\\button_red.png";
        next_player = "Player 2";
      }
      
      // The new values if the current player is Player 2. 
      else {
        tile_color = "BLACK";
        icon_path = "\\images\\button_black.png";
        next_player = "Player 1";
      }
      
      // Changes the color and filled properties to the tile. 
      current_tile.setColor(tile_color);
      current_tile.setIcon(new ImageIcon(cwd + icon_path));
      current_tile.setFilled(true);
      
      // Changes the current player to the next player. 
      current_player = next_player;
    }
    
    // Checks which player won the game. 
    String winner = checkWin();
    
    if (winner == "Player 1" || winner == "Player 2") {
      
      // Displays who won the game.
      JOptionPane.showMessageDialog(null, winner + " wins!");
      
      // Prompts the user if they want to play again. 
      int play_again = JOptionPane.showConfirmDialog(null, "Play again?", null, JOptionPane.YES_NO_OPTION);
      
      // Restarts the game if 'yes' was selected. 
      if (play_again == JOptionPane.YES_OPTION) {
        restart();
      }
      // Closes the window if 'no' was selected. 
      else {
        dispose();
      }
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
    
    // Assembling the row wins. 
    ArrayList<ArrayList<Tile>> row_wins = new ArrayList<ArrayList<Tile>>();
    
    for (ArrayList<Tile> row : board) {
      for (int index = 0; index < 4; index++) {
        
        List<Tile> sub_list = row.subList(index, index + 4);
        ArrayList<Tile> row_win = new ArrayList(sub_list);
        
        row_wins.add(new ArrayList(row_win));
      }
    }
    
    // Assembling the column wins. 
    ArrayList<ArrayList<Tile>> column_wins = new ArrayList<ArrayList<Tile>>();
    
    for (ArrayList<Tile> column : getColumns()) {
      for (int index = 0; index < 3; index++) {
        
        List<Tile> sub_list = column.subList(index, index + 4);
        ArrayList<Tile> column_win = new ArrayList(sub_list);
        
        column_wins.add(column_win);
      }
    }
    
   
    // Assembling the diagonal wins.
    
    /* The starting coordinates for diagonals with a positive slop. 
     * - - - - - - -
     * - - - - - - -
     * - - - - - - -
     * X - - - - - -
     * X - - - - - -
     * X X X X - - -
     */
    int[][] pos_start_coords = {{0, 3}, {0, 4}, {0, 5}, 
                                {1, 5}, {2, 5}, {3, 5}};

    /* The starting coordinates for diagonals with a negative slop. 
     * X X X X - - -
     * X - - - - - -
     * X - - - - - -
     * - - - - - - -
     * - - - - - - -
     * - - - - - - -
     */
    int[][] neg_start_coords = {{0, 2}, {0, 1}, {0, 0}, 
                                {1, 0}, {2, 0}, {3, 0}, };
    
    ArrayList<ArrayList<Tile>> diagonals = getDiagonals(pos_start_coords, true);
    diagonals.addAll(getDiagonals(neg_start_coords, false));
    
    ArrayList<ArrayList<Tile>> diag_wins = new ArrayList<ArrayList<Tile>>();
    
    for (ArrayList<Tile> diagonal : diagonals) {
      for (int index = 0; index < diagonal.size() - 3; index++) {
        
        List<Tile> diag_win = diagonal.subList(index, index + 4);
        diag_wins.add(new ArrayList(diag_win));
      }
    }
    
    // An ArrayList of every possible win condition. 
    ArrayList<ArrayList<Tile>> win_conditions = new ArrayList<ArrayList<Tile>>();
    win_conditions.addAll(row_wins);
    win_conditions.addAll(column_wins);
    win_conditions.addAll(diag_wins);
    
    // There is no winner by default. 
    String winner = null;
    
    // Iterates through evey possible win condition. 
    for (ArrayList<Tile> condition : win_conditions) {
      
      HashSet<String> check_win = new HashSet<String>();
      
      for (Tile tile : condition) {
        check_win.add(tile.getColor());
      }
      
      // If all the items in the win condition have the same color...
      if (check_win.size() == 1) {
        
        // Retrieves the single color from the set. 
        String color = check_win.iterator().next();
        
        // Player 1 wins if all four tiles are red.
        if (color == "RED") {
          winner = "Player 1";
        }
        
        // Player 2 wins if all four tiles are black. 
        else if (color == "BLACK") {
          winner = "Player 2";
        }
      }
    }
    
    // Returns the winner.
    // This value with either be null, 'Player 1', or 'Player 2'.
    return winner;
  }
  
  
  /** Helper function for checkWin()
   * 
   * Assembles the diagonals given an array of starting coordinates
   * and the direction of the diagonals.
   */
  private ArrayList<ArrayList<Tile>> getDiagonals(int[][] starting_coords, 
                                                  boolean is_pos_slope) {
    
    ArrayList<ArrayList<Tile>> diagonals = new ArrayList<ArrayList<Tile>>();
    
    for (int[] coord : starting_coords) {
      
      int x = coord[0];
      int y = coord[1];
      
      ArrayList<Tile> diagonal = new ArrayList<Tile>();
      
      while (x < 7 && 0 <= y && y < 6) {
        
        Tile tile = board.get(y).get(x);
        diagonal.add(tile);
        
        x += 1;
        
        if (is_pos_slope) {
          y -= 1;
        } 
        else {
          y += 1;
        }
      }
      diagonals.add(diagonal);
    }
    
    return diagonals;
  }
  
  
  /** Restarts the ConnectFourGameWindow
   * 
   * Activated when the player opts to play again. 
   */
  public void restart() {
    
    // Resets the current player to Player 1.
    current_player = "Player 1";
    
    // Iterates throught every tile on the board. 
    for (ArrayList<Tile> row : board) {
      for (Tile tile : row) {
        
        // Resets the values of the tile to a those of a blank tile. 
        tile.setIcon(new ImageIcon());
        tile.setColor("NONE");
        tile.setFilled(false);
      }
    }
  }
  
  
}
