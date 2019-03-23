package edu.sjsu.cs.cs151.connectfour.animationtest;

import java.awt.*;
import javax.swing.*;

/** TileIcon.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class for a Tile's icon
 * 
 * @author Holly Lind
 * @since 3/18/2019
 */
public class TileIcon implements Icon {

	/**
	 * Creates a TileIcon
	 * @param shape
	 * @param width
	 * @param height
	 */
	public TileIcon(MoveableShape shape, int width, int height) {
		this.shape = shape;
		this.width = width;
		this.height = height;
	}
	
	
	/**
	 * Access method for icon's width
	 * @return width
	 */
	public int getIconWidth() {
		return width;
	}
	
	
	/**
	 * Access method for icon's height
	 * @return height
	 */
	public int getIconHeight() {
		return height;
	}
	
	
	/**
	 * Draws icon
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		shape.draw(g2);
	}
	
	
	private int width;
	private int height;
	private MoveableShape shape;
	
}
