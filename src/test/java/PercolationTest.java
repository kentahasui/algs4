/******************************************************************************
 *  Compilation:  javac PercolationTest
 *  Execution: tbd
 *  Dependencies: Percolation.java org.junit.*
 *
 *  JUnit4 unit tests for Percolation.java
 ******************************************************************************/

import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {
    private Percolation percolation;

    @Test
    public void oneByOneGridShouldNotPercolate() {
        percolation = new Percolation(1);
        assertFalse(percolation.percolates());
    }

    @Test
    public void oneByOneGrid_openOnlySite_shouldPercolate() {
        percolation = new Percolation(1);
        percolation.open(1, 1);
        assertTrue("", percolation.percolates());
    }

    @Test
    public void oneByOneGrid_callIsFull_shouldReturnFalse() {
        percolation = new Percolation(1);
        assertFalse(percolation.isFull(1, 1));
    }

    @Test
    public void oneByOneGrid_callPercolates_shouldReturnFalse() {
        percolation = new Percolation(1);
        assertFalse(percolation.percolates());
    }

    @Test
    public void oneByOneGrid_openOnlySite_callIsFull_shouldReturnTrue() {
        percolation = new Percolation(1);
        percolation.open(1, 1);
        assertTrue("", percolation.isFull(1, 1));
    }

    @Test
    public void twoByTwo_openNoSites_doesNotPercolate() {
        percolation = new Percolation(2);
        assertEquals(0, percolation.numberOfOpenSites());
        assertFalse(percolation.percolates());
    }

    @Test
    public void twoByTwo_openOnlyTopRow_topRowShouldBecomeFull_shouldNotPercolate() {
        percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(1, 2);
        assertEquals(2, percolation.numberOfOpenSites());
        assertTrue("", percolation.isFull(1, 1));
        assertTrue("", percolation.isFull(1, 2));
        assertFalse(percolation.percolates());
    }

    @Test
    public void twoByTwo_openOnlyBottomRow_bottomRowShouldStayEmpty_doesNotPercolate() {
        percolation = new Percolation(2);
        percolation.open(2, 1);
        percolation.open(2, 2);
        assertEquals(2, percolation.numberOfOpenSites());
        assertFalse(percolation.isFull(1, 1));
        assertFalse(percolation.isFull(1, 2));
        assertFalse(percolation.percolates());
    }


    @Test
    public void twoByTwo_openTopLeftSite_openBottomRightSite_shouldNotPercolate() {
        percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(2, 2);
        assertEquals(2, percolation.numberOfOpenSites());
        assertTrue("", percolation.isFull(1, 1));
        assertFalse(percolation.isFull(2, 2));
        assertFalse(percolation.percolates());
    }

    @Test
    public void twoByTwo_openTopLeftSite_openBottomLeftSite_shouldPercolate() {
        percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(2, 1);
        assertEquals(2, percolation.numberOfOpenSites());
        assertTrue("", percolation.isFull(1, 1));
        assertTrue("", percolation.isFull(2, 1));
        assertTrue("", percolation.percolates());
    }

    @Test
    public void threeByThree_openOnlyMiddleRow_shouldNotPercolate() {
        percolation = new Percolation(3);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        assertFalse(percolation.percolates());
    }

    @Test
    public void threeByThree_openMiddleColumn_shouldPercolate() {
        percolation = new Percolation(3);
        percolation.open(1, 2);
        percolation.open(2, 2);
        assertFalse(percolation.percolates());
        percolation.open(3, 2);
        assertTrue("", percolation.percolates());
    }

    @Test
    public void backwashTest() {
        percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(3, 3);

        assertFalse(percolation.isFull(3, 3));
        assertTrue("", percolation.percolates());
    }

    @Test
    public void initializePercolation_immediatelyCallGetNumberOfOpenSites_shouldReturnZero() {
        percolation = new Percolation(10);
        assertEquals(0, percolation.numberOfOpenSites());
    }

    @Test(expected = IllegalArgumentException.class)
    public void passInNegativeValuesToConstructor_throwsIllegalArgumentException() {
        percolation = new Percolation(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void openRowGreaterThanSideLength_throwsIllegalArgumentException() {
        percolation = new Percolation(3);
        percolation.open(4, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void openColGreaterThanSideLength_throwsIllegalArgumentException() {
        percolation = new Percolation(3);
        percolation.open(1, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void openRowZero_throwsIllegalArgumentException() {
        percolation = new Percolation(3);
        percolation.open(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void openColZero_throwsIllegalArgumentException() {
        percolation = new Percolation(3);
        percolation.open(1, 0);
    }
}