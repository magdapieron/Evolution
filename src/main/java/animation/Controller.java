package animation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import objects.Animal;
import objects.Plant;
import simulation.Engine;
import simulation.Statistics;
import java.util.List;

public class Controller {
//
	private final Engine engine;

	public  Controller(Engine engine)
	{
		this.engine = engine;
	}

	@FXML
	TableView<Image>  map1;

	@FXML
	TableView<Image>  map2;

	@FXML
	Button startStop1;

	@FXML
	Button startStop2;

	@FXML
	public void startStop(ActionEvent event) {
		engine.stopStart();
		if(engine.isRunning())
			System.out.println("Start!!");
		else
			System.out.println("Stop!!");
	}

	public void createMap()
	{
		int height = engine.getInitialParameters().getHeight()*10;
		int width = engine.getInitialParameters().getWidth()*10;
		map1.setPrefHeight(height);
		map1.setPrefWidth(width);
		map2.setPrefHeight(height);
		map2.setPrefWidth(width);
		for(int i=0; i<height; i++)
		{

		}
	}

	public void initialize() {
		//map.getColumns().setVisible(false).
		createMap();
		map1.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, null, null)));
		map2.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, null, null)));

	}

	private void updateAnimals(List<Animal> animals)
	{

	}

	private void updatePlant(List<Plant> plants)
	{

	}

	private void updateStat(Statistics statistics)
	{

	}

	public void nextDay()
	{
		updateAnimals(engine.getAnimals());
		updatePlant(engine.getPlants());
		updateStat(engine.getStatistics());
	}

////	private void saveStat(Button button)
////	{
////		try
////		{
////			if(button == button1)
////			{
////				FileWriter stat = new FileWriter("Map1stat.txt");
////				stat.write("Map2");
////				stat.close();
////			}
////			else if(button == button2)
////			{
////				FileWriter stat = new FileWriter("Map2stat.txt");
////				stat.write("Map1");
////				stat.close();
////			}
////		} catch (IOException ex)
////		{
////			ex.printStackTrace();
////		}
////	}
}
