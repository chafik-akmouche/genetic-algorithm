package geneticalgorithm;
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
		} else {
			op.addToListeAmelioration((double)(fitness_new-fitness_old));
		}
	}
	
	/**
	 * ajouter une récompense à l'opérateur adéquat
	 * @param op
	 * @param fitness_old
	 * @param fitness_new
	 */
	public static void majReward(Operator op, int fitness_old, int fitness_new) {
		if (fitness_new <= fitness_old) {
			op.addToListeReward(0);
		} else {
			op.addToListeReward(fitness_new - fitness_old);
		}
	}
}