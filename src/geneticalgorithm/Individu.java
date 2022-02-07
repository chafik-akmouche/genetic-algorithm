package geneticalgorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author Chafik
 *
 */
public class Individu {
	private int[] genes;
	private int fitness;
	private boolean fitnessChange = true;

	Random rand = new Random();

	// constructeur
	public Individu(int taille_individu) {
		genes = new int[taille_individu];
	}

	/**
	 * initialiation des genes d un individu aleatoirement
	 * 
	 * @return
	 */
	public Individu initialiationIndividu() {
		for (int i = 0; i < genes.length; i++) {
			genes[i] = 0;// rand.nextInt(2);
		}
		return this;
	}

	/**
	 * afficher le contenu du tableau genes
	 */
	public String toString() {
		return Arrays.toString(this.genes);
	}

	/**
	 * calcul de la fitness d'un individu
	 * 
	 * @return
	 */
	public int fitness() {
		int fitnessIndividu = 0;
		for (int i = 0; i < genes.length; i++) {
			if (genes[i] == 1) {
				fitnessIndividu++;
			}
		}
		return fitnessIndividu;
	}

	/**
	 * get genes
	 * 
	 * @return tab
	 */
	public int[] getGenes() {
		fitnessChange = true;
		return genes;
	}

	/**
	 * get fitness
	 * 
	 * @return int
	 */
	public int getFitness() {
		if (fitnessChange) {
			fitness = fitness();
			fitnessChange = false;
		}
		return fitness;
	}

}
