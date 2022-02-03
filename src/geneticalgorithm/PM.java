package geneticalgorithm;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
/**
 * Sélection avec probability matching (PM)
 * @author Chafik
 */
public class PM {
	/**
	 * mettre à jour les probabilité de chaque opérateur
	 * @param op
	 * @param taille_liste_op
	 */
	public static void majRoulette(Operator op, int taille_liste_op) {
		double sommeReward = 0.0;
		for (int i=0; i<op.getListeReward().size(); i++) {
			sommeReward += op.getListeReward().get(i);
		}		
		if(sommeReward > 0)
			op.setProba( Test.pMin + (1 - taille_liste_op * Test.pMin) * (op.getListeReward().get(op.getListeReward().size()-1) / (sommeReward)) );
		else
			op.setProba(Test.pInitPM);
	}
	/**
	 * sélectionner un opérateur
	 * @param listeOp
	 * @return index
	 */
	public static int selectOperateur(ArrayList<Operator> listeOp) {
		double r = Math.random();
		double sommeProba = 0;
		int i = 0;
		while (sommeProba < r && i < listeOp.size()-1) {
			sommeProba += listeOp.get(i).getProba();
			if(sommeProba < r) i++;
		}
		return i;
	}	
	
	/**
	 * lancement du PM (Probability matching)
	 * @param historiqueOp
	 * @param hisOp
	 * @param historiqueFitnessMax
	 * @param nb_execution
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void launch(ArrayList<Double> historiqueOp, ArrayList<Double> historiqueFitnessMin, ArrayList<Double> historiqueFitnessMoy, ArrayList<Double> historiqueFitnessMax, int nb_execution) 
			throws FileNotFoundException, UnsupportedEncodingException {
		/** liste des opérateurs */
		ArrayList <Operator> listeOp = new ArrayList <Operator>();
		/** sauvegarde des fitness */
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		//initialisation individus & population
		Population pop = new Population(Test.TAILLE_POPULATION, Test.TAILLE_INDIVIDU).initialiationPopulation();
		//System.out.println("### Population initiale");
		//Display.affichagePpulation(pop);	
		
		//init de la liste des opérateurs de mutation
		for (int i=0; i<Test.MutationOperators.length; i++) {
			listeOp.add(new Operator(Test.MutationOperators[i], Test.pInitPM, 0));
		}
		//affichage de l'etat initial des opérateurs
		//Display.affichageEtatOp(listeOp);
		
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
			int index = PM.selectOperateur(listeOp);
			String current_op = listeOp.get(index).getName();
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
					
			//Display.affichagePpulation(pop);
			Display.affichageFitness(generation, fitnessMin, fitnessMoy, fitnessMax);
			
			//ajout de l'amélioration (on utilise la fitness en %)
			Reward.majAmelioration ( listeOp.get(index), fitnessMoy.get(generation-1), fitnessMoy.get(generation) );
			
			//ajout de la récompense
			Reward.majReward( listeOp.get(index), listeOp.get(index).getNb_fois(), listeOp.get(index).getListeAmelioration().get(listeOp.get(index).getNb_fois()-1) );
			
			//mise à jour de la roulette proportionnelle
			for(int i=0; i<listeOp.size(); i++) {
				PM.majRoulette(listeOp.get(index), listeOp.size());
			}
			
			//affichage de l'etat actuel des opérateurs
			//Display.affichageEtatOp(listeOp);
			
			// Condition d'arret de la boucle évolutionnaire
			if ((sommeFitnessIndividus / Test.TAILLE_POPULATION) == Test.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
			
			generation++;
		}
		
		//affichage de l'etat actuel des opérateurs
		//Affichage.affichageEtatOp(listeOp);
		
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
		
		//sauvegarde de l'historique des fitness
//		if (historiqueFitnessMax.size() == 0) {
//			for(int i=0; i<fitnessMax.size(); i++) {
//				historiqueFitnessMin.add( fitnessMin.get(i) );
//				historiqueFitnessMoy.add( fitnessMoy.get(i) );
//				historiqueFitnessMax.add( fitnessMax.get(i) );
//			}
//		}
	}
}
