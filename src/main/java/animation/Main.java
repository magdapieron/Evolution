package animation;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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
        MapController controller1 = new MapController(initialParameters);
        MapController controller2 = new MapController(initialParameters);

        HBox hbox = new HBox();
        FXMLLoader fxml1 = new FXMLLoader(getClass().getResource("/map.fxml"));
        FXMLLoader fxml2 = new FXMLLoader(getClass().getResource("/map.fxml"));
        fxml1.setController(controller1);
        fxml2.setController(controller2);
        hbox.getChildren().addAll(fxml1.load(), (Parent)fxml2.load());

        Scene scene = new Scene(hbox);
   		theStage.setScene(scene);
        theStage.setTitle("Evolution!");
   		theStage.show();
    }
}
