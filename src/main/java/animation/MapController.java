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
import objects.Genotype;
import objects.Plant;
import simulation.Engine;
import simulation.InitialParameters;
import simulation.Statistics;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MapController implements Jungle {

    private final Engine engine;
    private final Image veryHealthyPig = new Image("/VeryHealthyPig.png", 20, 20, true, true);
    private final Image healthyPig = new Image("/HealthyPig.png", 20, 20, true, true);
    private final Image sickPig = new Image("/SickPig.png", 20, 20, true, true);
    private final Image almostDeadPig = new Image("/AlmostDeadPig.png", 20, 20, true, true);
    private final Image trackedPig = new Image("/TrackedPig.png", 20, 20, true, true);
    private final Image pigWithDominantGenotype = new Image("/pigWithDominantGenotype.png", 20, 20, true, true);
    private final Image grass= new Image("/Grass.png", 20, 20, true, true);
    private final int height;
    private final int width;
    private final Vector2d jungleLL;
    private final Vector2d jungleUR;
    private GraphicsContext gc;
    GraphicsContext back;
    private boolean canMark = true;
    private boolean isTracked = false;

    public  MapController (InitialParameters initialParameters)
    {
        this.engine = new Engine(initialParameters);
        engine.setController(this);
        this.height = engine.getInitialParameters().getHeight()*20;
        this.width = engine.getInitialParameters().getWidth()*20;
        Vector2d mapCenter = new Vector2d(Math.round(width / 2), Math.round(height / 2));
        this.jungleLL = jungleLowerLeftCorner(width, height, engine.getInitialParameters().getJungleRatio(), mapCenter);
        this.jungleUR = jungleUpperRightCorner(width, height, engine.getInitialParameters().getJungleRatio(), mapCenter);
        Thread simulation1 = new Thread(engine);
        simulation1.start();
    }

    @FXML
    Canvas animation;

    @FXML
    Canvas background;

    @FXML
    TextArea mapStat;

    @FXML
    TextArea animalStat;

    @FXML
    public void startStop() {
        engine.stopStart();
        this.canMark = !canMark;
    }

    @FXML
    public void markAnimal(MouseEvent event)
    {   // can only mark one animal when animation is stopped
        if(canMark)
        {
            Vector2d position = new Vector2d((int)event.getX()/ 20, (int)event.getY()/ 20);
            if (!isTracked)
            {   // if on clicked position is more than one animal, then get it with the biggest energy
                List<Animal> animalsAtPosition = new ArrayList<>();

                for (Animal animal : engine.getAnimals())
                {
                    if (animal.getPosition().equals(position))
                        animalsAtPosition.add(animal);
                }
                if (animalsAtPosition.size() != 0)
                {
                    animalsAtPosition.sort(Animal::compareTo);
                    engine.setTrackedAnimal(animalsAtPosition.get(0));
                    markAnimal(animalsAtPosition.get(0));
                    this.isTracked = true;
                }
            }
            else if (this.isTracked && engine.getTrackedAnimal().getAnimal().getPosition().equals(position))
            {
                uncheckTracking();
            }
        }
    }

    @FXML
    public void markDominant()
    {
        if(canMark)
        {
            Genotype genotype = engine.getStatistics().getDominantGenotype();
            if(genotype != null)
            {
                for(Animal animal : engine.getAnimals())
                {
                    if(animal.getGenotype().equals(genotype))
                        gc.drawImage(pigWithDominantGenotype, animal.getPosition().x*20, animal.getPosition().y*20);
                }
            }
        }
    }

    @FXML
    private void saveStat()
    {
        try
        {
            FileWriter stat = new FileWriter("src/main/resources/" + engine.getStatistics().getEpoch() + "Stat.txt");
            PrintWriter line = new PrintWriter(stat);
            for(String statistics : engine.getStatistics().getAllStatisitc())
            {
                line.println(statistics);
            }
            line.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void uncheckTracking()
    {
        engine.setNoTrackedAnimal();
        this.isTracked = false;
        for(Animal animal : engine.getAnimals())
        {
            animal.setTracked(false);
            animal.setTrackedAncestor(false);
        }
        updateAnimalsOnMap(engine.getAnimals());
    }

    public void createMap()
    {
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
        this.back = background.getGraphicsContext2D();
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
            if(!animal.isTracked())
                drawAnimal(animal);
            else
                markAnimal(animal);
        }
    }

    private void markAnimal(Animal animal)
    {
        gc.drawImage(trackedPig, animal.getPosition().x*20, animal.getPosition().y*20);
    }

    private void drawAnimal(Animal animal)
    {
        Vector2d position = animal.getPosition();
        int energy = animal.getEnergy();
        if(energy > 75)
            gc.drawImage(veryHealthyPig, position.x*20, position.y*20);
        else if(energy > 50 && energy <= 75)
            gc.drawImage(healthyPig, position.x*20, position.y*20);
        else if(energy > 25 && energy <= 50)
            gc.drawImage(sickPig, position.x*20, position.y*20);
        else if(energy > 0 && energy <= 25)
            gc.drawImage(almostDeadPig, position.x*20, position.y*20);
    }

    private void updatePlantsOnMap(List<Plant> plants)
    {
        for(Plant plant : plants) {
            gc.drawImage(grass, plant.getPosition().x*20, plant.getPosition().y*20);
        }
    }

    private void updateMapStat(Statistics statistics)
    {
        mapStat.clear();
        mapStat.appendText("Epoch: " + statistics.getEpoch() + "\n");
        mapStat.appendText("Number of animals: " + statistics.getNumberOfAllAnimals() + "\n");
        mapStat.appendText("Number of plants: " + statistics.getNumberOfAllPlants() + "\n");
        mapStat.appendText("Dominant genotype: " + "\n" + statistics.getDominantGenotype() + "\n");
        mapStat.appendText("Avg energy level of living animals: " + statistics.getAvgEnergyLevelOfLivingAnimals() + "\n");
        mapStat.appendText("Avg life expectancy of animals: " + statistics.getAvgLifeExpectancyOfAnimals() + "\n");
        mapStat.appendText("Avg number of children: " + statistics.getAvgNumberOfChildren() + "\n");
    }

    private void updateTrackedAnimalStat()
    {
        Animal animal = engine.getTrackedAnimal().getAnimal();
        animalStat.clear();
        animalStat.appendText("Position: " + animal.getPosition() + "\n");
        animalStat.appendText("Genotype: " + animal.getGenotype() + "\n");
        animalStat.appendText("Birth epoch: " + animal.getBirthEpoch() + "\n");
        animalStat.appendText("Energy: " + animal.getEnergy() + "\n");
        if(animal.isDead())
            animalStat.appendText("Death epoch : " + animal.getDeathEpoch() + "\n");
        animalStat.appendText("New children: " + engine.getTrackedAnimal().getNumberOfNewChildren() + "\n");
        animalStat.appendText("New ancestors: " + engine.getTrackedAnimal().getNumberOfNewAncestor() + "\n");
    }

    public void nextDay(List<Animal> animals, List<Plant> plants, Statistics stat)
    {
        gc.clearRect(0,0,width, height);
        updateAnimalsOnMap(animals);
        updatePlantsOnMap(plants);
        updateMapStat(stat);
        if(engine.getTrackedAnimal().getAnimal() != null)
        {
            // if tracked animal is dead, can select a new one, but until then display the statistics of the deceased
            if(this.isTracked && engine.getTrackedAnimal().getAnimal().isDead())
            {
                updateTrackedAnimalStat();
                this.isTracked = false;
            }
            else
                updateTrackedAnimalStat();
        }
    }
}


