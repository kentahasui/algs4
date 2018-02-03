/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation k < input.txt
 *  Dependencies: RandomizedQueue.java StdIn.java StdOut.java
 *
 *  Command-line client to print k items from n StdIn items uniformly at random.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/** Permutation client class for Deque and RandomizedQueue classes */
public class Permutation {
    /**
     * Main method to run this class as a command-line program.
     * See the comments at the top of this file for more information on running this program.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Validate that there is one command line argument
        if (args.length < 1) {
            StdOut.println("Please enter one argument: k (number of items to print)");
            return;
        }

        // Cache number of items to print. Generate Randomized Queue.
        int numItemsToPrint = Integer.parseInt(args[0]);

        // Read lines from Standard Input to queue.
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        // Validate that user passed in enough items to the program
        if (queue.size() < numItemsToPrint) {
            StdOut.printf("Please enter at least %s items\n",
                    numItemsToPrint, numItemsToPrint);
            return;
        }

        // Print permutations
        for (int num = 0; num < numItemsToPrint; num++) {
            StdOut.println(queue.dequeue());
        }
    }
}
