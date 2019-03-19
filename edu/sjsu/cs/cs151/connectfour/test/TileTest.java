package edu.sjsu.cs.cs151.connectfour.test;

/** TileTest.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * JUnit test class for Tile.java. 
 * Changed to extend TestCase since Krish's version didn't work on my laptop.. (-Holly)
 * 
 * @author Krish Ghiy, Holly Lind, and Albert Ong
 * @since 3/18/2019
 */

import edu.sjsu.cs.cs151.connectfour.game.Tile;
import junit.framework.TestCase;

public class TileTest extends TestCase {

	//@Test
	public void testSetColor() {
		Tile t = new Tile(3,4);
		t.setColor("Red");
		assertTrue(t.getColor().equals("Red"));
	}

	//@Test
	public void testNowFilled() {
		Tile t = new Tile(3,4);
		t.nowFilled();
		assertTrue(t.getFilled());
	}

	//@Test
	public void testResetTile() {
		Tile t = new Tile(3,4);
		t.setColor("Red");
		t.resetTile();
		assertTrue(t.getColor().equals(""));
	}

}