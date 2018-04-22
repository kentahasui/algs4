/******************************************************************************
 *  Compilation:  javac SeamCarver.java
 *  Execution:    java SeamCarver
 *  Dependencies: Picture.java
 *
 *  SeamCarver implementation.
 *  Uses a topological sort based Shortest Paths algorithm.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SeamCarver {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private static final int INVALID = -1;
    private Picture picture;

    /**
     * Create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        validateNotNull(picture);

        // Create defensive copy
        this.picture = new Picture(picture);
    }

    /**
     * current picture
     */
    public Picture picture() {
        // Create defensive copy
        return new Picture(picture);
    }

    /**
     * Width of current pic
     */
    public int width() {
        return picture.width();
    }

    /**
     * Height of current pic
     */
    public int height() {
        return picture.height();
    }

    /**
     * Energy of pixel at column x and row y
     *
     * @param x row
     * @param y col
     * @return Energy of pixel or 1000 if at border of picture
     */
    public double energy(int x, int y) {
        validateCoordinates(x, y);
        if (x == 0 || x == width() - 1) return 1000;
        if (y == 0 || y == height() - 1) return 1000;
        return Math.sqrt(xGradientSquared(x, y) + yGradientSquared(x, y));
    }

    private int xGradientSquared(int x, int y) {
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        int redCentralDifference = right.getRed() - left.getRed();
        int greenCentralDifference = right.getGreen() - left.getGreen();
        int blueCentralDifference = right.getBlue() - left.getBlue();
        return (redCentralDifference * redCentralDifference) +
                (greenCentralDifference * greenCentralDifference) +
                (blueCentralDifference * blueCentralDifference);
    }

    private int yGradientSquared(int x, int y) {
        Color top = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);
        int redCentralDifference = bottom.getRed() - top.getRed();
        int greenCentralDifference = bottom.getGreen() - top.getGreen();
        int blueCentralDifference = bottom.getBlue() - top.getBlue();
        return (redCentralDifference * redCentralDifference) +
                (greenCentralDifference * greenCentralDifference) +
                (blueCentralDifference * blueCentralDifference);
    }

    /**
     * Sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        /* If all cells are on the border, return the first column (all 0s) */
        if (width() <= 2) {
            int[] seam = new int[height()];
            Arrays.fill(seam, 0);
            return seam;
        }

        return findVerticalSeamCore();
    }

    /**
     * Sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        /* If all cells are on the border, return the first row (all 0s) */
        if (height() <= 2) {
            int[] seam = new int[width()];
            Arrays.fill(seam, 0);
            return seam;
        }

        // Rotate picture -90 degrees
        transpose();

        // Find vertical seam, and convert to a horizontal seam
        int[] verticalSeam = findVerticalSeamCore();
        int[] horizontalSeam = transposeSeamBack(verticalSeam);

        // Rotate picture back to normal: 90 degrees
        transposeBack();

        return horizontalSeam;
    }

    /**
     * Remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {
        validateNotNull(seam);
        validateVerticalSeam(seam);
        validateSeamEntriesDifferByAtMostOne(seam);
        removeVerticalSeamCore(seam);
    }

    /**
     * Remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
        validateNotNull(seam);
        validateHorizontalSeam(seam);
        validateSeamEntriesDifferByAtMostOne(seam);

        // Convert horizontal seam to vertical seam (rotate -90 degrees)
        int[] vSeam = transposeSeam(seam);

        // Transpose picture and remove vertical seam
        transpose();
        removeVerticalSeamCore(vSeam);
        transposeBack();
    }

    private int[] findVerticalSeamCore() {
        /* Vertical seam must span the height of the picture */
        int[] seam = new int[height()];

        // Find shortest paths from top row: process vertices in topo sort order.
        double[][] energy = buildEnergy();
        double[][] distTo = buildDistTo();
        int[][] edgeTo = buildEdgeTo();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                relax(energy, distTo, edgeTo, x, y);
            }
        }

        // Find the vertex with smallest distance from top row
        int x = INVALID;
        double smallest = INFINITY;
        for (int currX = 0; currX < width(); currX++) {
            double currDist = distTo[currX][height() - 1]; // Check only bottom row
            if (currDist < smallest) {
                smallest = currDist;
                x = currX;
            }
        }

        // Traverse up edgeTo[][] tree from bottom row to top row, starting at closest vertex on bottom row
        int y = height() - 1;
        while (x != INVALID) {
            seam[y] = x;
            x = edgeTo[x][y];
            y--;
        }

        // Return the seam
        return seam;
    }

    /**
     * Each entry in vSeam is an x coordinate
     */
    private void removeVerticalSeamCore(int[] vSeam) {
        // Create a new picture, with one element removed
        Picture newPicture = new Picture(width() - 1, height());
        // Row-major order
        for (int y = 0; y < height(); y++) {
            int xToRemove = vSeam[y];
            // Copy over all entries in row that is less than x
            for (int x = 0; x < xToRemove; x++) {
                newPicture.set(x, y, picture.get(x, y));
            }
            // Copy over remaining entries greater than x, shifting over to the left by 1
            for (int x = xToRemove + 1; x < width(); x++) {
                newPicture.set(x - 1, y, picture.get(x, y));
            }
        }
        picture = newPicture;
    }

    private double[][] buildEnergy() {
        double[][] arr = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                arr[x][y] = energy(x, y);
            }
        }
        return arr;
    }

    private double[][] buildDistTo() {
        double[][] distTo = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                // Top row has distance of 0
                if (y == 0) distTo[x][y] = 0;
                    // Other rows are initialized to max possible value
                else distTo[x][y] = INFINITY;
            }
        }
        return distTo;
    }

    private int[][] buildEdgeTo() {
        int[][] distTo = new int[width()][height()];
        // Top row has no parents
        for (int x = 0; x < width(); x++) {
            distTo[x][0] = INVALID;
        }
        return distTo;
    }

    /**
     * Returns x coordinate of neighbors for vertical seam (neighbors on bottom row).
     *
     * @param x X coordinate of source
     * @param y Y coordinate of source
     * @return A collection of x-coordinates of the source's neighbors.
     * All neighbor y-coordinates will be y+1.
     */
    private Iterable<Integer> adj(int x, int y) {
        List<Integer> adj = new ArrayList<>();

        /* Already at bottom row: no neighbors */
        if (y == height() - 1) return adj;

        /* At leftmost column: no neighbor to bottom left */
        if (x == 0) {
            adj.add(x);
            adj.add(x + 1);
        }
        /* At rightmost column: no neighbor to bottom right */
        else if (x == (width() - 1)) {
            adj.add(x - 1);
            adj.add(x);
        }
        /* Not at either of vertical borders: all three neighbors */
        else {
            adj.add(x - 1);
            adj.add(x);
            adj.add(x + 1);
        }

        /* Return adjacency list */
        return adj;
    }

    /**
     * Relaxes edges (if applicable) of edges incident to a source vertex.
     *
     * @param energy  - Energy grid to calculate edge weights
     * @param distTo  - Shortest path lengths to date
     * @param edgeTo  - Parent X coordinate for a given vertex (all parent Y coordinates are child - 1)
     * @param sourceX - Source vertex's x coordinate
     * @param sourceY - Source vertex's y coordinate
     */
    private void relax(double[][] energy, double[][] distTo, int[][] edgeTo,
                       int sourceX, int sourceY) {
        int y = sourceY + 1; // All neighbors are to the bottom for energy grid

        // Relax all neighbors
        for (int x : adj(sourceX, sourceY)) {
            // Calculate distances
            double oldDistance = distTo[x][y];
            double newDistance = distTo[sourceX][sourceY] + energy[x][y];
            // Update distTo and edgeTo if edge relaxed
            if (newDistance < oldDistance) {
                distTo[x][y] = newDistance;
                edgeTo[x][y] = sourceX;     // We only store x coordinate
            }
        }
    }



    /**
     * Transposes the picture from horizontal back to vertical.
     * In other words, rotates the picture -90 degrees (counterclockwise)
     */
    private void transpose() {
        Picture newPicture = new Picture(height(), width());
        for (int oldX = 0; oldX < width(); oldX++) {
            for (int oldY = 0; oldY < height(); oldY++) {
                int newX = oldY;
                int newY = width() - 1 - oldX;
                newPicture.set(newX, newY, picture.get(oldX, oldY));
            }
        }
        picture = newPicture;
    }


    /**
     * Transposes the picture from vertical (default) to horizontal
     * In other words, rotates the picture 90 degrees (clockwise)
     */
    private void transposeBack() {
        Picture newPicture = new Picture(height(), width());
        // Iterate through grid column by column
        for (int oldX = 0; oldX < width(); oldX++) {
            for (int oldY = 0; oldY < height(); oldY++) {
                int newX = height() - 1 - oldY;
                int newY = oldX;
                newPicture.set(newX, newY, picture.get(oldX, oldY));
            }
        }
        picture = newPicture;
    }

    /**
     * Converts a horizontal seam into a vertical seam.
     * Rotates each array entry -90 degrees.
     *
     * @param horizontalSeam A horizontal seam
     * @return The corresponding vertical seam.
     */
    private int[] transposeSeam(int[] horizontalSeam) {
        int[] verticalSeam = new int[horizontalSeam.length];
        for (int i = 0; i < horizontalSeam.length; i++) {
            int oldX = i;
            int oldY = horizontalSeam[i];
            int newX = oldY;
            int newY = width() - 1 - oldX;
            verticalSeam[newY] = newX;
        }
        return verticalSeam;
    }

    /**
     * Converts a vertical seam into a horizontal seam.
     * Rotates each array entry 90 degrees.
     *
     * @param verticalSeam A vertical seam
     * @return The corresponding horizontal seam
     */
    private int[] transposeSeamBack(int[] verticalSeam) {
        int[] horizontalSeam = new int[verticalSeam.length];
        for (int i = 0; i < verticalSeam.length; i++) {
            int oldX = verticalSeam[i];
            int oldY = i;
            int newX = height() - 1 - oldY;
            int newY = oldX;
            horizontalSeam[newX] = newY;
        }
        return horizontalSeam;
    }

    private void validateNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Null argument");
        }
    }

    private void validateCoordinates(int x, int y) {
        if (x < 0) throw new IllegalArgumentException("X cannot be negative");
        if (y < 0) throw new IllegalArgumentException("Y cannot be negative");
        if (x >= width()) throw new IllegalArgumentException("X is too large");
        if (y >= height()) throw new IllegalArgumentException("Y is too large");
    }

    private void validateVerticalSeam(int[] seam) {
        if (width() <= 1) throw new IllegalArgumentException("Width is already 1");
        if (seam.length != height()) throw new IllegalArgumentException("Seam does not equal the height");
        for (int y = 0; y < seam.length; y++) {
            int x = seam[y];
            validateCoordinates(x, y);
        }
    }

    private void validateHorizontalSeam(int[] seam) {
        if (height() <= 1) throw new IllegalArgumentException("Height is already 1");
        if (seam.length != width()) throw new IllegalArgumentException("Seam does not equal the width");
        for (int x = 0; x < seam.length; x++) {
            int y = seam[x];
            validateCoordinates(x, y);
        }
    }

    private void validateSeamEntriesDifferByAtMostOne(int[] seam) {
        int prevEntry = seam[0] - 1;
        for (int entry : seam) {
            int difference = Math.abs(entry - prevEntry);
            if (difference > 1) {
                throw new IllegalArgumentException(String.format("Seam entries %s and %s differ by more than 1", prevEntry, entry));
            }
            prevEntry = entry;
        }
    }
}
