package simulation;

import java.util.ArrayList;
import java.util.List;
import animation.MapController;
import enums.MapDirection;
import javafx.application.Platform;
import map.Jungle;
import map.Vector2d;
import map.WorldMap;
import objects.Animal;
import objects.Plant;
import objects.TrackedAnimal;

public class Engine implements  Runnable, Jungle {

	private final InitialParameters initialParameters;
	private final WorldMap map;
	private final List<Plant> plants;
	private final List<Animal> animals;
	private final Statistics statistics;
	private TrackedAnimal trackedAnimal;
	private MapController controller;
	private boolean running;

	public Engine(InitialParameters initialParameters) {
		this.initialParameters = initialParameters;
		this.map = new WorldMap(initialParameters.getWidth(), initialParameters.getHeight());
		this.plants = new ArrayList<>();
		this.animals = new ArrayList<>();
		this.statistics = new Statistics();
		this.trackedAnimal = new TrackedAnimal();
		this.running = false;
		addFirstAnimals(initialParameters.getNumberOfFirstAnimals());
		statistics.refreshStatistics(animals.size(), plants.size(), animals);
	}

	private void addFirstAnimals(int numberOfFirstAnimals) {
		for (int i = 0; i < numberOfFirstAnimals; i++) {
			if (numberOfFirstAnimals > initialParameters.getHeight() * initialParameters.getWidth())
				throw new IllegalArgumentException("Too much initial animals!");
			Vector2d position;
			do {
				position = map.randomPosition(initialParameters.getWidth(), 0, initialParameters.getHeight(), 0);
			}
			while (map.isOccupied(position));
			Animal animal = new Animal(position, MapDirection.randomOrientation(), initialParameters.getStartEnergy(), this.map, 1);
			animals.add(animal);
			this.map.placeAnimal(animal);
		}
	}

	private void removeDeadAnimals() {
		List<Animal> animalsToRemove = new ArrayList<>();
		for (Animal animal : animals) {
			if (animal.isDead()) {
				animal.setDeathEpoch(statistics.getEpoch());
				this.map.removeDeadAnimal(animal.getPosition(), animal);
				animalsToRemove.add(animal);
			}
		}
		for (Animal animal : animalsToRemove) {
			animals.remove(animal);
		}
		statistics.addDeadAnimals(animalsToRemove);
	}

	private void moveAnimals() {
		for (Animal animal : animals)
			animal.move(initialParameters.getMoveEnergy());
	}

	private void eating() {
		List<Plant> plantsToRemove = new ArrayList<>();
		for (Plant plant : plants) {
			List<Animal> listAnimalsToFeed = map.getAnimalsToFeed(plant.getPosition());
			for (Animal animal : listAnimalsToFeed) {
				animal.eatPlant(initialParameters.getPlantEnergy() / listAnimalsToFeed.size());
			}
			if (!listAnimalsToFeed.isEmpty()) {
				this.map.removePlant(plant);
				plantsToRemove.add(plant);
			}
		}

		for (Plant plantToRemove : plantsToRemove) {
			plants.remove(plantToRemove);
		}
	}

	private void reproduceAnimals() {
		List<List<Animal>> listAnimalsToReproduce = map.getPairsAnimalsToReproduce(initialParameters.getStartEnergy());
		if (!listAnimalsToReproduce.isEmpty()) {
			for (List<Animal> parents : listAnimalsToReproduce) {
				Animal child = parents.get(0).reproduction(parents.get(1), statistics.getEpoch());
				animals.add(child);
				this.map.placeAnimal(child);

				if(parents.get(0).isTracked() || parents.get(1).isTracked())
				{
					trackedAnimal.newChildren();
					child.setTrackedAncestor(true);
				}

				if(parents.get(0).isTrackedAncestor() || parents.get(1).isTrackedAncestor())
				{
					trackedAnimal.newAncestor();
					child.setTrackedAncestor(true);
				}
			}
		}
	}

	private void addNewPlants() {
		addPlantToJungle();
		addPlantToSteppe();
	}

	private void addPlantToJungle() {
		Vector2d rightCorner = jungleUpperRightCorner(initialParameters.getWidth(), initialParameters.getHeight(), getInitialParameters().getJungleRatio(), map.getMapCenter());
		Vector2d leftCorner = jungleLowerLeftCorner(initialParameters.getWidth(), initialParameters.getHeight(), getInitialParameters().getJungleRatio(), map.getMapCenter());
		int jungleSurface = (rightCorner.x - leftCorner.x) * (rightCorner.y - leftCorner.y);
		int ctr = 0;
		Vector2d position;

		do {
			position = map.randomPosition(rightCorner.x, leftCorner.x, rightCorner.y, leftCorner.y);
			ctr++;
		}
		while (map.isOccupied(position) && ctr < jungleSurface);

		if (ctr <= jungleSurface) {
			Plant newPlant = new Plant(position);
			plants.add(newPlant);
			this.map.setPlant(newPlant);
		}
	}

	private void addPlantToSteppe() {
		Vector2d rightCorner = jungleUpperRightCorner(initialParameters.getWidth(), initialParameters.getHeight(), getInitialParameters().getJungleRatio(), map.getMapCenter());
		Vector2d leftCorner = jungleLowerLeftCorner(initialParameters.getWidth(), initialParameters.getHeight(), getInitialParameters().getJungleRatio(), map.getMapCenter());
		int steppeSurface = initialParameters.getHeight() * initialParameters.getWidth() - (rightCorner.x - leftCorner.x) * (rightCorner.y - leftCorner.y);
		int ctr = 0;
		Vector2d position;

		do {
			position = map.randomPosition(initialParameters.getWidth(), 0, initialParameters.getHeight(), 0);
			ctr++;
		}
		while (map.isOccupied(position) && ctr < steppeSurface && (position.y <= leftCorner.y || position.y >= rightCorner.y ||
				position.x <= leftCorner.x || position.x >= rightCorner.x));

		if (ctr <= steppeSurface) {
			Plant newPlant = new Plant(position);
			plants.add(newPlant);
			this.map.setPlant(newPlant);
		}
	}

	public void nextDay() {
		removeDeadAnimals();
		moveAnimals();
		eating();
		reproduceAnimals();
		addNewPlants();
		statistics.refreshStatistics(animals.size(), plants.size(), animals);
		controller.nextDay(animals, plants, statistics);
	}

	public boolean areAliveAnimals() {
		return animals.size() > 0;
	}

	public void stopStart() {
		running = !running;
	}

	@Override
	public void run() {
		while (areAliveAnimals()) {
			if (running) {
				try {
					Thread.sleep(600);
					Platform.runLater(() -> nextDay());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setController(MapController controller) {
		this.controller = controller;
	}

	public InitialParameters getInitialParameters() {
		return initialParameters;
	}

	public List<Plant> getPlants() {
		return plants;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public TrackedAnimal getTrackedAnimal() {
		return trackedAnimal;
	}

	public void setTrackedAnimal(Animal animal) {
		this.trackedAnimal.newTrackedAnimal(animal);
	}

	public void setNoTrackedAnimal()
	{
		this.trackedAnimal = new TrackedAnimal();
	}
}
