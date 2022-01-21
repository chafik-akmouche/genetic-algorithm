package kab.geneticalgorithm.utils;

import java.util.ArrayList;

public class Operator {
	private String name;
	private double proba;
	private int nb_fois;
	private ArrayList<Integer> listeReward;
	private ArrayList<Double> listeAmelioration;
	private ArrayList<Integer> generations;
	
	public Operator (String name, double proba, int nb_fois) {
		this.name = name;
		this.proba = proba;
		this.nb_fois = nb_fois;
		this.listeReward = new ArrayList<Integer>();
		this.listeAmelioration = new ArrayList<Double>();
		this.generations = new ArrayList<Integer>();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getProba() {
		return proba;
	}

	public void setProba(double proba) {
		this.proba = proba;
	}

	public int getNb_fois() {
		return nb_fois;
	}

	public void setNb_fois(int nb_fois) {
		this.nb_fois = nb_fois;
	}
	
	public void incNb_fois() {
		this.nb_fois++;
	}

	public ArrayList<Integer> getListeReward() {
		return listeReward;
	}

	public void addToListeReward(int reward) {
		this.listeReward.add(reward);
	}

	public ArrayList<Double> getListeAmelioration() {
		return listeAmelioration;
	}

	public void addToListeAmelioration(double amelioration) {
		this.listeAmelioration.add(amelioration);
	}

	public ArrayList<Integer> getGenerations() {
		return generations;
	}
	
	public void addToGenerations(int generation) {
		this.generations.add(generation);
	}
}
