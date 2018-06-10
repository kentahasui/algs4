/******************************************************************************
 *  Compilation:  javac BurrowsWheeler.java
 *  Execution:    java BurrowsWheeler
 *  Dependencies: CircularSuffixArray.java
 *
 *  Burrows-Wheeler transform and inverseTransform implementation.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    // alphabet size of extended ASCII
    private static final int R = 256;

    /**
     * Burrows-Wheeler transform.
     * Reads: A string of length N from BinaryStdIn.
     * Writes: An integer followed by a stream of N chars to BinaryStdOut.
     */
    public static void transform() {
        // Do nothing if input is empty
        if (BinaryStdIn.isEmpty()) return;

        // Read string and construct suffix array
        String s = BinaryStdIn.readString();
        CircularSuffixArray suffixArray = new CircularSuffixArray(s);
        int n = s.length();

        /* Last column in sorted suffix array */
        char[] lastColumn = new char[n];
        /* Index of original string, when the suffixes are in sorted order */
        int originalStringRowNum = -1;
        /* Initialize lastColumn and originalStringRowNum */
        for (int sortedRowNum = 0; sortedRowNum < n; sortedRowNum++) {
            int startIndex = suffixArray.index(sortedRowNum);
            // Found original string!
            if (startIndex == 0) {
                originalStringRowNum = sortedRowNum;
            }
            lastColumn[sortedRowNum] = getChar(s, startIndex, n - 1);
        }
        /* Write results */
        BinaryStdOut.write(originalStringRowNum);
        for (char c : lastColumn) BinaryStdOut.write(c);
        BinaryStdOut.flush();
    }

    private static char getChar(String s, int startIndex, int pos) {
        int newIndex = startIndex + pos;
        if (newIndex >= s.length()) {
            newIndex = newIndex % s.length();
        }
        return s.charAt(newIndex);
    }

    /** Perform Burrows-Wheeler inverse transform. Reads and writes from BinaryStdOut */
    public static void inverseTransform() {
        if (BinaryStdIn.isEmpty()) return;
        int originalStringRowNum = BinaryStdIn.readInt();

        // Read input string
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            sb.append(BinaryStdIn.readChar());
        }

        // Last column of circular suffix array
        char[] lastColumn = sb.toString().toCharArray();

        // First column of circular suffix array
        char[] firstColumn = Arrays.copyOf(lastColumn, lastColumn.length);
        keyIndexedCountingSort(firstColumn);

        // Build next array
        int[] next = buildNext(lastColumn);

        // Write n chars to stdout. This is the original string
        int index = originalStringRowNum, num = 0;
        while (num < firstColumn.length) {
            BinaryStdOut.write(firstColumn[index]);
            index = next[index];
            num++;
        }
        BinaryStdOut.flush();
    }

    /**
     * Key-indexed counting sort. Stable sort. NOT in-place.
     */
    private static void keyIndexedCountingSort(char[] in) {
        int n = in.length;
        char[] temp = new char[n];
        int[] count = new int[R + 1];
        // Count occurrences for each character
        for (char c : in) count[c + 1]++;
        // Aggregate occurrences
        for (int r = 0; r < R; r++) count[r + 1] += count[r];
        // Copy sorted characters into temp array
        for (char c : in) temp[count[c]++] = c;
        // Copy temp array back into original array
        System.arraycopy(temp, 0, in, 0, n);
    }

    /**
     * Build next[], used to re-create original string
     */
    private static int[] buildNext(char[] lastColumn) {
        int n = lastColumn.length;
        int[] next = new int[n];
        int[] count = new int[R + 1];

        // First stage of key-indexed counting: count occurrences of each char
        for (char c : lastColumn) count[c + 1]++;
        for (int r = 0; r < R; r++) count[r + 1] += count[r];

        for (int lastColIndex = 0; lastColIndex < n; lastColIndex++) {
            // Find character at last column
            char c = lastColumn[lastColIndex];
            // Find row at which this (last) character is the first character
            int firstColIndex = count[c]++;
            // The next row for row is the original row
            next[firstColIndex] = lastColIndex;
        }
        return next;
    }

    /**
     * Sample client that calls {@code transform()} if the command-line
     * argument is "-" and {@code inverseTransform()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
