package zulu;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import vue.Dialogues;

public class Checker {
	private File zuluAPI;
	public Checker() {
		
	}
	public int getCurrentVersion(File fichier) {
		Scanner sc=null;
		try {
			sc = new Scanner(fichier);
		} catch (FileNotFoundException e) {
			Dialogues.erreur(e).showAndWait();
			e.printStackTrace();
		}
		sc.useDelimiter("\"");
		sc.next();
		String version = sc.next();
		sc.close();
		version = version.substring(version.indexOf("_")+1, version.length());
		return Integer.parseInt(version);
	}
	public int getLatest() {
		try (BufferedInputStream in = new BufferedInputStream(new URL("https://api.azul.com/zulu/download/community/v1.0/bundles/latest/?jdk_version=8&os=windows&arch=x86&hw_bitness=64&features=fx").openStream());
				FileOutputStream fileOutputStream = new FileOutputStream("version.json")) {
				byte dataBuffer[] = new byte[1024];
			    int bytesRead;
			    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			        fileOutputStream.write(dataBuffer, 0, bytesRead);
			    }
				} catch (IOException e) {
					Dialogues.erreur(e).showAndWait();
					e.printStackTrace();
				}
		Scanner sc=null;
		zuluAPI=new File("file.txt");
		try {
			sc = new Scanner(zuluAPI);
		} catch (FileNotFoundException e) {
			Dialogues.erreur(e).showAndWait();
			e.printStackTrace();
		}
		sc.useDelimiter(",");
		boolean end=false;
        while(!end){
            String line = sc.next().toLowerCase().toString();
            if(line.contains("jdk_version")){
                end=true;
                sc.next();
            }
        }
        String version = sc.next();
        sc.close();
		return Integer.parseInt(version);
	}
	public void downloadZulu() {
		// TODO Auto-generated method stub
		if(zuluAPI!=null) {
			boolean end=false;
			Scanner sc;
			try {
				sc = new Scanner(zuluAPI);
				sc.useDelimiter(",");
				String line = "";
				while(!end){
		            line = sc.next().toLowerCase().toString();
		            if(line.contains("url")){
		                end=true;
		            }
		        }
				sc.close();
				line = line.substring(line.indexOf(":")+2,line.length()-1);
				try {
					Desktop.getDesktop().browse(new URI(line));
				} catch (IOException e) {
					Dialogues.erreur(e).showAndWait();
					e.printStackTrace();
				} catch (URISyntaxException e) {
					Dialogues.erreur(e).showAndWait();
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				Dialogues.erreur(e).showAndWait();
				e.printStackTrace();
			}
		}
		
	}
}
