
/** Button.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * UI button object.
 * 
 * @author Holly Lind
 * @since 24.04.2019
 * 
 */

package edu.sjsu.cs.cs151.connectfour.View;

import javax.swing.JButton;
import javax.swing.ImageIcon;


public class Button extends JButton {

  private int x;
  private int y;
  
  /**
   * Creates a Button
   * @param x
   * @param y
   */
  public Button(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public Button(ImageIcon icon) {
    super(icon);
  }
  
  /**
   * Access method for x
   * @return x
   */
  public int getXCoord() {
    return x;
  }
  
  
  /**
   * Access method for y
   * @return y
   */
  public int getYCoord() {
    return y;
  }
}
