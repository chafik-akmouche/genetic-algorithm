package kab.geneticalgorithm.utils;

import java.util.Arrays;
import java.util.Random;

public class Individu {
	private int[] genes;
	private int fitness;
	private boolean fitnessChange = true;
	
	Random rand = new Random();
	
	// constructeur
	public Individu (int taille_individu) {
		genes = new int[taille_individu];
	}
	
	// initialiation des genes d un individu aleatoirement
	public Individu initialiationIndividu() {
		for (int i=0; i<genes.length; i++) {  
			genes[i] = 0;//rand.nextInt(2);
		}
		return this;
	}
	
	// pour afficher le contenu du tableau genes
	public String toString () {
		return Arrays.toString(this.genes);
	}
	
	// calcul de la fitness d'un individu
	public int fitness () {
		int fitnessIndividu = 0;
		for (int i=0; i<genes.length; i++) {
			if (genes[i] == 1) {
				fitnessIndividu++;
			}
		}		
		return fitnessIndividu;
	}

	
	// getters
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
