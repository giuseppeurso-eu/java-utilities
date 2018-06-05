package eu.giuseppeurso.utilities.networking;


public class MAINexecutor {

	public static void main(String[] args) {
		
		//String trustPath="/home/giuseppe/apps/java/jdk1.8.0_112/jre/lib/security/cacerts";
		String trustPath="/home/giuseppe/my-truststore.jks";
		SSL.setKeyOrTrustStores(trustPath, "123456", null, null);
		SSL.disableHostnameVerifier();
		SSL.checkHTTPS("https://192.168.200.45:8443");
		
		 
		// Alfresco NTLMv1 SSO Passthru works only with Apache Component 4.2
		//NtlmHttpClient.connectWithJCIFS("TESTAD", "paolo", "1234", "http://localhost:8080/share");
		//NtlmHttpClient.connectWithAPACHEHttpComponent42("TESTAD", "paolo", "gius-pc", "1234", "http://localhost:8080/share", "localhost",8080);
		//NtlmHttpClient.connectWithAPACHEHttpComponent43("TESTAD", "paolo", "gius-pc", "1234", "http://localhost:8080/share", "localhost",8080);
		
		
		// Send Email Message
		//Email.sendMailTLS("user@gmail.com", "1234", true, true, "smtp.gmail.com", "587", "mawdev@gmail.com", "dest@eee.it", "notification", "This is a notification test");
	}

}
