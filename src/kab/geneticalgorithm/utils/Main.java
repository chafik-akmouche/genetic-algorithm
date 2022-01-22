package kab.geneticalgorithm.utils;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Chafik
 */
public class Main {
	/** initialisation*/
	public static final int TAILLE_POPULATION = 80;
	public static final int TAILLE_INDIVIDU = 60;
	public static final int MAX_GENERATION = 50;
	public static final int NB_EXECUTION = 10;
	
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
	
	/** proba init & proba min */
	public static double pInit = (double)1/MutationOperators.length;
	public static double pMin = 0.05;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//init liste historique operateurs
		ArrayList<Integer> hisOp = new ArrayList<Integer>();
		ArrayList<Integer> historiqueOp = new ArrayList<Integer>();
		for(int i=0; i<MutationOperators.length; i++) {
			historiqueOp.add(0);
		}
		
		//init liste fitness
		ArrayList<Double> historiqueFitness = new ArrayList<Double>();
		for (int i=0; i<=MAX_GENERATION; i++) {
			historiqueFitness.add(0.0);
		}
		
		int nb_execution = 0;
		while (nb_execution < NB_EXECUTION) {
			System.out.println("############################ EXECUTION ("+(nb_execution+1) +") ############################");
			PM.launche(historiqueOp, hisOp, historiqueFitness);
			nb_execution++;
		}
		
		for (int i=0; i<historiqueOp.size(); i++) {
			historiqueOp.set(i, historiqueOp.get(i)/NB_EXECUTION);
		}
		
		for(int i=0; i<historiqueFitness.size(); i++) {
			historiqueFitness.set(i, (historiqueFitness.get(i)/NB_EXECUTION));
		}
		
		//affichage des courbes
		Curve.draw2(MAX_GENERATION, historiqueFitness, historiqueOp);
		Curve.draw3(MAX_GENERATION, hisOp);
		
		//lancement de l'AG Classique
		ClassicAG.lanche();
	}	
}