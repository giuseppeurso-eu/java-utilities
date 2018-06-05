package eu.giuseppeurso.utilities.networking;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

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
		
		//Uncomment to full debug SSL handshake stack
		Properties systemProps = System.getProperties();
		systemProps.put("javax.net.debug","all");
		
		HttpURLConnection connection;
		URL connurl;
		try {
			connurl = new URL(aUrl);
			connection = (HttpURLConnection) connurl.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("GET");
	        	      
	        // Get response
	        InputStream is = connection.getInputStream();
	        StringWriter writer = new StringWriter();
	        IOUtils.copy(is, writer, "UTF-8");
	        System.out.println("------------------------");
	        System.out.println("RESPONSE: "+writer.toString());
	        System.out.println("------------------------");
	        IOUtils.closeQuietly(is);
	       
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}       
	}
	
	/**
	 * A method to customize the default Java Secure Socket Extension (JSSE) SSLContext. It provides the ability to specify key and trust store files and password.
	 * 
	 * @see http://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/JSSERefGuide.html
	 */
	public static void setKeyOrTrustStores (String trustStore, String trustStorePassword, String keyStore,String keyStorePassword){
		Properties systemProps = System.getProperties();
		//Uncomment to debug full SSL handshake stack 
		//systemProps.put("javax.net.debug","all");
		if (keyStore!=null) {
			File fkey = new File(keyStore);
			if (fkey.isFile()) {
				 try {
					systemProps.put("javax.net.ssl.keyStore", keyStore);
					systemProps.put("javax.net.ssl.keyStorePassword", keyStorePassword);
					 System.out.println("Key Store File is located at: " + fkey.getCanonicalPath());
					 System.out.println("Key Store Pass: " + keyStorePassword);
				} catch (IOException e) {
					System.out.println("IO Exception while trying to get the SSL Keystore file: "+e);
				}
			}
		}
		if (trustStore!=null) {
			File ftrust = new File(trustStore);
			if (ftrust.isFile()) {
				 try {
					 systemProps.put("javax.net.ssl.trustStore", trustStore);
					 systemProps.put("javax.net.ssl.trustStorePassword", trustStorePassword);
					 System.out.println("Trusted Store File is located at: " + ftrust.getCanonicalPath());
					 System.out.println("Trusted Store Pass: " + trustStorePassword);
				} catch (IOException e) {
					System.out.println("IO Exception while trying to get the SSL TrustStore file: "+e);
				}
			}
		}
		try {
			System.setProperties(systemProps);			 
		} catch (Exception e) {
			System.out.println("Exception while trying to configure JSSE System Properties for SSL: "+e);
		}
	}

	
	/**
	 * Disable hostname verification during SSL handshaking (default is true). 
	 */
	public static void disableHostnameVerifier() {
	    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	        public boolean verify(String arg0, SSLSession arg1) {
	            return true;
	        }
	    });
	    
	}
	
}
