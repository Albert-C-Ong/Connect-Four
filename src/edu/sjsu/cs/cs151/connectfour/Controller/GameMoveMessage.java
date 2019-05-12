package edu.sjsu.cs.cs151.connectfour.Controller;

public class GameMoveMessage extends Message{
	
	private int x;
	private boolean restart = false;
	
	public GameMoveMessage(int x) {
		this.x = x;
	}
	
	public GameMoveMessage(boolean restart) {
		this.restart = restart;
	}
	
	public boolean getRestart() {
		return restart;
	}
	
	public int getXCoord() {
		return x;
	}
}
