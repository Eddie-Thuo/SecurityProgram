import javax.crypto.SecretKey;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * This class contains the functionality
 * that Alice requires to encrypt, generate, writing to
 * a file and looking up. Based on Questions 1,2,4
 *
 * @author Eddie Thuo
 * @author Luke Okomilo
 */

public class Alice {

    /**
     * Generates 1024 puzzles each with
     * their own unique id.
     * @return A
     */
    public static Puzzle[] createPuzzles() {
        Puzzle[] puzzles = new Puzzle[1024];
        Puzzle puzzle;

        for (int i = 0; i < 1024; i++) {
            puzzle = new Puzzle(i);
            puzzles[i] = puzzle;
        }
        return puzzles;
    }

    /**
     * Encrypts all 1024 puzzles individually
     * based on their unique id using the DES encryption
     * standard.
     * @param puzzles
     * @return 1024 encrypted puzzles.
     * @throws Exception
     */
    public static String[] encryptPuzzles(Puzzle[] puzzles) throws Exception {

        DES des = new DES();
        String[] encryptedPuzzles = new String[1024];

        for (int i = 0; i < 1024; i++) {
            byte[] puzzleByteArray = puzzles[i].getPuzzle();
            SecretKey secretKey = CryptoLib.createKey(puzzles[i].getKey());
            String plaintextPuzzle = CryptoLib.byteArrayToString(puzzleByteArray);
            String encryptedPuzzle = des.encrypt(plaintextPuzzle, secretKey);
            encryptedPuzzles[i] = encryptedPuzzle;
        }
        return encryptedPuzzles;
    }

    /**
     * Responsible for writing each cryptogram into a text
     * file.
     * @param encryptedPuzzles The number of encrypted puzzles
     *
     */
    public static void writePuzzlesToFile(String[] encryptedPuzzles) {

        try { 
            File filename = new File ("puzzles.txt");
            FileWriter fw = new FileWriter(filename, true);
            for (String encryptedPuzzle : encryptedPuzzles) {
                fw.write(encryptedPuzzle + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("File write error:" + e);
        }
    }

    /**
     * Responsible for enabling Alice to lookup the key
     * asscociated the given puzzle number.
     * @param puzzleID The unique puzzle number
     * @param puzzles Number of puzzles
     * @return SecretKey if in fact the key was found.
     * @throws Exception
     */
    public static SecretKey lookupKey(int puzzleID, Puzzle[] puzzles) throws Exception {

        SecretKey secretKey = null;

        /**
         * For each puzzle, if the puzzle number in 2 bytes converted
         * into a int type is equal to the puzzle id then return the secret key.
         */
        for (Puzzle puzzle : puzzles) {
            if (CryptoLib.byteArrayToSmallInt(puzzle.getPuzzleID()) == puzzleID) {
                secretKey = CryptoLib.createKey(puzzle.getKey());
                break;
            }
        }
        return secretKey;
    }
}
