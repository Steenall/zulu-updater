package controleur;

import java.io.FileNotFoundException;
import java.io.IOException;

import zulu.Checker;
import zulu.Zulu;

public class Controleur {
	//private Panneau panneau;
	private Checker checker;
	public Controleur(/*Panneau panneau*/) {
		//this.panneau=panneau;
		checker = new Checker();
	}
	public void update(Zulu current) throws FileNotFoundException, IOException {
		Zulu latest= checker.getLatest(current);
		checker.downloadZulu(latest, current);
	}
	public void updateConsole(Zulu current) throws FileNotFoundException, IOException {
		Zulu latest= checker.getLatest(current);
		checker.downloadZuluConsole(latest, current);
	}
}
