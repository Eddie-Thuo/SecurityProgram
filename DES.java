import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class DES {

    private static Cipher cipher;

    public DES() throws Exception {
        cipher = Cipher.getInstance("DES");
    }

    public String encrypt(String plainText, SecretKey secretKey) throws Exception {

        //Convert plaintext into byte representation
        byte[] plainTextByte = plainText.getBytes();

        //Initialise the cipher to be in encrypt mode, using the given key.
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        //Perform the encryption
        byte[] encryptedByte = cipher.doFinal(plainTextByte);

        //Get a new Base64 (ASCII) encoder and use it to convert ciphertext back to a string
        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(encryptedByte);
    }

    /**
     * Returns a decrypted plaintext string from an encrypted string and key provided.
     *
     * @param encryptedText string to be decrypted
     * @param secretKey     key to use for decryption
     * @return plaintext string
     * @throws Exception
     */
    public String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        //Get a new Base64 (ASCII) decoder and use it to convert ciphertext from a string into bytes
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);

        //Initialise the cipher to be in decryption mode, using the given key.
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        //Perform the decryption
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        //Convert byte representation of plaintext into a string
        return new String(decryptedByte);
    }
}
