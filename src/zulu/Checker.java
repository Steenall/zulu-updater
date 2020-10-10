package zulu;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import controleur.download.Download;
import controleur.download.DownloadObserver;
import vue.Dialogues;

public class Checker extends DownloadObserver{
	
	private static final String useJDK ="jdk_version";
	private static final String useZulu ="zulu_version";
	
	private static final String fileNameVersion ="version.json";
	
	public Checker() {
		
	}
	public Zulu getCurrentZulu(File fichier) {
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
				if(line.contains("JAVA_VERSION=")) {
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
				}else if(line.contains("OS_NAME=")) {
					current.setOs(line.substring(line.indexOf("\"")+1,line.length()-1));
				}else if(line.contains("OS_ARCH=")) {
					current.setArchitecture(line.substring(line.indexOf("\"")+1,line.length()-1));
				}else if(line.contains("IMPLEMENTOR_VERSION=")) {
					current.setBuildId(line.substring(line.indexOf(".")+1, line.indexOf("-")));
				}
			}
			sc.close();
			FilenameFilter isJDK = new FilenameFilter(){
				public boolean accept(File dir, String name) {
					return name.equals("jmods")||name.equals("jre");
				}
			};
			FilenameFilter isFX = new FilenameFilter(){
				public boolean accept(File dir, String name) {
					return name.contains("OPENJFX");
				}
			};
			boolean jdk=false;
			boolean fx=false;
			if(fichier.getParentFile().list(isJDK).length>0)jdk=true;
			if(fichier.getParentFile().list(isFX).length>0)fx=true;
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
		System.out.println(current.toString());
		return current;
	}
	public Zulu getLatest(Zulu current) throws IOException {
		Zulu latest=current.clone();
		Download.createDownload(new URL(latest.getURL()), this, fileNameVersion);
		synchronized (getObj()){
			try {
				getObj().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		File zuluAPI = new File(fileNameVersion);
		Scanner sc = new Scanner(zuluAPI);
		sc.useDelimiter(",");
		boolean end=false;
		String tempSearch;
		if(current.getJavaVersion().equals(JavaVersion.java_8))tempSearch=useJDK;
		else tempSearch=useZulu;
        while(!end){
        	String line = sc.next().toLowerCase().toString();
        	if(line.contains(tempSearch)){
        		end=true;
        	}
        }
        if(current.getJavaVersion().equals(JavaVersion.java_8)) {
        	sc.next();
            latest.setBuildId(sc.next());
        }else {
            latest.setBuildId(sc.next()+"+"+sc.next().replace("]", ""));
        }
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
		
	}
	public void downloadZuluConsole(Zulu latest, Zulu current) {
		if(latest.getBuildId().equals(current.getBuildId())) {
			System.out.println("Votre version actuelle est à jour");
			System.exit(0);
		}else {
			System.out.println("Une mise à jour est disponbile, voulez-vous la télécharger ?");
			System.out.println("Y : Oui, N : Non");
			Scanner sc = new Scanner(System.in);
			String reponse;
			boolean err=false;
			do {
				if(err)
					System.out.println("Veuillez rentrez Y ou N");
				reponse=sc.nextLine();
				err=true;
			}while(!reponse.equals("y")||!reponse.equals("Y")||!reponse.equals("n")||!reponse.equals("N"));
			sc.close();
			if(reponse.equals("y")||reponse.equals("Y")) {
				try {
					Desktop.getDesktop().browse(new URI(latest.getDownloadURL()));
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
