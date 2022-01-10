package kab.geneticalgorithm.utils;

import java.io.IOException;

public class Main {
	/**
	 * initialisation
	 */
	public static final int TAILLE_POPULATION = 80;
	public static final int TAILLE_INDIVIDU = 80;
	public static final int NOMBRE_ITERATION = 40;
	
	/** 
	 * Opérateurs de selection :
	 */
	public static final String S2H = "selection2Hasard";
	public static final String S2M = "selection2Meilleur";
	public static final String S2MS5 = "selection2MeilleurSur5";
	public static final String S1H = "selection1Hasard";
	public static final String S1M = "selection1Meilleur";
	
	/**
	 * Opérateurs de remplacement :
	 */
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";	
	public static final String R1MA = "remplacement1Mauvais";
	
	/**
	 * Opérateurs de mutation :	
	 */
	public static final String M1F = "mutation1Flip";
	public static final String M2F = "mutation2Flip";
	public static final String M3F = "mutation3flip";
	public static final String M5F = "mutation5flip";
	//public static final String[] MutationOperators = {"mutation1Flip", "mutation2Flip", "mutation3flip", "mutation5flip"};
	
	/**
	 * Main Method
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {		
		Launcher.run(S2M, R2MA, M2F);
	}
	
}
