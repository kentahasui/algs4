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
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;
import java.util.List;

public class KdTree {
    // Number of points in set (NOT the number of nodes in set)
    private int size = 0;
    // Root node
    private Node root = null;

    // Closest distance and point for nearest() method
    private double closestDistance;
    private Point2D closestPoint;

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
        root = insert(point, root, Dimension.X, new LinkedStack<Node>());
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
    private Node insert(Point2D point, Node currentNode, Dimension dimension, LinkedStack<Node> parents){
        // Base case: Insert into root or leaf
        if (currentNode == null) {
            size++;
            return new Node(point, dimension, parents);
        }

        // Recursive case: Keep searching
        parents.push(currentNode);

        // Point's key is less than current node: insert into LEFT subtree
        if (currentNode.pointInLeftSubtree(point)){
            currentNode.left = insert(point, currentNode.left, dimension.next(), parents);
        }
        // Point's key is greater than current node: insert into RIGHT subtree
        else if (currentNode.pointInRightSubtree(point)) {
            currentNode.right = insert(point, currentNode.right, dimension.next(), parents);
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
     * Adds all relevant points to the <em>out</em> parameter.
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
        closestPoint = null;
        closestDistance = Double.POSITIVE_INFINITY;
        nearest(point, root);
        return closestPoint;
    }

    private void nearest(Point2D queryPoint, Node node){
        // Base case: we hit a leaf. Do nothing.
        if (node == null) return;

        /*
         * Calculate the closest point in the node
         */
        for (Point2D point : node.points){
            double distance = point.distanceSquaredTo(queryPoint);
            if (distance < closestDistance){
                closestDistance = distance;
                closestPoint = point;
            }
        }

        /*
         * Pruning case: If all points along the segment are farther than the
         * closest point so far, only check the closest subtree
         */
        double distanceToRect = node.rect.distanceSquaredTo(queryPoint);
        if (distanceToRect > closestDistance){
            nearest(queryPoint, node.subtreeContainingPoint(queryPoint));
        }
        // Otherwise we must check other points
        else {
            nearest(queryPoint, node.subtreeContainingPoint(queryPoint));
            nearest(queryPoint, node.subtreeNotContainingPoint(queryPoint));
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
        draw(root);
    }

    private void draw(Node node){
        if (node == null) return;
        node.draw();
        draw(node.left);
        draw(node.right);
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
        private RectHV rect;
        private Node left;
        private Node right;

        private Node(Point2D point, Dimension dimension, LinkedStack<Node> parents){
            this.points = new TreeSet<>();
            this.points.add(point);
            this.dimension = dimension;
            this.key = dimension.getKey(point);
            this.rect = getRect(point, parents);
        }

        private void draw(){
            StdDraw.setPenRadius(0.01);
            for (Point2D point : points) {
                point.draw();
            }
            StdDraw.setPenRadius();
            dimension.drawSegment(this);
        }

        private RectHV getRect(Point2D point, LinkedStack<Node> parents){
            // Corner case: root node.
            // Draw a vertical line at x = key
            // Splits the unit square in half at the specified x coordinate
            if (parents.isEmpty()){
                return new RectHV(0.0, 0.0, key, 1.0);
            }

            Node directParent = parents.peek();
            RectHV parentRect = directParent.rect;
            double xmin;
            double ymin;
            double xmax = -1;
            double ymax = -1;
//            if (directParent.pointInLeftSubtree(point)){
//
//            }
//            else {
//
//            }

            switch(dimension) {
                case X:
                    xmin = parentRect.xmin();
                    xmax = key;
                    // Current point is part of left subtree
                    if (directParent.pointInLeftSubtree(point)) {
                        ymin = parentRect.ymin();
                        ymax = parentRect.ymax();
                    }
                    // Right subtree
                    else {
                        ymin = parentRect.ymax();
                        for (Node parent: parents){
                            if (point.y() < parent.rect.ymax()){
                                ymax = parent.rect.ymax();
                                break;
                            }
                        }
                        if (ymax < 0) ymax = 1.0;
                    }
                    break;
                default: // Y
                    ymin = parentRect.ymin();
                    ymax = key;
                    // left subtree
                    if (point.x() < parentRect.xmax()) {
                        xmin = parentRect.xmin();
                        xmax = parentRect.xmax();
                    }
                    // right subtree
                    else {
                        // All x coordinates in right subtree are
                        // greater than all x coordinates in right subtree
                        xmin = parentRect.xmax();

                        // This line segment will continue until we
                        // reach a parent on the right-hand side
                        for (Node parent : parents){
                            if (point.x() < parent.rect.xmax()){
                                xmax = parent.rect.xmax();
                                break;
                            }
                        }
                        // If there are no such parents, draw the line to
                        // the edge of the unit square
                        if (xmax < 0) xmax = 1.0;
                    }
                    break;
            }
            return new RectHV(xmin, ymin, xmax, ymax);
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
            @Override
            public void drawSegment(Node node){
                StdDraw.setPenColor(StdDraw.RED);
                Point2D p = new Point2D(node.key, node.rect.ymin());
                Point2D q = new Point2D(node.key, node.rect.ymax());
                p.drawTo(q);
                StdDraw.setPenColor();
            }
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
            @Override
            public void drawSegment(Node node){
                StdDraw.setPenColor(StdDraw.BLUE);
                Point2D p = new Point2D(node.rect.xmin(), node.key);
                Point2D q = new Point2D(node.rect.xmax(), node.key);
                p.drawTo(q);
                StdDraw.setPenColor();
            }
        };

        public Dimension next() { return null; }
        public double getKey(Point2D point){ return -1; }
        public double getMin(RectHV rect) { return -1; }
        public double getMax(RectHV rect) { return -1; }
        public void drawSegment(Node node) { }
    }
}
