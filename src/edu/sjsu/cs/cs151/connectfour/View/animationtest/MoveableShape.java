
/** MoveableShape.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * An interface for animated shapes.
 * 
 * @author Holly Lind and Albert Ong
 * @since 23.03.2019
 */

package edu.sjsu.cs.cs151.connectfour.View.animationtest;

import java.awt.Graphics2D;


public interface MoveableShape {

  void draw(Graphics2D g2);
  void move();
  
}
