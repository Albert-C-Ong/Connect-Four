
/** Tile.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class the represents an individual tile on the
 * Connect Four board. A subclass of JButton. 
 * 
 * @author Albert Ong
 * @since 14.02.2019
 */

import javax.swing.*;

public class Tile extends JButton {       
  
  private static int x_coord;
  private static int y_coord;
  private static String color;
  private static boolean filled;
  
  // The default constructor for the Tile class. 
  public Tile() {
    
    x_coord = 0;
    y_coord = 0;
    filled = false;
  }
  
  // Getter methods.
  public static int getXCoord() {
    return x_coord;
  }
  public static int getYCoord() {
    return y_coord;
  }
  public static String getColor() {
    return color;
  }
  public static boolean getFilled() {
    return filled;
  }
  
  // Setter methods.
  public static void setXCoord(int x) {
    x_coord = x;
  }
  public static void setYCoord(int y) {
    y_coord = y;
  }
  public static void setColor(String color_input) {
    color = color_input;
  }
  public static void setFilled(boolean filled_input) {
    filled = filled_input;
  }
}
