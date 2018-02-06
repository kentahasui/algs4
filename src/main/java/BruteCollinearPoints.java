/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints input.txt
 *  Dependencies: Point.java LineSegment.java
 *
 *  A class to calculate Collinear points on a plane via an exhaustive
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
 * Calculates all collinear points on a plane via a brute-force algorithm.
 * <p>
 * <pre>
 * {@code
 *
 * Point[] points = {
 *     new Point(1,1), new Point(2, 2), new Point(3, 3), new Point(4,4)};
 * BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points):
 * points.numberOfSegments(); // -> returns 1
 * points.segments(); // -> returns {new LineSegment(new Point(1,1), new Point(4,4)}
 * }
 * </pre>
 */
public class BruteCollinearPoints {
    private final LinkedQueue<LineSegment> segments = new LinkedQueue<>();

    /**
     * Constructor. Finds all collinear points consisting of <em>exactly</em> 4
     * points. Does not find any collinear points consisting of less than or
     * greater than 4 points.
     *
     * @param points A one-dimensional array of Points.
     * @throws IllegalArgumentException if ANY of the following is true:
     *                                  <ul>
     *                                  <li>The input array is null</li>
     *                                  <li>Any element in the array is null</li>
     *                                  <li>there are any repeated points in the array (same x and y) </li>
     *                                  </ul>
     */
    public BruteCollinearPoints(Point[] points) {
        // Validate input
        validateArrayIsNotNull(points);          // O(1)
        validateNoElementInArrayIsNull(points);  // O(N)
        Arrays.sort(points);                     // O(NlgN)
        validateNoRepeatedElements(points);      // O(N)

        // Do core work
        findCollinearPoints(points);             // O(N^4)
    }

    private boolean isEqual(double point1, double point2) {
        return Double.compare(point1, point2) == 0;
    }

    /**
     * Finds collinear points of length 4 and adds to the <em>segments</em>
     * instance variable.
     *
     * @param points Array of points sorted via the <b>NATURAL ORDER</b>
     */
    private void findCollinearPoints(Point[] points) {
        int length = points.length;

        // Memoize slopes from a given point for performance
        double[] slopes = new double[length];

        // Quad loop => ~1/4N^4 => O(N^4)
        for (int p = 0; p < length - 3; p++) {
            calculateSlopesFromSource(points, slopes, p); // O(N)

            for (int q = p + 1; q < length - 2; q++) {

                for (int r = q + 1; r < length - 1; r++) {

                    // Quit early if three of four points are not collinear
                    if (!isEqual(slopes[q], slopes[r])) {
                        continue;
                    }

                    for (int s = r + 1; s < length; s++) {
                        if (isEqual(slopes[q], slopes[r]) &&
                                isEqual(slopes[r], slopes[s])) {
                            segments.enqueue(
                                    createLineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates slopes from a source point to all points to the right
     * (points that are greater than the source point, according to the
     * natural order).
     * <p>
     * Mutates the slopes array.
     *
     * @param points      An array of points, sorted by the natural order (y, x)
     * @param slopes      An array of slopes. Formally, for a given index i such that
     *                    sourceIndex < i, the entry slopes[i] is equal to
     *                    the slope from the source point to the ith entry in the
     *                    "points" array.
     * @param sourceIndex The index of the source node in the "points" array.
     */
    private void calculateSlopesFromSource(Point[] points, double[] slopes, int sourceIndex) {
        Point source = points[sourceIndex];
        for (int destIndex = sourceIndex + 1; destIndex < points.length; destIndex++) {
            Point dest = points[destIndex];
            slopes[destIndex] = source.slopeTo(dest);
        }
    }

    /**
     * Create a line segment out of 4 collinear points.
     * The endpoints of the line segment are the first and last points
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
     * Throws an exception if there are any repeated points in the input
     */
    private void validateNoRepeatedElements(Point[] points) {
        // assert isSorted(points);
        Point prevPoint = null;
        for (Point point : points) {
            if (prevPoint != null && prevPoint.compareTo(point) == 0) {
                throw new IllegalArgumentException(String.format(
                        "Degenerate points found: %s and %s",
                        prevPoint,
                        point));
            }
            prevPoint = point;
        }
    }

    /**
     * Returns number of line segments containing 4 collinear points
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * Returns all line segments containing exactly 4 collinear points
     */
    public LineSegment[] segments() {
        LineSegment[] segmentArray = new LineSegment[segments.size()];
        int index = 0;
        for (LineSegment segment : segments) {
            segmentArray[index++] = segment;
        }
        return segmentArray;
    }

    /**
     * Provided test client for BruteCollinearPoints.
     * Reads coordinates from a file and prints collinear segments of length 4.
     * Also displays the segments using {@link StdDraw}
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
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
