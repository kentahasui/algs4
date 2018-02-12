/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board input.txt
 *  Dependencies:
 *
 *  A class representing a board to use in an 8-puzzle application.
 *
 *
 *
 ******************************************************************************/

/** NxN board of integers*/
public class Board {
    // Value that represents a blank tile in the board
    private static final int BLANK = 0;

    private final int n;
    private final int nSquared;
    private final char[] board;

    // Index of the blank item
    private int blankIndex;

    private boolean isSolution;
    private int manhattan;
    private int hamming;
    private Iterable<Board> neighbors;

    public Board(int[][] input){
        if (input == null) { throw new IllegalArgumentException("null input"); }
        if (input.length <= 1) { throw new IllegalArgumentException("Input too small"); }

        n = input.length;
        nSquared = n * n;
        board = new char[nSquared];
    }

    public int dimension(){
        return -1;
    }

    /**
     * Calculates the hamming distance.
     * @return A non-negative integer representing the number of blocks
     * that are out of place.
     */
    public int hamming(){
        return -1;
    }

    /**
     * Calculates and returns the Manhattan distance.
     * @return The sum of distances (vertical + horizontal) between a
     * given block and its goal.
     * A non-negative integer.
     */
    public int manhattan(){
        return -1;
    }

    /**
     * Returns true for goal boards and false otherwise.
     * A goal board has the blank block located on the lower-right hand corner.
     * All other blocks are positioned in increasing, sorted order.
     * For example:
     *
     * <br/>
     * 1 2 3<br/>
     * 4 5 6<br/>
     * 7 8 0<br/>
     * <br/>
     *
     * Formally:
     * For each index i such that 0 <= i < n^2-1:
     * board[i] == i+1 <br/>
     *
     * Additionally:
     * board[n^2-1] == 0.
     *
     * @return True for goal boards.
     */
    public boolean isGoal(){
        return false;
    }

    /**
     * @return A board with any two (non-blank) blocks switched
     */
    public Board twin(){
        return this;
    }

    @Override
    public boolean equals(Object that){
        return false;
    }

    /**
     * Returns all valid neighbor {@link Board}s.
     * Swapping the blank block with any adjacent block creates a neighbor board.
     * @return An iterable of neighbor boards.
     */
    public Iterable<Board> neighbors(){
        return null;
    }

    /**
     * String representation of this board.
     */
    public String toString(){
        return "";
    }

}
