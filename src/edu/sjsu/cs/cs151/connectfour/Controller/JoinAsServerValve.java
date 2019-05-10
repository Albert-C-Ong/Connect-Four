package edu.sjsu.cs.cs151.connectfour.Controller;

import java.util.concurrent.BlockingQueue;

import javax.swing.SwingWorker;

import edu.sjsu.cs.cs151.connectfour.View.View;
import edu.sjsu.cs.cs151.connectfour.network.Server;

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
public class JoinAsServerValve implements Valve {

	
	public JoinAsServerValve(View view, BlockingQueue<Message> queue) {
		this.view = view;
		this.queue = queue;
	}
	
	
	public ValveResponse execute(Message message) {
		if (message.getClass() != JoinAsServerMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		//initializes server
		view.setServer(new Server(queue, view));
		
		//replace menu panel with loading
		view.replacePanel(view.getMenuPanel(), view.getLoadingPanel());
		
		//swing worker stuff
		SwingWorker<Object, Object> server = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				view.getServer().startRunning();
				return null;
			}
		};

		server.execute();
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
	private BlockingQueue<Message> queue;
}
