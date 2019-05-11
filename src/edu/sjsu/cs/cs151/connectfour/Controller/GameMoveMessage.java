package edu.sjsu.cs.cs151.connectfour.Controller;

public class GameMoveMessage extends Message{
	
	public int x;
	public boolean restart = false;
	
	public GameMoveMessage(int x) {
		this.x = x;
	}
	
	public GameMoveMessage(boolean restart) {
		this.restart = restart;
	}
}
