package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.View.View;

/** StartLocalGameValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that starts a local Connect Four game
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class StartLocalGameValve implements Valve {
	
	
	public StartLocalGameValve(View view) {
		this.view = view;
	}

	
	public ValveResponse execute(Message message) {
		if (message.getClass() != StartLocalGameMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		view.replacePanel(view.getMenuPanel(), view.getGamePanel());
		
		return ValveResponse.EXECUTED;
	}

	
	private View view;
}
