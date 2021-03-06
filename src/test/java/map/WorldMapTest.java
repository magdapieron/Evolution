package map;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import enums.MapDirection;
import objects.Animal;
import objects.Plant;

public class WorldMapTest {
	
	private WorldMap map;
	
	@Before
	public void setUp()
	{
		map = new WorldMap(100, 50);
	}

	@Test
	public void randomPositionTest()
	{
		Vector2d randomPosition = map.randomPosition(100, 0, 50, 0);
		Vector2d randomPosition2 = map.randomPosition(50, 5, 20, 7);
		
		assertTrue(randomPosition.x <= 100 && randomPosition.x >= 0 && randomPosition.y <= 50 && randomPosition.y >= 0);
		assertTrue(randomPosition2.x <= 50 && randomPosition2.x >= 5 && randomPosition2.y <= 20 && randomPosition2.y >= 7);
	}
	
	@Test
	public void placeAnimalTest()
	{
		Vector2d position = new Vector2d(3,5);
		Animal animal = new Animal(position, MapDirection.NORTH, 50, map, 0);
		
		map.placeAnimal(animal);
		
		assertTrue(map.isOccupied(position));		
	}
	
	@Test
	public void addAnimalToPositionTest()
	{
		Vector2d position1 = new Vector2d(3,5);
		Vector2d position2 = new Vector2d(3,6);
		Animal animal1 = new Animal(position1, MapDirection.NORTH, 50, map, 0);
		Animal animal2 = new Animal(position2, MapDirection.SOUTH, 55, map, 0);
		
		map.placeAnimal(animal1);
		map.placeAnimal(animal2);
		
		assertTrue(map.isOccupied(position1));
		assertTrue(map.isOccupied(position2));		
	}
	
	@Test
	public void removeAnimalFromPositionTest()
	{
		Vector2d position1 = new Vector2d(3,5);
		Vector2d position2 = new Vector2d(3,6);
		Animal animal1 = new Animal(position1, MapDirection.NORTH, 50, map, 0);
		Animal animal2 = new Animal(position2, MapDirection.SOUTH, 55, map, 0);
		
		map.placeAnimal(animal1);
		map.placeAnimal(animal2);
		map.removeAnimalFromPosition(position1, animal1);
		map.removeAnimalFromPosition(position2, animal2);
		
		assertFalse(map.isOccupied(position1));
		assertFalse(map.isOccupied(position2));	
	}	
	
	@Test
	public void randomPositionForChildTest()
	{
		Vector2d parentPosition = new Vector2d(1,1);
		Animal parent = new Animal(parentPosition, MapDirection.NORTH, 50, map, 0);
		
		//positions around parent is not occupied
		map.placeAnimal(parent);
		Vector2d childPosition = map.randomPositionForChild(parentPosition);
		assertTrue(childPosition.x <= 2 && childPosition.x >= 0 && childPosition.y <= 2 && childPosition.y >= 0);
		assertNotEquals(childPosition, parentPosition);

		// one position around parent is not occupied			
		map.placeAnimal(new Animal(new Vector2d(1,2), MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(new Vector2d(0,2), MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(new Vector2d(2,2), MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(new Vector2d(0,1), MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(new Vector2d(2,1), MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(new Vector2d(1,0), MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(new Vector2d(2,0), MapDirection.NORTH, 50, map, 0));

		childPosition = map.randomPositionForChild(parentPosition);
		assertTrue(childPosition.x == 0 && childPosition.y == 0);
		assertNotEquals(childPosition, parentPosition);
	}
	
	@Test
	public void getAnimalsToFeedTest()
	{
		Vector2d position = new Vector2d(1,1);
		
		List<Animal> listAnimalsToFeed;
		
		map.placeAnimal(new Animal(position, MapDirection.EAST, 50, map, 0));
		map.placeAnimal(new Animal(position, MapDirection.NORTH, 50, map, 0));
		map.placeAnimal(new Animal(position, MapDirection.NORTH_WEST, 50, map, 0));
		
		listAnimalsToFeed = map.getAnimalsToFeed(position);

		assertEquals(3, listAnimalsToFeed.size());
	}
	
	@Test
	public void getPairsAnimalsToReproduceTest()
	{
		Vector2d position = new Vector2d(1,1);		
		List<List<Animal>> listPairsAnimalsToReproduce;
		
		Animal animal1 = new Animal(position, MapDirection.EAST, 50, map, 0);
		Animal animal2 = new Animal(position, MapDirection.NORTH, 28, map, 0);
		map.placeAnimal(animal1);
		map.placeAnimal(animal2);
		map.placeAnimal(new Animal(position, MapDirection.NORTH_WEST, 20, map, 0));
		
		listPairsAnimalsToReproduce = map.getPairsAnimalsToReproduce(40);
		List<Animal> pair = listPairsAnimalsToReproduce.get(0);

		assertEquals(1, listPairsAnimalsToReproduce.size());
		assertSame(animal1,pair.get(0));
		assertSame(animal2,pair.get(1));
		
		listPairsAnimalsToReproduce = map.getPairsAnimalsToReproduce(100);
		assertTrue(listPairsAnimalsToReproduce.isEmpty());
	}
	
	@Test
	public void isOccupied()
	{
		Vector2d position1 = new Vector2d(1,1);
		Vector2d position2 = new Vector2d(1,2);
		Plant plant = new Plant(position2);
		
		map.placeAnimal(new Animal(position1, MapDirection.EAST, 50, map, 0)); 
		map.setPlant(plant);
		
		assertTrue(map.isOccupied(position1));
		assertTrue(map.isOccupied(position2));
	}
	
	@Test
	public void setPlantTest()
	{	
		Vector2d position = new Vector2d(1,1);
		Plant plant = new Plant(position);
		
		map.setPlant(plant);
		assertTrue(map.isOccupied(position));
	}

	@Test
	public void RemovePlantTest()
	{
		Vector2d position = new Vector2d(1,1);
		Plant plant = new Plant(position);
		
		map.setPlant(plant);
		map.removePlant(plant);
		
		assertFalse(map.isOccupied(position));		
	}
}
