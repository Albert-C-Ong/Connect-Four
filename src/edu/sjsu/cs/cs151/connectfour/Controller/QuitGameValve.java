package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.Model.Model;
import edu.sjsu.cs.cs151.connectfour.View.GamePanel;
import edu.sjsu.cs.cs151.connectfour.View.View;

/** QuitGameValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used to quit a Connect Four game
 * Behaves differently for local/online games
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class QuitGameValve implements Valve {

	public QuitGameValve(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	
	public ValveResponse execute(Message message) {
		if (message.getClass() != QuitGameMessage.class)
			return ValveResponse.MISS;

		//actions in model
		model.newGame();
		
		//actions in view
		QuitGameMessage specificMessage = (QuitGameMessage) message;

		//if local game, just replace game panel with menu
		view.getGamePanel().clearBoard();
		
//		//if online game, close quitting player's connection + replace w/ menu panel
		if (!specificMessage.getLocalGame()) {
			if (Controller.SERVER.getActiveStatus())
				Controller.SERVER.closeConnection();
				
			else Controller.CLIENT.closeConnection();
		}

		view.replacePanel(view.getGamePanel(), view.getMenuPanel());
		
		return ValveResponse.EXECUTED;
	}
	
	private Model model;
	private View view;

}
