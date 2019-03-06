package game;

/**
 * Tile object represents one Tile
 * each Tile has (x, y) coordinates, color, and is filled/unfilled
 */
public class Tile {

	
	/**
	 * creates a Tile at coordinates (x, y)
	 * @param x_coord: x coordinate of the Tile
	 * @param y_coord: y coordinate of the Tile
	 * @postcondition Tile is colorless and unfilled
	 */
	public Tile(int x_coord, int y_coord) {
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		color = "";
		filled = false;
	}
	
	
	/**
	 * access method for x
	 * @return x_coord
	 */
	public int getXCoord() {
		return x_coord;
	}
	
	
	/**
	 * access method for y
	 * @return y_coord
	 */
	public int getYCoord() { 
		return y_coord;
	}
	
	
	/**
	 * access method for color
	 * @return color
	 */
	public String getColor() {
		return color;
	}
	
	
	/**
	 * access method for fill
	 * @return filled
	 */
	public boolean getFilled() {
		return filled;
	}
	
	
	/**
	 * modifier method for x
	 * @param newColor: String to set color to
	 */
	public void setColor(String newColor) {
		color = newColor;
	}
	
	
	/**
	 * sets filled to true 
	 * use after a player selects this particular Tile
	 * @postcondition Tile is filled
	 */
	public void nowFilled() {
		filled = true;
	}
	
	
	/**
	 * resets all of Tile parameters except for coordinates
	 * use when new game is started
	 * @postcondition Tile is colorless and unfilled
	 */
	public void resetTile() {
		color = "";
		filled = false;
	}
	
	
	private int x_coord;
	private int y_coord;
	private String color;
	private boolean filled;
}
