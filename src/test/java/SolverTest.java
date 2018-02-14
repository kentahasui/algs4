/******************************************************************************
 *  Compilation:  javac SolverTest.java
 *  Execution:    java SolverTest
 *  Dependencies: Solver.java
 *
 *  Unit tests for Solver
 ******************************************************************************/
import static com.google.common.truth.Truth.assertThat;
import org.junit.Test;

public class SolverTest {
    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for constructor
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void boardIsNull_shouldThrowIllegalArgumentException(){
        new Solver(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for isSolvable()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_shouldBeSolvable(){
        int[][] initial = {{0}};
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isTrue();
    }

    @Test
    public void twoByTwo_unsolvable_shouldBeUnsolvable(){
        int[][] initial = {
                {3, 2},
                {1, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isFalse();
    }

    @Test
    public void twoByTwo_solved_shouldBeSolvable(){
        int[][] initial = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isTrue();
    }

    @Test
    public void twoByTwo_oneMoveToGoal_shouldBeSolvable(){
        int[][] initial = {
                {1, 0},
                {3, 2}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isTrue();
    }

    @Test
    public void twoByTwo_twoMovesToGoal_shouldBeSolvable(){
        int[][] initial = {
                {0, 1},
                {3, 2}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isTrue();
    }

    @Test
    public void threeByThree_solved_shouldBeSolvable(){
        int[][] initial = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isTrue();
    }

    @Test
    public void fourByFour_solved_shouldBeSolvable(){
        int[][] initial = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isTrue();
    }

    @Test
    public void fourByFour_unsolvable_shouldNotBeSolvable(){
        int[][] initial = {
                {2, 1, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.isSolvable()).isFalse();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for moves()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_shouldHaveZeroMovesToGoal(){
        int[][] initial = {{0}};
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(0);
    }

    @Test
    public void twoByTwo_unsolvable_shouldHaveNegativeMovesToGoal(){
        int[][] initial = {
                {3, 2},
                {1, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(-1);
    }

    @Test
    public void twoByTwo_solved_shouldHaveZeroMovesToGoal(){
        int[][] initial = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(0);
    }

    @Test
    public void twoByTwo_oneMoveToGoal_shouldHaveOneMoveToGoal(){
        int[][] initial = {
                {1, 0},
                {3, 2}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(1);
    }

    @Test
    public void twoByTwo_twoMovesToGoal_shouldHaveTwoMovesToGoal(){
        int[][] initial = {
                {0, 1},
                {3, 2}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(2);
    }

    @Test
    public void threeByThree_solved_shouldHaveZeroMovesToGoal(){
        int[][] initial = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(0);
    }

    @Test
    public void fourByFour_solved_shouldHaveZeroMovesToGoal(){
        int[][] initial = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(0);
    }

    @Test
    public void fourByFour_unsolvable_shouldHaveNegativeMovesToGoal(){
        int[][] initial = {
                {2, 1, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.moves()).isEqualTo(-1);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for solution()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void oneByOne_solutionShouldOnlyBeInitialBoard(){
        int[][] initial = {{0}};
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).containsExactly(
                new Board(initial)).inOrder();
    }

    @Test
    public void twoByTwo_unsolvable_solutionShouldBeNull(){
        int[][] initial = {
                {3, 2},
                {1, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).isNull();
    }

    @Test
    public void twoByTwo_solved_solutionShouldOnlyBeInitialBoard(){
        int[][] initial = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).containsExactly(
                new Board(initial)).inOrder();
    }

    @Test
    public void twoByTwo_oneMoveToGoal_solutionShouldHaveTwoBoards(){
        // Initial board
        int[][] initial = {
                {1, 0},
                {3, 2}
        };

        // Move blank down
        int[][] move1 = {
                {1, 2},
                {3, 0}
        };

        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).containsExactly(
                new Board(initial),
                new Board(move1)
        ).inOrder();
    }

    @Test
    public void twoByTwo_twoMovesToGoal_solutionShouldHaveThreeBoards(){
        int[][] initial = {
                {0, 1},
                {3, 2}
        };

        // Move blank right
        int[][] move1 = {
                {1, 0},
                {3, 2}
        };

        // Move blank down
        int[][] move2 = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).containsExactly(
                new Board(initial),
                new Board(move1),
                new Board(move2)).inOrder();
    }

    @Test
    public void twoByTwo_twoMovesToGoal_solverShouldBeImmutable(){
        int[][] initial = {
                {0, 1},
                {3, 2}
        };

        // Move blank right
        int[][] move1 = {
                {1, 0},
                {3, 2}
        };

        // Move blank down
        int[][] move2 = {
                {1, 2},
                {3, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);

        assertThat(solver.solution()).containsExactly(
                board,
                new Board(move1),
                new Board(move2)).inOrder();

        initial[0] = new int[]{3, 2};
        initial[1] = new int[]{1, 0};

        assertThat(solver.solution()).containsExactly(
                board,
                new Board(move1),
                new Board(move2)).inOrder();

        assertThat(solver.solution()).containsExactly(
                board,
                new Board(move1),
                new Board(move2)).inOrder();

    }

    @Test
    public void threeByThree_solved_solutionShouldContainOnlyInitialBoard(){
        int[][] initial = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).containsExactly(
                new Board(initial)).inOrder();
    }

    @Test
    public void fourByFour_solved_solutionShouldContainOnlyInitialBoard(){
        int[][] initial = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).containsExactly(
                new Board(initial)).inOrder();
    }

    @Test
    public void fourByFour_unsolvable_solutionShouldBeNull(){
        int[][] initial = {
                {2, 1, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        Board board = new Board(initial);
        Solver solver = new Solver(board);
        assertThat(solver.solution()).isNull();
    }
}
