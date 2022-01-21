package kab.geneticalgorithm.utils;
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
	 * création du script générant les graphes + test du système d'exploitation
	 * @param nb_iteration
	 * @param fitnessMin
	 * @param fitnessMoy
	 * @param fitnessMax
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void draw(int nb_iteration, ArrayList<Double> fitnessMin, ArrayList<Double> fitnessMoy, ArrayList<Double> fitnessMax) throws FileNotFoundException, UnsupportedEncodingException {
		int afficherCourbe = JOptionPane.showOptionDialog(
				null, "Voulez-vous visualiser la courbe Fitness/Iteration ?", "Courbe Fitness",
	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (afficherCourbe == 0) {
			String SE = System.getProperty("os.name").toLowerCase();
			if (SE.indexOf("nux") >= 0) {
				PrintWriter writer = new PrintWriter("data.dat", "UTF-8");
				writer.println("Iteration F-Min F-Moy F-Max");
				for (int i=0; i<nb_iteration; i++) {
					writer.println(i +" "+fitnessMin.get(i)+" "+fitnessMoy.get(i)+" "+fitnessMax.get(i));
				}				
				writer.close();
				Curve.shellExec();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE,
				        JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
	/**
	 * execution du script
	 */
	public static void shellExec () {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("sh script.sh");
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