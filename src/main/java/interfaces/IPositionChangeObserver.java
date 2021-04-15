package interfaces;

import map.Vector2d;
import objects.Animal;

public interface IPositionChangeObserver {

	/**
	 * Delete pair <oldPosition, animal> from LinkedHashMap and add pair <newPosition, animal>
	 */
	
	void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
