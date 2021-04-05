package enums;

import java.util.Random;

import map.Vector2d;

public enum MapDirection {

	NORTH, 
	NORTH_EAST,
	EAST, 
	SOUTH_EAST,
	SOUTH, 
	SOUTH_WEST,
	WEST,
	NORTH_WEST;
	
	public String toString()
	{
		switch(this)
		{
			case NORTH: return "N";
			case NORTH_EAST: return "NE";
			case EAST: return "E";
			case SOUTH_EAST: return "SE";
			case SOUTH: return "S";
			case SOUTH_WEST: return "SW";
			case WEST: return "W";	
			case NORTH_WEST: return "NW";
			default: return "No such direction";
		}			
	}
	
	public MapDirection changeOrientation(int rotation)
	{
		int index = this.ordinal();
		return MapDirection.values()[(index+rotation) % 8];
	}
	
	public MapDirection next()
	{
		int index = this.ordinal();
		return MapDirection.values()[(index+1) % 8];
	}
	
	public MapDirection previous()
	{
		int index = this.ordinal();
		return MapDirection.values()[(index+7) % 8];
	}
	
	public Vector2d toUnitVector()
	{
		switch(this)
		{
			case NORTH: return new Vector2d(0,1);
			case SOUTH: return new Vector2d(0,-1);
			case WEST: return new Vector2d(-1,0);
			case EAST: return new Vector2d(1,0);		
			case NORTH_EAST: return new Vector2d(1,1);
			case SOUTH_EAST: return new Vector2d(1,-1);
			case SOUTH_WEST: return new Vector2d(-1,-1);
			case NORTH_WEST: return new Vector2d(-1,1);
			default: return null;
		}		
	}
	
	// draws initial orientation for the first animals or children 
	public static MapDirection randomOrientation()
	{
		return MapDirection.values()[new Random().nextInt(8)];
	}
}
