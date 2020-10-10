package controleur.download;

public class DownloadObserver {
	private Object obj;
	public DownloadObserver() {
		obj=new Object();
	}
	public void actualise(DownloadStatus status, float progression) {
		if(status==DownloadStatus.CONNECTION) {
			System.out.println("Connexion en cours");
		}else if (status==DownloadStatus.DOWNLOADING) {
			String temp = String.valueOf(progression);
			temp = temp.substring(0,temp.indexOf(".")+2);
			System.out.print("Etat du téléchargement "+temp+"%");
			System.out.print("\r");
		}else if(status==DownloadStatus.COMPLETE) {
			//If the download is to fast, you need to wait until the main class received the response
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Téléchargement réussi");
			synchronized (obj){
				obj.notify();
			}
		}
	}
	public Object getObj() {
		return obj;
	}
}
