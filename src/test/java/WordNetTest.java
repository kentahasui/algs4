/******************************************************************************
 *  Unit tests for WordNet.java
 ******************************************************************************/

import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.Digraph;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

public class WordNetTest {
    ////////////////////////////////////////////////////////////////////////////
    // Exception Testing
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////
    // Constructor: FileNames
    ////////////////////////////
    @Test(expected =  IllegalArgumentException.class)
    public void ctor_hypernymsAndSynsetsAreEmpty_throwsException(){
        String synsets = null;
        String hypernyms = null;
        new WordNet(synsets, hypernyms);
    }

    ////////////////////////////
    // Constructor: Digraph
    ////////////////////////////
    @Test(expected = Exception.class)
    public void ctor_gIsEmpty_throwsException_treeHasNoRoot(){
        Digraph g = new Digraph(0);
        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    @Test
    public void ctor_gIsSingleVertex_noException(){
        Digraph g = new Digraph(1);
        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    @Test(expected = Exception.class)
    public void ctor_gIsRooted_gHasCycle_throwsException(){
        Digraph g = new Digraph(3);
        // Edges to root
        g.addEdge(0, 2);
        g.addEdge(1, 2);

        // Cycle
        g.addEdge(0, 1);
        g.addEdge(1, 0);
        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    @Test(expected = Exception.class)
    public void ctor_gIsNotRooted_gHasCycle_throwsException(){
        Digraph g = new Digraph(4);
        // Cycle
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    @Test(expected = Exception.class)
    public void ctor_gHasNoRoot_throwsException(){
        Digraph g = new Digraph(5);
        // Connected component 1
        g.addEdge(1, 0);

        // Connected component 2
        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(4, 2);

        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    @Test(expected = Exception.class)
    public void ctor_gHasTwoRoots_throwsException(){
        Digraph g = new Digraph(6);
        // 0 is first root
        g.addEdge(1, 0);

        // 5 is second root
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);

        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    @Test(expected = Exception.class)
    public void ctor_gHasNoEdges_gHasMoreThanOneVertex_throwsException(){
        Digraph g = new Digraph(2);
        WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
    }

    //////////////////////////////Map<Integer, String> vertexToNouns//////////////////////////////////////////////
    // isNoun
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void isNoun_null_throws(){
        Digraph g = new Digraph(1);
        WordNet wordNet = WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
        wordNet.isNoun(null);
    }

    @Test
    public void isNoun_oneVertex(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.isNoun("a")).isTrue();
        assertThat(wordNet.isNoun("b")).isFalse();
        assertThat(wordNet.isNoun("1")).isFalse();
    }

    @Test
    public void isNoun_multipleVertices_(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(1));
        Map<Integer, String> vertexToNouns = new HashMap<>();
        vertexToNouns.put(0, "a");
        vertexToNouns.put(1, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.isNoun("a")).isTrue();
        assertThat(wordNet.isNoun("b")).isTrue();
        assertThat(wordNet.isNoun("1")).isFalse();
    }

    @Test
    public void isNoun_multipleVerticesInSameSynset(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        nounToVertices.put("a", Lists.newArrayList(0, 1, 2));
        vertexToNouns.put(0, "a");
        vertexToNouns.put(1, "a");
        vertexToNouns.put(2, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.isNoun("a")).isTrue();
    }

    @Test
    public void isNoun_nounIsNotInGraph_returnsFalse(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.isNoun("A")).isFalse();
    }

    ////////////////////////////////////////////////////////////////////////////
    // nouns
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void nouns_oneVertex_returnsCollectionWithOneNoun(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.nouns()).containsExactly("a");
    }

    @Test
    public void nouns_multipleVertices_returnsAllVertices(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(1));
        Map<Integer, String> vertexToNouns = new HashMap<>();
        vertexToNouns.put(0, "a");
        vertexToNouns.put(1, "b");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.nouns()).containsExactly("a", "b");
    }

    @Test
    public void nouns_multipleVerticesInSameSynset_dedupesNouns(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0, 1, 2));
        Map<Integer, String> vertexToNouns = new HashMap<>();
        vertexToNouns.put(0, "a");
        vertexToNouns.put(1, "a");
        vertexToNouns.put(2, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.nouns()).containsExactly("a");
    }

    ////////////////////////////////////////////////////////////////////////////
    // distance
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void distance_nounsAreNull_throws(){
        Digraph g = new Digraph(1);
        WordNet wordNet = WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
        wordNet.distance(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void distance_nounAIsNotNoun_throws(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        wordNet.distance("b", "a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void distance_nounBIsNotNoun_throws(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        Map<Integer, String> vertexToNouns = new HashMap<>();
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        wordNet.distance("a", "b");
    }

    @Test
    public void distance_aAndBAreSameNoun_returnsZero(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.distance("a", "a")).isEqualTo(0);
    }

    @Test
    public void distance_aAndBAreInSameSynset_returnsZero() {
        Digraph g = new Digraph(2);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // Hypernyms row 0: 0,1
        g.addEdge(0, 1);

        // Synsets Row 1: 0,a b,Vertex zero
        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(0));
        vertexToNouns.put(0, "a b");

        // Row 2: 1,c,Vertex two
        nounToVertices.put("c", Lists.newArrayList(1));

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.distance("a", "b")).isEqualTo(0);
    }

    @Test
    public void distance_bOnPathOfA_distanceBetweenAAndB(){
        Digraph g = new Digraph(6);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // 0,2
        g.addEdge(0, 2);
        // 1,2,3
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        // 2,3
        g.addEdge(2, 3);
        // 3,5
        g.addEdge(3, 5);
        // 4,3,5
        g.addEdge(4, 3);
        g.addEdge(4, 5);

        // 0,a,
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        // 1,b
        nounToVertices.put("b", Lists.newArrayList(1));
        vertexToNouns.put(1, "b");
        // 2,c
        nounToVertices.put("c", Lists.newArrayList(2));
        vertexToNouns.put(2, "c");
        // 3,d
        nounToVertices.put("d", Lists.newArrayList(3));
        vertexToNouns.put(3, "d");
        // 4,e
        nounToVertices.put("e", Lists.newArrayList(4));
        vertexToNouns.put(4, "e");
        // 5,f
        nounToVertices.put("f", Lists.newArrayList(5));
        vertexToNouns.put(5, "f");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // a->c->d
        assertThat(wordNet.distance("a", "d")).isEqualTo(2);
        // b->d->f
        assertThat(wordNet.distance("b", "f")).isEqualTo(2);
        // b->d
        assertThat(wordNet.distance("b", "d")).isEqualTo(1);
    }

    @Test
    public void distance_AOnPathOfB_distanceBetweenAAndB(){
        Digraph g = new Digraph(6);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // 0,2
        g.addEdge(0, 2);
        // 1,2,3
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        // 2,3
        g.addEdge(2, 3);
        // 3,5
        g.addEdge(3, 5);
        // 4,3,5
        g.addEdge(4, 3);
        g.addEdge(4, 5);

        // 0,a,
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        // 1,b
        nounToVertices.put("b", Lists.newArrayList(1));
        vertexToNouns.put(1, "b");
        // 2,c
        nounToVertices.put("c", Lists.newArrayList(2));
        vertexToNouns.put(2, "c");
        // 3,d
        nounToVertices.put("d", Lists.newArrayList(3));
        vertexToNouns.put(3, "d");
        // 4,e
        nounToVertices.put("e", Lists.newArrayList(4));
        vertexToNouns.put(4, "e");
        // 5,f
        nounToVertices.put("f", Lists.newArrayList(5));
        vertexToNouns.put(5, "f");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // a->c->d
        assertThat(wordNet.distance("d", "a")).isEqualTo(2);
        // b->d->f
        assertThat(wordNet.distance("f", "b")).isEqualTo(2);
        // b->d
        assertThat(wordNet.distance("d", "b")).isEqualTo(1);
    }

    @Test
    public void distance_oneCommonAncestor_distanceToAncestor(){
        Digraph g = new Digraph(6);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // 0,2
        g.addEdge(0, 2);
        // 1,2,3
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        // 2,3
        g.addEdge(2, 3);
        // 3,5
        g.addEdge(3, 5);
        // 4,3,5
        g.addEdge(4, 3);
        g.addEdge(4, 5);

        // 0,a,
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        // 1,b
        nounToVertices.put("b", Lists.newArrayList(1));
        vertexToNouns.put(1, "b");
        // 2,c
        nounToVertices.put("c", Lists.newArrayList(2));
        vertexToNouns.put(2, "c");
        // 3,d
        nounToVertices.put("d", Lists.newArrayList(3));
        vertexToNouns.put(3, "d");
        // 4,e
        nounToVertices.put("e", Lists.newArrayList(4));
        vertexToNouns.put(4, "e");
        // 5,f
        nounToVertices.put("f", Lists.newArrayList(5));
        vertexToNouns.put(5, "f");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // a->c->d | e->d
        assertThat(wordNet.distance("a", "e")).isEqualTo(3);
        // b->c | a->c
        assertThat(wordNet.distance("b", "a")).isEqualTo(2);
    }

    @Test
    public void distance_synsetsHaveDifferentClosestAncestors_choosesShortestPath(){
        Digraph g = new Digraph(5);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        g.addEdge(0, 2);
        g.addEdge(2, 4);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        nounToVertices.put("a", Lists.newArrayList(0, 2));
        vertexToNouns.put(0, "a");
        vertexToNouns.put(2, "a");
        nounToVertices.put("b", Lists.newArrayList(1, 3));
        vertexToNouns.put(1, "b");
        vertexToNouns.put(3, "b");
        nounToVertices.put("c", Lists.newArrayList(4));
        vertexToNouns.put(4, "c");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // 2->4 | 3->4
        // a->c | b->c
        assertThat(wordNet.distance("a", "b")).isEqualTo(2);
    }

    ////////////////////////////////////////////////////////////////////////////
    // sap
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void sap_nounsAreNull_throws(){
        Digraph g = new Digraph(1);
        WordNet wordNet = WordNetTestUtils.newWordNet(g, new HashMap<>(), new HashMap<>());
        wordNet.sap(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sap_nounAIsNotNoun_throws(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(1));
        Map<Integer, String> vertexToNouns = new HashMap<>();
        vertexToNouns.put(1, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        wordNet.sap("b", "a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void sap_nounBIsNotNoun_throws(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(1));
        Map<Integer, String> vertexToNouns = new HashMap<>();
        vertexToNouns.put(1, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        wordNet.sap("a", "b");
    }

    @Test
    public void sap_aAndBAreSameNoun_returnsEitherOfNouns(){
        Digraph g = new Digraph(1);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.sap("a", "a")).isEqualTo("a");
    }

    @Test
    public void sap_aAndBAreInSameSynset_returnsAnyInSynset() {
        Digraph g = new Digraph(2);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // Hypernyms row 0: 0,1
        g.addEdge(0, 1);

        // Synsets Row 1: 0,a b,Vertex zero
        nounToVertices.put("a", Lists.newArrayList(0));
        nounToVertices.put("b", Lists.newArrayList(0));
        vertexToNouns.put(0, "a b");

        // Row 2: 1,c,Vertex two
        nounToVertices.put("c", Lists.newArrayList(1));

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        assertThat(wordNet.sap("a", "b")).isEqualTo("a b");
    }

    @Test
    public void sap_bOnPathOfA_returnsB(){
        Digraph g = new Digraph(6);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // 0,2
        g.addEdge(0, 2);
        // 1,2,3
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        // 2,3
        g.addEdge(2, 3);
        // 3,5
        g.addEdge(3, 5);
        // 4,3,5
        g.addEdge(4, 3);
        g.addEdge(4, 5);

        // 0,a,
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        // 1,b
        nounToVertices.put("b", Lists.newArrayList(1));
        vertexToNouns.put(1, "b");
        // 2,c
        nounToVertices.put("c", Lists.newArrayList(2));
        vertexToNouns.put(2, "c");
        // 3,d
        nounToVertices.put("d", Lists.newArrayList(3));
        vertexToNouns.put(3, "d");
        // 4,e
        nounToVertices.put("e", Lists.newArrayList(4));
        vertexToNouns.put(4, "e");
        // 5,f
        nounToVertices.put("f", Lists.newArrayList(5));
        vertexToNouns.put(5, "f");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // a->c->d
        assertThat(wordNet.sap("a", "d")).isEqualTo("d");
        // b->d->f
        assertThat(wordNet.sap("b", "f")).isEqualTo("f");
        // b->d
        assertThat(wordNet.sap("b", "d")).isEqualTo("d");
    }

    @Test
    public void sap_AOnPathOfB_returnsA(){
        Digraph g = new Digraph(6);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // 0,2
        g.addEdge(0, 2);
        // 1,2,3
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        // 2,3
        g.addEdge(2, 3);
        // 3,5
        g.addEdge(3, 5);
        // 4,3,5
        g.addEdge(4, 3);
        g.addEdge(4, 5);

        // 0,a,
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        // 1,b
        nounToVertices.put("b", Lists.newArrayList(1));
        vertexToNouns.put(1, "b");
        // 2,c
        nounToVertices.put("c", Lists.newArrayList(2));
        vertexToNouns.put(2, "c");
        // 3,d
        nounToVertices.put("d", Lists.newArrayList(3));
        vertexToNouns.put(3, "d");
        // 4,e
        nounToVertices.put("e", Lists.newArrayList(4));
        vertexToNouns.put(4, "e");
        // 5,f
        nounToVertices.put("f", Lists.newArrayList(5));
        vertexToNouns.put(5, "f");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // a->c->d
        assertThat(wordNet.sap("d", "a")).isEqualTo("d");
        // b->d->f
        assertThat(wordNet.sap("f", "b")).isEqualTo("f");
        // b->d
        assertThat(wordNet.sap("d", "b")).isEqualTo("d");
    }

    @Test
    public void sap_oneCommonAncestor_returnsAncestor(){
        Digraph g = new Digraph(6);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        // 0,2
        g.addEdge(0, 2);
        // 1,2,3
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        // 2,3
        g.addEdge(2, 3);
        // 3,5
        g.addEdge(3, 5);
        // 4,3,5
        g.addEdge(4, 3);
        g.addEdge(4, 5);

        // 0,a,
        nounToVertices.put("a", Lists.newArrayList(0));
        vertexToNouns.put(0, "a");
        // 1,b
        nounToVertices.put("b", Lists.newArrayList(1));
        vertexToNouns.put(1, "b");
        // 2,c
        nounToVertices.put("c", Lists.newArrayList(2));
        vertexToNouns.put(2, "c");
        // 3,d
        nounToVertices.put("d", Lists.newArrayList(3));
        vertexToNouns.put(3, "d");
        // 4,e
        nounToVertices.put("e", Lists.newArrayList(4));
        vertexToNouns.put(4, "e");
        // 5,f
        nounToVertices.put("f", Lists.newArrayList(5));
        vertexToNouns.put(5, "f");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // a->c->d | e->d
        assertThat(wordNet.sap("a", "e")).isEqualTo("d");
        // b->c | a->c
        assertThat(wordNet.sap("b", "a")).isEqualTo("c");
    }

    @Test
    public void sap_synsetsHaveDifferentClosestAncestors_usesClosestSynsetToAncestor(){
        Digraph g = new Digraph(5);
        Map<String, List<Integer>> nounToVertices = new HashMap<>();
        Map<Integer, String> vertexToNouns = new HashMap<>();

        g.addEdge(0, 2);
        g.addEdge(2, 4);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        nounToVertices.put("a", Lists.newArrayList(0, 2));
        vertexToNouns.put(0, "a");
        vertexToNouns.put(2, "a");
        nounToVertices.put("b", Lists.newArrayList(1, 3));
        vertexToNouns.put(1, "b");
        vertexToNouns.put(3, "b");
        nounToVertices.put("c", Lists.newArrayList(4));
        vertexToNouns.put(4, "c");

        WordNet wordNet = WordNetTestUtils.newWordNet(g, nounToVertices, vertexToNouns);
        // 2->4 | 3->4
        // a->c | b->c
        assertThat(wordNet.sap("a", "b")).isEqualTo("c");
    }

}
