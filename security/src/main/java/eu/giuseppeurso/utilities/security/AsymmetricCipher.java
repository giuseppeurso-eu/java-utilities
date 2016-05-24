package eu.giuseppeurso.utilities.security;



import java.io.File;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;

/**
 * Public key cryptography or asymmetric key cryptography
 * http://www.reindel.com/articles/asymmetric-public-key-encryption-using-rsa-java-and-openssl.txt
 * http://www.flexiprovider.de/examples/ExampleRSA.html
 * 
 * @author Giuseppe Urso (www.giuseppeuros.eu)
 *
 */
public class AsymmetricCipher {
	
	
	public static void main(String[] args) {
		
		String sourceFileToCrypt ="src/main/resources/test-01.json";
		String destCryptedFile = "target/test-01.crypted";
		try {
			cryptFileWithRSA2048(sourceFileToCrypt, destCryptedFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/**
 * Encryption
 * @param inpBytes
 * @param key
 * @param xform
 * @return
 * @throws Exception
 */
  private static byte[] encrypt(byte[] inpBytes, PrivateKey key,  String xform) throws Exception {
    Cipher cipher = Cipher.getInstance(xform);
    cipher.init(Cipher.ENCRYPT_MODE, key);
    return cipher.doFinal(inpBytes);
  }
  
  /**
   * Decryption
   * @param inpBytes
   * @param key
   * @param xform
   * @return
   * @throws Exception
   */
  private static byte[] decrypt(byte[] inpBytes, PublicKey key, String xform) throws Exception{
    Cipher cipher = Cipher.getInstance(xform);
    cipher.init(Cipher.DECRYPT_MODE, key);
    return cipher.doFinal(inpBytes);
  }

  /**
   * File Encryption with RSA 2048 key
   * @param sourceFileToCrypt
   * @param destCryptedFile
   * @throws Exception
   */
  public static void cryptFileWithRSA2048(String sourceFileToCrypt, String destCryptedFile) throws Exception {
    String xform = "RSA/ECB/PKCS1Padding";
    // Generate a key-pair
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    int keysize = 2048;
    kpg.initialize(keysize); 
    KeyPair kp = kpg.generateKeyPair();
    PublicKey pubk = kp.getPublic();
    PrivateKey prvk = kp.getPrivate();

    String uncryptedFile="target/test-01.uncrypted";
    
    File file = new File(sourceFileToCrypt);
    byte[] dataBytes = FileUtils.readFileToByteArray(file);
    
//    System.out.println("File lenght is: " +dataBytes.length*8+ " bits (="+dataBytes.length+" bytes)");
//    System.out.println("Max allowed size is: "+keysize+" bits (= "+keysize/8+" bytes)" );
//    System.out.println("Pub key encoded size is: "+pubk.getEncoded().length);
//    System.out.println("Priv key encoded size is: "+prvk.getEncoded().length);
  
    byte[] encBytes = encrypt(dataBytes, prvk, xform);
    System.out.println("Lenght of cripted bytes is: "+encBytes.length);
    file = new File(destCryptedFile);
    FileUtils.writeByteArrayToFile(file, encBytes);
    System.out.println("Crypted file at: "+destCryptedFile);
    
    byte[] decBytes = decrypt(encBytes, pubk, xform);
    file = new File(uncryptedFile);
    FileUtils.writeByteArrayToFile(file, decBytes);
    System.out.println("Uncrypted file at: "+uncryptedFile);
    
    boolean expected = java.util.Arrays.equals(dataBytes, decBytes);
	System.out.println("Test " + (expected ? "SUCCEEDED!" : "FAILED!"));
   
	
    
  }
}
