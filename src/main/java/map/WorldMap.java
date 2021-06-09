package map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import enums.MapDirection;
import interfaces.IEnergyChangeObserver;
import interfaces.IPositionChangeObserver;
import objects.Animal;
import objects.Plant;

public class WorldMap implements IPositionChangeObserver, IEnergyChangeObserver{
	
	private int width;
	private int height;
	private Map<Vector2d, Plant> plants;
	private Map<Vector2d, TreeSet<Animal>> animals;
	private Random random = new Random();
	private double jungleRatio;
	private Vector2d mapCenter;
	
	public WorldMap(int width, int height, double jungleRatio) 
	{
		this.width = width;
		this.height = height;
		this.plants = new LinkedHashMap<>(); 
		this.animals = new LinkedHashMap<>();			
		this.jungleRatio = jungleRatio;
		this.mapCenter = new Vector2d((int) Math.round(width/2), (int) Math.round(height/2));
	}
	
	public Vector2d randomPosition(int maxX, int minX, int maxY, int minY)
	{
		Vector2d randomPosition = new Vector2d(random.nextInt(maxX-minX+1)+minX, random.nextInt(maxY-minY+1)+minY);
		return randomPosition;
	}
	
	public void placeAnimal(Animal animal)
	{
		animal.addPositionObserver(this);						// register the map as an observer			
		animal.addEnergyObserver(this);							// register the energy as an observer	
		addAnimalToPosition(animal.getPosition(), animal);
	}
	
	public void addAnimalToPosition(Vector2d position, Animal animal)
	{
		TreeSet<Animal> animalsOnPosition = animals.get(position);
		
		if( animalsOnPosition == null)											
		{
			TreeSet<Animal> set = new TreeSet<>();
			set.add(animal);
			animals.put(position, set);
		}
		else
		{
			animalsOnPosition.add(animal);
			animals.computeIfPresent(position, (key, value) ->  animalsOnPosition);
		}
	}
	
	public void removeAnimalFromPosition(Vector2d position, Animal animal)
	{
		animals.get(position).remove(animal);
	}
	
	public void removeDeadAnimal(Vector2d position, Animal animal)
	{
		removeAnimalFromPosition(position, animal);
		animal.removeEnergyObserver(this);						
		animal.removePositionObserver(this);
	}

	public Vector2d randomPositionForChild(Vector2d parentPosition)
	{
		Vector2d childPosition = null;
		List<Integer> freePositions = new ArrayList<>();
		
		
		for(int i=0; i<8; i++)
		{
			if(!this.isOccupied(parentPosition.add(MapDirection.values()[i].toUnitVector())))
				freePositions.add(i);
		}
		
		// if there are free positions, draw of one of them
		if(!freePositions.isEmpty())
		{
			int direction = freePositions.get(random.nextInt(freePositions.size()));
			childPosition = parentPosition.add(MapDirection.values()[direction].toUnitVector());
		}
		
		// if there is not free position, draw of occupied one
		else
		{
			int direction = random.nextInt(8);
			childPosition = parentPosition.add(MapDirection.values()[direction].toUnitVector());
		}
		return childPosition;
	}
	
	public List<Animal> getAnimalsToFeed(Vector2d position)
	{
		List<Animal> listAnimalsToFeed = new ArrayList<>();
		if(animals.containsKey(position) && !animals.get(position).isEmpty())
		{			
			int biggestEnergy = animals.get(position).last().getEnergy();
			for (Animal animal : animals.get(position)) 
			{
		        if (animal.getEnergy() == biggestEnergy )
		        	listAnimalsToFeed.add(animal);
		    }	
		}		
		return listAnimalsToFeed;	
	}
	
	public List<List<Animal>> getPairsAnimalsToReproduce(int startEnergy)
	{
		List<List<Animal>> listAnimalsToReproduce = new ArrayList<>();
		
		for (Vector2d key : animals.keySet())
		{
			List<Animal> animalsOnPosition = new ArrayList<>(animals.get(key));
			if(animalsOnPosition.size() >= 2)
			{
				List<Animal> animalsPair = new ArrayList<>();
			
				if(animalsOnPosition.get(0).getEnergy() >= startEnergy/2 && animalsOnPosition.get(1).getEnergy() >= startEnergy/2)
				{
					animalsPair.add(animalsOnPosition.get(0));
					animalsPair.add(animalsOnPosition.get(1));
					listAnimalsToReproduce.add(animalsPair);
				}
			}
		}
		return listAnimalsToReproduce;
	}
	
	// Return true if given position on the map is occupied 
	public boolean isOccupied(Vector2d position)
	{
		if (animals.containsKey(position) && !animals.get(position).isEmpty())
			return true;
		return plants.containsKey(position);
	}
	
	public void removePlant(Plant plant)
	{
		plants.remove(plant.getPosition());
	}
	
	public void setPlant(Plant plant)
	{
		plants.put(plant.getPosition(), plant);
	}
	
	@Override
	public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
		removeAnimalFromPosition(oldPosition, animal);
		addAnimalToPosition(newPosition, animal);
	}

	@Override
	public void energyChanged(Animal animal) {
		removeAnimalFromPosition(animal.getPosition(), animal);
		addAnimalToPosition(animal.getPosition(), animal);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2d getMapCenter() {
		return mapCenter;
	}
}
