package geneticalgorithm;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Chafik
 */
public class Test {
	/*********************************************************
	 **************** OPÉRATEURS EXISTANTS *******************
	 *********************************************************/
	/** Opérateurs de selection */
	public static final String S1H = "selection1Hasard";
	public static final String S2H = "selection2Hasard";
	public static final String S1M = "selection1Meilleur";
	public static final String S2M = "selection2Meilleur";
	public static final String S2MS5 = "selection2MeilleurSur5";
	/** Opérateur de croisement */
	public static final String CS = "croisementSimple"; // l'enfant va prendre soit les genes du parent1 soit ceux
														// parent2
	public static final String CMP = "croisementMonoPoint";
	public static final String CU = "croisementUniforme";
	/** Opérateurs de remplacement */
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";
	public static final String R1MA = "remplacement1Mauvais";
	/** Opérateurs de mutation */
	public static final String M1F = "mutation1Flip";
	public static final String M2F = "mutation2Flip";
	public static final String M3F = "mutation3flip";
	public static final String M5F = "mutation5flip";
	public static final String[] MutationOperators = { M1F, M2F, M3F, M5F };

	/*********************************************************
	 ************* PARAMÈTRES DE L'ALGORITHME ****************
	 *********************************************************/
	public static final int TAILLE_POPULATION = 300;
	public static final int TAILLE_INDIVIDU = 300;
	public static final int MAX_GENERATION = 100;
	public static final int NB_EXECUTION = 4;
	public static final double PROBA_MUTATION = 0.25;
	public final static String AOS = "PM"; // UCB
	public static double pMin = 0.05; // pour PM
	public static final int C = 3; // pour UCB
	/** proba / val init */
	public static double pInitPM = (double) 1 / MutationOperators.length; // pour PM
	public static double valInitUCB = 0.0; // Pour UCB
	/** Paramètres pour l'AG Classique & AOS */
	public static final String selection = S2M;
	public static final String croisement = CMP;
	public static final String mutation = M2F;
	public static final String remplacement = R2MA;

	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<Double> historiqueOp = new ArrayList<Double>();
		for (int i = 0; i < MutationOperators.length; i++)
			historiqueOp.add(0.0);
		
		// liste utilisation des opérateurs & initialisation
		//ArrayList<Integer> listeUtilisationOp = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> listeUtilisationOp = new ArrayList<ArrayList<Integer>>();
		
		// listes historique fitness Bandit
		ArrayList<Double> historiqueFitnessMax = new ArrayList<Double>();
		ArrayList<Double> historiqueFitnessMoy = new ArrayList<Double>();
		ArrayList<Double> historiqueFitnessMin = new ArrayList<Double>();

		// listes historique fitness AG classique
		ArrayList<Double> historiqueFitnessMaxAGC = new ArrayList<Double>();
		ArrayList<Double> historiqueFitnessMoyAGC = new ArrayList<Double>();
		ArrayList<Double> historiqueFitnessMinAGC = new ArrayList<Double>();

		int nb_execution = 0;
		while (nb_execution < NB_EXECUTION) {
			Bandit.launch(selection, croisement, remplacement, historiqueOp, historiqueFitnessMin, historiqueFitnessMoy,
					historiqueFitnessMax, C, nb_execution, AOS, listeUtilisationOp);
			nb_execution++;
		}		

		// calculer le nombre moyen d'utilisation de chaque operateur
		for (int i = 0; i < historiqueOp.size(); i++) {
			historiqueOp.set(i, historiqueOp.get(i) / NB_EXECUTION);
		}

		// sauvegarde des fitness (bandit) dans un fichier
		Curve.fitnessBandit(historiqueFitnessMin.size(), historiqueFitnessMin, historiqueFitnessMoy,
				historiqueFitnessMax);
		
		Curve.nbUtilisationOp(listeUtilisationOp.get(0).size(), listeUtilisationOp);

		// AG Classique
		int nb_exec = 0;
		while (nb_exec < NB_EXECUTION) {
			ClassicAG.launch(nb_exec, selection, croisement, mutation, R2M, historiqueFitnessMinAGC,
					historiqueFitnessMoyAGC, historiqueFitnessMaxAGC);
			nb_exec++;
		}
		// sauvegarde des fitness (AG classique) dans un fichier
		Curve.fitnessClassicAG(historiqueFitnessMinAGC.size(), historiqueFitnessMinAGC, historiqueFitnessMoyAGC,
				historiqueFitnessMaxAGC);

		// sauvegarde nbr d'utilisation des opérateurs dans un fichier
		Curve.histogrammeOp(MutationOperators, historiqueOp);

		// tracer les courbes
		Curve.draw();
	}
}