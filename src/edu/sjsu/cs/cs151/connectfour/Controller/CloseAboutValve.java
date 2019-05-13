package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.View.View;

/** CloseAboutValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that replaces the about panel with the menu panel
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class CloseAboutValve implements Valve {

	
	/**
	 * Ctor - initializes view
	 * @param view
	 */
	public CloseAboutValve(View view) {
		this.view = view;
	}
	
	
	/**
	 * Replaces about panel with menu panel
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != CloseAboutMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		view.replacePanel(view.getAboutPanel(), view.getMenuPanel());
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
}
