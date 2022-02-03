package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;

public class UCB {
	/**
	 * MAJ UCB
	 * @param op
	 * @param taille_liste_op
	 * @param c
	 * @param generation
	 */
	public static void majUCB(Operator op, int taille_liste_op, int c, int generation) {
		op.setProba( op.getListeReward().get( op.getListeReward().size()-1 ) + c * Math.sqrt( generation/( 2*Math.log(1+op.getNb_fois())+1 ) ) );
	}
	
	/**
	 * Sélection d'un opérateur
	 * @param listeOp
	 * @return
	 */
	public static int selectOperateur(ArrayList<Operator> listeOp, int generation) {
		Random rand = new Random();
		int index = 0;
		if (generation <= listeOp.size()*3) {
			while (listeOp.get(index).getNb_fois() >= 3) {
				index = rand.nextInt(listeOp.size());
			}
		} else {
			double max = listeOp.get(0).getProba();
			for(int i=0; i<listeOp.size(); i++) {
				if (max < listeOp.get(i).getProba()) {
					max = listeOp.get(i).getProba();
					index = i;
				}
			}
		}
		return index;
	}	

	/**
	 * lancement UCB
	 * @param historiqueOp
	 * @param historiqueFitnessMin
	 * @param historiqueFitnessMoy
	 * @param historiqueFitnessMax
	 * @param c
	 * @param nb_execution
	 */
	public static void launch(ArrayList<Double> historiqueOp, ArrayList<Double> historiqueFitnessMin, ArrayList<Double> historiqueFitnessMoy, ArrayList<Double> historiqueFitnessMax, int c, int nb_execution) {
		
		/** liste des opérateurs */
		ArrayList <Operator> listeOp = new ArrayList <Operator>();
		/** sauvegarde des fitness */
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		//initialisation individus & population
		Population pop = new Population(Test.TAILLE_POPULATION, Test.TAILLE_INDIVIDU).initialiationPopulation();
		
		//init de la liste des opérateurs de mutation
		for (int i=0; i<Test.MutationOperators.length; i++) {
			listeOp.add(new Operator(Test.MutationOperators[i], Test.valInitUCB, 0));
		}
		
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		//init des listes fitness.
		fitnessMin.add(0.0);	
		fitnessMoy.add(0.0);	
		fitnessMax.add(0.0);
		
		int generation = 1;
		boolean popParfaite = false;
		while (generation <= Test.MAX_GENERATION && !popParfaite) {
			System.out.println("############### GENERATION (" + generation + ") ###############");
			//sélectionner un operateur
			int index = selectOperateur(listeOp, generation);
			String current_op = listeOp.get(index).getName();
			//System.out.println("Op sélectionné = " + current_op);
			
			listeOp.get(index).incNb_fois();
			
			//mutation
			pop = GA.mutationPopulation(pop, current_op);
			
			//croisement & selection & remplacement
			pop = GA.croisementMonoPoint(pop, Test.S2M, Test.R2MA);	
			//pop = GA.croisementSimplePopulation(pop, S2M, R2MA);
			//pop = GA.croisementUniformePopulation(pop, S2M, R2MA);
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();		
			//calcul de la somme des fitness des individus
			double sommeFitnessIndividus = 0;
			for (int x=0; x<Test.TAILLE_POPULATION; x++) {
				sommeFitnessIndividus = sommeFitnessIndividus + (pop.getIndividus()[x].getFitness());
			}
			//ajout des fitness
			fitnessMin.add((double) (pop.getIndividus()[Test.TAILLE_POPULATION-1].getFitness()));
			fitnessMoy.add((double)sommeFitnessIndividus/Test.TAILLE_POPULATION);
			fitnessMax.add((double) (pop.getIndividus()[0].getFitness()));
			
			Display.affichagePpulation(pop);
			Display.affichageFitness(generation, fitnessMin, fitnessMoy, fitnessMax);
			
			//ajout de l'amélioration (on utilise la fitness en %)
			Reward.majAmelioration ( listeOp.get(index), fitnessMoy.get(generation-1), fitnessMoy.get(generation) );
			
			//ajout de la récompense
			Reward.majReward( listeOp.get(index), listeOp.get(index).getNb_fois(), listeOp.get(index).getListeAmelioration().get(listeOp.get(index).getNb_fois()-1) );
			
			//mise à jour de la val UCB
			for(int i=0; i<listeOp.size(); i++) {
				majUCB(listeOp.get(index), listeOp.size(), c, generation);
			}
			//affichage de l'etat actuel des opérateurs
			Display.affichageEtatOp(listeOp);
			
			// Condition d'arret de la boucle évolutionnaire
			if ((sommeFitnessIndividus / Test.TAILLE_POPULATION) == Test.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
			
			generation++;
		}
		
		//affichage de l'etat actuel des opérateurs
		System.out.println("######################################################");
		Display.affichageEtatOp(listeOp);
		System.out.println("######################################################");
		
		//sauvegarde de l'historique de l'utilisation de chaque opérateur (en incr)
		for(int i=0; i<listeOp.size(); i++) {
			historiqueOp.set(i, ( (double)(listeOp.get(i).getNb_fois()) + historiqueOp.get(i)) );
		}
		
		// sauvegarde de la moyenne des fitness
		if (nb_execution == 0) {
			for(int i=0; i<fitnessMin.size(); i++) {
				historiqueFitnessMin.add(fitnessMin.get(i));
				historiqueFitnessMoy.add(fitnessMoy.get(i));
				historiqueFitnessMax.add(fitnessMax.get(i));
			}
		} else {
			if (fitnessMin.size() > historiqueFitnessMin.size()) {
				for(int i=0; i<historiqueFitnessMin.size(); i++) {
					historiqueFitnessMin.set(i, (double)(historiqueFitnessMin.get(i)+fitnessMin.get(i))/2);
					historiqueFitnessMoy.set(i, (double)(historiqueFitnessMoy.get(i)+fitnessMoy.get(i))/2);
					historiqueFitnessMax.set(i, (double)(historiqueFitnessMax.get(i)+fitnessMax.get(i))/2);
				}
			} else {
				for(int i=0; i<fitnessMin.size(); i++) {
					historiqueFitnessMin.set(i, (double)(historiqueFitnessMin.get(i)+fitnessMin.get(i))/2);
					historiqueFitnessMoy.set(i, (double)(historiqueFitnessMoy.get(i)+fitnessMoy.get(i))/2);
					historiqueFitnessMax.set(i, (double)(historiqueFitnessMax.get(i)+fitnessMax.get(i))/2);
				}
			}
		}
	}
}
