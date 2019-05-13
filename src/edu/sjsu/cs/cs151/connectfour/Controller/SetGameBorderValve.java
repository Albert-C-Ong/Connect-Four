package edu.sjsu.cs.cs151.connectfour.Controller;

import javax.swing.BorderFactory;

import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

/** SetGameBorderValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that sets the player border
 * 
 * @author Krish Ghiya
 * @since 12.05.2019
 */
public class SetGameBorderValve implements Valve{

	/**
	 * Sets border for online games
	 */
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != SetGameBorderMessage.class) return ValveResponse.MISS;
		
		ConnectFour.view.getGamePanel().setBorder(BorderFactory.createTitledBorder(
												 ((SetGameBorderMessage) message).getTitle()));
		
		return ValveResponse.EXECUTED;
	}

}
