//package simulation;
//
//import animation.Main;
//import com.google.gson.Gson;
//import javafx.application.Application;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//
//public class World {
//
//    public static void main(String[] args) {
//
//		Gson gson = new Gson();
//		InitialParameters initialParameters = null;
//
//		try (Reader reader = new FileReader("./parameters.json"))
//		{
//		// Convert JSON File to Java Object
//		initialParameters = gson.fromJson(reader, InitialParameters.class);
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//			System.exit(-1);
//		}
//		initialParameters.checkParameters();
////		Main.main(args);
//
//    }
//}
