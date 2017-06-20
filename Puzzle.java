import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * This class contains methods and a
 * constructor for a puzzle containing 26 byte arrays.
 * Fulfills the requirements of question
 *
 * @author Luke Okomilo
 * @author Eddie Thuo
 */

public class Puzzle {

    /*Byte representation of all zeros*/
    private byte[] zeroBytes = new byte[16];
    /*Byte representation of unique puzzle number*/
    private byte[] puzzleNumbers = new byte[2];
    /*Byte representation of key*/
    private byte[] key = new byte[8];
    /*Byte representation of completed puzzle */
    private byte[] completePuzzle = new byte[26];
    /*Used for generating random key*/
    private SecureRandom random = new SecureRandom();

    /**
     * Constructor used to generate a puzzle
     * of 26 bytes
     * @param puzzleID I given unique puzzle number (ID)
     * for a puzzle.
     */
    public Puzzle(int puzzleID) {

        zeroBytes = generateZeroBytes();
        puzzleNumbers = generatePuzzleNumbers(puzzleID);
        key = generateKey();
        completePuzzle = combineArrays(zeroBytes, puzzleNumbers, key);
    }

    /**
     * Second constructor used to create a complete puzzle
     * provided the number of bytes provided. In this case
     * 26 bytes.
     * @param puzzleInBytes byte array of a puzzle
     * @throws Exception
     */
    Puzzle(byte[] puzzleInBytes) throws Exception {

        if (puzzleInBytes.length == 26) {
            System.arraycopy(puzzleInBytes, 0, zeroBytes, 0, 16);
            System.arraycopy(puzzleInBytes, 16, puzzleNumbers, 0, 2);
            System.arraycopy(puzzleInBytes, 18, key, 0, 8);
            completePuzzle = combineArrays(zeroBytes, puzzleNumbers, key);
        } else {
            throw new Exception("Unexpected byte array length.");
        }
    }

    /**
     * Retrieve the puzzle.
     * @return 26 byte array completed puzzle
     */
    public byte[] getPuzzle() {
        return completePuzzle;
    }

    /**
     * Retrieve the 16 bytes of all Zeros
     * @return 16 byte array of zeros bytes
     */
    public byte[] getZeroBytes() {
        return zeroBytes;
    }

    /**
     * Retrieve the unique 2 byte
     * puzzle number (ID)
     * @return 2 byte array for puzzle number
     */
    public byte[] getPuzzleID() {
        return puzzleNumbers;
    }

    /**
     *Retrieve the key
     * @return 8 byte array of key
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * Generate 16 byte array of all zeros
     * segment of the puzzle.
     * @return 16 byte array of zeros.
     */
    private byte[] generateZeroBytes() {
        Arrays.fill(zeroBytes, (byte) 0);
        return zeroBytes;
    }

    /**
     * Generate a unique number (ID) for a puzzle
     * @param puzzleID
     * @return 2 byte array representation of the unique number
     */
    private byte[] generatePuzzleNumbers(int puzzleID) {
        puzzleNumbers = CryptoLib.smallIntToByteArray(puzzleID);
        return puzzleNumbers;
    }

    /**
     * Generates the key randomly
     * @return 8 byte array representation of the key.
     */
    private byte[] generateKey() {
        byte[] randomBytes = new byte[2];
        byte[] zeroBytes = new byte[6];
        random.nextBytes(randomBytes);
        Arrays.fill(zeroBytes, (byte) 0);
        System.arraycopy(randomBytes, 0, key, 0, 2);
        System.arraycopy(zeroBytes, 0, key, 2, 6);
        return key;
    }

    private byte[] generateRandom(){
        byte[] zeroBytes = new byte[6];
        Random random = new Random();
        byte[] randomBytes = CryptoLib.smallIntToByteArray(random.nextInt(65536));
        Arrays.fill(zeroBytes, (byte) 0);
        System.arraycopy(randomBytes, 0, key, 0, 2);
        System.arraycopy(zeroBytes, 0, key, 2, 6);
        return key;
    }

    /**
     * Responsible for combining the 16 byte array of
     * all zeros followed by the unique 2 byte array number
     * and the last  6 byte of key of all zeros to create a puzzle.
     * @param zeroBits
     * @param puzzleNumbers
     * @param key
     * @return
     */
    private byte[] combineArrays(byte[] zeroBits, byte[] puzzleNumbers, byte[] key) {
        System.arraycopy(zeroBits, 0, completePuzzle, 0, 16);
        System.arraycopy(puzzleNumbers, 0, completePuzzle, 16, 2);
        System.arraycopy(key, 0, completePuzzle, 18, 8);
        return completePuzzle;
    }
}
