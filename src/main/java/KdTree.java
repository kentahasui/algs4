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
import edu.princeton.cs.algs4.SET;
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
        root = insert(point, root, Dimension.X, new LinkedStack<>());
    }

    /**
     * Recursive helper method to insert a point.
     *
     * @param point       The point to insert.
     * @param currentNode The current node being processed.
     * @param dimension   X or Y. Even-level nodes use the X dimension, and
     *                    odd-level nodes use Y as the dimension.
     * @param ancestors     A list of nodes from current.parent to the root
     *
     * @return The current node being processed. If a new node is created
     * (insert into leaf), returns a new node.
     */
    private Node insert(Point2D point, Node currentNode, Dimension dimension, LinkedStack<Node> ancestors) {
        // Base case: Insert into root or leaf
        if (currentNode == null) {
            size++;
            return new Node(point, dimension, ancestors);
        }

        // Recursive case: Keep searching
        ancestors.push(currentNode);

        // Point's key is less than current node: insert into LEFT subtree
        if (currentNode.pointInLeftSubtree(point)) {
            currentNode.left = insert(point, currentNode.left, dimension.next(), ancestors);
        }
        // Point's key is greater than current node: insert into RIGHT subtree
        else if (currentNode.pointInRightSubtree(point)) {
            currentNode.right = insert(point, currentNode.right, dimension.next(), ancestors);
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
        for (Point2D point : currentNode.points) {
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

        // Cache old closest distance for pruning
        double oldClosestDistance = closestDistance;

//        System.out.printf("\nProcessing points %s\n", node.points);
//        System.out.printf("Rectangle: (%s, %s) (%s, %s)\n", node.xmin, node.ymin, node.xmax, node.ymax);
//        System.out.printf("Closest so far: %s [distance = %s]\n", closestPoint, closestDistance);


        /*
         * Pruning case: If all points along the segment are farther than the
         * closest point so far, only check the closest subtree
         */
        double distanceToRect = node.rectDistanceSquaredTo(queryPoint);
        if (distanceToRect > oldClosestDistance) {
//            System.out.println("Distance is farther than closest distance! Pruning subtree not containing point");
            nearest(queryPoint, node.subtreeContainingPoint(queryPoint));
            return;
        }

        /*
         * Calculate the closest point in the node
         */
        for (Point2D point : node.points) {
            double distance = point.distanceSquaredTo(queryPoint);
            if (distance < closestDistance) {
//                System.out.printf("    Found new closest point: %s [distance=%s]\n", point, distance);
                closestDistance = distance;
                closestPoint = point;
            }
        }

        // Otherwise we must check other point
        nearest(queryPoint, node.subtreeContainingPoint(queryPoint));
        nearest(queryPoint, node.subtreeNotContainingPoint(queryPoint));
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
        // Key of the node
        private final double key;
        private final Dimension dimension;
        private final SET<Point2D> points;

        // Rectangle
        private double xmin = -1;
        private double xmax = -1;
        private double ymin = -1;
        private double ymax = -1;

        // Left/bottom and right/top subtrees
        private Node left;
        private Node right;

        private Node(Point2D point, Dimension dimension, LinkedStack<Node> ancestors) {
            this.points = new SET<>();
            this.points.add(point);
            this.dimension = dimension;
            this.key = dimension.getKey(point);
            initializeRect(point, ancestors);
        }

        private void draw() {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            for (Point2D point : points) {
                point.draw();
            }
            StdDraw.setPenRadius();
            dimension.drawSegment(this);
        }

        /**
         * Distance from the closest point in this rectangle to the given point.
         * Uses squared distance to avoid null distances.
         */
        private double rectDistanceSquaredTo(Point2D p) {
            double dx = 0.0, dy = 0.0;
            if      (p.x() < xmin) dx = p.x() - xmin;
            else if (p.x() > xmax) dx = p.x() - xmax;
            if      (p.y() < ymin) dy = p.y() - ymin;
            else if (p.y() > ymax) dy = p.y() - ymax;
            return dx*dx + dy*dy;
        }


        /**
         * Constructs the rectangle associated with this Node.
         *
         * @param point   A point in this node
         * @param ancestors All parents from this node up to the root
         */
        private void initializeRect(Point2D point, LinkedStack<Node> ancestors) {
            // Corner case: root node.
            // Draw a vertical line at x = key
            // Splits the unit square in half at the specified x coordinate
            if (ancestors.isEmpty()) {
                xmin = 0.0;
                ymin = 0.0;
                xmax = key;
                ymax = 1.0;
                return;
            }

            Node parent = ancestors.peek();
            if (dimension == Dimension.X) {
                xmin = parent.xmin;
                xmax = key;
                // Current point is part of left subtree
                if (parent.pointInLeftSubtree(point)) {
                    ymin = parent.ymin;
                    ymax = parent.ymax;
                }
                // Right subtree
                else {
                    ymin = parent.ymax;
                    for (Node ancestor : ancestors) {
                        if (point.y() < ancestor.ymax) {
                            ymax = ancestor.ymax;
                            break;
                        }
                    }
                    if (ymax < 0) ymax = 1.0;
                }
            }
            else {
                ymin = parent.ymin;
                ymax = key;
                // left subtree
                if (point.x() < parent.xmax) {
                    xmin = parent.xmin;
                    xmax = parent.xmax;
                }
                // right subtree
                else {
                    // All x coordinates in right subtree are
                    // greater than all x coordinates in right subtree
                    xmin = parent.xmax;

                    // This line segment will continue until we
                    // reach a parent on the right-hand side
                    for (Node ancestor : ancestors) {
                        if (point.x() < ancestor.xmax) {
                            xmax = ancestor.xmax;
                            break;
                        }
                    }
                    // If there are no such parents, draw the line to
                    // the edge of the unit square
                    if (xmax < 0) xmax = 1.0;
                }
            }
        }

        private Node subtreeContainingPoint(Point2D otherPoint) {
            return pointInLeftSubtree(otherPoint) ? left : right;
        }

        private Node subtreeNotContainingPoint(Point2D otherPoint) {
            return pointInLeftSubtree(otherPoint) ? right : left;
        }

        private boolean hasSameKey(Point2D queryPoint) {
            return Double.compare(key, dimension.getKey(queryPoint)) == 0;
        }

        private boolean pointInLeftSubtree(Point2D queryPoint) {
            return key > dimension.getKey(queryPoint);
        }

        private boolean pointInRightSubtree(Point2D queryPoint) {
            return key < dimension.getKey(queryPoint);
        }

        private boolean rectangleInLeftSubtree(RectHV queryRect) {
            return key > dimension.getMin(queryRect);
        }

        private boolean rectangleInRightSubtree(RectHV queryRect) {
            return key < dimension.getMax(queryRect);
        }
    }

    /**
     * Nested helper enum.
     * Represents dimension of a given node.
     * Simplifies math logic.
     */
    private enum Dimension {
        X {
            @Override
            public Dimension next() {
                return Y;
            }

            @Override
            public double getKey(Point2D point) {
                return point.x();
            }

            @Override
            public double getMin(RectHV rect) {
                return rect.xmin();
            }

            @Override
            public double getMax(RectHV rect) {
                return rect.xmax();
            }

            @Override
            public void drawSegment(Node node) {
                StdDraw.setPenColor(StdDraw.RED);
                Point2D p = new Point2D(node.key, node.ymin);
                Point2D q = new Point2D(node.key, node.ymax);
                p.drawTo(q);
            }
        },
        Y {
            @Override
            public Dimension next() {
                return X;
            }

            @Override
            public double getKey(Point2D point) {
                return point.y();
            }

            @Override
            public double getMin(RectHV rect) {
                return rect.ymin();
            }

            @Override
            public double getMax(RectHV rect) {
                return rect.ymax();
            }

            @Override
            public void drawSegment(Node node) {
                StdDraw.setPenColor(StdDraw.BLUE);
                Point2D p = new Point2D(node.xmin, node.key);
                Point2D q = new Point2D(node.xmax, node.key);
                p.drawTo(q);
                StdDraw.setPenColor();
            }
        };

        public Dimension next() {
            return null;
        }

        public double getKey(Point2D point) {
            return -1;
        }

        public double getMin(RectHV rect) {
            return -1;
        }

        public double getMax(RectHV rect) {
            return -1;
        }

        public void drawSegment(Node node) {
            // Do nothing
        }
    }
}
