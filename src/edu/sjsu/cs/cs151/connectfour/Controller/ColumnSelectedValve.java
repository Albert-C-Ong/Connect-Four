package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.Model.*;
import edu.sjsu.cs.cs151.connectfour.View.View;

/** ColumnSelectedValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve used whenever a ColumnSelectedMessage is taken
 * from the queue of messages in the Controller.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class ColumnSelectedValve implements Valve {

	
	/**
	 * Constructor - initializes the Model and View pointers
	 * @param model
	 * @param view
	 */
	public ColumnSelectedValve(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	
	/**
	 * Updates the Model and View to place a Tile
	 * If the message isn't a ColumnSelectedMessage, then do nothing
	 * @param message - the message that was taken
	 */
	public ValveResponse execute(Message message) {
		if (message.getClass() != ColumnSelectedMessage.class)
			return ValveResponse.MISS;

		//actions in model
		ColumnSelectedMessage specificMessage = (ColumnSelectedMessage) message;
		GameInfo updatedGameInfo = model.oneTurn(specificMessage.getPlayer(), specificMessage.getColumnSelected());
		
		//actions in view
		//do nothing if the wrong player went or chose a full column
		if (updatedGameInfo.getMostRecentlyPlacedTile() != null) {
			//draw the new tile
			Tile recentTile = updatedGameInfo.getMostRecentlyPlacedTile();
			view.drawNewPiece(recentTile.getXCoord(), recentTile.getYCoord(), recentTile.getColor());
			
			//open a dialog box if the game is over
			if (updatedGameInfo.getCurrentState() == GameState.WIN)
				view.gameOverDialogBox(updatedGameInfo.getCurrentPlayer() + " wins!", "Winner");
			else if (updatedGameInfo.getCurrentState() == GameState.TIE)
				view.gameOverDialogBox("Tie!", "Tie");
		}
		
		return ValveResponse.EXECUTED;
	}
	
	
	
	private Model model;
	private View view;
}
