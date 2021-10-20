package kab.geneticalgorithm.utils;

import java.util.Arrays;

public class Test {
	public static final int TAILLE_POPULATION = 10;
	public static final int TAILLE_INDIVIDU = 8;
	public static int NOMBRE_ITERATION = 10;
	
	public static void main(String[] args) {
		Population pop = new Population(TAILLE_POPULATION, TAILLE_INDIVIDU).initialiationPopulation();
		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		while (NOMBRE_ITERATION >= 0 /*|| pop.getIndividus()[0].getFitness() != pop.getIndividus().length*/) {			
			pop = ga.evaluationPopulation(pop);
			pop.TrierIndividusParFitness();
			System.out.println("------------- Iteration " + NOMBRE_ITERATION + " -------------");
			affichagePpulation(pop);
			
			NOMBRE_ITERATION--;
		}
	}
	
	public static void affichagePpulation (Population pop) {
		for (int i=0; i<pop.getIndividus().length; i++) {
			System.out.println("Individu "+ i + " : " + 
					Arrays.toString(pop.getIndividus()[i].getGenes()) + 
					" Fitness = " + pop.getIndividus()[i].getFitness());
		}
	}
}
