package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genotype {

	private final List<Integer> genotype;
	private final Random random = new Random();
	
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
		int missingGene;
		
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
		
		if(index1 >= index2)
		{
			int tmp = index2;
			index2 = index1;
			index1 = tmp;
		}				

		boolean[] parents = drawOfGroups();		
		
		for(int i=0; i<3; i++)
		{
			Genotype parentGenotype;
			if(parents[i])
				 parentGenotype = this;
			else
				parentGenotype = other;

			switch (i) {
				case 0 -> childGenotype.addAll(donateGenes(0, index1, parentGenotype));
				case 1 -> childGenotype.addAll(donateGenes(index1 + 1, index2, parentGenotype));
				case 2 -> childGenotype.addAll(donateGenes(index2 + 1, 31, parentGenotype));
			}
		}				
		Collections.sort(childGenotype);
		completeGenotype(childGenotype);
		return new Genotype(childGenotype);
	}
	
	private boolean[] drawOfGroups()
	{
		boolean[] parents = new boolean[3];
		int i = random.nextInt(3);				// 3 groups of genes, true if from this, false if from other
		Arrays.fill(parents, false);
		
		parents[i] = true;						
										
		return parents;	
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
		return this.genotype.get(random.nextInt(32));
	}
	
	public List<Integer> getGenotype() {
		return genotype;
	}

	@Override
	public String toString() {
		String gen = String.valueOf(genotype).replaceAll("\\s+", "");
		return gen.replaceAll(",", "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genotype == null) ? 0 : genotype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Genotype)) {
			return false;
		}
		Genotype other = (Genotype) obj;
		if (genotype == null) {
			if (other.genotype != null) {
				return false;
			}
		} else return genotype.equals(other.genotype);
		return true;
	}
	
}
