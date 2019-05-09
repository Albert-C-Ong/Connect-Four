package edu.sjsu.cs.cs151.connectfour.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.connectfour.View.View;
import edu.sjsu.cs.cs151.connectfour.network.Client;

/** StartOnlineGameValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that looks for an online game
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class StartOnlineGameValve implements Valve {
	
	
	public StartOnlineGameValve(View view, BlockingQueue<Message> queue) {
		this.view = view;
		this.queue = queue;
	}

	
	public ValveResponse execute(Message message) {
		if (message.getClass() != StartOnlineGameMessage.class)
			return ValveResponse.MISS;

		//actions in model - none
		
		//actions in view
		//initialize client
		view.setClient(new Client(queue));
		
		//try to find server
		String address = view.getClient().findServer();
		
		//if server is there, prompt to join
		if (!address.startsWith("No")) {

			view.getClient().setServerIP(address);

			String hostName = "";
			try {
				hostName = InetAddress.getByName(address).getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			view.joinOnlineDialogBox(hostName);
		}
		
		//if no server, prompt to host
		else {
			view.hostOnlineDialogBox();
		}
		
		return ValveResponse.EXECUTED;
	}
	
	
	private View view;
	private BlockingQueue<Message> queue;
}
