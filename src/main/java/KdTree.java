/******************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree
 *  Dependencies: Point2D.java RectHV.java
 *
 *  Defines the KdTree class, which represents points within a unit square
 *  using a K-Dimensional tree.
 *  This class is implemented using a 2-dimensional tree.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;
import java.util.List;

public class KdTree {
    private int size = 0;
    private Node root = null;

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    /**
     * Inserts a point into the tree.
     * Does nothing if the point already exists.
     * @param point A point in the unit square.
     * @throws IllegalArgumentException if the point is null or if the point
     * is not within the unit square.
     */
    public void insert(Point2D point) {
        checkPointIsNotNull(point);
        checkPointIsInUnitSquare(point);
        root = insert(point, root, Dimension.X);
    }

    /**
     * Recursive helper method to insert a point.
     *
     * @param point The point to insert.
     * @param currentNode The current node being processed.
     * @param dimension X or Y. Even-level nodes use the X dimension, and
     *                  odd-level nodes use Y as the dimension.
     * @return The current node being processed. If a new node is created
     * (insert into leaf), returns a new node.
     */
    private Node insert(Point2D point, Node currentNode, Dimension dimension){
        // Base case: Insert into root or leaf
        if (currentNode == null) {
            size++;
            return new Node(point, dimension);
        }

        // Point's key is less than current node: insert into LEFT subtree
        if (currentNode.pointInLeftSubtree(point)){
            currentNode.left = insert(point, currentNode.left, dimension.next());
        }
        // Point's key is greater than current node: insert into RIGHT subtree
        else if (currentNode.pointInRightSubtree(point)) {
            currentNode.right = insert(point, currentNode.right, dimension.next());
        }
        // Point's key is equal to current node and point is not already in set:
        // Add point to current node
        // Does nothing if set already contains the point
        else if (!currentNode.points.contains(point)) {
            size++;
            currentNode.points.add(point);
        }

        // Return current node to avoid maintaining reference to parent
        return currentNode;
    }

    /**
     * Whether or not this tree contains the given point.
     * Throws an exception if the point is null.
     */
    public boolean contains(Point2D point){
        checkPointIsNotNull(point);
        return contains(point, root);
    }

    /**
     * Recursive helper method for contains.
     * @param point The query point
     * @param currentNode The current node being processed
     * @return True if the tree contains the given point.
     */
    private boolean contains(Point2D point, Node currentNode){
        // Base case 1: tree is empty or we hit end of search
        if (currentNode == null) return false;

        // Base case 2: Found key in the tree.
        // If this node contains the point, then the tree contains the point
        // Otherwise the tree does NOT contain the point.
        // This is the only node that can contain this point, as the keys
        // are equivalent
        if (currentNode.hasSameKey(point)) {
            return currentNode.points.contains(point);
        }

        // Recursive case 1: Point will be in left subtree
        else if (currentNode.pointInLeftSubtree(point)) {
            return contains(point, currentNode.left);
        }

        // Recursive case 2: Point will be in right subtree
        else {
            return contains(point, currentNode.right);
        }
    }

    /**
     * Finds all points contained within or on the boundary of a query rectangle.
     */
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) throw new IllegalArgumentException("Null rectangle");
        List<Point2D> out = new ArrayList<>();
        range(rect, root, out);
        return out;
    }

    /**
     * Private helper method to find all points contained within or
     * on the boundary of a query rectangle.
     *
     * Adds all relevant point to the <em>out</em> parameter.
     *
     * @param rect The query rectangle.
     * @param currentNode The current node being processed.
     * @param out The accumulator to store results.
     */
    private void range(RectHV rect, Node currentNode, List<Point2D> out){
        // Base case: reached leaf of the tree. Do nothing.
        if (currentNode == null) return;

        // Recursive case 1: Check left subtree
        // Prunes this path if the rectangle is not in the left subtree.
        if (currentNode.rectangleInLeftSubtree(rect)){
            range(rect, currentNode.left, out);
        }

        // Add all points contained within the rectangle
        for (Point2D point : currentNode.points){
            if (rect.contains(point)) out.add(point);
        }

        // Recursive case 2: Check right subtree
        // Prunes if the rectangle is not in right subtree
        if(currentNode.rectangleInRightSubtree(rect)){
            range(rect, currentNode.right, out);
        }

    }

    /**
     * Finds the closest point to the query point.
     * Uses euclidean distance as the closeness metric.
     * @param point The query point.
     * @return The closest point to the given point, or null if the tree is empty.
     * @throws IllegalArgumentException if the query point is null.
     */
    public Point2D nearest(Point2D point){
        checkPointIsNotNull(point);
        ClosestPoint closest = new ClosestPoint();
        nearest(point, root, closest);
        return closest.point;
    }

    /**
     * Recursive helper method for nearest neighbor.
     * Prunes subtrees for efficient search.
     * @param point Query point
     * @param currentNode Currently processing node
     * @param closestSoFar Stores the closest point so far, as well as the
     *                     distance from that point to the query point
     */
    private void nearest(Point2D point, Node currentNode, ClosestPoint closestSoFar){
        // Base case: we hit a leaf. Do nothing.
        if (currentNode == null) return;

        // Calculate distances from all points in node to query point
        // Processes points in natural order
        ClosestPoint closestInNode = new ClosestPoint();
        for (Point2D currentPoint : currentNode.points){
            ClosestPoint current = new ClosestPoint(point, currentPoint);
            if (current.compareTo(closestInNode) < 0){
                closestInNode.update(current);
            }
        }

        // TODO(kentahasui): process points with equal distances
        // Found new closest node: must process points in both subtrees
        // (both sides of the dividing line)
        if (closestInNode.compareTo(closestSoFar) < 0) {
            closestSoFar.update(closestInNode);
            nearest(point, currentNode.subtreeContainingPoint(point), closestSoFar);
            nearest(point, currentNode.subtreeNotContainingPoint(point), closestSoFar);
        }

        // If the point is on this dividing line, we still have to
        // look at both sides of the dividing line
        else if (currentNode.hasSameKey(point)) {
            nearest(point, currentNode.left, closestSoFar);
            nearest(point, currentNode.right, closestSoFar);
        }

        // All points in this node are further away than closest so far.
        // Only need to process points on the same side of the dividing line.
        else {
            nearest(point, currentNode.subtreeContainingPoint(point), closestSoFar);
        }
    }

    private void checkPointIsNotNull(Point2D point){
        if (point == null) {
            throw new IllegalArgumentException("Null point");
        }
    }

    private void checkPointIsInUnitSquare(Point2D point){
        if (point.x() < 0.0 || point.x() > 1.0 || point.y() < 0.0 || point.y() > 1.0){
            throw new IllegalArgumentException("Point not inside unit square");
        }
    }

    /** Draws points to stdDraw */
    public void draw(){
        draw(root, new SegmentCollection());
    }

    private void draw(Node node, SegmentCollection segments){
        // Base case: hit leaf
        if (node == null) return;

        StdDraw.setPenRadius(0.005);
        // Draw point
        for (Point2D point : node.points) {
            point.draw();
        }
        StdDraw.setPenRadius();

        // Draw segment
        segments.findAndDrawSegment(node);

        // Recurse
        draw(node.left, segments);
        draw(node.right, segments);
    }

    /**
     * Node in the KdTree.
     * Each node uses a point's dimension (x or y) when comparing with other points.
     * Each node can have one or more points within.
     */
    private class Node {
        // Key of the node
        private final double key;
        private final Dimension dimension;
        private final TreeSet<Point2D> points;
        private Node left;
        private Node right;

        private Node(Point2D point, Dimension dimension) {
            this.points = new TreeSet<>();
            this.points.add(point);
            this.dimension = dimension;
            this.key = dimension.getKey(point);
        }

        private Node subtreeContainingPoint(Point2D otherPoint) {
            return pointInLeftSubtree(otherPoint) ? left : right;
        }

        private Node subtreeNotContainingPoint(Point2D otherPoint) {
            return pointInLeftSubtree(otherPoint) ? right : left;
        }

        private boolean hasSameKey(Point2D otherPoint) {
            return key == dimension.getKey(otherPoint);
        }

        private boolean pointInLeftSubtree(Point2D otherPoint) {
            return key > dimension.getKey(otherPoint);
        }

        private boolean pointInRightSubtree(Point2D otherPoint) {
            return key < dimension.getKey(otherPoint);
        }

        private boolean rectangleInLeftSubtree(RectHV rect) {
            return key > dimension.getMin(rect);
        }

        private boolean rectangleInRightSubtree(RectHV rect) {
            return key < dimension.getMax(rect);
        }
    }

    /**
     * Nested helper enum.
     * Represents dimension of a given node.
     * Simplifies math logic.
     */
    private enum Dimension{
        X {
            @Override
            public Dimension next() { return Y; }
            @Override
            public double getKey(Point2D point) { return point.x(); }
            @Override
            public double getMin(RectHV rect) { return rect.xmin(); }
            @Override
            public double getMax(RectHV rect) { return rect.xmax(); }
        },
        Y {
            @Override
            public Dimension next() { return X; }
            @Override
            public double getKey(Point2D point) { return point.y(); }
            @Override
            public double getMin(RectHV rect) { return rect.ymin(); }
            @Override
            public double getMax(RectHV rect) { return rect.ymax(); }
        };

        public Dimension next() { return null; }

        public double getKey(Point2D point){ return -1; }
        public double getMin(RectHV rect) { return -1; }
        public double getMax(RectHV rect) { return -1; }
    }

    /**
     * Represents the closest point to a given query point.
     * Helper class for <em>nearest()</em> method.
     * Uses squared euclidean distance to the query point as the closeness
     * metric.
     */
    private static class ClosestPoint {
        Point2D point;

        double distance;

        private ClosestPoint(){
            point = null;
            distance = Double.POSITIVE_INFINITY;
        }

        private ClosestPoint(Point2D queryPoint, Point2D currentPoint){
            this.point = currentPoint;
            this.distance = queryPoint.distanceSquaredTo(currentPoint);
        }

        /** Updates the current point with the point */
        private void update(ClosestPoint otherPoint){
            this.point = otherPoint.point;
            this.distance = otherPoint.distance;
        }

        /** Compares two closest points by distance, then by natural order (y, then x). */
        private int compareTo(ClosestPoint that){
            if (distance < that.distance) return -1;
            if (distance > that.distance) return 1;
            return this.point.compareTo(that.point);
        }
    }

    /**
     * Helper class used purely for drawing.
     */
    private static class SegmentCollection {
        private static final double MIN_POINT = 0.00;
        private static final double MAX_POINT = 1.00;
        private static final Segment LEFT_VERTICAL = new Segment(new Point2D(MIN_POINT, MIN_POINT), new Point2D(MIN_POINT, MAX_POINT));
        private static final Segment RIGHT_VERTICAL = new Segment(new Point2D(MAX_POINT, MIN_POINT), new Point2D(MAX_POINT, MAX_POINT));
        private static final Segment BOTTOM_HORIZONTAL = new Segment(new Point2D(MIN_POINT, MIN_POINT), new Point2D(MAX_POINT, MIN_POINT));
        private static final Segment TOP_HORIZONTAL = new Segment(new Point2D(MIN_POINT, MAX_POINT), new Point2D(MAX_POINT, MIN_POINT));

        private TreeSet<Segment> verticals;
        private TreeSet<Segment> horizontals;

        private SegmentCollection(){
            verticals = new TreeSet<>(new XComparator());
            verticals.add(LEFT_VERTICAL);
            verticals.add(RIGHT_VERTICAL);

            horizontals = new TreeSet<>(new YComparator());
            horizontals.add(TOP_HORIZONTAL);
            horizontals.add(BOTTOM_HORIZONTAL);
        }

        private void findAndDrawSegment(Node node){
//            System.out.printf("Processing points %s\n", node.points);
            TreeSet<Segment> perpendicular;
            TreeSet<Segment> parallel;

            switch(node.dimension){
                case X:
                    perpendicular = horizontals;
                    parallel = verticals;
                    break;
                default: // Y
                    perpendicular = verticals;
                    parallel = horizontals;
            }
//
//            System.out.printf("Perpendicular: %s\n", perpendicular);
//            System.out.printf("Parallel:      %s\n", parallel);

            Point2D smallestPoint = node.points.first();
            NavigableSet<Segment> lesserLines = perpendicular.headSet(smallest(node), false);
            Point2D minEndpoint = endpoint(node, lesserLines.descendingSet());

            Point2D largestPoint = node.points.last();
            NavigableSet<Segment> greaterLines = perpendicular.tailSet(largest(node), false);
            Point2D maxEndpoint = endpoint(node, greaterLines);

//            System.out.printf("Lesser perpendicular lines: %s\n", lesserLines);
//            System.out.printf("Greater perpendicular lines: %s\n", greaterLines);
//
//            System.out.println("Min endpoint: " + minEndpoint);
//            System.out.printf("Max endpoint: %s\n", maxEndpoint);

            // Add the segment
            Segment segment = new Segment(minEndpoint, maxEndpoint);

            // Boundary case: points are at corners
            switch(node.dimension){
                case X:
                    if (smallestPoint.y() == 0 || largestPoint.y() == 1) {
                        // Point is on left vertical line
                        if (node.key == 0){
                            segment = LEFT_VERTICAL;
                        }
                        // Point is on right vertical line
                        else if (node.key == 1){
                            segment = RIGHT_VERTICAL;
                        }

                    }
                    break;
                case Y:
                    // Node is horizontal, but it's at the lower or upper left corner
                    if (smallestPoint.x() == 0 || largestPoint.x() == 1) {
                        // Draw bottom horizontal line
                        if (node.key == 0){
                            segment = BOTTOM_HORIZONTAL;
                        }
                        // Draw top horizontal line
                        else if (node.key == 1){
                            segment = TOP_HORIZONTAL;
                        }
                    }
                    break;
            }
            parallel.add(segment);

            // Draw the actual segment
//            System.out.printf("Drawing segment %s\n", segment);
            if (node.dimension == Dimension.X) StdDraw.setPenColor(StdDraw.RED);
            else StdDraw.setPenColor(StdDraw.BLUE);
            segment.min.drawTo(segment.max);
            StdDraw.setPenColor();

            System.out.println();
        }

        private Point2D endpoint(Node node, NavigableSet<Segment> segments){
            for (Segment segment : segments){
                if (intersects(node, segment)){
                    return endpoint(node, segment);
                }
            }
            return null;
        }

        /**
         * Gets the endpoint of a node
         * @param node
         * @param segment
         * @return
         */
        private Point2D endpoint(Node node, Segment segment){
            switch(node.dimension){
                case X:
                    return new Point2D(node.key, segment.min.y());
                case Y:
                    return new Point2D(segment.min.x(), node.key);
                default:
                    return null;
            }
        }

        /**
         * @param node A given node
         * @param segment A perpendicular line.
         *                If node.dimension is X (vertical), Segment must be a horizontal line
         *                If node.dimension is Y (horizontal), segment must be a vertical line
         * @return True if a line drawn from the node will intersect with the given line segment
         */
        private boolean intersects(Node node, Segment segment){
            switch(node.dimension){
                // We are processing a vertical node
                // Check if a vertical line from this node will intersect
                // the given (horizontal) line
                case X:
                    return segment.min.x() <= node.key && segment.max.x() >= node.key;
                case Y:
                    return segment.min.y() <= node.key && segment.max.y() >= node.key;
                default:
                    return false;
            }
        }

        private Segment smallest(Node node){
            Point2D point = node.points.first();
            return new Segment(point, point);
        }

        private Segment largest(Node node){
            Point2D point = node.points.last();
            return new Segment(point, point);
        }

        /**
         * Represents either a vertical or horizontal line segment
         */
        private static class Segment {
            private Point2D min;
            private Point2D max;

            private Segment(Point2D min, Point2D max){
                this.min = min;
                this.max = max;
            }

            @Override
            public boolean equals(Object other){
                if (this == other) return true;
                if (other == null) return false;
                if (getClass() != other.getClass()) return false;
                Segment otherSegment = (Segment)other;
                return min.equals(otherSegment.min) && max.equals(otherSegment.max);
            }

            @Override
            public String toString() {
                return min + " -> " + max;
            }
        }

        // Compare by X min, then by X max
        private static class XComparator implements Comparator<Segment> {

            @Override
            public int compare(Segment segment1, Segment segment2) {
                if (segment1 == segment2) return 0;
                if (segment1 == null) return -1;
                if (segment2 == null) return 1;
                if (segment1.equals(segment2)) return 0;

                // Compare only by x coordinates
                if (segment1.min.x() < segment2.min.x()) return -1;
                if (segment1.min.x() > segment2.min.x()) return 1;
                if (segment1.max.x() < segment2.max.x()) return -1;
                if (segment1.max.x() > segment2.max.x()) return 1;

                // Then compare by y coordinates
                return 0;
            }
        }

        // Compare by Y min, then by Y max
        private static class YComparator implements Comparator<Segment> {
            @Override
            public int compare(Segment segment1, Segment segment2){
                if (segment1 == segment2) return 0;
                if (segment1 == null) return -1;
                if (segment2 == null) return 1;
                if (segment1.equals(segment2)) return 0;

                // Compare only by y coordinates
                if (segment1.min.y() < segment2.min.y()) return -1;
                if (segment1.min.y() > segment2.min.y()) return 1;
                if (segment1.max.y() < segment2.max.y()) return -1;
                if (segment1.max.y() > segment2.max.y()) return 1;

                return 0;
            }
        }
    }

}
