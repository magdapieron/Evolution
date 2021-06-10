package animation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import map.Jungle;
import map.Vector2d;
import objects.Animal;
import objects.Plant;
import simulation.Engine;
import simulation.InitialParameters;
import simulation.Statistics;
import java.util.ArrayList;
import java.util.List;

public class MapController implements Jungle {

    private final Engine engine;
    private final Image veryHealthyPig = new Image("/VeryHealthyPig.png", 20, 20, true, true);
    private final Image healthyPig = new Image("/HealthyPig.png", 20, 20, true, true);
    private final Image sickPig = new Image("/SickPig.png", 20, 20, true, true);
    private final Image almostDeadPig = new Image("/AlmostDeadPig.png", 20, 20, true, true);
    private final Image grass= new Image("/Grass.png", 20, 20, true, true);
    private final int height;
    private final int width;
    private final Vector2d jungleLL;
    private final Vector2d jungleUR;
    private GraphicsContext gc;
    private boolean canMark = true;

    public  MapController (InitialParameters initialParameters)
    {
        this.engine = new Engine(initialParameters);
        engine.setController(this);
        this.height = engine.getInitialParameters().getHeight()*20;
        this.width = engine.getInitialParameters().getWidth()*20;
        Vector2d mapCenter = new Vector2d((int) Math.round(width / 2), (int) Math.round(height / 2));
        this.jungleLL = jungleLowerLeftCorner(width, height, engine.getInitialParameters().getJungleRatio(), mapCenter);
        this.jungleUR = jungleUpperRightCorner(width, height, engine.getInitialParameters().getJungleRatio(), mapCenter);
        Thread simulation1 = new Thread(engine);
        simulation1.start();
    }

//    @FXML
//    Button markDominant;

    @FXML
    Canvas animation;

    @FXML
    Canvas background;

    @FXML
    TextArea mapStat;

    @FXML
    TextArea animalStat;

    @FXML
    public void startStop(ActionEvent event) {
        engine.stopStart();
        this.canMark = !canMark;
    }


    @FXML
    public void markAnimal(MouseEvent event)        // to repair
    {
        if(canMark)
        {   // if on clicked position is more than one animal, then get it with the biggest energy
            List<Animal> animalsAtPosition = new ArrayList<>();
//            Image image = event.
            double x = event.getX();
            double y = event.getY();
            System.out.println("1: " + x + " " + y + "\n");

            Vector2d position = new Vector2d((int)x/20, (int)y/20);
            System.out.println("2: " + position + "\n");
            Animal clikcedAnimal = null;
            for(Animal animal : engine.getAnimals())
            {
                if(animal.getPosition().equals(position))
                    animalsAtPosition.add(animal);
            }
            if(animalsAtPosition.size() != 0)
            {
                animalsAtPosition.sort(Animal::compareTo);
                clikcedAnimal = animalsAtPosition.get(0);
                System.out.println(clikcedAnimal + " " + clikcedAnimal.getPosition() + " " + x + " " + y);
            }
            System.out.println(" po: " + x + " " + y);
        }
    }

    @FXML
    public void markDominant(ActionEvent event)
    {
        if(canMark)
        {

        }
    }

    public void createMap()
    {
        GraphicsContext back = background.getGraphicsContext2D();
        back.setFill(Color.YELLOWGREEN);
        back.fillRect(0, 0, width,height);
        back.setFill(Color.GREEN);
        back.fillRect(jungleLL.x,jungleLL.y, jungleUR.x-jungleLL.x, jungleUR.y-jungleLL.y);
        back.setFill(Color.BLACK);
        back.setLineWidth(3);
        back.strokeLine(0, 0,0 ,height);
        back.strokeLine(0, 0,width ,0);
        back.strokeLine(width ,0,width,height);
        back.strokeLine(0, height,width ,height);
    }

    public void initialize() {
        this.gc = animation.getGraphicsContext2D();
        animation.setHeight(height);
        animation.setWidth(width);
        background.setHeight(height);
        background.setWidth(width);
        createMap();
        nextDay(engine.getAnimals(), engine.getPlants(), engine.getStatistics());
    }

    private void updateAnimalsOnMap(List<Animal> animals)
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

    private void updatePlantsOnMap(List<Plant> plants)
    {
        for(Plant plant : plants) {
            gc.drawImage(grass, plant.getPosition().x*20, plant.getPosition().y*20);
        }
    }

    private void updateStat(Statistics statistics)
    {
        mapStat.clear();
        mapStat.appendText("Number of animals: " + statistics.getNumberOfAllAnimals() + "\n");
        mapStat.appendText("Number of plants: " + statistics.getNumberOfAllPlants() + "\n");
        mapStat.appendText("Dominant genotype: " + "\n" + statistics.getDominantGenotype() + "\n");
        mapStat.appendText("Avg energy level of living animals: " + statistics.getAvgEnergyLevelOfLivingAnimals() + "\n");
        mapStat.appendText("Avg life expectancy of animals: " + statistics.getAvgLifeExpectancyOfAnimals() + "\n");
        mapStat.appendText("Avg number of children: " + statistics.getAvgNumberOfChildren() + "\n");
    }

    public void nextDay(List<Animal> animals, List<Plant> plants, Statistics stat)
    {
        gc.clearRect(0,0,width, height);
        updateAnimalsOnMap(animals);
        updatePlantsOnMap(plants);
        updateStat(stat);
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


