package simulation;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Stage;

public class World /*extends Application*/ {

	public static void main(String args[]) {
		
		Gson gson = new Gson();
		
		try (Reader reader = new FileReader("./parameters.json")) {

		// Convert JSON File to Java Object
		InitialParameters initialParameters = gson.fromJson(reader, InitialParameters.class);
	        
		// print initialParameters object
		System.out.println(initialParameters.height);

	   } 
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
//	@Override
//	public void start(Stage stage) throws Exception {
//		stage.setTitle("Evolutionary Generator");
//        Group root = new Group();
//        Scene theScene = new Scene(root);
//        stage.setScene(theScene);
//        Canvas canvas = new Canvas(600, 600);
//        root.getChildren().add(canvas);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//
//        new AnimationTimer() {
//            public void handle(long currentNanoTime) {
//                gc.setFill(Color.GREEN);
//                gc.fillRect(50, 50, 500, 500);
//                gc.setFill(Color.DARKGREEN);
//                gc.fillRect(250, 250, 100, 100);
//            }
//        }.start();
//
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
	
}
