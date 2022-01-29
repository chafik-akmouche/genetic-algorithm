package geneticalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Affichage
 * @author Chafik
 *
 */
public class Display {
	/**
	 * Méthode affichage de la population
	 * @param population
	 */
	public static void affichagePpulation (Population pop) {
		for (int i=0; i<pop.getIndividus().length; i++) {
			System.out.println("Individu "+ i + " : " + 
					Arrays.toString(pop.getIndividus()[i].getGenes()) + 
					" Fitness = " + pop.getIndividus()[i].getFitness());
		}		
	}
	
	/**
	 * affichage generation & fitness
	 * @param index
	 * @param fitnessMin
	 * @param fitnessMoy
	 * @param fitnessMax
	 */
	public static void affichageFitness (int index, ArrayList<Double>fitnessMin, ArrayList<Double>fitnessMoy, ArrayList<Double>fitnessMax) {
		System.out.println( "Fitness Min : "+ fitnessMin.get(index)+"\t| "+
							"Fitness Moy : "+ fitnessMoy.get(index)+"\t| "+
							"Fitness Max : "+ fitnessMax.get(index));
	}
	
	/**
	 * affichage de l'etat des opérateurs
	 * @param listeOp
	 */
	public static void affichageEtatOp (ArrayList<Operator> listeOp) {
		System.out.println("### ETAT DES OPERATEURS");
		for (int i=0; i<listeOp.size(); i++) {
			System.out.print("op "+i+": "+listeOp.get(i).getName()+" | proba/val="+listeOp.get(i).getProba()+" | nb_fois="+listeOp.get(i).getNb_fois());
			System.out.print("\n\tReward: [ ");
			for(int j=0; j<listeOp.get(i).getListeReward().size(); j++) {
				System.out.print(listeOp.get(i).getListeReward().get(j)+" ");
			}
			System.out.print("]\n\tAmélioration: [ ");
			for(int k=0; k<listeOp.get(i).getListeAmelioration().size(); k++) {
				System.out.print(listeOp.get(i).getListeAmelioration().get(k)+" ");
			}
			System.out.print("]\n");
		}
	}

}
