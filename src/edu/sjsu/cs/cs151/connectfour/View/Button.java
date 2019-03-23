package edu.sjsu.cs.cs151.connectfour.View;

import javax.swing.JButton;

/** Button.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * UI button object.
 * 
 * @author Holly Lind
 * @since 3/18/2019
 * 
 */
public class Button extends JButton {

	
	/**
	 * Creates a Button
	 * @param x
	 * @param y
	 */
	public Button(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	
	
	private int x;
	private int y;
}
