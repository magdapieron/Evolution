package objects;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enums.MapDirection;
import map.WorldMap;
import map.Vector2d;

public class AnimalTest {

	@Test
	public void moveTest()
	{
		Genotype genotype = new Genotype();
		Vector2d position = new Vector2d(1,2);
		int energy = 0;
		WorldMap map = new WorldMap(5,5, 0.3);
		MapDirection orientation = MapDirection.randomOrientation();
		Genotype genotype2 = new Genotype();
		
		Animal animal = new Animal(genotype, position, orientation, energy, map, 0);
		
		animal.move();
		assertNotEquals(new Vector2d(1,2), animal.getPosition());		
	}
	
	@Test
	public void reproduceTest()
	{
		WorldMap map = new WorldMap(5,5, 0.3);
		Animal animal1 = new Animal(new Vector2d(1,2), MapDirection.NORTH, 4, map, 0);
		Animal animal2 = new Animal(new Vector2d(1,2), MapDirection.WEST, 4, map, 0);
		
		Animal child = animal1.reproduction(animal2);
		Vector2d childPosition = child.getPosition();
		
		assertTrue(childPosition.x <= 2 && childPosition.x >= 0 && childPosition.y <= 3 && childPosition.y >=1);
		assertFalse(childPosition.equals(animal1.getPosition()));
		assertEquals(2, child.getEnergy());
	}
	
	@Test
	public void eatPlantTest()
	{
		WorldMap map = new WorldMap(5,5, 0.3);
		Animal animal = new Animal(new Vector2d(1,2), MapDirection.NORTH, 4, map, 0);
		animal.eatPlant(3);
		assertEquals(7, animal.getEnergy());
	}
}
