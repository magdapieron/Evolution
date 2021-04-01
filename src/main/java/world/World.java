package world;

import java.util.Random;

import enums.MapDirection;
import interfaces.IMapInteraction;
import map.Map;
import map.Vector2d;
import objects.Animal;
import objects.Genotype;

public class World {

	public static void main(String args[]) {
		
//		int[] geny = new int[32];
//		int randomGen = 0;
//		
//		for(int i=0; i<32; i++)
//		{
//			Random r = new Random();
//			randomGen = r.nextInt(8);
//			geny[i] = randomGen;
//		}	
//		for(int i=0; i<32; i++)
//			System.out.println(geny[i]);
	
		Genotype genotype = new Genotype();
		Vector2d position = null;
		int energy = 0;
		Map map = null;
		MapDirection orientation = null;
		
		Animal animal = new Animal(genotype, position, orientation, energy, map);
		animal.getGenotype();
		System.out.println(animal.getGenotype());
	}
}
