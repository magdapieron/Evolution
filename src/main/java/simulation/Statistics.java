package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import objects.Animal;
import objects.Genotype;

public class Statistics {

	private static List<String> allStatisitc;

	private int numberOfAllAnimals;
	private int numberOfAllPlants;
	private Genotype dominantGenotype;
	private double avgEnergyLevelOfLivingAnimals;
	private double avgLifeExpectancyOfAnimals;
	private double avgNumberOfChildren;
	private int epoch = 0;
	private List<Animal> deadAnimal;

	public Statistics()
	{
		allStatisitc = new ArrayList<>();
		this.deadAnimal = new ArrayList<>();
	}

	public void refreshStatistics(int numberOfAnimals, int numberOfPlants, List<Animal> animals)
	{
		setNumberOfAllAnimals(numberOfAnimals);
		setNumberOfAllPlants(numberOfPlants);
		setDominantGenotype(animals);
		setAvgEnergyLevelOfLivingAnimals(animals);
		setAvgLifeExpectancyOfAnimals();
		setAvgNumberOfChildren(animals);
		nextEpoch();
		addStatistics();
	}

	private void addStatistics()
	{
		if(dominantGenotype == null)
			allStatisitc.add(epoch-1, "Epoch: " + epoch + " Avg animals number: " + numberOfAllAnimals +
				" Avg animals number: " + numberOfAllAnimals + " Dominant genotype: -" + " Avg energy level: " +
				avgEnergyLevelOfLivingAnimals + " Avg lifespan: " + avgLifeExpectancyOfAnimals + " Avg children number: "
				+ avgNumberOfChildren);
		else
			allStatisitc.add(epoch-1, "Epoch: " + epoch + " Avg animals number: " + numberOfAllAnimals +
					" Avg animals number: " + numberOfAllAnimals + " Dominant genotype: " + dominantGenotype + " Avg energy level: " +
					avgEnergyLevelOfLivingAnimals + " Avg lifespan: " + avgLifeExpectancyOfAnimals + " Avg children number: "
					+ avgNumberOfChildren);
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
	        if(maxEntry != null && maxEntry.getValue() > 1)
	        	this.dominantGenotype = maxEntry.getKey();
	        else
	        	this.dominantGenotype = null;
	}
	
	private void setAvgEnergyLevelOfLivingAnimals(List<Animal> animals) 
	{
		int sumOfEnergy = 0;
		
		for(Animal animal : animals)
			sumOfEnergy += animal.getEnergy();

		this.avgEnergyLevelOfLivingAnimals = (double)Math.round((double)sumOfEnergy/animals.size()*100)/100;
	}

	private void setAvgLifeExpectancyOfAnimals()
	{
		if(this.deadAnimal.size() != 0 && this.deadAnimal != null)
		{
			int lifeExpectancy = 0;
			for(Animal animal : deadAnimal)
			{
				lifeExpectancy += animal.getLifeExpectancy();
			}
			this.avgLifeExpectancyOfAnimals = (double)lifeExpectancy/deadAnimal.size();
		}
		else
			this.avgLifeExpectancyOfAnimals = 0;
	}
	
	public void addDeadAnimals(List<Animal> animals)
	{
		this.deadAnimal = animals;
	}
	
	private void setAvgNumberOfChildren(List<Animal> animals)
	{
		int sumOfChildren = 0;
		
		for(Animal animal : animals)
			sumOfChildren += animal.getChildren();

		this.avgNumberOfChildren = (double)Math.round((double)sumOfChildren/animals.size()*100)/100;
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

	public List<String> getAllStatisitc() {
		return allStatisitc;
	}
}
