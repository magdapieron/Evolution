package objects;

import map.Vector2d;

public class Plant {

	private Vector2d position;
	
	Plant(Vector2d position)
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
