
/** TileTest.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * JUnit test class for Tile.java. 
 * @author Krish Ghiy, Holly Lind, and Albert Ong
 * @since 09.03.2019
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TileTest {

	@Test
	void testSetColor() {
		Tile t = new Tile(3,4);
		t.setColor("Red");
		assertTrue(t.getColor().equals("Red"));
	}

	@Test
	void testSetFilled() {
		Tile t = new Tile(3,4);
		t.setFilled(true);
		assertTrue(t.getFilled());
	}

	@Test
	void testToString() {
		Tile t = new Tile(3,4);
		assertTrue(t.toString().equals(3 + " " + 4));
	}

}
