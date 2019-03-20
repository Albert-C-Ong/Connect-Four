package game;

/** ConnectFourGame.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class that deals with the game mechanics
 * of Connect Four.
 * 
 * @author Holly Lind
 * @since 3/9/2019
 */
public class ConnectFourGame {

	private Board gameBoard;
	private boolean player1turn;
	private boolean activeGame;
	
	public ConnectFourGame()
	{
		gameBoard 	= new Board();
		player1turn = true;
		activeGame 	= true;
	}
	
	
	// plays through one turn
	// takes player who wants to place a Tile and the column they select
	public String oneTurn(boolean player1, int columnSelected)
	{
		if (!activeGame)
		{
			//do nothing if the game is over
			return "game is already over";
		}
		else if (player1 != player1turn)
		{
			//tell UI that wrong player went
			return "wrong player; should be other player's turn";
		}
		
		//find row where Tile will be placed
		int rowSelected = gameBoard.checkColumnPlacement(columnSelected);
		if (rowSelected == 6)
		{
			//tell UI that player chose a full column
			return "chosen column is full";
		}
		
		//if the player passed above conditions, then place the piece
		gameBoard.setTile(columnSelected, rowSelected, player1);
		if (!checkWin(columnSelected, rowSelected))
		{
			player1turn = !player1turn;
			return "piece placed at " + columnSelected + ", " + rowSelected;
		}
		else if (player1turn)
		{
			activeGame = false;
			return "player 1 won!";
		}
		else
		{
			activeGame = false;
			return "player 2 won!";
		}
	}
	
	
	// getter method for the Board
	public Board getBoard() { return gameBoard; }
	
	
	// takes coordinates of Tile most recently placed (filled)
	// if it made a 4 in a row, returns true - else returns false
	public boolean checkWin(int column, int row)
	{
		String color = gameBoard.getTile(column, row).getColor();
		return checkWinVertical(column, row, color) || checkWinHorizontal(column, row, color) ||
				checkWinLeftDiagonal(column, row, color) || checkWinRightDiagonal(column, row, color);
	}
	
	
	// returns true if 4 in a row vertical
	// * x * * 
	// * x * * 
	// * x * * 
	// * x * * 
	public boolean checkWinVertical(int column, int row, String sameColor)
	{
		if (row == 0 || row == 1 || row == 2)
			return false;
		else if (gameBoard.getTile(column, row - 1).getColor().equals(sameColor)
				&& gameBoard.getTile(column, row - 2).getColor().equals(sameColor)
				&& gameBoard.getTile(column, row - 3).getColor().equals(sameColor))
			return true;
		else
			return false;
	}
	
	
	// returns true if 4 in a row horizontal
	// * * * * 
	// * * * * 
	// x x x x 
	// * * * * 
	public boolean checkWinHorizontal(int column, int row, String sameColor)
	{
		if (column == 0)
			return checkWinHorizontalHelper(1, row, sameColor);
		else if (column == 1)
		{
			return checkWinHorizontalHelper(0, row, sameColor) ||
					checkWinHorizontalHelper(2, row, sameColor);
		}
		else if (column == 2)
		{
			return checkWinHorizontalHelper(0, row, sameColor) ||
					checkWinHorizontalHelper(1, row, sameColor) ||
					checkWinHorizontalHelper(3, row, sameColor);
		}
		else if (column == 3)
		{
			return checkWinHorizontalHelper(0, row, sameColor) ||
					checkWinHorizontalHelper(1, row, sameColor) ||
					checkWinHorizontalHelper(2, row, sameColor) ||
					checkWinHorizontalHelper(4, row, sameColor);
		}
		else if (column == 4)
		{
			return checkWinHorizontalHelper(1, row, sameColor) ||
					checkWinHorizontalHelper(2, row, sameColor) ||
					checkWinHorizontalHelper(3, row, sameColor);
		}
		else if (column == 5)
		{
			return checkWinHorizontalHelper(2, row, sameColor) ||
					checkWinHorizontalHelper(3, row, sameColor);
		}
		else if (column == 6)
			return checkWinHorizontalHelper(3, row, sameColor);
		else
			return false;
	}
	
	
	// takes coordinates of a Tile and color
	// checks if it and 3 Tiles to the right of it have the same color
	public boolean checkWinHorizontalHelper(int column, int row, String sameColor)
	{
		return gameBoard.getTile(column, row).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 1, row).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 2, row).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 3, row).getColor().equals(sameColor);
	}
	
	
	// returns true if 4 in a row left diagonal
	// x * * * 
	// * x * * 
	// * * x * 
	// * * * x 
	public boolean checkWinLeftDiagonal(int column, int row, String sameColor)
	{
		int sum = column + row;
		if (sum <= 2 || sum >= 9)
			return false;
		else if (sum == 3)
		{
			return checkWinLeftDiagonalHelper(0, 3, sameColor);
		}
		else if (sum == 4)
		{
			return checkWinLeftDiagonalHelper(0, 4, sameColor) ||
					checkWinLeftDiagonalHelper(1, 3, sameColor);
		}
		else if (sum == 5)
		{
			return checkWinLeftDiagonalHelper(0, 5, sameColor) ||
					checkWinLeftDiagonalHelper(1, 4, sameColor) ||
					checkWinLeftDiagonalHelper(2, 3, sameColor);
		}
		else if (sum == 6)
		{
			return checkWinLeftDiagonalHelper(1, 5, sameColor) ||
					checkWinLeftDiagonalHelper(2, 4, sameColor) ||
					checkWinLeftDiagonalHelper(3, 3, sameColor);
		}
		else if (sum == 7)
		{
			return checkWinLeftDiagonalHelper(2, 5, sameColor) ||
					checkWinLeftDiagonalHelper(3, 4, sameColor);
		}
		else if (sum == 8)
		{
			return checkWinLeftDiagonalHelper(3, 5, sameColor);
		}
		else
			return false;
	}
	
	
	// takes coordinates of a Tile and color
	// checks if it and 3 Tiles to the lower right of it have the same color
	public boolean checkWinLeftDiagonalHelper(int column, int row, String sameColor)
	{
		return gameBoard.getTile(column, row).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 1, row - 1).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 2, row - 2).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 3, row - 3).getColor().equals(sameColor);
	}
	
	
	// returns true if 4 in a row right diagonal
	// * * * x 
	// * * x * 
	// * x * * 
	// x * * * 
	public boolean checkWinRightDiagonal(int column, int row, String sameColor)
	{
		int dif = column - row;
		if (dif <= -3 || dif >= 4)
			return false;
		else if (dif == -2)
		{
			return checkWinRightDiagonalHelper(0, 2, sameColor);
		}
		else if (dif == -1)
		{
			return checkWinRightDiagonalHelper(0, 1, sameColor) ||
					checkWinRightDiagonalHelper(1, 2, sameColor);
		}
		else if (dif == 0)
		{
			return checkWinRightDiagonalHelper(0, 0, sameColor) ||
					checkWinRightDiagonalHelper(1, 1, sameColor) ||
					checkWinRightDiagonalHelper(2, 2, sameColor);
		}
		else if (dif == 1)
		{
			return checkWinRightDiagonalHelper(1, 0, sameColor) ||
					checkWinRightDiagonalHelper(2, 1, sameColor) ||
					checkWinRightDiagonalHelper(3, 2, sameColor);
		}
		else if (dif == 2)
		{
			return checkWinRightDiagonalHelper(2, 0, sameColor) ||
					checkWinRightDiagonalHelper(3, 1, sameColor);
		}
		else if (dif == 3)
		{
			return checkWinRightDiagonalHelper(3, 0, sameColor);
		}
		else
			return false;
	}
	
	
	// takes coordinates of a Tile and color
	// checks if it and 3 Tiles to the upper right of it have the same color
	public boolean checkWinRightDiagonalHelper(int column, int row, String sameColor)
	{
		return gameBoard.getTile(column, row).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 1, row + 1).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 2, row + 2).getColor().equals(sameColor) &&
				gameBoard.getTile(column + 3, row + 3).getColor().equals(sameColor);
	}
	
	
	// resets the game
	public void newGame()
	{
		gameBoard.resetBoard();
		player1turn = true;
		activeGame 	= true;
	}
}
