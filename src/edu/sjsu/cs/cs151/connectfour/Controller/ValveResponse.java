package edu.sjsu.cs.cs151.connectfour.Controller;

/** ValveResponse.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A state that a Valve can have. MISS is when the Valve
 * and Message don't match, EXECUTED is when the Valve is 
 * done updating the View/Model, and FINISH is when all the
 * Valves are done.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public enum ValveResponse {
	MISS, EXECUTED, FINISH;
}
