package geneticalgorithm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * @author Chafik
 */
public class Curve {
	/**
	 * création du fichier pour la génération de la courbe fitness/génération de l'AG classique
	 * génération des courbes fitness/génération de l'AG classique et PM
	 * @param nb_iteration
	 * @param fitnessMin
	 * @param fitnessMoy
	 * @param fitnessMax
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void fitnessClassicAG(int nb_iteration, ArrayList<Double> fitnessMin, ArrayList<Double> fitnessMoy, ArrayList<Double> fitnessMax) throws FileNotFoundException, UnsupportedEncodingException {
		String SE = System.getProperty("os.name").toLowerCase();
		if (SE.indexOf("nux") >= 0) {
			PrintWriter writer = new PrintWriter("data_fitnessClassicAG.dat", "UTF-8");
			writer.println("Generation F-Min F-Moy F-Max");
			for (int i=0; i<nb_iteration; i++) {
				writer.println(i +" "+fitnessMin.get(i)+" "+fitnessMoy.get(i)+" "+fitnessMax.get(i));
			}				
			writer.close();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * création du fichier pour la génération de la courbe fitness/génération
	 * @param nb_generation
	 * @param historiqueFitnessMax
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public static void fitnessBandit(int nb_generation, ArrayList<Double> historiqueFitnessMin, ArrayList<Double> historiqueFitnessMoy, ArrayList<Double> historiqueFitnessMax) throws FileNotFoundException, UnsupportedEncodingException {
		String SE = System.getProperty("os.name").toLowerCase();
		if (SE.indexOf("nux") >= 0) {
			PrintWriter writer = new PrintWriter("data_fitness_bandit.dat", "UTF-8");
			writer.println("Generation F-Min F-Moy F-Max");
			for (int i=0; i<nb_generation; i++) {
				writer.println(i +" "+historiqueFitnessMin.get(i)+" "+historiqueFitnessMoy.get(i)+" "+historiqueFitnessMax.get(i));
			}				
			writer.close();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
	}
	
	/**
	 * génération de l'histogramme opérateur/nbr_utilisation
	 * @param mutationoperators
	 * @param historiqueOp
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public static void histogrammeOp(String[] mutationOperators, ArrayList<Double> historiqueOp) throws FileNotFoundException, UnsupportedEncodingException {
		String SE = System.getProperty("os.name").toLowerCase();
		if (SE.indexOf("nux") >= 0) {
			PrintWriter writer = new PrintWriter("data_histogrammeOp.dat", "UTF-8");
			writer.println("Opérateur Nombre d'utilisation");
			for (int i=0; i<mutationOperators.length; i++) {
				writer.println(i +" "+historiqueOp.get(i));
			}				
			writer.close();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Tracer les courbe & histohramme
	 */
	public static void draw() {
		Curve.shellExec("script_fitness");
		Curve.shellExec("script_histogramme");
	}
	
	/**
	 * execution du script
	 * @param file
	 */
	public static void shellExec(String file) {
		Process process = null;
		try {
			String cmd = "sh "+file.toString()+".sh";
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			process.destroy();
		}
	}

}