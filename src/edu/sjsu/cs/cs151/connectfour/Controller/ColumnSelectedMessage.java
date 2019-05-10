package edu.sjsu.cs.cs151.connectfour.Controller;

/** ColumnSelectedMessage.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Message for when a column is selected on the Connect Four board
 * See ColumnSelectedValve for controller functionality
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class ColumnSelectedMessage extends Message {

	
	public ColumnSelectedMessage(int columnSelected, String player, boolean localGame) {
		this.columnSelected = columnSelected;
		this.player = player;
		this.localGame = localGame;
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
	
	
	public boolean getLocalGame() {
		return localGame;
	}
	
	
	
	private int columnSelected;
	private String player;
	private boolean localGame;
}
