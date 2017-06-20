Please Compile and Run the Demonstrate.java file. Ensure all java files are together for successful compiling. HTML files for documentation can be found in the HTML directory. For writing to a text file, remove code on line 41 in Bob.java to view puzzles.txt file. This was used to prevent overflow of reading puzzles if user decides to run program more than once.

We were unsure as to whether to create a random 2 byte segment of the key randomly between the values 0-65536 or not. Instead we generated a random key with no bounds. Below was a possible method of the key generation with 65536 as a bound. 

 private byte[] generateRandom(){
    byte[] zeroBytes = new byte[6];
    Random random = new Random();
    byte[] randomBytes = CryptoLib.smallIntToByteArray(random.nextInt(65536));
    Arrays.fill(zeroBytes, (byte) 0);
    System.arraycopy(randomBytes, 0, key, 0, 2);
    System.arraycopy(zeroBytes, 0, key, 2, 6);
    return key;
}
