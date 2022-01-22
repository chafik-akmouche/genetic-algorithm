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
	 * affichage de la courbe fitness/génération
	 * @param nb_iteration
	 * @param fitnessMin
	 * @param fitnessMoy
	 * @param fitnessMax
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void draw1(int nb_iteration, ArrayList<Double> fitnessMin, ArrayList<Double> fitnessMoy, ArrayList<Double> fitnessMax) throws FileNotFoundException, UnsupportedEncodingException {
		String SE = System.getProperty("os.name").toLowerCase();
		if (SE.indexOf("nux") >= 0) {
			PrintWriter writer = new PrintWriter("data1.dat", "UTF-8");
			writer.println("Generation F-Min F-Moy F-Max");
			for (int i=0; i<nb_iteration; i++) {
				writer.println(i +" "+fitnessMin.get(i)+" "+fitnessMoy.get(i)+" "+fitnessMax.get(i));
			}				
			writer.close();
			Curve.shellExec("script1");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * affichage de la courbe fitness par opérateur / génération
	 * @param nb_generation
	 * @param fitnessMax
	 * @param historiqueOp
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void draw2(int nb_generation, ArrayList<Double> fitnessMax, ArrayList<Integer> historiqueOp) throws FileNotFoundException, UnsupportedEncodingException {
		int som = 0;
		for (int s=0; s<historiqueOp.size(); s++) {
			som = som + historiqueOp.get(s);
		}
		String SE = System.getProperty("os.name").toLowerCase();
		if (SE.indexOf("nux") >= 0) {
			PrintWriter writer = new PrintWriter("data2.dat", "UTF-8");
			writer.println("Generation F(M1F) F(M2F) F(M3F) F(M5F)");
			for (int i=0; i<nb_generation; i++) {
				writer.print(i+" ");
				for (int j=0; j<historiqueOp.size(); j++) {
					if(historiqueOp.get(j) == 0) {
						writer.print(0+" ");
					} else {
						//writer.print( (fitnessMax.get(i) / som) * historiqueOp.get(j) + " ");
						//writer.print( fitnessMax.get(i) * historiqueOp.get(j) + " ");
						writer.print( (fitnessMax.get(i) / historiqueOp.get(j)) + " " );
					}
				}
				writer.print("\n");
			}
			writer.close();
			Curve.shellExec("script2");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * affichage nbr utilisation de chaque opérateur / génération 
	 * @param nb_generation
	 * @param historiqueOp
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void draw3(int nb_generation, ArrayList<Integer> historiqueOp) throws FileNotFoundException, UnsupportedEncodingException {
		String SE = System.getProperty("os.name").toLowerCase();
		if (SE.indexOf("nux") >= 0) {
			PrintWriter writer = new PrintWriter("data3.dat", "UTF-8");
			writer.println("Generation M1F M2F M3F M5F");
				int i = 0;
				int s = 0;
				for (int j=0; j<historiqueOp.size(); j++) {
					if(j == 0) {
						writer.print( i + " " + historiqueOp.get(j) + " ");
						i++;	
						s++;
					} else {
						writer.print( historiqueOp.get(j) + " ");
						s++;
					}
					if (s == 4) {
						if ( i < nb_generation ) {
							writer.print( "\n" + i + " ");
							i++;	
							s = 0;
						}
					}
				}
			writer.close();
			Curve.shellExec("script3");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Vous êtes sur "+SE+".\nNous ne pouvons malheureusement pas générer la courbe.", "Erreur - "+SE, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
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