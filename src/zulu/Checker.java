package zulu;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
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
	public Zulu getCurrentVersion(File fichier) {
		Scanner sc=null;
		Zulu current=null;
		try {
			sc = new Scanner(fichier);
			if(fichier==null) {
				sc.close();
				throw new FileNotFoundException();
			}
			current = new Zulu();
			String line;
			while(sc.hasNextLine()) {
				 line = sc.nextLine();
				if(line.contains("JAVA_VERSION")) {
					line =line.substring(line.indexOf("\"")+1, line.length()-1);
					if(!line.contains(".")) {
						current.setJavaVersion(Integer.parseInt(line));
					}else {
						if(line.contains("1.8")) {
							current.setJavaVersion(8);
							current.setBuildId(line.substring(line.indexOf("_")+1));
						}else {
							current.setJavaVersion(Integer.parseInt(line.substring(0,line.indexOf("."))));
						}
					}
				}else if(line.contains("OS_NAME")) {
					current.setOs(line.substring(line.indexOf("\"")+1,line.length()-1));
				}else if(line.contains("OS_ARCH")) {
					current.setArchitecture(line.substring(line.indexOf("\"")+1,line.length()-1));
				}else if(line.contains("IMPLEMENTOR_VERSION")) {
					current.setBuildId(line.substring(line.indexOf(".")+1, line.indexOf("-")-1));
				}
			}
			sc.close();
			FilenameFilter isJDK = new FilenameFilter(){
				public boolean accept(File dir, String name) {
					return name.equals("jre");
				}
			};
			FilenameFilter isFX = new FilenameFilter(){
				public boolean accept(File dir, String name) {
					return name.contains("OPENJFX");
				}
			};
			boolean jdk=false;
			boolean fx=false;
			if(fichier.getParentFile().list(isJDK)!=null)jdk=true;
			if(fichier.getParentFile().list(isFX)!=null)fx=true;
			if(jdk) {
				if(fx)
					current.setJavaPackage(JavaPackage.JDK_FX);
				else
					current.setJavaPackage(JavaPackage.JDK);
			}else {
				if(fx)
					current.setJavaPackage(JavaPackage.JRE_FX);
				else
					current.setJavaPackage(JavaPackage.JRE);				
			}
			sc = new Scanner(fichier.getPath());
		} catch (FileNotFoundException e) {
			Dialogues.erreur(e).showAndWait();
			e.printStackTrace();
		} catch(NullPointerException e) {
			
		}
		return current;
	}
	public Zulu getLatest(Zulu current) {
		Zulu latest=current.clone();
		try (BufferedInputStream in = new BufferedInputStream(new URL(current.getURL()).openStream());
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
		zuluAPI=new File("version.json");
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
        latest.setBuildId(sc.next());
        sc.close();
        return latest;
	}
	public void downloadZulu(Zulu latest, Zulu current) {
		if(latest.getBuildId().equals(current.getBuildId())) {
			Dialogues.upToDate().showAndWait();
			System.exit(0);
		}else {
			if(Dialogues.newVersion()) {
				try {
					Desktop.getDesktop().browse(new URI(latest.getDownloadURL()));
					System.exit(0);
				} catch (IOException e) {
					Dialogues.erreur(e).showAndWait();
					e.printStackTrace();
				} catch (URISyntaxException e) {
					Dialogues.erreur(e).showAndWait();
					e.printStackTrace();
				}
			}
		}
		/*if(zuluAPI!=null) {
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
		}*/
		
	}
}
