package edu.sjsu.cs.cs151.connectfour.View.animation;

import java.awt.Graphics2D;

/** MoveableShape.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * An interface for animated shapes.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public interface MoveableShape {

  void draw(Graphics2D g2);
  void move();
  
}
