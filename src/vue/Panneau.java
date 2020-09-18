package vue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font; 

public class Panneau extends BorderPane{
	public Panneau() {
		Button verifier = new Button("Mettre à jour");
		verifier.setMinSize(80, 40);
		verifier.setFont(new Font(14));
		verifier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Dialogues.miseAJour();
            }
		});
		setCenter(verifier);
	}
}
