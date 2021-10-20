package kab.geneticalgorithm.utils;

public class GeneticAlgorithm {

	// individu parfait a 8 genes, tous a 1
	public static final int[] individu_parfait = {1, 1, 1, 1, 1, 1, 1};
	public static final int taille_selection = 4;
	
	
	// evaluation de la population
	public Population evaluationPopulation (Population pop) {
		return mutationPopulation(croisementPopulation(pop));
	}
	
	// croisement de la population
	private Population croisementPopulation (Population pop) {
		return pop;
	}
	
	// mutation de la population
	private Population mutationPopulation (Population pop) {
		return pop;
	}
	
	// croisement des individus
	private Individu croisementIndividu (Individu individu1, Individu individu2) {
		Individu individuCroise = new Individu(8);
		for (int i=0; i<individu1.getGenes().length; i++) {
			if (Math.random() < 0.5) {
				individuCroise.getGenes()[i] = individu1.getGenes()[i];
			} else {
				individuCroise.getGenes()[i] = individu2.getGenes()[i];
			}
		}
		return individuCroise;
	}
	
	// selection population
	private Population selectionPopulation (Population pop) {
		Population selection = new Population(taille_selection, 8);
		
		return selection;
	}
	
}
