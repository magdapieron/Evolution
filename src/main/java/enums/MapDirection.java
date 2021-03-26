package enums;

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
			case NORTH: return "⭡";
			case NORTH_EAST: return "⭧";
			case EAST: return "⭢";
			case SOUTH_EAST: return "⭨";
			case SOUTH: return "⭣";
			case SOUTH_WEST: return "⭩";
			case WEST: return "⭠";	
			case NORTH_WEST: return "⭦";
			default: return "No such direction";
		}			
	}
	
	public MapDirection changeOrientation(int direction)
	{
		int index = this.ordinal();
		return MapDirection.values()[(index+direction) % 8];
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
}
