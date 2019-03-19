package edu.sjsu.cs.cs151.connectfour.animationtest;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/** TileShape.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class with the shape of a Tile drawn using Graphics2D
 * 
 * @author Holly Lind
 * @since 3/18/2019
 */
public class TileShape implements MoveableShape {

	
	/**
	 * Makes a moving Tile
	 * @param x
	 * @param y
	 * @param diameter
	 * @param color
	 * @param max_distance - max distance it will travel before stopping
	 * @postcondition velocity and acceleration are initialized
	 */
	public TileShape(int x, int y, int diameter, Color outer_color, Color inner_color, int max_distance) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.outer_color = outer_color;
		this.inner_color = inner_color;
		this.max_distance = max_distance;
	
		velocity = 100;
		acceleration = 50;
	}
	
	
	/**
	 * Moves the Tile, assuming it hasn't gone it's max distance yet
	 * changed velocity according to acceleration
	 */
	public void move() {
		if (y + velocity >= max_distance)
			y = max_distance;
		else {
			y += velocity;
			velocity += acceleration;
		}
	}
	
	
	/**
	 * Draws the Tile
	 */
	public void draw(Graphics2D g2) {
		Ellipse2D.Double outerCircle = new Ellipse2D.Double(x, y, diameter, diameter);
		Ellipse2D.Double innerCircle = new Ellipse2D.Double(x + 15, y + 15, diameter - 30, diameter - 30);
		
		g2.setColor(outer_color);
		g2.fill(outerCircle);
		g2.draw(outerCircle);
		
		g2.setColor(inner_color);
		g2.fill(innerCircle);
		g2.draw(innerCircle);
	}
	
	
	private int x;
	private int y;
	private int diameter;
	private Color outer_color;
	private Color inner_color;
	private int max_distance;
	private int velocity;
	private int acceleration;
}
