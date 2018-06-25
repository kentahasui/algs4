/**
 * Unit tests for the draw() method
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;

public class KdTreeDrawTest {
    private void draw(KdTree tree){
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        tree.draw();
        StdDraw.show();
        StdDraw.pause(50000);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for draw()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void draw_emptyTree(){
        KdTree tree = new KdTree();
        draw(tree);
    }

    @Test
    public void draw_oneLine(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        draw(tree);
    }

    @Test
    public void draw_twoLines(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.25, 0.25));
        draw(tree);
    }

    @Test
    public void draw_multiplePointsWithSameKey(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.5, 0.9));
        tree.insert(new Point2D(0.5, 0.3));
        tree.insert(new Point2D(0.25, 0.25));
        draw(tree);
    }

    @Test
    public void draw_horizontal_bothEndpointsHitExistingLine(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.25, 0.25));
        tree.insert(new Point2D(0.2, 0.75));
        tree.insert(new Point2D(0.3, 0.6));
        draw(tree);
    }

    @Test
    public void draw_vertical_bothEndpointsHitExistingLine(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.25, 0.25));
        tree.insert(new Point2D(0.2, 0.75));
        tree.insert(new Point2D(0.3, 0.6));
        tree.insert(new Point2D(0.4, 0.4));
        draw(tree);
    }

    @Test
    public void draw_horizontal_chooseCloserEndpoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.75, 0.75));
        tree.insert(new Point2D(0.25, 0.80));
        tree.insert(new Point2D(0.1, 0.25));
        tree.insert(new Point2D(0.9, 0.25));
        tree.insert(new Point2D(0.6, 0.3));
        draw(tree);
    }

    @Test
    public void draw_vertical_chooseCloserEndpoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.5, 0.5));

        tree.insert(new Point2D(0.9, 0.9));
        tree.insert(new Point2D(0.95, 0.1));

        tree.insert(new Point2D(0.8, 0.8));
        tree.insert(new Point2D(0.7, 0.2));

        tree.insert(new Point2D(0.4, 0.4));
        draw(tree);
    }

    @Test
    public void draw_closerLineDoesNotReach_drawToFurtherLine(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.9, 0.9));

        tree.insert(new Point2D(0.75, 0.4));

        tree.insert(new Point2D(0.9, 0.6));
        tree.insert(new Point2D(0.6, 0.2));

        tree.insert(new Point2D(0.58, 0.4));
        draw(tree);
    }

    @Test
    public void draw_onUnitSquare(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.5));
        tree.insert(new Point2D(0.5, 0.0));

        tree.insert(new Point2D(1.0, 0.5));
        tree.insert(new Point2D(0.5, 1.0));
        StdDraw.setScale(-1, 2);
        draw(tree);
        StdDraw.setScale();
    }

    @Test
    public void draw_atCorners(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.0, 1.0));
        tree.insert(new Point2D(1.0, 0.0));
        tree.insert(new Point2D(1.0, 1.0));

        tree.insert(new Point2D(0.4, 1.0));
        StdDraw.setScale(-1, 2);
        draw(tree);
        StdDraw.setScale();
    }
}

