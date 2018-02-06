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

    // Number of segments found
    private int numSegments;

    // Maps Destination points to all
    /*
     * Running collection of Collinear Line Segments.
     * Formally, for all
     */
    private LinkedQueue<Point>[] sourcesByDestination;

    /**
     * Constructor.
     * @param points An array of {@link Point} objects. Can be sorted or unsorted.
     * @throws IllegalArgumentException if any of the below hold:
     *
     * <ul>
     *     <li>points is null</li>
     *     <li>points contains a null element</li>
     *     <li>points contains a repeated point</li>
     * </ul>
     */
    public FastCollinearPoints(Point[] points){
        validateArrayIsNotNull(points);              // O(1)
        validateNoElementInArrayIsNull(points);      // O(N)

        numSegments = 0;
        numPoints = points.length;                   // O(1)
        naturalOrdered = points.clone();             // O(N)
        Arrays.sort(naturalOrdered);                 // O(NlgN)

        validateNoRepeatedElements(naturalOrdered);  // O(N)
        initSegmentsByDestination();                 // O(N)

        // Core work
        findCollinearPoints();                       // O(N^2lgN)

        System.out.printf("All segments: [%s] %s\n\n", numberOfSegments(), Arrays.toString(segments()));
    }

    private void initSegmentsByDestination(){
        // Unchecked cast OK for new Array of parameterized type
        sourcesByDestination = new LinkedQueue[numPoints];
        for (int i=0; i<numPoints; i++){
            sourcesByDestination[i] = new LinkedQueue<>();
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
//        System.out.printf("Sorted array: %s\n\n", Arrays.toString(naturalOrdered));

        // (N-4) * (O(NlgN) + O(N)) = O(N^2lgN) + O(N^2) ~ O(N^2lgN)
        // Iterate until there are only 4 points
        for (int sourceIndex = 0; sourceIndex <= numPoints - SEGMENT_LENGTH_THRESHOLD; sourceIndex++){
            // Accumulator for collinear points
            PointAccumulator acc = new PointAccumulator(naturalOrdered, sourceIndex);

            // Process remaining points, sorted by slope to source
            // EXCLUDES source point
            Point[] slopeOrdered = Arrays.copyOfRange(
                    naturalOrdered, sourceIndex+1, numPoints);  // O(N), but in practice will be N-1 + N - 2 ... 3 in total

            // Sort the remaining points by the slope to the source
            Arrays.sort(slopeOrdered, acc.source.slopeOrder());      // O(NlgN)

//            System.out.printf("\nSource %s: %s\n", acc.source, Arrays.toString(slopeOrdered));

            findCollinearPointsForGivenSource(slopeOrdered, acc); // O(N)
        }
    }

    /**
     * Finds collinear line segments for a specific configuration points.
     * Saves all found
     *
     * @param slopeOrdered Points sorted by slope to a specific source point.
     *                     Does NOT contain the source point.
     * @param pointAcc Pointer to first and dest points in the line segment
     */
    private void findCollinearPointsForGivenSource(Point[] slopeOrdered, PointAccumulator pointAcc) {
        // Iterate through all remaining points
        for(int destIndex = 0; destIndex < slopeOrdered.length; destIndex++) {
            Point current = slopeOrdered[destIndex];
            double slope = pointAcc.source.slopeTo(current);

//            System.out.printf("Processing point %s -> %s [slope %s]\n", pointAcc.source, current, slope);

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
    }


    /**
     * Adds a new Collinear Line Segment if the below two conditions hold:
     * <ul>
     *     <li>The segment contains at least 4 points</li>
     *     <li>The segment is NOT a subsegment of a line segment that has already been found</li>
     * </ul>
     * */
    private void addNewCollinearSegmentIfLongEnough(PointAccumulator pointAcc){
        // Guard clause: do nothing for < 4 collinear points
        if (pointAcc.isTooShort()) { return; }

        // Guard clause: do nothing for subsegments
        int destIndex = Arrays.binarySearch(naturalOrdered, pointAcc.dest);     // O(lgN)
        if (isSubsegment(pointAcc, destIndex)){
            System.out.printf("Found subsegment! %s -> %s\n", pointAcc.source, pointAcc.dest);
            return;
        }

        // Add Line Segment
        System.out.printf("Adding segment %s\n", pointAcc.segment());
        numSegments++;
        sourcesByDestination[destIndex].enqueue(pointAcc.source);
    }

    /**
     * Determines whether a point is
     * @param pointAcc
     * @return
     */
    private boolean isSubsegment(PointAccumulator pointAcc, int destIndex){
        Point dest = pointAcc.dest;
        double slope = pointAcc.previousSlope;

        if (destIndex < 0) { throw new RuntimeException(
                String.format("Cannot find point %s. Binary Search returns %s", dest, destIndex)); }

        LinkedQueue<Point> existingSources = sourcesByDestination[destIndex];
        if (existingSources.isEmpty()) { return false; }

        // Compare first points of line segment with all existing 1st points
        for (Point existingSource : existingSources) {
            // Same slope => subsegment
            if (slope == existingSource.slopeTo(dest)){
                return true;
            }
        }

        // No matching slopes. This is not a subsegment.
        return false;
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
        return numSegments;
    }

    /** Returns all line segments containing exactly 4 collinear points */
    public LineSegment[] segments() {
        // O(numSegments)

        // Generate LineSegments from Destination, Source pairs
        LineSegment[] segmentArray = new LineSegment[numSegments];

        int outIndex = 0;
        for (int destIndex = 0; destIndex < sourcesByDestination.length; destIndex++){
            Point dest = naturalOrdered[destIndex];
            LinkedQueue<Point> sources = sourcesByDestination[destIndex];
            for (Point source : sources) {
                segmentArray[outIndex++] = new LineSegment(source, dest);
            }
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
        private final Point source;
        private Point dest;

        private PointAccumulator(Point[] points, int index){
            System.out.printf("Creating acc with sourceIndex %s\n", index);
            source = points[index];
            reset();
        }

        /** Removes all points other than the source */
        private void reset(){
            length = 1;
            dest = source;
            previousSlope = INVALID_SLOPE;
        }

        private boolean isSlopeEqualToPreviousSlope(double slope){
            return previousSlope == INVALID_SLOPE || previousSlope == slope;
        }

        /**
         * Adds a new point and updates the previousSlope field.
         * Updates the first or dest pointer if it is an endpoint.
         */
        private void addPoint(Point newPoint, double slope){
            length++;
            previousSlope = slope;

            // ALWAYS update dest, since the array is in naturally increasing
            // order when the slopes are equivalent
            dest = newPoint;

            // TODO(kentahasui): determine if needed

            // If the new point is not an endpoint for the line segment,
            // we are not interested
//            if (newPoint.compareTo(first) < 0) { first = newPoint; }


//            if (newPoint.compareTo(dest) > 0) { dest = newPoint; }

        }

        /** Determines whether current lineSegment is large enough to output */
        private boolean isTooShort(){
            return length < SEGMENT_LENGTH_THRESHOLD;
        }

        /** Returns the LineSegment that this object represents */
        private LineSegment segment(){
            return new LineSegment(source, dest);
        }
    }

}
