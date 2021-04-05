package map;

import static org.junit.Assert.*;
import org.junit.Test;

public class Vector2dTest {

	Vector2d vec1 = new Vector2d(2,4);
	Vector2d vec2 = new Vector2d(1,2);
	
	@Test
	public void testAdd()
	{
		assertEquals(new Vector2d(3,6), vec1.add(new Vector2d(1,2)));
	}
	
	@Test
	public void testEqulas()
	{
		assertEquals(new Vector2d(2,4), vec1);
		assertFalse(vec1.equals(vec2));
		assertTrue(vec2.equals(vec2));
	}
	
	@Test
	public void testHashCode()
	{
		assertEquals(new Vector2d(2,4).hashCode(), vec1.hashCode());
		assertEquals(vec1.hashCode(), vec1.hashCode());
		assertNotEquals(vec1.hashCode(), vec2.hashCode());
	}
}
