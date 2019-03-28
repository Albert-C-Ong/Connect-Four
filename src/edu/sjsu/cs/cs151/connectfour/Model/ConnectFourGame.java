package edu.sjsu.cs.cs151.connectfour.Model;

/** ConnectFourGame.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class that deals with the game mechanics of Connect Four.
 * 
 * @author Holly Lind
 * @since 3/27/2019
 */
public class ConnectFourGame {

	
	/**
	 * creates Connect Four game
	 * keeps track of game mechanics
	 * @postcondition Board is initialized, starts with player 1
	 */
	public ConnectFourGame() {
		board = new Board();
		current_player = PLAYER_ONE;
		activeGame = true;
	}
	
	
	/**
	 * changes state of game based on player's most recent turn
	 * takes player input and column selected from user input (from UI)
	 * @param player that just went
	 * @param columnSelected by the player
	 * @return String indicating state of game
	 */
	public String oneTurn(String player, int columnSelected) {
		if (!activeGame)
			return "game is already over";
		else if (!player.equals(current_player))
			return "wrong player; should be other player's turn";
		
		int rowSelected = board.checkColumnPlacement(columnSelected);
		if (rowSelected == 6)
			return "chosen column is full";
		
		board.setTile(columnSelected, rowSelected, player);
		if (checkWin(columnSelected, rowSelected)) {
			activeGame = false;
			return "winner at " + columnSelected + ", " + rowSelected;
		}
		else if (checkTie()) {
			activeGame = false;
			return "tie at " + columnSelected + ", " + rowSelected + "  ";
		}
		else {
			changePlayer();
			return "piece placed at " + columnSelected + ", " + rowSelected;
		}
	}
	
	
	/**
	 * access method for gameBoard
	 * @return gameBoard
	 */
	public Board getBoard() { 
		return board; 
	}
	
	
	/**
	 * checks if a player has won based on most recent Tile placed
	 * @param column of most recent Tile placed
	 * @param row of most recent Tile placed
	 * @return true if player won; else false
	 */
	public boolean checkWin(int column, int row) {
		String color = board.getTile(column, row).getColor();
		return checkWinVertical(column, row, color) || checkWinHorizontal(column, row, color) ||
				checkWinLeftDiagonal(column, row, color) || checkWinRightDiagonal(column, row, color);
	}
	
	
	/**
	 * checks if Board is full
	 * @return true if Board is full; else false
	 */
	public boolean checkTie() {
		boolean fullBoard = true;
		for (int i = 0; i < Board.getColumns(); i++)
			fullBoard = fullBoard && (board.checkColumnPlacement(i) == 6);
		  
		return fullBoard;
	}
	  
	
	/**
	 * resets Connect Four Game (for new game)
	 * @postcondition gameBoard is reset, becomes player 1's turn
	 */
	public void newGame() {
		board.resetBoard();
		current_player = PLAYER_ONE;
		activeGame = true;
	}
	
	
	/**
	 * access method for current_player
	 * use if you want player turns to automatically switch (as oppose to manual)
	 * @return current_player
	 */
	public String getCurrentPlayer() {
		return current_player;
	}
	
	
	/**
	 * access method for PLAYER_ONE
	 * @return PLAYER_ONE
	 */
	public static String getPlayerOne() {
		return PLAYER_ONE;
	}
	
	
	/**
	 * access method for PLAYER_TWO
	 * @return PLAYER_TWO
	 */
	public static String getPlayerTwo() {
		return PLAYER_TWO;
	}
	
	
	
	private Board board;
	private String current_player;
	private boolean activeGame;
	private static final String PLAYER_ONE = "Player 1";
	private static final String PLAYER_TWO = "Player 2";
	
	
	// switches players
	private void changePlayer() {
		if (current_player.equals(PLAYER_ONE))
			current_player = PLAYER_TWO;
		else 
			current_player = PLAYER_ONE;
	}
	
	
	// returns true if 4 in a row vertical
	// * x * * 
	// * x * * 
	// * x * * 
	// * x * * 
	private boolean checkWinVertical(int column, int row, String sameColor) {
		if (row == 0 || row == 1 || row == 2)
			return false;
		else if (board.getTile(column, row - 1).getColor().equals(sameColor)
				&& board.getTile(column, row - 2).getColor().equals(sameColor)
				&& board.getTile(column, row - 3).getColor().equals(sameColor))
			return true;
		else
			return false;
	}
	
	
	// returns true if 4 in a row horizontal
	// * * * * 
	// * * * * 
	// x x x x 
	// * * * * 
	private boolean checkWinHorizontal(int column, int row, String sameColor) {
		if (column == 0)
			return checkWinHorizontalHelper(0, row, sameColor);
		else if (column == 1)
			return checkWinHorizontalHelper(0, row, sameColor) ||
					checkWinHorizontalHelper(1, row, sameColor);
		else if (column == 2)
			return checkWinHorizontalHelper(0, row, sameColor) ||
					checkWinHorizontalHelper(1, row, sameColor) ||
					checkWinHorizontalHelper(2, row, sameColor);
		else if (column == 3)
			return checkWinHorizontalHelper(0, row, sameColor) ||
					checkWinHorizontalHelper(1, row, sameColor) ||
					checkWinHorizontalHelper(2, row, sameColor) ||
					checkWinHorizontalHelper(3, row, sameColor);
		else if (column == 4)
			return checkWinHorizontalHelper(1, row, sameColor) ||
					checkWinHorizontalHelper(2, row, sameColor) ||
					checkWinHorizontalHelper(3, row, sameColor);
		else if (column == 5)
			return checkWinHorizontalHelper(2, row, sameColor) ||
					checkWinHorizontalHelper(3, row, sameColor);
		else if (column == 6)
			return checkWinHorizontalHelper(3, row, sameColor);
		else
			return false;
	}
	
	
	// takes coordinates of a Tile and color
	// checks if it and 3 Tiles to the right of it have the same color
	private boolean checkWinHorizontalHelper(int column, int row, String sameColor) {
		return board.getTile(column, row).getColor().equals(sameColor) &&
				board.getTile(column + 1, row).getColor().equals(sameColor) &&
				board.getTile(column + 2, row).getColor().equals(sameColor) &&
				board.getTile(column + 3, row).getColor().equals(sameColor);
	}
	
	
	// returns true if 4 in a row left diagonal
	// x * * * 
	// * x * * 
	// * * x * 
	// * * * x 
	private boolean checkWinLeftDiagonal(int column, int row, String sameColor) {
		int sum = column + row;
		if (sum <= 2 || sum >= 9)
			return false;
		else if (sum == 3)
			return checkWinLeftDiagonalHelper(0, 3, sameColor);
		else if (sum == 4)
			return checkWinLeftDiagonalHelper(0, 4, sameColor) ||
					checkWinLeftDiagonalHelper(1, 3, sameColor);
		else if (sum == 5)
			return checkWinLeftDiagonalHelper(0, 5, sameColor) ||
					checkWinLeftDiagonalHelper(1, 4, sameColor) ||
					checkWinLeftDiagonalHelper(2, 3, sameColor);
		else if (sum == 6)
			return checkWinLeftDiagonalHelper(1, 5, sameColor) ||
					checkWinLeftDiagonalHelper(2, 4, sameColor) ||
					checkWinLeftDiagonalHelper(3, 3, sameColor);
		else if (sum == 7)
			return checkWinLeftDiagonalHelper(2, 5, sameColor) ||
					checkWinLeftDiagonalHelper(3, 4, sameColor);
		else if (sum == 8)
			return checkWinLeftDiagonalHelper(3, 5, sameColor);
		else
			return false;
	}
	
	
	// takes coordinates of a Tile and color
	// checks if it and 3 Tiles to the lower right of it have the same color
	private boolean checkWinLeftDiagonalHelper(int column, int row, String sameColor) {
		return board.getTile(column, row).getColor().equals(sameColor) &&
				board.getTile(column + 1, row - 1).getColor().equals(sameColor) &&
				board.getTile(column + 2, row - 2).getColor().equals(sameColor) &&
				board.getTile(column + 3, row - 3).getColor().equals(sameColor);
	}
	
	
	// returns true if 4 in a row right diagonal
	// * * * x 
	// * * x * 
	// * x * * 
	// x * * * 
	private boolean checkWinRightDiagonal(int column, int row, String sameColor) {
		int dif = column - row;
		if (dif <= -3 || dif >= 4)
			return false;
		else if (dif == -2)
			return checkWinRightDiagonalHelper(0, 2, sameColor);
		else if (dif == -1)
			return checkWinRightDiagonalHelper(0, 1, sameColor) ||
					checkWinRightDiagonalHelper(1, 2, sameColor);
		else if (dif == 0)
			return checkWinRightDiagonalHelper(0, 0, sameColor) ||
					checkWinRightDiagonalHelper(1, 1, sameColor) ||
					checkWinRightDiagonalHelper(2, 2, sameColor);
		else if (dif == 1)
			return checkWinRightDiagonalHelper(1, 0, sameColor) ||
					checkWinRightDiagonalHelper(2, 1, sameColor) ||
					checkWinRightDiagonalHelper(3, 2, sameColor);
		else if (dif == 2)
			return checkWinRightDiagonalHelper(2, 0, sameColor) ||
					checkWinRightDiagonalHelper(3, 1, sameColor);
		else if (dif == 3)
			return checkWinRightDiagonalHelper(3, 0, sameColor);
		else
			return false;
	}
	
	
	// takes coordinates of a Tile and color
	// checks if it and 3 Tiles to the upper right of it have the same color
	private boolean checkWinRightDiagonalHelper(int column, int row, String sameColor) {
		return board.getTile(column, row).getColor().equals(sameColor) &&
				board.getTile(column + 1, row + 1).getColor().equals(sameColor) &&
				board.getTile(column + 2, row + 2).getColor().equals(sameColor) &&
				board.getTile(column + 3, row + 3).getColor().equals(sameColor);
	}
	
	

}
