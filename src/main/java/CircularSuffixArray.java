/******************************************************************************
 *  Compilation:  javac CircularSuffixArray.java
 *  Execution:    java CircularSuffixArray
 *  Dependencies:
 *
 *  Circular Suffix Array implementation.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.BinaryStdOut;

public class CircularSuffixArray {
    /* Minimum cutoff to sort via insertion sort */
    private static final int CUTOFF = 15;
    /* Number of characters in extended ASCII alphabet */
//    private static final int R = 256;

    /* Number of characters in the original string */
    private final int n;
    /* Original input string */
    private final char[] str;
    /* Suffixes in sorted order. Contains index of original suffix */
    private final int[] suffixes;

    public CircularSuffixArray(String s) {
        // Exception handling
        if (s == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        // Initialize instance variables
        n = s.length();
        str = s.toCharArray();
        suffixes = new int[n];

        /* Initialize Suffixes */
        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        /* Sort suffixes */
//        lsdRadixSort();
        threeWayQuickSort(0, n - 1, 0);
    }

    /**
     * Returns length of original string
     */
    public int length() {
        return n;
    }

    /**
     * Returns original index of circular suffixes for the given sorted suffix index
     */
    public int index(int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Invalid index: " + i);
        }
        return suffixes[i];
    }

    /**
     * Gets the character stored in the given position for a given circular suffix.
     *
     * @param startIndex - The original index for the suffix
     * @param pos        - The position of the character
     * @return - The character stored at position pos offset by startIndex
     */
    private char getChar(int startIndex, int pos) {
        int newIndex = startIndex + pos;
        if (newIndex >= n) {
            newIndex = newIndex % n;
        }
        return str[newIndex];
    }

    /** Helper function for three-way Radix Quicksort */
    private void threeWayQuickSort(int lo, int hi, int pos) {
        // Base case 1: hi < lo, so we can stop recursion
        if (hi <= lo) return;

        // Base case 2: We've exhausted all digits so we can stop recursion
        if (pos >= n) return;

        // Base case 3: Cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertionSort(lo, hi, pos);
            return;
        }

        // Pointer to elements less than or greater than pivot
        int lt = lo;
        int gt = hi;
        // Pivot element
        char pivot = getChar(suffixes[lo], pos);
        // Start partitioning
        int currIndex = lo + 1;
        while (currIndex <= gt) { // Make sure pointers don't cross
            char c = getChar(suffixes[currIndex], pos);
            // Found an element less than pivot: move to lt section and proceed
            if (c < pivot) swap(lt++, currIndex++);
                // Found an element greater than pivot: move to gt section and proceed
            else if (c > pivot) swap(currIndex, gt--);
                // Found an element that is same as pivot: keep going
            else currIndex++;
        }

        // a[lo..lt-1] < pivot = a[lt..gt] < a[gt+1..hi]
        // Sort suffixes with start character less than pivot element
        threeWayQuickSort(lo, lt - 1, pos);
        // Sort suffixes with start character equal to pivot, starting with next digit / radix position
        threeWayQuickSort(lt, gt, pos + 1);
        // Sort suffixes with start character greater than pivot element
        threeWayQuickSort(gt + 1, hi, pos);
    }

    /**
     * Insertion sort for small subarrays
     */
    private void insertionSort(int lo, int hi, int pos) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(suffixes[j], suffixes[j - 1], pos); j--) {
                swap(j, j - 1);
            }
        }
    }

    /**
     * Swaps elements in suffixes array at positions i and j
     */
    private void swap(int i, int j) {
        int temp = suffixes[i];
        suffixes[i] = suffixes[j];
        suffixes[j] = temp;
    }

    /**
     * Helper function to determine if one suffix is strictly less than another, starting at a given position (radix)
     */
    private boolean less(int startIndexV, int startIndexW, int startPos) {
        for (int currPos = startPos; currPos < n; currPos++) {
            char charV = getChar(startIndexV, currPos);
            char charW = getChar(startIndexW, currPos);
            if (charV < charW) return true;
            if (charV > charW) return false;
        }
        // Are two equal strings less? ==> NO: optimize insertion sort for strings with repeated characters
        return false;
    }

    /** Main method for unit testing */
    public static void main(String[] args) {
        String str = "zebra";
        CircularSuffixArray suffixArray = new CircularSuffixArray(str);
        BinaryStdOut.write(suffixArray.length());
        BinaryStdOut.write(suffixArray.index(0));
        BinaryStdOut.write(suffixArray.index(1));
        BinaryStdOut.write(suffixArray.index(2));
        BinaryStdOut.write(suffixArray.index(3));
        BinaryStdOut.write(suffixArray.index(4));
        BinaryStdOut.flush();
    }

//    /** Sorts the contents of the suffix array via LSD radix sort */
//    private void lsdRadixSort() {
//        if (n == 1) return;
//        // Temporary array to store intermediate results
//        int[] temp = new int[n];
//        // Radix sort from LSD to MSD (right to left)
//        for (int pos = n - 1; pos >= 0; pos--) {
//            // Count of all characters in extended ASCII alphabet (key-indexed counting)
//            int[] count = new int[R + 1];
//            // Increment count for character in the given pos for each suffix
//            for (int suffixNum = 0; suffixNum < n; suffixNum++) {
//                char c = getChar(suffixes[suffixNum], pos);
//                count[c + 1]++; // Offset by 1 for key-indexed counting
//            }
//            // Aggregate the counts to use as offsets into temp
//            for (int r = 0; r < R; r++) {
//                count[r + 1] += count[r];
//            }
//            // Stably sort the entries via the char at [pos]
//            for (int suffixNum = 0; suffixNum < n; suffixNum++) {
//                char c = getChar(suffixes[suffixNum], pos);
//                // Put suffix at the correct position
//                temp[count[c]] = suffixes[suffixNum];
//                // Deal with repeated letters
//                count[c]++;
//            }
//            // Copy entries over from temp
//            System.arraycopy(temp, 0, suffixes, 0, n);
//        }
//    }

//    /** Utility to print circular suffix array to stdout */
//    private void print() {
//        for (int i = 0; i < n; i++) {
//            int startIndex = suffixes[i];
//            for (int pos = 0; pos < n; pos++) {
//                System.out.print(getChar(startIndex, pos));
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

}
