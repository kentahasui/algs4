import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.net.URL;

import static com.google.common.truth.Truth.assertThat;

public class BoggleSolverFileTest {
    private BoggleSolver solver;
    private BoggleBoard board;
    private ClassLoader loader = getClass().getClassLoader();
    private String fileName(String file){
        URL url = loader.getResource("boggle/" + file);
        if (url == null) throw new IllegalArgumentException(
                loader.getResource("") + "boggle/" + file + " not found"
        );
        return url.getFile();
    }

    private BoggleSolver newSolver(String fileName){
        In in = new In(fileName(fileName));
        return new BoggleSolver(in.readAllStrings());
    }

    private BoggleBoard newBoggleBoard(String fileName){
        return new BoggleBoard(fileName(fileName));
    }

    private int getScore(Iterable<String> words){
        int score = 0;
        for (String word : words) {
            score += solver.scoreOf(word);
        }
        return score;
    }

    @Test
    public void getAllValidWords_dictionary_2letters(){
        solver = newSolver("dictionary-2letters.txt");
        board = newBoggleBoard("board-points5.txt");
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_points0(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points0.txt");
        assertThat(solver.getAllValidWords(board)).isEmpty();
    }

    @Test
    public void getAllValidWords_points1(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points1.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).containsExactly("TWP");
        assertThat(getScore(words)).isEqualTo(1);
    }

    @Test
    public void getAllValidWords_points2(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points2.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(2);
    }

    @Test
    public void getAllValidWords_points3(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points3.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(3);
    }

    @Test
    public void getAllValidWords_points4(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points4.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(4);
    }

    @Test
    public void getAllValidWords_points5(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points5.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(5);
    }

//    @Test
//    public void getAllValidWords_16q(){
//        solver = newSolver("dictionary-16q.txt");
//        board = newBoggleBoard("board-16q.txt");
//        Iterable<String> words = solver.getAllValidWords(board);
//        assertThat(words).contains("QUQUQU");
//    }

    @Test
    public void getAllValidWords_antidisestablishmentarianisms(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-antidisestablishmentarianisms.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("ANTIDISESTABLISHMENTARIANISMS");
    }

    @Test
    public void getAllValidWords_aqua(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-aqua.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).doesNotContain("AQUA");
    }

    @Test
    public void getAllValidWords_couscous(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-couscous.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("COUSCOUS");
    }

    @Test
    public void getAllValidWords_diagonal(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-diagonal.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("THEN");
    }

    @Test
    public void getAllValidWords_dichlorodiphenyltrichloroethanes(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-dichlorodiphenyltrichloroethanes.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("DICHLORODIPHENYLTRICHLOROETHANES");
    }

    @Test
    public void getAllValidWords_dodo(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-dodo.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("DODO");
    }

    @Test
    public void getAllValidWords_estrangers(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-estrangers.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("ESTRANGERS");
    }

    @Test
    public void getAllValidWords_horizontal(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-horizontal.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("DATA");
    }

    @Test
    public void getAllValidWords_inconsequentially(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-inconsequentially.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("INCONSEQUENTIALLY");
    }

    @Test
    public void getAllValidWords_noon(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-noon.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("NOON");
    }

    @Test
    public void getAllValidWords_pneumonoultramicroscopicsilicovolcanoconiosis(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-pneumonoultramicroscopicsilicovolcanoconiosis.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("PNEUMONOULTRAMICROSCOPICSILICOVOLCANOCONIOSIS");
    }

    @Test
    public void getAllValidWords_quinquevalencies(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-quinquevalencies.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("QUINQUEVALENCIES");
    }

    @Test
    public void getAllValidWords_qwerty(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-qwerty.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).doesNotContain("QWERTY");
    }

    @Test
    public void getAllValidWords_rotavator(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-rotavator.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("ROTAVATOR");
    }

    @Test
    public void getAllValidWords_vertical(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-vertical.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(words).contains("TREE");
        assertThat(words).contains("NODE");
    }

    @Test
    public void getAllValidWords_points100(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points100.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(100);
    }

    @Test
    public void getAllValidWords_points200(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points200.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(200);
    }

    @Test
    public void getAllValidWords_points300(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points300.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(300);
    }

    @Test
    public void getAllValidWords_points400(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points400.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(400);
    }

    @Test
    public void getAllValidWords_points500(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points500.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(500);
    }

    @Test
    public void getAllValidWords_points750(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points750.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(750);
    }

    @Test
    public void getAllValidWords_points777(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points777.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(777);
    }

    @Test
    public void getAllValidWords_points1000(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points1000.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(1000);
    }

    @Test
    public void getAllValidWords_points1111(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points1111.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(1111);
    }

    @Test
    public void getAllValidWords_points1250(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points1250.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(1250);
    }

    @Test
    public void getAllValidWords_points1500(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points1500.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(1500);
    }

    @Test
    public void getAllValidWords_points2000(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points2000.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(2000);
    }

    @Test
    public void getAllValidWords_points4410(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points4410.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(4410);
    }

    @Test
    public void getAllValidWords_points4527(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points4527.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(4527);
    }

    @Test
    public void getAllValidWords_points4540(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points4540.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(4540);
    }

    @Test
    public void getAllValidWords_points13464(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points13464.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(13464);
    }

    @Test
    public void getAllValidWords_points26539(){
        solver = newSolver("dictionary-yawl.txt");
        board = newBoggleBoard("board-points26539.txt");
        Iterable<String> words = solver.getAllValidWords(board);
        assertThat(getScore(words)).isEqualTo(26539);
    }
}
