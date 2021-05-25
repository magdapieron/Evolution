package simulation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import enums.MapDirection;
import map.Vector2d;
import map.WorldMap;
import objects.Animal;
import objects.Plant;

@RunWith(MockitoJUnitRunner.class)
class EngineTest {

	InitialParameters initialParameters = new InitialParameters();	
	
//	@Mock
//	private WorldMap map = new WorldMap(100, 50, 0.1);
//	
//	@InjectMocks
	private Engine engine;
	
	@Test
	void addFirstAnimalsTest() {

		initialParameters.setHeight(50);
		initialParameters.setWidth(100);
		initialParameters.setJungleRatio(0.1);
		initialParameters.setNumberOfFirstAnimals(5);
		initialParameters.setStartEnergy(100);	
		
		engine = new Engine(initialParameters);
		
		assertTrue(engine.getAnimals().size() == 5);
	}
	
	@Test
	void removeDeadAnimalsTest() {
		
		initialParameters.setHeight(50);
		initialParameters.setWidth(100);
		initialParameters.setJungleRatio(0.1);
		initialParameters.setNumberOfFirstAnimals(5);
		initialParameters.setStartEnergy(-1);	
		
		engine = new Engine(initialParameters);
		
		assertTrue(engine.getAnimals().size() == 5);
		 
		engine.removeDeadAnimals();
		
		assertTrue(engine.getAnimals().size() == 0);

	}
	
	@Test
	void moveAnimalsTest() {
		
		initialParameters.setHeight(50);
		initialParameters.setWidth(100);
		initialParameters.setJungleRatio(0.1);
		initialParameters.setNumberOfFirstAnimals(1);
		initialParameters.setStartEnergy(100);
		initialParameters.setMoveEnergy(10);
		Vector2d position;
		engine = new Engine(initialParameters);
		
		position = engine.getAnimals().get(0).getPosition();
		engine.moveAnimals();
		
		assertTrue(engine.getAnimals().get(0).getEnergy() == 90);
		assertNotEquals(position, engine.getAnimals().get(0).getPosition());
	}
//	
//	@Test
//	void eatingTest() {
//		
//		initialParameters.setHeight(50);
//		initialParameters.setWidth(100);
//		initialParameters.setJungleRatio(0.1);
//		initialParameters.setNumberOfFirstAnimals(5);
//		initialParameters.setStartEnergy(100);
//		initialParameters.setMoveEnergy(10);
//		Vector2d position = new Vector2d(1,1);
//		List<Animal> animal = new ArrayList<>();
//		animal.add(new Animal(position, MapDirection.NORTH, 50, map, 0));
//
//		engine = new Engine(initialParameters);
//		
//		when(map.getAnimalsToFeed(position)).thenReturn(animal);
//		
//	}
	
//	@Test
//	void reproduceAnimalsTest() {
//
//		initialParameters.setHeight(50);
//		initialParameters.setWidth(100);
//		initialParameters.setJungleRatio(0.1);
//		initialParameters.setNumberOfFirstAnimals(0);
//		initialParameters.setStartEnergy(100);
//		Vector2d position = new Vector2d(1,1);
//		Animal animal1 = new Animal(position, MapDirection.NORTH, 50, map, 0);
//		Animal animal2 = new Animal(position, MapDirection.NORTH, 50, map, 0);
//		engine = new Engine(initialParameters);
//		
//		
//		map.placeAnimal(animal1);
//		map.placeAnimal(animal2);
//		
//		assertTrue(engine.getAnimals().size() == 3);
//	}
	
	@Test
	void addNewPlantsTest() {
		
		initialParameters.setHeight(50);
		initialParameters.setWidth(100);
		initialParameters.setJungleRatio(0.1);
		initialParameters.setNumberOfFirstAnimals(0);
		initialParameters.setStartEnergy(100);
		List<Plant> plants;
		
		engine = new Engine(initialParameters);
		
		engine.addNewPlants();
		plants = engine.getPlants();
		
		assertTrue(plants.size() == 2);	
	}
	
	@Test
	void nextDayTest() {
		
	}
}
