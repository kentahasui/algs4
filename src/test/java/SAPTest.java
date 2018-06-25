

/******************************************************************************
 *  Unit tests for SAP.java
 ******************************************************************************/
import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.Digraph;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
public class SAPTest {
    ////////////////////////////////////////////////////////////////////////////
    // Exception Testing
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////
    // Constructor
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void ctor_digraphIsNull_throwsException(){
        Digraph G = null;
        new SAP(G);
    }

    ////////////////////////////
    // length
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void length_vIsNull_throwsException(){
        Digraph G = new Digraph(1);
        SAP sap = new SAP(G);
        sap.length(null, new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void length_wIsNull_throwsException(){
        Digraph G = new Digraph(1);
        SAP sap = new SAP(G);
        sap.length(new ArrayList<>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void length_vIsTooSmall(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.length(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void length_wIsTooSmall(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.length(5, -2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void length_vIsTooLarge(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.length(10, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void length_wIsTooLarge(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.length(7, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void length_noVertexInVisInGraph(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        ArrayList<Integer> v = Lists.newArrayList(-1, -2, -3, 10, 11);
        ArrayList<Integer> w = Lists.newArrayList(1, 2, 3, 4);
        sap.length(v, w);
    }

    @Test(expected = IllegalArgumentException.class)
    public void length_noVertexInWisInGraph(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        ArrayList<Integer> v = Lists.newArrayList(1, 2, 3, 4);
        ArrayList<Integer> w = Lists.newArrayList(-1, -2, -3, 10, 11);
        sap.length(v, w);
    }


    ////////////////////////////
    // ancestor
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void ancestor_wIsNull_throwsException(){
        Digraph G = new Digraph(1);
        SAP sap = new SAP(G);
        sap.ancestor(new ArrayList<>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestor_vIsTooSmall(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.ancestor(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestor_wIsTooSmall(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.ancestor(5, -2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void ancestor_vIsTooLarge(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.ancestor(10, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestor_wIsTooLarge(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        sap.ancestor(7, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestor_noVertexInVisInGraph(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        ArrayList<Integer> v = Lists.newArrayList(-1, -2, -3, 10, 11);
        ArrayList<Integer> w = Lists.newArrayList(1, 2, 3, 4);
        sap.ancestor(v, w);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestor_noVertexInWisInGraph(){
        Digraph G = new Digraph(10);
        SAP sap = new SAP(G);
        ArrayList<Integer> v = Lists.newArrayList(1, 2, 3, 4);
        ArrayList<Integer> w = Lists.newArrayList(-1, -2, -3, 10, 11);
        sap.ancestor(v, w);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Immutability
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void sapIsImmutable(){
        Digraph G = new Digraph(5);
        G.addEdge(1, 0);
        G.addEdge(2, 0);
        SAP sap = new SAP(G);
        assertThat(sap.ancestor(1, 2)).isEqualTo(0);

        // Mutate graph
        G.addEdge(1, 2);
        assertThat(sap.ancestor(1, 2)).isEqualTo(0);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Ancestor: single vertex
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void ancestor_single_vAndWHaveDifferentAncestors_ancestorIsNegative(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(2, 3);
        G.addEdge(3, 2);
        SAP sap = new SAP(G);
        assertThat(sap.ancestor(0, 2)).isEqualTo(-1);
    }

    @Test
    public void ancestor_single_vwAreSameVertex_ancestorIsV(){
        Digraph G = new Digraph(3);
        SAP sap = new SAP(G);
        assertThat(sap.ancestor(1, 1)).isEqualTo(1);
    }

    @Test
    public void ancestor_vIsAncestorOfw_ancestorIsV(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(0, 2);

        G.addEdge(1, 2);
        G.addEdge(2, 1);

        G.addEdge(1, 4);
        G.addEdge(2, 3);

        G.addEdge(3, 4);
        G.addEdge(4, 3);


        SAP sap = new SAP(G);
        // 4 | 0->1->4
        assertThat(sap.ancestor(4, 0)).isEqualTo(4);
        // 3 | 2->3
        assertThat(sap.ancestor(3, 2)).isEqualTo(3);
    }

    @Test
    public void ancestor_single_wIsAncestorOfv_ancestorIsW(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(0, 2);

        G.addEdge(1, 2);

        G.addEdge(1, 4);
        G.addEdge(2, 3);

        G.addEdge(3, 4);
        G.addEdge(4, 3);


        SAP sap = new SAP(G);
        // 0->2->3 | 3
        assertThat(sap.ancestor(0, 3)).isEqualTo(3);
        // 1->4 | 4
        assertThat(sap.ancestor(1, 4)).isEqualTo(4);
    }

    @Test
    public void ancestor_single_multiplePathsToAncestor_choosesShortest(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(0, 4);
        SAP sap = new SAP(G);
        // 0->4 | 3->4
        assertThat(sap.ancestor(0, 3)).isEqualTo(4);
    }

    @Test
    public void ancestor_single_hasCycle_choosesShortestPath(){
        Digraph G = new Digraph(7);

        // Cycle 1
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 0);

        // Cycle 2
        G.addEdge(5, 6);
        G.addEdge(6, 5);

        // Root
        G.addEdge(2, 4);
        G.addEdge(5, 4);

        SAP sap = new SAP(G);
        // 6->5->4 | 3->0->1->2->4
        assertThat(sap.ancestor(6, 3)).isEqualTo(4);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Length: single vertex
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void length_single_vAndWHaveDifferentAncestors_ancestorIsNegative(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(2, 3);
        G.addEdge(3, 2);
        SAP sap = new SAP(G);
        // N/A
        assertThat(sap.length(0, 2)).isEqualTo(-1);
    }

    @Test
    public void length_single_vwAreSameVertex_ancestorIsV(){
        Digraph G = new Digraph(3);
        SAP sap = new SAP(G);
        // 1 | 1
        assertThat(sap.length(1, 1)).isEqualTo(0);
    }

    @Test
    public void length_vIsAncestorOfw_ancestorIsV(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(0, 2);

        G.addEdge(1, 2);
        G.addEdge(2, 1);

        G.addEdge(1, 4);
        G.addEdge(2, 3);

        G.addEdge(3, 4);
        G.addEdge(4, 3);


        SAP sap = new SAP(G);

        // 4 | 0->1->4
        assertThat(sap.length(4, 0)).isEqualTo(2);
        // 3 | 2->3
        assertThat(sap.length(3, 2)).isEqualTo(1);
    }

    @Test
    public void length_single_wIsAncestorOfv_ancestorIsW(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(0, 2);

        G.addEdge(1, 2);

        G.addEdge(1, 4);
        G.addEdge(2, 3);

        G.addEdge(3, 4);
        G.addEdge(4, 3);


        SAP sap = new SAP(G);
        // 0->2->3 | 3
        assertThat(sap.length(0, 3)).isEqualTo(2);
        // 1->4 | 4
        assertThat(sap.length(1, 4)).isEqualTo(1);
    }

    @Test
    public void length_single_multiplePathsToAncestor_choosesShortestPath(){
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(0, 4);
        SAP sap = new SAP(G);
        // 0->4 + 3->4
        assertThat(sap.length(0, 3)).isEqualTo(2);
    }

    @Test
    public void length_single_hasCycle_choosesShortestPath(){
        Digraph G = new Digraph(7);

        // Cycle 1
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 0);

        // Cycle 2
        G.addEdge(5, 6);
        G.addEdge(6, 5);

        // Root
        G.addEdge(2, 4);
        G.addEdge(5, 4);

        SAP sap = new SAP(G);
        // 6->5->4 + 3->0->1->2->4
        assertThat(sap.length(6, 3)).isEqualTo(6);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Ancestor: single vertex
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void ancestor_multi_vAndWHaveDifferentAncestors_returnsInvalidVertex(){
        Digraph G = new Digraph(9);

        // Vertices reachable from v
        G.addEdge(0, 3);
        G.addEdge(1, 3);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(4, 5);
        G.addEdge(5, 3);

        // Vertices reachable from w
        G.addEdge(6, 7);
        G.addEdge(6, 8);
        G.addEdge(7, 8);

        SAP sap = new SAP(G);
        // 6->5->4 + 3->0->1->2->4
        Iterable<Integer> v = Lists.newArrayList(0, 1, 2);
        Iterable<Integer> w = Lists.newArrayList(6, 7);
        assertThat(sap.length(v, w)).isEqualTo(-1);
    }

    @Test
    public void ancestor_multi_resetsWhenRunningWithDifferentVertices(){
        Digraph G = new Digraph(6);

        // Vertices reachable from w
        G.addEdge(0, 1);
        G.addEdge(2, 1);
        G.addEdge(3, 2);

        // Vertices reachable from v
        G.addEdge(4, 5);

        SAP sap = new SAP(G);
        Iterable<Integer> v = Lists.newArrayList(4);
        Iterable<Integer> w = Lists.newArrayList(0, 2);
        assertThat(sap.ancestor(v, w)).isEqualTo(-1);

        // 2 | 3->2
        v = Lists.newArrayList(3);
        assertThat(sap.ancestor(w, v)).isEqualTo(2);
    }

    @Test
    public void ancestor_multi_vIsInPathToW_ancestorIsV(){
        Digraph G = new Digraph(9);
        G.addEdge(0, 3);
        G.addEdge(1, 4);
        G.addEdge(2, 5);
        G.addEdge(5, 8);
        G.addEdge(6, 8);
        G.addEdge(7, 8);
        SAP sap = new SAP(G);
        Iterable<Integer> v = Lists.newArrayList(5, 6, 7);
        Iterable<Integer> w = Lists.newArrayList(0, 1, 2);
        // 5 | 2->5
        assertThat(sap.ancestor(v, w)).isEqualTo(5);
    }

    @Test
    public void ancestor_multi_wIsInPathToV_ancestorIsW(){
        Digraph G = new Digraph(9);
        G.addEdge(0, 3);
        G.addEdge(1, 4);
        G.addEdge(2, 5);
        G.addEdge(5, 8);
        G.addEdge(6, 8);
        G.addEdge(7, 8);
        SAP sap = new SAP(G);
        Iterable<Integer> v = Lists.newArrayList(0, 1, 2);
        Iterable<Integer> w = Lists.newArrayList(5, 6, 7);
        // 5 | 2->5
        assertThat(sap.ancestor(v, w)).isEqualTo(5);
    }

    @Test
    public void ancestor_multi_multiplePathsToSameAncestor_shortestPathChosen(){
        Digraph G = new Digraph(10);
        G.addEdge(0, 4);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(4, 5);
        SAP sap = new SAP(G);
        Iterable<Integer> v = Lists.newArrayList(0, 7);
        Iterable<Integer> w = Lists.newArrayList(3, 8);
        // 0->4 |  3->4
        assertThat(sap.ancestor(v, w)).isEqualTo(4);
    }

    @Test
    public void ancestor_multi_hasCycle(){
        Digraph G = new Digraph(11);
        // Cycle 1
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 0);
        G.addEdge(3, 4);

        // Cycle 2
        G.addEdge(5, 4);
        G.addEdge(5, 6);
        G.addEdge(6, 7);
        G.addEdge(7, 5);
        SAP sap = new SAP(G);

        Iterable<Integer> v = Lists.newArrayList(0, 1, 8);
        Iterable<Integer> w = Lists.newArrayList(9, 10, 6);
        // 0->4 |  3->4
        assertThat(sap.ancestor(v, w)).isEqualTo(4);

    }

}

