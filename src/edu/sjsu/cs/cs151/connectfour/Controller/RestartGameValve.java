package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.Model.Model;
import edu.sjsu.cs.cs151.connectfour.View.GamePanel;
import edu.sjsu.cs.cs151.connectfour.View.View;

/** RestartGameValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used to restart a Connect Four game
 * Behaves differently for local/online games
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class RestartGameValve implements Valve {

	/**
	 * Ctor - initalizes variables
	 * @param model
	 * @param view
	 */
	public RestartGameValve(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	
	/**
	 * Resets model and view
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != RestartGameMessage.class)
			return ValveResponse.MISS;

		//actions in model
		model.newGame();
		
		//actions in view
		RestartGameMessage specificMessage = (RestartGameMessage) message;
		
		//if local game, just clear board
		view.getGamePanel().clearBoard();
		
		if (!specificMessage.getLocalGame()) {
			
			if (Controller.SERVER.getActiveStatus())
				Controller.SERVER.sendMove(new GameMoveMessage(true));
				
			else Controller.CLIENT.sendMove(new GameMoveMessage(true));
		}
		
		return ValveResponse.EXECUTED;

	}
	
	
	private Model model;
	private View view;
}
