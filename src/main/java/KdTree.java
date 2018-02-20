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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    public void draw(){

    }

    /**
     * P
     * @param rect
     * @return
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
        else if (currentNode.hasSameKey(point)){
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

    /**
     * Represents the closest point to a given query point.
     * Helper class for <em>nearest()</em> method.
     * Uses squared euclidean distance to the query point as the closeness
     * metric.
     */
    private class ClosestPoint {
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
     * Node in the KdTree.
     * Each node uses a point's dimension (x or y) when comparing with other points.
     * Each node can have one or more points within.
     */
    private class Node {
        // Key of the node
        private final double key;
        private final Dimension dimension;
        private final Set<Point2D> points;
        private Node left;
        private Node right;

        private Node(Point2D point, Dimension dimension){
            this.points = new TreeSet<>();
            this.points.add(point);
            this.dimension = dimension;
            this.key = dimension.getKey(point);
        }

        private Node subtreeContainingPoint(Point2D otherPoint){
            return pointInLeftSubtree(otherPoint) ? left : right;
        }

        private Node subtreeNotContainingPoint(Point2D otherPoint){
            return pointInLeftSubtree(otherPoint) ? right : left;
        }

        private boolean hasSameKey(Point2D otherPoint){
            return key == dimension.getKey(otherPoint);
        }

        private boolean pointInLeftSubtree(Point2D otherPoint){
            return key > dimension.getKey(otherPoint);
        }

        private boolean pointInRightSubtree(Point2D otherPoint){
            return key < dimension.getKey(otherPoint);
        }

        private boolean rectangleInLeftSubtree(RectHV rect){
            return key > dimension.getMin(rect);
        }

        private boolean rectangleInRightSubtree(RectHV rect){
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
}
