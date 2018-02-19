import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/******************************************************************************
 *  Compilation:  javac KdTreeTest.java
 *  Execution:    java KdTreeTest
 *  Dependencies: KdTree.java
 *
 *  Unit tests for KdTree.java
 *
 ******************************************************************************/
public class KdTreeTest {
    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for isEmpty()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void treeHasNoPoints_shouldBeEmpty(){
        KdTree tree = new KdTree();
        assertThat(tree.isEmpty()).isTrue();
    }

    @Test
    public void treeHasOnePoint_shouldNotBeEmpty(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.1));
        assertThat(tree.isEmpty()).isFalse();
    }

    @Test
    public void treeHasTwoPoints_shouldNotBeEmpty(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.1, 0.1));
        assertThat(tree.isEmpty()).isFalse();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for size()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void treeHasNoPoints_shouldHaveSizeZero(){
        KdTree tree = new KdTree();
        assertThat(tree.size()).isEqualTo(0);
    }

    @Test
    public void treeHasOnePoint_shouldHaveSizeOne(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.1));
        assertThat(tree.size()).isEqualTo(1);    }

    @Test
    public void treeHasTwoPoints_shouldHasSizeTwo(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.1, 0.1));
        assertThat(tree.size()).isEqualTo(2);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for insert()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void insertNullPoint_shouldThrowException(){
        KdTree tree = new KdTree();
        tree.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertPointNotInUnitSquare_shouldThrowException(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(2, 2));
    }

    @Test
    public void insertIntoEmptytree_addsNewPoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.2));
        assertThat(tree.contains(new Point2D(0.1, 0.2))).isTrue();
    }

    @Test
    public void insertIntotree_addsNewPoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.2));
        assertThat(tree.contains(new Point2D(0.3, 0.4))).isFalse();
        tree.insert(new Point2D(0.3, 0.4));
        assertThat(tree.contains(new Point2D(0.3, 0.4))).isTrue();
    }

    @Test
    public void insertExistingPoint_doesNothing(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.1, 0.1));
        assertThat(tree.size()).isEqualTo(1);
    }

    @Test
    public void insertPointWithSameXCoordAsExisting_addsNewPoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.2));
        tree.insert(new Point2D(0.1, 0.9));
        assertThat(tree.contains(new Point2D(0.1, 0.2))).isTrue();
        assertThat(tree.contains(new Point2D(0.1, 0.9))).isTrue();
        assertThat(tree.size()).isEqualTo(2);
    }

    @Test
    public void insertPointWithSameYCoordAsExisting_addsNewPoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.2, 0.1));
        tree.insert(new Point2D(0.9, 0.1));
        assertThat(tree.contains(new Point2D(0.2, 0.1))).isTrue();
        assertThat(tree.contains(new Point2D(0.9, 0.1))).isTrue();
        assertThat(tree.size()).isEqualTo(2);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for contains()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void containsOnNullPoint_shouldThrowException(){
        KdTree tree = new KdTree();
        tree.contains(null);
    }

    @Test
    public void emptytree_shouldNotContainAnyPoint(){
        KdTree tree = new KdTree();
        assertThat(tree.contains(new Point2D(0.1, 0.1))).isFalse();
    }

    @Test
    public void nonEmptytree_doesNotContainPoint_containsShouldBeFalse(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(1, 1));
        assertThat(tree.contains(new Point2D(0.0, 1))).isFalse();
    }

    @Test
    public void nonEmptytree_containsPoint_containsShouldBeTrue(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(1, 1));
        assertThat(tree.contains(new Point2D(0.0, 0.0))).isTrue();
    }

    @Test
    public void treeDoesNotContainPoint_insertPoint_treeShouldContainPoint(){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.0, 0.0));
        assertThat(tree.contains(new Point2D(0.5, 0.5))).isFalse();
        tree.insert(new Point2D(0.5, 0.5));
        assertThat(tree.contains(new Point2D(0.5, 0.5))).isTrue();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for range()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void rangeOnNullPoint_shouldThrowException(){
        KdTree tree = new KdTree();
        tree.range(null);
    }

    @Test
    public void emptytree_rangeShouldBeEmpty(){
        KdTree tree = new KdTree();
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        assertThat(tree.range(rect)).isEmpty();
    }

    @Test
    public void noPointsInRange_rangeShouldBeEmpty(){
        KdTree tree = new KdTree();
        RectHV rect = new RectHV(0.5, 0.5, 0.6, 0.6);
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.2, 0.2));
        tree.insert(new Point2D(0.3, 0.3));
        tree.insert(new Point2D(0.4, 0.4));
        assertThat(tree.range(rect)).isEmpty();
    }

    @Test
    public void rectIsUnitSquare_rangeShouldContainEveryPoint(){
        KdTree tree = new KdTree();
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.2, 0.2));
        tree.insert(new Point2D(0.3, 0.3));
        tree.insert(new Point2D(0.4, 0.4));
        tree.insert(new Point2D(0.5, 0.5));
        assertThat(tree.range(rect)).containsExactly(
                new Point2D(0.1, 0.1),
                new Point2D(0.2, 0.2),
                new Point2D(0.3, 0.3),
                new Point2D(0.4, 0.4),
                new Point2D(0.5, 0.5));
    }

    @Test
    public void rangeShouldContainPointsOnBoundary(){
        KdTree tree = new KdTree();
        RectHV rect = new RectHV(0.0, 0.0, 0.7, 0.4);
        tree.insert(new Point2D(0.3, 0.0));
        assertThat(tree.range(rect)).containsExactly(
                new Point2D(0.3, 0.0));
    }

    @Test
    public void rangeShouldContainPointsInsideRectangle(){
        KdTree tree = new KdTree();
        RectHV rect = new RectHV(0.0, 0.0, 0.7, 0.7);
        tree.insert(new Point2D(0.3, 0.4));
        assertThat(tree.range(rect)).containsExactly(
                new Point2D(0.3, 0.4));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for nearest()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void nearestOnNullPoint_shouldThrowException(){
        KdTree tree = new KdTree();
        tree.nearest(null);
    }

    @Test
    public void emptytree_nearestShouldBeNull(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.1, 0.2);
        assertThat(tree.nearest(point)).isNull();
    }

    @Test
    public void nearestNeighborOfPoint_shouldBePointItself(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.1, 0.2);
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.1, 0.2));
        tree.insert(new Point2D(1.0, 1.0));
        assertThat(tree.nearest(point)).isEqualTo(point);
    }

    @Test
    public void nearestNeighborOftreeWithOnePoint_shouldBeTheOnlyPoint(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(1.0, 1.0);
        tree.insert(new Point2D(0.0, 0.0));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.0, 0.0));
    }

    @Test
    public void allPointsOnSameVerticalLine_closestShouldBeTheNeighbor(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.5, 0.5);
        tree.insert(new Point2D(0.6, 0.0));
        tree.insert(new Point2D(0.6, 0.4));
        tree.insert(new Point2D(0.6, 0.5));
        tree.insert(new Point2D(0.6, 0.6));
        tree.insert(new Point2D(0.6, 1));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.6, 0.5));
    }

    @Test
    public void allPointsOnSameHorizontalLine_closestShouldBeTheNeighbor(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.5, 0.5);
        tree.insert(new Point2D(0.0, 0.8));
        tree.insert(new Point2D(0.4, 0.8));
        tree.insert(new Point2D(0.5, 0.8));
        tree.insert(new Point2D(0.6, 0.8));
        tree.insert(new Point2D(1.0, 0.8));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.5, 0.8));
    }

    @Test
    public void twoPointsEquidistantFromQueryPoint_shouldBreakTiesByNaturalOrder(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.5, 0.5);
        tree.insert(new Point2D(0.6, 0.6));
        tree.insert(new Point2D(0.4, 0.4));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.4, 0.4));
    }

    @Test
    public void twoPointsEquidistantFromQueryPoint_doesNotBreakTiesByDistanceToOrigin(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.5, 0.5);
        tree.insert(new Point2D(0.6, 0.4));
        tree.insert(new Point2D(0.4, 0.6));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.6, 0.4));
    }

    @Test
    public void nearestPointOnOtherSideOfSplitLine_shouldReturnCorrectPoint(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.7, 0.7);
        tree.insert(new Point2D(0.6, 0.0));
        tree.insert(new Point2D(0.61, 0.01));
        tree.insert(new Point2D(0.59, 0.7));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.59, 0.7));
    }

    @Test
    public void nearestPointOnSameSideOfSplitLine_shouldReturnCorrectPoint(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.7, 0.7);
        tree.insert(new Point2D(0.6, 0.0));
        tree.insert(new Point2D(0.69, 0.69));
        tree.insert(new Point2D(0.3, 0.4));
        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.69, 0.69));
    }

    @Test
    public void pointsClusteredInCorners_shouldReturnCorrectNearest(){
        KdTree tree = new KdTree();
        Point2D point = new Point2D(0.75, 0.75);

        // Lower left
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.2, 0.2));

        // Upper left
        tree.insert(new Point2D(0.0, 1));
        tree.insert(new Point2D(0.1, 0.9));

        // Lower Right
        tree.insert(new Point2D(0.9, 0.01));
        tree.insert(new Point2D(0.8, 0.3));

        // Upper Right
        tree.insert(new Point2D(0.7, 0.7));
        tree.insert(new Point2D(0.71, 0.71));
        tree.insert(new Point2D(0.88, 0.88));

        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.71, 0.71));
    }
}

