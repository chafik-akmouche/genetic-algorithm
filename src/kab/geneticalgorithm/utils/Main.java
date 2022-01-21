package kab.geneticalgorithm.utils;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Chafik
 */
public class Main {
	/** initialisation*/
	public static final int TAILLE_POPULATION = 10;
	public static final int TAILLE_INDIVIDU = 10;
	public static final int MAX_GENERATION = 10;
	/** Opérateurs de selection*/
	public static final String S1H = "selection1Hasard";
	public static final String S2H = "selection2Hasard";
	public static final String S1M = "selection1Meilleur";
	public static final String S2M = "selection2Meilleur";
	public static final String S2MS5 = "selection2MeilleurSur5";
	/** Opérateurs de remplacement*/
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";	
	public static final String R1MA = "remplacement1Mauvais";
	/** Opérateurs de mutation*/
	public static final String M1F = "mutation1Flip";
	public static final String M2F = "mutation2Flip";
	public static final String M3F = "mutation3flip";
	public static final String M5F = "mutation5flip";
	/** liste des opérateurs de mutation */
	public static final String[] MutationOperators = {M1F, M2F, M3F, M5F};
	public static ArrayList <Operator> listeOp = new ArrayList <Operator>();
	/** proba init & proba min */
	public static double pInit = (double)1/MutationOperators.length;
	public static double pMin = 0.05;
	/** sauvegarde des fitness */
	public static ArrayList <Double> fitnessMin = new ArrayList <Double>();
	public static ArrayList <Double> fitnessMoy = new ArrayList <Double>();
	public static ArrayList <Double> fitnessMax = new ArrayList <Double>();
	/**
	 * méthode main
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		//initialisation individus & population
		Population pop = new Population(TAILLE_POPULATION, TAILLE_INDIVIDU).initialiationPopulation();
		System.out.println("### Population initiale");
		pop.affichagePpulation(pop);	
		
		//initialisation de la liste des opérateurs de mutation
		for (int i=0; i<MutationOperators.length; i++) {
			listeOp.add(new Operator(MutationOperators[i], pInit, 0));
		}
		//affichage de l'etat initial des opérateurs
		affichageEtatOp();
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		//init des listes fitness
		fitnessMin.add(0.0);	fitnessMoy.add(0.0);	fitnessMax.add(0.0);
		
		int generation = 1;
		boolean popParfaite = false;
		while (generation <= MAX_GENERATION && !popParfaite) {
			System.out.println("############### GENERATION (" + generation + ") ###############");
			//sélectionner un operateur
			int index = PM.selectOperateur(listeOp);
			String current_op = listeOp.get(index).getName();
			listeOp.get(index).incNb_fois();
			listeOp.get(index).addToGenerations(generation);
			
			//mutation
			pop = GA.mutationPopulation(pop, current_op);
			
			//croisement & selection & remplacement
			pop = GA.croisementMonoPoint(pop, S2M, R2MA);	
			//pop = GA.croisementSimplePopulation(pop, S2M, R2MA);
			//pop = GA.croisementUniformePopulation(pop, S2M, R2MA);
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();		
			//calcul de la somme des fitness des individus
			double sommeFitnessIndividus = 0;
			for (int x=0; x<TAILLE_POPULATION; x++) {
				sommeFitnessIndividus = sommeFitnessIndividus + (pop.getIndividus()[x].getFitness());
			}
			//ajout des fitness
			fitnessMin.add((double) (((pop.getIndividus()[TAILLE_POPULATION-1].getFitness())*100)/TAILLE_INDIVIDU));
			fitnessMoy.add(((sommeFitnessIndividus/TAILLE_POPULATION)*100)/TAILLE_INDIVIDU);
			fitnessMax.add((double) (((pop.getIndividus()[0].getFitness())*100)/TAILLE_INDIVIDU));
					
			pop.affichagePpulation(pop);
			affichageFitness(generation);
			System.out.println("### Opérateur sélectionné : " + current_op);
			
			//ajout de la récompense
			Reward.majReward(listeOp.get(index), (int)(fitnessMoy.get(generation-1)*TAILLE_INDIVIDU)/100, (int)(fitnessMoy.get(generation)*TAILLE_INDIVIDU)/100);
			
			//ajout de l'amélioration
			Reward.majAmelioration(listeOp.get(index), (double)(fitnessMoy.get(generation-1)), (double)(fitnessMoy.get(generation)));
			
			//mise à jour de la roulette proportionnelle
			for(int i=0; i<listeOp.size(); i++) {
				PM.majRoulette(listeOp.get(i), listeOp.size());
			}
			
			//affichage de l'etat actuel des opérateurs
			affichageEtatOp();
			
			// Condition d'arret
			if ((sommeFitnessIndividus / TAILLE_POPULATION) == TAILLE_INDIVIDU) {
				popParfaite = true;
			}
			generation++;
		}		
		
		//affichage de la courbe
		Curve.draw(generation, fitnessMin, fitnessMoy, fitnessMax);
		
		
	}
	
	/**
	 * affichage de l'etat des opérateurs
	 */
	private static void affichageEtatOp () {
		System.out.println("### ETAT DES OPERATEURS");
		for (int i=0; i<listeOp.size(); i++) {
			System.out.print("op "+i+": "+listeOp.get(i).getName()+" | proba="+listeOp.get(i).getProba()+" | nb_fois="+listeOp.get(i).getNb_fois());
			System.out.print("\n\tReward: [");
			for(int j=0; j<listeOp.get(i).getListeReward().size(); j++) {
				System.out.print(listeOp.get(i).getListeReward().get(j)+" ");
			}
			System.out.print("]\n\tGénération: [");
			for(int k=0; k<listeOp.get(i).getGenerations().size(); k++) {
				System.out.print(listeOp.get(i).getGenerations().get(k)+" ");
			}
			System.out.print("]\n\tAmélioration: [");
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
	private static void affichageFitness (int index) {
		System.out.println("Fitness Min : "+ fitnessMin.get(index)+" %\t| "+
							"Fitness Moy : "+ fitnessMoy.get(index)+" %\t| "+
							"Fitness Max : "+ fitnessMax.get(index)+" %");
	}
	
}
