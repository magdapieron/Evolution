package map;

public class Map extends MapInteration {
	
	private int width;
	private int height;
	
	public Map(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
		// TODO Auto-generated method stub
		
	}

}
