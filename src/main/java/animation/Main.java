package animation;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulation.Engine;
import simulation.InitialParameters;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class Main extends Application {
// pig clipart png from pngtree.com: https://pngtree.com/so/pig-clipart'
// grass clipart png from pl.pngtree.com: https://pl.pngtree.com/so/trawa-clipart

    private static InitialParameters initialParameters;

	public static void main(String[] args)
    {
        Gson gson = new Gson();
        initialParameters = null;

        try (Reader reader = new FileReader("./parameters.json"))
        {
            // Convert JSON File to Java Object
            initialParameters = gson.fromJson(reader, InitialParameters.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        initialParameters.checkParameters();

        launch(args);
    }
 
    @Override
    public void start (Stage theStage) throws Exception
    {
        Engine engine1 = new Engine(initialParameters);
        Engine engine2 = new Engine(initialParameters);
        Controller controller = new Controller(engine1);
        Controller controller2 = new Controller(engine2);
        engine1.setController(controller);
        engine1.setController(controller);
        Thread simulation1 = new Thread(engine1);
        Thread simulation2 = new Thread(engine2);
        System.out.println("start!");
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/file.fxml"));
        fxml.setController(controller);

        AnchorPane pane = fxml.load();
    	Scene scene = new Scene(pane);
   		theStage.setScene(scene);
        theStage.setTitle("Evolution!");
   		theStage.show();

//		simulation1.start();
//		simulation2.start();
    }

//	private class Simulation implements  Runnable
//    {
//        private Controller controller;
//
//        public Simulation(Controller controller)
//        {
//            this.controller = controller;
//        }
//
//        Engine engine = new Engine(initialParameters);
//
//        @Override
//        public void run()
//        {
//            while(engine.areAliveAnimals())
//            {
//                if(running)
//                {
//                    try
//                    {
//                        Thread.sleep(100);
//                        Platform.runLater(() -> engine.nextDay());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    public void setInitialParameters(InitialParameters initialParameters) {
        this.initialParameters = initialParameters;
    }
}
