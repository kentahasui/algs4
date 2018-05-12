import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class BoggleSolverTest {
    private BoggleSolver solver;
    private BoggleBoard board;

    ////////////////////////////////////////////////////////////////////////////
    // Ctor()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void ctor_null_throws(){
        solver = new BoggleSolver(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // getAllValidWords()
    ////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getAllValidWords_null_throws(){
        solver = new BoggleSolver(new String[]{"TEST"});
        solver.getAllValidWords(null);
    }

    @Test
    public void getAllValidWords_1x1_noMatches_returnsNothing(){
        solver = new BoggleSolver(new String[]{"A", "B", "C"});
        board = new BoggleBoard(new char[][]{{'Z'}});
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_1x1_returnsNothing(){
        solver = new BoggleSolver(new String[]{"A", "B", "C"});
        board = new BoggleBoard(new char[][]{{'A'}});
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_1x2_returnsNothing(){
        solver = new BoggleSolver(new String[]{"AB", "BA", "CCCC"});
        board = new BoggleBoard(new char[][]{
                {'A', 'B'}
        });
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_2x1_returnsNothing(){
        solver = new BoggleSolver(new String[]{"AB", "BA", "CCCC"});
        board = new BoggleBoard(new char[][]{
                {'A'},
                {'B'}
        });
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_2x2_onlyLength2Matches_returnsNothing(){
        solver = new BoggleSolver(new String[]{
                "AB", "AC", "AD", "BA", "BC", "BD",
                "CA", "CB", "CD", "DA", "DB", "DC"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_2x2_emptyDict_returnsNothing(){
        solver = new BoggleSolver(new String[]{
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_2x2_onlyLength3(){
        solver = new BoggleSolver(new String[]{
                "ABC", "ACB", "BCA", "DAB"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly(
                "ABC", "ACB", "BCA", "DAB"
        );
    }

    @Test
    public void getAllValidWords_2x2_allSameChar(){
        solver = new BoggleSolver(new String[]{
                "A", "AA", "AAA", "AAAA"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'A'},
                {'A', 'A'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly(
                "AAA", "AAAA"
        );
    }

    @Test
    public void getAllValidWords_2x2_allMatchesLengthFour(){
        solver = new BoggleSolver(new String[]{
                "ABCD", "ABDC", "ACBD", "ACDB", "ADBC", "ADCB",
                "BACD", "BADC", "BCAD", "BCDA", "BDAC", "BDCA",
                "CABD", "CADB", "CBAD", "CBDA", "CDAB", "CDBA",
                "DABC", "DACB", "DBAC", "DBCA", "DCAB", "DCBA"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly(
                "ABCD", "ABDC", "ACBD", "ACDB", "ADBC", "ADCB",
                "BACD", "BADC", "BCAD", "BCDA", "BDAC", "BDCA",
                "CABD", "CADB", "CBAD", "CBDA", "CDAB", "CDBA",
                "DABC", "DACB", "DBAC", "DBCA", "DCAB", "DCBA"
        );
    }

    @Test
    public void getAllValidWords_2x2_onlyInDict(){
        solver = new BoggleSolver(new String[]{
                "ABCD",
                "BACD", "BADC", "BCAD",
                "CDAB", "CDBA",
                "DCAB", "DCBA"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly(
                "ABCD",
                "BACD", "BADC", "BCAD",
                "CDAB", "CDBA",
                "DCAB", "DCBA"
        );
    }

    @Test
    public void getAllValidWords_2x2_dictOnlyContainsLength5(){
        solver = new BoggleSolver(new String[]{
                "ABCDA",
                "BCDAF",
                "ABCDE"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_2x2_dedupes(){
        solver = new BoggleSolver(new String[]{
                "ABCD", "ABCD"
        });
        board = new BoggleBoard(new char[][]{
                {'A', 'B'},
                {'C', 'D'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly("ABCD");
    }

    @Test
    public void getAllValidWords_onlyQs(){
        solver = new BoggleSolver(new String[]{
                "Q", "QQ", "QQQ", "QQQQ",
                "QU", "QUQU", "QUQUQU", "QUQUQUQU", "QUQUQUQUQU",
                "QUQ", "QUQUQ", "QUQUQUQ", "QUQUQUQUQ",
        });
        board = new BoggleBoard(new char[][]{
                {'Q', 'Q'},
                {'Q', 'Q'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly(
                 "QUQU", "QUQUQU", "QUQUQUQU"
        );
    }

    @Test
    public void getAllValidWords_queue(){
        solver = new BoggleSolver(new String[]{
                "QUEUE", "QEEU", "QU", "UEEE", "UQEU"
        });
        board = new BoggleBoard(new char[][]{
                {'Q', 'E'},
                {'E', 'U'}
        });
        assertThat(solver.getAllValidWords(board)).containsExactly("QUEUE");
    }

    ////////////////////////////////////////////////////////////////////////////
    // scoreOf()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void scoreOf_null_throws(){
        solver = new BoggleSolver(new String[]{"TEST"});
        solver.scoreOf(null);
    }

    @Test
    public void scoreOf_emptyString_0(){
        solver = new BoggleSolver(new String[]{"TEST", ""});
        assertThat(solver.scoreOf("")).isEqualTo(0);
    }

    @Test
    public void scoreOf_length1_0(){
        solver = new BoggleSolver(new String[]{"A", "B"});
        assertThat(solver.scoreOf("A")).isEqualTo(0);
    }

    @Test
    public void scoreOf_length2_0(){
        solver = new BoggleSolver(new String[]{"AA", "BA"});
        assertThat(solver.scoreOf("BA")).isEqualTo(0);
    }

    @Test
    public void scoreOf_length3_notInDict_0(){
        solver = new BoggleSolver(new String[]{"AA", "BA", "AAA"});
        assertThat(solver.scoreOf("BBB")).isEqualTo(0);
    }

    @Test
    public void scoreOf_length3_1(){
        solver = new BoggleSolver(new String[]{"AA", "BA", "AAA"});
        assertThat(solver.scoreOf("AAA")).isEqualTo(1);
    }

    @Test
    public void scoreOf_length4_1(){
        solver = new BoggleSolver(new String[]{"ABCD", "BA", "AAA"});
        assertThat(solver.scoreOf("ABCD")).isEqualTo(1);
    }

    @Test
    public void scoreOf_length5_2(){
        solver = new BoggleSolver(new String[]{
                "ABCDE"
        });
        assertThat(solver.scoreOf("ABCDE")).isEqualTo(2);
    }

    @Test
    public void scoreOf_length6_3(){
        solver = new BoggleSolver(new String[]{
                "ABCDEF"
        });
        assertThat(solver.scoreOf("ABCDEF")).isEqualTo(3);
    }
    @Test
    public void scoreOf_length7_5(){
        solver = new BoggleSolver(new String[]{
                "ABCDEFG"
        });
        assertThat(solver.scoreOf("ABCDEFG")).isEqualTo(5);
    }

    @Test
    public void scoreOf_length8_11(){
        solver = new BoggleSolver(new String[]{
                "ABCDEFGH"
        });
        assertThat(solver.scoreOf("ABCDEFGH")).isEqualTo(11);
    }

    @Test
    public void scoreOf_length10_11(){
        solver = new BoggleSolver(new String[]{
                "ABCDEFGHIJ"
        });
        assertThat(solver.scoreOf("ABCDEFGHIJ")).isEqualTo(11);
    }

    @Test
    public void scoreOf_length20_11(){
        solver = new BoggleSolver(new String[]{
                "ABCDEFGHIJABCDEFGHIJ"
        });
        assertThat(solver.scoreOf("ABCDEFGHIJABCDEFGHIJ")).isEqualTo(11);
    }

    @Test
    public void scoreOf_Q(){
        solver = new BoggleSolver(new String[]{
                "QU", "Q", "QUQU", "QUQUQU"
        });
        assertThat(solver.scoreOf("Q")).isEqualTo(0);
        assertThat(solver.scoreOf("QU")).isEqualTo(0);
        assertThat(solver.scoreOf("QUQU")).isEqualTo(1);
    }

}

