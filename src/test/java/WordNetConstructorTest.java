/******************************************************************************
 *  Unit tests for WordNet.java's constructor
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static com.google.common.truth.Truth.assertThat;

public class WordNetConstructorTest {
    In synsetsIn;
    In hypernymsIn;

//    @Before
//    public void setUp(){
//        synsetsIn = Mockito.mock(In.class);
//        hypernymsIn = Mockito.mock(In.class);
//    }
//    ////////////////////////////
//    // Constructor: In
//    ////////////////////////////
//    @Test(expected = IllegalArgumentException.class)
//    public void synsetsAndHypernymsAreBothEmpty_throwsException_graphNotRooted(){
//        Mockito.when(synsetsIn.hasNextLine()).thenReturn(false);
//        Mockito.when(hypernymsIn.hasNextLine()).thenReturn(false);
//        new WordNet(synsetsIn, hypernymsIn);
//    }
//
//    @Test
//    public void oneSynsetAndNoHypernyms_noException(){
//        Mockito.when(synsetsIn.hasNextLine()).thenReturn(true, false);
//        Mockito.when(synsetsIn.readLine()).thenReturn("0,a,one");
//        Mockito.when(hypernymsIn.hasNextLine()).thenReturn(false);
//        new WordNet(synsetsIn, hypernymsIn);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void twoSynsetsAndNoHypernyms_throws_graphNotRooted(){
//        Mockito.when(synsetsIn.hasNextLine()).thenReturn(
//                true, true, false);
//        Mockito.when(synsetsIn.readLine()).thenReturn(
//                "0,a,one",
//                "1,b,two");
//        Mockito.when(hypernymsIn.hasNextLine()).thenReturn(false);
//        new WordNet(synsetsIn, hypernymsIn);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void rootedGraphButHasCycle_throws_graphHasCycle(){
//        // Mock synsets
//        Mockito.when(synsetsIn.hasNextLine()).thenReturn(
//                true, true, true, false);
//        Mockito.when(synsetsIn.readLine()).thenReturn(
//                "0,a,one",
//                "1,b,two",
//                "2,c,three");
//
//        // Mock hypernyms
//        Mockito.when(hypernymsIn.hasNextLine()).thenReturn(true, true, false);
//        Mockito.when(hypernymsIn.readLine()).thenReturn(
//                "0,1,2",
//                "1,0,2");
//
//        // Construct WordNet
//        new WordNet(synsetsIn, hypernymsIn);
//    }
//
//    @Test
//    public void sameNounInMultipleSynsets(){
//        // Mock synsets
//        Mockito.when(synsetsIn.hasNextLine()).thenReturn(
//                true, true, true, false);
//        Mockito.when(synsetsIn.readLine()).thenReturn(
//                "0,a,one",
//                "1,a,two",
//                "2,c,three");
//
//        // Mock hypernyms
//        Mockito.when(hypernymsIn.hasNextLine()).thenReturn(
//                true, true, false);
//        Mockito.when(hypernymsIn.readLine()).thenReturn(
//                "0,2",
//                "1,2");
//
//        WordNet wordNet = new WordNet(synsetsIn, hypernymsIn);
//        assertThat(wordNet.nouns()).containsExactly("a", "c");
//    }
//
//    @Test
//    public void oneSynsetHasMultipleNouns(){
//        // Mock synsets
//        Mockito.when(synsetsIn.hasNextLine()).thenReturn(
//                true, true, true, false);
//        Mockito.when(synsetsIn.readLine()).thenReturn(
//                "0,a b c,one",
//                "1,d e f,two",
//                "2,g h i j,three");
//        // Mock hypernyms
//        Mockito.when(hypernymsIn.hasNextLine()).thenReturn(
//                true, true, false);
//        Mockito.when(hypernymsIn.readLine()).thenReturn(
//                "0,2",
//                "1,2");
//        WordNet wordNet = new WordNet(synsetsIn, hypernymsIn);
//        assertThat(wordNet.nouns()).containsExactly(
//                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
//
//        assertThat(wordNet.sap("b", "e")).isAnyOf("g", "h", "i", "j");
//    }
}
