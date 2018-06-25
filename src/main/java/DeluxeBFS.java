/******************************************************************************
 *  Compilation:  javac DeluxeBFS.java
 *  Execution:    java DeluxeBFS
 *  Dependencies:
 *
 *  Defines the DeluxeBFS class, which uses an optimized version of Breadth-First
 *  search.
 *
 *  You must call the bfs() method before calling either getLength() or
 *  getAncestor(). Otherwise the behavior is undefined.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.HashSet;
import java.util.Set;

public class DeluxeBFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int INVALID_VERTEX = -1;

    /* Digraph on which to run BFS in lockstep */
    private final Digraph G;

    /* Distance from v */
    private final int[] vDistTo;
    /* Distance from w */
    private final int[] wDistTo;

    /* Vertices marked while running BFS from v */
    private final Set<Integer> vMarked;
    /* Vertices marked while running BFS from w */
    private final Set<Integer> wMarked;

    private int ancestor;
    private int length;

    public DeluxeBFS(Digraph G) {
        this.G = G;
        vDistTo = new int[G.V()];
        wDistTo = new int[G.V()];

        vMarked = new HashSet<>();
        wMarked = new HashSet<>();
    }

    /**
     * Returns length of shortest ancestral path, or Integer.MAX_VALUE if none exists
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns length of closest common ancestor, or -1 if none exists
     */
    public int getAncestor() {
        return ancestor;
    }

    /**
     * Runs BFS in lockstep for two individual source vertices.
     * Quits when any of the below occurs:
     * <ul>
     * <li>A vertex that is accessible from both v and w is found</li>
     * <li>There are no more vertices on path from v AND on no more vertices on path from w</li>
     * </ul>
     *
     * @param v A vertex
     * @param w Another vertex
     */
    public void bfs(int v, int w) {
        // Initialize the search
        vMarked.clear();
        wMarked.clear();
        ancestor = INVALID_VERTEX;
        length = INFINITY;
        Queue<Integer> vQ = new Queue<>();
        Queue<Integer> wQ = new Queue<>();

        // Start search on v
        vMarked.add(v);
        vDistTo[v] = 0;
        vQ.enqueue(v);

        // Start search on w
        wDistTo[w] = 0;
        wMarked.add(w);
        wQ.enqueue(w);

        // Edge case: v and w are same vertex.
        if (v == w) {
            ancestor = v;
            length = 0;
            return;
        }

        // Keep searching while the queues are not empty
        while (!vQ.isEmpty() || !wQ.isEmpty()) {
            processNextVertexOnQueue(vQ, vMarked, wMarked, vDistTo, wDistTo);
            processNextVertexOnQueue(wQ, wMarked, vMarked, wDistTo, vDistTo);
        }
    }

    /**
     * Runs BFS in lockstep for two sets of source vertices.
     */
    public void bfs(Iterable<Integer> allV, Iterable<Integer> allW) {
        // Initialize the search
        vMarked.clear();
        wMarked.clear();
        ancestor = INVALID_VERTEX;
        length = INFINITY;
        Queue<Integer> vQ = new Queue<>();
        Queue<Integer> wQ = new Queue<>();

        // Initialize v
        for (int v : allV) {
            vMarked.add(v);
            vDistTo[v] = 0;
            vQ.enqueue(v);
        }

        // Initialize w
        for (int w : allW) {
            wMarked.add(w);
            wDistTo[w] = 0;
            wQ.enqueue(w);
            // Edge case: common vertex found
            if (vMarked.contains(w)) {
                length = 0;
                ancestor = w;
                return;
            }
        }

        // Keep searching while the queues are not empty
        while (!vQ.isEmpty() || !wQ.isEmpty()) {
            // Process next vertex on v's path. Quit early if common ancestor found.
            processNextVertexOnQueue(vQ, vMarked, wMarked, vDistTo, wDistTo);

            // Process next vertex on w's path. Quit early if common ancestor found.
            processNextVertexOnQueue(wQ, wMarked, vMarked, wDistTo, vDistTo);

        }
    }

    /**
     * Processes the next vertex on the BFS queue.
     * Pass in either vQ or wQ, depending on whether we are processing
     * vertices on v's path or on w's path.
     * <p>
     * Dequeues the first vertex on the queue (in FIFO order) and adds
     * all of its neighbors to the queue. Mutates the distTo and thisMarked
     * parameters.
     * <p>
     * If a vertex on path of both v and w is found, quits early and
     * updates the length and ancestor instance variables.
     *
     * @param q           A queue of vertices to process
     * @param thisMarked  If q is vQ, vertices seen so far starting from v.
     *                    Else vertices seen so far starting from w.
     * @param otherMarked If q is vQ, vertices seen so far starting from w.
     *                    Else vertices seen so far starting from v.
     * @param thisDistTo  If q is vQ, distance from v to a given vertex.
     *                    Else distance from w to a given vertex.
     * @param otherDistTo If q is vQ, distance from w to a given vertex.
     *                    Else distance from v to a given vertex.
     */
    private void processNextVertexOnQueue(Queue<Integer> q,
                                          Set<Integer> thisMarked,
                                          Set<Integer> otherMarked,
                                          int[] thisDistTo,
                                          int[] otherDistTo) {
        // Do nothing if the queue is empty
        if (q.isEmpty()) return;

        // Source vertex (already marked and processed)
        int source = q.dequeue();

        // Process all neighbors and add to queue
        for (int dest : G.adj(source)) {
            // Skip any vertices we have already seen
            if (thisMarked.contains(dest)) continue;

            // BFS core operation
            thisMarked.add(dest);
            thisDistTo[dest] = thisDistTo[source] + 1;
            q.enqueue(dest);

            // Ancestor found
            if (otherMarked.contains(dest)) {
                int distToDest = thisDistTo[dest] + otherDistTo[dest];

                // Closest ancestor found
                if (distToDest < length) {
                    length = distToDest;
                    ancestor = dest;
                }
            }
        }
    }
}
