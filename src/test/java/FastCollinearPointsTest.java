/******************************************************************************
 *  Compilation:  javac FastCollinearPointsTest.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: FastCollinearPointsTest.java
 *
 *  Unit tests for FastCollinearPoints
 ******************************************************************************/
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class FastCollinearPointsTest {

    @Test(expected = IllegalArgumentException.class)
    public void inputIsNull_throwsIllegalArgumentException(){
        FastCollinearPoints collinearPoints = new FastCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputHasNullElements_throwsIllegalArgumentException(){
        Point[] points = {new Point(1, 1), null, new Point(1, 2)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputHasDegeneratePoints_throwsIllegalArgumentException(){
        Point[] points = {
                new Point(1, 1),
                new Point(1, 2),
                new Point(100, 300),
                new Point(0, 0),
                new Point(1, 2),
                new Point(1, 3)};

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
    }

    @Test
    public void inputIsEmpty_hasNoCollinearPoints(){
        Point[] points = {};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(0);
    }

    @Test
    public void inputHasLessThanFourElements_hasNoCollinearPoints(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(0);
    }

    @Test
    public void inputHasNoCollinearPoints_hasNoCollinearPoints(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 0)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(0);
    }

    @Test
    public void inputHasFourPointsAndIsCollinear_hasOneCollinearSegment(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(1);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(1, 1), new Point(4, 4))
        ).inOrder();
    }

    @Test
    public void inputHasTwoCollinearSegments_returnsTwoCollinearSegments(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(10, 20),
                new Point(11, 22),
                new Point(12, 24),
                new Point(13, 26)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(2);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(1, 1), new Point(4,4)),
                new LineSegment(new Point(10, 20), new Point(13, 26))
        ).inOrder();
    }

    @Test
    public void inputHasTwoCollinearSegmentsScattered_returnsTwoCollinearSegments(){
        Point[] points = {
                new Point(13, 26),
                new Point(4, 4),
                new Point(12, 24),
                new Point(3, 3),
                new Point(11, 22),
                new Point(2, 2),
                new Point(10, 20),
                new Point(1, 1),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(2);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(1, 1), new Point(4,4)),
                new LineSegment(new Point(10, 20), new Point(13, 26))
        ).inOrder();
    }

    @Test
    public void inputHasVerticalSlope_horizontalSlope_positiveSlope_negativeSlope_intersecting(){
        Point[] points = {

                // Middle of intersection
                new Point(3, 3),

                // Positive Slope
                new Point(1, 1),
                new Point(2, 2),
                new Point(4, 4),

                // Negative slope
                new Point(2, 4),
                new Point(4, 2),
                new Point(5, 1),

                // Horizontal
                new Point(1, 3),
                new Point(2, 3),
                new Point(4, 3),

                // Vertical
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 4)
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                // Positive slope
                new LineSegment(new Point(1, 1), new Point(4, 4)),
                // Vertical slope
                new LineSegment(new Point(3, 1), new Point(3, 4)),
                // Negative slope
                new LineSegment(new Point(5, 1), new Point(2, 4)),
                // Horizontal slope
                new LineSegment(new Point(1, 3), new Point(4, 3))
        );
    }

    @Test
    public void inputHasCollinearPointsAtEndOfArray(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 200),
                new Point(3, 77),
                new Point(4, 99),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(1, 1), new Point(4,4))
        ).inOrder();
    }

    @Test
    public void inputHasCollinearPointsInDifferentOrder_shouldCreateLineSegmentInOrder(){
        Point[] points = {
                new Point(3, 3),
                new Point(2, 200),
                new Point(3, 77),
                new Point(4, 99),
                new Point(4, 4),
                new Point(1, 1),
                new Point(2, 2)};
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(1);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(1, 1), new Point(4,4))
        ).inOrder();
    }

    @Test
    public void inputHasMoreThanFourCollinearPoints_shouldReturnAllPoints(){
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(5, 5),
                new Point(6, 6),
                new Point(7, 7),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(0, 0), new Point(7, 7))).inOrder();
    }

    @Test
    public void inputHasMaximumNumberOfCollinearSegmentsOfLengthFour_shouldCreateTenSegments(){
        // 4x4 grid of points
        Point[] points = {
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(1, 0),
                new Point(1, 1),
                new Point(1, 2),
                new Point(1, 3),
                new Point(2, 0),
                new Point(2, 1),
                new Point(2, 2),
                new Point(2, 3),
                new Point(3, 0),
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 3)};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);

        assertThat(collinearPoints.numberOfSegments()).isEqualTo(10);

        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(0, 0), new Point(3, 0)),
                new LineSegment(new Point(0, 0), new Point(0, 3)),
                new LineSegment(new Point(0, 0), new Point(3, 3)),
                new LineSegment(new Point(1, 0), new Point(1, 3)),
                new LineSegment(new Point(2, 0), new Point(2, 3)),
                new LineSegment(new Point(3, 0), new Point(0, 3)),
                new LineSegment(new Point(3, 0), new Point(3, 3)),
                new LineSegment(new Point(0, 1), new Point(3, 1)),
                new LineSegment(new Point(0, 2), new Point(3, 2)),
                new LineSegment(new Point(0, 3), new Point(3, 3)));
    }

        @Test
    public void input6(){
        Point[] points = {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234 , 5678),
                new Point(14000, 10000)};

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(14000, 10000), new Point(32000, 10000)));
    }
}
