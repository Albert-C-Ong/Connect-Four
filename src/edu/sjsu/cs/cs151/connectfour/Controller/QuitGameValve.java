package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.Model.Model;
import edu.sjsu.cs.cs151.connectfour.View.View;

/** QuitGameValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used whenever a QuitGameMessage is taken
 * from the queue of messages in the Controller.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class QuitGameValve implements Valve {

	
	/**
	 * Constructor - initializes Model and View pointers
	 * @param model
	 * @param view
	 */
	public QuitGameValve(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	
	/**
	 * Updates Model and View to clear game and return to main menu
	 * If the message isn't a QuitGameMessage, then do nothing
	 * @param message - the message that was taken
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != QuitGameMessage.class)
			return ValveResponse.MISS;

		//actions in model
		model.newGame();
		
		//actions in view
		view.clearBoard();
		view.returnToMainMenu();
		
		return ValveResponse.FINISH;
	}
	
	
	private Model model;
	private View view;

}
