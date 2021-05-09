package animation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// pig clipart png from pngtree.com: https://pngtree.com/so/pig-clipart'
// grass clipart png from pl.pngtree.com: https://pl.pngtree.com/so/trawa-clipart

public class Main extends Application {

    public static void main(String[] args) 
    {
        launch(args);
    }
 
    @Override
    public void start (Stage theStage) throws Exception
    {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("file.fxml"));
    	Scene scene = new Scene(pane);
   		theStage.setScene(scene);
        theStage.setTitle("Evolution!");
   		theStage.show();	
    }
}
