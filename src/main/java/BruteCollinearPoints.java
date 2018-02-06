/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints input.txt
 *  Dependencies: Point.java LineSegment.java
 *
 *  A class to calculate Collinear naturalOrdered on a plane via an exhaustive
 *  Brute-Force algorithm.
 *
 *  Points on a plane are represented by a one-dimensional array of
 *  {@link Point} objects.
 *
 *  To run the program, pass in a path to file input.txt on the command line.
 *  input.txt should have a number n followed by n lines of coordinate pairs.
 *  Example:
 *
 *  5
 *  1 1
 *  2 2
 *  3 3
 *  4 400
 *  5 900
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Calculates all collinear naturalOrdered on a plane via a brute-force algorithm.
 * <p>
 * <pre>
 * {@code
 *
 * Point[] naturalOrdered = {
 *     new Point(1,1), new Point(2, 2), new Point(3, 3), new Point(4,4)};
 * BruteCollinearPoints collinearPoints = new BruteCollinearPoints(naturalOrdered):
 * naturalOrdered.numberOfSegments(); // -> returns 1
 * naturalOrdered.segments(); // -> returns {new LineSegment(new Point(1,1), new Point(4,4)}
 * }
 * </pre>
 */
public class BruteCollinearPoints {
    private final LinkedQueue<LineSegment> segments = new LinkedQueue<>();
    private final int numPoints;
    private final Point[] naturalOrdered;

    /**
     * Constructor. Finds all collinear naturalOrdered consisting of <em>exactly</em> 4
     * naturalOrdered. Does not find any collinear naturalOrdered consisting of less than or
     * greater than 4 naturalOrdered.
     *
     * @param points A one-dimensional array of Points.
     * @throws IllegalArgumentException if ANY of the following is true:
     *                                  <ul>
     *                                  <li>The input array is null</li>
     *                                  <li>Any element in the array is null</li>
     *                                  <li>there are any repeated naturalOrdered in the array (same x and y) </li>
     *                                  </ul>
     */
    public BruteCollinearPoints(Point[] points) {
        // Validate input
        validateArrayIsNotNull(points);          // O(1)
        validateNoElementInArrayIsNull(points);  // O(N)

        this.numPoints = points.length;          // O(1)
        this.naturalOrdered = points.clone();    // O(N)
        Arrays.sort(this.naturalOrdered);        // O(NlgN)
        validateNoRepeatedElements();      // O(N)

        // Do core work
        findCollinearPoints();             // O(N^4)
    }

    /**
     * Finds collinear naturalOrdered of length 4 and adds to the <em>segments</em>
     * instance variable.
     * The <em>naturalOrdered</em> instance variable is an array of naturalOrdered that is
     * sorted in the <b>natural order</b>
     */
    private void findCollinearPoints() {
        // Memoize slopes from a given point for performance
        double[] slopes = new double[numPoints];

        // Quad loop => ~1/4N^4 => O(N^4)
        for (int p = 0; p < numPoints - 3; p++) {
            calculateSlopesFromSource(naturalOrdered, slopes, p); // O(N)

            for (int q = p + 1; q < numPoints - 2; q++) {

                for (int r = q + 1; r < numPoints - 1; r++) {

                    // Quit early if three of four naturalOrdered are not collinear
                    if (!isEqual(slopes[q], slopes[r])) {
                        continue;
                    }

                    for (int s = r + 1; s < numPoints; s++) {
                        if (isEqual(slopes[q], slopes[r]) &&
                                isEqual(slopes[r], slopes[s])) {
                            segments.enqueue(
                                    createLineSegment(naturalOrdered[p], naturalOrdered[s]));
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates slopes from a source point to all naturalOrdered to the right
     * (naturalOrdered that are greater than the source point, according to the
     * natural order).
     * <p>
     * Mutates the slopes array.
     *
     * @param points      An array of naturalOrdered, sorted by the natural order (y, x)
     * @param slopes      An array of slopes. Formally, for a given index i such that
     *                    sourceIndex < i, the entry slopes[i] is equal to
     *                    the slope from the source point to the ith entry in the
     *                    "naturalOrdered" array.
     * @param sourceIndex The index of the source node in the "naturalOrdered" array.
     */
    private void calculateSlopesFromSource(Point[] points, double[] slopes, int sourceIndex) {
        Point source = points[sourceIndex];
        for (int destIndex = sourceIndex + 1; destIndex < points.length; destIndex++) {
            Point dest = points[destIndex];
            slopes[destIndex] = source.slopeTo(dest);
        }
    }

    /**
     * Create a line segment out of 4 collinear naturalOrdered.
     * The endpoints of the line segment are the first and last naturalOrdered
     * processed because the input array is sorted at the start via the
     * natural order.
     */
    private LineSegment createLineSegment(Point start, Point end) {
        return new LineSegment(start, end);
    }

    /**
     * Throws an exception if the input array is null
     */
    private void validateArrayIsNotNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
    }

    /**
     * Throws an exception if any element in the input array is null
     */
    private void validateNoElementInArrayIsNull(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Null point found in input");
            }
        }
    }

    /**
     * Throws an exception if there are any repeated naturalOrdered in the input
     */
    private void validateNoRepeatedElements() {
        // assert isSorted(naturalOrdered);
        Point prevPoint = null;
        for (Point point : naturalOrdered) {
            if (prevPoint != null && prevPoint.compareTo(point) == 0) {
                throw new IllegalArgumentException(String.format(
                        "Degenerate naturalOrdered found: %s and %s",
                        prevPoint,
                        point));
            }
            prevPoint = point;
        }
    }

    /**
     * Returns number of line segments containing 4 collinear naturalOrdered
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * Returns all line segments containing exactly 4 collinear naturalOrdered
     */
    public LineSegment[] segments() {
        LineSegment[] segmentArray = new LineSegment[segments.size()];
        int index = 0;
        for (LineSegment segment : segments) {
            segmentArray[index++] = segment;
        }
        return segmentArray;
    }

    /** Helper method to check if two doubles are equal */
    private boolean isEqual(double point1, double point2) {
        return Double.compare(point1, point2) == 0;
    }

    /**
     * Provided test client for BruteCollinearPoints.
     * Reads coordinates from a file and prints collinear segments of length 4.
     * Also displays the segments using {@link StdDraw}
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        // read the n naturalOrdered from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the naturalOrdered
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
