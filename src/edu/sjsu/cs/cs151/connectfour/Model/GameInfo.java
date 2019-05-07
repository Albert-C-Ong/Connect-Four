package edu.sjsu.cs.cs151.connectfour.Model;

/** GameInfo.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class that has all the details of the current 
 * state of the game.
 * 
 * @author Holly Lind
 * @since 02.05.2019
 */
public class GameInfo {


	/**
	 * Constructor - initializes variables
	 * @param currentBoard
	 * @param currentPlayer
	 * @param currentState
	 * @param mostRecentlyPlacedTile
	 */
	public GameInfo(Board currentBoard, String currentPlayer, GameState currentState, Tile mostRecentlyPlacedTile) {
		this.currentBoard = currentBoard;
		this.currentPlayer = currentPlayer;
		this.currentState = currentState;
		this.mostRecentlyPlacedTile = mostRecentlyPlacedTile;
	}
	
	
	/**
	 * Access method for currentBoard
	 * @return currentBoard
	 */
	public Board getBoard() {
		return currentBoard;
	}
	
	
	/**
	 * Access method for currentPlayer
	 * @return currentPlayer
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	/**
	 * Access method for currentState
	 * @return currentState
	 */
	public GameState getCurrentState() {
		return currentState;
	}
	
	
	/**
	 * Access method for mostRecentlyPlacedTile
	 * @return mostRecentlyPlacedTile
	 */
	public Tile getMostRecentlyPlacedTile() {
		return mostRecentlyPlacedTile;
	}
	
	
	
	private Board currentBoard;
	private String currentPlayer;
	private GameState currentState;
	private Tile mostRecentlyPlacedTile;
	
}
