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

public class KdTree {

    public boolean isEmpty(){ return false; }
    public int size() { return -1; }
    public void insert(Point2D point) {}
    public boolean contains(Point2D point){ return false; }
    public void draw(){}
    public Iterable<Point2D> range(RectHV rect){
        return new ArrayList<>();
    }
    public Point2D nearest(Point2D point){
        return null;
    }

}
