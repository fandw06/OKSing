package dawei.oksing;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download {

	public static void main(String[] args) {
		String addr = "http://mc-storage.oss.okchang.com/tingting/music/201508/gyh_1439133736_10800746.m4a";
		URL website;
		try {
			website = new URL(addr);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("information.m4a");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
