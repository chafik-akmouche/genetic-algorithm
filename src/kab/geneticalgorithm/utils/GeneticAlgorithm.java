package kab.geneticalgorithm.utils;

import java.util.Random;

public class GeneticAlgorithm {

	public static final int taille_selection = 2;
	public static final double proba_mutation = 0.25;
	
	Random rand = new Random();
	
	// evaluation de la population
	//public Population evaluationPopulation (Population pop) {
		//return mutationPopulation(croisementPopulation(pop));
	//}
	
	/*
	 * croisement de la population 
	 */
	public Population croisementSimplePopulation (Population pop) {
		Population popSelectionnee = new Population(taille_selection, Main.getTailleIndividu());		
		Population popCroisee = new Population(pop.getIndividus().length, Main.getTailleIndividu());
		
		for (int i=0; i<pop.getIndividus().length; i++) {
			// popSelectionnee = selection2Hasard(pop);        // selectionner 2 individus au hasard
			// popSelectionnee = selection2MeilleurSur5(pop);  // selectionner les 2 meilleurs individus sur 5 choisis au hasard
			popSelectionnee = selection2Meilleur(pop);         // selectionner les 2 meilleurs individus de la population
			Individu individu1 = popSelectionnee.getIndividus()[0];
			Individu individu2 = popSelectionnee.getIndividus()[1];
			
			// croiser les genes des 2 individus selectionnes
			popCroisee.getIndividus()[i] = croisementSimpleIndividu(individu1, individu2);
		}
		
		return popCroisee;
	}
	
	/*
	 * croisement mono-point
	 */
//	public Population croisementMonoPointPopulation (Population pop) {
//		Population parentsSelectionnes = new Population(taille_selection, Main.getTailleIndividu());		
//		Population popCroisee = new Population(pop.getIndividus().length, Main.getTailleIndividu());
//		
//		for (int i=0; i<pop.getIndividus().length; i++) {
//			// parentsSelectionnes = selection2Hasard(pop); // selectionner 2 parents au hasard
//			// parentsSelectionnes = selection2MeilleurSur5(pop);  // selectionner les 2 meilleurs parents sur 5 choisis au hasard
//			parentsSelectionnes = selection2Meilleur(pop);  // selectionner les 2 meilleurs parents de la population
//			Individu individu1 = parentsSelectionnes.getIndividus()[0];
//			Individu individu2 = parentsSelectionnes.getIndividus()[1];
//			
//			// croisement mono-point des 2 parents selectionnes pour creer 2 enfants
//			popCroisee.getIndividus()[i] = croisementIndividu(individu1, individu2);
//		}
//		
//		return popCroisee;
//	}
	
	/*
	 * croisement uniforme
	 */
	public Population croisementUniformePopulation (Population pop) {		
		Population popCroisee = new Population(pop.getIndividus().length, Main.getTailleIndividu());
		Individu individu1;
		Individu individu2;
		
		for (int i=0; i<pop.getIndividus().length; i++) {
			individu1 = selection1Hasard(pop);  // selectionner un individu aleatoirement a partir de la population
			individu2 = new Individu(Main.getTailleIndividu());  // generer un individu avec des genes aleatoires
			for (int k=0; k<individu2.getGenes().length; k++) {  
				individu2.getGenes()[k] = rand.nextInt(2);
			}
			
			popCroisee.getIndividus()[i] = croisementUniformeIndividu(individu1, individu2);
		}
		return popCroisee;
	}

	/*
	 * croisement simple des genes d un individu
	 */
	private Individu croisementSimpleIndividu (Individu individu1, Individu individu2) {
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
	 * croisement uniforme des genes d un individu
	 */
	private Individu croisementUniformeIndividu(Individu individu1, Individu individu2) {
		Individu individuCroise = new Individu(Main.getTailleIndividu());
		for (int i=0; i<individu1.getGenes().length; i++) {
			if (individu2.getGenes()[i] == 1) {
				if (individu1.getGenes()[i] == 1) {
					individuCroise.getGenes()[i] = 0;
				} else {
					individuCroise.getGenes()[i] = 1;
				}
			} else {
				individuCroise.getGenes()[i] = individu1.getGenes()[i];
			}
		}
		return individuCroise;
	}
	
	/*
	 * mutation population 
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
	 * selection 2 individus au hasard
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
	
	/*
	 * selection les 2 meilleurs individus sur 5 choisis aleatoirement
	 */
	private Population selection2MeilleurSur5 (Population pop) {
		Population selection = new Population(5, Main.getTailleIndividu());
		Population popSelectionnee = new Population(taille_selection, Main.getTailleIndividu());
		// selectionner 5 aleatoirement
		for (int i=0; i<5; i++) {
			selection.getIndividus()[i] = pop.getIndividus()[(int)(Math.random()*pop.getIndividus().length)];
		}
		selection.TrierIndividusParFitness();
		for (int i=0; i<taille_selection; i++) {
			popSelectionnee.getIndividus()[i] = selection.getIndividus()[i];
		}
		return popSelectionnee;
	}
	
	/*
	 * selection 2 meilleurs individus de la population
	 */
	private Population selection2Meilleur (Population pop) {
		Population popSelectionnee = new Population(taille_selection, Main.getTailleIndividu());
		pop.TrierIndividusParFitness();
		for (int i=0; i<taille_selection; i++) {
			popSelectionnee.getIndividus()[i] = pop.getIndividus()[i];
		}
		return popSelectionnee;
	}
	
	/*
	 * selection d un individu au hasard
	 */
	private Individu selection1Hasard(Population pop) {
		Individu individuSelectionne = new Individu(Main.getTailleIndividu());
		individuSelectionne = pop.getIndividus()[rand.nextInt(pop.getIndividus().length)];
		return individuSelectionne;
	}
	
}
