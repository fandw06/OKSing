package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestHeader {

	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL("http://mc-storage.oss.okchang.com/tingting/music/201504/gyh_1428640798_10362725.m4a");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection conn = null;
	    try {
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        System.out.println(conn.getContentLength());
	    } catch (IOException e) {
	        System.out.println(-1);
	    } finally {
	        conn.disconnect();
	    }
	    System.out.format("progress: %2.2f%%\n", 13.5);
	}

}
