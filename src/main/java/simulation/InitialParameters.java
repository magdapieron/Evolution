package simulation;

public class InitialParameters {

	 private int width;
	 private int height;
	 private int startEnergy;
	 private int moveEnergy;
	 private int plantEnergy;
	 private int numberOfFirstAnimals; 
	 private double jungleRatio;
	  
	public void checkParameters() 
	{
		if(width > 23 || height > 23)
			throw new IllegalArgumentException("The map can be up to 23x23! Change parameters!");

		if(jungleRatio >= 0.5)
			throw new IllegalArgumentException("JungleRatio must be lower than 0.5! Change parameters!"); //  steppes cover most of the world
			
		int jungleWidth = (int) Math.floor(width*jungleRatio);
		int jungleHeight = (int) Math.floor(height*jungleRatio);
		
		if(jungleHeight <= 0 || jungleWidth <= 0)
			throw new IllegalArgumentException("There's no Jungle! Change parameters!");
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getStartEnergy() {
		return startEnergy;
	}

	public int getMoveEnergy() {
		return moveEnergy;
	}

	public int getPlantEnergy() {
		return plantEnergy;
	}

	public int getNumberOfFirstAnimals() {
		return numberOfFirstAnimals;
	}

	public double getJungleRatio() {
		return jungleRatio;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setStartEnergy(int startEnergy) {
		this.startEnergy = startEnergy;
	}

	public void setMoveEnergy(int moveEnergy) {
		this.moveEnergy = moveEnergy;
	}

	public void setPlantEnergy(int plantEnergy) {
		this.plantEnergy = plantEnergy;
	}

	public void setNumberOfFirstAnimals(int numberOfFirstAnimals) {
		this.numberOfFirstAnimals = numberOfFirstAnimals;
	}

	public void setJungleRatio(double jungleRatio) {
		this.jungleRatio = jungleRatio;
	}
}
