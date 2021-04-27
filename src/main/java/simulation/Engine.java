package simulation;

import java.util.ArrayList;
import java.util.List;
import enums.MapDirection;
import map.Vector2d;
import map.WorldMap;
import objects.Animal;
import objects.Plant;

public class Engine {
	
	private WorldMap map;
	private InitialParameters initialParameters; 
	private List<Plant> plants;
	private List<Animal> animals;
	
	public Engine(InitialParameters initialParameters)
	{
		checkJungleRatio(initialParameters.jungleRatio);
		this.map = new WorldMap(initialParameters.width, initialParameters.height, initialParameters.jungleRatio);
		this.plants = new ArrayList<>();
		this.animals = new ArrayList<>();
		this.initialParameters = initialParameters;
		addFirstAnimals(initialParameters.numberOfFirstAnimals);
	}

	private void checkJungleRatio(double jungleRatio) 
	{
		if(jungleRatio >= 0.5)
			throw new IllegalArgumentException("JungleRatio must be lower than 0.5! Change parameters!"); //  steppes cover most of the world
	}
	
	private void addFirstAnimals(int numberOfFirstAnimals)
	{
		for(int i=0; i<numberOfFirstAnimals; i++)
		{
			if(numberOfFirstAnimals > initialParameters.height*initialParameters.width)
				throw  new IllegalArgumentException("Too much initial animals!");
			Vector2d position;
			do
			{
				position = map.randomPosition(initialParameters.width, 0, initialParameters.height, 0);
			}
			while(map.isOccupied(position));
			Animal animal = new Animal(position, MapDirection.randomOrientation(), initialParameters.startEnergy, this.map, 0); // first animals have birthepoch = 0?
			animals.add(animal);
			this.map.placeAnimal(animal);
		}
	}
	
	public void removeDeadAnimals() 
	{
		for(Animal animal : animals)
		{
			if(animal.getEnergy() == 0)
			{
				this.map.removeDeadAnimal(animal.getPosition(), animal);
				animals.remove(animal);	
			}
		}		 
	}
	
	public void moveAnimals()
	{
		for(Animal animal : animals)
			animal.move(initialParameters.moveEnergy);
	}
	
	public void eating()
	{
		List<Plant> plantsToRemove = new ArrayList<>();
		for(Plant plant : plants)
		{
			List<Animal> listAnimalsToFeed = map.getAnimalsToFeed(plant.getPosition());
			for(Animal animal : listAnimalsToFeed)
			{
				animal.eatPlant(initialParameters.plantEnergy/listAnimalsToFeed.size());
			}
			if(!listAnimalsToFeed.isEmpty())
			{
				this.map.removePlant(plant);
				plantsToRemove.add(plant);
			}
		}
		
		for(Plant plantToRemove : plantsToRemove)
		{
			plants.remove(plantToRemove);
		}
	}
	
	public void reproduceAnimals()
	{
		List<List<Animal>> listAnimalsToReproduce = map.getPairsAnimalsToReproduce(initialParameters.startEnergy);
		if(!listAnimalsToReproduce.isEmpty())
		{
			for(List<Animal> parents : listAnimalsToReproduce)
			{
				Animal child = parents.get(0).reproduction(parents.get(1));
				animals.add(child);
				this.map.placeAnimal(child);
			}
		}
	}
	
	public void addNewPlants()
	{
		addPlantToJungle();
		addPlantToSteppe();
	}
	
	private void addPlantToJungle()
	{
		Vector2d rightCorner = map.jungleUpperRightCorner();
		Vector2d leftCorner = map.jungleLowerLeftCorner();		
		int ctr=0;
		Vector2d position = null;
		
		do
		{
			position = map.randomPosition(rightCorner.x, leftCorner.x, rightCorner.y, leftCorner.y);
			ctr++;
		}
		while(map.isOccupied(position) && ctr <= (rightCorner.x-leftCorner.y)*(rightCorner.y*leftCorner.y));
		
		if(ctr < (rightCorner.x-leftCorner.y)*(rightCorner.y*leftCorner.y))
		{
			Plant newPlant = new Plant(position);
			plants.add(newPlant);
			this.map.setPlant(newPlant);
		}
			
	}
	
	private void addPlantToSteppe()		// better way? 
	{
		Vector2d rightCorner = map.jungleUpperRightCorner();
		Vector2d leftCorner = map.jungleLowerLeftCorner();		
		int ctr=0;
		Vector2d position = null;
		
		do
		{
			position = map.randomPosition(initialParameters.width, 0, initialParameters.height, 0);
			ctr++;
		}
		while(map.isOccupied(position) && ctr <= initialParameters.width*initialParameters.height - ((rightCorner.x-leftCorner.y)*(rightCorner.y*leftCorner.y)) &&
				position.x >= leftCorner.x && position.x <= rightCorner.x && position.y >= leftCorner.y && position.y <= rightCorner.y);
				
		
		if(ctr < initialParameters.width*initialParameters.height)
		{
			Plant newPlant = new Plant(position);
			plants.add(newPlant);
			this.map.setPlant(newPlant);
		}
	}
	
	public void nextDay()
	{
		if(animals.size() > 0)
		{
			removeDeadAnimals();
			moveAnimals();
			eating();
			reproduceAnimals();
			addNewPlants();	
		}
	}
}
