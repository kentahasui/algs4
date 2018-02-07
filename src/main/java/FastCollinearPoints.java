/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints input.txt
 *  Dependencies: Point.java LineSegment.java
 *
 *  A class to calculate Collinear points on a plane via an efficient
 *  sorting-based algorithm.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    // Constants
    private static final int SEGMENT_LENGTH_THRESHOLD = 4;
    private static final double INVALID_SLOPE = Double.NEGATIVE_INFINITY;

    // Number of points in input.
    private final int numPoints;

    // Stores input points, naturally ordered (by y-coord, then by x-coord)
    private final Point[] naturalOrdered;

    // Cache for adjacent points with equal slopes [collinear points]
    // from a given destination.
    private Point source;
    private Point dest;
    private double previousSlope;
    private int length;

    // Stores output segments
    private final LinkedQueue<LineSegment> segments;

    /*
     * Running collection of slopes.
     * Each index i in this array represents the Point at naturalOrdered[i]
     * Each queue at index i contains slopes for collinear line segments,
     * with the destination at naturalOrdered[i].
     */
    private final List<LinkedQueue<Double>> slopesByDestination;

    /**
     * Constructor.
     *
     * @param points An array of {@link Point} objects. Can be sorted or unsorted.
     * @throws IllegalArgumentException if any of the below hold:
     *                                  <p>
     *                                  <ul>
     *                                  <li>points is null</li>
     *                                  <li>points contains a null element</li>
     *                                  <li>points contains a repeated point</li>
     *                                  </ul>
     */
    public FastCollinearPoints(Point[] points) {
        // Null checks
        validateArrayIsNotNull(points);                     // O(1)
        validateNoElementInArrayIsNull(points);             // O(N)

        // Initialize instance variables
        numPoints = points.length;                          // O(1)
        naturalOrdered = Arrays.copyOf(points, numPoints);  // O(N)
        Arrays.sort(naturalOrdered);                        // O(NlgN)

        // Initialize running total of segments
        segments = new LinkedQueue<>();

        // Initialize cache of line segment slopes, keyed by destination point.
        // In production code, we'd likely use a hash table for more efficient
        // analysis (we don't have to binary search through the sorted input).
        // Hash tables would offer worse worst-case performance O(N) but
        // significantly better expected performance O(1) for finding the
        // index of a point.
        slopesByDestination = new ArrayList<>(numPoints);
        for (int i = 0; i < numPoints; i++) {
            slopesByDestination.add(new LinkedQueue<>());
        }

        // Repeated element check
        validateNoRepeatedElements(naturalOrdered);      // O(N)

        // Core work
        findCollinearPoints();                           // O(N^2lgN)
    }

    /**
     * Finds maximal collinear points of length >= 4.
     * Processes the points stored in the <em>naturalOrdered</em> instance var.
     * <em>naturalOrdered</em> must already be sorted via its natural ordering.
     * <p>
     * Stores all Collinear Line Segments in the <em>segments</em> instance
     * variable.
     */
    private void findCollinearPoints() {
        // (N-4) * (O(NlgN) + O(N)) = O(N^2lgN) + O(N^2) ~ O(N^2lgN)
        // Iterate until there are only 4 points
        for (int sourceIndex = 0; sourceIndex <= numPoints - SEGMENT_LENGTH_THRESHOLD; sourceIndex++) {
            // Cache source point, initialize pointers
            source = naturalOrdered[sourceIndex];
            reset();

            // Process remaining points, sorted by slope to source
            Point[] slopeOrdered = Arrays.copyOfRange(
                    naturalOrdered, sourceIndex + 1, numPoints);  // O(N), but in practice will be N-1 + N - 2 ... 3 in total

            // Sort the remaining points by the slope to the source
            Arrays.sort(slopeOrdered, source.slopeOrder());      // O(NlgN)

            findCollinearPointsForGivenSource(slopeOrdered); // O(N)
        }
    }

    /**
     * Finds collinear line segments for a specific configuration points.
     * The source point is stored in the <em>source</em> instance variable
     *
     * @param slopeOrdered Points sorted by slope to a specific source point.
     *                     Does NOT contain the source point.
     */
    private void findCollinearPointsForGivenSource(Point[] slopeOrdered) {
        // Iterate through all remaining points
        for (int destIndex = 0; destIndex < slopeOrdered.length; destIndex++) {
            Point current = slopeOrdered[destIndex];
            double slope = source.slopeTo(current);

            // Add point to line segment if slopes are equal
            if (isSlopeEqualToPreviousSlope(slope)) {
                addPoint(current, slope);
                continue;
            }

            // When the slopes are not equal, check if the collinear segments
            // are long enough
            addNewCollinearSegmentIfLongEnough();
            reset();
            addPoint(current, slope);
        }
        addNewCollinearSegmentIfLongEnough();
    }


    /**
     * Adds a new Collinear Line Segment if the below two conditions hold:
     * <ul>
     * <li>The segment contains at least 4 points</li>
     * <li>The segment is NOT a subsegment of a line segment that has already been found</li>
     * </ul>
     */
    private void addNewCollinearSegmentIfLongEnough() {
        // Guard clause: do nothing for < 4 collinear points
        if (length < SEGMENT_LENGTH_THRESHOLD) {
            return;
        }

        // Guard clause: do nothing for subsegments
        int destIndex = Arrays.binarySearch(naturalOrdered, dest);     // O(lgN)
        if (isSubsegment(destIndex)) {
            return;
        }

        // Add Line Segment
        segments.enqueue(new LineSegment(source, dest));
        slopesByDestination.get(destIndex).enqueue(previousSlope);
    }

    /**
     * Determines whether a collection of points is a subsegment of a
     * previously-found line segment
     *
     * @param destIndex The index of the destination point
     * @return True if the line segment is a subsegment
     */
    private boolean isSubsegment(int destIndex) {
        // All slopes of line segments to this destination
        LinkedQueue<Double> slopesToDest = slopesByDestination.get(destIndex);

        // If there are no line segments to this destination yet,
        // there cannot be any subsegments.
        if (slopesToDest.isEmpty()) {
            return false;
        }

        // If there is a line segment to this destination with the same slope,
        // we have found a subsegment.
        for (double existingSlope : slopesToDest) {
            if (isEqual(previousSlope, existingSlope)) {
                return true;
            }
        }

        // No matching slopes. This is not a subsegment.
        return false;
    }

    /**
     * Resets instance variables when we find a point with different slope
     */
    private void reset() {
        length = 1;
        dest = source;
        previousSlope = INVALID_SLOPE;
    }

    /**
     * True if the new point is the first point after the source,
     * or if the slope to this point is equal to the slope to the
     * previous point.
     */
    private boolean isSlopeEqualToPreviousSlope(double newSlope) {
        return isEqual(previousSlope, INVALID_SLOPE)
                || isEqual(previousSlope, newSlope);
    }

    /**
     * Adds point to current line segment chain and updates
     * various instance variables
     */
    private void addPoint(Point newPoint, double newSlope) {
        length++;
        previousSlope = newSlope;

        /* We do not need to check if the new point is less than the source.
           We iterate through the input array in sorted order, and
           ignore all points we have already processed.
           If the point is the smallest point of a collinear segment,
           the line segment would have been added as a source already.
           Similarly, we do not need to check if the new point is greater
           than the destination. Since the array is already sorted in
           its natural order before we sort by slope order, when two points
           have the same slope they are kept in their natural order.
           Thus the point that we encountered last will ALWAYS be the largest
           point in the line segment.
         */
        dest = newPoint;
    }

    /**
     * Convenience method for comparing two doubles
     */
    private boolean isEqual(double a, double b) {
        return Double.compare(a, b) == 0;
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
                throw new IllegalArgumentException("No point in input can be null");
            }
        }
    }

    /**
     * Throws an exception if there are any repeated points in the input
     * The array must be sorted.
     */
    private void validateNoRepeatedElements(Point[] points) {
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
        // Create and return defensive copy of segments
        LineSegment[] out = new LineSegment[numberOfSegments()];
        int index = 0;
        for (LineSegment segment : segments) {
            out[index++] = segment;
        }
        return out;
    }

    /**
     * Test client
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
