package objects;

import enums.MapDirection;
import interfaces.IMapInteraction;
import map.Vector2d;
import map.Map;

public class Animal implements IMapInteraction{

	private Genotype genotype;
	private Vector2d position;
	private int energy;
	private Map map;
	private MapDirection orientation;
	
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
	
	public void move(int direction)		// losowanie pozycji na podstawie genotypu, na pozniej
	{	
		orientation.changeOrientation(direction);
	}
	
	public Animal reproduction(Animal other)
	{		
		return null;		
	}

	public boolean canMoveTo(Vector2d position) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean placeAnimal(Animal animal) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isOccupied(Vector2d position) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object objectAt(Vector2d position) {
		// TODO Auto-generated method stub
		return null;
	}
}
