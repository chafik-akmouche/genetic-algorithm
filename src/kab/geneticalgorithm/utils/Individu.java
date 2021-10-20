package kab.geneticalgorithm.utils;

import java.util.Arrays;

public class Individu {
	private int[] genes;
	private int fitness;
	private boolean fitnessChange = true;
	
	// individu (chromosome) = enemble de genes
	public Individu (int taille_individu) {
		genes = new int[taille_individu];
	}
	
	// initialiation des genes d un individu a 0
	public Individu initialiationIndividu() {
		for (int i=0; i<genes.length; i++) {
			genes[i] = 0;
		}
		return this;
	}
	
	// pour imprimer le contenu du tableau genes
	public String toString () {
		return Arrays.toString(this.genes);
	}
	
	// calcul de la fitness
	public int fitness () {
		int fitnessIndividu = 0;
		for (int i=0; i<genes.length; i++) {
			if (genes[i] == 1) {
				fitnessIndividu++;
			}
		}		
		return fitnessIndividu;
	}

	// getters & setters
	public int[] getGenes() {
		fitnessChange = true;
		return genes;
	}

	public int getFitness() {
		if (fitnessChange) {
			fitness = fitness();
			fitnessChange = false;
		}
		return fitness;
	}

}
