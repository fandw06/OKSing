package dawei.oksing;

import java.util.ArrayList;
import java.util.List;

public class Person {
	public ERR_MSG errMsg;
	public enum ERR_MSG {INVALID, NOT_FOUND, FOUND};
	public String address; 
	public String name;
	public String photoAddress;
	public String numOfFans;
	public String numOfFriends;
	public String numOfSongs;
	public List<Song> listOfSongs;
	
	public Person(String addr) {
		
		this.address = addr;
		if (!address.matches("http://ok.okchang.com/home-[0-9]{8}.html"))
			errMsg = ERR_MSG.INVALID;
		else {
			String content = Crawler.getSource(address);
			if (content.contains("<meta name=\"keywords\" content=\"404\" />"))
				errMsg = ERR_MSG.NOT_FOUND;
			else {
				errMsg = ERR_MSG.FOUND;
				this.name = Crawler.searchPattern(content, "<meta name=\"description\" content=\"(.+?)\" />", 1)[0];
				this.photoAddress = Crawler.searchPattern(content, "<img src=\"(http.+?)\".+?/>", 1)[0];
				this.numOfFans = Crawler.searchPattern(content, "<li class=\"la2\">·ÛË¿:<span id=\"fans_box\">(.+?)</span></li>", 1)[0];
				this.numOfFriends = Crawler.searchPattern(content, "<li class=\"la3\">¹Ø×¢:<span>(.+?)</span></li>", 1)[0];
				this.numOfSongs = Crawler.searchPattern(content, "<li class=\"la1\">×÷Æ·:<span>(.+?)</span></li>", 1)[0];
				listOfSongs = new ArrayList<Song>();
			}
		}
		
		
		
	}
	
	public List<Song> listSongs() {
		int pages = Integer.parseInt(numOfSongs)/10 + 1;
		for (int p = 0; p < pages; p++) {			
			String curAddr = address.replaceFirst(".html", "-"+Integer.toString(p+1)+".html");
			String content = Crawler.getSource(curAddr);
			for (int i = 0; i<10 && 10*p + i < Integer.parseInt(numOfSongs); i++) {
			    String infoOfSong[] = Crawler.searchPattern(content, 
			    		"<dd><span class=\"t\"><label>"+Integer.toString(p*10+i+1)+"¡¢</label><a title=\"(.+?)\" target=\"_blank\" href=\"(.+?)\".+?<span class=\"c\">(.+?)</span><span class=\"s\">(.+?)</span><span class=\"p\"><a target=\"_blank\" href=\"(.+?)\"></a></span></dd>", 
			    		4);
			    infoOfSong[1] = "http://ok.okchang.com"+ infoOfSong[1];
	//		    System.out.println(Arrays.toString(infoOfSong));
			    Song song = new Song(infoOfSong[0], infoOfSong[2], infoOfSong[3], infoOfSong[1]);
			    listOfSongs.add(song);
			}
		}
		return listOfSongs;
	}
	
	@Override
	public String toString() {
		return "Name: "+this.name+
				"\nPhotoAddress: "+ this.photoAddress+
				"\nNumber of funs: "+ this.numOfFans+
				"\nNumber of friends: " + this.numOfFriends+
				"\nNumber of songs: "+ this.numOfSongs;
	}
}
