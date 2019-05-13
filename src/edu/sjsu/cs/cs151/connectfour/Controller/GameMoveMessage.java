package edu.sjsu.cs.cs151.connectfour.Controller;

/** GameMoveMessage.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Message for making a move in the game
 * 
 * @author Krish Ghiya
 * @since 12.05.2019
 */
public class GameMoveMessage extends Message{
	
	/**
	 * Ctor - initializes x
	 * @param x - x coordinate on board
	 */
	public GameMoveMessage(int x) {
		this.x = x;
	}
	
	/**
	 * Ctor - initializes restart
	 * @param restart - true if restarting game
	 */
	public GameMoveMessage(boolean restart) {
		this.restart = restart;
	}
	
	/**
	 * Access method for restart
	 * @return restart
	 */
	public boolean getRestart() {
		return restart;
	}
	
	/**
	 * Access method for x
	 * @return x
	 */
	public int getXCoord() {
		return x;
	}
	
	private int x;
	private boolean restart = false;
}
