package map;

import static org.junit.Assert.*;
import java.util.List;
import java.util.TreeSet;
import org.junit.Test;
import enums.MapDirection;
import objects.Animal;
import objects.Plant;

public class WorldMapTest {

	@Test
	public void jungleCornersTest() 	// here's the problem :( 
	{
//		WorldMap map = new WorldMap(100, 50, 0.1);
//		assertEquals(new Vector2d(45,23), map.jungleLowerLeftCorner());
//		assertEquals(new Vector2d(55,28), map.jungleUpperRightCorner());
//		
//		WorldMap map2 = new WorldMap(99, 40, 0.1);
//		assertEquals(new Vector2d(44,18), map2.jungleLowerLeftCorner());
//		assertEquals(new Vector2d(54,22), map2.jungleUpperRightCorner());
	}

	@Test
	public void randomPositionTest()
	{
		WorldMap map = new WorldMap(100, 50, 0.1);
		Vector2d randomPosition = map.randomPosition(100, 0, 50, 0);
		assertTrue(randomPosition.x <= 100 && randomPosition.x >= 0 && randomPosition.y <= 50 && randomPosition.y >= 0);
		
		Vector2d randomPosition2 = map.randomPosition(50, 5, 20, 7);
		assertTrue(randomPosition2.x <= 50 && randomPosition2.x >= 5 && randomPosition2.y <= 20 && randomPosition2.y >= 7);
	}
	
	@Test
	public void placenimalTest()
	{
		Vector2d position = new Vector2d(3,5);
		WorldMap map = new WorldMap(100, 50, 0.1);
		Animal animal = new Animal(position, MapDirection.NORTH, 50, map, 0);
		map.placeAnimal(animal);
		
		assertTrue(map.isOccupied(position));		
	}
	
	@Test
	public void addAnimalToPositionAndRemoveAnimalFromPositionTest()
	{
		Vector2d position1 = new Vector2d(3,5);
		Vector2d position2 = new Vector2d(3,6);
		WorldMap map = new WorldMap(100, 50, 0.1);
		Animal animal1 = new Animal(position1, MapDirection.NORTH, 50, map, 0);
		Animal animal2 = new Animal(position2, MapDirection.SOUTH, 55, map, 0);
		map.addAnimalToPosition(position1, animal1);
		map.addAnimalToPosition(position2, animal2);
		
		assertTrue(map.isOccupied(position1));
		assertTrue(map.isOccupied(position2));		
	}
	
	@Test
	public void removeAnimalFromPositionTest()
	{
		Vector2d position1 = new Vector2d(3,5);
		Vector2d position2 = new Vector2d(3,6);
		WorldMap map = new WorldMap(100, 50, 0.1);
		Animal animal1 = new Animal(position1, MapDirection.NORTH, 50, map, 0);
		Animal animal2 = new Animal(position2, MapDirection.SOUTH, 55, map, 0);
		
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> map.removeAnimalFromPosition(new Vector2d(1,1), animal1));
		assertEquals("No animal at position: " + new Vector2d(1,1), ex.getMessage());
		
		map.addAnimalToPosition(position1, animal1);
		assertTrue(map.isOccupied(position1));
		
		map.addAnimalToPosition(position2, animal2);
		assertTrue(map.isOccupied(position2));	
		
		map.removeAnimalFromPosition(position1, animal1);
		assertFalse(map.isOccupied(position1));
		
		map.removeAnimalFromPosition(position2, animal2);
		assertFalse(map.isOccupied(position2));	
	}	
	
	@Test
	public void randomPositionForChildTest()
	{
		Vector2d parentPosition = new Vector2d(1,1);
		WorldMap map = new WorldMap(100, 50, 0.1);
		Animal parent = new Animal(parentPosition, MapDirection.NORTH, 50, map, 0);
		
		//positions around parent is not occupied
		map.addAnimalToPosition(parentPosition, parent);
		Vector2d childPosition = map.randomPositionForChild(parentPosition);
		assertTrue(childPosition.x <= 2 && childPosition.x >= 0 && childPosition.y <= 2 && childPosition.y >= 0);
		assertFalse(childPosition.equals(parentPosition));

		// one position around parent is not occupied	
		map.addAnimalToPosition(new Vector2d(1,2), new Animal(new Vector2d(1,2), MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(new Vector2d(0,2), new Animal(new Vector2d(0,2), MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(new Vector2d(2,2), new Animal(new Vector2d(2,2), MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(new Vector2d(0,1), new Animal(new Vector2d(0,1), MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(new Vector2d(2,1), new Animal(new Vector2d(2,1), MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(new Vector2d(1,0), new Animal(new Vector2d(1,0), MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(new Vector2d(2,0), new Animal(new Vector2d(2,0), MapDirection.NORTH, 50, map, 0));
		
		childPosition = map.randomPositionForChild(parentPosition);
		assertTrue(childPosition.x == 0 && childPosition.y == 0);	
		assertFalse(childPosition.equals(parentPosition));	
	}
	
	@Test
	public void getAnimalsToFeedTest()
	{
		WorldMap map = new WorldMap(100, 50, 0.1);
		Vector2d position = new Vector2d(1,1);
		
		List<Animal> listAnimalsToFeed = map.getAnimalsToFeed(position);
		assertTrue(listAnimalsToFeed.isEmpty());
		
		map.addAnimalToPosition(position, new Animal(position, MapDirection.EAST, 50, map, 0));
		map.addAnimalToPosition(position, new Animal(position, MapDirection.NORTH, 50, map, 0));
		map.addAnimalToPosition(position, new Animal(position, MapDirection.NORTH_WEST, 50, map, 0));
		
		listAnimalsToFeed = map.getAnimalsToFeed(position);
		assertTrue(listAnimalsToFeed.size() == 3);
	}
	
	@Test
	public void getPairsAnimalsToReproduceTest()
	{
		WorldMap map = new WorldMap(100, 50, 0.1);
		Vector2d position = new Vector2d(1,1);
		
		List<List<Animal>> listPairsAnimalsToReproduce = map.getPairsAnimalsToReproduce(40);
		assertTrue(listPairsAnimalsToReproduce.isEmpty());
		
		Animal animal1 = new Animal(position, MapDirection.EAST, 50, map, 0);
		Animal animal2 = new Animal(position, MapDirection.NORTH, 28, map, 0);
		map.addAnimalToPosition(position, new Animal(position, MapDirection.NORTH_WEST, 20, map, 0));
		
		listPairsAnimalsToReproduce = map.getPairsAnimalsToReproduce(40);
		assertTrue(listPairsAnimalsToReproduce.size() == 1);
		
		List<Animal> pair = listPairsAnimalsToReproduce.get(0);
		assertTrue(pair.get(0) == animal1 && pair.get(1) == animal2);
		
		listPairsAnimalsToReproduce = map.getPairsAnimalsToReproduce(100);
		assertTrue(listPairsAnimalsToReproduce.isEmpty());
	}
	
	@Test
	public void isOccupied()
	{
		WorldMap map = new WorldMap(100, 50, 0.1);
		Vector2d position1 = new Vector2d(1,1);
		Vector2d position2 = new Vector2d(1,2);
		
		map.addAnimalToPosition(position1, new Animal(position1, MapDirection.EAST, 50, map, 0)); 
		map.addAnimalToPosition(position1, new Animal(position1, MapDirection.NORTH, 50, map, 0));
		
		assertTrue(map.isOccupied(position1));
		assertFalse(map.isOccupied(position2));
		
		Plant plant = new Plant(position2);
		map.setPlant(plant);
		assertTrue(map.isOccupied(position2));
	}
	
	@Test
	public void  objectsAt()
	{
		WorldMap map = new WorldMap(100, 50, 0.1);
		Vector2d position1 = new Vector2d(1,1);
		Vector2d position2 = new Vector2d(1,2);
		Vector2d position3 = new Vector2d(2,1);
		
		Plant plant = new Plant(position3);
		Animal animal1 = new Animal(position1, MapDirection.EAST, 50, map, 0);
		Animal animal2 = new Animal(position2, MapDirection.NORTH, 40, map, 0);
		
		map.addAnimalToPosition(position1, animal1); 
		map.addAnimalToPosition(position2, animal2);
		map.setPlant(plant);

		TreeSet<Animal> set1 = (TreeSet<Animal>)map.objectsAt(position1);
		assertTrue(animal1 == set1.first());
		TreeSet<Animal> set2 = (TreeSet<Animal>)map.objectsAt(position2);
		assertTrue(animal2 == set2.first());
		assertTrue(plant == map.objectsAt(position3));
		
	}
	
	@Test
	public void setPlantAndRemovePlantTest()
	{
		WorldMap map = new WorldMap(100, 50, 0.1);
		Vector2d position = new Vector2d(1,1);
		Plant plant = new Plant(position);
		
		map.setPlant(plant);
		assertTrue(map.isOccupied(position));
		assertTrue(map.objectsAt(position) == plant);
		
		map.removePlant(plant);
		assertFalse(map.isOccupied(position));		
	}
}
