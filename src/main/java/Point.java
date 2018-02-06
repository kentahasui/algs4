/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    private Comparator<Point> slopeComparator;

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (isDegenerate(that)) { return Double.NEGATIVE_INFINITY; }
        if (isHorizontal(that)) { return +0.0; }
        if (isVertical(that)) { return Double.POSITIVE_INFINITY; }

        double rise = (double) that.y - y;
        double run = (double) that.x - x;
        return rise / run;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        // Case 1: Both points are equal. Points should be the same.
        if (isDegenerate(that)) { return 0; }

        // Case 2: Both points are on same horizontal line. Compare by x coord.
        if (isHorizontal(that)) { return x < that.x ? -1 : 1; }

        // Case 3: Any other case. Simply compare by y coordinate.
        return y < that.y ? -1 : 1;
    }

    /** Determines whether the given point has the same x and y coordinates */
    private boolean isDegenerate(Point that) {
        return y == that.y && x == that.x;
    }

    /** Determines whether the given point has the same y coordinate */
    private boolean isHorizontal(Point that) {
        return y == that.y;
    }

    /** Determines whether the given point has the same x coordinate */
    private boolean isVertical(Point that) {
        return x == that.x;
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        // Cache existing instance for performance
        if (slopeComparator == null) { slopeComparator = new SlopeComparator(); }
        return slopeComparator;
    }
    /**
     * Immutable comparator.
     * Compares points based on slope to this point.
     * Implemented as a non-static class in order to retain pointer to
     * enclosing class.
     */
    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);

            // If the points have the same slope to the source point,
            // compare by y, then by x
            if (Double.compare(slope1, slope2) == 0) {
                return p1.compareTo(p2);
            }

            // If the points have different slopes, compare by the slopes.
            // The smaller slope is lesser, and the larger slope is greater.
            return Double.compare(slope1, slope2);
        }
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}