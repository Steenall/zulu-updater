package controleur;

import zulu.Checker;
import zulu.Zulu;

public class Controleur {
	//private Panneau panneau;
	private Checker checker;
	public Controleur(/*Panneau panneau*/) {
		//this.panneau=panneau;
		checker = new Checker();
	}
	public void update(Zulu current) {
		Zulu latest= checker.getLatest(current);
		checker.downloadZulu(latest, current);
	}
}
