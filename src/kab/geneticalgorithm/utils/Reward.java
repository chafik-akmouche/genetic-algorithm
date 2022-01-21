package kab.geneticalgorithm.utils;
/**
 * @author Chafik
 */
public class Reward {
	/**
	 * ajouter l'amélioration (en %) à l'opérateur adéquat
	 * @param op
	 * @param fitness_old
	 * @param fitness_new
	 */
	public static void majAmelioration(Operator op, double fitness_old, double fitness_new) {
		if (fitness_new <= fitness_old) {
			op.addToListeAmelioration(0.0);
			//System.out.println("Ajout de l'amélioration 0 à l'operateur " + op.getName());
		} else {
			op.addToListeAmelioration((double)(fitness_new-fitness_old));
			//System.out.println("Ajout de l'amélioration "+((double)fitness_new-fitness_old)+" à l'operateur " + op.getName());
		}
	}
	
//	public static void updateReward(ArrayList<Double> listeReward, int iter, int index, double val) {
//		listeReward.set(index, ((iter-1) * listeReward.get(index) + val) / (iter));
//	}
	
	/**
	 * ajouter une récompense à l'opérateur adéquat
	 * @param op
	 * @param fitness_old
	 * @param fitness_new
	 */
	public static void majReward(Operator op, int fitness_old, int fitness_new) {
		if (fitness_new <= fitness_old) {
			op.addToListeReward(0);
			//System.out.println("Ajout du reward 0 à l'operateur " + op.getName());
		} else {
			op.addToListeReward(fitness_new - fitness_old);
			//System.out.println("Ajout du reward "+((double)fitness_new-fitness_old)+" à l'operateur " + op.getName());
		}
	}

}
