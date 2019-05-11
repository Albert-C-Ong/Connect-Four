package edu.sjsu.cs.cs151.connectfour.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.connectfour.Model.*;
import edu.sjsu.cs.cs151.connectfour.View.View;

/** Controller.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Combines model and view 
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class Controller {

	private BlockingQueue<Message> messageQueue;
	private View view;
	private Model model;
	private List<Valve> valves = new LinkedList<>();
	public static final Server SERVER = new Server();
	public static final Client CLIENT = new Client();
	
	/**
	 * Constructor - initializes view, model, messageQueue, and valves
	 * @param view - the game's UI
	 * @param model - the model of the game
	 * @param queue - a queue of messages from the view of actions the user has taken
	 * @postcondition - valves has an instance of every type of valve
	 */
	public Controller (View view, Model model, BlockingQueue<Message> queue) {
		this.view = view;
		this.model = model;
		messageQueue = queue;
		
		valves.add(new StartLocalGameValve(view));
		valves.add(new OpenAboutValve(view));
		valves.add(new CloseAboutValve(view));
		valves.add(new CloseFrameValve());
		valves.add(new ExitLoadingValve(view));
		valves.add(new ColumnSelectedValve(model, view));
		valves.add(new QuitGameValve(model, view));
		valves.add(new RestartGameValve(model, view));
		valves.add(new JoinAsClientValve(view));
		valves.add(new StartServerValve(view));
		valves.add(new SetGameBorderValve());
		valves.add(new PlayerLeftValve());
		valves.add(new PlayerRestartValve());
	}
	
	
	/**
	 * The main loop of the controller
	 * takes messages from the queue, then matches them to a valve and executes it
	 */
	public void mainLoop() {
		ValveResponse response = ValveResponse.EXECUTED;
		Message message = null;
		
		while (response != ValveResponse.FINISH) {
			try {
				message = (Message) messageQueue.take();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Valve valve : valves) {
				response = valve.execute(message);
				if (response != ValveResponse.MISS) 
					break;
			}
			//now the response is executed
			//if response becomes finish, then loop stops
		}
	}
}
