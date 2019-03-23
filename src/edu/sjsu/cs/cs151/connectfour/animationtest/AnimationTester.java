
/** AnimationTester.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class that tests the Tile falling animation.
 * 
 * @author Holly Lind and Albert Ong
 * @since 23.03.2019
 */

package edu.sjsu.cs.cs151.connectfour.animationtest;

import java.awt.*;
import javax.swing.*;


public class AnimationTester {

  // Default color values. 
  private static Color dark_red = new Color(250, 50, 50);
  private static Color light_red = new Color(200, 30, 30);
  private static Color dark_gray = new Color(90, 90, 90);
  private static Color light_gray = new Color(60, 60, 60);
  
  private static final int DELAY = 100; //delay between repaints
  
  
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    
    animateTile(frame, "red",   600, 0);
    animateTile(frame, "black", 500, 500);
    animateTile(frame, "red",   400, 1000);
    animateTile(frame, "black", 300, 1500);
    
    frame.setLayout(new FlowLayout());
    frame.setTitle("Animation Tester");
    frame.setSize(new Dimension(1600, 900));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);
  }
  
  
  //animates a Tile falling
  private static void animateTile(JFrame frame, String color, int endHeight, int initialDelay) {
    
    //makes the shape + an icon for it
    MoveableShape shape;
    if (color.equalsIgnoreCase("red"))
      shape = new TileShape(0, 0, 100, dark_red, light_red, endHeight);
    else
      shape = new TileShape(0, 0, 100, dark_gray, light_gray, endHeight); 
    TileIcon icon = new TileIcon(shape, 100, 1000);
    
    //labels the shape + adds to frame
    final JLabel label = new JLabel(icon);
    frame.add(label);
    
    //sets timer - shape starts moving after initial delay
    Timer t = new Timer(DELAY, event ->
    {
      shape.move();
      label.repaint();
    });
    t.setInitialDelay(initialDelay);
    t.start();
  } 
}
