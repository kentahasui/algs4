/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board input.txt
 *  Dependencies: LinkedQueue.java
 *
 *  A class representing a board to use in an 8-puzzle application.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.LinkedQueue;

/** NxN board of integers*/
public class Board {
    // Value that represents a blank tile in the board
    private static final int BLANK = 0;
    private static final int INVALID = -1;

    // Board dimension
    private final int n;
    // Board size (number of blocks / grids)
    private final int nSquared;
    // Index of lower right-most block
    private final int lowerRightIndex;

    // Internal representation of the board
    private final char[] board;

    // Index of the blank item
    private int blankIndex;

    // Cached values
    private Boolean isGoal;
    private int manhattan = INVALID;
    private int hamming = INVALID;

    /**
     * Constructor.
     * @param input An nxn grid containing the numbers 0 ... n^2-1
     */
    public Board(int[][] input){
        if (input == null) { throw new IllegalArgumentException("Null input"); }
        if (input.length <= 0) { throw new IllegalArgumentException("Input too small"); }

        n = input.length;
        nSquared = n * n;
        lowerRightIndex = nSquared - 1;
        board = populateBoardAndSetBlankIndex(input); // O(N^2)
    }

    /**
     * Loads the implementation of the board as a 1-D array of chars
     */
    private char[] populateBoardAndSetBlankIndex(int[][] input){
        char[] arr = new char[nSquared];
        int index = 0;
        for(int[] row : input){
            for(int val : row){
                if (val == BLANK) { blankIndex = index; }
                arr[index++] = (char) val;
            }
        }
        return arr;
    }

    /**
     * Calculates the block that should be at the specified index.
     * @param index The 1-D index of the board array
     * @return Returns 0 for the lower-right position in the board.
     * For all other indices, returns index+1.
     */
    private int toGoalValue(int index){
        if (index == nSquared - 1) { return 0; }
        return index + 1;
    }

    /**
     * True if the block at the given index has the goal value.
     * False otherwise.
     */
    private boolean hasGoalValue(int index){
        return toGoalValue(index) == (int) board[index];
    }

    /**
     * Given a block value, determines whether it is at the correct position.
     * The blank block should be at the lower-rightmost position
     * (index n*n-1).
     * All other blocks should be at the one-based index.
     *
     * @param currentIndex A positive integer representing a block.
     * @return The index of <em>board</em> that this
     */
    private int toGoalIndex(int currentIndex){
        int value = (int) board[currentIndex];
        if (value == BLANK) { return lowerRightIndex; }
        return value - 1;
    }


    /**
     * Determines whether a given block is at its goal location.
     * @param index The index of the block in <em>board</em>
     * @return True if the block is at the goal index. False otherwise.
     */
    private boolean atGoalIndex(int index){
        return index == toGoalIndex(index);
    }

    /** Private helper method to determine whether a block at a given index
     * is blank.*/
    private boolean isBlank(int index){
        return board[index] == BLANK;
    }

    /**
     * Returns the dimension of the board.
     * For a 4x4 board, this will return 4.
     */
    public int dimension(){
        return n;
    }

    /**
     * Calculates the hamming distance.
     * @return A non-negative integer representing the number of blocks
     * that are out of place.
     */
    public int hamming(){
        if (hamming == INVALID) {
            hamming = calculateHamming();
        }
        return hamming;
    }

    /** Helper method to calculate the hamming value for this board */
    private int calculateHamming(){
        int hammingVal = 0;
        for (int index = 0; index < nSquared; index++){
            // Skip blank
            if (isBlank(index)) { continue; }

            // Skip blocks at goal index
            if (atGoalIndex(index)) { continue; }

            hammingVal++;
        }
        return hammingVal;
    }


    /**
     * Calculates and returns the Manhattan distance.
     * @return The sum of distances (vertical + horizontal) between a
     * given block and its goal.
     * A non-negative integer.
     */
    public int manhattan(){
        if (manhattan == INVALID){
            manhattan = calculateManhattan();
        }
        return manhattan;
    }

    private int calculateManhattan(){
        int manhattanVal = 0;
        for (int index = 0; index < nSquared; index++){
            // Blanks shouldn't be factored into the manhattan distance
            if (isBlank(index)) { continue; }

            manhattanVal += distance(index);
        }
        return manhattanVal;
    }

    /**
     * Manhattan distance between the block at a given position
     * @param index
     * @return
     */
    private int distance(int index){
        int goalIndex = toGoalIndex(index);
        return verticalDistance(index, goalIndex) +
                horizontalDistance(index, goalIndex);

    }

    private int verticalDistance(int currentIndex, int goalIndex){
        return Math.abs(toRow(currentIndex) - toRow(goalIndex));
    }

    private int horizontalDistance(int currentIndex, int goalIndex){
        return Math.abs(toCol(currentIndex) - toCol(goalIndex));
    }

    private int toRow(int index){
        return index / n;
    }

    private int toCol(int index){
        return index % n;
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
        if (isGoal == null){
            isGoal = calculateIsGoal();
        }
        return isGoal;
    }

    private boolean calculateIsGoal(){
        // If the blank isn't at the lower-right corner, quit early
        if (blankIndex != nSquared - 1){
            return false;
        }

        // Loop through the board to see if all blocks are in correct position
        for (int index = 0; index < nSquared - 1; index++){
            if (!hasGoalValue(index)) { return false; }
        }
        return true;
    }


    /**
     * @return A board with any two (non-blank) blocks switched
     * If dimension is 1, returns the same board.
     */
    public Board twin(){
        if (dimension() == 1) { return new Board(boardTo2DArray()); }

        int blockIndex1 = firstBlockIndex(0);
        int blockIndex2 = firstBlockIndex(blockIndex1+1);

        int[][] twinInput = boardTo2DArray();
        exchange(twinInput, blockIndex1, blockIndex2);
        return new Board(twinInput);
    }

    /**
     * Helper method to swap the contents of a 2D array at the given indices.
     * @param arr A two-dimensional array that
     * @param index1 The first index to swap for the corresponding 1D array
     * @param index2 The second index to swap for the corresponding 1D array
     */
    private void exchange(int[][] arr, int index1, int index2){
        int row1 = toRow(index1);
        int col1 = toCol(index1);

        int row2 = toRow(index2);
        int col2 = toCol(index2);

        int tmp = arr[row1][col1];
        arr[row1][col1] = arr[row2][col2];
        arr[row2][col2] = tmp;
    }

    /**
     * Converts the current board to a new 2D array
     * @return A 2D array
     */
    private int[][] boardTo2DArray(){
        int[][] output = new int[n][n];
        int index = 0;
        for (int row = 0; row < n; row++){
            for (int col = 0; col < n; col++){
                output[row][col] = board[index++];
            }
        }
        return output;
    }

    /**
     * Returns the index of the first non-empty block in the board.
     * Pass in a start index to start searching at a given position.
     * To search all the blocks, pass in 0 as start.
     *
     * @param start The index (inclusive) to start searching at.
     * @return The index of the first non-empty block. -1 if not found.
     */
    private int firstBlockIndex(int start){
        for(int index = start; index<nSquared; index++){
            if (isBlank(index)) { continue; }
            return index;
        }
        return -1;
    }

    /**
     * Custom equals implementation.
     */
    @Override
    public boolean equals(Object that){
        if (this == that) { return true; }
        if (that == null) { return false; }
        if (getClass() != that.getClass()) { return false; }

        Board thatBoard = (Board) that;
        if (dimension() != thatBoard.dimension()) { return false; }

        for (int index = 0; index < nSquared; index++){
            if (board[index] != thatBoard.board[index]) { return false; }
        }
        return true;
    }



    /**
     * Returns all valid neighbor {@link Board}s.
     * Swapping the blank block with any adjacent block creates a neighbor board.
     * @return An iterable of neighbor boards.
     */
    public Iterable<Board> neighbors(){
        int[][] tmp = boardTo2DArray();
        LinkedQueue<Board> output = new LinkedQueue<>();
        for (Direction direction : Direction.values()){
            // Skip invalid directions
            if (!neighborIsValid(direction)) { continue; }

            // Swap blank with neighbor
            int indexToSwap = neighborBlankIndex(direction);
            exchange(tmp, blankIndex, indexToSwap);
            output.enqueue(new Board(tmp));

            // Reset, for performance (micro-optimization)
            exchange(tmp, blankIndex, indexToSwap);
        }
        return output;
    }

    /**
     * True if we can swap the blank block with the neighbor to the given
     * direction without going out of bounds.
     */
    private boolean neighborIsValid(Direction direction){
        int blankRow = toRow(blankIndex);
        int blankCol = toCol(blankIndex);
        switch(direction){
            case UP: return blankRow > 0;
            case DOWN: return blankRow < n - 1;
            case LEFT: return blankCol > 0;
            case RIGHT: return blankCol < n -1;
            default: return false;
        }
    }

    /**
     * @param direction UP, DOWN, LEFT, or RIGHT.
     * @return The 1D index of the neighbor at the given direction.
     */
    private int neighborBlankIndex(Direction direction){
        switch(direction) {
            case UP: return blankIndex - n;
            case DOWN: return blankIndex + n;
            case LEFT: return blankIndex - 1;
            case RIGHT: return blankIndex + 1;
            default: return -1;
        }
    }

    /** Convenience enum for looping through neighbors */
    private enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * String representation of this board.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d \n", dimension()));

        int[][] board2D = boardTo2DArray();
        for (int row = 0; row < n; row++){
            for (int col = 0; col < n; col++){
                builder.append(String.format("%2d ", board2D[row][col]));
            }
            builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }
}
