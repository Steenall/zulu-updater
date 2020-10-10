package vue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import controleur.Controleur;
import zulu.Checker;
import zulu.Zulu;

public class Main {
	private static boolean updateCheckConsole(File link) {
		if(link.exists()) {
			Checker check = new Checker();
			Zulu current = check.getCurrentZulu(link);
			if(current!=null) {
				try {
					(new Controleur()).updateConsole(current);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.err.println("Un fichier téléchargé n'a pas été retrouvé");
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Un problème est survenu lors du téléchargement des informations de mise à jour, il peut s'agir d'un problème avec l'API d'Azul ou un problème de génération du lien de téléchargement");
				}
			}
			return true;
		}else {
			return false;
		}
	}
	public static void main(String[] args) {
		if(args.length>0&&args[0].equals("-c")) {
			File link;
			if(args.length>1) {
				link = new File(args[1]);
				if(!updateCheckConsole(link))
					System.out.println("Le fichier spécifié est introuvable");
				else {
					System.out.println("Voulez-vous sauvegarder l'emplacement spécifié ? [Y/N]");
					Scanner sc = new Scanner(System.in);
					String reponse;
					do{
						reponse = sc.nextLine();
					}while(!reponse.toLowerCase().equals("y")||!reponse.toLowerCase().equals("n"));
					sc.close();
					if(reponse.toLowerCase().equals("y")) {
						File config = new File("link.conf");
						try (FileOutputStream fop = new FileOutputStream(config)) {
							if (!config.exists()) {
								config.createNewFile();
							}
							byte[] contentInBytes = link.getAbsolutePath().getBytes();
							fop.write(contentInBytes);
							fop.flush();
							fop.close();
						} catch (IOException e) {
							Dialogues.erreur(e);
							e.printStackTrace();
						}
					}
				}
			}else {
				File release = new File("release");
				if(!updateCheckConsole(release)) {
					link = new File("link.conf");
					if(!updateCheckConsole(release)) {
						System.err.println("Veuillez spécifié un chemin vers votre JDK");
					}
				}
			}
		}
		else{
			Appli.prepareAppli(args);
		}
	}
}
