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
		this.map = new WorldMap(initialParameters.width, initialParameters.height, initialParameters.jungleRatio);
		this.plants = new ArrayList<>();
		this.animals = new ArrayList<>();
		this.initialParameters = initialParameters;
		addFirstAnimals(initialParameters.numberOfFirstAnimals);
	}

	void addFirstAnimals(int numberOfFirstAnimals)
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
			while(this.map.isOccupied(position));
			Animal animal = new Animal(position, MapDirection.randomOrientation(), initialParameters.startEnergy, this.map, 0); // first animals have birthepoch = 0?
			map.placeAnimal(animal);
			animals.add(animal);
		}
	}
	
	void removeDeadAnimals() 
	{
		for(Animal animal : animals)
		{
			if(animal.getEnergy() == 0)
			{
				map.removeDeadAnimal(animal.getPosition(), animal);
				animals.remove(animal);	
			}
		}		 
	}
	
	void moveAnimals()
	{
		for(Animal animal : animals)
			animal.move();
	}
	
	void eating()
	{
		for(Plant plant : plants)
		{
			
		}
	}
	
	void reproduceAnimals()
	{
		
	}
	
	void addNewPlants()
	{
		
	}
}
