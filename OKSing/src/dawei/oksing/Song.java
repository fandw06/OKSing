package dawei.oksing;


import java.net.MalformedURLException;
import java.net.URL;
  
public class Song {  
	
    public String title;   
    public String numOfPlayed;
    public String uploadTime;
    public int size;
    public URL mediaUrl;  
    public String address; 
   
    public Song(String title, String numOfPlayed, String uploadTime, String address) {   
        this.title = title;
        this.numOfPlayed = numOfPlayed;
        this.uploadTime = uploadTime;
        this.address = address;
        this.size = 0;
    }  
    
    public URL getMediaAddress() {
    	String content = Crawler.getSource(address);
        String mediaAddr = Crawler.searchPattern(content, "m4a: \"(.+?)\"", 1)[0];
        try {
			mediaUrl = new URL(mediaAddr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        return mediaUrl;
    }

    @Override  
    public String toString() {  
        return "Title: " + title + 
        		"\nNumber of played times: " + numOfPlayed + 
        		"\nAddress: " + address + 
        		"\nUpload time: " + uploadTime +
        		"\nMedia address: "+ mediaUrl+"\n";  
    }  
}  