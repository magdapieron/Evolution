package objects;

import interfaces.IMapElement;
import map.Vector2d;

public class Plant implements IMapElement{

	private Vector2d position;
	
	public Plant(Vector2d position)
	{
		this.position = position;
	}
	
	public Vector2d getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		return "*";
	}
}
