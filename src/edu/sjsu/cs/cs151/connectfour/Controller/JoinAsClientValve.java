package edu.sjsu.cs.cs151.connectfour.Controller;

import javax.swing.SwingWorker;

import edu.sjsu.cs.cs151.connectfour.View.View;

/** JoinAsClientValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used when joining an online game as the client
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class JoinAsClientValve implements Valve {
	
	
	public JoinAsClientValve(View view) {
		this.view = view;
	}

	
	public ValveResponse execute(Message message) {
		if (message.getClass() != JoinAsClientMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		//replace menu panel w/ client
		view.replacePanel(view.getMenuPanel(), view.getClient());
		
		//swing worker stuff
		SwingWorker<Object, Object> server = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				view.getClient().startRunning();
				return null;
			}
		};

		server.execute();
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
	
}
