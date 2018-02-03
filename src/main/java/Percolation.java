/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java Percolation.java
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  This file contains the Percolation class, which you can use to run
 *  Monte-Carlo simulations
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Union-Find hidden nodes, used to optimize union-find performance
    private final int topHiddenNode;
    private final int bottomHiddenNode;

    // Running total of number of open sites
    private int numberOfOpenSites;
    // Length of each side in the grid
    private final int sideLength;
    // Open/Closed grid. A site (x, y) is open if grid[x-1][y-1] == true.
    private final boolean[][] grid;

    // UnionFind object to check if the grid percolates
    private final WeightedQuickUnionUF percolationUnionFind;

    // UnionFInd object to check if the grid is full
    private final WeightedQuickUnionUF fullnessUnionFind;

    /**
     * Construct a new Percolation instance, which represents an n-by-n grid .
     *
     * @param sideLength: The side length of the grid.
     * @throws IllegalArgumentException if the sideLength <= 0.
     */
    public Percolation(int sideLength) {
        validateConstructor(sideLength);

        // Initialize instance variables
        numberOfOpenSites = 0;
        this.sideLength = sideLength;

        // Initialize the grid. Since this is a boolean[][],
        // all entries should be initialized to false.
        // Initialization will take O(n^2) time.
        grid = new boolean[sideLength][sideLength];

        // Initialize the UnionFind data structure that determines whether a given node is empty or full
        int numberOfSites = sideLength * sideLength;
        topHiddenNode = numberOfSites;
        bottomHiddenNode = numberOfSites + 1;
        percolationUnionFind = new WeightedQuickUnionUF(numberOfSites + 2);
        fullnessUnionFind = new WeightedQuickUnionUF(numberOfSites + 1);

        // Union all sites on top row with TOP_HIDDEN_NODE
        // Union all sites on bottom row with BOTTOM_HIDDEN_NODE
        int topRow = 1;
        int bottomRow = sideLength;
        for (int col = 1; col <= sideLength; col++) {
            fullnessUnionFind.union(topHiddenNode, convertSiteToUnionFindNode(topRow, col));
            percolationUnionFind.union(topHiddenNode, convertSiteToUnionFindNode(topRow, col));
            percolationUnionFind.union(bottomHiddenNode, convertSiteToUnionFindNode(bottomRow, col));
        }
    }

    /**
     * Open site at the given 1-based row and column.
     * Connects site to all surrounding sites (top, bottom, left, right).
     * If any of the surrounding sites are full (top, bottom, left, right), then this site will become full as well.
     *
     * @param row 1-based row number.
     * @param col 1-based column number.
     * @throws IllegalArgumentException if either row or column are less than 1 or greater than sideLength.
     */
    public void open(int row, int col) {
        // Check inputs
        validateArguments(row, col);

        // If the site is already open, do nothing
        if (isOpen(row, col)) {
            return;
        }

        // Open the given site
        numberOfOpenSites++;
        grid[row - 1][col - 1] = true;

        // Connect the site with its neighbors (we can perhaps refactor this method for readability).
        connectSiteToNeighbors(row, col);
    }

    private void connectSiteToNeighbors(int row, int col) {
        int sourceNode = convertSiteToUnionFindNode(row, col);

        // Connect to each neighbor (left, right, top, bottom)
        for (Neighbor neighbor : Neighbor.values()) {
            // Row and column for this particular neighbor
            int neighborRow = neighbor.getRow(row);
            int neighborCol = neighbor.getCol(col);

            // Guard clauses: If the source node is at an edge or the neighbor is closed, do not connect
            if (!neighbor.isValidSite(sideLength, row, col)) {
                continue;
            }
            if (!isOpen(neighborRow, neighborCol)) {
                continue;
            }

            // No need to check if the source node is already connected to its neighbors, as WeightedQuickUF#union()
            // already does it for us.
            int neighborNode = convertSiteToUnionFindNode(neighborRow, neighborCol);
            fullnessUnionFind.union(sourceNode, neighborNode);
            percolationUnionFind.union(sourceNode, neighborNode);
        }
    }

    /**
     * Determines whether a given site is open or closed.
     *
     * @param row 1-based row number (Top row is 1, bottom row is sideLength)
     * @param col 1-based column number (Leftmost column is 1, rightmost column is sideLength)
     * @return True if the given site is open.
     */
    public boolean isOpen(int row, int col) {
        validateArguments(row, col);
        return grid[row - 1][col - 1];
    }

    /**
     * Determines whether a given site on the grid is full.
     *
     * @return True iff the given site is open AND is connected to any site on the top row of the grid.
     */
    public boolean isFull(int row, int col) {
        validateArguments(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return fullnessUnionFind.connected(topHiddenNode, convertSiteToUnionFindNode(row, col));
    }

    /**
     * Return the total number of open sites.
     *
     * @return The count of open sites.
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * Determines whether the given system percolates or not.
     *
     * @return True if any of the sites on the bottom row are connected to any of the sites on the top row.
     */
    public boolean percolates() {
        // Deal with wonky situation when we have a 1x1 grid
        if (sideLength == 1) {
            return isOpen(1, 1);
        }

        return percolationUnionFind.connected(topHiddenNode, bottomHiddenNode);
    }

    /**
     * Helper method to convert a site in the grid (represented by a row and column) to a node in Union-Find
     * (represented by a single number).
     * <p>
     * The top-left site in the grid will be node 0.
     * The bottom-right site in the grid will be node sideLength^2 - 1.
     * <p>
     * Example (for a 3x3 grid):
     * convertSiteToUnionFindNode(1, 1) -> 0
     * convertSiteToUnionFindNode(2, 1) -> 3
     * convertSiteToUnionFindNode(2, 3) -> 5
     *
     * @param row 1-based row number (Top row is 1, bottom row is sideLength)
     * @param col 1-based column number (Leftmost column is 1, rightmost column is sideLength)
     * @return The 0=based node number in the UnionFind data structure.
     */
    private int convertSiteToUnionFindNode(int row, int col) {
        validateArguments(row, col);
        int zeroIndexedRow = row - 1;
        int zeroIndexedCol = col - 1;
        return (sideLength * zeroIndexedRow) + zeroIndexedCol;
    }

    /**
     * Helper method to validate constructor arguments.
     *
     * @param n The length of each side in the grid.
     * @throws IllegalArgumentException if sideLength is less than 0.
     */
    private void validateConstructor(int n) {
        if (n <= 0) {
            String msg = "Invalid side length %s. Please enter a value > 0.";
            throw new IllegalArgumentException(String.format(msg, n));
        }
    }

    /**
     * Helper method to validate site coordinates.
     *
     * @param row A row number
     * @param col A column number
     * @throws IllegalArgumentException if row or col is 0, or if row or col is greater than sideLength.
     */
    private void validateArguments(int row, int col) {
        if (row <= 0 || col <= 0 || row > sideLength || col > sideLength) {
            String errorMessage = "Site (%s, %s) is not a valid site. " +
                    "Row and col must each be greater than 0 and less than or equal to %s.";
            throw new IllegalArgumentException(String.format(errorMessage, row, col, sideLength));
        }
    }

    /**
     * Helper class to calculate a site's neighbors.
     * Each site has 4 possible neighbors: Top, Bottom, Left, and Right.
     * A given site can have 0-4 neighbors, depending on the size of the grid and its location within the grid.
     * You can check if a site has a valid neighbor by calling:
     * <code>
     * int sideLength = 10;
     * Neighbor.TOP.isValidSite(sideLength, 1, 1); // --> Should return false, because this site is on the top row.
     * Neighbor.RIGHT.isValidSite(sideLength, 1, 1); // -> Should return true, because this site has a rhs neighbor.
     * </code>
     */
    private enum Neighbor {
        TOP {
            @Override
            public boolean isValidSite(int n, int sourceRow, int sourceCol) {
                return sourceRow > 1; // Sites on the top row have no top neighbors
            }

            @Override
            public int getRow(int sourceRow) {
                // Return the site on the row above
                return sourceRow - 1;
            }

            @Override
            public int getCol(int sourceCol) {
                // column doesn't change
                return sourceCol;
            }
        },
        BOTTOM {
            @Override
            public boolean isValidSite(int n, int sourceRow, int sourceCol) {
                // Sites already on the bottom row have no bottom neighbors
                return sourceRow < n;
            }

            @Override
            public int getRow(int sourceRow) {
                // Return the row below
                return sourceRow + 1;
            }

            @Override
            public int getCol(int sourceCol) {
                // Column doesn't change
                return sourceCol;
            }
        },
        LEFT {
            @Override
            public boolean isValidSite(int n, int sourceRow, int sourceCol) {
                // Sites already on the leftmost column have no neighbors to the left
                return sourceCol > 1;
            }

            @Override
            public int getRow(int sourceRow) {
                // Row does not change
                return sourceRow;
            }

            @Override
            public int getCol(int sourceCol) {
                // Return one column to the left
                return sourceCol - 1;
            }
        },

        RIGHT {
            @Override
            public boolean isValidSite(int n, int sourceRow, int sourceCol) {
                // Sites already on the rightmost column have no neighbors to the right
                return sourceCol < n;
            }

            @Override
            public int getRow(int sourceRow) {
                // Row does not change
                return sourceRow;
            }

            @Override
            public int getCol(int sourceCol) {
                // Return one column to the right
                return sourceCol + 1;
            }
        };

        /**
         * Given a source site (row and column), determines if this is a valid neighbor.
         * If a site is on the topmost row, it has no neighbor to the top.
         * If a site is on the bottom-most row, it has no neighbor to bottom.
         * If a site is on the leftmost row, it has no neighbor to the left.
         * If a site is on the rightmost row, it has no neighbor to the right.
         * <p>
         * To get valid data from this class, the following invariants must hold:
         * <ul>
         * <li>sideLength > 0</li>
         * <li>0 < row <= sideLength</li>
         * <li>0 < col <= sideLength</li>
         * </ul>
         * <p>
         * If you are using this class as part of the Percolation class, you should call Percolation#validateArguments()
         * before calling any of these methods.
         *
         * @param n The side length for the Percolation grid (nxn grid).
         * @param sourceRow  The 1-based row number of the source site.
         * @param sourceCol  The 1-based column number of the destination site.
         * @return True if this is a valid neighbor.
         */
        public boolean isValidSite(int n, int sourceRow, int sourceCol) {
            return false;
        }

        /**
         * Returns the row for this neighbor.
         * Does no bounds checking, so all callers much check if this a valid site by first calling isValidSite() for a
         * given row, column, and grid size.
         *
         * @param sourceRow The 1-indexed row number representing the source site's row.
         * @return The 1-indexed row number representing the neighbor site's row.
         */
        public int getRow(int sourceRow) { return -1; }

        /**
         * Returns the column for this neighbor.
         * Does no bounds checking, so all callers much check if this a valid site by first calling isValidSite() for a
         * given row, column, and grid size.
         *
         * @param sourceCol The 1-indexed column number representing the source site's column.
         * @return The 1-indexed column number representing the neighbor site's column.
         */
        public int getCol(int sourceCol) { return -1; }
    }
}
