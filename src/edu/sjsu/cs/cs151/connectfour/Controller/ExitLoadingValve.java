package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.View.View;

/** ExitLoadingValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used when clicking the exit button on the loading panel
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class ExitLoadingValve implements Valve {

	/**
	 * Ctor. Passes view to local variable
	 * @param View
	 */
	public ExitLoadingValve(View view) {
		this.view = view;
	}
	
	/**
	 * Closes connection if exit button is pressed in loading screen.
	 * @return {@link ValveResponse}
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != ExitLoadingMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		//close server - ??????????
		if (Controller.SERVER.getActiveStatus()) Controller.SERVER.closeConnection();
		else if (Controller.CLIENT.getActiveStatus()) Controller.CLIENT.closeConnection();
		
		//replace loading panel with menu
		view.replacePanel(view.getLoadingPanel(), view.getMenuPanel());
		
		return ValveResponse.EXECUTED;
	}

	
	private View view;
}
