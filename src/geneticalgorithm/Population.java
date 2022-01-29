package geneticalgorithm;

import java.util.Arrays;

public class Population {
	private Individu[] individus;
	private int taille_individu;
	
	/**
	 * constructeur
	 * @param taille_population
	 * @param taille_individu
	 */
	public Population(int taille_population, int taille_individu) {
		this.taille_individu = taille_individu;
		// creation de la population
		individus = new Individu[taille_population];
	}
	
	/**
	 * initialiation de la population
	 * @return
	 */
	public Population initialiationPopulation () {
		for (int i=0; i<individus.length; i++) {
			// creation des individu de notre population + initialiation
			individus[i] = new Individu(taille_individu).initialiationIndividu();
		}
		// trier les individu par fitness
		TrierIndividusParFitness();
		return this;
	}

	/**
	 * metode pour trier les individus par fitness
	 */
	public void TrierIndividusParFitness() {
		Arrays.sort(individus, (individu1, individu2) -> {
			int flag = 0;
			if (individu1.getFitness() > individu2.getFitness()) {
				flag = -1;
			} else if (individu1.getFitness() < individu2.getFitness()) {
				flag = 1;
			}
		return flag;
		});
	}

	/**
	 * 
	 * @return individu (tab)
	 */
	public Individu[] getIndividus() {
		return individus;
	}
}
