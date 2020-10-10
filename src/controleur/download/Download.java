package controleur.download;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Thanks to java-tips.org for providing an example of a download manager
 * https://www.java-tips.org/java-se-tips-100019/15-javax-swing/1391-how-to-create-a-download-manager-in-java.html
 */
public class Download implements Runnable {
	
	private static final int MAX_BUFFER_SIZE = 1024;
     
	private URL url;
	private int size;
	private int downloaded;
	private DownloadStatus status;
	private String name;
	private Object obj;
	
	private DownloadObserver observer;
	
	private Download(URL url, DownloadObserver observer, String name) {
		this.url = url;
		this.observer = observer;
		size = -1;
		downloaded = 0;
		status = DownloadStatus.CONNECTION;
		this.name = name;
		obj = new Object();
		download();
	}
	public static void createDownload(URL url, DownloadObserver observer, String name) {
		new Download(url, observer,name);
	}
	public String getUrl() {
		return url.toString();
	}
	
	public int getSize() {
		return size;
	}
	private float getProgress() {
		return ((float) downloaded / size) * 100;
	}
	
	public DownloadStatus getStatus() {
		return status;
	}
	public void pause() {
		status = DownloadStatus.PAUSED;
		stateChanged();
	}
	public void resume() {
		status = DownloadStatus.DOWNLOADING;
		stateChanged();
		download();
	}
	public void cancel() {
		status = DownloadStatus.CANCELLED;
		stateChanged();
	}
	private void error() {
		status = DownloadStatus.ERROR;
		stateChanged();
	}
	private void download() {
		Thread thread = new Thread(this);
		thread.start();
		stateChanged();
		try {
			synchronized (obj) {
                obj.wait();
            }
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		do {
			stateChanged();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(status==DownloadStatus.DOWNLOADING);
	}
	private void stateChanged() {
		observer.actualise(status, getProgress());
	}
	@Override
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;
		try {
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
			connection.connect();
			if (connection.getResponseCode() / 100 != 2) {
				error();
			}
			int contentLength = connection.getContentLength();
			if (contentLength < 1) {
				error();
			}
			if (size == -1) {
				size = contentLength;
				//stateChanged();
			}
			file = new RandomAccessFile(name, "rw");
			file.seek(downloaded);
			stream = connection.getInputStream();
			status = DownloadStatus.DOWNLOADING;
			synchronized (obj) {
                obj.notify();
            }
			while (status == DownloadStatus.DOWNLOADING) {
				byte buffer[];
				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}
				int read = stream.read(buffer);
				if (read == -1)
					break;
				file.write(buffer, 0, read);
				downloaded += read;
			}
			if (status == DownloadStatus.DOWNLOADING) {
				status = DownloadStatus.COMPLETE;
				stateChanged();
			}
		} catch (Exception e) {
			error();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (Exception e) {
					
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					
				}
			}
		}
	}
	public File getFile() {
		// TODO Auto-generated method stub
		return new File(name);
	}
}