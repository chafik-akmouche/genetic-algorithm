package kab.geneticalgorithm.utils;

import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Chafik
 */
public class Main {
	/** initialisation*/
	public static final int TAILLE_POPULATION = 6;
	public static final int TAILLE_INDIVIDU = 6;
	public static final int NOMBRE_ITERATION = 20;
	/** Opérateurs de selection*/
	public static final String S1H = "selection1Hasard";		//select 1 au hasard
	public static final String S2H = "selection2Hasard";		//select 2 au hasard
	public static final String S1M = "selection1Meilleur";		//select le meilleur
	public static final String S2M = "selection2Meilleur";		//select les 2 meilleurs
	public static final String S2MS5 = "selection2MeilleurSur5";//select les 2 meilleurs sur 5
	/** Opérateurs de remplacement*/
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";	
	public static final String R1MA = "remplacement1Mauvais";
	/** Opérateurs de mutation*/
	public static final String M1F = "mutation1Flip";
	public static final String M2F = "mutation2Flip";
	public static final String M3F = "mutation3flip";
	public static final String M5F = "mutation5flip";
	//public static final String[] MutationOperators = {"mutation1Flip", "mutation2Flip", "mutation3flip", "mutation5flip"};
	/**
	 * Methode main
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Population pop = new Population(Main.TAILLE_POPULATION, Main.TAILLE_INDIVIDU).initialiationPopulation();
		
		System.out.println("### Population initiale");
		pop.affichagePpulation(pop);
		
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		int nb_iteration = 0;
		boolean popParfaite = false;
		
		while (nb_iteration < NOMBRE_ITERATION && !popParfaite) {			
			//MUTATION
			pop = GA.mutationPopulation(pop, M2F);
			
			//CROISEMENT & SELECTION & REMPLACEMENT
			pop = GA.croisementMonoPoint(pop, S2M, R2MA);	
			//pop = GA.croisementSimplePopulation(pop, S2M, R2MA);
			//pop = GA.croisementUniformePopulation(pop, S2M, R2MA);
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();		
			
			double sommeFitnessIndividus = 0;
			for (int x=0; x<TAILLE_POPULATION; x++) {
				sommeFitnessIndividus = sommeFitnessIndividus + (pop.getIndividus()[x].getFitness());
			}
			
			fitnessMin.add((double) (((pop.getIndividus()[TAILLE_POPULATION-1].getFitness())*100)/TAILLE_INDIVIDU));
			fitnessMoy.add(((sommeFitnessIndividus/TAILLE_POPULATION)*100)/TAILLE_INDIVIDU);
			fitnessMax.add((double) (((pop.getIndividus()[0].getFitness())*100)/TAILLE_INDIVIDU));			
			
			// Affichage des generations & fitness moyenne
			System.out.println("### Iteration (" + nb_iteration + ") | "
					+ "Fitness Moyenne de la population : "+ sommeFitnessIndividus / TAILLE_POPULATION
					+" soit " +(((sommeFitnessIndividus/TAILLE_POPULATION)*100)/TAILLE_INDIVIDU)+" %");
			
			pop.affichagePpulation(pop);
			
			// Condition d'arret
			if ((sommeFitnessIndividus / TAILLE_POPULATION) == TAILLE_INDIVIDU) {
				popParfaite = true;
			}			
			nb_iteration++;
		}		
		//affichage de la courbe
		Curve.draw(nb_iteration, fitnessMin, fitnessMoy, fitnessMax);
	}
	
}
