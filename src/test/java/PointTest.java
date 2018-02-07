/******************************************************************************
 *  Compilation:  javac PointTest.java
 *  Execution:    java PointTest
 *  Dependencies: Point.java
 *
 *  Unit tests for Point.java
 *
 ******************************************************************************/
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;

public class PointTest {
    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for slopeTo()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void slopeToSamePoint_shouldBeNegativeInfinity(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        assertThat(p1.slopeTo(p2)).isNegativeInfinity();
    }

    @Test
    public void verticalSlope_shouldBePositiveInfinity(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 5);
        assertThat(p1.slopeTo(p2)).isPositiveInfinity();
    }

    @Test
    public void horizontalSlope_shouldBeZero(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(10, 1);
        assertThat(p1.slopeTo(p2)).isZero();
    }

    @Test
    public void positiveSlope(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 10);
        assertThat(p1.slopeTo(p2)).isEqualTo(9.0);
    }

    @Test
    public void negativeSlope(){
        Point p1 = new Point(0, 100);
        Point p2 = new Point(10, 0);
        assertThat(p1.slopeTo(p2)).isEqualTo(-10.0);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for compareTo()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void compareTo_samePoint_shouldBeZero(){
        Point p1 = new Point(1, 100);
        Point p2 = new Point(1, 100);
        assertThat(p1).isEquivalentAccordingToCompareTo(p2);
    }

    @Test
    public void compareTo_vertical_smallerYCoordinate_shouldBeLess(){
        Point p1 = new Point(1, 10);
        Point p2 = new Point(1, 100);
        assertThat(p1).isLessThan(p2);
    }

    @Test
    public void compareTo_vertical_largerYCoordinate_shouldBeGreater(){
        Point p1 = new Point(1, 100);
        Point p2 = new Point(1, 1);
        assertThat(p1).isGreaterThan(p2);
    }

    @Test
    public void compareTo_horizontal_smallerXCoordinate_shouldBeLess(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(20, 1);
        assertThat(p1).isLessThan(p2);
    }

    @Test
    public void compareTo_horizontal_largerXCoordinate_shouldBeGreater(){
        Point p1 = new Point(30, 1);
        Point p2 = new Point(29, 1);
        assertThat(p1).isGreaterThan(p2);
    }

    @Test
    public void compareTo_bothCoordinatesSmaller_shouldBeLess(){
        Point p1 = new Point(20, 25);
        Point p2 = new Point(120, 125);
        assertThat(p1).isLessThan(p2);
    }

    @Test
    public void compareTo_bothCoordinatesLarger_shouldBeLarger(){
        Point p1 = new Point(120, 125);
        Point p2 = new Point(20, 25);
        assertThat(p1).isGreaterThan(p2);
    }

    @Test
    public void compareTo_shouldBeTransitive(){
        Point p1 = new Point(1, 2);
        Point p2 = new Point(10, 20);
        Point p3 = new Point(100, 200);

        assertThat(p1).isLessThan(p2);
        assertThat(p2).isLessThan(p3);
        assertThat(p1).isLessThan(p3);
    }

    @Test
    public void compareTo_shouldBeAntiSymmetric(){
        Point p1 = new Point(1, 2);
        Point p2 = new Point(10, 20);
        assertThat(p1).isLessThan(p2);
        assertThat(p2).isGreaterThan(p1);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for slopeOrder()
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////
    // Pairwise comparison
    ///////////////////////////////////
    @Test
    public void slopeOrder_comparisonPointsAreSameAsSourcePoints_shouldBeEquivalent(){
        Point source = new Point(1, 1);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        Comparator<Point> comparator = source.slopeOrder();
        assertThat(comparator.compare(p1, p2)).isEqualTo(0);
    }

    @Test
    public void slopeOrder_comparisonPointsAreEqualToEachOtherButDiffersFromSource_shouldBeEquivalent(){
        Point source = new Point(1, 1);
        Point p1 = new Point(2, 2);
        Point p2 = new Point(2, 2);
        Comparator<Point> comparator = source.slopeOrder();
        assertThat(comparator.compare(p1, p2)).isEqualTo(0);
    }

    @Test
    public void slopeOrder_point1IsVerticalToSource_pointTwoIsHorizontalToSource_pointWithHorizontalSlopeIsCloser(){
        Point source = new Point(1, 1);
        Point p1 = new Point(1, 20);
        Point p2 = new Point(20, 1);
        assertThat(source.slopeOrder().compare(p1, p2)).isEqualTo(1);
    }

    @Test
    public void slopeOrder_slopeToOnePointSmallerThanSlopeToAnother_pointWithSmallerSlopeIsCloser(){
        Point source = new Point(1, 1);
        Point p1 = new Point(2, 20);
        Point p2 = new Point(3, 3000);
        assertThat(source.slopeOrder().compare(p1, p2)).isEqualTo(-1);
    }

    @Test
    public void slopeOrder_slopeToOnePointLargerThanSlopeToAnother_pointWithLargerSlopeIsFarther(){
        Point source = new Point(1, 1);
        Point p1 = new Point(2, 200);
        Point p2 = new Point(3, 4);
        assertThat(source.slopeOrder().compare(p1, p2)).isEqualTo(1);
    }

    @Test
    public void slopeOrder_allPointsOnHorizontalLine_pointsAreEqual(){
        Point source = new Point(1, 1);
        Point p1 = new Point(2, 1);
        Point p2 = new Point(3, 1);
        assertThat(source.slopeOrder().compare(p1, p2)).isEqualTo(0);
    }

    @Test
    public void slopeOrder_allPointsOnVerticalLine_pointsAreEqual(){
        Point source = new Point(1, 1);
        Point p1 = new Point(1, 500);
        Point p2 = new Point(1, 700);
        assertThat(source.slopeOrder().compare(p1, p2)).isEqualTo(0);
    }

    ///////////////////////////////////
    // Array-level comparison
    ///////////////////////////////////
    @Test
    public void sortArrayBySlopeOrder_allPointsInArrayAreCollinear_undefinedBehavior(){
        Point source = new Point(0, 0);
        Point p1 = new Point(10, 10);
        Point p2 = new Point(9, 9);
        Point p3 = new Point(8, 8);
        Point p4 = new Point(7, 7);
        Point[] points = {p1, p2, p3, p4};
        Arrays.sort(points, source.slopeOrder());

        assertThat(points).asList().containsExactly(p1, p2, p3, p4).inOrder();
    }

    @Test
    public void sortArrayBySlopeOrder_noPointsAreCollinear_sortBySlope(){
        Point source = new Point(0, 0);
        Point p1 = new Point(2, 5);
        Point p2 = new Point(3, 100);
        Point p3 = new Point(4, 3);
        Point p4 = new Point(3, 9);
        Point[] points = {p1, p2, p3, p4};
        Arrays.sort(points, source.slopeOrder());

        assertThat(points).asList().containsExactly(p3, p1, p4, p2).inOrder();
    }

    @Test
    public void sortArrayBySlopeOrder_sortByDifferentSources_createsDifferentSorting(){
        Point source1 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);
        Point p3 = new Point(3, 1);
        Point p4 = new Point(4, 1);
        Point[] points = {p1, p2, p3, p4};
        Arrays.sort(points, source1.slopeOrder());
        assertThat(points).asList().containsExactly(p4, p3, p2, p1).inOrder();

        Point source2 = new Point(5, 2);
        Arrays.sort(points, source2.slopeOrder());
        assertThat(points).asList().containsExactly(p1, p2, p3, p4).inOrder();
    }
}
