package eu.giuseppeurso.utilities.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.pdf.codec.Base64;

/**
 * Class for SSL, TLS, HTTPS, etc.
 * @author Giuseppe Urso (www.giuseppeuros.eu)
 *
 */
public class SSL {
	
	/**
	 * Check HTTPS Url
	 * @param aUrl
	 */
	public static void checkHTTPS(String aUrl) {
		
		//Uncomment to debug full SSL handshake stack
		//Properties systemProps = System.getProperties();
		//systemProps.put("javax.net.debug","all");
		
		HttpURLConnection connection;
		URL connurl;
		try {
			connurl = new URL(aUrl);
			connection = (HttpURLConnection) connurl.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-type", "application/timestamp-query");
	        connection.setRequestProperty("Content-Transfer-Encoding", "binary");
	        String user="";
	        String password ="";
	        if((user!= null) && !user.equals("")){
	            String userPassword = user + ":" + password;
	            connection.setRequestProperty("Authorization", "Basic " + Base64.encodeBytes(userPassword.getBytes()));
	        }
	        OutputStream os = connection.getOutputStream();
	      
	        // Get response
	        InputStream is = connection.getInputStream();
	        StringWriter writer = new StringWriter();
	        //IOUtils.toByteArray(is, writer, "UTF-8");
	        IOUtils.copy(is, writer, "UTF-8");
	        System.out.println("Request: "+writer.toString());
	        IOUtils.closeQuietly(is);
	       
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}       
	}

}
