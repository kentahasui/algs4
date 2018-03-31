/******************************************************************************
 *  Compilation:  javac Outcast.java
 *  Execution:    java Outcast
 *  Dependencies: WordNet.java
 *
 *  Outcast implementation based on WordNet
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordNet){
        validateNotNull(wordNet);
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns){
        validateNotNull(nouns);
        int N = nouns.length;
        if (N < 2) throw new IllegalArgumentException("Must pass in at least two nouns");

        // Cache max distance so far and the outcast
        int maxDistanceSoFar = -1;
        String outcast = null;

        // Compute distance
        for (int i = 0; i < N; i++){
            int distance = 0;
            String noun = nouns[i];

            // Compute distance from this noun to all other nouns
            for (int j = 0 ; j < N; j++){
                /*
                 Avoid computing distance to self.
                 Not strictly necessary since distance will be 0.
                 But can save a computation cycle.
                 */
                if (i == j) continue;
                distance += wordNet.distance(noun, nouns[j]);
            }

            // Update variables if found outcast
            if (distance > maxDistanceSoFar) {
                maxDistanceSoFar = distance;
                outcast = noun;
            }
        }

        // This should never happen as long as there are at least 2 valid
        // nouns passed in.
        if (outcast == null){
            throw new IllegalArgumentException("No outcast found!");
        }

        return outcast;
    }

    private void validateNotNull(Object x){
        if (x == null) throw new IllegalArgumentException("Argument cannot be null");
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
