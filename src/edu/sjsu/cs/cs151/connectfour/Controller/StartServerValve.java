package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.View.View;

/** StartServerValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used when joining an online game as the host
 * 
 * @author Krish Ghiya
 * @since 12.05.2019
 */
public class StartServerValve implements Valve {
	
	/**
	 * Ctor that assigns view to local view
	 * @param view - static view
	 */
	public StartServerValve(View view) {
		this.view = view;
	}
	
	/**
	 * Starts server on new thread.
	 * @return {@link ValveResponse} whether process was executed or not
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != StartServerMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		
		//Thread
		Thread thread = new Thread(Controller.SERVER);
		thread.start();
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
}
