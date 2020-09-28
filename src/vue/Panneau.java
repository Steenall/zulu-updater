package vue;

import java.io.IOException;
import controleur.Controleur;
import controleur.EcouteurMenus;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

public class Panneau extends BorderPane{
	private MenuBar menus;
	private EcouteurMenus ecouteurMenus;
	private SelectVersion selectVersion;
	private Controleur controleur;

	public Panneau() {
		controleur=new Controleur(/*this*/);
		try {
			FXMLLoader loader = new FXMLLoader(Panneau.class.getResource("menu.fxml"));
			menus = loader.load();
			setTop(menus);
			ecouteurMenus=new EcouteurMenus();
			//ecouteurMenus.setControleur(controleur);
			ecouteurMenus = loader.getController();
		}catch (IOException e) {
			e.printStackTrace();
		}
		selectVersion = new SelectVersion(controleur);
		setCenter(selectVersion);
		//initialize();
	}

	public EcouteurMenus getEcouteurMenus() {
		return ecouteurMenus;
	}

	public SelectVersion getSelectVersion() {
		return selectVersion;
	}

	public Controleur getControleur() {
		return controleur;
	}
}
