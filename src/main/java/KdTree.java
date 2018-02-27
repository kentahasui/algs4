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
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;


public class KdTree {
    // Number of points in set (NOT the number of nodes in set)
    private int size = 0;
    // Root node
    private Node root = null;

    // Closest distance and point for nearest() method
    private double closestDistance;
    private Point2D closestPoint;

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
        checkPointIsInUnitSquare(point);
        root = insert(point, root, new LinkedStack<>(), true);
    }

    /**
     * Recursive helper method to insert a point.
     *
     * @param point      The point to insert.
     * @param node       The current node being processed.
     * @param ancestors  A list of nodes from current.parent to the root
     * @param isVertical True for vertical lines (key = point.x(): even-level node).
     *                   False otherwise.
     * @return The current node being processed. If a new node is created
     * (insert into leaf), returns a new node.
     */
    private Node insert(Point2D point, Node node, LinkedStack<Node> ancestors, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(point, ancestors, isVertical);
        }

        // Recursive case: Keep searching
        ancestors.push(node);

        // Point's key is less than current node: insert into LEFT subtree
        if (node.inLeftSubtree(point)) {
            node.left = insert(point, node.left, ancestors, !isVertical);
        }
        // Point's key is greater than node: insert into RIGHT subtree
        else if (node.inRightSubtree(point)) {
            node.right = insert(point, node.right, ancestors, !isVertical);
        }
        // Point's key is equal to current node and point is not already in set:
        // Add point to current node
        else if (!node.contains(point)) {
            size++;
            node.add(point);
        }

        // Return current node to avoid maintaining reference to parent
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
     * @param point       The query point
     * @param currentNode The current node being processed
     * @return True if the tree contains the given point.
     */
    private boolean contains(Point2D point, Node currentNode) {
        // Base case 1: tree is empty or we hit end of search
        if (currentNode == null) return false;

        // Base case 2: Found key in the tree.
        if (currentNode.contains(point)) return true;

            // Recursive case 1: , point will be in left subtree
        else if (currentNode.inLeftSubtree(point)) {
            return contains(point, currentNode.left);
        }

        // Recursive case 2: If in tree, point will be in right subtree
        else {
            return contains(point, currentNode.right);
        }
    }

    /**
     * Finds all points contained within or on the boundary of a query rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null rectangle");
        List<Point2D> out = new ArrayList<>();
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
    private void range(RectHV rect, Node currentNode, List<Point2D> out) {
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

        // Always go down side of dividing line with point
        nearest(queryPoint, node.subtreeContainingPoint(queryPoint));

        // Prune
        double distanceToRect = node.distanceSquaredToClosestPointInSegment(queryPoint);
        if (distanceToRect < closestDistance) {
            nearest(queryPoint, node.subtreeNotContainingPoint(queryPoint));
        }
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
        private final List<Double> coords;

        // Line segment associated with this node.
        // Y coordinates for vertical nodes. X coordinates for horizontal.
        private double segmentMin = -1;
        private double segmentMax = -1;

        // Left/bottom and right/top subtrees
        private Node left;
        private Node right;

        private Node(Point2D point, LinkedStack<Node> ancestors, boolean isVertical) {
            this.coords = new ArrayList<>(1);
            this.isVertical = isVertical;
            this.key = getKey(point);
            initializeSegment(point, ancestors);
            add(point);
        }

        public String toString() {
            return String.format("{Key: %s | points: %s | isVertical: %s}", key, getPoints(), isVertical);
        }

        /**
         * Add a new point to this node
         */
        private void add(Point2D point) {
            double nonKey = isVertical ? point.y() : point.x();
            coords.add(nonKey);
        }

        /**
         * True iff this node already contains the query point
         */
        private boolean contains(Point2D point) {
            double otherKey = getKey(point);
            if (Double.compare(key, otherKey) != 0) return false;
            if (isVertical) return coords.contains(point.y());
            else return coords.contains(point.x());
        }

        /**
         * X coordinate for vertical nodes. Y coordinate for horizontal.
         */
        private double getKey(Point2D point) {
            return isVertical ? point.x() : point.y();
        }

        private boolean inLeftSubtree(Point2D queryPoint) {
            return getKey(queryPoint) < key;
        }

        private boolean inRightSubtree(Point2D queryPoint) {
            return getKey(queryPoint) > key;
        }

        private Node subtreeContainingPoint(Point2D otherPoint) {
            return inLeftSubtree(otherPoint) ? left : right;
        }

        private Node subtreeNotContainingPoint(Point2D otherPoint) {
            return inLeftSubtree(otherPoint) ? right : left;
        }

        private boolean rectangleInLeftSubtree(RectHV queryRect) {
            double rectMin = isVertical ? queryRect.xmin() : queryRect.ymin();
            return key > rectMin;
        }

        private boolean rectangleInRightSubtree(RectHV queryRect) {
            double rectMax = isVertical ? queryRect.xmax() : queryRect.ymax();
            return key < rectMax;
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

        /**
         * Constructs the Line Segment associated with this node.
         *
         * @param point     A point in this node
         * @param ancestors All parents from this node up to the root
         */
        private void initializeSegment(Point2D point, LinkedStack<Node> ancestors) {
            double x = point.x();
            double y = point.y();

            findSegmentMin(x, y, ancestors);
            findSegmentMax(x, y, ancestors);

            // No points found: segments extend to edges of unit square
            if (segmentMin < 0) segmentMin = 0.0;
            if (segmentMax < 0) segmentMax = 1.0;
        }

        private void findSegmentMin(double x, double y, LinkedStack<Node> ancestors) {
            if (isVertical) {
                for (Node ancestor : ancestors) {
                    // Ignore vertical lines
                    if (ancestor.isVertical) continue;
                    // Find highest horizontal line below this point
                    if (ancestor.key < y) {
                        segmentMin = ancestor.key;
                        break;
                    }
                }
            } else {
                for (Node ancestor : ancestors) {
                    // Ignore horizontal lines
                    if (!ancestor.isVertical) continue;
                    // Find first vertical line to the left of this point
                    if (ancestor.key < x) {
                        segmentMin = ancestor.key;
                        break;
                    }
                }
            }
        }

        private void findSegmentMax(double x, double y, LinkedStack<Node> ancestors) {
            if (isVertical) {
                for (Node ancestor : ancestors) {
                    // Skip vertical lines
                    if (ancestor.isVertical) continue;
                    // Find first horizontal line to the above this point
                    if (ancestor.key > y) {
                        segmentMax = ancestor.key;
                        break;
                    }
                }
            } else {
                for (Node ancestor : ancestors) {
                    // Skip horizontal lines
                    if (!ancestor.isVertical) continue;
                    // Find first vertical line to the right of point
                    if (ancestor.key > x) {
                        segmentMax = ancestor.key;
                        break;
                    }
                }
            }
        }
    }
}
