/******************************************************************************
 *  Compilation:  javac BoggleSolver.java
 *  Execution:    java BoggleSolver dictionary.txt board.txt
 *  Dependencies: In.java StdOut.java BoggleBoard.java
 *
 *  This class solves arbitrary Boggle boards.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoggleSolver {
    /**
     * Dictionary of valid words
     */
    private final BoggleTrie dict = new BoggleTrie();

    public BoggleSolver(String[] dictionary) {
        validateNotNull(dictionary);
        for (String word : dictionary) {
            dict.add(word);
        }
    }

    /**
     * Returns score of a word, or 0 if the word is not in the dictionary
     */
    public int scoreOf(String word) {
        validateNotNull(word);
        if (!dict.contains(word)) return 0;
        if (word.length() <= 2) return 0;
        if (word.length() <= 4) return 1;
        if (word.length() == 5) return 2;
        if (word.length() == 6) return 3;
        if (word.length() == 7) return 5;
        return 11;
    }

    /**
     * Finds all valid words in boggle board
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        validateNotNull(board);
        /* Valid words found so far */
        BoggleTrie found = new BoggleTrie();

        /* Already marked words in this simple path. Will be reset in backtracking subroutine */
        boolean[][] marked = new boolean[board.rows()][board.cols()];

        /* Current word we are processing */
        StringBuilder current = new StringBuilder();

        // Run backtracking DFS subroutine from every cell
        // Finds all simple paths that form valid words
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                getAllValidWords(board, found, marked, current, row, col);
            }
        }

        return found;
    }

    private void getAllValidWords(BoggleBoard board, BoggleTrie found,
                                  boolean[][] marked, StringBuilder current,
                                  int row, int col) {
        // Prevent re-use of die on a given simple path
        marked[row][col] = true;

        // Add letter
        char letter = board.getLetter(row, col);
        current.append(letter);
        if (letter == 'Q') current.append('U');

        // Found a valid word. Add to collection of found words
        if (current.length() >= 3 && dict.contains(current)) {
            found.add(current);
        }

        // Keep searching iff we can make a valid word going forward
        if (dict.isPrefix(current.toString())) {
            for (Cell cell : adj(board, row, col)) {
                // Skip already-seen columns. Maybe move this to adj? nah. We want to eventually cache adj cells
                if (marked[cell.row][cell.col]) continue;
                // Recursively check all valid words
                getAllValidWords(board, found, marked, current, cell.row, cell.col);
            }
        }

        // Backtrack
        marked[row][col] = false;
        current.deleteCharAt(current.length() - 1);
        if (letter == 'Q') {
            current.deleteCharAt(current.length() - 1);
        }
    }

    /**
     * Returns all valid neighbors for a given cell
     */
    private List<Cell> adj(BoggleBoard board, int row, int col) {
        List<Cell> cells = new ArrayList<>();
        // Row above
        cells.add(new Cell(row - 1, col - 1)); // Top left
        cells.add(new Cell(row - 1, col)); // Top middle
        cells.add(new Cell(row - 1, col + 1)); // Top right
        // Middle row
        cells.add(new Cell(row, col - 1)); // Left
        cells.add(new Cell(row, col + 1)); // Right
        // Bottom row
        cells.add(new Cell(row + 1, col - 1)); // Bottom left
        cells.add(new Cell(row + 1, col)); // Bottom middle
        cells.add(new Cell(row + 1, col + 1)); // Bottom right

        // Remove invalid cells
        for (Iterator<Cell> iter = cells.iterator(); iter.hasNext();) {
            Cell c = iter.next();
            if (!isValid(board, c)) iter.remove();
        }

        return cells;
    }

    /**
     * Helper method to determine whether a cell is valid on the board
     */
    private boolean isValid(BoggleBoard board, Cell cell) {
        return cell.row >= 0
                && cell.col >= 0
                && cell.row < board.rows()
                && cell.col < board.cols();
    }

    private void validateNotNull(Object arg) {
        if (arg == null) throw new IllegalArgumentException("Argument cannot be null");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

    /**
     * Represents a cell in the Boggle Board (row + col)
     */
    private static final class Cell {
        int row;
        int col;

        private Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return String.format("[%s, %s]", row, col);
        }
    }

    /*
    private void getAllValidWords(BoggleBoard board, BoggleTrie found,
                                  boolean[][] marked, CellCollection[][] adj,
                                  StringBuilder current,
                                  int row, int col){
        // Prevent re-use of die on a given simple path
        marked[row][col] = true;

        // Add letter
        char letter = board.getLetter(row, col);
        current.append(letter);
        if (letter == 'Q') current.append('U');

        // Found a valid word. Add to collection of found words
        if (current.length() >= 3 && dict.contains(current)){
            found.add(current);
        }

        // Keep searching iff we can make a valid word going forward
        if (dict.isPrefix(current.toString())){
            for (Cell cell : adj[row][col]){
                // Skip already-seen columns. Maybe move this to adj? nah. We want to eventually cache adj cells
                if (marked[cell.row][cell.col]) continue;
                // Recursively check all valid words
                getAllValidWords(board, found, marked, adj, current, cell.row, cell.col);
            }
        }

        // Backtrack
        marked[row][col] = false;
        current.deleteCharAt(current.length() - 1);
        if (letter == 'Q') {
            current.deleteCharAt(current.length() - 1);
        }
    }

    private CellCollection[][] makeAdj(BoggleBoard b){
        CellCollection[][] out = new CellCollection[b.rows()][b.cols()];
        for(int row = 0; row < b.rows(); row++){
            for (int col = 0; col < b.cols(); col++){
                out[row][col] = makeAdj(b, row, col);
            }
        }
        return out;
    }

    private CellCollection makeAdj(BoggleBoard board, int row, int col){
        List<Cell> cells = new ArrayList<>();
        // Row above
        cells.add(new Cell(row - 1, col - 1)); // Top left
        cells.add(new Cell(row - 1, col)); // Top middle
        cells.add(new Cell(row - 1, col + 1)); // Top right
        // Middle row
        cells.add(new Cell(row, col - 1)); // Left
        cells.add(new Cell(row, col + 1)); // Right
        // Bottom row
        cells.add(new Cell(row + 1, col - 1)); // Bottom left
        cells.add(new Cell(row + 1, col)); // Bottom middle
        cells.add(new Cell(row + 1, col + 1)); // Bottom right

        // Remove invalid cells
        for (Iterator<Cell> iter = cells.iterator(); iter.hasNext();){
            Cell c = iter.next();
            if (!isValid(board, c)) iter.remove();
        }
        return new CellCollection(cells);
    }

    // Represents a collection of Cells
    private static class CellCollection implements Iterable<Cell>{
        private List<Cell> cells;

        private CellCollection(List<Cell> cells) {
            this.cells = cells;
        }

        @Override
        public Iterator<Cell> iterator() {
            return cells.iterator();
        }
    }
*/
}
