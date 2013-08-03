package com.stunsci.jprotocol.encryption;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.*;

public class EncryptionHelper {
    private KeyPair keyPair;
    private SecretKey aesKey;
    
    public String getAESKey()
    {
    	try {
			return new String(aesKey.getEncoded(), aesKey.getFormat());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public String getPublicKey()
    {
    	byte[] encodedKey = keyPair.getPublic().getEncoded();
		
    	return Base64.encodeBase64String(encodedKey);	
    }
    
    public String getPrivateKey()
    {
    	byte[] encodedKey = keyPair.getPrivate().getEncoded();
		
    	return Base64.encodeBase64String(encodedKey);	
    }
    
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
    
	public String byteArrayToHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
	}
	
	public byte[] signString(String input)
	{
        byte[] data;
        Signature sig = null;
        byte[] signatureBytes = null;
        
		try {
			data = input.getBytes("UTF8");
		
			sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(keyPair.getPrivate());
			sig.update(data);
			signatureBytes = sig.sign();
			
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (InvalidKeyException e) {
			e.printStackTrace();
		}
        catch (SignatureException e) {
			e.printStackTrace();
        }
        
        return signatureBytes;
	}
	
    public Boolean verifySignature(byte[] signature)
    {
        Signature sig = null;
        byte[] signatureBytes = null;
        Boolean result = null;
        
		try {
			sig = Signature.getInstance("SHA1WithRSA");
	        sig.initVerify(keyPair.getPublic());
	        sig.update(signature);
	        result = sig.verify(signatureBytes);		
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (InvalidKeyException e) {
			e.printStackTrace();
		}
        catch (SignatureException e) {
			e.printStackTrace();
        }

        return result;
    }
	
    public String encryptStringWithSymKey(byte[] input)
    {
    	Cipher cph = null;
    	byte[] ciphertext = null;
    	
		try {
			cph = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
    	
    	try {
			cph.init(Cipher.ENCRYPT_MODE, aesKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
    	try {
			ciphertext = cph.doFinal(input);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
    	
    	String encoded = Base64.encodeBase64String(ciphertext);
    	
		return encoded;
    }
    
    public String encryptStringWithPublicKey(String input)
    {
    	Cipher cph = null;
    	byte[] ciphertext = null;
    	
		try {
			cph = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
    	
    	try {
			cph.init(Cipher.ENCRYPT_MODE, this.keyPair.getPublic());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
    	try {
			ciphertext = cph.doFinal(input.getBytes());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
    	
    	String encoded = Base64.encodeBase64String(ciphertext);
    	
		return encoded;
    }
    
    public String getEncryptedSymKey()
    {
    	return this.encryptStringWithPublicKey(Base64.encodeBase64String(this.aesKey.getEncoded()));
    }
    
	public EncryptionHelper(){
        try {
        	KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        	keyPairGen.initialize(512);
            keyPair = keyPairGen.generateKeyPair();
            
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            aesKey = keyGen.generateKey();
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
}
