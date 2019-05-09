package edu.sjsu.cs.cs151.connectfour.Controller;

import edu.sjsu.cs.cs151.connectfour.Model.*;
import edu.sjsu.cs.cs151.connectfour.View.GamePanel;
import edu.sjsu.cs.cs151.connectfour.View.View;
import edu.sjsu.cs.cs151.connectfour.network.Client;
import edu.sjsu.cs.cs151.connectfour.network.Server;

/** ColumnSelectedValve.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A Valve that places a piece on the Connect Four board
 * Behaves differently for local/online games
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

		//actions in model
		ColumnSelectedMessage specificMessage = (ColumnSelectedMessage) message;
		GameInfo updatedGameInfo = model.oneTurn(specificMessage.getPlayer(), specificMessage.getColumnSelected());

		//actions in view
		if (updatedGameInfo.getMostRecentlyPlacedTile() != null) {
			Tile recentTile = updatedGameInfo.getMostRecentlyPlacedTile();
			
			//if local game, do stuff on gamePanel
			if (specificMessage.getLocalGame()) {
				//draw the new tile
				gamePanel.drawNewPiece(recentTile.getXCoord(), recentTile.getYCoord(), recentTile.getColor());
				
				//open a dialog box if the game is over
				if (updatedGameInfo.getCurrentState() == GameState.WIN)
					gamePanel.gameOverDialogBox(updatedGameInfo.getCurrentPlayer() + " wins!", "Winner");
				else if (updatedGameInfo.getCurrentState() == GameState.TIE)
					gamePanel.gameOverDialogBox("Tie!", "Tie");
			}
			
			//if server, do stuff on server
			else if (specificMessage.getPlayer() == Model.getPlayerOne()) {
				server = view.getServer();
				
				//draw the new tile
				System.out.println("x: " + recentTile.getXCoord() + ", y: " +  recentTile.getYCoord() + 
						", color: " + recentTile.getColor());
				server.drawNewPiece(recentTile.getXCoord(), recentTile.getYCoord(), recentTile.getColor());
				
				//send the move
				server.sendMove(recentTile.getXCoord() + "," + recentTile.getYCoord());
				
				//open a dialog box if the game is over
				if (updatedGameInfo.getCurrentState() == GameState.WIN)
					server.gameOverDialogBox(updatedGameInfo.getCurrentPlayer() + " wins!", "Winner");
				else if (updatedGameInfo.getCurrentState() == GameState.TIE)
					server.gameOverDialogBox("Tie!", "Tie");
			}
			
			//if client, do stuff on client
			else {
				client = view.getClient();
				
				//draw the new tile
				client.drawNewPiece(recentTile.getXCoord(), recentTile.getYCoord(), recentTile.getColor());
				
				//send the move
				client.sendMove(recentTile.getXCoord() + "," + recentTile.getYCoord());
				
				//open a dialog box if the game is over
				if (updatedGameInfo.getCurrentState() == GameState.WIN)
					client.gameOverDialogBox(updatedGameInfo.getCurrentPlayer() + " wins!", "Winner");
				else if (updatedGameInfo.getCurrentState() == GameState.TIE)
					client.gameOverDialogBox("Tie!", "Tie");
			}		
		}
		
		return ValveResponse.EXECUTED;
	}
	
	
	
	private Model model;
	private View view;
	private GamePanel gamePanel;
	private Server server;
	private Client client;
}
