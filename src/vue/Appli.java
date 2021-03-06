package vue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controleur.Controleur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import zulu.Checker;
import zulu.Zulu;

public class Appli extends Application {
	public static void prepareAppli(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		File link = new File("link.conf");
		File link2 = new File("release");
		if(link.exists()) {
		    Scanner sc;
			try {
				sc = new Scanner(link);
			    String path = sc.nextLine();
			    File releaseFile = new File(path);
				Checker check = new Checker();
				Zulu current = check.getCurrentZulu(releaseFile);
				if(current!=null) {
					(new Controleur()).update(current);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else if(link2.exists()){
			Checker check = new Checker();
			Zulu current = check.getCurrentZulu(link2);
			if(current!=null) {
				(new Controleur()).update(current);
			}
		}
		else {
			Panneau panneau = new Panneau();
		    Scene scene = new Scene(panneau);
		    stage.setScene(scene);
		    stage.setWidth(600);
		    stage.setHeight(400);
		    stage.setTitle("Zulu updater");
		    stage.show();			
		}
	}

}
