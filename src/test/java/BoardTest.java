/******************************************************************************
 *  Compilation:  javac BoardTest.java
 *  Execution:    java BoardTest
 *  Dependencies: Board.java
 *
 *  Unit tests for Board
 ******************************************************************************/
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class BoardTest {

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for constructor
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void nullBoard_shouldThrowException(){
        new Board(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for dimension()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_dimension_shouldBeOne(){
        int[][] input = {
                {0}
        };
        Board board = new Board(input);
        assertThat(board.dimension()).isEqualTo(1);
    }

    @Test
    public void twoByTwo_dimension_shouldBeTwo(){
        int[][] input = {
                {0, 1},
                {2, 3}
        };
        Board board = new Board(input);
        assertThat(board.dimension()).isEqualTo(2);
    }

    @Test
    public void threeByThree_dimension_shouldBeThree(){
        int[][] input = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board board = new Board(input);
        assertThat(board.dimension()).isEqualTo(3);
    }

    @Test
    public void fourByFour_dimension_shouldBeFour(){
        int[][] input = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        };
        Board board = new Board(input);
        assertThat(board.dimension()).isEqualTo(4);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for isGoal()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_shouldBeGoal(){
        int[][] input = {
                {0}
        };
        Board board = new Board(input);
        assertThat(board.isGoal()).isTrue();
    }

    @Test
    public void twoByTwo_solved_shouldBeGoal(){
        int[][] input = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(input);
        assertThat(board.isGoal()).isTrue();
    }

    @Test
    public void threeByThree_solved_shouldBeGoal(){
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(input);
        assertThat(board.isGoal()).isTrue();
    }

    @Test
    public void fourByFour_solved_shouldBeGoal(){
        int[][] input = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(input);
        assertThat(board.isGoal()).isTrue();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for hamming()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_hammingShouldBeZero(){
        int[][] input = {
                {0}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(0);
    }

    @Test
    public void twoByTwo_solved_hammingShouldBeZero(){
        int[][] input = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(0);
    }

    @Test
    public void threeByThree_solved_hammingShouldBeZero(){
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(0);
    }

    @Test
    public void fourByFour_solved_hammingShouldBeZero(){
        int[][] input = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(0);
    }

    @Test
    public void twoByTwo_oneBlockOutOfPlace_hammingShouldBeOne(){
        int[][] input = {
                {1, 2},
                {0, 3}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(1);
    }

    @Test
    public void twoByTwo_twoBlocksOutOfPlace_hammingShouldBeTwo(){
        int[][] input = {
                {2, 1},
                {3, 0}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(2);
    }

    @Test
    public void twoByTwo_threeBlocksOutOfPlace_hammingShouldBeThree(){
        int[][] input = {
                {3, 0},
                {2, 1}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(3);
    }

    @Test
    public void threeByThree_shouldCalculateHamming(){
        int[][] input = {
                {1, 5, 0},
                {3, 2, 4},
                {7, 8, 6}
        };
        // 1 + 3 + 2 + 1
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(5);
    }

    @Test
    public void fourByFour_shouldCalculateHamming(){
        int[][] input = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        };
        Board board = new Board(input);
        assertThat(board.hamming()).isEqualTo(15);
    }


    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for manhattan()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_manhattanShouldBeZero(){
        int[][] input = {
                {0}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(0);
    }

    @Test
    public void twoByTwo_solved_manhattanShouldBeZero(){
        int[][] input = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(0);
    }

    @Test
    public void threeByThree_solved_manhattanShouldBeZero(){
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(0);
    }

    @Test
    public void fourByFour_solved_manhattanShouldBeZero(){
        int[][] input = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(0);
    }

    @Test
    public void twoByTwo_oneBlockOutOfPlace_manhattan(){
        int[][] input = {
                {1, 2},
                {0, 3}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(1);
    }

    @Test
    public void twoByTwo_differentBlockOutOfPlace_manhattan(){
        int[][] input = {
                {0, 2},
                {3, 1}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(2);
    }

    @Test
    public void twoByTwo_twoBlocksOutOfPlace_manhattan(){
        int[][] input = {
                {2, 0},
                {3, 1}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(3);
    }

    @Test
    public void twoByTwo_threeBlocksOutOfPlace_manhattan(){
        int[][] input = {
                {0, 3},
                {2, 1}
        };
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(6);
    }

    @Test
    public void threeByThree_shouldCalculateManhattan(){
        int[][] input = {
                {1, 2, 3},
                {4, 6, 5},
                {0, 8, 7}
        };
        // 1 + 1 + 2
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(4);
    }

    @Test
    public void fourByFour_shouldCalculateManhattan(){
        int[][] input = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        };

        // 1:1 + 2:1 + 3:1 + 4:4
        // 5:1 + 6:1 + 7:1 + 8:4
        // 9:1 + 10:1 + 11:1 + 12:4
        // 13:1 + 14:1 + 15:1
        // 12(1) + 3(4) = 12 + 12 = 24
        Board board = new Board(input);
        assertThat(board.manhattan()).isEqualTo(24);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for twin()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_hammingShouldThrowException(){
        // TODO(kentahasui): implement
        int[][] input = {
                {0}
        };
        Board board = new Board(input);
    }

    @Test
    public void twoByTwo_blankAtFirstPosition_twin(){
        int[][] input = {
                {0, 1},
                {2, 3}
        };
        Board board = new Board(input);

        // Swap index 1 and index 2
        int[][] expected = {
                {0, 2},
                {1, 3}
        };
        assertThat(board.twin()).isEqualTo(new Board(expected));
    }

    @Test
    public void twoByTwo_blankAtSecondPosition_twin(){
        int[][] input = {
                {1, 0},
                {3, 2}
        };
        Board board = new Board(input);

        // Swap index 0 and index 2
        int[][] expected = {
                {3, 0},
                {1, 2}
        };
        assertThat(board.twin()).isEqualTo(new Board(expected));
    }

    @Test
    public void twoByTwo_blankAtThirdPosition_twin(){
        int[][] input = {
                {1, 2},
                {0, 3}
        };
        Board board = new Board(input);

        // Swap index 0 and index 1
        int[][] expected = {
                {2, 1},
                {0, 3}
        };
        assertThat(board.twin()).isEqualTo(new Board(expected));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for equals()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void differentDimensions_equals_returnsFalse(){
        int[][] input1 = {{0, 1}, {2, 3}};
        int[][] input2 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

        assertThat(new Board(input1)).isNotEqualTo(new Board(input2));
    }

    @Test
    public void differentBoards_equals_returnsFalse(){
        int[][] input1 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] input2 = {{1, 2, 3}, {4, 5, 0}, {6, 7, 8}};

        assertThat(new Board(input1)).isNotEqualTo(new Board(input2));
    }

    @Test
    public void equalBoards_equals_returnsTrue(){
        int[][] input1 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] input2 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

        assertThat(new Board(input1)).isEqualTo(new Board(input2));
    }

    @Test
    public void differentClasses_equals_returnsFalse(){
        int[][] input1 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        char[] input2 = new char[10];
        assertThat(new Board(input1)).isNotEqualTo(input2);
    }

    @Test
    public void equals_reflexive(){
        int[][] input = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        Board board = new Board(input);
        assertThat(board).isEqualTo(board);
    }

    @Test
    public void equals_transitive(){
        int[][] input1 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] input2 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] input3 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

        Board board1 = new Board(input1);
        Board board2 = new Board(input2);
        Board board3 = new Board(input3);

        assertThat(board1).isEqualTo(board2);
        assertThat(board2).isEqualTo(board3);
        assertThat(board1).isEqualTo(board3);
    }

    @Test
    public void equals_symmetric(){
        int[][] input1 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] input2 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

        Board board1 = new Board(input1);
        Board board2 = new Board(input2);

        assertThat(board1).isEqualTo(board2);
        assertThat(board2).isEqualTo(board1);
    }

    @Test
    public void equals_nonNull(){
        int[][] input = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        Board board = new Board(input);
        assertThat(board).isNotEqualTo(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for neighbors()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_shouldHaveNoNeighbors(){
        int[][] input = {
                {0}
        };
        Board board = new Board(input);
        assertThat(board.neighbors()).isEmpty();
    }

    @Test
    public void twoByTwo_blankAtTopLeft_shouldHaveTwoNeighbors(){
        int[][] input = {
                {0, 1},
                {2, 3}
        };
        Board board = new Board(input);

        // Move blank right
        int[][] neighbor1 = {
                {1, 0},
                {2, 3}
        };

        // Move blank down
        int[][] neighbor2 = {
                {2, 1},
                {0, 3}
        };

        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2));
    }

    @Test
    public void twoByTwo_blankAtTopRight_shouldHaveTwoNeighbors(){
        int[][] input = {
                {1, 0},
                {2, 3}
        };
        Board board = new Board(input);

        // Move blank left
        int[][] neighbor1 = {
                {0, 1},
                {2, 3}
        };

        // Move blank down
        int[][] neighbor2 = {
                {1, 3},
                {2, 0}
        };

        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2));
    }

    @Test
    public void twoByTwo_blankAtBottomLeft_shouldHaveTwoNeighbors(){
        int[][] input = {
                {1, 2},
                {0, 3}
        };
        Board board = new Board(input);

        // Move blank up
        int[][] neighbor1 = {
                {0, 2},
                {1, 3}
        };

        // Move blank right
        int[][] neighbor2 = {
                {1, 2},
                {3, 0}
        };

        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2));
    }

    @Test
    public void twoByTwo_blankAtBottomRight_shouldHaveTwoNeighbors(){
        int[][] input = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(input);

        // Move blank up
        int[][] neighbor1 = {
                {1, 0},
                {3, 2}
        };

        // Move blank left
        int[][] neighbor2 = {
                {1, 2},
                {0, 3}
        };

        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2));
    }

    @Test
    public void threeByThree_blankAtTopMiddle_shouldHaveThreeNeighbors(){
        int[][] input = {
                {1, 0, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board board = new Board(input);

        // Move blank left
        int[][] neighbor1 = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };

        // Move blank right
        int[][] neighbor2 = {
                {1, 2, 0},
                {3, 4, 5},
                {6, 7, 8}
        };

        // move blank down
        int[][] neighbor3 = {
                {1, 4, 2},
                {3, 0, 5},
                {6, 7, 8}
        };
        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2), new Board(neighbor3));
    }

    @Test
    public void threeByThree_blankAtBottomMiddle_shouldHaveThreeNeighbors(){
        int[][] input = {
                {1, 2, 3},
                {4, 5, 7},
                {7, 0, 8}
        };
        Board board = new Board(input);

        // Move blank up
        int[][] neighbor1 = {
                {1, 2, 3},
                {4, 0, 7},
                {7, 5, 8}
        };

        // Move blank right
        int[][] neighbor2 = {
                {1, 2, 3},
                {4, 5, 7},
                {7, 8, 0}
        };

        // move blank left
        int[][] neighbor3 = {
                {1, 2, 3},
                {4, 5, 7},
                {0, 7, 8}
        };

        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2),
                new Board(neighbor3));
    }

    @Test
    public void threeByThree_blankAtMiddle_shouldHaveFourNeighbors(){
        int[][] input = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };
        Board board = new Board(input);

        // Move blank up
        int[][] neighbor1 = {
                {1, 0, 3},
                {4, 2, 5},
                {6, 7, 8}
        };

        // Move blank right
        int[][] neighbor2 = {
                {1, 2, 3},
                {4, 5, 0},
                {6, 7, 8}
        };

        // move blank down
        int[][] neighbor3 = {
                {1, 2, 3},
                {4, 7, 5},
                {6, 0, 8}
        };

        // move blank left
        int[][] neighbor4 = {
                {1, 2, 3},
                {0, 4, 5},
                {6, 7, 8}
        };
        assertThat(board.neighbors()).containsExactly(
                new Board(neighbor1), new Board(neighbor2),
                new Board(neighbor3), new Board(neighbor4));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for toString()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void twoByTwo_toString(){
        int[][] input = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(input);
        assertThat(board.toString()).isEqualTo("" +
                "\n" +
                "2 \n" +
                " 1  2 \n" +
                " 3  0 "
        );
    }

    @Test
    public void threeByThree_toString(){
        int[][] input = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board board = new Board(input);
        assertThat(board.toString()).isEqualTo("" +
                "\n" +
                "3 \n" +
                " 0  1  2 \n" +
                " 3  4  5 \n" +
                " 6  7  8 ");
    }

    @Test
    public void fiveByFive_toString(){
        int[][] input = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 0}
        };
        Board board = new Board(input);
        assertThat(board.toString()).isEqualTo("" +
                "\n" +
                "5 \n" +
                " 1  2  3  4  5 \n" +
                " 6  7  8  9 10 \n" +
                "11 12 13 14 15 \n" +
                "16 17 18 19 20 \n" +
                "21 22 23 24  0 ");
    }
}
