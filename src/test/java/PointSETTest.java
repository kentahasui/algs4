/******************************************************************************
 *  Compilation:  javac PointSETTest.java
 *  Execution:    java PointSETTest
 *  Dependencies: PointSET.java
 *
 *  Unit test for PointSET
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PointSETTest {
    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for isEmpty()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void setHasNoPoints_shouldBeEmpty(){
        PointSET set = new PointSET();
        assertThat(set.isEmpty()).isTrue();
    }

    @Test
    public void setHasOnePoint_shouldNotBeEmpty(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.1));
        assertThat(set.isEmpty()).isFalse();
    }

    @Test
    public void setHasTwoPoints_shouldNotBeEmpty(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(0.1, 0.1));
        assertThat(set.isEmpty()).isFalse();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for size()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void setHasNoPoints_shouldHaveSizeZero(){
        PointSET set = new PointSET();
        assertThat(set.size()).isEqualTo(0);
    }

    @Test
    public void setHasOnePoint_shouldHaveSizeOne(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.1));
        assertThat(set.size()).isEqualTo(1);    }

    @Test
    public void setHasTwoPoints_shouldHasSizeTwo(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(0.1, 0.1));
        assertThat(set.size()).isEqualTo(2);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for insert()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void insertNullPoint_shouldThrowException(){
        PointSET set = new PointSET();
        set.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertPointNotInUnitSquare_shouldThrowException(){
        PointSET set = new PointSET();
        set.insert(new Point2D(2, 2));
    }

    @Test
    public void insertIntoEmptySet_addsNewPoint(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.2));
        assertThat(set.contains(new Point2D(0.1, 0.2))).isTrue();
    }

    @Test
    public void insertIntoSet_addsNewPoint(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.2));
        assertThat(set.contains(new Point2D(0.3, 0.4))).isFalse();
        set.insert(new Point2D(0.3, 0.4));
        assertThat(set.contains(new Point2D(0.3, 0.4))).isTrue();
    }

    @Test
    public void insertExistingPoint_doesNothing(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.1, 0.1));
        assertThat(set.size()).isEqualTo(1);
    }

    @Test
    public void insertPointWithSameXCoordAsExisting_addsNewPoint(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.2));
        set.insert(new Point2D(0.1, 0.9));
        assertThat(set.contains(new Point2D(0.1, 0.2))).isTrue();
        assertThat(set.contains(new Point2D(0.1, 0.9))).isTrue();
        assertThat(set.size()).isEqualTo(2);
    }

    @Test
    public void insertPointWithSameYCoordAsExisting_addsNewPoint(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.2, 0.1));
        set.insert(new Point2D(0.9, 0.1));
        assertThat(set.contains(new Point2D(0.2, 0.1))).isTrue();
        assertThat(set.contains(new Point2D(0.9, 0.1))).isTrue();
        assertThat(set.size()).isEqualTo(2);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for contains()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void containsOnNullPoint_shouldThrowException(){
        PointSET set = new PointSET();
        set.contains(null);
    }

    @Test
    public void emptySet_shouldNotContainAnyPoint(){
        PointSET set = new PointSET();
        assertThat(set.contains(new Point2D(0.1, 0.1))).isFalse();
    }

    @Test
    public void nonEmptySet_doesNotContainPoint_containsShouldBeFalse(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(1, 1));
        assertThat(set.contains(new Point2D(0.0, 1))).isFalse();
    }

    @Test
    public void nonEmptySet_containsPoint_containsShouldBeTrue(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(1, 1));
        assertThat(set.contains(new Point2D(0.0, 0.0))).isTrue();
    }

    @Test
    public void setDoesNotContainPoint_insertPoint_setShouldContainPoint(){
        PointSET set = new PointSET();
        set.insert(new Point2D(0.0, 0.0));
        assertThat(set.contains(new Point2D(0.5, 0.5))).isFalse();
        set.insert(new Point2D(0.5, 0.5));
        assertThat(set.contains(new Point2D(0.5, 0.5))).isTrue();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for range()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void rangeOnNullPoint_shouldThrowException(){
        PointSET set = new PointSET();
        set.range(null);
    }

    @Test
    public void emptySet_rangeShouldBeEmpty(){
        PointSET set = new PointSET();
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        assertThat(set.range(rect)).isEmpty();
    }

    @Test
    public void noPointsInRange_rangeShouldBeEmpty(){
        PointSET set = new PointSET();
        RectHV rect = new RectHV(0.5, 0.5, 0.6, 0.6);
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.2, 0.2));
        set.insert(new Point2D(0.3, 0.3));
        set.insert(new Point2D(0.4, 0.4));
        assertThat(set.range(rect)).isEmpty();
    }

    @Test
    public void rectIsUnitSquare_rangeShouldContainEveryPoint(){
        PointSET set = new PointSET();
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.2, 0.2));
        set.insert(new Point2D(0.3, 0.3));
        set.insert(new Point2D(0.4, 0.4));
        set.insert(new Point2D(0.5, 0.5));
        assertThat(set.range(rect)).containsExactly(
                new Point2D(0.1, 0.1),
                new Point2D(0.2, 0.2),
                new Point2D(0.3, 0.3),
                new Point2D(0.4, 0.4),
                new Point2D(0.5, 0.5));
    }

    @Test
    public void rangeShouldContainPointsOnBoundary(){
        PointSET set = new PointSET();
        RectHV rect = new RectHV(0.0, 0.0, 0.7, 0.4);
        set.insert(new Point2D(0.3, 0.0));
        assertThat(set.range(rect)).containsExactly(
                new Point2D(0.3, 0.0));
    }

    @Test
    public void rangeShouldContainPointsInsideRectangle(){
        PointSET set = new PointSET();
        RectHV rect = new RectHV(0.0, 0.0, 0.7, 0.7);
        set.insert(new Point2D(0.3, 0.4));
        assertThat(set.range(rect)).containsExactly(
                new Point2D(0.3, 0.4));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for nearest()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void nearestOnNullPoint_shouldThrowException(){
        PointSET set = new PointSET();
        set.nearest(null);
    }

    @Test
    public void emptySet_nearestShouldBeNull(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.1, 0.2);
        assertThat(set.nearest(point)).isNull();
    }

    @Test
    public void nearestNeighborOfPoint_shouldBePointItself(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.1, 0.2);
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(0.1, 0.2));
        set.insert(new Point2D(1.0, 1.0));
        assertThat(set.nearest(point)).isEqualTo(point);
    }

    @Test
    public void nearestNeighborOfSetWithOnePoint_shouldBeTheOnlyPoint(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(1.0, 1.0);
        set.insert(new Point2D(0.0, 0.0));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.0, 0.0));
    }

    @Test
    public void allPointsOnSameVerticalLine_closestShouldBeTheNeighbor(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.5, 0.5);
        set.insert(new Point2D(0.6, 0.0));
        set.insert(new Point2D(0.6, 0.4));
        set.insert(new Point2D(0.6, 0.5));
        set.insert(new Point2D(0.6, 0.6));
        set.insert(new Point2D(0.6, 1));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.6, 0.5));
    }

    @Test
    public void allPointsOnSameHorizontalLine_closestShouldBeTheNeighbor(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.5, 0.5);
        set.insert(new Point2D(0.0, 0.8));
        set.insert(new Point2D(0.4, 0.8));
        set.insert(new Point2D(0.5, 0.8));
        set.insert(new Point2D(0.6, 0.8));
        set.insert(new Point2D(1.0, 0.8));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.5, 0.8));
    }

    @Test
    public void twoPointsEquidistantFromQueryPoint_shouldBreakTiesByNaturalOrder(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.5, 0.5);
        set.insert(new Point2D(0.6, 0.6));
        set.insert(new Point2D(0.4, 0.4));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.4, 0.4));
    }

    @Test
    public void twoPointsEquidistantFromQueryPoint_doesNotBreakTiesByDistanceToOrigin(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.5, 0.5);
        set.insert(new Point2D(0.6, 0.4));
        set.insert(new Point2D(0.4, 0.6));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.6, 0.4));
    }

    @Test
    public void nearestPointOnOtherSideOfSplitLine_shouldReturnCorrectPoint(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.7, 0.7);
        set.insert(new Point2D(0.6, 0.0));
        set.insert(new Point2D(0.61, 0.01));
        set.insert(new Point2D(0.59, 0.7));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.59, 0.7));
    }

    @Test
    public void nearestPointOnSameSideOfSplitLine_shouldReturnCorrectPoint(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.7, 0.7);
        set.insert(new Point2D(0.6, 0.0));
        set.insert(new Point2D(0.69, 0.69));
        set.insert(new Point2D(0.3, 0.4));
        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.69, 0.69));
    }

    @Test
    public void pointsClusteredInCorners_shouldReturnCorrectNearest(){
        PointSET set = new PointSET();
        Point2D point = new Point2D(0.75, 0.75);

        // Lower left
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.2, 0.2));

        // Upper left
        set.insert(new Point2D(0.0, 1));
        set.insert(new Point2D(0.1, 0.9));

        // Lower Right
        set.insert(new Point2D(0.9, 0.01));
        set.insert(new Point2D(0.8, 0.3));

        // Upper Right
        set.insert(new Point2D(0.7, 0.7));
        set.insert(new Point2D(0.71, 0.71));
        set.insert(new Point2D(0.88, 0.88));

        assertThat(set.nearest(point)).isEqualTo(new Point2D(0.71, 0.71));
    }

}
