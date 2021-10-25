package kab.geneticalgorithm.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static final int TAILLE_POPULATION = 6;
	public static final int TAILLE_INDIVIDU = 6;
	public static final int NOMBRE_ITERATION = 1000;
	
	// Methodes de selection :
	public static final String S2H = "selection2Hasard";
	public static final String S2M = "selection2Meilleur";
	public static final String S2MS5 = "selection2MeilleurSur5";
	public static final String S1H = "selection1Hasard";
	public static final String S1M = "selection1Meilleur";
	
	// Methodes de remplacement :
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";	
	public static final String R1MA = "remplacement1Mauvais";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		CurveTracer courbe = new CurveTracer();
		
		Population pop = new Population(TAILLE_POPULATION, TAILLE_INDIVIDU).initialiationPopulation();
		
		System.out.println("### Population initiale");
		affichagePpulation(pop);
		
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		ArrayList <Double> fitnessGenerat = new ArrayList <Double>();
		
		int nb_iteration = 0;
		boolean popParfaite = false;
		while (nb_iteration < NOMBRE_ITERATION && !popParfaite) {			
			nb_iteration++;
			/*
			 * *****************************************************************
			 * *************** CROISEMENT & SELECTION & REMPLACEMENT ***********
			 * *****************************************************************
			 */
			//pop = GA.croisementSimplePopulation(pop, S2M, R1MA);
			//pop = GA.croisementUniformePopulation(pop, S1M, R1MA);
			pop = GA.croisementMonoPoint(pop, S2M, R2MA); //S2MS5
			
			
			/*
			 * *****************************************************************
			 * **************************** MUTATION ***************************
			 * *****************************************************************
			 */
			pop = GA.mutationPopulation(pop);			
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();
			
			double fitnessGeneration = 0;
			for (int x=0; x<TAILLE_POPULATION; x++) {
				fitnessGeneration = fitnessGeneration + (pop.getIndividus()[x].getFitness());
			}			
			fitnessGenerat.add(fitnessGeneration/TAILLE_POPULATION);
			// Affichage des generations & fitness moyenne
			System.out.println("### Generation " + nb_iteration + " | Fitness Moyenne: "+ fitnessGeneration/TAILLE_POPULATION);
			affichagePpulation(pop);
			
			// Condition d'arret
			if ((fitnessGeneration/TAILLE_POPULATION) == TAILLE_INDIVIDU) {
				popParfaite = true;
			}
		}
		
		/*
		 * *****************************************************************
		 * **************************** COURBE *****************************
		 * *****************************************************************
		 */
		int afficherCourbe = JOptionPane.showOptionDialog(
				null, "Voulez-vous voir la courbe Fitness/Iteration ?", "Courbe Fitness",
	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (afficherCourbe == 0) {
			String SE = System.getProperty("os.name").toLowerCase();
			if (SE.indexOf("nux") >= 0) {
				PrintWriter writer = new PrintWriter("data.dat", "UTF-8");
				writer.println("Iteration Fitness");
				for (int i=0; i<nb_iteration; i++) {
					writer.println(i +" "+fitnessGenerat.get(i));
				}				
				writer.close();
				courbe.draw();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
	
	// methode pour affichage de la population
	public static void affichagePpulation (Population pop) {
		for (int i=0; i<pop.getIndividus().length; i++) {
			System.out.println("Individu "+ i + " : " + 
					Arrays.toString(pop.getIndividus()[i].getGenes()) + 
					" Fitness = " + pop.getIndividus()[i].getFitness());
		}		
	}

	// getter
	public static int getTailleIndividu() {
		return TAILLE_INDIVIDU;
	}
}
