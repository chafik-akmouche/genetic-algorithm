package kab.geneticalgorithm.utils;

import java.io.IOException;

/**
 * 
 * @author Chafik
 *
 */
public class CurveTracer {
	
	public CurveTracer () {}
	
	public void draw () {
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

