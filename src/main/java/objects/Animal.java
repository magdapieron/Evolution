package objects;

import java.util.LinkedList;
import java.util.List;
import interfaces.IPositionChangeObserver;
import enums.MapDirection;
import interfaces.IMapElement;
import map.Vector2d;
import map.Map;

public class Animal implements IMapElement{

	private Genotype genotype;
	private Vector2d position;
	private int energy;
	private Map map;
	private MapDirection orientation;
	private List<IPositionChangeObserver> observers = new LinkedList();
	
	public Animal(Genotype genotype, Vector2d initialPosition, MapDirection initialOrientation, int energy, Map map )
	{
		this.genotype = genotype;
		this.position = initialPosition;
		this.orientation = initialOrientation;
		this.energy = energy;
		this.map = map;
	}

	public Vector2d getPosition() {
		return position;
	}

	public int getEnergy() {
		return energy;
	}
	
	public MapDirection getOrientation() {
		return orientation;
	}

	public Genotype getGenotype() {
		return genotype;
	}

	@Override
	public String toString() {
		return orientation.toString();
	}
	
	// draw of rotation based on genotype 
	public void move()		
	{	
		int rotation = this.genotype.drawGen();
		this.orientation = orientation.changeOrientation(rotation);
		
		Vector2d oldPosition = position;
		Vector2d newPosition = position.add(orientation.toUnitVector());
		int newX = newPosition.x;
		int newY = newPosition.y;
		
		if(newX > map.getWidth())
		{
			newX = newX - map.getWidth();
		}
		else if(newX == -1)
		{
			newX = newX + map.getWidth();
		}
		
		if(newY > map.getHeight())
		{
			newY = newY - map.getHeight();
		}
		else if(newY == -1)
		{
			newX = newY + map.getHeight();
		}
		
		this.position = new Vector2d(newX, newY);
		newPosition = this.position;
		
		positionChanged(oldPosition, newPosition);
	}
	
	int randomRotation()
	{
		return MapDirection.randomOrientation().ordinal(); 		
	}
	
	public Animal reproduction(Animal other)
	{		
		return null;		
	}
	
	public void addObserver(IPositionChangeObserver observer)
	 {
		 observers.add(observer);
	 }
	 
	public void removeObserver(IPositionChangeObserver observer)
	 {
		 observers.remove(observer);
	 }
	 
	public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
	 {
		 for(IPositionChangeObserver obs : observers)
		 {
			 obs.positionChanged(oldPosition, newPosition);
		 }
	 }
}
