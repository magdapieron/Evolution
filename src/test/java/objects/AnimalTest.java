package objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import enums.MapDirection;
import map.WorldMap;
import map.Vector2d;

public class AnimalTest {

	WorldMap map;
	MapDirection orientation;
	Vector2d position;
	
	@Before
	public void setUp()
	{
		map = new WorldMap(5,5);
		orientation = MapDirection.EAST;
		position = new Vector2d(1,2);
	}
	
	@Test
	public void moveTest()
	{
		int energy = 50;
		Genotype genotype = new Genotype();
		Animal animal = new Animal(genotype, position, orientation, energy, map, 0); 		
		
		animal.move(5);
		
		assertNotEquals(position, animal.getPosition());	
		assertEquals(45, animal.getEnergy());
	}
	
	@Test
	public void reproductionTest()
	{
		Animal animal1 = new Animal(position, orientation, 4, map, 0);
		Animal animal2 = new Animal(position, orientation, 4, map, 0);
		
		Animal child = animal1.reproduction(animal2, 0);
		Vector2d childPosition = child.getPosition();
		
		assertTrue(childPosition.x <= 2 && childPosition.x >= 0 && childPosition.y <= 3 && childPosition.y >=1);
		assertNotEquals(childPosition, animal1.getPosition());
		assertEquals(2, child.getEnergy());
		assertEquals(3, animal1.getEnergy());
		assertEquals(3, animal2.getEnergy());
	}
	
	@Test
	public void eatPlantTest()
	{
		Animal animal = new Animal(position, orientation, 4, map, 0);
		animal.eatPlant(3);
		
		assertEquals(7, animal.getEnergy());
	}
}
