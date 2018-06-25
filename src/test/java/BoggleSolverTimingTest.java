import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StopwatchCPU;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.net.URL;

public class BoggleSolverTimingTest {
    @Rule
    public TestName testName = new TestName();
    private int numSolved = 0;
    private ClassLoader loader = getClass().getClassLoader();

    private String fileName(String file){
        URL url = loader.getResource("boggle/" + file);
        if (url == null) throw new IllegalArgumentException(
                loader.getResource("") + "boggle/" + file + " not found"
        );
        return url.getFile();
    }

    private void runTest(String dictName, boolean useHasbro){
        System.out.println(testName.getMethodName());

        // Calculate time to generate dict
        StopwatchCPU stopwatch = new StopwatchCPU();
        BoggleSolver solver = new BoggleSolver(new In(fileName(dictName)).readAllStrings());
        System.out.printf("Generating solver: %.4f seconds\n", stopwatch.elapsedTime());

        stopwatch = new StopwatchCPU();
        while(stopwatch.elapsedTime() < 20){
            if (useHasbro) solver.getAllValidWords(new BoggleBoard());
            else           solver.getAllValidWords(new BoggleBoard(10, 10));
            solver.getAllValidWords(new BoggleBoard());
            numSolved++;
        }

        double time = stopwatch.elapsedTime();
//        System.out.printf("Number of seconds elapsed: %.2f\n", time);
//        System.out.printf("Number of random boards solved in %s seconds: %s\n", time, numSolved);
        System.out.printf("Number of random boards solved per second: %.2f \n", (double) numSolved / time);
        System.out.println();
    }


    @Test
    public void four_2letters(){
        runTest("dictionary-2letters.txt", true);
    }

    @Test
    public void four_16q(){
        runTest("dictionary-16q.txt", true);
    }

    @Test
    public void four_nursery(){
        runTest("dictionary-nursery.txt", true);
    }

    @Test
    public void four_algs4(){
        runTest("dictionary-algs4.txt", true);
    }

    @Test
    public void four_common(){
        runTest("dictionary-common.txt", true);
    }

    @Test
    public void four_shakespeare(){
        runTest("dictionary-shakespeare.txt", true);
    }

    @Test
    public void four_enable2k(){
        runTest("dictionary-enable2k.txt", true);
    }

    @Test
    public void four_twl06(){
        runTest("dictionary-twl06.txt", true);
    }

    @Test
    public void four_yawl(){
        runTest("dictionary-yawl.txt", true);
    }

    @Test
    public void four_sowpods(){
        runTest("dictionary-sowpods.txt", true);
    }

    @Test
    public void ten_2letters(){
        runTest("dictionary-2letters.txt", false);
    }

    @Test
    public void ten_16q(){
        runTest("dictionary-16q.txt", false);
    }

    @Test
    public void ten_algs4(){
        runTest("dictionary-algs4.txt", false);
    }

    @Test
    public void ten_common(){
        runTest("dictionary-common.txt", false);
    }

    @Test
    public void ten_enable2k(){
        runTest("dictionary-enable2k.txt", false);
    }

    @Test
    public void ten_nursery(){
        runTest("dictionary-nursery.txt", false);
    }

    @Test
    public void ten_shakespeare(){
        runTest("dictionary-shakespeare.txt", false);
    }

    @Test
    public void ten_twl06(){
        runTest("dictionary-twl06.txt", false);
    }

    @Test
    public void ten_sowpods(){
        runTest("dictionary-sowpods.txt", false);
    }

    @Test
    public void ten_yawl(){
        runTest("dictionary-yawl.txt", false);
    }
}
