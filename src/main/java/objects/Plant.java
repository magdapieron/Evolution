package objects;

import interfaces.IMapElement;
import map.Vector2d;

public class Plant implements IMapElement{

	private final Vector2d position;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Plant)) {
			return false;
		}
		Plant other = (Plant) obj;
		if (position == null) {
			return other.position == null;
		} else return position.equals(other.position);
	}
}
