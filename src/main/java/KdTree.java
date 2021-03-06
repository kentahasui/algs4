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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    // Number of points in set (NOT the number of nodes in set)
    private int size = 0;
    // Root node
    private Node root = null;

    // Closest distance and point for nearest() method
    private double closestDistance;
    private Point2D closestPoint;

    // End of segments when inserting new nodes
    private double largestXmin;
    private double largestYmin;

    private double smallestXmax;
    private double smallestYmax;


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Inserts a point into the tree.
     * Does nothing if the point already exists.
     *
     * @param point A point in the unit square.
     * @throws IllegalArgumentException if the point is null or if the point
     *                                  is not within the unit square.
     */
    public void insert(Point2D point) {
        checkPointIsNotNull(point);
        largestXmin = 0.0;
        largestYmin = 0.0;

        smallestXmax = 1.0;
        smallestYmax = 1.0;
        root = insert(point, root, true);
    }

    /**
     * Recursive helper method to insert a point.
     *
     * @param point      The point to insert.
     * @param node       The current node being processed.
     * @param isVertical True for vertical lines (key = point.x(): even-level node).
     *                   False otherwise.
     *                   The root should always be vertical.
     * @return The current node being processed. If a new node is created
     * (insert into leaf), returns a new node.
     */
    private Node insert(Point2D point, Node node, boolean isVertical) {
        // Base case: At root or leaf. We did not find any node with matching key
        // Create a new Node and insert it at a leaf.
        if (node == null) {
            size++;
            return new Node(point, isVertical, largestXmin, largestYmin, smallestXmax, smallestYmax);
        }

        // Recursive case: not at the leaf
        double key = node.getKey(point);

        // Point's key is less than current node: insert into LEFT/BOTTOM subtree
        if (key < node.key) {
            // Update right endpoint
            if (isVertical) smallestXmax = Math.min(smallestXmax, node.key);
            // Update top endpoint
            else            smallestYmax = Math.min(smallestYmax, node.key);
            // Recurse
            node.left = insert(point, node.left, !isVertical);
        }

        // Point's key is greater than node: insert into RIGHT subtree
        else if (key > node.key) {
            // Update left endpoint
            if (isVertical) largestXmin = Math.max(largestXmin, node.key);
            // Update bottom endpoint
            else            largestYmin = Math.max(largestYmin, node.key);
            // Recurse
            node.right = insert(point, node.right, !isVertical);
        }
        // Point's key is equal to current node and point is not already in set:
        // Add point to current node
        else if (!node.contains(point)) {
            size++;
            node.add(point);
        }

        // Return a pointer to current node, so parent can update children
        return node;
    }

    /**
     * Whether or not this tree contains the given point.
     * Throws an exception if the point is null.
     */
    public boolean contains(Point2D point) {
        checkPointIsNotNull(point);
        return contains(point, root);
    }

    /**
     * Recursive helper method for contains.
     *
     * @param point The query point
     * @param node  The current node being processed
     * @return True if the tree contains the given point.
     */
    private boolean contains(Point2D point, Node node) {
        // Base case 1: tree is empty or we hit end of search
        if (node == null) return false;

        double key = node.getKey(point);
        // Recursive case 1: node in left subtree
        if (key < node.key) {
            return contains(point, node.left);
        }
        // Recursive case 2: node in right subtree
        else if (key > node.key) {
            return contains(point, node.right);
        }
        // Base case 2: node has same key as the point. See if node contains the point.
        else {
            return node.contains(point);
        }
    }

    /**
     * Finds all points contained within or on the boundary of a query rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null rectangle");
        List<Point2D> out = new LinkedList<>();
        range(rect, root, out);
        return out;
    }

    /**
     * Private helper method to find all points contained within or
     * on the boundary of a query rectangle.
     * <p>
     * Adds all relevant points to the <em>out</em> parameter.
     *
     * @param rect        The query rectangle.
     * @param currentNode The current node being processed.
     * @param out         The accumulator to store results.
     */
    private void range(RectHV rect, Node currentNode, Collection<Point2D> out) {
        // Base case: reached leaf of the tree. Do nothing.
        if (currentNode == null) return;

        // Recursive case 1: Check left subtree
        // Prunes this path if the rectangle is not in the left subtree.
        if (currentNode.rectangleInLeftSubtree(rect)) {
            range(rect, currentNode.left, out);
        }

        // Add all points contained within the rectangle
        for (Point2D point : currentNode.getPoints()) {
            if (rect.contains(point)) out.add(point);
        }

        // Recursive case 2: Check right subtree
        // Prunes if the rectangle is not in right subtree
        if (currentNode.rectangleInRightSubtree(rect)) {
            range(rect, currentNode.right, out);
        }
    }

    /**
     * Finds the closest point to the query point.
     * Uses euclidean distance as the closeness metric.
     *
     * @param point The query point.
     * @return The closest point to the given point, or null if the tree is empty.
     * @throws IllegalArgumentException if the query point is null.
     */
    public Point2D nearest(Point2D point) {
        checkPointIsNotNull(point);
        closestPoint = null;
        closestDistance = Double.POSITIVE_INFINITY;
        nearest(point, root);
        return closestPoint;
    }

    private void nearest(Point2D queryPoint, Node node) {
        // Base case: we hit a leaf. Do nothing.
        if (node == null) return;

        // Find closest point so far
        for (Point2D point : node.getPoints()) {
            double distance = point.distanceSquaredTo(queryPoint);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }

        // Calculate which side of the dividing line the point is on
        Node subtreeContainingPoint;
        Node subtreeNotContainingPoint;
        double key = node.getKey(queryPoint);
        if (key < node.key) {
            subtreeContainingPoint = node.left;
            subtreeNotContainingPoint = node.right;
        } else {
            subtreeContainingPoint = node.right;
            subtreeNotContainingPoint = node.left;
        }

        // Always go down side of dividing line with point
        nearest(queryPoint, subtreeContainingPoint);

        // Prune if closest point on line segment is farther than closest point so far
        double distanceToSegment = node.distanceSquaredToClosestPointInSegment(queryPoint);
        if (distanceToSegment < closestDistance) {
            nearest(queryPoint, subtreeNotContainingPoint);
        }
    }

    private void checkPointIsNotNull(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("Null point");
        }
    }

    /**
     * Draws points to stdDraw
     */
    public void draw() {
        draw(root);
    }

    /**
     * Recursively draws each node in tree
     */
    private void draw(Node node) {
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
    private static class Node {
        private final boolean isVertical;

        // Key of the node
        private final double key;
        private final Collection<Double> coords;

        // Line segment associated with this node.
        // Y coordinates for vertical nodes. X coordinates for horizontal.
        private final double segmentMin;
        private final double segmentMax;

        // Left/bottom and right/top subtrees
        private Node left;
        private Node right;

        /**
         * Constructor
         */
        private Node(Point2D point,
                     boolean isVertical,
                     double xmin,
                     double ymin,
                     double xmax,
                     double ymax) {
            this.coords = new HashSet<>(1);
            this.isVertical = isVertical;
            this.key = getKey(point);
            if (isVertical) {
                segmentMin = ymin;
                segmentMax = ymax;
            }
            else {
                segmentMin = xmin;
                segmentMax = xmax;
            }
            add(point);
        }

        /**
         * Add a new point to this node
         */
        private void add(Point2D point) {
            double nonKey = isVertical ? point.y() : point.x();
            coords.add(nonKey);
        }

        /**
         * True iff this node already contains the query point.
         * Assume that the points have equal keys.
         */
        private boolean contains(Point2D point) {
            if (isVertical) return coords.contains(point.y());
            else return coords.contains(point.x());
        }

        /**
         * X coordinate for vertical nodes. Y coordinate for horizontal.
         */
        private double getKey(Point2D point) {
            return isVertical ? point.x() : point.y();
        }

        /**
         * True if the rectangle's left/bottom is less than the line segment's smaller endpoint
         */
        private boolean rectangleInLeftSubtree(RectHV queryRect) {
            double rectMin = isVertical ? queryRect.xmin() : queryRect.ymin();
            return key > rectMin;
        }

        /**
         * True if the rectangle's right/top is greater than the line segment's larger endpoint
         */
        private boolean rectangleInRightSubtree(RectHV queryRect) {
            double rectMax = isVertical ? queryRect.xmax() : queryRect.ymax();
            return key < rectMax;
        }

        /**
         * Distance from the closest point in this line segment to the given point.
         * Uses squared distance to avoid null distances.
         */
        private double distanceSquaredToClosestPointInSegment(Point2D p) {
            double x = p.x();
            double y = p.y();
            double closestX, closestY;
            if (isVertical) {
                // Vertical lines have exactly one x coordinate
                closestX = key;
                // query point is under the lower endpoint. Lower endpoint is the closest we get
                if (y < segmentMin) closestY = segmentMin;
                    // Query point is over the higher endpoint. Higher endpoint is the closest we get
                else if (y > segmentMax) closestY = segmentMax;
                    // Query point is contained within the line segment. The closest y coordinate is the query point's y coordinate
                else closestY = y;
            } else {
                closestY = key;
                if (x < segmentMin) closestX = segmentMin;
                else if (x > segmentMax) closestX = segmentMax;
                else closestX = x;
            }
            double dx = x - closestX;
            double dy = y - closestY;
            return dx * dx + dy * dy;
        }

        /**
         * Gets all points stored in this node
         */
        private Iterable<Point2D> getPoints() {
            List<Point2D> out = new ArrayList<>();
            for (double coord : coords) {
                if (isVertical) out.add(new Point2D(key, coord));
                else out.add(new Point2D(coord, key));
            }
            return out;
        }

        private void draw() {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            for (Point2D point : getPoints()) {
                point.draw();
            }
            StdDraw.setPenRadius();
            drawSegment();
        }

        private void drawSegment() {
            Point2D p;
            Point2D q;
            if (isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                p = new Point2D(key, segmentMin);
                q = new Point2D(key, segmentMax);

            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                p = new Point2D(segmentMin, key);
                q = new Point2D(segmentMax, key);
            }
            p.drawTo(q);
            StdDraw.setPenColor();
        }

        public String toString() {
            return String.format("{Key: %s | points: %s | isVertical: %s}", key, getPoints(), isVertical);
        }
    }
}
