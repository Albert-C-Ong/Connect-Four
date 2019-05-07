package edu.sjsu.cs.cs151.connectfour.Controller;

/** ColumnSelectedMessage.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Message for when a column is selected on the Connect
 * Four board
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class ColumnSelectedMessage extends Message {

	/**
	 * Constructor - initializes the message
	 * @param columnSelected - x coordinate of the button pushed
	 * @param player - the player that pushed the button
	 */
	public ColumnSelectedMessage(int columnSelected, String player) {
		this.columnSelected = columnSelected;
		this.player = player;
	}


	/**
	 * Access method for columnSelected
	 * @return columnSelected
	 */
	public int getColumnSelected() {
		return columnSelected;
	}
	
	
	/**
	 * Access method for player
	 * @return player
	 */
	public String getPlayer() {
		return player;
	}
	
	
	
	private int columnSelected;
	private String player;
}
