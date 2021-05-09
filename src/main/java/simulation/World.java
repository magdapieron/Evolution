package simulation;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import com.google.gson.Gson;

public class World {
	
    public static void main(String[] args) {
        
		Gson gson = new Gson();
		InitialParameters initialParameters = null;
		
		try (Reader reader = new FileReader("./parameters.json")) 
		{
		// Convert JSON File to Java Object
		initialParameters = gson.fromJson(reader, InitialParameters.class);
		} 
		catch (IOException e) 
		{
	        e.printStackTrace();
	        System.exit(-1);
	    }
    			
		Thread engine1 = new Thread(new Engine(initialParameters));
		Thread engine2 = new Thread(new Engine(initialParameters));
		
		engine1.start();
		engine2.start();  	
    }
}
