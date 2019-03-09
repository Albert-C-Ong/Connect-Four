
/** Tile.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class the represents an individual tile on the
 * Connect Four board. A subclass of JButton. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 08.03.2019
 */

import javax.swing.*;

public class Tile extends JButton {       
  
  private int x_coord;
  private int y_coord;
  private String color = "NONE";
  private boolean filled = false;
  
  // The default constructor for the Tile class. 
  public Tile(int x, int y) {
    x_coord = x;
    y_coord = y;
  }
  
  public Tile(int x, int y, String input) {
    super(input);
    x_coord = x;
    y_coord = y;
    
  }
  
  // Getter methods.
  public int getXCoord() { 
    return x_coord; 
  }
  public int getYCoord() { 
    return y_coord; 
  }
  public String getColor() {
    return color; 
  }
  public boolean getFilled() { 
    return filled; 
  }
  
  // Setter methods.
  public void setColor(String color_input) {
    color = color_input;
  }
  public void setFilled(boolean filled_input) {
    filled = filled_input;
  }
  
  
  public String toString() {
    return x_coord + " " + y_coord;
  }
}
