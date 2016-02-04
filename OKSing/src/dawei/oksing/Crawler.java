package dawei.oksing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;  
import java.net.URLConnection;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
public class Crawler {  
	
	/**
	 * Return a string containing the source code.
	 * @param address
	 * @return
	 */
    static String getSource(String address) {  
 
        String result = "";   
        BufferedReader in = null;  
        try {   
            URL url = new URL(address);   
            URLConnection connection = url.openConnection();   
            connection.connect();   
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
            String line;  
            while ((line = in.readLine()) != null)   
                result += line;  
            
        } catch (Exception e) {  
            System.err.println("Cannot get source: " + e);  
            e.printStackTrace();  
        }  
 
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;   
    }  
  
    /**
     * This function match a regex in content, return first nums groups.
     * 
     * @param content
     * @param regex
     * @param nums
     * @return
     */
    static String[] searchPattern(String content, String regex, int nums) {
    	if (content == null)
    		return null;
    	Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String result[] = new String[nums];
			for (int i = 1; i <= nums; i++) 
				result[i-1] = matcher.group(i);
			return result;
		}
		return null; 
    }
    
    /**
     * Get the file size from a url, eg, a m4a file.
     * @param url
     * @return
     */
    static int getFileSize(URL url) {
    	HttpURLConnection conn = null;
	    try {
	    	conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        return conn.getContentLength();
	    } catch (IOException e) {
	        return -1;
	    } finally {
	        conn.disconnect();
	    }
    }
  
} 