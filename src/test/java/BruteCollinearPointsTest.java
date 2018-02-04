import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/******************************************************************************
 *  Compilation:  javac BruteCollinearPointsTest.java
 *  Execution:    java BruteCollinearPointsTest
 *  Dependencies: BruteCollinearPoints.java
 *
 *  Unit tests for BruteCollinearPoints
 ******************************************************************************/
public class BruteCollinearPointsTest {

    @Test(expected = IllegalArgumentException.class)
    public void inputIsNull_throwsIllegalArgumentException(){
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputHasNullElements_throwsIllegalArgumentException(){
        Point[] points = {new Point(1, 1), null, new Point(1, 2)};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
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

        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
    }

    @Test
    public void inputIsEmpty_hasNoCollinearPoints(){
        Point[] points = {};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(0);
    }

    @Test
    public void inputHasLessThanFourElements_hasNoCollinearPoints(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(0);
    }

    @Test
    public void inputHasNoCollinearPoints_hasNoCollinearPoints(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 0)};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(0);
    }

    @Test
    public void inputHasFourPointsAndIsCollinear_hasOneCollinearSegment(){
        Point[] points = {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
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
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
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
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
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
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
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
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
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
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        assertThat(collinearPoints.numberOfSegments()).isEqualTo(1);
        assertThat(collinearPoints.segments()).asList().containsExactly(
                new LineSegment(new Point(1, 1), new Point(4,4))
        ).inOrder();
    }
}
