package map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import enums.MapDirection;
import objects.Plant;

public class WorldMap extends MapIntegration {
	
	private int width;
	private int height;
	private Map<Vector2d, Plant> plants;
	private Random random = new Random();
	private double jungleRatio;
	private Vector2d mapCenter;
	
	public WorldMap(int width, int height, double jungleRatio)
	{
		this.width = width;
		this.height = height;
		this.plants = new LinkedHashMap<>();
		this.jungleRatio = jungleRatio;
		this.mapCenter = new Vector2d((int) Math.round(width/2), (int) Math.round(height/2));
	}
	
	public Vector2d jungleLowerLeftCorner()
	{		
		int jungleWidth = (int) Math.round(width*jungleRatio);
		int jungleHeight = (int) Math.round(height*jungleRatio);
		return new Vector2d((int)Math.round(mapCenter.x - jungleWidth) , (int)Math.round(mapCenter.y - jungleHeight));
	}
	
	public Vector2d jungleUpperRightCorner()
	{		
		int jungleWidth = (int) Math.round(width*jungleRatio);
		int jungleHeight = (int) Math.round(height*jungleRatio);
		return mapCenter.add(new Vector2d(jungleWidth, jungleHeight));
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
		List<Integer> freePositions = new ArrayList<>();
		
		for(int i=0; i<8; i++)
		{
			if(isOccupied(parentPosition.add(MapDirection.values()[i].toUnitVector())));
			freePositions.add(i);
		}
		
		// if there are free positions, draw of one of them
		if(!freePositions.isEmpty())
		{
			int direction = freePositions.get(random.nextInt(freePositions.size()));
			childPosition = parentPosition.add(MapDirection.values()[direction].toUnitVector());
		}
		// if there is not free position, draw of occupied one
		else
		{
			int direction = random.nextInt(8);
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
				return animals.get(position);
			}
			if(plants.get(position) != null)
			{
				return plants.get(position);
			}				
		}			
		return null;
	}
	
	@Override
	public boolean isOccupied(Vector2d position) {
		
		if(super.isOccupied(position))
			return true;

		return (plants.containsKey(position));
	}
	
	public void removePlant(Plant plant)
	{
		plants.remove(plant.getPosition());
	}
	
	public void setPlant(Plant plant)
	{
		plants.put(plant.getPosition(), plant);
	}
	
	public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
		// TODO Auto-generated method stub
		
	}

}
