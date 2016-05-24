package eu.giuseppeurso.utilities.security;

import java.io.*;
import java.security.*;
import java.security.spec.*;

class VerifySignature {

    public static void main(String[] args) {

        /* Verify a DSA signature */
    	String resourceDir="src/main/resources";
    	String filePubKey=resourceDir+"/pub.key";
    	String fileSignature=resourceDir+"/signature";
    	String filePlainText=resourceDir+"/test-02.json";
		args =new String[] {filePubKey,fileSignature,filePlainText};

        if (args.length != 3) {
            System.out.println("Usage: VerSig " +
                "publickeyfile signaturefile " + "datafile");
        }
        else try {

        	// First, read in the encoded public key bytes
        	FileInputStream keyfis = new FileInputStream(args[0]);
        	byte[] encKey = new byte[keyfis.available()];  
        	keyfis.read(encKey);

        	keyfis.close();

        	// Instantiate a DSA public key from its encoding (need a key specification first)
        	X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        	
        	// Now it is necessary a KeyFactory object to do the conversion (that object must be one that works with DSA keys)
        	KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        	
        	//Finally, a KeyFactory object must be used to generate a PublicKey from the key specification.
        	PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
        	
        	// Input the Signature Bytes
        	FileInputStream sigfis = new FileInputStream(args[1]);
        	byte[] sigToVerify = new byte[sigfis.available()]; 
        	sigfis.read(sigToVerify);
        	sigfis.close();
        	
        	//Initialize the Signature Object for Verification
        	Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        	sig.initVerify(pubKey);
        	
        	//Supply the Signature Object With the Data to be Verified
        	FileInputStream datafis = new FileInputStream(args[2]);
        	BufferedInputStream bufin = new BufferedInputStream(datafis);

        	byte[] buffer = new byte[1024];
        	int len;
        	while (bufin.available() != 0) {
        	    len = bufin.read(buffer);
        	    sig.update(buffer, 0, len);
        	};

        	bufin.close();
        	
        	// Verify the Signature
        	boolean verifies = sig.verify(sigToVerify);
        	System.out.println("signature verifies: " + verifies);
        	
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }

}
