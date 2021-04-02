package map;

import java.util.Objects;

public class Vector2d {

	public final int x;
	public final int y;
	
	public Vector2d(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2d (" + x + "," + y + ")";
	}
	
	boolean precedes(Vector2d other) 
	{
		return (this.x <= other.x && this.y <= other.y);
	}
	
	boolean follows(Vector2d other)
	{
		return (this.x >= other.x && this.y >= other.y);
	}
	
	public Vector2d upperRight(Vector2d other)
	{
		int x1;
		int y1;
		if(this.x>=other.x)
			x1 = this.x;
		else
			x1 = other.x;
		
		if(this.y>=other.y)
			y1 = this.y;
		else
			y1 = other.y;
		
		return new Vector2d(x1,y1);
	}
	
	public Vector2d lowerLeft(Vector2d other)
	{
		int x1;
		int y1;
		if(this.x<=other.x)
			x1 = this.x;
		else
			x1 = other.x;
		
		if(this.y<=other.y)
			y1 = this.y;
		else
			y1 = other.y;
		
		return new Vector2d(x1,y1);
	}
	
	public Vector2d add(Vector2d other)
	{
		return new Vector2d(this.x+other.x, this.y+other.y);
	}
	
	public Vector2d subtract(Vector2d other)
	{
		return new Vector2d(this.x-other.x, this.y-other.y);
	}
	
	public Vector2d opposite()
	{	 
		return new Vector2d(this.x*(-1), this.y*(-1));
	}
	
	
	public boolean equals(Object other)
	{
		if (this == other)
		    return true;
		if (!(other instanceof Vector2d))
		    return false;
		Vector2d that = (Vector2d) other;
		if(this.x == that.x && this.y == that.y)	
			return true;
		return false; 
	}
	
	@Override
	public int hashCode() {
	  return Objects.hash(this.x, this.y);
	}
}
