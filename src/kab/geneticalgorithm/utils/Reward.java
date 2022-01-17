package kab.geneticalgorithm.utils;

import java.util.ArrayList;

/**
 * 
 * @author Chafik
 *
 */
public class Reward {
	
	/**
	 * initialisation liste reward
	 * @param taille
	 * @return liste
	 */
	public static ArrayList<Double> initReward (int taille){
		ArrayList<Double> listeReward = new ArrayList<Double>();
		for(int i=0; i<taille; i++) {
			listeReward.add(i, 0.0);
		}
		return listeReward;
	}
	
	/**
	 * initialisation de la liste de l'historique des rewards
	 * @param taille
	 * @return
	 */
	public static ArrayList<Double> initHisReward (int taille) {//il faut une matrice
		ArrayList<Double> listeHisReward = new ArrayList<Double>();
		for(int i=0; i<taille; i++) {
			listeHisReward.add(i, 0.0);
		}
		return listeHisReward;
	}
	
	/**
	 * initialisation de la liste des operateurs
	 * @param listeOper
	 * @param taille
	 */
	public static void initHisOperateur(ArrayList<Integer> listeOper, int taille) {
		for(int i=0; i<taille; i++) {
			listeOper.add(i, 0);
		}
	}
	
	/**
	 * calcul de l'amélioration (reward) immédiate
	 * @param val_init
	 * @param val_mut
	 * @param decalage
	 * @return
	 */
	public static double amelioration(double val_init, double val_mut, int decalage) {
		return ((val_mut - val_init) + decalage);
	}
	
	/**
	 * mise à jour des rewards
	 * @param listeReward
	 * @param iter
	 * @param index
	 * @param val
	 */
	public static void updateReward(ArrayList<Double> listeReward, int iter, int index, double val) {
		listeReward.set(index, ((iter-1) * listeReward.get(index) + val) / (iter));
	}
	
	/**
	 * fenetre coulissante
	 * @param listeReward
	 * @param listeHisReward
	 * @param tailleHis
	 * @param index
	 * @param val
	 */
	public static void fenetreCoulissante(ArrayList<Double> listeReward, ArrayList<Double> listeHisReward, int tailleHis, int index, double val) {
		if (listeHisReward.get(index) == 0) {//listeHisReward :> il me faut une matrice
			listeHisReward.set(index, val);
		} else {
			listeHisReward.add(index, val);
		}
		
		if(listeHisReward.size() > tailleHis) {
			//reward_history[index] = reward_history[index][1:len(reward_history[index])]
		}
		listeReward.set(index, listeHisReward.get(index)/listeHisReward.size());
	}

}
