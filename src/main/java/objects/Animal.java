package objects;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import interfaces.IPositionChangeObserver;
import enums.MapDirection;
import interfaces.IEnergyChangeObserver;
import interfaces.IMapElement;
import map.Vector2d;
import map.WorldMap;

public class Animal implements Comparable<Animal>, IMapElement {

	private static int currentId;
	private int id;
	
	private Genotype genotype;
	private Vector2d position;
	private int energy;
	private WorldMap map;
	private MapDirection orientation;
	private List<IPositionChangeObserver> positionObserver = new LinkedList<>();
	private List<IEnergyChangeObserver> energyObserver = new LinkedList<>();
	private int children;
	private int birthEpoch;
	private int deathEpoch;
	
	// initial animal with random genes
	public Animal(Vector2d initialPosition, MapDirection initialOrientation, int energy, WorldMap map, int birthEpoch )
	{
		this.id = currentId;
		currentId++;
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
	
	// draw of rotation based on genotype 
	public void move(int moveEnergy)		
	{	
		int rotation = this.genotype.drawGene();
		this.orientation = orientation.changeOrientation(rotation);
		
		Vector2d oldPosition = position;
		Vector2d newPosition = position.add(orientation.toUnitVector());
		int newX = newPosition.x;
		int newY = newPosition.y;
		
		this.position = wrapEdgesOfMap(newX, newY);
		
		positionChanged(oldPosition, position);
		
		this.energy -= moveEnergy;
		this.energyChanged();
	}
	
	private Vector2d wrapEdgesOfMap(int x, int y)
	{
		if(x > map.getWidth())
		{
			x = x - map.getWidth();
		}
		else if(x == -1)
		{
			x = x + map.getWidth();
		}
		
		if(y > map.getHeight())
		{
			y = y - map.getHeight();
		}
		else if(y == -1)
		{
			y = y + map.getHeight();
		}
		
		return new Vector2d(x,y);
	}
	
	public Animal reproduction(Animal other)
	{		
		Vector2d childPosition = map.randomPositionForChild(this.position);
		MapDirection childOrientation = MapDirection.randomOrientation();
		int childEnergy = this.energy/4 + other.getEnergy()/4;
		Genotype childGenotype = this.genotype.createGenotype(other.getGenotype());

		this.energy -= this.energy/4;
		this.energyChanged();
		this.newChild();

		other.energy -= other.energy/4;
		other.energyChanged();
		other.newChild();
	
		return new Animal(childGenotype, childPosition, childOrientation, childEnergy, this.map, this.birthEpoch);		
	}
	
	public void eatPlant(int plantEnergy)
	{
		this.energy += plantEnergy;
		this.energyChanged();
	}
	
	public void newChild()
	{
		this.children++;
	}
	
	public void addPositionObserver(IPositionChangeObserver observer)
	 {
		positionObserver.add(observer);
	 }
	 
	public void removePositionObserver(IPositionChangeObserver observer)
	 {
		positionObserver.remove(observer);
	 }
	 
	public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
	 {
		 for(IPositionChangeObserver obs : positionObserver)
		 {
			 obs.positionChanged(oldPosition, newPosition, this);
		 }
	 }
	
	public void addEnergyObserver(IEnergyChangeObserver observer)
	 {
		energyObserver.add(observer);
	 }
	 
	public void removeEnergyObserver(IEnergyChangeObserver observer)
	 {
		energyObserver.remove(observer);
	 }
	 
	public void energyChanged()
	 {
		 for(IEnergyChangeObserver obs : energyObserver)
		 {
			 obs.energyChanged(this);
		 }
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
	
	public int getChildren() {
		return children;
	}

	public int getLifeExpectancy()
	{
		return deathEpoch-birthEpoch;
	}

	public int getId()
	{
		return id;
	}
	
	@Override
	public String toString() {
		return orientation.toString();
	}

	@Override
	public int compareTo(Animal o) {
		return Comparator.comparing(Animal::getEnergy).reversed().thenComparing(Animal::getId).compare(this, o);
	}
}
