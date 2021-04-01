package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genotype {

	private final List<Integer> genotype;
	
	public Genotype()
	{
		this.genotype = new ArrayList<Integer>();
		generateGenotype();	
	}
	
	// receives an array with parents' genes, created by createGenotype method 
	public Genotype(List<Integer> parentsGenotype)
	{
		this.genotype = parentsGenotype;
	}
		
	// generates a genotype for the first animals on the map	
	private void generateGenotype()
	{
		for(int i=0; i<32; i++)
		{
			genotype.add(i, new Random().nextInt(8));
		}

		constainsAll(genotype);
	}
	
	private void constainsAll(List<Integer> genotype)
	{			
		List<Boolean> missing = findMissing(genotype);
		int missingGen = 0;
		
		while(missing.contains(false))
		{
			missingGen = missing.indexOf(false);
			genotype.add(new Random().nextInt(32), missingGen);
			missing = findMissing(genotype);
		} 
		Collections.sort(genotype);
	}
	
	List<Boolean> findMissing(List<Integer> genotype)
	{
		List<Boolean> missing = new ArrayList<Boolean>();
		
		for(int i=0; i<8; i++)					
		{
			if(genotype.contains(i))		
				missing.add(i, true);
			else
				missing.add(i, false);
		}
		return missing;
	}
	
	public Genotype createGenotype( Genotype other)
	{
		List<Integer> childGenotype = new ArrayList<Integer>();
		
		int index1 = new Random().nextInt(32);
		int index2 = new Random().nextInt(32);
		
		while(index1 == index2)
			index2 = new Random().nextInt(32);				// must be divided into 3 groups
		
		if(index1 > index2)
		{
			int tmp = index1;
			index1 = index2;
			index2 = tmp;
		}
		
		List<Boolean> parents = null;
		int i=0;
		
		while(i != 2)										// 3 groups of genes, true if from this, false if from other
		{													
			parents.add(i, new Random().nextBoolean());
			i++;
		}
		
		if(!parents.contains(false))								// maybe can do this better?
		{															
			parents.add(new Random().nextInt(3), false);
		}
		else if(!parents.contains(true))
		{
			parents.add(new Random().nextInt(3), true);
		}															// now we are sure, that there's child will recive genes after
																	// both parents
		
		for(int j=0; j<3; j++)
		{
			Genotype parentGenotype = null;
			if(parents.get(j))
				 parentGenotype = this;
			else
				parentGenotype = other;
			
			switch(j)
			{
			case 0: childGenotype.addAll(donateGenes(0, index1, parentGenotype));
			break;
			case 1: childGenotype.addAll(donateGenes(index1+1, index2, parentGenotype));
			break;
			case 2: childGenotype.addAll(donateGenes(index2+1, 31, parentGenotype));
			break;
			}
		}				
		Collections.sort(childGenotype);
		constainsAll(childGenotype);
		return new Genotype(childGenotype);
	}
	
	List<Integer> donateGenes(int start, int end, Genotype parentGenotype)
	{
		List<Integer> genes = new ArrayList<Integer>();
		while(start <= end)
		{
			genes.add(parentGenotype.genotype.get(start));
			start++;
		}		
		return genes;		
	}
	
	
	@Override
	public String toString() {
		return "Genotype: " + genotype;
	}
	
}
