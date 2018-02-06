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

import java.util.Arrays;

public class FastCollinearPoints {
    // Constants
    private static final int SEGMENT_LENGTH_THRESHOLD = 4;
    private static final double INVALID_SLOPE = Double.NEGATIVE_INFINITY;

    private LinkedQueue<Point>[] points;

    // Collinear line segments
    private final LinkedQueue<LineSegment> segments = new LinkedQueue<>();

    /**
     * Represents a collection of points that make up a LineSegment.
     * LineSegments are constructed according to a given source point, so the
     * source point is the specified in the constructor.
     * This collection of points always has size of at least 1, since
     * the source is always included.
     */
    private static class PointAccumulator {
        private int length;
        private double previousSlope;
        private Point first;
        private Point last;
        private final Point source;
        private final int sourceIndex;

        private PointAccumulator(Point[] points, int index){
            System.out.printf("Creating acc with sourceIndex %s\n", index);
            source = points[index];
            sourceIndex = index;
            reset();
        }

        /** Removes all points other than the source */
        private void reset(){
            length = 1;
            first = source;
            last = source;
            previousSlope = INVALID_SLOPE;
        }

        private boolean isSlopeEqualToPreviousSlope(double slope){
            return previousSlope == INVALID_SLOPE || previousSlope == slope;
        }

        /**
         * Adds a new point and updates the previousSlope field.
         * Updates the first or last pointer if it is an endpoint.
         */
        private void addPoint(Point newPoint, double slope){
            length++;
            previousSlope = slope;

            // If the new point is not an endpoint for the line segment,
            // we are not interested
            if (newPoint.compareTo(first) < 0) { first = newPoint; }
            if (newPoint.compareTo(last) > 0) { last = newPoint; }

        }

        /** Determines whether current lineSegment is large enough to output */
        private boolean isLongEnough(){
            return length >= SEGMENT_LENGTH_THRESHOLD;
        }

        /** Returns the LineSegment that this object represents */
        private LineSegment segment(){
            return new LineSegment(first, last);
        }
    }

    /**
     * Constructor
     * @param points An unsorted array of {@link Point} objects
     */
    public FastCollinearPoints(Point[] points){
        validateArrayIsNotNull(points);          // O(1)
        validateNoElementInArrayIsNull(points);  // O(N)
        Arrays.sort(points);                     // O(NlgN)
        validateNoRepeatedElements(points);      // O(N)

        findCollinearPoints(points);             // O(N^2lgN)

        System.out.printf("%s\n\n", Arrays.toString(segments()));
    }


    /**
     * Finds maximal collinear points of length >= 4.
     * Stores all Collinear Line Segments in the <em>segments</em> instance
     * variable.
     *
     * @param naturalOrdered Points sorted by their natural order.
     */
    private void findCollinearPoints(Point[] naturalOrdered){
        System.out.printf("Sorted array: %s\n\n", Arrays.toString(naturalOrdered));

        // N * (O(NlgN) + O(N)) = O(N^2lgN) + O(N^2) ~ O(N^2lgN)
        for (int sourceIndex = 0; sourceIndex < naturalOrdered.length; sourceIndex++){
            // Accumulator for collinear points
            PointAccumulator acc = new PointAccumulator(naturalOrdered, sourceIndex);

            // TODO(kentahasui): only copy over unused parts of array
            // Create copy of original array to sort by name
            Point[] slopeOrdered = naturalOrdered.clone();  // O(N)

            // Sort the remaining points by the slope to the source
            Arrays.sort(
                    slopeOrdered,
                    sourceIndex,
                    slopeOrdered.length,
                    acc.source.slopeOrder());

            System.out.println("Source " + acc.source  + ": " + Arrays.toString(slopeOrdered));

            addCollinearSegmentsForSlopeOrdering(slopeOrdered, acc); // O(N)
        }
    }

    /**
     * Finds collinear line segments for a specific configuration points.
     * Saves all found
     *
     * @param slopeOrdered
     * @param pointAccumulator Stores source index.
     */
    private void addCollinearSegmentsForSlopeOrdering(Point[] slopeOrdered, PointAccumulator pointAccumulator) {
        // Iterate through all points from source
        for(int destIndex = pointAccumulator.sourceIndex+1; destIndex < slopeOrdered.length; destIndex++) {

            Point current = slopeOrdered[destIndex];
            double slope = pointAccumulator.source.slopeTo(current);

            System.out.printf("Processing point %s -> %s [slope %s]\n", pointAccumulator.source, current, slope);

            // Add point to line segment if slopes are equal
            if (pointAccumulator.isSlopeEqualToPreviousSlope(slope)) {
                pointAccumulator.addPoint(current, slope);
                continue;
            }

            // When the slopes are not equal, check if the collinear segments
            // are long enough
            addNewCollinearSegmentIfLongEnough(pointAccumulator);
            pointAccumulator.reset();
            pointAccumulator.addPoint(current, slope);
        }
        addNewCollinearSegmentIfLongEnough(pointAccumulator);
        System.out.println();
    }

    /** Helper method to avoid code duplication */
    private void addNewCollinearSegmentIfLongEnough(PointAccumulator pointAccumulator){
        // TODO(kentahasui): deal with subsegments for segments with length > 4
        if (pointAccumulator.isLongEnough()) {
            System.out.printf("Adding segment %s\n", pointAccumulator.segment());
            segments.enqueue(pointAccumulator.segment());
        }
    }

    /** Throws an exception if the input array is null */
    private void validateArrayIsNotNull(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
    }

    /** Throws an exception if any element in the input array is null */
    private void validateNoElementInArrayIsNull(Point[] points){
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
    private void validateNoRepeatedElements(Point[] points){
        Point prevPoint = null;
        for (Point point : points){
            if (prevPoint != null && prevPoint.compareTo(point) == 0) {
                throw new IllegalArgumentException(String.format(
                        "Degenerate points found: %s and %s",
                        prevPoint,
                        point));
            }
            prevPoint = point;
        }
    }

    /** Returns number of line segments containing 4 collinear points */
    public int numberOfSegments(){
        return segments.size();
    }

    /** Returns all line segments containing exactly 4 collinear points */
    public LineSegment[] segments() {
        LineSegment[] segmentArray = new LineSegment[segments.size()];
        int index = 0;
        for (LineSegment segment : segments){
            segmentArray[index++] = segment;
        }

        return segmentArray;
    }

    /** Test client */
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
