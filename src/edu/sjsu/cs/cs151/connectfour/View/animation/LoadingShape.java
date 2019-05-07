package edu.sjsu.cs.cs151.connectfour.View.animation;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/** LoadingShape.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A moving loading circle.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class LoadingShape implements MoveableShape {

	/**
	 * Constructor - initializes 8 rectangles in the circle
	 * also sets top rectangle to be dark initially
	 */
	public LoadingShape() {
		rectangles = new Shape[8];
		
		Rectangle2D.Double rectNorth 	= new Rectangle2D.Double(47.88, 10, 4.24, 30); //x, y, width, height
		Rectangle2D.Double rectEast 	= new Rectangle2D.Double(60, 47.88, 30, 4.24);
		Rectangle2D.Double rectSouth 	= new Rectangle2D.Double(47.88, 60, 4.24, 30);
		Rectangle2D.Double rectWest 	= new Rectangle2D.Double(10, 47.88, 30, 4.24);
		
		int[] xPointsNE = {57, 60, 83, 80};
		int[] yPointsNE = {40, 43, 20, 17};
		Polygon rectNorthEast = new Polygon(xPointsNE, yPointsNE, 4);
		
		int[] xPointsSE = {57, 60, 83, 80};
		int[] yPointsSE = {60, 57, 80, 83};
		Polygon rectSouthEast = new Polygon(xPointsSE, yPointsSE, 4);
		
		int[] xPointsSW = {40, 43, 20, 17};
		int[] yPointsSW = {57, 60, 83, 80};
		Polygon rectSouthWest = new Polygon(xPointsSW, yPointsSW, 4);
		
		int[] xPointsNW = {17, 20, 43, 40};
		int[] yPointsNW = {20, 17, 40, 43};
		Polygon rectNorthWest = new Polygon(xPointsNW, yPointsNW, 4);
		
		rectangles[0] = rectNorth;
		rectangles[1] = rectNorthEast;
		rectangles[2] = rectEast;
		rectangles[3] = rectSouthEast;
		rectangles[4] = rectSouth;
		rectangles[5] = rectSouthWest;
		rectangles[6] = rectWest;
		rectangles[7] = rectNorthWest;
		
		currentDark = 0;
	}
	
	
	/**
	 * Draws the rectangles at different colors (shades)
	 */
	public void draw(Graphics2D g2) {
		g2.setColor(lightRed);
		for (int i = 0; i < rectangles.length; i++)
			g2.fill(rectangles[i]);
		
		g2.setColor(darkRed);
		g2.fill(rectangles[currentDark]);
	}

	
	/**
	 * Changes which rectangles are dark/light
	 */
	public void move() {
		if (currentDark == rectangles.length - 1)
			currentDark = 0;
		else 
			currentDark++;
	}

	
	
	private Shape[] rectangles;
	private int currentDark;
	private Color darkRed = new Color(255, 42, 42);
	private Color lightRed = new Color(255, 161, 161);
}
