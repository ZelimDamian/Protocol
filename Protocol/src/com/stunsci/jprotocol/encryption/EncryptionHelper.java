package com.stunsci.jprotocol.encryption;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptionHelper {
 	private KeyPairGenerator keyGen;
    private KeyPair keyPair;
    
    public KeyPair getKeyPair()
    {
        return this.keyPair;
    }
    
	public String digestString(String toDigest)
	{
		String digest = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			digest = byteArrayToHexString(md.digest(toDigest.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return digest;
	}
    
	public static String byteArrayToHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
	}
	
    public EncryptionHelper(){
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            keyPair = keyGen.generateKeyPair();
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
}
