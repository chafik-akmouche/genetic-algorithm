package geneticalgorithm;

/**
 * @author Chafik
 */
public class Reward {
	/**
	 * ajouter l'amélioration à l'opérateur adéquat
	 * 
	 * @param op
	 * @param fitness_old
	 * @param fitness_new
	 */
	public static void majAmelioration(Operator op, double fitness_old, double fitness_new) {
		if (fitness_new <= fitness_old)
			op.addToListeAmelioration(0.0);
		else
			op.addToListeAmelioration((double) (fitness_new - fitness_old));
	}

	/**
	 * ajouter une récompense à l'opérateur adéquat
	 * 
	 * @param op
	 * @param fitness_old
	 * @param fitness_new
	 */
	public static void majReward(Operator op, int index, double amelioration) {
		op.getListeReward().add(index,
				(((op.getNb_fois() - 1) * op.getListeReward().get(index - 1)) + amelioration) / op.getNb_fois());
	}
}