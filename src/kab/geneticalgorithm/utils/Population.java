package kab.geneticalgorithm.utils;

import java.util.Arrays;

public class Population {
	private Individu[] individus;
	private int taille_individu;
	
	// population = ensemble d individus
	// taille_population = taille_individu 
	// jai utilise la meme taille: nbr dindividu dans une pop = nbr de genes dans un individu
	public Population(int taille_population, int taille_individu) {
		this.taille_individu = taille_individu;
		individus = new Individu[taille_population]; // pop a taille_population individu
	}
	
	// initialiation de la population
	public Population initialiationPopulation () {
		for (int i=0; i<individus.length; i++) {
			individus[i] = new Individu(taille_individu).initialiationIndividu();
							   // individu = 8 genes
		}
		
		TrierIndividusParFitness();
		return this;
	}

	// Trier les individus par fitness
	public void TrierIndividusParFitness() {
		Arrays.sort(individus, (individu1, individu2) -> {
			int flag = 0;
			if (individu1.getFitness() > individu1.getFitness()) {
				flag = -1;
			} else if (individu1.getFitness() < individu1.getFitness()) {
				flag = 1;
			}
		return flag;
		});
	}

	// getters & setters
	public Individu[] getIndividus() {
		return individus;
	}
	
	
}
