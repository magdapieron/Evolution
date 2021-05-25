package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import objects.Animal;
import objects.Genotype;

public class Statistics {
	
	private int numberOfAllAnimals;
	private int numberOfAllPlants;
	private Genotype dominantGenotype;
	private double avgEnergyLevelOfLivingAnimals;
	private double avgLifeExpectancyOfAnimals;
	private double avgNumberOfChildren;
	private int epoch;
	
	public void refreshStatistics(int numberOfAnimals, int numberOfPlants, List<Animal> animals)
	{
		setNumberOfAllAnimals(numberOfAnimals);
		setNumberOfAllPlants(numberOfPlants);
		setDominantGenotype(animals);
		setAvgEnergyLevelOfLivingAnimals(animals);
		setAvgNumberOfChildren(animals); 
		nextEpoch();
	}
	
	private void setNumberOfAllAnimals(int number) 
	{
		this.numberOfAllAnimals = number;
	}
	
	private void setNumberOfAllPlants(int number) 
	{
		this.numberOfAllPlants = number;
	}
	
	private void setDominantGenotype(List<Animal> animals)
	{
		Map<Genotype,Integer> genotypes = new HashMap<>();
		
		for(Animal animal : animals)
		{
			Genotype genotype = animal.getGenotype();
			Integer ctr = genotypes.get(genotype);
			if(ctr == null)
				genotypes.put(genotype, 1);
			else
			{
				ctr = genotypes.get(genotype)+1;
				genotypes.put(genotype, ctr);
			}
		}
		
		 Map.Entry<Genotype, Integer> maxEntry = null;
	        for (Entry<Genotype, Integer> entry : genotypes.entrySet()) 
	        {
	            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) 
	            {
	                maxEntry = entry;
	            }
	        }
	        this.dominantGenotype = maxEntry.getKey();
	}
	
	private void setAvgEnergyLevelOfLivingAnimals(List<Animal> animals) 
	{
		int sumOfEnergy = 0;
		
		for(Animal animal : animals)
			sumOfEnergy += animal.getEnergy();
		
		this.avgEnergyLevelOfLivingAnimals = (double)sumOfEnergy/animals.size();
	}
	
	private double setAvgLifeExpectancyOfAnimals(List<Animal> animals) 
	{
		int lifeExpectancy = 0;
		for(Animal animal : animals)
		{
			lifeExpectancy += animal.getLifeExpectancy();
		}
		return (double)lifeExpectancy/animals.size();
	}
	
	public void addDeadAnimals(List<Animal> animals)
	{
		this.avgLifeExpectancyOfAnimals += setAvgLifeExpectancyOfAnimals(animals);
	}
	
	private void setAvgNumberOfChildren(List<Animal> animals) 
	{
		int sumOfChildren = 0;
		
		for(Animal animal : animals)
		{
			sumOfChildren += animal.getChildren();
		}
		
		this.avgNumberOfChildren = (double)sumOfChildren/animals.size();
	}
	
	private void nextEpoch()
	{
		this.epoch++;
	}
	
	public int getNumberOfAllAnimals(){
		return numberOfAllAnimals;
	}
	
	public int getNumberOfAllPlants() {
		return numberOfAllPlants;
	}
	
	public Genotype getDominantGenotype() {
		return dominantGenotype;
	}
	
	public double getAvgEnergyLevelOfLivingAnimals() {
		return avgEnergyLevelOfLivingAnimals;
	}
	
	public double getAvgLifeExpectancyOfAnimals() {
		return avgLifeExpectancyOfAnimals;
	}
	
	public double getAvgNumberOfChildren() {
		return avgNumberOfChildren;
	}
	
	public int getEpoch(){
		return epoch;
	}
}
