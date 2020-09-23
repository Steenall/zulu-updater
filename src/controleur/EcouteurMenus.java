package controleur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.event.ActionEvent;
import vue.Dialogues;
import zulu.Checker;
import zulu.Zulu;

public class EcouteurMenus {
	private Controleur controleur;
	public void setControleur(Controleur controleur) {
		this.controleur=controleur;
	}
	public void onSave(ActionEvent actionEvent) {
		File releaseFile = Dialogues.getReleaseFile();
		File link = new File("link.conf");
		try (FileOutputStream fop = new FileOutputStream(link)) {
			if (!link.exists()) {
				link.createNewFile();
			}
			byte[] contentInBytes = releaseFile.getAbsolutePath().getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			Dialogues.erreur(e);
			e.printStackTrace();
		}
		Dialogues.Redemarrer().showAndWait();
		System.exit(0);
	}
	public void onFermer(ActionEvent actionEvent) {
		System.exit(0);
	}
	public void onAPropos(ActionEvent actionEvent) {
		Dialogues.aPropos().showAndWait();
	}
}
