import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.Digraph;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

public class OutcastTest {
    @Test(expected = IllegalArgumentException.class)
    public void ctor_WordNetIsNull_throws(){
        new Outcast(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outcast_nounsAreNull_throws(){
        Digraph graph = new Digraph(1);
        WordNet wordNet = new WordNet(graph, new HashMap<>(), new HashMap<>());
        Outcast outcast = new Outcast(wordNet);
        outcast.outcast(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outcast_nounsIsArrayOfSizeZero_throws(){
        Digraph graph = new Digraph(1);
        WordNet wordNet = new WordNet(graph, new HashMap<>(), new HashMap<>());
        Outcast outcast = new Outcast(wordNet);
        outcast.outcast(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outcast_nounsIsArrayOfSizeOne_throws(){
        Digraph graph = new Digraph(1);
        WordNet wordNet = new WordNet(graph, new HashMap<>(), new HashMap<>());
        Outcast outcast = new Outcast(wordNet);
        outcast.outcast(new String[1]);
    }

    @Test
    public void outcast_allNounsAreIdentical_returnsAnyNoun(){
        Digraph graph = new Digraph(3);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, List<String>> vertexToNouns = new HashMap<>();

        graph.addEdge(0, 2);
        graph.addEdge(1, 2);

        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(1));
        nounToVertices.put("c", Lists.newArrayList(2));

        WordNet wordNet = new WordNet(graph, nounToVertices, vertexToNouns);
        Outcast outcast = new Outcast(wordNet);
        String[] nouns = new String[]{"a", "a", "a", "a"};

        assertThat(outcast.outcast(nouns)).isEqualTo("a");
    }

    @Test
    public void outcast_allNounsAreInSameSynset_returnsAnyNoun(){
        Digraph graph = new Digraph(3);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, List<String>> vertexToNouns = new HashMap<>();

        graph.addEdge(0, 2);
        graph.addEdge(1, 2);

        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(0));
        nounToVertices.put("c", Lists.newArrayList(0));
        nounToVertices.put("d", Lists.newArrayList(1));
        nounToVertices.put("e", Lists.newArrayList(2));

        WordNet wordNet = new WordNet(graph, nounToVertices, vertexToNouns);
        Outcast outcast = new Outcast(wordNet);
        String[] nouns = new String[]{"c", "b", "a"};

        assertThat(outcast.outcast(nouns)).isAnyOf("a", "b", "c");
    }

    @Test
    public void outcast_oneOutcast_returnsOutcast(){
        Digraph graph = new Digraph(5);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, List<String>> vertexToNouns = new HashMap<>();

        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(3, 2);
        graph.addEdge(4, 3);

        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(1));
        nounToVertices.put("c", Lists.newArrayList(2));
        nounToVertices.put("d", Lists.newArrayList(3));
        nounToVertices.put("e", Lists.newArrayList(4));

        WordNet wordNet = new WordNet(graph, nounToVertices, vertexToNouns);
        Outcast outcast = new Outcast(wordNet);
        String[] nouns = new String[]{"a", "b", "c", "d", "e"};

        assertThat(outcast.outcast(nouns)).isEqualTo("e");
    }

    @Test
    public void outcast_multipleOutcasts_returnsOutcast(){
        Digraph graph = new Digraph(7);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, List<String>> vertexToNouns = new HashMap<>();

        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(3, 2);
        graph.addEdge(4, 3);
        graph.addEdge(5, 2);
        graph.addEdge(6, 5);

        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(1));
        nounToVertices.put("c", Lists.newArrayList(2));
        nounToVertices.put("d", Lists.newArrayList(3));
        nounToVertices.put("e", Lists.newArrayList(4));
        nounToVertices.put("f", Lists.newArrayList(5));
        nounToVertices.put("g", Lists.newArrayList(6));

        WordNet wordNet = new WordNet(graph, nounToVertices, vertexToNouns);
        Outcast outcast = new Outcast(wordNet);
        String[] nouns = new String[]{"a", "b", "c", "d", "e", "f", "g"};

        assertThat(outcast.outcast(nouns)).isAnyOf("g", "e");
    }
}
