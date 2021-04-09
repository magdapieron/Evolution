package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genotype {

	private final List<Integer> genotype;
	private Random random = new Random();
	
	public Genotype()
	{
		this.genotype = new ArrayList<>();
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
			genotype.add(i, random.nextInt(8));
		}

		completeGenotype(genotype);
	}
	
	private void completeGenotype(List<Integer> genotype)
	{			
		List<Boolean> missing = findMissing();
		int missingGene = 0;
		
		while(missing.contains(false))
		{
			missingGene = missing.indexOf(false);
			genotype.add(random.nextInt(32), missingGene);
			missing = findMissing();
		} 
		Collections.sort(genotype);
	}
	
	private List<Boolean> findMissing()		
	{
		List<Boolean> missing = new ArrayList<>();
		
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
		List<Integer> childGenotype = new ArrayList<>();
		
		int index1 = random.nextInt(32);				// must be divided into 3 groups
		int index2 = random.nextInt(32);
		
		while(index1 == index2)						
			index2 = random.nextInt(32);				
		
		if(index1 > index2)
		{
			int tmp = index1;
			index1 = index2;
			index2 = tmp;
		}
		
		List<Boolean> parents = drawOfGroups();		
		
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
		completeGenotype(childGenotype);
		return new Genotype(childGenotype);
	}
	
	private List<Boolean> drawOfGroups()
	{
		List<Boolean> parents = new ArrayList<>();
		int i=0;
		
		while(i < 3)						// 3 groups of genes, true if from this, false if from other
		{													
			parents.add(i, random.nextBoolean());
			i++;
		}
		
		if(!parents.contains(false))	
		{															
			parents.add(random.nextInt(3), false);
		}
		else if(!parents.contains(true))
		{
			parents.add(random.nextInt(3), true);
		}											
		return parents;					// now we are sure, that child will receive genes after both parents
	}
	
	private List<Integer> donateGenes(int start, int end, Genotype parentGenotype)
	{
		List<Integer> genes = new ArrayList<>();
		while(start <= end)
		{
			genes.add(parentGenotype.genotype.get(start));
			start++;
		}		
		return genes;		
	}
	
	public int drawGene()
	{
		int gene = this.genotype.get(random.nextInt(32));
		return gene;
	}
	
	public List<Integer> getGenotype() {
		return genotype;
	}

	@Override
	public String toString() {
		return "Genotype: " + genotype;
	}
	
}
