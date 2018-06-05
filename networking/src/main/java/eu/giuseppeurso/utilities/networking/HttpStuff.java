package eu.giuseppeurso.utilities.networking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


/**
 * URL stuff
 * @author Giuseppe Urso (www.giuseppeuros.eu)
 *
 */
public class HttpStuff {
	
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
	 * Post Upload with Basic Authentication using Multipart form
	 */
	public static String postUploadMultipartBasicAuth(String _url,String username,String password,
												InputStream fileUploadStream, String fileMimetype, String fileName,	String fileFieldName,
												Map<?, ?> otherTextField) throws Exception {
		StringBuffer response = new StringBuffer();
		URL url = new URL(_url);
		String boundary = MultiPartFormOutputStream.createBoundary();
		System.out.println("POST Url:"+_url);
		URLConnection urlConn = MultiPartFormOutputStream.createConnection(url, username, password);
		urlConn.setRequestProperty("Accept", "*/*");
		urlConn.setRequestProperty("Content-Type", MultiPartFormOutputStream.getContentType(boundary));
		urlConn.setRequestProperty("Connection", "Keep-Alive");
		urlConn.setRequestProperty("Cache-Control", "no-cache");
		
		MultiPartFormOutputStream multipartStream =new MultiPartFormOutputStream(urlConn.getOutputStream(), boundary);
		System.out.println("Writing parameters in the multipart form...");
		Iterator<?> it = otherTextField.entrySet().iterator();
		while (it.hasNext()) {
		   Map.Entry pairs = (Map.Entry)it.next();
		   System.out.println("Passed parameter: "+pairs.getKey() + " = " + pairs.getValue());
		   multipartStream.writeField(pairs.getKey().toString(), pairs.getValue().toString());
		}
		
		System.out.println("Uploading file "+fileName+" ...");
		multipartStream.writeFile(fileFieldName, fileMimetype,fileName, fileUploadStream);
		multipartStream.close();
		
		System.out.println("Reading response from server...\n");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line = "";
		while((line = in.readLine()) != null) {
			 System.out.println(line);
			 response.append(line);
		}
		in.close();
		return response.toString();
	}
	
	/**
	 * Post Json to generic URL and returns response as string·
	 */
	public static String postJson(String _url, JSONObject jsonInput) {
		StringBuffer response = new StringBuffer();
		try {
			
			URL url = new URL(_url);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(jsonInput.toString());
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			}
			System.out.println("\nCrunchify REST Service Invoked Successfully..");
			response.append(inputLine);
			in.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	return response.toString();
	}
	
	/**
	 * Post Json data with Basic Authentication
	 */
	public static String postJsonBasicAuth(String _url, String username,String password, JSONObject jsonInput) {
		String responseText="";
		
		// Authentication
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(_url);
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		BasicScheme basicScheme = new BasicScheme();
		Header authHeader;
		try {
			authHeader = basicScheme.authenticate(credentials, postRequest, null);
			postRequest.addHeader(authHeader);

			// Http Post
			StringEntity input = new StringEntity(jsonInput.toString());
			input.setContentType("application/json");
			postRequest.setEntity(input);
			// System.out.println("TEST CASE: " + this.getName() + " -> "+"Request: "+postRequest.getMethod()+" "+EntityUtils.toString(input));
			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("FAILED Http error code: "+ response.getStatusLine().getStatusCode() +" "+response.getStatusLine().getReasonPhrase());
			} else {
				HttpEntity respEntity = response.getEntity();
				responseText = EntityUtils.toString(respEntity);
				System.out.println("Response status code: " + response.getStatusLine().getStatusCode());
				System.out.println("Response text: " + responseText.replace("\n", ""));
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	return responseText;
	}
	
	/**
	 * Get URL authenticated with a Map of parameters. 
	 * @param _url
	 * @param username
	 * @param password
	 * @param paramatersMap
	 * @return
	 */
	public static String getUrlAuthWithParameters(String _url, String username, String password, Map<String, String> paramatersMap) {
		StringBuffer response = new StringBuffer();
		String userPassword = username + ":" + password;
		String encoding = new String(Base64.encodeBase64(userPassword.getBytes()));
		
		if (paramatersMap!=null && paramatersMap.size()>0) {
			System.out.println("Appending parameters to the url...");
			Iterator<?> iter = paramatersMap.entrySet().iterator();
		    while (iter.hasNext()) {
		        Map.Entry pairs = (Map.Entry) iter.next();
		        System.out.println("Passed parameter: "+pairs.getKey() + "=" + pairs.getValue());
		        _url = _url+"?"+pairs.getKey().toString()+"="+pairs.getValue().toString();
		    }
		}
		  try {
			URL url = new URL(_url);
			System.out.println("GET Url: "+_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty ("Authorization", "Basic " + encoding);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Response from Server...");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				response.append(output);				
			}
			conn.disconnect();
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  return response.toString();
		}


	/**
	 * Returns Basic Authenticated CloseableHttpClient (Apache Http) via CredentialsProvider.
	 * @return
	 */
	public static CloseableHttpClient createBasicAuthenticatedClient(String user, String password) {
		CloseableHttpClient authenticatedClient = null;
		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
			provider.setCredentials(AuthScope.ANY, credentials);
			authenticatedClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		}catch (Exception e) {
			System.out.println("Error while trying to create authenticated HttpClient by using ApacheHttp: "+e);
			e.printStackTrace();
		}
		return authenticatedClient;		
	}
}
