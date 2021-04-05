package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GenotypeTest {

	@Test
	public void generateGenotypeTest()
	{
		List<Integer> genotype = new Genotype().getGenotype();
		
		assertEquals(32, genotype.size());
		
		for(int i=0; i<8; i++)
		{
			assertTrue(genotype.contains(i));
		}
		
		for(int gene : genotype)
		{
			assertTrue(gene >= 0 && gene <= 7);
		}
	}
	
	@Test 
	public void createGenotypeTest()
	{
		Genotype genotype1 = new Genotype();
		Genotype genotype2 = new Genotype();
		
		List<Integer> genotype = genotype1.createGenotype(genotype2).getGenotype();
		
		assertEquals(32, genotype.size());
		
		for(int i=0; i<8; i++)
		{
			assertTrue(genotype.contains(i));
		}
		
		for(int gene : genotype)
		{
			assertTrue(gene >= 0 && gene <= 7);
		}
	}
	
	@Test
	public void drawGeneTest()
	{
		Genotype genotype = new Genotype();
		int gene = genotype.drawGene();
		assertTrue(gene >= 0 && gene <=7);
	}

}
