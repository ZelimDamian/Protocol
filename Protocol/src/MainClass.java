import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import org.apache.commons.codec.binary.Base64;

public class MainClass {
    public static void main(String[] args) throws Exception {

        KeyPair keyPair = getKeyPair();

        byte[] data = "test".getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(keyPair.getPrivate());
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        System.out.println("Singature:" + new Base64().encode(signatureBytes));

        sig.initVerify(keyPair.getPublic());
        sig.update(data);

        System.out.println(sig.verify(signatureBytes));
    }

    private static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        return kpg.genKeyPair();
    }
}