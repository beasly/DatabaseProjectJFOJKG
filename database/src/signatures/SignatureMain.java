package signatures;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: jufert
 * Date: 21.12.12
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class SignatureMain {

  public static void main(String[] args) throws Exception {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

  // package org.bouncycastle.jce.provider muss noch hinzugefügt werden


  // Hier müssen die Keypairs aus unserem früheren Programm implementiert werden
  KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
  keyGen.initialize(512, new SecureRandom());
  KeyPair keyPair = keyGen.generateKeyPair();

  //Nachricht die signiert werden soll
  byte[] message = "Mobile Anwendungen macht Spass!".getBytes();

  //Erstellen der Signaturen
  Signature signature = Signature.getInstance("SHA1withRSA", "BC");
  signature.initSign(keyPair.getPrivate(), new SecureRandom());

  signature.update(message);

  }

}
