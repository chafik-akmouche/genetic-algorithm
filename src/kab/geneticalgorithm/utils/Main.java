package kab.geneticalgorithm.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static final int TAILLE_POPULATION = 4;
	public static final int TAILLE_INDIVIDU = 8;
	public static final int NOMBRE_ITERATION = 10000;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		CurveTracer courbe = new CurveTracer();
		
		Population pop = new Population(TAILLE_POPULATION, TAILLE_INDIVIDU).initialiationPopulation();
		
		System.out.println("### Population initiale");
		affichagePpulation(pop);
		
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		ArrayList <Double> fitnessGenerat = new ArrayList <Double>();
		
		int nb_iteration = 0;
		boolean popParfaite = false;
		while (nb_iteration < NOMBRE_ITERATION /*&& pop.getIndividus()[0].getFitness() != TAILLE_INDIVIDU*/ && !popParfaite) {			
			nb_iteration++;
			
			//pop = GA.croisementSimplePopulation(pop);
			//pop = GA.croisementUniformePopulation(pop);
			pop = GA.croisementMonoPoint(pop);
			
			pop = GA.mutationPopulation(pop);
			pop.TrierIndividusParFitness();
			
			double fitnessGeneration = 0;
			for (int x=0; x<TAILLE_POPULATION; x++) {
				fitnessGeneration = fitnessGeneration + (pop.getIndividus()[x].getFitness());
			}
			
			fitnessGenerat.add(fitnessGeneration/TAILLE_POPULATION);
			
			System.out.println("### Generation " + nb_iteration + " | Fitness Moyenne: "+ fitnessGeneration/TAILLE_POPULATION);
			affichagePpulation(pop);
			
			if ((fitnessGeneration/TAILLE_POPULATION) == TAILLE_INDIVIDU) {
				popParfaite = true;
			}
		}
		
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
