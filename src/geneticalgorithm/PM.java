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
			op.setProba(Test.pInit);
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
		while (sommeProba<r && i<listeOp.size()-1) {
			sommeProba += listeOp.get(i).getProba();
			if(sommeProba < r) i++;
		}
		return i;
	}	
	
	/**
	 * lancement du PM (Probability matching)
	 * @param historiqueOp
	 * @param hisOp
	 * @param historiqueFit
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void launch(ArrayList<Integer> historiqueOp, ArrayList<Integer> hisOp, ArrayList<Double> historiqueFit) throws FileNotFoundException, UnsupportedEncodingException {
		/** liste des opérateurs */
		ArrayList <Operator> listeOp = new ArrayList <Operator>();
		/** sauvegarde des fitness */
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		//initialisation individus & population
		Population pop = new Population(Test.TAILLE_POPULATION, Test.TAILLE_INDIVIDU).initialiationPopulation();
		System.out.println("### Population initiale");
		//pop.affichagePpulation(pop);	
		
		//initialisation de la liste des opérateurs de mutation
		for (int i=0; i<Test.MutationOperators.length; i++) {
			listeOp.add(new Operator(Test.MutationOperators[i], Test.pInit, 0));
		}
		//affichage de l'etat initial des opérateurs
		affichageEtatOp(listeOp);
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		//init des listes fitness
		fitnessMin.add(0.0);	fitnessMoy.add(0.0);	fitnessMax.add(0.0);
				
		int generation = 1;
		boolean popParfaite = false;
		while (generation <= Test.MAX_GENERATION && !popParfaite) {
			System.out.println("############### GENERATION (" + generation + ") ###############");
			//sélectionner un operateur
			int index = PM.selectOperateur(listeOp);
			String current_op = listeOp.get(index).getName();
			listeOp.get(index).incNb_fois();
			listeOp.get(index).addToGenerations(generation);
			
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
			fitnessMin.add((double) (((pop.getIndividus()[Test.TAILLE_POPULATION-1].getFitness())*100)/Test.TAILLE_INDIVIDU));
			fitnessMoy.add(((sommeFitnessIndividus/Test.TAILLE_POPULATION)*100)/Test.TAILLE_INDIVIDU);
			fitnessMax.add((double) (((pop.getIndividus()[0].getFitness())*100)/Test.TAILLE_INDIVIDU));
					
			//pop.affichagePpulation(pop);
			affichageFitness(generation, fitnessMin, fitnessMoy, fitnessMax);
			//System.out.println("### Opérateur sélectionné : " + current_op);
			
			//ajout de la récompense
			Reward.majReward(listeOp.get(index), (int)(fitnessMoy.get(generation-1)*Test.TAILLE_INDIVIDU)/100, (int)(fitnessMoy.get(generation)*Test.TAILLE_INDIVIDU)/100);
			
			//ajout de l'amélioration
			Reward.majAmelioration(listeOp.get(index), (double)(fitnessMoy.get(generation-1)), (double)(fitnessMoy.get(generation)));
			
			//mise à jour de la roulette proportionnelle
			for(int i=0; i<listeOp.size(); i++) {
				PM.majRoulette(listeOp.get(index), listeOp.size());
			}
			
			//affichage de l'etat actuel des opérateurs
			//affichageEtatOp(listeOp);
			
			// Condition d'arret de la boucle évolutionnaire
			if ((sommeFitnessIndividus / Test.TAILLE_POPULATION) == Test.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
			
			//sauvegarde de l'historique de toutes les utilisations de chaque opérateur
			for(int i=0; i<listeOp.size(); i++) {
				hisOp.add( listeOp.get(i).getNb_fois() );
			}
			
			generation++;
		}
		
		//affichage de l'etat actuel des opérateurs
		affichageEtatOp(listeOp);
		
		//sauvegarde de l'historique de l'utilisation de chaque opérateur (en incr)
		for(int i=0; i<listeOp.size(); i++) {
			historiqueOp.set(i, ( listeOp.get(i).getNb_fois()) + historiqueOp.get(i) );
		}
		
//		//sauvegarde de l'historique de toutes les utilisations de chaque opérateur
//		for(int i=0; i<listeOp.size(); i++) {
//			hisOp.add( listeOp.get(i).getNb_fois() );
//		}
		
		//sauvegarde des fitness max de chaque génération
		for(int i=0; i<fitnessMax.size(); i++) {
			historiqueFit.set(i, ( fitnessMax.get(i) + historiqueFit.get(i) ));
		}
	}
	
	/**
	 * affichage de l'etat des opérateurs
	 * @param listeOp
	 */
	public static void affichageEtatOp (ArrayList<Operator> listeOp) {
		System.out.println("### ETAT DES OPERATEURS");
		for (int i=0; i<listeOp.size(); i++) {
			System.out.print("op "+i+": "+listeOp.get(i).getName()+" | proba="+listeOp.get(i).getProba()+" | nb_fois="+listeOp.get(i).getNb_fois());
			System.out.print("\n\tReward: [ ");
			for(int j=0; j<listeOp.get(i).getListeReward().size(); j++) {
				System.out.print(listeOp.get(i).getListeReward().get(j)+" ");
			}
			System.out.print("]\n\tGénération: [ ");
			for(int k=0; k<listeOp.get(i).getGenerations().size(); k++) {
				System.out.print(listeOp.get(i).getGenerations().get(k)+" ");
			}
			System.out.print("]\n\tAmélioration: [ ");
			for(int k=0; k<listeOp.get(i).getListeAmelioration().size(); k++) {
				System.out.print(listeOp.get(i).getListeAmelioration().get(k)+" ");
			}
			System.out.print("]\n");
		}
	}
	
	/**
	 * affichage generation & fitness (%)
	 * @param index
	 * @param fitnessMin
	 * @param fitnessMoy
	 * @param fitnessMax
	 */
	public static void affichageFitness (int index, ArrayList<Double>fitnessMin, ArrayList<Double>fitnessMoy, ArrayList<Double>fitnessMax) {
		System.out.println( "Fitness Min : "+ fitnessMin.get(index)+" %\t| "+
							"Fitness Moy : "+ fitnessMoy.get(index)+" %\t| "+
							"Fitness Max : "+ fitnessMax.get(index)+" %");
	}
}
