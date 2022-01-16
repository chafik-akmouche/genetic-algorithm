package kab.geneticalgorithm.utils;

import java.util.Random;

/**
 * 
 * @author Chafik
 *
 */
public class BanditAlgorithm {
	static Random rand = new Random();
	
	public static int MutationOperator (String[] MutationOperators) {
		int oper = 0;
		if(Math.random() < 0.25) {
			oper = 0;
		} 
		else if(Math.random() < 0.5) {
			oper = 1;
		}
		else if(Math.random() < 0.75) {
			oper = 2;
		}
		else if(Math.random() < 1) {
			oper = 3;
		}
		return oper;
	}

}
