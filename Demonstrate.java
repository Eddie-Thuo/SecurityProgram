import javax.crypto.SecretKey;


/**
 * Demonstrate class is used to demonstrate
 * the communication between Alice and Bob
 * through a shared secret key with the code
 * written from other classes. This class
 * fulfils the requirements of Question 5.
 *
 * @author Eddie Thuo
 * @author Luke Okomilo
 */
public class Demonstrate {

    public static void main(String[] args) throws Exception {

        Puzzle[] puzzles = Alice.createPuzzles();
        System.out.println("1024 Puzzles created.");

        String[] encryptedPuzzleArray = Alice.encryptPuzzles(puzzles);
        System.out.println("Puzzles encrypted.");

        Alice.writePuzzlesToFile(encryptedPuzzleArray);
        System.out.println("Puzzles written to file 'puzzles.txt'.");

        System.out.println("Random puzzle chosen to crack:");
        String randomPuzzle = Bob.crackRandom();

        System.out.println("Decrypted:");
        System.out.println(randomPuzzle);

        Puzzle puzzle = new Puzzle(CryptoLib.stringToByteArray(randomPuzzle));
        int puzzleNumber = CryptoLib.byteArrayToSmallInt(puzzle.getPuzzleID());
        System.out.println("Chosen puzzle number: " + puzzleNumber);

        SecretKey secretKeyBob = CryptoLib.createKey(puzzle.getKey());

        SecretKey secretKeyAlice = Alice.lookupKey(puzzleNumber, puzzles);
        String plaintextMessage = "Test message from Alice to Bob.";
        System.out.println("Alice sends Bob:\n'" + plaintextMessage + "'.");

        DES des = new DES();

        String encryptedMessage = des.encrypt(plaintextMessage, secretKeyAlice);
        System.out.println("Encrypted message: " + encryptedMessage);

        String decryptedMessage = des.decrypt(encryptedMessage, secretKeyBob);
        System.out.println("Bob decrypts the message and gets:\n'" + decryptedMessage + "'.");
    }
}
