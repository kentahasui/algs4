import edu.princeton.cs.algs4.Digraph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/** Helper methods for Week 6: WordNet */
public class WordNetTestUtils{

//    @Test
//    public void testReflection() throws NoSuchElementException, NoSuchMethodException {
//        Constructor<WordNet> constructor = WordNet.class.getDeclaredConstructor(Digraph.class, Map.class, Map.class);
//        constructor.newInstance()
//
//
//        Constructor[] ctors = WordNet.class.getDeclaredConstructors();
//
//        for (Constructor ctor : ctors){
//            System.out.println(ctor);
//            System.out.println(ctor.getParameterCount());
//        }
//    }

    public static WordNet newWordNet(Digraph g,
                                     Map<String, List<Integer>> nounToVertices,
                                     Map<Integer, String> vertexToNouns){
        try {
            // Create public constructor
            Constructor<WordNet> constructor = WordNet.class.getDeclaredConstructor(
                    Digraph.class, Map.class, Map.class);
            constructor.setAccessible(true);
            return constructor.newInstance(g, nounToVertices, vertexToNouns);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex){
            throw new RuntimeException(ex);
        }
    }
}
