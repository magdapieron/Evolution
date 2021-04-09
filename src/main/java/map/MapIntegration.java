package map;

import java.util.LinkedHashMap;
import java.util.Map;
import interfaces.IPositionChangeObserver;
import objects.Animal;

/**
 * contains methods for animals, than can be overridden also for plants in Map
 */

public abstract class MapIntegration implements IPositionChangeObserver{
	
	protected Map<Vector2d, Animal> animals = new LinkedHashMap<>();
	
	
	// Place animal on the map
	boolean placeAnimal(Animal animal)
	{
		if(isOccupied(animal.getPosition()))
			throw new IllegalArgumentException(animal.getPosition() + "is occupied");
		animals.put(animal.getPosition(), animal);
		animal.addObserver(this);						// register the map as an observer			
		return true;	
	}
   
	
	// Return true if given position on the map is occupied 
	boolean isOccupied(Vector2d position)
	{
		return animals.containsKey(position);
	}
	
	//  Return an object at given position
	public abstract Object objectAt(Vector2d position);
}
