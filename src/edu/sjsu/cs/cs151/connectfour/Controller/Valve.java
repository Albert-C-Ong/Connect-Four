package edu.sjsu.cs.cs151.connectfour.Controller;

/** Valve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class that represents a Valve. Valves update the Model
 * and View depending on their corresponding Message.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public interface Valve {

	/**
	 * Updates the Model and View based on the Message
	 * If the message doesn't match the valve, then do nothing
	 * @param message - the message that was taken
	 * @return ValveResponse.EXECUTED for the right message; else ValveResponse.MISS 
	 */
	public ValveResponse execute(Message message);
	
}
