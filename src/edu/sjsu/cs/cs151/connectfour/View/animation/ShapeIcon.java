package edu.sjsu.cs.cs151.connectfour.View.animation;

import java.awt.*;
import javax.swing.Icon;

/** ShapeIcon.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * An icon that houses a MoveableShape
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class ShapeIcon implements Icon {

	/**
	 * Constructor - initializes variables
	 * @param shape - shape inside the icon
	 * @param width - width of icon
	 * @param height - height of icon
	 */
	public ShapeIcon(MoveableShape shape, int width, int height) {
		this.shape = shape;
		this.width = width;
		this.height = height;
	}
	
	
	/**
	 * Access method for height
	 * @return height
	 */
	public int getIconHeight() {
		return height;
	}

	
	/** 
	 * Access method for width
	 * @return width
	 */
	public int getIconWidth() {
		return width;
	}

	
	/**
	 * Paints the shape in the icon
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		shape.draw(g2);
	}

	
	
	private int width;
	private int height;
	private MoveableShape shape;
}
