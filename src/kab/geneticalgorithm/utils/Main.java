package kab.geneticalgorithm.utils;

import java.util.Arrays;

public class Main {
	public static final int TAILLE_POPULATION = 10;
	public static final int TAILLE_INDIVIDU = 8;
	public static final int NOMBRE_ITERATION = 10;
	
	public static void main(String[] args) {
		Population pop = new Population(TAILLE_POPULATION, TAILLE_INDIVIDU).initialiationPopulation();
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		int nb_iteration = 0;
		while (nb_iteration < NOMBRE_ITERATION && pop.getIndividus()[0].getFitness() != TAILLE_INDIVIDU ) {			
			nb_iteration++;
			
			pop = GA.croisementPopulation(pop);
			pop = GA.mutationPopulation(pop);
			
			//pop = GA.evaluationPopulation(pop);
			pop.TrierIndividusParFitness();
			System.out.println("------------------ Generation " + nb_iteration + " -----------------");
			affichagePpulation(pop);
		}
	}
	
	// methode pour affichage de la population
	public static void affichagePpulation (Population pop) {
		for (int i=0; i<pop.getIndividus().length; i++) {
			System.out.println("Individu "+ i + " : " + 
					Arrays.toString(pop.getIndividus()[i].getGenes()) + 
					" Fitness = " + pop.getIndividus()[i].getFitness());
		}		
	}

	// getter
	public static int getTailleIndividu() {
		return TAILLE_INDIVIDU;
	}
}
