package edu.sjsu.cs.cs151.connectfour.Controller;

public class SetGameBorderMessage extends Message{
	
	private String title;
	
	public SetGameBorderMessage(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
