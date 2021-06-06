//package simulation;
//
//import static org.junit.Assert.*;
//import map.Vector2d;
//import objects.Plant;
//import org.junit.Test;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
////@RunWith(MockitoJUnitRunner.class)
//public class EngineTest {
//
//	InitialParameters initialParameters = new InitialParameters();
//
////	@Mock
////	private WorldMap map = new WorldMap(100, 50, 0.1);
////
////	@InjectMocks
//	private Engine engine;
//
//	@Test
//	public void addFirstAnimalsTest() {
//
//		initialParameters.setHeight(50);
//		initialParameters.setWidth(100);
//		initialParameters.setJungleRatio(0.1);
//		initialParameters.setNumberOfFirstAnimals(5);
//		initialParameters.setStartEnergy(100);
//
//		engine = new Engine();
//
//		assertEquals(5, engine.getAnimals().size());
//	}
//
//	@Test
//	public void removeDeadAnimalsTest() {
//
//		initialParameters.setHeight(50);
//		initialParameters.setWidth(100);
//		initialParameters.setJungleRatio(0.1);
//		initialParameters.setNumberOfFirstAnimals(5);
//		initialParameters.setStartEnergy(-1);
//
//		engine = new Engine();
//
//		assertEquals(5, engine.getAnimals().size());
//
//	//	engine.removeDeadAnimals();
//
//		assertEquals(0, engine.getAnimals().size());
//
//	}
//
//	@Test
//	public void moveAnimalsTest() {
//
//		initialParameters.setHeight(50);
//		initialParameters.setWidth(100);
//		initialParameters.setJungleRatio(0.1);
//		initialParameters.setNumberOfFirstAnimals(1);
//		initialParameters.setStartEnergy(100);
//		initialParameters.setMoveEnergy(10);
//		Vector2d position;
//		engine = new Engine();
//
//		position = engine.getAnimals().get(0).getPosition();
//	//	engine.moveAnimals();
//
//		assertEquals(90, engine.getAnimals().get(0).getEnergy());
//		assertNotEquals(position, engine.getAnimals().get(0).getPosition());
//	}
////
////	@Test
////	void eatingTest() {
////
////		initialParameters.setHeight(50);
////		initialParameters.setWidth(100);
////		initialParameters.setJungleRatio(0.1);
////		initialParameters.setNumberOfFirstAnimals(5);
////		initialParameters.setStartEnergy(100);
////		initialParameters.setMoveEnergy(10);
////		Vector2d position = new Vector2d(1,1);
////		List<Animal> animal = new ArrayList<>();
////		animal.add(new Animal(position, MapDirection.NORTH, 50, map, 0));
////
////		engine = new Engine(initialParameters);
////
////		when(map.getAnimalsToFeed(position)).thenReturn(animal);
////
////	}
//
////	@Test
////	void reproduceAnimalsTest() {
////
////		initialParameters.setHeight(50);
////		initialParameters.setWidth(100);
////		initialParameters.setJungleRatio(0.1);
////		initialParameters.setNumberOfFirstAnimals(0);
////		initialParameters.setStartEnergy(100);
////		Vector2d position = new Vector2d(1,1);
////		Animal animal1 = new Animal(position, MapDirection.NORTH, 50, map, 0);
////		Animal animal2 = new Animal(position, MapDirection.NORTH, 50, map, 0);
////		engine = new Engine(initialParameters);
////
////
////		map.placeAnimal(animal1);
////		map.placeAnimal(animal2);
////
////		assertTrue(engine.getAnimals().size() == 3);
////	}
//
//	@Test
//	public void addNewPlantsTest() {
//
//		initialParameters.setHeight(50);
//		initialParameters.setWidth(100);
//		initialParameters.setJungleRatio(0.1);
//		initialParameters.setNumberOfFirstAnimals(0);
//		initialParameters.setStartEnergy(100);
//		List<Plant> plants;
//
//		engine = new Engine();
//
//		//engine.addNewPlants();
//		plants = engine.getPlants();
//
//		assertEquals(2, plants.size());
//	}
//
//	@Test
//	void nextDayTest() {
//
//	}
//}
