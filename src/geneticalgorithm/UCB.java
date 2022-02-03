package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;

public class UCB {
	/**
	 * MAJ UCB
	 * @param op
	 * @param taille_liste_op
	 * @param c
	 * @param generation
	 */
	public static void majUCB(Operator op, int taille_liste_op, int c, int generation) {
		op.setProba( op.getListeReward().get( op.getListeReward().size()-1 ) + c * Math.sqrt( generation/( 2*Math.log(1+op.getNb_fois())+1 ) ) );
	}
	
	/**
	 * Sélection d'un opérateur
	 * @param listeOp
	 * @return
	 */
	public static int selectOperateur(ArrayList<Operator> listeOp, int generation) {
		Random rand = new Random();
		int index = 0;
		if (generation <= listeOp.size()*3) {
			while (listeOp.get(index).getNb_fois() >= 3) {
				index = rand.nextInt(listeOp.size());
			}
		} else {
			double max = listeOp.get(0).getProba();
			for(int i=0; i<listeOp.size(); i++) {
				if (max < listeOp.get(i).getProba()) {
					max = listeOp.get(i).getProba();
					index = i;
				}
			}
		}
		return index;
	}
}
