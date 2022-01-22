package kab.geneticalgorithm.utils;
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
			op.setProba( Main.pMin + (1 - taille_liste_op * Main.pMin) * (op.getListeReward().get(op.getListeReward().size()-1) / (sommeReward)) );
		else
			op.setProba(Main.pInit);
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
	
	public static void launche(ArrayList<Integer> historiqueOp, ArrayList<Double> historiqueFit) throws FileNotFoundException, UnsupportedEncodingException {
		/** liste des opérateurs */
		ArrayList <Operator> listeOp = new ArrayList <Operator>();
		/** sauvegarde des fitness */
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		//initialisation individus & population
		Population pop = new Population(Main.TAILLE_POPULATION, Main.TAILLE_INDIVIDU).initialiationPopulation();
		System.out.println("### Population initiale");
		pop.affichagePpulation(pop);	
		
		//initialisation de la liste des opérateurs de mutation
		for (int i=0; i<Main.MutationOperators.length; i++) {
			listeOp.add(new Operator(Main.MutationOperators[i], Main.pInit, 0));
		}
		//affichage de l'etat initial des opérateurs
		affichageEtatOp(listeOp);
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		//init des listes fitness
		fitnessMin.add(0.0);	fitnessMoy.add(0.0);	fitnessMax.add(0.0);
				
		int generation = 1;
		boolean popParfaite = false;
		while (generation <= Main.MAX_GENERATION && !popParfaite) {
			System.out.println("############### GENERATION (" + generation + ") ###############");
			//sélectionner un operateur
			int index = PM.selectOperateur(listeOp);
			String current_op = listeOp.get(index).getName();
			listeOp.get(index).incNb_fois();
			listeOp.get(index).addToGenerations(generation);
			
			//mutation
			pop = GA.mutationPopulation(pop, current_op);
			
			//croisement & selection & remplacement
			pop = GA.croisementMonoPoint(pop, Main.S2M, Main.R2MA);	
			//pop = GA.croisementSimplePopulation(pop, S2M, R2MA);
			//pop = GA.croisementUniformePopulation(pop, S2M, R2MA);
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();		
			//calcul de la somme des fitness des individus
			double sommeFitnessIndividus = 0;
			for (int x=0; x<Main.TAILLE_POPULATION; x++) {
				sommeFitnessIndividus = sommeFitnessIndividus + (pop.getIndividus()[x].getFitness());
			}
			//ajout des fitness
			fitnessMin.add((double) (((pop.getIndividus()[Main.TAILLE_POPULATION-1].getFitness())*100)/Main.TAILLE_INDIVIDU));
			fitnessMoy.add(((sommeFitnessIndividus/Main.TAILLE_POPULATION)*100)/Main.TAILLE_INDIVIDU);
			fitnessMax.add((double) (((pop.getIndividus()[0].getFitness())*100)/Main.TAILLE_INDIVIDU));
					
			pop.affichagePpulation(pop);
			affichageFitness(generation, fitnessMin, fitnessMoy, fitnessMax);
			System.out.println("### Opérateur sélectionné : " + current_op);
			
			//ajout de la récompense
			Reward.majReward(listeOp.get(index), (int)(fitnessMoy.get(generation-1)*Main.TAILLE_INDIVIDU)/100, (int)(fitnessMoy.get(generation)*Main.TAILLE_INDIVIDU)/100);
			
			//ajout de l'amélioration
			Reward.majAmelioration(listeOp.get(index), (double)(fitnessMoy.get(generation-1)), (double)(fitnessMoy.get(generation)));
			
			//mise à jour de la roulette proportionnelle
			for(int i=0; i<listeOp.size(); i++) {
				PM.majRoulette(listeOp.get(i), listeOp.size());
			}
			
			//affichage de l'etat actuel des opérateurs
			affichageEtatOp(listeOp);
			
			// Condition d'arret
			if ((sommeFitnessIndividus / Main.TAILLE_POPULATION) == Main.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
//			if (generation == 1) {
//				for(int i=0; i<Main.MAX_GENERATION; i++) {
//					historiqueFit.add(fitnessMoy.get(generation));
//				}
//			} else if (generation > 1) {
//				for(int i=0; i<historiqueFit.size(); i++) {
//					historiqueFit.set(i, (fitnessMoy.get(generation) + historiqueFit.get(i))/2);
//				}
//			}
			generation++;
		}
		for(int i=0; i<listeOp.size(); i++) {
			historiqueOp.set(i, (listeOp.get(i).getNb_fois())+historiqueOp.get(i));
		}
		
		for(int i=0; i<fitnessMax.size(); i++) {
			historiqueFit.set(i, (fitnessMax.get(i) + historiqueFit.get(i)));
		}
		//affichage de la courbe
		//Curve.draw(generation, fitnessMin, fitnessMoy, fitnessMax);
	}
	
	/**
	 * affichage de l'etat des opérateurs
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
	 */
	public static void affichageFitness (int index, ArrayList<Double>fitnessMin, ArrayList<Double>fitnessMoy, ArrayList<Double>fitnessMax) {
		System.out.println("Fitness Min : "+ fitnessMin.get(index)+" %\t| "+
							"Fitness Moy : "+ fitnessMoy.get(index)+" %\t| "+
							"Fitness Max : "+ fitnessMax.get(index)+" %");
	}
}
