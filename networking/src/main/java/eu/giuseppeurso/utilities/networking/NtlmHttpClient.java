package eu.giuseppeurso.utilities.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import jcifs.Config;
import jcifs.ntlmssp.NtlmFlags;

public class NtlmHttpClient {
	
	
	/**
	 *  NTLM v1 authentication with JCIFS and Java Net</br>
	 * http://jcifs.samba.org/src/docs/httpclient.html
	 * 
	 * @param domain
	 * @param user
	 * @param passwd
	 * @param serverUrl
	 */
	public static void connectWithJCIFS (String domain, String user, String passwd, String serverUrl){
		
		
		Config.registerSmbURLHandler();
		Config.setProperty("jcifs.smb.client.domain", domain);
		Config.setProperty("jcifs.smb.client.username", user);
		Config.setProperty("jcifs.smb.client.password", passwd);
		Config.setProperty("jcifs.netbios.hostname", "gius-mawledsk09");
		
		//http://jcifs.samba.org/src/docs/api/index.html
//		0,1 -- Sends LM and NTLM responses.
//		2 -- Sends only the NTLM response. This is more secure than Levels 0 and 1, because it eliminates the cryptographically-weak LM response.
//		3,4,5 -- Sends LMv2 and NTLMv2 data. NTLMv2 session security is also negotiated if the server supports it. This is the default behavior (in 1.3.0 or later).
		Config.setProperty("jcifs.smb.lmCompatibility", "0");
		
		try {
			URL url = new URL(serverUrl);
			
			BufferedReader reader = new BufferedReader( new InputStreamReader(url.openStream()));
			String line;
			while ((line = reader.readLine()) != null) {
			    System.out.println(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * NTLM v1 authentication with Apache Component deprecated 0bject (4.2 and previous)</br>
	 * 
	 * http://devsac.blogspot.it/2010/10/supoprt-for-ntlmv2-with-apache.html
	 * https://hc.apache.org/httpcomponents-client-ga/ntlm.html
	 * https://stackoverflow.com/questions/21179686/ntlm-authentication-java-via-httpclient
	 */
	public static void connectWithAPACHEHttpComponent42(String domain, String user, String workstation, String passwd, String loginUrl, String host, int port){
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
	    List<String> authpref = new ArrayList<String>();
	    authpref.add(AuthSchemes.NTLM);
	    
	    httpclient.getParams().setParameter(AuthPNames.TARGET_AUTH_PREF, authpref);
	    NTCredentials creds = new NTCredentials(user,passwd,workstation,domain);
	    httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
	    System.out.println("Trying to login to: "+loginUrl);
	    System.out.println("NTLM Credentials: [user= "+user+" password="+passwd+" domain="+domain+" workstation="+workstation+"]");
	    HttpHost target = new HttpHost(host, port, "http");
	    HttpContext localContext = new BasicHttpContext();
	    HttpGet httpget = new HttpGet(loginUrl);
	    
		try {
			HttpResponse response = httpclient.execute(target, httpget, localContext);
			
			int statusCode = response.getStatusLine().getStatusCode();
		    System.out.println("RESPONSE STATUS CODE:" + statusCode);
		    
		    HttpEntity entity = response.getEntity();
			System.out.println("RESPONSE PAGE: \n"+EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  
	    
	    
	}
	
	/**
	 * NTLM v1 authentication with Apache Component 4.3 </br>
	 * @param domain
	 * @param user
	 * @param workstation
	 * @param passwd
	 * @param loginUrl
	 * @param host
	 * @param port
	 */
	public static void connectWithAPACHEHttpComponent43(String domain, String user, String workstation, String passwd, String loginUrl, String host, int port){
		
		
	    List<String> authpref = new ArrayList<String>();
	    authpref.add(AuthSchemes.NTLM);
	    
	    CookieStore cookieStore = new BasicCookieStore();
	    
	    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	    NTCredentials creds = new NTCredentials(user,passwd,workstation,domain);
	    credentialsProvider.setCredentials(AuthScope.ANY, creds);
	    
	    RequestConfig defaultRequestConfig = RequestConfig.custom()
	            .setCookieSpec(CookieSpecs.STANDARD)
	            .setExpectContinueEnabled(true)
	            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM))
	            .build();
	    
	    CloseableHttpClient httpclient = HttpClients.custom()
	    		.setDefaultCookieStore(cookieStore)
	    	    .setDefaultCredentialsProvider(credentialsProvider)
	            .setDefaultRequestConfig(defaultRequestConfig)
	            .build(); 	    

	    System.out.println("Trying to login to: "+loginUrl);
	    System.out.println("NTLM Credentials: [user= "+user+" password="+passwd+" domain="+domain+" workstation="+workstation+"]");
	    HttpHost target = new HttpHost(host, port, "http");
	    HttpClientContext context = HttpClientContext.create();
	    context.setCredentialsProvider(credentialsProvider);
	    
	    // Execute a cheap method first. This will trigger NTLM authentication
	    HttpGet httpget = new HttpGet(loginUrl);
	    try {
			CloseableHttpResponse response = httpclient.execute(target, httpget, context);
			cookieStore.getCookies();
			
			int statusCode = response.getStatusLine().getStatusCode();
		    System.out.println("RESPONSE STATUS CODE:" + statusCode);
		    
		    HttpEntity entity = response.getEntity();
			System.out.println("RESPONSE PAGE: \n"+EntityUtils.toString(entity));
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	  
	    
	    
	}
}
