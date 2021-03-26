package objects;

import enums.MapDirection;
import interfaces.IMapInteraction;
import map.Vector2d;

public class Animal {

	private Genotype genotype;
	private Vector2d position;
	private int energy;
	private IMapInteraction map;
	private MapDirection orientation;
	
	Animal(Genotype genotyp, Vector2d initialPosition, MapDirection initialOrientation, int energy, IMapInteraction map )
	{
		this.genotype = genotyp;
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

	@Override
	public String toString() {
		return orientation.toString();
	}
	
	public void move(int direction)		// losowanie pozycji na podstawie genotypu, na pozniej
	{	
		orientation.changeOrientation(direction);
	}
}
