/******************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver input.txt
 *  Dependencies: In.java MinPQ.java ResizingArrayStack.java StdOut.java
 *
 *  Solves an 8-puzzle instance.
 *
 *  Usage:
 *  java Solver input.txt
 *
 *  input.txt should be a text file containing a grid of numbers from 0 to N^2-1:
 *
 *  N
 *  x01 x02 x03 ... x0N
 *  x11 x12 y13 ... y1N
 *  ...
 *  xN1 xN2 xN3 ... xNN
 *
 *  Where N is the dimension of the grid.
 *  All values in the grid must be unique and be between 0 and N^2-1 (inclusive)
 *  0 represents a blank block.
 *
 *  A solved puzzle represents a grid with all items in ascending order and
 *  the blank block at the lower right-most position.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solver class.
 */
public class Solver {
    // Pointer to goal Board
    private final BoardMove goal;

    // Whether this board is indeed solvable
    private final boolean solvable;

    /**
     * Constructor.
     * @param initial An initial board.
     * @throws IllegalArgumentException if the board is null.
     */
    public Solver(Board initial){
        if (initial == null) { throw new IllegalArgumentException("null input"); }

        // Priority queue for A* search starting from initial board
        MinPQ<BoardMove> pq = new MinPQ<>();
        // Priority queue for A* search starting from twin board to detect unsolvables
        MinPQ<BoardMove> pqTwin = new MinPQ<>();

        // Initialize A* search algorithm
        pq.insert(new BoardMove(0, null, initial));
        pqTwin.insert(new BoardMove(0, null, initial.twin()));

        BoardMove current = pq.delMin();
        BoardMove currentTwin = pqTwin.delMin();

        // Search until we find the goal board
        while(!current.board.isGoal() && !currentTwin.board.isGoal()){
            addNeighborsToQueue(current, pq);
            addNeighborsToQueue(currentTwin, pqTwin);
            current = pq.delMin();
            currentTwin = pqTwin.delMin();
        }

        // Goal now contains the correct goal board, even for unsolvable boards.
        solvable = current.board.isGoal();
        goal = solvable ? current : currentTwin;
    }

    /**
     * Helper method to add valid neighbors to a priority queue.
     * Reduces code duplication, as we run this operation for both
     * a board and its twin.
     * Each node in the queue has a pointer to the parent, as well as
     * the number of moves made so far in this specific path down the tree.
     */
    private void addNeighborsToQueue(BoardMove current, MinPQ<BoardMove> queue){
        for (Board neighbor : current.board.neighbors()){
            if (equalToParent(current, neighbor)) { continue; }
            queue.insert(new BoardMove(current.moves + 1, current, neighbor));
        }
    }

    /**
     * Determines whether a boardMove can be skipped.
     * @param current Current node that we are processing.
     * @param neighbor A neighbor of this node.
     * @return True if the neighbor is the same as the node's parent.
     * Returns False if the current node is the initial board.
     * Returns False in all other cases.
     */
    private boolean equalToParent(BoardMove current, Board neighbor){
        if (current == null || neighbor == null) { return false; }
        if (current.prev == null) { return false; }
        return neighbor.equals(current.prev.board);
    }

    /** True for solvable boards. */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Number of moves to get to the goal
     * @return -1 if the board is unsolvable.
     */
    public int moves(){
        if (!isSolvable()) { return -1; }
        return goal.moves;
    }

    /**
     * Returns the sequence of boards taken until the goal was reached.
     * The first element in the iterable will be the initial node.
     * The final element will be the goal node.
     * If the initial board is already solved, will return iterable of
     * length 1.
     * If the initial board is unsolvable, will return null.
     *
     * @return An iterable of {@link Board}s.
     */
    public Iterable<Board> solution() {
        // Guard clause
        if (!isSolvable()) { return null; }

        // Travel from goal (leaf) to root
        ResizingArrayStack<Board> solution = new ResizingArrayStack<>();
        BoardMove current = goal;
        while(current != null){
            solution.push(current.board);
            current = current.prev;
        }

        return solution;
    }

    /** Command line client */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    /**
     * Represents node in game tree while running the A* search algorithm.
     * Each node has a pointer to its parent.
     * Given a node, you can traverse up the tree to its root.
     * In terms of the 8Puzzle application, given a goal BoardMove, you can
     * follow the parent pointers until you get to the move.
     */
    private class BoardMove implements Comparable<BoardMove> {
        // Moves so far of this SPECIFIC PATH within the A* search algorithm
        private int moves;
        private int priority;
        private int manhattan;
        private BoardMove prev;
        private Board board;

        private BoardMove(int moves, BoardMove prev, Board board){
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.priority = moves + manhattan;
            this.prev = prev;
            this.board = board;
        }

        @Override
        public int compareTo(BoardMove other){
            // Break ties by smaller manhattan
            if (priority == other.priority) {
                return Integer.compare(manhattan, other.manhattan);
            }
            return Integer.compare(priority, other.priority);
        }
    }
}
