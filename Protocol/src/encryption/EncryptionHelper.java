package encryption;

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
    
    private static EncryptionHelper instance;
    private EncryptionHelper(){
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            keyPair = keyGen.generateKeyPair();
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    public static EncryptionHelper getInstance() {
        if(instance == null) {
        	instance = new EncryptionHelper();
        }
        return instance;
    }
}
