package kab.geneticalgorithm.utils;

import java.io.IOException;

public class Main {
	public static final int TAILLE_POPULATION = 1000;
	public static final int TAILLE_INDIVIDU = 100;
	public static final int NOMBRE_ITERATION = 10;
	
	// Methodes de selection :
	public static final String S2H = "selection2Hasard";
	public static final String S2M = "selection2Meilleur";
	public static final String S2MS5 = "selection2MeilleurSur5";
	public static final String S1H = "selection1Hasard";
	public static final String S1M = "selection1Meilleur";
	
	// Methodes de remplacement :
	public static final String R2MA = "remplacement2Mauvais";
	public static final String R2M = "remplacement2Meilleur";	
	public static final String R1MA = "remplacement1Mauvais";
	
	// Methodes de remplacement :
	public static final String M1f = "mutation1Flip";
	public static final String M2f = "mutation2Flip";	
	public static final String Mkf = "mutationKflip";	
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		Launcher.operateur(S2M, R2MA, M2f, 4);
	}
	
}
