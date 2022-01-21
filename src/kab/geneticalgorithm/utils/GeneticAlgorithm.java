package kab.geneticalgorithm.utils;

import java.util.Random;

/**
 * 
 * @author Chafik
 *
 */
public class GeneticAlgorithm {

	public static final int taille_selection = 2;
	public static final double proba_mutation = 0.25;
	
	Random rand = new Random();

	/**
	 * Croisement population
	 * @param pop
	 * @param selection
	 * @param remplacement
	 * @return pop
	 */
	public Population croisementSimplePopulation (Population pop, String selection, String remplacement) {
		//System.out.println("### Croisement simple");
		Population popSelectionnee = new Population(taille_selection, Main.TAILLE_INDIVIDU);		
		Population popCroisee = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		
		// initialisation de la nouvelle population issue du croisement
		for (int i=0; i<pop.getIndividus().length; i++) {
			popCroisee.getIndividus()[i] = pop.getIndividus()[i];
		}		
		for (int i=0; i<pop.getIndividus().length; i++) {
			switch (selection) {
				case "selection2Hasard": popSelectionnee = selection2Hasard(pop);
				break;
				case "selection2Meilleur": popSelectionnee = selection2Meilleur(pop);
				break;
				case "selection2MeilleurSur5": popSelectionnee = selection2MeilleurSur5(pop);
				break;
			}
			Individu individu1 = popSelectionnee.getIndividus()[0];
			Individu individu2 = popSelectionnee.getIndividus()[1];
			
			switch (remplacement) {
				case "remplacement1Mauvais": popCroisee = remplacement1Mauvais (popCroisee, croisementSimpleIndividu(individu1, individu2));
				break;
			}
		}		
		return popCroisee;
	}

	/**
	 * croisement mono-point
	 * @param pop
	 * @param selection
	 * @param remplacement
	 * @return pop croisée
	 */
	public Population croisementMonoPoint (Population pop, String selection, String remplacement) {
		//System.out.println("### Croisement mono-point.");
		Population parentsSelectionnes = new Population(2, Main.TAILLE_INDIVIDU);
		Population popCroisee = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		
		// initialisation de la nouvelle population issue du croisement
		for (int i=0; i<pop.getIndividus().length; i++) {
			popCroisee.getIndividus()[i] = pop.getIndividus()[i];
		}
		for (int i=0; i<pop.getIndividus().length; i++) {
			switch (selection) {
				case "selection2Hasard": parentsSelectionnes = selection2Hasard(pop);
				break;
				case "selection2Meilleur": parentsSelectionnes = selection2Meilleur(pop);
				break;
				case "selection2MeilleurSur5": parentsSelectionnes = selection2MeilleurSur5(pop);
				break;
			}
			Individu parent1 = parentsSelectionnes.getIndividus()[0];
			Individu parent2 = parentsSelectionnes.getIndividus()[1];
			// affichage
			//System.out.println("Parents selectionnes");
			//Launcher.affichagePpulation(parentsSelectionnes);			
			switch (remplacement) {
				case "remplacement2Mauvais": popCroisee = remplacement2Mauvais (popCroisee, croisementMonoPointParent (parent1, parent2).getIndividus()[0], croisementMonoPointParent (parent1, parent2).getIndividus()[1]);
				break;
				case "remplacement2Meilleur": popCroisee = remplacement2Meilleur (popCroisee, croisementMonoPointParent (parent1, parent2).getIndividus()[0], croisementMonoPointParent (parent1, parent2).getIndividus()[1]);
				break;
			}
		}		
		return popCroisee;
	}

	/**
	 * croisement uniforme
	 * @param pop
	 * @param selection
	 * @param remplacement
	 * @return pop croisée
	 */
	public Population croisementUniformePopulation (Population pop, String selection, String remplacement) {
		//System.out.println("### Croisement uniforme");
		Population popCroisee = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		// initialisation de la nouvelle population issue du croisement
		for (int i=0; i<pop.getIndividus().length; i++) {
			popCroisee.getIndividus()[i] = pop.getIndividus()[i];
		}
		Individu individu1 = null;
		Individu individu2;
		
		for (int i=0; i<pop.getIndividus().length; i++) {
			switch (selection) {
				case "selection1Hasard": individu1 = selection1Hasard(pop);
				break;
				case "selection1Meilleur": individu1 = selection1Meilleur(pop);
				break;
			}
			individu2 = new Individu(Main.TAILLE_INDIVIDU);  // generer un individu avec des genes aleatoires
			for (int k=0; k<individu2.getGenes().length; k++) {  
				individu2.getGenes()[k] = rand.nextInt(2);
			}	
			switch (remplacement) {
				case "remplacement1Mauvais": popCroisee = remplacement1Mauvais (popCroisee, croisementUniformeIndividu(individu1, individu2));
				break;
			}
		}
		return popCroisee;
	}

	/**
	 * croisement simple des genes d un individu
	 * @param individu1
	 * @param individu2
	 * @return individu croisé
	 */
	private Individu croisementSimpleIndividu (Individu individu1, Individu individu2) {
		//System.out.println("### croisement simple ...");
		Individu individuCroise = new Individu(Main.TAILLE_INDIVIDU);
		for (int i=0; i<individu1.getGenes().length; i++) {
			if (Math.random() < 0.5) {
				individuCroise.getGenes()[i] = individu1.getGenes()[i];
			} else {
				individuCroise.getGenes()[i] = individu2.getGenes()[i];
			}
		}
		return individuCroise;
	}
	
	/**
	 * croisement mono-point de 2 parents -> naissance de 2 enfants
	 * @param parent1
	 * @param parent2
	 * @return enfant
	 */
	private Population croisementMonoPointParent(Individu parent1, Individu parent2) {
		//System.out.println("### Croisement mono-point ...");
		// les 2 enfants issus du croisement mono-point
		Population enfants = new Population(2, Main.TAILLE_INDIVIDU);
		Individu enfantCroise1 = new Individu(Main.TAILLE_INDIVIDU);
		Individu enfantCroise2 = new Individu(Main.TAILLE_INDIVIDU);
		// enfant 1
		for (int i=0; i<Main.TAILLE_INDIVIDU/2; i++) {
			enfantCroise1.getGenes()[i] = parent1.getGenes()[i];
		}
		for (int j=Main.TAILLE_INDIVIDU/2; j<Main.TAILLE_INDIVIDU; j++) {
			enfantCroise1.getGenes()[j] = parent2.getGenes()[j];
		}
		// enfant 2
		for (int k=0; k<Main.TAILLE_INDIVIDU/2; k++) {
			enfantCroise2.getGenes()[k] = parent2.getGenes()[k];
		}
		for (int z=Main.TAILLE_INDIVIDU/2; z<Main.TAILLE_INDIVIDU; z++) {
			enfantCroise2.getGenes()[z] = parent1.getGenes()[z];
		}
		enfants.getIndividus()[0] = enfantCroise1;
		enfants.getIndividus()[1] = enfantCroise2;
		
		return enfants;
	}
	
	/**
	 * croisement uniforme des genes d un individu
	 * @param individu1
	 * @param individu2
	 * @return individu croisé
	 */
	private Individu croisementUniformeIndividu(Individu individu1, Individu individu2) {
		//System.out.println("### Croisement uniforme des 2 individus ...");
		Individu individuCroise = new Individu(Main.TAILLE_INDIVIDU);
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
	
	/**
	 * mutation population 
	 * @param pop
	 * @param operateurMutation
	 * @return pop mutée
	 */
	public Population mutationPopulation (Population pop, String operateurMutation) {
		Population popMutee = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		for (int i=0; i<pop.getIndividus().length; i++) {
			switch(operateurMutation) {
			case "mutation1Flip":
				popMutee.getIndividus()[i] = mutation1FlipIndividu(pop.getIndividus()[i]);
				break;
			case "mutation2Flip":
				popMutee.getIndividus()[i] = mutationBitFlipIndividu(pop.getIndividus()[i]);
				break;
			case "mutation3flip":
				popMutee.getIndividus()[i] = mutationKFlipIndividu(pop.getIndividus()[i], 3);
				break;
			case "mutation5flip":
				popMutee.getIndividus()[i] = mutationKFlipIndividu(pop.getIndividus()[i], 5);
				break;
			}
		}
		return popMutee;
	}
	
	/**
	 * mutation 1-flip : selectionner aleatoirement 1 gêne et le muter
	 * @param individu
	 * @return individu
	 */
	private Individu mutation1FlipIndividu (Individu individu) {
		//System.out.println("### Mutation 1-flip ...");
		//Individu individuMute = new Individu(individu.getGenes().length);
		int gene = rand.nextInt(individu.getGenes().length);
		if (Math.random() < proba_mutation) {
			if(individu.getGenes()[gene] == 1) {
				individu.getGenes()[gene] = 0;
			} else {
				individu.getGenes()[gene] = 1;
			}
		}
		return individu;
	}
	
	/**
	 * mutation bit-flip (swap Mutation) : selectionner 2 genes aleatoirement et les muter
	 * @param individu
	 * @return individu
	 */
	private Individu mutationBitFlipIndividu (Individu individu) {
		//System.out.println("### Mutation bit-flip ...");
		int gene1 = rand.nextInt(individu.getGenes().length);
		int gene2 = rand.nextInt(individu.getGenes().length);
		if (Math.random() < proba_mutation) {
			if(individu.getGenes()[gene1] == 1) individu.getGenes()[gene1] = 0;
			else individu.getGenes()[gene1] = 1;
			
			if(individu.getGenes()[gene2] == 1) individu.getGenes()[gene2] = 0;
			else individu.getGenes()[gene2] = 1;
		}
		return individu;
	}
	
	/**
	 * mutation k-flip (inversion Mutation) : selectionner aleatoirement k genes et les muter
	 * @param individu
	 * @param k
	 * @return individu
	 */
	private Individu mutationKFlipIndividu (Individu individu, int k) {
		if (k <= individu.getGenes().length) {
			//System.out.println("### Mutation "+k+"-flip ...");
			int[] PositionsGenesSelectiones = new int[k];
			for(int i=0; i<PositionsGenesSelectiones.length; i++) {
				PositionsGenesSelectiones[i] = rand.nextInt(individu.getGenes().length);
				//System.out.println("gene sélectionné : " + PositionsGenesSelectiones[i]);
			}
			if (Math.random() < proba_mutation) {
				for(int i=0; i<PositionsGenesSelectiones.length; i++) {
					if(individu.getGenes()[PositionsGenesSelectiones[i]] == 1) individu.getGenes()[PositionsGenesSelectiones[i]] = 0;
					else individu.getGenes()[PositionsGenesSelectiones[i]] = 1;
				}
			}
		} else {
			System.out.println("Erreur mutation - k > tille de l'individu");
		}
		return individu;
	}
	
	/**
	 * selection 2 individus au hasard
	 * @param pop
	 * @return individus sélectionnés
	 */
	private Population selection2Hasard (Population pop) {
		//System.out.println("### Selection de 2 individus au hasard");
		Population popSelectionnee = new Population(taille_selection, Main.TAILLE_INDIVIDU);
		for (int i=0; i<taille_selection; i++) {
			popSelectionnee.getIndividus()[i] = pop.getIndividus()[(int)(Math.random()*pop.getIndividus().length)];
		}
		// trier par ordre de fitness la population selectionnee
		popSelectionnee.TrierIndividusParFitness();
		return popSelectionnee;
	}
	
	/**
	 * selection les 2 meilleurs individus sur 5 aleatoires
	 * @param pop
	 * @return individus sélectionnés
	 */
	private Population selection2MeilleurSur5 (Population pop) {
		//System.out.println("### Selection des 2 meilleurs individus sur 5");
		Population selection = new Population(5, Main.TAILLE_INDIVIDU);
		Population popSelectionnee = new Population(taille_selection, Main.TAILLE_INDIVIDU);
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
	
	/**
	 * selection 2 meilleurs individus de la population
	 * @param pop
	 * @return individus sélectionnés
	 */
	private Population selection2Meilleur (Population pop) {
		//System.out.println("### Selection des 2 meilleurs individus");
		Population popSelectionnee = new Population(taille_selection, Main.TAILLE_INDIVIDU);
		pop.TrierIndividusParFitness();
		for (int i=0; i<taille_selection; i++) {
			popSelectionnee.getIndividus()[i] = pop.getIndividus()[i];
		}
		return popSelectionnee;
	}
	
	/**
	 * selection d un individu au hasard
	 * @param pop
	 * @return individus sélectionnés
	 */
	private Individu selection1Hasard(Population pop) {
		//System.out.println("### Selection d'un individu au hasard");
		Individu individuSelectionne = new Individu(Main.TAILLE_INDIVIDU);
		individuSelectionne = pop.getIndividus()[rand.nextInt(pop.getIndividus().length)];
		return individuSelectionne;
	}
	
	/**
	 * selection du meilleur individu
	 * @param pop
	 * @return individus sélectionnés
	 */
	private Individu selection1Meilleur(Population pop) {
		//System.out.println("### Selection du meilleur individu");
		Individu individuSelectionne = new Individu(Main.TAILLE_INDIVIDU);
		pop.TrierIndividusParFitness();
		individuSelectionne = pop.getIndividus()[0];
		return individuSelectionne;
	}	
	
	/*
	 * ********************************************************************
	 * ************************ REMPLACEMENT ******************************
	 * ********************************************************************
	 */
	
	/**
	 * remplacement de l'individu le plus mauvais
	 * @param pop
	 * @param individu
	 * @return nouvelle pop
	 */
	private Population remplacement1Mauvais(Population pop, Individu individu) {
		//System.out.println("### Remplacement de plus mauvais individu");
		Population popIssue = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		// initialisation de la nouvelle population
		for (int j=0; j<pop.getIndividus().length; j++) {
			popIssue.getIndividus()[j] = pop.getIndividus()[j];
		}
		popIssue.TrierIndividusParFitness();
		if (popIssue.getIndividus().length > 0) {
			popIssue.getIndividus()[popIssue.getIndividus().length-1] = individu;
		}		
		return popIssue;
	}
	
	/**
	 * remplacement des 2 mauvais individus de la population
	 * @param pop
	 * @param enfant1
	 * @param enfant2
	 * @return nouvelle pop
	 */
	private Population remplacement2Mauvais (Population pop, Individu enfant1, Individu enfant2) {
		//System.out.println("### Remplacement des 2 individus les plus mauvais");
		Population popIssue = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		// initialisation de la nouvelle population
		for (int j=0; j<pop.getIndividus().length; j++) {
			popIssue.getIndividus()[j] = pop.getIndividus()[j];
		}
		popIssue.TrierIndividusParFitness();
		if (popIssue.getIndividus().length > 0) {
			popIssue.getIndividus()[popIssue.getIndividus().length-1] = enfant1;
			if (popIssue.getIndividus().length > 1) {
				popIssue.getIndividus()[popIssue.getIndividus().length-2] = enfant2;
			} else {
				System.out.println("Cette population contient un seul individu. On ne peut pas remplacer les 2 derniers !");
			}
		}
		return popIssue;
	}
	
	/**
	 * remplacement des 2 meilleurs individus de la population
	 * @param pop
	 * @param enfant1
	 * @param enfant2
	 * @return nouvelle pop
	 */
	private Population remplacement2Meilleur (Population pop, Individu enfant1, Individu enfant2) {
		//System.out.println("### Remplacement des 2 meilleurs individus");
		Population popIssuse = new Population(pop.getIndividus().length, Main.TAILLE_INDIVIDU);
		// initialisation de la nouvelle population
		for (int j=0; j<pop.getIndividus().length; j++) {
			popIssuse.getIndividus()[j] = pop.getIndividus()[j];
		}
		popIssuse.TrierIndividusParFitness();
		if (popIssuse.getIndividus().length > 0) {
			popIssuse.getIndividus()[0] = enfant1;
			if (popIssuse.getIndividus().length > 1) {
				popIssuse.getIndividus()[1] = enfant2;
			} else {
				System.out.println("Cette population contient un seul individu. On ne peut pas remplacer les 2 meilleurs !");
			}
		}
		return popIssuse;
	}
	
}
