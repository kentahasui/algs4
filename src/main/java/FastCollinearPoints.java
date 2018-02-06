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

    // Number of points
    private final int numPoints;

    // Stores input points, naturally ordered (by y-coord, then by x-coord)
    private final Point[] naturalOrdered;


    private int numSegments;
    // Line Segments by destination
    private LinkedQueue<Point>[] segmentsByDestination;

    // Collinear line segments
    private final LinkedQueue<LineSegment> segments = new LinkedQueue<>();

    /**
     * Constructor
     * @param points An unsorted array of {@link Point} objects
     */
    public FastCollinearPoints(Point[] points){
        validateArrayIsNotNull(points);              // O(1)
        validateNoElementInArrayIsNull(points);      // O(N)

        numPoints = points.length;                   // O(1)
        naturalOrdered = points.clone();             // O(N)
        Arrays.sort(naturalOrdered);                 // O(NlgN)

        validateNoRepeatedElements(naturalOrdered);  // O(N)
        initSegmentsByDestination();                 // O(N)

        // Core work
        findCollinearPoints();                       // O(N^2lgN)

        System.out.printf("%s\n\n", Arrays.toString(segments()));
    }

    private void initSegmentsByDestination(){
        // Unchecked cast OK for new Array of parameterized type
        segmentsByDestination = new LinkedQueue[numPoints];
        for (int i=0; i<numPoints; i++){
            segmentsByDestination[i] = new LinkedQueue<>();
        }
    }

    /**
     * Finds maximal collinear points of length >= 4.
     * Processes the points stored in the <em>naturalOrdered</em> instance var.
     * <em>naturalOrdered</em> must already be sorted via its natural ordering.
     *
     * Stores all Collinear Line Segments in the <em>segments</em> instance
     * variable.
     *
     */
    private void findCollinearPoints(){
        System.out.printf("Sorted array: %s\n\n", Arrays.toString(naturalOrdered));

        // N * (O(NlgN) + O(N)) = O(N^2lgN) + O(N^2) ~ O(N^2lgN)
        // Iterate until there are only 4 points
        for (int sourceIndex = 0; sourceIndex <= numPoints - SEGMENT_LENGTH_THRESHOLD; sourceIndex++){
            // Accumulator for collinear points
            PointAccumulator acc = new PointAccumulator(naturalOrdered, sourceIndex);

            // Process remaining points, sorted by slope to source
            // EXCLUDE source point
            Point[] slopeOrdered = Arrays.copyOfRange(
                    naturalOrdered, sourceIndex+1, numPoints);  // O(N), but in practice will be N-1 + N - 2 ... 3 in total

            // Sort the remaining points by the slope to the source
            Arrays.sort(slopeOrdered, acc.source.slopeOrder());

            System.out.println("Source " + acc.source  + ": " + Arrays.toString(slopeOrdered));

            findCollinearPointsForGivenSource(slopeOrdered, acc); // O(N)
        }
    }

    /**
     * Finds collinear line segments for a specific configuration points.
     * Saves all found
     *
     * @param slopeOrdered Points sorted by slope to a specific source point.
     *                     Does NOT contain the source point.
     * @param pointAcc Pointer to first and last points in the line segment
     */
    private void findCollinearPointsForGivenSource(Point[] slopeOrdered, PointAccumulator pointAcc) {
        // Iterate through all remaining points
        for(int destIndex = 0; destIndex < slopeOrdered.length; destIndex++) {
            Point current = slopeOrdered[destIndex];
            double slope = pointAcc.source.slopeTo(current);

            System.out.printf("Processing point %s -> %s [slope %s]\n", pointAcc.source, current, slope);

            // Add point to line segment if slopes are equal
            if (pointAcc.isSlopeEqualToPreviousSlope(slope)) {
                pointAcc.addPoint(current, slope);
                continue;
            }

            // When the slopes are not equal, check if the collinear segments
            // are long enough
            addNewCollinearSegmentIfLongEnough(pointAcc);
            pointAcc.reset();
            pointAcc.addPoint(current, slope);
        }
        addNewCollinearSegmentIfLongEnough(pointAcc);
        System.out.println();
    }


    /**
     * Determines whether a point is
     * @param pointAcc
     * @return
     */
    private boolean isSubsegment(PointAccumulator pointAcc, int destIndex){
        Point first = pointAcc.first;
        Point dest = pointAcc.last;

        if (destIndex < 0) { throw new RuntimeException(
                String.format("Cannot find point %s. Binary Search returns %s", dest, destIndex)); }

        LinkedQueue<Point> existingSources = segmentsByDestination[destIndex];
        if (existingSources.isEmpty()) { return false; }

        // Compare first points of line segment with all existing 1st points
        for (Point existingFirst : existingSources) {
            // Same slope => potentially subsegment
            if (first.slopeTo(dest) == existingFirst.slopeTo(dest)){
                // TODO(kentahasui): Determine if check is necessary, since we always process lower points first

                /*
                We only want to add the lowest point as the first
                point in the segment (Otherwise we'd end up with subsegments)


                Thus if the new first is greater than the existing point,
                we have a subsegment.

                Otherwise we the new first is indeed the lowest point with a
                slope to the given array.
                 */
                return first.compareTo(existingFirst) >= 0 ;
            }
        }

        // No matching slopes. This is not a subsegment.
        return false;
    }

    /** Helper method to avoid code duplication */
    private void addNewCollinearSegmentIfLongEnough(PointAccumulator pointAcc){

        // TODO(kentahasui): deal with subsegments for segments with length > 4
        // Guard clause: do nothing for < 4 collinear points
        if (pointAcc.isTooShort()) { return; }

        int destIndex = Arrays.binarySearch(naturalOrdered, pointAcc.last);
        // Guard clause: do nothing for subsegments
        if (isSubsegment(pointAcc, destIndex)){
            System.out.printf("Found subsegment! %s -> %s\n", pointAcc.first, pointAcc.last);
            return;
        }

        System.out.printf("Adding segment %s\n", pointAcc.segment());
        segmentsByDestination[destIndex].enqueue(pointAcc.first);
        segments.enqueue(pointAcc.segment());


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
        // TODO(kentahasui): Generate segments from destination, source pairs
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

        private PointAccumulator(Point[] points, int index){
            System.out.printf("Creating acc with sourceIndex %s\n", index);
            source = points[index];
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

            // TODO(kentahasui): determine if needed

            // If the new point is not an endpoint for the line segment,
            // we are not interested
            if (newPoint.compareTo(first) < 0) { first = newPoint; }
            if (newPoint.compareTo(last) > 0) { last = newPoint; }

        }

        /** Determines whether current lineSegment is large enough to output */
        private boolean isTooShort(){
            return length < SEGMENT_LENGTH_THRESHOLD;
        }

        /** Returns the LineSegment that this object represents */
        private LineSegment segment(){
            return new LineSegment(first, last);
        }
    }

}
