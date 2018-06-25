import edu.princeton.cs.algs4.Digraph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/** Helper methods for Week 6: WordNet */
public class WordNetTestUtils{

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
