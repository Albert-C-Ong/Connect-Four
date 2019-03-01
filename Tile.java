package game;

// pretty much the same as the main version

public class Tile {

	private int 	x_coord;
	private int 	y_coord;
	private String 	color;
	private boolean filled;

	
	public Tile(int x_coord, int y_coord)
	{
		this.x_coord 	= x_coord;
		this.y_coord 	= y_coord;
		color 			= "";
		filled 			= false;
	}
	
	
	// getter methods
	public int getXCoord() 		{ return x_coord; }
	public int getYCoord() 		{ return y_coord; }
	public String getColor() 	{ return color; }
	public boolean getFilled() 	{ return filled; }
	
	
	// setter method for color
	public void setColor(String newColor)
	{
		color = newColor;
	}
	
	
	// setter method for filled
	// can't make a Tile unfilled using this - no reason to do so mid-game
	public void nowFilled()
	{
		filled = true;
	}
	
	
	// resets a Tile's color and filled to default
	public void resetTile()
	{
		color	= "";
		filled 	= false;
	}
}
