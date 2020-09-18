package vue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import zulu.Checker;

public class Dialogues {
	public static Alert upToDate() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Version déjà à jour");
		alert.setContentText("Votre version est déjà à jour");
		return alert;
	}
	public static Alert newVersion() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Nouvelle version disponible");
		alert.setContentText("Voulez vous télécharger la nouvelle version ?");
		return alert;
	}
	public static void miseAJour() {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Ouvrir un fichier de version");
	    fileChooser.getExtensionFilters().addAll(
	    		new ExtensionFilter("Fichier release","release"),
	    		new ExtensionFilter("Tous les fichiers","*"));
		Checker check = new Checker();
		int current = check.getCurrentVersion(fileChooser.showOpenDialog(null));
		int latest = check.getLatest();
		if(current!=latest) {
			if(Dialogues.newVersion().showAndWait().get().equals(ButtonType.YES))
				check.downloadZulu();
			System.exit(0);
		}
		else {
			Dialogues.upToDate().showAndWait();
			System.exit(0);
		}
	}
	public static Alert erreur(Exception exception) {
		Alert alert = new Alert(AlertType.ERROR);
		if(exception instanceof FileNotFoundException) {
			alert.setTitle("Impossible de trouver le fichier");
			alert.setContentText("Il semblerait que l'application n'a pas accès en lecture ou en écriture dans le dossier de l'application.");
		}
		else if(exception instanceof IOException) {
			alert.setTitle("Erreur interne");
			alert.setContentText("Une erreur interne est survenu.");
		}
		else if(exception instanceof URISyntaxException) {
			alert.setTitle("Erreur réseau");
			alert.setContentText("Impossible de se connecter à l'API de Azul, cela peut venir de votre connexion ou des serveurs d'Azul");	
		}else {
			alert.setTitle("Erreur inconnu");
			alert.setContentText("Une erreur inconnu c'est produite");
		}
		return alert;
	}
}
