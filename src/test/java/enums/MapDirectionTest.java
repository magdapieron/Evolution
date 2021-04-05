package enums;

import static org.junit.Assert.*;
import org.junit.Test;


public class MapDirectionTest {

	@Test 
	public void testNext()
	{
		assertEquals(MapDirection.NORTH_WEST, MapDirection.WEST.next());
		assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH.next());
		assertEquals(MapDirection.EAST, MapDirection.NORTH_EAST.next());
		assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_EAST.next());
	}

	@Test
	public void testPrevious()
	{
		assertEquals(MapDirection.NORTH_EAST, MapDirection.EAST.previous());
		assertEquals(MapDirection.SOUTH_EAST, MapDirection.SOUTH.previous());
		assertEquals(MapDirection.WEST, MapDirection.NORTH_WEST.previous());
		assertEquals(MapDirection.EAST, MapDirection.SOUTH_EAST.previous());
	}
	
	@Test
	public void testRandomOrientation()
	{
		MapDirection direction = MapDirection.randomOrientation();
		assertTrue(direction.ordinal() < 8 && direction.ordinal() >= 0);
		assertTrue(direction.toUnitVector().x >= -1 && direction.toUnitVector().x <= 1);
		assertTrue(direction.toUnitVector().y >= -1 && direction.toUnitVector().y <= 1);
	}
	
	@Test
	public void changeOrientationTest()
	{
		MapDirection orientation = MapDirection.NORTH;
		assertEquals(MapDirection.NORTH ,orientation.changeOrientation(0));
		assertEquals(MapDirection.SOUTH ,orientation.changeOrientation(4));
		assertEquals(MapDirection.NORTH ,orientation.changeOrientation(8));
	}
}
