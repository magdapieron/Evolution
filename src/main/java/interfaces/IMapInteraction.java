package interfaces;

import map.Vector2d;
import objects.Animal;

public interface IMapInteraction {
		
		boolean canMoveTo(Vector2d position);

		 /**
	    * Indicate if any object can move to the given position.
	    *
	    * @param position The position checked for the movement possibility.
	    *            
	    * @return True if the object can move to that position.
	    */

		 boolean placeAnimal(Animal animal);
	   
	    /**
	    * Place a animal on the map.
	    *
	    * @param animal  The animal to place on the map.
	    * @return True if the animal was placed.
	    */
	   
	   boolean isOccupied(Vector2d position);  
	    /**
	    * Return true if given position on the map is occupied. 
	    *
	    * @param position  Position to check.
	    * @return True if the position is occupied.
	    */

	   Object objectAt(Vector2d position);
	   
	    /**
	    * Return an object at a given position.
	    *
	    * @param position The position to check.
	    * @return Object or null if the position is not occupied.
	    */

}
