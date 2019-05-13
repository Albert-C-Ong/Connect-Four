package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.Model.*;
import edu.sjsu.cs.cs151.connectfour.View.GamePanel;
import edu.sjsu.cs.cs151.connectfour.View.View;
import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

/**
 * ColumnSelectedValve.java
 * 
 * CS 151 Spring 2019 Professor Katarzyna Tarnowska
 * 
 * A Valve that places a piece on the Connect Four board Behaves differently for
 * local/online games
 * 
 * @author Holly Lind
 * @since 09.05.2019
 */
public class ColumnSelectedValve implements Valve {

	public ColumnSelectedValve(Model model, View view) {
		this.model = model;
		this.view = view;
		gamePanel = view.getGamePanel();
	}

	public ValveResponse execute(Message message) {
		if (message.getClass() != ColumnSelectedMessage.class)
			return ValveResponse.MISS;
      
		// actions in model
		ColumnSelectedMessage specificMessage = (ColumnSelectedMessage) message;
		String player = specificMessage.getPlayer();
		
		GameInfo updatedGameInfo = model.oneTurn(player, specificMessage.getColumnSelected());

		// actions in view
		if (updatedGameInfo.getMostRecentlyPlacedTile() != null) {
			Tile recentTile = updatedGameInfo.getMostRecentlyPlacedTile();

			// draw the new tile
			gamePanel.drawNewPiece(recentTile.getXCoord(), recentTile.getYCoord(), recentTile.getColor());
			
			if (Controller.SERVER.getActiveStatus() && player.equals(Model.getPlayerOne()))
				Controller.SERVER.sendMove(new GameMoveMessage(recentTile.getXCoord()));
			
			else if (Controller.CLIENT.getActiveStatus() && player.equals(Model.getPlayerTwo())) 
				Controller.CLIENT.sendMove(new GameMoveMessage(recentTile.getXCoord()));
			
			// open a dialog box if the game is over
			if (updatedGameInfo.getCurrentState() == GameState.WIN)
				gamePanel.gameOverDialogBox(updatedGameInfo.getCurrentPlayer() + " wins!", "Winner");
			
			else if (updatedGameInfo.getCurrentState() == GameState.TIE)
				gamePanel.gameOverDialogBox("Tie!", "Tie");
		}

		return ValveResponse.EXECUTED;
	}

	private Model model;
	private View view;
	private GamePanel gamePanel;
}
