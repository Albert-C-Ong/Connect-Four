package test;

import edu.sjsu.cs.cs151.connectfour.Model.Tile;
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