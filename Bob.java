import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * This class fulfils Bob requirements
 * to read in and decrypt a text file and crack a puzzle
 * randomly. Fulfills Question 3.
 * @author Eddie Thuo
 * @author Luke Okomilo
 */

public class Bob {

    /**
     * Reads in a text file containing all 1024
     * encrypted puzzles and store into a array.
     * @return String[] of all encrypted puzzles.
     */
    private static String[] readPuzzlesFromFile() {
        String[] encryptedPuzzles = new String[1024];

        try {
            File inputFile = new File("puzzles.txt");
            Scanner inFile = new Scanner(inputFile);
            int i = 0;

            /*Read through all line of text file
            * and add to array.
            * */
            while (inFile.hasNextLine()) {
                String encryptedPuzzle = inFile.nextLine();
                encryptedPuzzles[i] = encryptedPuzzle;
                i++;
            }
            inputFile.delete(); // Avoid overflow when reading from file by deleting after each run
        } catch (FileNotFoundException e) {
            System.out.println("File not found error:" + e);
        }
        return encryptedPuzzles;
    }

    /**
     * Decrypting a individual puzzle with the
     * given key.
     * @param encryptedPuzzle The individual puzzle for decryption.
     * @param secretKey secret key provided
     * @return
     * @throws Exception
     */
    private static String decryptPuzzle(String encryptedPuzzle, SecretKey secretKey) throws Exception {
        DES des = new DES();
        return des.decrypt(encryptedPuzzle, secretKey);
    }

    /**
     * Selects a random encrypted puzzle for
     * decryption and attempts to crack it using brute
     * force of 65536 possible keys. A puzzle is broken if
     * all
     * @return the puzzles which was cracked with a random message.
     * @throws Exception
     */
     public static String crackRandom() throws Exception {

        Random random = new Random();
        /*Random number between 0-1024 to select particular encrypted puzzle */
        int randomPuzzleNumber = random.nextInt(1024);

        /*Retrieve array of all encrypted puzzles*/
        String[] encryptedPuzzles = readPuzzlesFromFile();
        /*Random selected puzzle*/
        String encryptedPuzzle = encryptedPuzzles[randomPuzzleNumber];
        System.out.println(encryptedPuzzle);

        /*Random selected puzzle*/
        byte[] keyBytes;
         /*Last 48 bits of key of all zeros*/
        byte[] keyZeroBytes = new byte[6];
         /*Complete key of 8 bytes*/
        byte[] completeKey = new byte[8];
         /*16 bytes of all zeros */
        byte[] zeroBytes = new byte[16];

        Arrays.fill(keyZeroBytes, (byte) 0); //fill with zeros
        Arrays.fill(zeroBytes, (byte) 0); // fill with zeros.

        Puzzle puzzle;
        String puzzleString = null;
        String puzzleZeroBytes;
        SecretKey secretKey;

        /*
         * For all 65536 keys, attempt to
         * crack puzzle. First 2 bytes of key added to complete 8 byte key
         * Remaining 48 bits (6 bytes) of all zeros to the end of
         * the complete key. Once structure of key is completed,
         * create secret key from 8 byte completed key.
         */
        for (int i = 0; i < 65536; i++) {
            keyBytes = CryptoLib.smallIntToByteArray(i);
            System.arraycopy(keyBytes, 0, completeKey, 0, 2);
            System.arraycopy(keyZeroBytes, 0, completeKey, 2, 6);
            secretKey = CryptoLib.createKey(completeKey);

            try {
                try {
                    puzzleString = decryptPuzzle(encryptedPuzzle, secretKey);
                    puzzle = new Puzzle(CryptoLib.stringToByteArray(puzzleString));
                    puzzleZeroBytes = CryptoLib.byteArrayToString(puzzle.getZeroBytes());
                    /* If plaintext started with all zeros (16 bytes)
                     * equal to that of the plaintext of puzzle then you know it
                     * has been cracked.
                     * */
                    if (Objects.equals(puzzleZeroBytes, CryptoLib.byteArrayToString(zeroBytes))) {
                        break;
                    }
                } catch (IllegalArgumentException ignored) {
                }
            } catch (BadPaddingException ignored) {
            }
        }
        return puzzleString;
    }
}
