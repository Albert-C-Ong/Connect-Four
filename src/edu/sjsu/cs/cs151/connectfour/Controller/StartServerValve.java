package edu.sjsu.cs.cs151.connectfour.Controller;

import java.util.concurrent.BlockingQueue;

import javax.swing.SwingWorker;

import edu.sjsu.cs.cs151.connectfour.Model.Server;
import edu.sjsu.cs.cs151.connectfour.View.View;

/** JoinAsServerValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used when joining an online game as the host
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class StartServerValve implements Valve {

	public static Thread thread;
	public StartServerValve(View view) {
		this.view = view;
	}
	
	
	public ValveResponse execute(Message message) {
		if (message.getClass() != StartServerMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		
		//Thread
		thread = new Thread(Controller.SERVER);
		thread.start();
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
}
