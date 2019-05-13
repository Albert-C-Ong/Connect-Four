package edu.sjsu.cs.cs151.connectfour.Controller;

/** CloseFrameValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that stops the controller's main loop
 * Used when the frame is closed (by clicking exit on the menu panel)
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class CloseFrameValve implements Valve {
	
	/**
	 * Returns FINISH, which stops the controller loop
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != CloseFrameMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		//actions in view - none
		
		return ValveResponse.FINISH;
	}
	
}
