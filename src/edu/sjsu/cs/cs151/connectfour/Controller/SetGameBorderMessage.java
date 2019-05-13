package edu.sjsu.cs.cs151.connectfour.Controller;

/** SetGameBorderMessage.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Message for setting game border in online play
 * See SetGameBorderValve for controller functionality
 * 
 * @author Krish Ghiya
 * @since 12.05.2019
 */
public class SetGameBorderMessage extends Message{
	
	/**
	 * Ctor - initializes title
	 * @param title
	 */
	public SetGameBorderMessage(String title) {
		this.title = title;
	}
	
	/**
	 * Access method for title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	private String title;
}
