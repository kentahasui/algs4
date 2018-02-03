/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats n T
 *  Dependencies: Percolation.java StdOut.java StdRandom.java StdStats.java
 *
 *  This program runs independent Monte Carlo simulation experiments in order
 *  to estimate the Percolation Threshold.
 *
 *  It takes two command-line arguments:
 *    - n: The side length of a percolation grid (creates a grid with nxn sites)
 *    - T: Number of trials to run.
 *
 *  Program logic:
 *    - Runs T Monte Carlo simulations using an n * n percolation Grid.
 *    - Records each percolation threshold (# open sites / # total sites)
 *    - It then prints:
 *      - The mean of all calculated percolation thresholds
 *      - The standard deviation of all percolation thresholds
 *      - The 95% confidence interval
 ******************************************************************************/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    /* Z value for 95% confidence interval */
    private static final double Z_VALUE = 1.96;

    /* Side length of percolation grid (nxn) */
    private final int sideLength;

    /* Number of trials to run */
    private final double numTrials;

    /* Results. Each element is an estimated percolation threshold for a given trial. */
    private final double[] thresholds;

    /* Mean and standard deviation */
    private final double mean;
    private final double stddev;

    /**
     * PercolationStats constructor
     *
     * @param sideLength - The side length of the percolation grid.
     * @param trials     - Number of trials to run.
     * @throws IllegalArgumentException if sideLength or trials is less than 1.
     */
    public PercolationStats(int sideLength, int trials) {
        validateConstructor(sideLength, trials);

        // Initialize instance variables
        this.sideLength = sideLength;
        numTrials = (double) trials;
        thresholds = new double[trials];

        // Run the trials
        for (int trial = 0; trial < trials; trial++) {
            runTrial(trial);
        }

        // Calculate mean and stddev
        mean = StdStats.mean(thresholds);
        stddev = sideLength == 1 ? Double.NaN : StdStats.stddev(thresholds);

    }

    /**
     * Calculates the sample mean of the percolation threshold.
     *
     * @return mean of the sampled percolation thresholds.
     */
    public double mean() {
        return mean;
    }

    /**
     * Calculates the sample standard deviation for the percolation threshold.
     *
     * @return The standard deviation or NAN if numTrials is 1.
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Calculates the lower bound of the confidence interval for the percolation threshold.
     *
     * @return The lower bound of the confidence interval or NAN if numTrials is 1.
     */
    public double confidenceLo() {
        if (numTrials == 1) {
            return Double.NaN;
        }
        return mean - marginOfError();
    }

    /**
     * Calculates the upper bound of the confidence interval for the percolation threshold.
     *
     * @return The upper bound of the confidence interval or NAN if numTrials is 1.
     */
    public double confidenceHi() {
        if (numTrials == 1) {
            return Double.NaN;
        }
        return mean + marginOfError();
    }

    /**
     * Helper method to calculate margin of error for estimating the confidence intervals
     */
    private double marginOfError() {
        double rootTrials = Math.sqrt(numTrials);
        return Z_VALUE * (stddev() / rootTrials);
    }

    /**
     * Helper method to generate a random row or column.
     *
     * @return A random integer between 1 and n (inclusive).
     */
    private int randomInt() {
        return StdRandom.uniform(sideLength) + 1;
    }

    /**
     * Helper method to run a single trial and calculate an estimated percolation threshold.
     *
     * @param trial The trial number. Stores the result in thresholds[trial].
     */
    private void runTrial(int trial) {
        Percolation percolation = new Percolation(sideLength);
        while (!percolation.percolates()) {
            percolation.open(randomInt(), randomInt());
        }
        double totalSites = (double) (sideLength * sideLength);
        double openSites = (double) percolation.numberOfOpenSites();
        double threshold = openSites / totalSites;
        thresholds[trial] = threshold;
    }

    /**
     * Helper method to validate the constructor. Throws an exception if either of the arguments are less than 1.
     */
    private void validateConstructor(int n, int trials) {
        String errMessage = "Invalid arguments [sideLength: %s, trials:%s]. " +
                "Both must be a non-zero, positive integer.";
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(String.format(errMessage, n, trials));
        }
    }

    /**
     * Main method to run this class as a command-line program.
     * See the comments at the top of this file for more information on running this program.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println("Please enter two arguments: " +
                    "n (side length of the grid) and " +
                    "t (number of trials");
            return;
        }

        int sideLength = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(sideLength, trials);

        StdOut.printf("mean                    = %f \n", stats.mean());
        StdOut.printf("stddev                  = %f \n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f , %f] \n", stats.confidenceLo(), stats.confidenceHi());
    }
}
