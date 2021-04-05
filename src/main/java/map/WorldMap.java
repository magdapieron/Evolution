package map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import enums.MapDirection;
import objects.Plant;

public class WorldMap extends MapInteration {
	
	private int width;
	private int height;
	private Map<Vector2d, Plant> steppes;
	
	public WorldMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.steppes = new LinkedHashMap<Vector2d, Plant>();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2d randomPositionForChild(Vector2d parentPosition)
	{
		Vector2d childPosition = null;
		List<Integer> freePositions = new ArrayList<Integer>();
		
		for(int i=0; i<8; i++)
		{
			if(isOccupied(parentPosition.add(MapDirection.values()[i].toUnitVector())));
			freePositions.add(i);
		}
		
		// if there are free positions, draw of one of them
		if(freePositions.size() != 0)
		{
			int direction = freePositions.get(new Random().nextInt(freePositions.size()));
			childPosition = parentPosition.add(MapDirection.values()[direction].toUnitVector());
		}
		// if there is not free position, draw of occupied one
		else
		{
			int direction = freePositions.get(new Random().nextInt(8));
			childPosition = parentPosition.add(MapDirection.values()[direction].toUnitVector());
		}
		return childPosition;
	}
	
	public Object objectAt(Vector2d position) 
	{
		if(isOccupied(position))  
		{
			if(animals.get(position) != null)
			{
				Object obj = animals.get(position);
				return obj;
			}
			if(steppes.get(position) != null)
			{
				Object obj = steppes.get(position);
				return obj;
			}				
			if(animals.get(position) != null)
			{
				Object obj = animals.get(position);
				return obj;
			}
		}			
		return null;
	}
	
	@Override
	public boolean isOccupied(Vector2d position) {
		
		if(super.isOccupied(position))
			return true;

		return (steppes.containsKey(position));
	}
	
	public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
		// TODO Auto-generated method stub
		
	}

}
