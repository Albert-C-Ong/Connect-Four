package edu.sjsu.cs.cs151.connectfour.Controller;

/** RestartGameMessage.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Message for when the game is restarted
 * See RestartGameMessage for controller functionality
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class RestartGameMessage extends Message {

	public RestartGameMessage(String player, boolean localGame, boolean endGame) {
		this.player = player;
		this.localGame = localGame;
		this.endGame = endGame;
	}
	
	
	public String getPlayer() {
		return player;
	}
	
	
	public boolean getLocalGame() {
		return localGame;
	}
	
	public boolean getEndGame() {
		return endGame;
	}
	
	private String player;
	private boolean localGame;
	private boolean endGame;
}
