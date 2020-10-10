package vue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import zulu.Architecture;
import zulu.JavaPackage;
import zulu.JavaVersion;
import zulu.OS;
import zulu.Zulu;

public class SelectVersion extends GridPane{
	
	private ChoiceBox<String> choixVersion;
	private ChoiceBox<OS> choixOs;
	private ChoiceBox<Architecture> choixArchitecture;
	private ChoiceBox<JavaPackage> choixJDK;
	
	private String version="0";
	
	public SelectVersion(Controleur controleur) {
		ArrayList<String> listVersion = new ArrayList<String>();
		for(JavaVersion i:JavaVersion.values())
			listVersion.add(i.getVersion()+" "+i.getDureeDeVie());
		choixVersion = new ChoiceBox<String>(FXCollections.observableArrayList(listVersion));
		choixOs = new ChoiceBox<OS>(FXCollections.observableArrayList(OS.values()));
		choixArchitecture = new ChoiceBox<Architecture>(FXCollections.observableArrayList(Architecture.values()));
		choixJDK = new ChoiceBox<JavaPackage>(FXCollections.observableArrayList(JavaPackage.values()));
		Button load = new Button("Importer votre configuration");
		Button update = new Button("Mettre à jour");
		load.setOnAction(e->{
			Zulu current = Dialogues.loadConfig();
			if(current!=null) {
				choixVersion.setValue(current.getJavaVersion().getVersion()+" "+current.getJavaVersion().getDureeDeVie());
				choixArchitecture.setValue(current.getArchitecture());
				choixOs.setValue(current.getOs());
				choixJDK.setValue(current.getJavaPackage());
				version=current.getBuildId();
				update.fire();
			}
		});
		update.setOnAction(e->{
			Zulu current = new Zulu();
			current.setArchitecture(choixArchitecture.getValue());
			current.setJavaPackage(choixJDK.getValue());
			current.setOs(choixOs.getValue());
			current.setBuildId(version);
			current.setJavaVersion(Integer.parseInt(choixVersion.getValue().substring(0, choixVersion.getValue().indexOf(" "))));
			try {
				controleur.update(current);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				Dialogues.erreur(e1).showAndWait();
			} catch (IOException e1) {
				e1.printStackTrace();
				Dialogues.erreur(e1).showAndWait();
			}
		});
		choixJDK.setValue(JavaPackage.JDK_FX);
		choixVersion.setValue(listVersion.get(0));
		String os = System.getProperty("os.name").toLowerCase();
		if(OS.Windows.getNom().equals(os)) 
			choixOs.setValue(OS.Windows);
		else if(OS.MacOS.getNom().equals(os)) 
			choixOs.setValue(OS.MacOS);
		else if(OS.Linux.getNom().equals(os)) 
			choixOs.setValue(OS.Linux);
		else
			choixOs.setValue(OS.Windows);
		String arch = System.getProperty("os.arch");
		if(arch.contains("amd64")||arch.contains("x86_64"))
			choixArchitecture.setValue(Architecture.x86_64);
		else if(arch.contains("x86")||arch.contains("i386")||arch.contains("i486")||arch.contains("i586")||arch.contains("i686"))
			choixArchitecture.setValue(Architecture.x86);
		else if(arch.contains("aarch64"))
			choixArchitecture.setValue(Architecture.arm64);
		else if(arch.contains("arm"))
			choixArchitecture.setValue(Architecture.arm32);
		else
			choixArchitecture.setValue(Architecture.x86_64);
		add(new Label("Version de java :"),0,0);
		add(choixVersion,1,0);
		add(new Label("OS :"),2,0);
		add(choixOs,3,0);
		add(new Label("Architecture :"),0,1);
		add(choixArchitecture,1,1);
		add(new Label("Type de jdk :"),2,1);
		add(choixJDK,3,1);
		add(update,3,4);
		add(load,1,4);
		setAlignment(Pos.CENTER);
		setHgap(10.0);
		setVgap(10.0);
	}
}
