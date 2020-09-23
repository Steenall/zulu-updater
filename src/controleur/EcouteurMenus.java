package controleur;

import javafx.event.ActionEvent;
import vue.Dialogues;

public class EcouteurMenus {
	private Controleur controleur;
	public void setControleur(Controleur controleur) {
		this.controleur=controleur;
	}
	public void onSave(ActionEvent actionEvent) {
		
	}
	public void onFermer(ActionEvent actionEvent) {
		System.exit(0);
	}
	public void onAPropos(ActionEvent actionEvent) {
		Dialogues.aPropos().showAndWait();
	}
}
