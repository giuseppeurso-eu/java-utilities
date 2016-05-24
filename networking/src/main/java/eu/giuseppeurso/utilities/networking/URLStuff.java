package eu.giuseppeurso.utilities.networking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

/**
 * URL stuff
 * @author Giuseppe Urso (www.giuseppeuros.eu)
 *
 */
public class URLStuff {
	
	/**
	 * Get url as string
	 * @param url
	 * @return
	 */
	public static String getUrlAsString(String url) {
		StringBuffer response = new StringBuffer();
		
		try {
	        URLConnection conn = new URL(url).openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) { 
	            response.append(inputLine);
	        }
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return response.toString();
	}
	
	/**
	 * Get URL with Basic authentication
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getUrlAuth(String url, String username, String password) {
		StringBuffer response = new StringBuffer();
		String userPassword = username + ":" + password;
		String encoding = new String(Base64.encodeBase64(userPassword.getBytes()));
		
		try {
	        URLConnection conn = new URL(url).openConnection();
	        conn.setRequestProperty ("Authorization", "Basic " + encoding);
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) { 
	            response.append(inputLine);
	        }
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return response.toString();
	}
	
	
	/**
	 * Get URL as Bytes
	 * @param url
	 * @return
	 */
	public static byte[] getUrlAsBytes(String url) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		URLConnection conn = null;
		BufferedInputStream in = null;
		try {
	        conn = new URL(url).openConnection();
	        in = new BufferedInputStream(conn.getInputStream());
	        byte[] buf = new byte[2048];
	        int len = 0;
	        while ((len = in.read(buf)) != -1) { 
	            bytes.write(buf, 0, len);
	        }
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {in.close();} catch (Exception ignore) {}
		}
        
		return bytes.toByteArray();
	}
	
	/**
	 * Post URL wih Basic Authentication
	 */
	public static void postAuth(String _url,String username,String password,
			InputStream stream,String mime, String FileName,String FieldName,Map<?, ?> parameter) throws Exception {
		URL url = new URL(_url);
		// create a boundary string
		String boundary = MultiPartFormOutputStream.createBoundary();
		URLConnection urlConn = MultiPartFormOutputStream.createConnection(url, username, password);
		urlConn.setRequestProperty("Accept", "*/*");
		urlConn.setRequestProperty("Content-Type", 
			MultiPartFormOutputStream.getContentType(boundary));
		// set some other request headers...
		urlConn.setRequestProperty("Connection", "Keep-Alive");
		urlConn.setRequestProperty("Cache-Control", "no-cache");
		// no need to connect cuz getOutputStream() does it
		MultiPartFormOutputStream out = 
			new MultiPartFormOutputStream(urlConn.getOutputStream(), boundary);
		// write a text field element
		 Iterator<?> it = parameter.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());
		       out.writeField(pairs.getKey().toString(), pairs.getValue().toString());
		    }

//		out.writeField("title", "text field text");
//		out.writeField("entita", "Clienti");
//		out.writeField("fornitore", "Verdi");
		
		
		// upload a file
		out.writeFile(FieldName, mime,FileName, stream);
		// can also write bytes directly
		//out.writeFile("myFile", "text/plain", "C:\\test.txt", 
	//		"This is some file text.".getBytes("ASCII"));
		out.close();
		// read response from server
		BufferedReader in = new BufferedReader(
			new InputStreamReader(urlConn.getInputStream()));
		String line = "";
		while((line = in.readLine()) != null) {
			 System.out.println(line);
		}
		in.close();
	}

}
