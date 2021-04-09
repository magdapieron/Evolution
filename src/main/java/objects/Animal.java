package objects;

import java.util.LinkedList;
import java.util.List;
import interfaces.IPositionChangeObserver;
import enums.MapDirection;
import interfaces.IMapElement;
import map.Vector2d;
import map.WorldMap;

public class Animal implements IMapElement{

	private Genotype genotype;
	private Vector2d position;
	private int energy;
	private WorldMap map;
	private MapDirection orientation;
	private List<IPositionChangeObserver> observers = new LinkedList<>();
	private int children;
	private int birthEpoch;
	private int deathEpoch;
	// private int descendants; ?
	
	// initial animal with random genes
	public Animal(Vector2d initialPosition, MapDirection initialOrientation, int energy, WorldMap map, int birthEpoch )
	{
		this.position = initialPosition;
		this.orientation = initialOrientation;
		this.energy = energy;
		this.map = map;
		this.children = 0;
		this.birthEpoch = birthEpoch;
		this.deathEpoch = -1;
		this.genotype = new Genotype();
	}

	// born animal
	public Animal(Genotype genotype, Vector2d initialPosition, MapDirection initialOrientation, int energy, WorldMap map, int birthEpoch )
	{
		this(initialPosition, initialOrientation, energy, map, birthEpoch);
		this.genotype = genotype;
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
		int rotation = this.genotype.drawGene();
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
		
		positionChanged(oldPosition, position);
	}
	
	public Animal reproduction(Animal other)
	{		
		Vector2d childPosition = map.randomPositionForChild(this.position);
		MapDirection childOrientation = MapDirection.randomOrientation();
		int childEnergy = this.energy/4 + other.getEnergy()/4;
		Genotype childGenotype = this.genotype.createGenotype(other.getGenotype());
		
		this.energy -= this.energy*(1/4);
		other.energy -= other.energy*(1/4);
		
		this.newChild();
		other.newChild();
		
		return new Animal(childGenotype, childPosition, childOrientation, childEnergy, this.map, this.birthEpoch);		
	}
	
	public void eatPlant(int plantEnergy)
	{
		this.energy += plantEnergy;
	}
	
	public int getLifeExpectancy()
	{
		return deathEpoch-birthEpoch;
	}
	
	public void newChild()
	{
		this.children++;
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
