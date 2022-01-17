package kab.geneticalgorithm.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Sélection avec probability matching (PM)
 * @author Chafik
 *
 */
public class PM {
	
	/**
	 * initialisation de la liste des probabilités
	 * @param taille (int)
	 * @return liste
	 */
	public static ArrayList<Double> initListeProba (int taille) {
		ArrayList<Double> listeProba = new ArrayList<Double>();
		for (int i=0; i<taille; i++) {
			listeProba.add(i, 0.0);
		}		
		return listeProba;
	}
	
	/**
	 * Mise à jour de la roulette adaptive
	 * @param listeReward
	 * @param listeProba
	 * @param pMin
	 */
	public static void updateRoulette(ArrayList<Double> listeReward, ArrayList<Double> listeProba, double pMin) {
		double sommeReward = 0;
		for (int i=0; i<listeReward.size(); i++) {
			sommeReward += listeReward.get(i);
		}
		if(sommeReward > 0) {
			for (int i=0; i<listeProba.size(); i++) {
				listeProba.add(i, pMin + (1 - listeProba.size() * pMin) * (listeReward.get(i) / (sommeReward)));
			}
		} else {
			listeProba = initListeProba(listeProba.size());
		}
	}
	
	/**
	 * sélection d'un opérateur
	 * @param listeProba
	 * @return index
	 */
	public static int selectOperateur(ArrayList<Double> listeProba) {
		Random rand = new Random();
		int r = rand.nextInt();
		double sommeProba = 0;
		int i = 0;
		while (sommeProba<r && i<listeProba.size()) {
			sommeProba += listeProba.get(i);
			if(sommeProba < r) {
				i++;
			}
		}
		return i;
	}
	
}
