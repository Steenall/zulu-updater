package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Panneau panneau = new Panneau();
	    Scene scene = new Scene(panneau);
	    stage.setScene(scene);
	    stage.setWidth(300);
	    stage.setHeight(200);
	    stage.setTitle("Zulu updater");
	    stage.show();
		
	}

}
