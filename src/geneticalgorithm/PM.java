package geneticalgorithm;

import java.util.ArrayList;

/**
 * Sélection avec probability matching (PM)
 * 
 * @author Chafik
 */
public class PM {
	/**
	 * mettre à jour les probabilité de chaque opérateur
	 * 
	 * @param op
	 * @param taille_liste_op
	 */
	public static void majRoulette(Operator op, int taille_liste_op) {
		double sommeReward = 0.0;
		for (int i = 0; i < op.getListeReward().size(); i++) {
			sommeReward += op.getListeReward().get(i);
		}
		if (sommeReward > 0)
			op.setProba(Test.pMin + (1 - taille_liste_op * Test.pMin)
					* (op.getListeReward().get(op.getListeReward().size() - 1) / (sommeReward)));
		else
			op.setProba(Test.pInitPM);
	}

	/**
	 * sélectionner un opérateur
	 * 
	 * @param listeOp
	 * @return index
	 */
	public static int selectOperateur(ArrayList<Operator> listeOp) {
		double r = Math.random();
		double sommeProba = 0;
		int i = 0;
		while (sommeProba < r && i < listeOp.size() - 1) {
			sommeProba += listeOp.get(i).getProba();
			if (sommeProba < r)
				i++;
		}
		return i;
	}
}
