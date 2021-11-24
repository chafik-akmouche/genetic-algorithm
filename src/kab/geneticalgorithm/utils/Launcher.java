package kab.geneticalgorithm.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Launcher {
		
	public Launcher() {}
	
	public static void operateur (String operateurSelection, String operateurRemplacement, String operateurMutation, int k) 
			throws FileNotFoundException, UnsupportedEncodingException {
		CurveTracer courbe = new CurveTracer();
		
		Population pop = new Population(Main.TAILLE_POPULATION, Main.TAILLE_INDIVIDU).initialiationPopulation();
		
		System.out.println("### Population initiale");
		affichagePpulation(pop);
		
		GeneticAlgorithm GA = new GeneticAlgorithm();
		
		ArrayList <Double> fitnessMin = new ArrayList <Double>();
		ArrayList <Double> fitnessPourcentage = new ArrayList <Double>();
		ArrayList <Double> fitnessMax = new ArrayList <Double>();
		
		int nb_iteration = 0;
		boolean popParfaite = false;
		while (nb_iteration < Main.NOMBRE_ITERATION && !popParfaite) {			
			nb_iteration++;
			
			switch(operateurMutation) {
			case "mutation1Flip":
				pop = GA.mutationPopulation(pop, operateurMutation, 0);
			case "mutation2Flip":
				pop = GA.mutationPopulation(pop, operateurMutation, 0);
			case "mutationKflip":
				pop = GA.mutationPopulation(pop, operateurMutation, k);
			}
			
			/*************** CROISEMENT & SELECTION & REMPLACEMENT ***********/
			//pop = GA.croisementSimplePopulation(pop, S2M, R1MA);
			//pop = GA.croisementUniformePopulation(pop, S1M, R1MA);
			pop = GA.croisementMonoPoint(pop, operateurSelection, operateurRemplacement); //S2MS5			
			
			// Trier les individus par ordre croissant des fitness
			pop.TrierIndividusParFitness();			
			double sommeFitnessIndividus = 0;
			for (int x=0; x<Main.TAILLE_POPULATION; x++) {
				sommeFitnessIndividus = sommeFitnessIndividus + (pop.getIndividus()[x].getFitness());
			}			
			fitnessMin.add((double) (((pop.getIndividus()[Main.TAILLE_POPULATION-1].getFitness())*100)/Main.TAILLE_INDIVIDU));
			fitnessPourcentage.add(((sommeFitnessIndividus/Main.TAILLE_POPULATION)*100)/Main.TAILLE_INDIVIDU);
			fitnessMax.add((double) (((pop.getIndividus()[0].getFitness())*100)/Main.TAILLE_INDIVIDU));			
			// Affichage des generations & fitness moyenne
			System.out.println("### Iteration (" + nb_iteration + ") | Fitness Moyenne de la population : "+ sommeFitnessIndividus / Main.TAILLE_POPULATION+" soit " +(((sommeFitnessIndividus/Main.TAILLE_POPULATION)*100)/Main.TAILLE_INDIVIDU)+" %");
			affichagePpulation(pop);			
			// Condition d'arret
			if ((sommeFitnessIndividus / Main.TAILLE_POPULATION) == Main.TAILLE_INDIVIDU) {
				popParfaite = true;
			}
		}
		
		for (int i=0; i<fitnessMin.size(); i++) {
			System.out.println("Fitness Min (%) : "+ fitnessMin.get(i)+" %");
		}
		for (int i=0; i<fitnessPourcentage.size(); i++) {
			System.out.println("Fitness Moyenne des populations : "+ fitnessPourcentage.get(i));
		}
		for (int i=0; i<fitnessMax.size(); i++) {
			System.out.println("Fitness Max (%) : "+ fitnessMax.get(i)+" %");
		}
		/**************************** COURBE *****************************/
		int afficherCourbe = JOptionPane.showOptionDialog(
				null, "Voulez-vous visualiser la courbe Fitness/Iteration ?", "Courbe Fitness",
	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (afficherCourbe == 0) {
			String SE = System.getProperty("os.name").toLowerCase();
			if (SE.indexOf("nux") >= 0) {
				PrintWriter writer = new PrintWriter("data.dat", "UTF-8");
				writer.println("Iteration F-Min F-Moy F-Max");
				for (int i=0; i<nb_iteration; i++) {
					writer.println(i +" "+fitnessMin.get(i)+" "+fitnessPourcentage.get(i)+" "+fitnessMax.get(i));
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
	//methode pour affichage de la population
	public static void affichagePpulation (Population pop) {
		for (int i=0; i<pop.getIndividus().length; i++) {
			System.out.println("Individu "+ i + " : " + 
					Arrays.toString(pop.getIndividus()[i].getGenes()) + 
					" Fitness = " + pop.getIndividus()[i].getFitness());
		}		
	}
}
