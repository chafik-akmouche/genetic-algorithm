package geneticalgorithm;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Chafik
 */
public class Test {
	/** paramètres */
	public static final int TAILLE_POPULATION = 100;
	public static final int TAILLE_INDIVIDU = 200;
	public static final int MAX_GENERATION = 100;
	public static final int NB_EXECUTION = 1;
	private final static String AOS = "UCB"; //PM
	public static double pMin = 0.05;
	public static final int C = 3;
	
	/** Opérateurs de selection*/
	public static final String S1H = "selection1Hasard";
	public static final String S2H = "selection2Hasard";
	public static final String S1M = "selection1Meilleur";
	public static final String S2M = "selection2Meilleur";
	public static final String S2MS5 = "selection2MeilleurSur5";
	
	/** Opérateurs de remplacement*/
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";	
	public static final String R1MA = "remplacement1Mauvais";
	
	/** Opérateurs de mutation*/
	public static final String M1F = "mutation1Flip";
	public static final String M2F = "mutation2Flip";
	public static final String M3F = "mutation3flip";
	public static final String M5F = "mutation5flip";
	
	/** liste des opérateurs de mutation */
	public static final String[] MutationOperators = {M1F, M2F, M3F, M5F};
	
	/** proba / val init */
	public static double pInitPM = (double)1/MutationOperators.length;
	public static double valInitUCB = 0.0;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<Double> historiqueOp = new ArrayList<Double>();
		for(int i=0; i<MutationOperators.length; i++)
			historiqueOp.add(0.0);
		
		//creation et init liste historique fitness
		ArrayList<Double> historiqueFitnessMax = new ArrayList<Double>();
		ArrayList<Double> historiqueFitnessMoy = new ArrayList<Double>();
		ArrayList<Double> historiqueFitnessMin = new ArrayList<Double>();
		
		int nb_execution = 0;
		while (nb_execution < NB_EXECUTION) {
			System.out.println("### EXECUTION ("+(nb_execution+1) +")");
			if (AOS == "PM") {
				PM.launch(historiqueOp, historiqueFitnessMin, historiqueFitnessMoy, historiqueFitnessMax);
			} else {
				UCB.launch(historiqueOp, historiqueFitnessMin, historiqueFitnessMoy, historiqueFitnessMax, C);
			}
			nb_execution++;
		}
		
		//calculer le nombre moyen d'utilisation de chaque operateur
		for (int i=0; i<historiqueOp.size(); i++) {
			historiqueOp.set(i, historiqueOp.get(i)/NB_EXECUTION);
		}
		
		//courbe fitness 
		Curve.fitnessBandit(historiqueFitnessMax.size(), historiqueFitnessMin, historiqueFitnessMoy, historiqueFitnessMax);
		//lancement de l'AG Classique & courbe fitness
		ClassicAG.launch();
		//histogramme d'utilisation des opérateurs
		Curve.histogrammeOp(MutationOperators, historiqueOp);
	}	
}