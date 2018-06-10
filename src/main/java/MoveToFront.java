/******************************************************************************
 *  Compilation:  javac MoveToFront.java
 *  Execution:    java MoveToFront
 *  Dependencies:
 *
 *  Move-To-Front encoding / decoding implementation.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    public static void encode() {
        // Initialize array of chars
        char[] chars = new char[R];
        for (int r = 0; r < R; r++) chars[r] = (char) r;

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int pos = 0;

            // Find input character in alphabet array
            for (int i = 0; i < R; i++) {
                if (chars[i] == c) {
                    pos = i;
                    break;
                }
            }

            // Shift all entries to the right
            for (int i = pos; i > 0; i--) {
                chars[i] = chars[i - 1];
            }

            // Move the specified char to the front
            chars[0] = c;

            // Write the position we found the char in, as an 8-bit char
            BinaryStdOut.write((char) pos);
        }

        BinaryStdOut.flush();
    }

    public static void decode() {
        // Initialize array of chars
        char[] chars = new char[R];
        for (int r = 0; r < R; r++) chars[r] = (char) r;

        while (!BinaryStdIn.isEmpty()) {
            // Read index of character (as 8-bit int)
            int pos = BinaryStdIn.readChar();

            // Find character
            char c = chars[pos];

            // Move character to front
            for (int r = pos; r > 0; r--) chars[r] = chars[r - 1];
            chars[0] = c;

            // Write character
            BinaryStdOut.write(c);
        }
        BinaryStdOut.flush();
    }

    /**
     * Sample client that calls {@code encode()} if the command-line
     * argument is "-" and {@code decode()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
