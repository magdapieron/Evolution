package map;

import java.util.ArrayList;
import java.util.Comparator;
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

public class WorldMap implements IPositionChangeObserver, IEnergyChangeObserver {
	
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
	
	public Vector2d jungleLowerLeftCorner()
	{		
		int jungleWidth = (int) Math.round(width*jungleRatio);
		int jungleHeight = (int) Math.round(height*jungleRatio);
		return new Vector2d((int)Math.round(mapCenter.x - jungleWidth) , (int)Math.round(mapCenter.y - jungleHeight));
	}
	
	public Vector2d jungleUpperRightCorner()
	{		
		int jungleWidth = (int) Math.round(width*jungleRatio);
		int jungleHeight = (int) Math.round(height*jungleRatio);
		return mapCenter.add(new Vector2d(jungleWidth, jungleHeight));
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
		TreeSet<Animal> animalsOnPosition = animals.get(animal.getPosition());
		if(animalsOnPosition == null)
		{
			TreeSet<Animal> set = new TreeSet<>(Comparator.comparing(Animal:: getEnergy));
			set.add(animal);
			animals.put(position, set);
		}
		else
		{
			animalsOnPosition.add(animal);
		}
	}
	
	public void removeAnimalFromPosition(Vector2d position, Animal animal)
	{
		TreeSet<Animal> animalsOnPosition = animals.get(animal.getPosition());
		
		if(animalsOnPosition.isEmpty() || animalsOnPosition == null)
		{
			throw  new IllegalArgumentException("No animal at position: " + position);	// to be served later? how?
		}

		else
		{
			animalsOnPosition.remove(animal);
		}
	}
	
	public void removeDeadAnimal(Vector2d position, Animal animal)
	{
		removeAnimalFromPosition(position, animal);
		animal.removeEnergyObserver(this);						//from this?
		animal.removePositionObserver(this);
	}

	public Vector2d randomPositionForChild(Vector2d parentPosition)
	{
		Vector2d childPosition = null;
		List<Integer> freePositions = new ArrayList<>();
		
		for(int i=0; i<8; i++)
		{
			if(isOccupied(parentPosition.add(MapDirection.values()[i].toUnitVector())));
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
	
	// Return true if given position on the map is occupied 
	public boolean isOccupied(Vector2d position)
	{
		if (animals.containsKey(position) && !animals.get(position).isEmpty())
			return true;
		return plants.containsKey(position);
	}
	
	public List<Animal> animalsToFeed(Vector2d position)
	{		
		return null;	
	}
	
	public Object objectsAt(Vector2d position)		// to check
	{
		if(isOccupied(position))  
		{
			if(!animals.get(position).isEmpty())
			{
				return animals.get(position);
			}
			if(plants.get(position) != null)
			{
				return plants.get(position);
			}				
		}			
		return null;
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
}
