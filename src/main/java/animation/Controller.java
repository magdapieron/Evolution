package animation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import map.Jungle;
import map.Vector2d;
import objects.Animal;
import objects.Plant;
import simulation.Engine;
import simulation.Statistics;
import java.util.List;

public class Controller implements Jungle {
//
	private final Engine engine;
	private final Image veryHealthyPig = new Image("/VeryHealthyPig.png", 20, 20, true, true);
	private final Image healthyPig = new Image("/HealthyPig.png", 20, 20, true, true);
	private final Image sickPig = new Image("/SickPig.png", 20, 20, true, true);
	private final Image almostDeadPig = new Image("/AlmostDeadPig.png", 20, 20, true, true);
	private final Image grass= new Image("/Grass.png", 20, 20, true, true);
	private int height;
	private int width;
	private Vector2d mapCenter;
	private Vector2d jungleLL;
	private  Vector2d jungleUR;
	private GraphicsContext gc1;
	private GraphicsContext gc2;

	public  Controller(Engine engine)
	{
		this.engine = engine;
		this.height = engine.getInitialParameters().getHeight()*20;
		this.width = engine.getInitialParameters().getWidth()*20;
		this.mapCenter = new Vector2d((int) Math.round(width/2), (int) Math.round(height/2));
		this.jungleLL = jungleLowerLeftCorner(width, height, engine.getInitialParameters().getJungleRatio(), mapCenter);
		this.jungleUR = jungleUpperRightCorner(width, height, engine.getInitialParameters().getJungleRatio(), mapCenter);
	}

	@FXML
	Canvas map1;

	@FXML
	Canvas background1;

	@FXML
	Canvas background2;

	@FXML
	Canvas  map2;

	@FXML
	Button startStop1;

	@FXML
	Button startStop2;

	@FXML
	public void startStop(ActionEvent event) {
		engine.stopStart();
	}

	public void createMap()
	{
		GraphicsContext back1 = background1.getGraphicsContext2D();
//		GraphicsContext back2 = background2.getGraphicsContext2D();
		back1.setFill(Color.YELLOWGREEN);
		back1.fillRect(0, 0, width,height);
		back1.setFill(Color.GREEN);
		back1.fillRect(jungleLL.x,jungleLL.y, jungleUR.x-jungleLL.x, jungleUR.y-jungleLL.y);
//		back2.setFill(Color.YELLOWGREEN);
//		back2.fillRect(0, 0, width,height);
//		back2.setFill(Color.GREEN);
//		back2.fillRect(jungleLL.x,jungleLL.y, jungleUR.x-jungleLL.x, jungleUR.y-jungleLL.y);
	}

	public void initialize() {
		this.gc1 = map1.getGraphicsContext2D();
		map1.setHeight(height);
		map1.setWidth(width);
//		map2.setHeight(height);
//		map2.setWidth(width);
		background1.setHeight(height);
		background1.setWidth(width);
//		background2.setHeight(height);
//		background2.setWidth(width);
		createMap();
		nextDay(engine.getAnimals(), engine.getPlants(), engine.getStatistics());
	}

	private void updateAnimalsOnMap(List<Animal> animals, GraphicsContext gc)
	{
		for(Animal animal : animals)
		{
			int energy = animal.getEnergy();
			Vector2d position = animal.getPosition();
			if(energy > 75)
				gc.drawImage(veryHealthyPig, position.x*20, position.y*20);
			else if(energy > 50 && energy <= 75)
				gc.drawImage(healthyPig, position.x*20, position.y*20);
			else if(energy > 25 && energy <= 50)
				gc.drawImage(sickPig, position.x*20, position.y*20);
			else if(energy > 0 && energy <= 25)
				gc.drawImage(almostDeadPig, position.x*20, position.y*20);
		}
	}

	private void updatePlantsOnMap(List<Plant> plants, GraphicsContext gc)
	{
		for(Plant plant : plants) {
			gc.drawImage(grass, plant.getPosition().x*20, plant.getPosition().y*20);
		}
	}

	private void updateStat(Statistics statistics, GraphicsContext gc)
	{

	}

	public void nextDay(List<Animal> animals, List<Plant> plants, Statistics stat)
	{
		gc1.clearRect(0,0,width, height);
		updateAnimalsOnMap(animals, gc1);
		updatePlantsOnMap(plants, gc1);
		updateStat(stat, gc1);
	}

////	private void saveStat(Button button)
////	{
////		try
////		{
////			FileWriter stat = new FileWriter("Map1stat.txt");
////			stat.write("Map2");
////			stat.close();
////		}
////		catch (IOException ex)
////		{
////			ex.printStackTrace();
////		}
////	}
}


