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
		return switch (this) {
			case NORTH -> "N";
			case NORTH_EAST -> "NE";
			case EAST -> "E";
			case SOUTH_EAST -> "SE";
			case SOUTH -> "S";
			case SOUTH_WEST -> "SW";
			case WEST -> "W";
			case NORTH_WEST -> "NW";
		};
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
		return switch (this) {
			case NORTH -> new Vector2d(0, 1);
			case SOUTH -> new Vector2d(0, -1);
			case WEST -> new Vector2d(-1, 0);
			case EAST -> new Vector2d(1, 0);
			case NORTH_EAST -> new Vector2d(1, 1);
			case SOUTH_EAST -> new Vector2d(1, -1);
			case SOUTH_WEST -> new Vector2d(-1, -1);
			case NORTH_WEST -> new Vector2d(-1, 1);
		};
	}
	
	// draws initial orientation for the first animals or children 
	public static MapDirection randomOrientation()
	{
		return MapDirection.values()[new Random().nextInt(8)];
	}
}
