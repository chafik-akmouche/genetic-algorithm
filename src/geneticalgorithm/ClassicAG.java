package geneticalgorithm;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
/**
 * @author Chafik
 */
public class ClassicAG {
	/**
	 * lancement de l'AG classique
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void launch () throws FileNotFoundException, UnsupportedEncodingException {
		/** sauvegarde des fitness */
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		//initialisation individus & population
		Population pop = new Population(Test.TAILLE_POPULATION, Test.TAILLE_INDIVIDU).initialiationPopulation();
		//System.out.println("### Population initiale");
		//pop.affichagePpulation(pop);	
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		//init des listes fitness
		fitnessMin.add(0.0);	fitnessMoy.add(0.0);	fitnessMax.add(0.0);
				
		int generation = 1;
		boolean popParfaite = false;
		while (generation <= Test.MAX_GENERATION && !popParfaite) {
			//System.out.println("############### GENERATION (" + generation + ") ###############");			
			//mutation
			pop = GA.mutationPopulation(pop, Test.M5F);
			
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
			//PM.affichageFitness(generation, fitnessMin, fitnessMoy, fitnessMax);
			
			// Condition d'arret de la boucle évolutionnaire
			if ((sommeFitnessIndividus / Test.TAILLE_POPULATION) == Test.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
			generation++;
		}	
				
		//affichage de la courbe fitnessMin, moy et max / génération
		Curve.draw1(generation, fitnessMin, fitnessMoy, fitnessMax);
	}

}
