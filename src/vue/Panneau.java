package vue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import controleur.Controleur;
import controleur.EcouteurMenus;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import zulu.Checker;
import zulu.Zulu;

public class Panneau extends BorderPane{
	private MenuBar menus;
	private EcouteurMenus ecouteurMenus;
	private SelectVersion selectVersion;
	private Controleur controleur;

	public Panneau() {
		controleur=new Controleur(this);
		try {
			FXMLLoader loader = new FXMLLoader(Panneau.class.getResource("menu.fxml"));
			menus = loader.load();
			setTop(menus);
			ecouteurMenus=new EcouteurMenus();
			ecouteurMenus.setControleur(controleur);
			ecouteurMenus = loader.getController();
		}catch (IOException e) {
			e.printStackTrace();
		}
		selectVersion = new SelectVersion(controleur);
		setCenter(selectVersion);
		initialize();
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
	public void initialize() {
		File link = new File("link.conf");
		if(link.exists()) {
		    Scanner sc;
			try {
				sc = new Scanner(link);
			    String path = sc.nextLine();
			    File releaseFile = new File(path);
				Checker check = new Checker();
				Zulu current = check.getCurrentZulu(releaseFile);
				if(current!=null) {
					controleur.update(current);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
