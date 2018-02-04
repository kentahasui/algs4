/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Point.java LineSegment.java
 *
 *  A class to calculate Collinear points on a plane via an exhaustive
 *  Brute-Force algorithm.
 *
 *  Points on a plane are represented by a one-dimensional array of
 *  {@link Point} objects.
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.LinkedQueue;

import java.util.Arrays;

/**
 * Calculates all collinear points on a plane via a brute-force algorithm.
 *
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
    private final LinkedQueue<LineSegment> segments;

    /**
     * Constructor. Finds all collinear points consisting of <em>exactly</em> 4
     * points. Does not find any collinear points consisting of less than or
     * greater than 4 points.
     *
     * @param points A one-dimensional array of Points.
     * @throws IllegalArgumentException if ANY of the following is true:
     * <ul>
     *     <li>The input array is null</li>
     *     <li>Any element in the array is null</li>
     *     <li>there are any repeated points in the array (same x and y) </li>
     * </ul>
     */
    public BruteCollinearPoints(Point[] points){
        // Initialize collection for results
        segments = new LinkedQueue<>();

        // Validate input
        validateArrayIsNotNull(points);
        validateNoElementInArrayIsNull(points);
        Arrays.sort(points);
        validateNoRepeatedElements(points);

        // Do core work
        findCollinearPoints(points);
    }

    private void findCollinearPoints(Point[] points){
        int length = points.length;
        for (int p = 0; p < length - 3; p++){
            Point pointP = points[p];
            for (int q = p + 1; q < length - 2; q++){
                double slopeQ = pointP.slopeTo(points[q]);

                for (int r = q + 1; r < length - 1; r++){
                    double slopeR = pointP.slopeTo(points[r]);

                    // Quit early if three of four points are not collinear
                    if (slopeQ != slopeR ){ continue; }

                    for (int s = r + 1; s < length; s++){
                        double slopeS = pointP.slopeTo(points[s]);

                        if (slopeQ == slopeR && slopeR == slopeS){
                            segments.enqueue(createLineSegment(
                                    pointP, points[q], points[r], points[s]));
                        }

//                        System.out.printf("%s %s %s %s\n", p, q, r, s);
                    }
                }
            }
        }
    }

    /**
     * Create a line segment out of 4 collinear points.
     * The endpoints of the line segment are the first and last points
     * when the segment is sorted via the natural order.
     */
    private LineSegment createLineSegment(Point p, Point q, Point r, Point s){
        return new LineSegment(p, s);
//        Point[] sortedPoints = {p, q, r, s};
//        Arrays.sort(sortedPoints);
//        return new LineSegment(sortedPoints[0], sortedPoints[3]);
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

    /** Throws an exception if there are any repeated points in the input */
    private void validateNoRepeatedElements(Point[] points){
        // assert isSorted(points);
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
}
