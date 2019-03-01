package game;

// if we make the Board its own class, it'd be something like this

import java.util.ArrayList;

public class Board extends ArrayList<ArrayList<Tile>> {

	private static final int BOARD_COLUMNS 	= 7;
	private static final int BOARD_ROWS 	= 6;
	private static final String PLAYER_ONE_COLOR = "red";
	private static final String PLAYER_TWO_COLOR = "black";
	
	// ctor - initializes board
	public Board() 
	{
		for (int i = 0; i < BOARD_COLUMNS; i++)
		{
			this.add(new ArrayList<Tile>());
			
			for (int j = 0; j < BOARD_ROWS; j++)
			{
				this.get(i).add(new Tile(i, j));
			}
		}
	}
	
	
	// takes x coordinate of selected column (0-6)
	// returns y coordinate where Tile should go (0-5)
	// returns 6 if column is full
	public int checkColumnPlacement(int column)
	{
		for (int row = 0; row < BOARD_ROWS; row++)
		{
			if (!this.get(column).get(row).getFilled())
				return row;
		}
		return 6;
	}
	
	
	// getter methods
	public static int getColumns() 	{ return BOARD_COLUMNS; }
	public static int getRows() 	{ return BOARD_ROWS; }
	public static String getPlayerOneColor() { return PLAYER_ONE_COLOR; }
	public static String getPlayerTwoColor() { return PLAYER_TWO_COLOR; }
	
	
	// sets the relevant Tile properties to match a player's move
	public void setTile(int column, int row, boolean player1)
	{
		Tile tileToChange = this.get(column).get(row);
		if (player1)
		{
			//set column, row to player 1's color
			tileToChange.setColor(PLAYER_ONE_COLOR);
			tileToChange.nowFilled();
		}
		else
		{
			//set column, row to player 2's color
			tileToChange.setColor(PLAYER_TWO_COLOR);
			tileToChange.nowFilled();
		}
	}
	
	
	// resets all of the Tiles on the Board
	public void resetBoard()
	{
		for (int i = 0; i < BOARD_COLUMNS; i++)
		{
			for (int j = 0; j < BOARD_ROWS; j++)
			{
				this.getTile(i, j).resetTile();
			}
		}
	}
	
	
	// returns Tile at coordinates x, y
	public Tile getTile(int x, int y)
	{
		return this.get(x).get(y);
	}
}
