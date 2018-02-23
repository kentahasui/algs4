/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET input.txt
 *  Dependencies: Point2D.java
 *
 *  Represents points in the unit square using a balanced red-black tree.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points = new TreeSet<>();

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D point) {
        checkPointIsNotNull(point);
        checkPointIsInUnitSquare(point);
        points.add(point);
    }

    public boolean contains(Point2D point) {
        checkPointIsNotNull(point);
        return points.contains(point);
    }

    /**
     * Draws the points in the set to {@link edu.princeton.cs.algs4.StdDraw}
     */
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    /**
     * Finds all points within or on the boundary of the rectangle.
     * If the set is empty, returns an empty iterable.
     *
     * @param rect A rectangle
     * @return All points within or on the boundary of the rectangle
     * @throws IllegalArgumentException if the rectangle is null
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Null rectangle");
        }

        List<Point2D> out = new ArrayList<>();
        for (Point2D point : points) {
            // Quit early if search goes past the farthest point in the
            // rectangle
            if (point.y() > rect.ymax()) break;

            // Add points contained within the rectangle
            if (rect.contains(point)) out.add(point);
        }
        return out;
    }

    /**
     * Returns the closest point to a query point.
     * If the set is empty, returns null.
     * If the query point is in the set, returns the point itself.
     * If there are two points equidistant to the query point,
     * breaks ties by natural ordering.
     *
     * @param point A query point within the unit square
     * @return The closest point to the query point, or null
     */
    public Point2D nearest(Point2D point) {
        checkPointIsNotNull(point);
        if (isEmpty()) return null;
        if (points.contains(point)) return point;

        // Cache intermediate results
        Point2D closestPoint = null;
        double closestDistance = Double.POSITIVE_INFINITY;

        // Iterate via natural order
        // Since we process smaller points first, lesser points will
        // hold the tie breaker
        for (Point2D current : points) {
            double distance = point.distanceSquaredTo(current);
            if (distance < closestDistance) {
                closestPoint = current;
                closestDistance = distance;
            }
        }
        return closestPoint;


        // Tree search does not work here
        // Since the points are sorted first by y-coord, then x-coord
    }

    private void checkPointIsNotNull(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("Null point");
        }
    }

    private void checkPointIsInUnitSquare(Point2D point) {
        if (point.x() < 0.0 || point.x() > 1.0 || point.y() < 0.0 || point.y() > 1.0) {
            throw new IllegalArgumentException("Point not inside unit square");
        }
    }

}
