

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
    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for isEmpty()
    ////////////////////////////////////////////////////////////////////////////
//    @Test
//    public void treeHasNoPoints_shouldBeEmpty(){
//        KdTree tree = new KdTree();
//        assertThat(tree.isEmpty()).isTrue();
//    }
//
//    @Test
//    public void treeHasOnePoint_shouldNotBeEmpty(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.1, 0.1));
//        assertThat(tree.isEmpty()).isFalse();
//    }
//
//    @Test
//    public void treeHasTwoPoints_shouldNotBeEmpty(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(0.1, 0.1));
//        assertThat(tree.isEmpty()).isFalse();
//    }
//
//    ////////////////////////////////////////////////////////////////////////////
//    // Unit tests for size()
//    ////////////////////////////////////////////////////////////////////////////
//    @Test
//    public void treeHasNoPoints_shouldHaveSizeZero(){
//        KdTree tree = new KdTree();
//        assertThat(tree.size()).isEqualTo(0);
//    }
//
//    @Test
//    public void treeHasOnePoint_shouldHaveSizeOne(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.1, 0.1));
//        assertThat(tree.size()).isEqualTo(1);    }
//
//    @Test
//    public void treeHasTwoPoints_shouldHasSizeTwo(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(0.1, 0.1));
//        assertThat(tree.size()).isEqualTo(2);
//    }
//
//    ////////////////////////////////////////////////////////////////////////////
//    // Unit tests for insert()
//    ////////////////////////////////////////////////////////////////////////////
//    @Test(expected = IllegalArgumentException.class)
//    public void insertNullPoint_shouldThrowException(){
//        KdTree tree = new KdTree();
//        tree.insert(null);
//    }
//
////    @Test(expected = IllegalArgumentException.class)
////    public void insertPointNotInUnitSquare_shouldThrowException(){
////        KdTree tree = new KdTree();
////        tree.insert(new Point2D(2, 2));
////    }
//
//    @Test
//    public void insertIntoEmptyTree_addsNewPoint(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.1, 0.2));
//        assertThat(tree.contains(new Point2D(0.1, 0.2))).isTrue();
//    }
//
//    @Test
//    public void insertIntoTree_addsNewPoint(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.1, 0.2));
//        assertThat(tree.contains(new Point2D(0.3, 0.4))).isFalse();
//        tree.insert(new Point2D(0.3, 0.4));
//        assertThat(tree.contains(new Point2D(0.3, 0.4))).isTrue();
//    }
//
//    @Test
//    public void insertExistingPoint_doesNothing(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.1, 0.1));
//        tree.insert(new Point2D(0.1, 0.1));
//        tree.insert(new Point2D(0.1, 0.1));
//        assertThat(tree.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void insertPointWithSameXCoordAsExisting_addsNewPoint(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.1, 0.2));
//        tree.insert(new Point2D(0.1, 0.9));
//        assertThat(tree.contains(new Point2D(0.1, 0.2))).isTrue();
//        assertThat(tree.contains(new Point2D(0.1, 0.9))).isTrue();
//        assertThat(tree.size()).isEqualTo(2);
//    }
//
//    @Test
//    public void insertPointWithSameYCoordAsExisting_addsNewPoint(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.2, 0.1));
//        tree.insert(new Point2D(0.9, 0.1));
//        assertThat(tree.contains(new Point2D(0.2, 0.1))).isTrue();
//        assertThat(tree.contains(new Point2D(0.9, 0.1))).isTrue();
//        assertThat(tree.size()).isEqualTo(2);
//    }
//
//    ////////////////////////////////////////////////////////////////////////////
//    // Unit tests for contains()
//    ////////////////////////////////////////////////////////////////////////////
//    @Test(expected = IllegalArgumentException.class)
//    public void containsOnNullPoint_shouldThrowException(){
//        KdTree tree = new KdTree();
//        tree.contains(null);
//    }
//
//    @Test
//    public void emptytree_shouldNotContainAnyPoint(){
//        KdTree tree = new KdTree();
//        assertThat(tree.contains(new Point2D(0.1, 0.1))).isFalse();
//    }
//
//    @Test
//    public void nonEmptytree_doesNotContainPoint_containsShouldBeFalse(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(1, 1));
//        assertThat(tree.contains(new Point2D(0.0, 1))).isFalse();
//    }
//
//    @Test
//    public void nonEmptyTree_containsPoint_containsShouldBeTrue(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(1, 1));
//        assertThat(tree.contains(new Point2D(0.0, 0.0))).isTrue();
//    }
//
//    @Test
//    public void treeDoesNotContainPoint_insertPoint_treeShouldContainPoint(){
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(0.0, 0.0));
//        assertThat(tree.contains(new Point2D(0.5, 0.5))).isFalse();
//        tree.insert(new Point2D(0.5, 0.5));
//        assertThat(tree.contains(new Point2D(0.5, 0.5))).isTrue();
//    }
//
//    ////////////////////////////////////////////////////////////////////////////
//    // Unit tests for range()
//    ////////////////////////////////////////////////////////////////////////////
//    @Test(expected = IllegalArgumentException.class)
//    public void rangeOnNullPoint_shouldThrowException(){
//        KdTree tree = new KdTree();
//        tree.range(null);
//    }
//
//    @Test
//    public void emptyTree_rangeShouldBeEmpty(){
//        KdTree tree = new KdTree();
//        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
//        assertThat(tree.range(rect)).isEmpty();
//    }
//
//    @Test
//    public void noPointsInRange_rangeShouldBeEmpty(){
//        KdTree tree = new KdTree();
//        RectHV rect = new RectHV(0.5, 0.5, 0.6, 0.6);
//        tree.insert(new Point2D(0.1, 0.1));
//        tree.insert(new Point2D(0.2, 0.2));
//        tree.insert(new Point2D(0.3, 0.3));
//        tree.insert(new Point2D(0.4, 0.4));
//        assertThat(tree.range(rect)).isEmpty();
//    }
//
//    @Test
//    public void rectIsUnitSquare_rangeShouldContainEveryPoint(){
//        KdTree tree = new KdTree();
//        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
//        tree.insert(new Point2D(0.1, 0.1));
//        tree.insert(new Point2D(0.2, 0.2));
//        tree.insert(new Point2D(0.3, 0.3));
//        tree.insert(new Point2D(0.4, 0.4));
//        tree.insert(new Point2D(0.5, 0.5));
//        assertThat(tree.range(rect)).containsExactly(
//                new Point2D(0.1, 0.1),
//                new Point2D(0.2, 0.2),
//                new Point2D(0.3, 0.3),
//                new Point2D(0.4, 0.4),
//                new Point2D(0.5, 0.5));
//    }
//
//    @Test
//    public void rangeShouldContainPointsOnBoundary(){
//        KdTree tree = new KdTree();
//        RectHV rect = new RectHV(0.0, 0.0, 0.7, 0.4);
//        tree.insert(new Point2D(0.3, 0.0));
//        assertThat(tree.range(rect)).containsExactly(
//                new Point2D(0.3, 0.0));
//    }
//
//    @Test
//    public void rangeShouldContainPointsInsideRectangle(){
//        KdTree tree = new KdTree();
//        RectHV rect = new RectHV(0.0, 0.0, 0.7, 0.7);
//        tree.insert(new Point2D(0.3, 0.4));
//        assertThat(tree.range(rect)).containsExactly(
//                new Point2D(0.3, 0.4));
//    }
//
//    ////////////////////////////////////////////////////////////////////////////
//    // Unit tests for nearest()
//    ////////////////////////////////////////////////////////////////////////////
//    @Test(expected = IllegalArgumentException.class)
//    public void nearestOnNullPoint_shouldThrowException(){
//        KdTree tree = new KdTree();
//        tree.nearest(null);
//    }
//
//    @Test
//    public void emptyTree_nearestShouldBeNull(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.1, 0.2);
//        assertThat(tree.nearest(point)).isNull();
//    }
//
//    @Test
//    public void nearestNeighborOfPoint_shouldBePointItself(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.1, 0.2);
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(0.1, 0.2));
//        tree.insert(new Point2D(1.0, 1.0));
//        assertThat(tree.nearest(point)).isEqualTo(point);
//    }
//
//    @Test
//    public void nearestNeighborOfTreeWithOnePoint_shouldBeTheOnlyPoint(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(1.0, 1.0);
//        tree.insert(new Point2D(0.0, 0.0));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.0, 0.0));
//    }
//
//    @Test
//    public void allPointsOnSameVerticalLine_closestShouldBeTheNeighbor(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.5, 0.5);
//        tree.insert(new Point2D(0.6, 0.0));
//        tree.insert(new Point2D(0.6, 0.4));
//        tree.insert(new Point2D(0.6, 0.5));
//        tree.insert(new Point2D(0.6, 0.6));
//        tree.insert(new Point2D(0.6, 1));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.6, 0.5));
//    }
//
//    @Test
//    public void allPointsOnSameHorizontalLine_closestShouldBeTheNeighbor(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.5, 0.5);
//        tree.insert(new Point2D(0.0, 0.8));
//        tree.insert(new Point2D(0.4, 0.8));
//        tree.insert(new Point2D(0.5, 0.8));
//        tree.insert(new Point2D(0.6, 0.8));
//        tree.insert(new Point2D(1.0, 0.8));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.5, 0.8));
//    }
//
//    @Test
//    public void twoPointsEquidistantFromQueryPoint_shouldBreakTiesByInsertionOrder(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.5, 0.5);
//        tree.insert(new Point2D(0.6, 0.6));
//        tree.insert(new Point2D(0.4, 0.4));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.6, 0.6));
//    }
//
//    @Test
//    public void twoPointsEquidistantFromQueryPoint_doesNotBreakTiesByDistanceToOrigin(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.5, 0.5);
//        tree.insert(new Point2D(0.6, 0.4));
//        tree.insert(new Point2D(0.4, 0.6));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.6, 0.4));
//    }
//
//    @Test
//    public void nearestPointOnOtherSideOfSplitLine_shouldReturnCorrectPoint(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.7, 0.7);
//        tree.insert(new Point2D(0.6, 0.0));
//        tree.insert(new Point2D(0.61, 0.01));
//        tree.insert(new Point2D(0.59, 0.7));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.59, 0.7));
//    }
//
//    @Test
//    public void nearestPointOnSameSideOfSplitLine_shouldReturnCorrectPoint(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.7, 0.7);
//        tree.insert(new Point2D(0.6, 0.0));
//        tree.insert(new Point2D(0.69, 0.69));
//        tree.insert(new Point2D(0.3, 0.4));
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.69, 0.69));
//    }
//
//    @Test
//    public void pointsClusteredInCorners_shouldReturnCorrectNearest(){
//        KdTree tree = new KdTree();
//        Point2D point = new Point2D(0.75, 0.75);
//
//        // Lower left
//        tree.insert(new Point2D(0.0, 0.0));
//        tree.insert(new Point2D(0.1, 0.1));
//        tree.insert(new Point2D(0.2, 0.2));
//
//        // Upper left
//        tree.insert(new Point2D(0.0, 1));
//        tree.insert(new Point2D(0.1, 0.9));
//
//        // Lower Right
//        tree.insert(new Point2D(0.9, 0.01));
//        tree.insert(new Point2D(0.8, 0.3));
//
//        // Upper Right
//        tree.insert(new Point2D(0.7, 0.7));
//        tree.insert(new Point2D(0.71, 0.71));
//        tree.insert(new Point2D(0.88, 0.88));
//
//        assertThat(tree.nearest(point)).isEqualTo(new Point2D(0.71, 0.71));
//    }
//
//    @Test
//    public void nearest_sameKeyAsNode_farther(){
//        KdTree tree = new KdTree();
//        Point2D query = new Point2D(0.6, 0.5);
//
//        // Root
//        tree.insert(new Point2D(0.5, 0.6));
//
//        // Same key as query, but distance is farther
//        tree.insert(new Point2D(0.99, 0.5));
//
//        // In left subtree: closest so far
//        tree.insert(new Point2D(0.61, 0.49));
//
//        // In right subtree: 2nd closest point
//        tree.insert(new Point2D(0.67, 0.55));
//
//        assertThat(tree.nearest(query)).isEqualTo(new Point2D(0.61, 0.49));
//    }
//
//    @Test
//    public void nearestNeighborOnOtherSideOfDividingLine(){
//        KdTree tree = new KdTree();
//        Point2D query = new Point2D(0.151, 0.500);
//        Point2D p1 = new Point2D(0.300, 0.600);
//        Point2D p2 = new Point2D(0.100, 0.500);
//
//        tree.insert(new Point2D(0.500, 0.500));
//        tree.insert(new Point2D(0.300, 0.600));
//        tree.insert(new Point2D(0.150, 0.150));
//        tree.insert(new Point2D(0.100, 0.500));
//        tree.insert(new Point2D(0.400, 0.700));
//        assertThat(tree.nearest(query)).isEqualTo(
//                new Point2D(0.100, 0.500));
//    }
//
//    @Test
//    public void nearestNeighbor_iterationOrder_6b(){
//        /*
//          A  0.0 0.25
//          B  0.125 0.5
//          C  0.5 0.375
//          D  0.625 0.0
//          E  0.375 1.0
//         */
//        KdTree tree = new KdTree();
//        Point2D query = new Point2D(0.875, 0.75);
//        tree.insert(new Point2D(0.0, 0.25));
//        tree.insert(new Point2D(0.125, 0.5));
//        tree.insert(new Point2D(0.5, 0.375));
//        tree.insert(new Point2D(0.625, 0.0));
//        tree.insert(new Point2D(0.375, 1.0));
//
//        Point2D nearest = tree.nearest(query);
//        assertThat(nearest).isEqualTo(new Point2D(0.5, 0.375));
//
//    }
//
//    @Test
//    public void nearestNeighbor_iterationOrder_6a(){
//        /*
//          A  0.7 0.2
//          B  0.5 0.4
//          C  0.2 0.3
//          D  0.4 0.7
//          E  0.9 0.6
//         */
//        KdTree tree = new KdTree();
//        Point2D query = new Point2D(0.98, 0.91);
//        tree.insert(new Point2D(0.7, 0.2));
//        tree.insert(new Point2D(0.5, 0.4));
//        tree.insert(new Point2D(0.2, 0.3));
//        tree.insert(new Point2D(0.4, 0.7));
//        tree.insert(new Point2D(0.9, 0.6));
//
//        Point2D nearest = tree.nearest(query);
//        assertThat(nearest).isEqualTo(new Point2D(0.9, 0.6));
//
//    }

}

