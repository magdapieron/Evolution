package animation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulation.Engine;
import simulation.InitialParameters;

public class Main extends Application {
// pig clipart png from pngtree.com: https://pngtree.com/so/pig-clipart'
// grass clipart png from pl.pngtree.com: https://pl.pngtree.com/so/trawa-clipart
	
	private InitialParameters initialParameters;
	
	public Main(InitialParameters initialParameters)
	{
		this.initialParameters = initialParameters;
	}
	
	public static void main(String[] args) 
    {
        launch(args);
    }
 
    @Override
    public void start (Stage theStage) throws Exception
    {
		Thread engine1 = new Thread(new Engine(initialParameters));
		Thread engine2 = new Thread(new Engine(initialParameters));
		
		engine1.start();
		engine2.start();
    	
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("file.fxml"));
    	Scene scene = new Scene(pane);
   		theStage.setScene(scene);
        theStage.setTitle("Evolution!");
   		theStage.show();	
    }
}
