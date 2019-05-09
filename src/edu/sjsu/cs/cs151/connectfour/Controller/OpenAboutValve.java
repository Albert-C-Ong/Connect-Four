package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.View.View;

/** OpenAboutValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that replaces the menu panel with the about panel
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class OpenAboutValve implements Valve {

	
	public OpenAboutValve(View view) {
		this.view = view;
	}
	
	
	public ValveResponse execute(Message message) {
		if (message.getClass() != OpenAboutMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		view.replacePanel(view.getMenuPanel(), view.getAboutPanel());
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
	
}
