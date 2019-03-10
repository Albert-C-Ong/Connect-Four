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
