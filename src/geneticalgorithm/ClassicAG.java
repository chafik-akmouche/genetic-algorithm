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
	public static void launch (int NB_EXECUTION, String selection, String croisement, String mutation, String remplacement, ArrayList<Double> historiqueFitnessMin, ArrayList<Double> historiqueFitnessMoy, ArrayList<Double> historiqueFitnessMax) 
			throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println("### AG CLASSIQUE ### EXECUTION (" + NB_EXECUTION + ")");
		
		/** sauvegarde des fitness */
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessMoy = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		//initialisation individus & population
		Population pop = new Population(Test.TAILLE_POPULATION, Test.TAILLE_INDIVIDU).initialiationPopulation();
		//System.out.println("### Population initiale");
		//Display.affichagePpulation(pop);	
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		//init des listes fitness
		fitnessMin.add(0.0);	
		fitnessMoy.add(0.0);	
		fitnessMax.add(0.0);
				
		int generation = 1;
		boolean popParfaite = false;
		while (generation <= Test.MAX_GENERATION && !popParfaite) {
			System.out.println("############### GENERATION (" + generation + ") ###############");
			
			//selection & croisement & mutation & remplacement
			pop = GA.cycle(pop, selection, croisement, mutation, remplacement);
			//pop = GA.cycle(pop, Test.S2M, Test.CMP, Test.M2F, Test.R2MA);
			
			//mutation
			//pop = GA.mutationPopulation(pop, Test.M2F);
			
			//selection & croisement & mutation & remplacement
			//pop = GA.croisementMonoPoint(pop, Test.S2M, Test.M2F, Test.R2MA);	
			//pop = GA.croisementSimplePopulation(pop, Test.S2M, Test.R2MA);
			//pop = GA.croisementUniformePopulation(pop, Test.S2M, Test.R2MA);
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();		
			//calcul de la somme des fitness des individus
			double sommeFitnessIndividus = 0;
			for (int x=0; x<Test.TAILLE_POPULATION; x++) {
				sommeFitnessIndividus = sommeFitnessIndividus + (pop.getIndividus()[x].getFitness());
			}
			
			//ajout des fitness
			fitnessMin.add((double) (pop.getIndividus()[Test.TAILLE_POPULATION-1].getFitness()));
			fitnessMoy.add(sommeFitnessIndividus/Test.TAILLE_POPULATION);
			fitnessMax.add((double) (pop.getIndividus()[0].getFitness()));
					
			//Display.affichagePpulation(pop);
			//Display.affichageFitness(generation, fitnessMin, fitnessMoy, fitnessMax);
			
			// Condition d'arret de la boucle évolutionnaire
			if ((sommeFitnessIndividus / Test.TAILLE_POPULATION) == Test.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
			generation++;
		}
		
		//convertir les fitness en (%)
		//for(int i=0; i<fitnessMax.size(); i++) {
			//fitnessMax.set(i, (double)((fitnessMax.get(i)*100) / Test.TAILLE_INDIVIDU));
		//}
		
		// sauvegarde de la moyenne des fitness
		if (NB_EXECUTION == 0) {
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
