package kab.geneticalgorithm.utils;

public class GeneticAlgorithm {

	//public static final int nb_individu_parfait = 1;
	public static final int taille_selection = 2;
	public static final double proba_mutation = 0.25;
	
	// evaluation de la population
	//public Population evaluationPopulation (Population pop) {
		//return mutationPopulation(croisementPopulation(pop));
	//}
	
	/*
	 * croisement de la population 
	 */
	public Population croisementPopulation (Population pop) {
		Population popCroisee = new Population(pop.getIndividus().length, Main.getTailleIndividu());
		for (int i=0; i<pop.getIndividus().length; i++) {
			Individu individu1 = selection2Hasard(pop).getIndividus()[0]; // selectionner 2 individus au hasard et prendre le 1er
			Individu individu2 = selection2Hasard(pop).getIndividus()[0]; // selectionner 2 autres individus au hasard et prendre le 1er
			popCroisee.getIndividus()[i] = croisementIndividu(individu1, individu2); // croiser les genes des 2 individus selectionnes
		}
		
		return popCroisee;
	}
	
	/*
	 * croisement des genes d un individu
	 * reste a faire: croisement mono-point, croisement uniforme ...
	 */
	private Individu croisementIndividu (Individu individu1, Individu individu2) {
		Individu individuCroise = new Individu(Main.getTailleIndividu());
		for (int i=0; i<individu1.getGenes().length; i++) {
			if (Math.random() < 0.5) {
				individuCroise.getGenes()[i] = individu1.getGenes()[i];
			} else {
				individuCroise.getGenes()[i] = individu2.getGenes()[i];
			}
		}
		return individuCroise;
	}
	
	/*
	 * mutation population
	 * cette methode permet de parcourir tous les individus existants et appeler la methode de mutation individu 
	 */
	public Population mutationPopulation (Population pop) {
		Population popMutee = new Population(pop.getIndividus().length, Main.getTailleIndividu());
		for (int i=0; i<pop.getIndividus().length; i++) {
			popMutee.getIndividus()[i] = mutationIndividu(pop.getIndividus()[i]);
		}
		return popMutee;
	}
	
	/*
	 * mutation individu
	 * cette methode permet de faire la mutation pour un individu selon une probabilite
	 */
	private Individu mutationIndividu (Individu individu) {
		Individu individuMute = new Individu(individu.getGenes().length);
		for (int i=0; i<individu.getGenes().length; i++) {
			if (Math.random() < proba_mutation) {
				if (Math.random() < 0.5) {
					individuMute.getGenes()[i] = 1;
				} else {
					individuMute.getGenes()[i] = 0;
				}
			} else {
				individuMute.getGenes()[i] = individu.getGenes()[i];
			}
		}
		return individuMute;
	}
	
	/*
	 * selection 2 au hasard
	 * cette methode selectionne 2 (taille_selection) individus aleatoirement parmis tous les individus exitants
	 * reste a faire : selection 2 sur 5,  selection 2 meilleurs sur 5 ...
	 */
	private Population selection2Hasard (Population pop) {
		Population popSelectionnee = new Population(taille_selection, Main.getTailleIndividu());
		for (int i=0; i<taille_selection; i++) {
			popSelectionnee.getIndividus()[i] = pop.getIndividus()[(int)(Math.random()*pop.getIndividus().length)];
		}
		// trier par ordre de fitness la population selectionnee
		popSelectionnee.TrierIndividusParFitness();
		return popSelectionnee;
	}
	
}
