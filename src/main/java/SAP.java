/******************************************************************************
 *  Compilation:  javac SAP.java
 *  Execution:    java SAP
 *  Dependencies:
 *
 *  Defines the SAP class, which calculates the Shortest Ancestral Path for two
 *  vertices in a Directed Graph (DiGraph).
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final DeluxeBFS bfs;
    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Digraph cannot be null");
        }

        // Make a copy of the graph
        this.G = new Digraph(G);
        // Pass in copy of the graph to BFS constructor
        this.bfs = new DeluxeBFS(this.G);
    }

    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        bfs.bfs(v, w);
        int ancestor = bfs.getAncestor();
        return ancestor == -1 ? -1 : bfs.getLength();
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v, w);
        bfs.bfs(v, w);
        int ancestor = bfs.getAncestor();
        return ancestor == -1 ? -1 : bfs.getLength();
    }

    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        bfs.bfs(v, w);
        return bfs.getAncestor();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v, w);
        bfs.bfs(v, w);
        return bfs.getAncestor();
    }

    private void validateVertices(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("v and w cannot be null");
        }
        for (int vtx : v) {
            validateVertex(vtx);
        }
        for (int vtx : w) {
            validateVertex(vtx);
        }
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= G.V()) {
            String err = "Found vertex %s. Vertex must be between 0 and %s";
            throw new IllegalArgumentException(String.format(
                    err, vertex, G.V() - 1));
        }
    }
}

/*
public class SAP {
    private final int INFINITY = Integer.MAX_VALUE;
    private final int INVALID_VERTEX = -1;

    private final Digraph G;
    // Result of breadthFirstSearch from vertex v
    private BreadthFirstDirectedPaths pathsFromV;

    // Marked vertices
    private HashSet<Integer> marked;

    // Distance from vertex w to vertex i for all indices i
    private int[] wDistTo;

    private int distToClosestAncestorSoFar;
    private int closestAncestorSoFar;

    public SAP(Digraph G){
        if (G == null) {
            throw new IllegalArgumentException("Digraph cannot be null");
        }
        this.G = new Digraph(G);
        marked = new HashSet<>();
        wDistTo = new int[G.V()];
    }

    public int length(int v, int w){
        return ancestor(v, w) == INVALID_VERTEX
                ? -1 : distToClosestAncestorSoFar;
    }


    public int length(Iterable<Integer> v, Iterable<Integer> w){
        return ancestor(v, w) == INVALID_VERTEX
                ? -1 : distToClosestAncestorSoFar;
    }

    public int ancestor(int v, int w){
        validateVertex(v);
        validateVertex(w);
        // Reset
        closestAncestorSoFar = INVALID_VERTEX;
        distToClosestAncestorSoFar = INFINITY;
        marked.clear();

        // Run BFS from v
        pathsFromV = new BreadthFirstDirectedPaths(G, v);

        // Initialize BFS from w
        Queue<Integer> q = new Queue<>();
        q.enqueue(w);
        marked.add(w);
        wDistTo[w] = 0;

        // Must process w before putting it on queue
        // Edge case: w is on same path as v, or w == v
        if (pathsFromV.distTo(w) < distToClosestAncestorSoFar){
            distToClosestAncestorSoFar = pathsFromV.distTo(w);
            closestAncestorSoFar = w;
        }

        // Run BFS from w
        runBfsFromW(q);

        return closestAncestorSoFar;
    }


    public int ancestor(Iterable<Integer> allV, Iterable<Integer> allW){
        validateVertices(allV, allW);
        // Reset variables
        closestAncestorSoFar = INVALID_VERTEX;
        distToClosestAncestorSoFar = INFINITY;
        marked.clear();

        // BFS
        pathsFromV = new BreadthFirstDirectedPaths(G, allV);

        // Initialize BFS from w
        Queue<Integer> q = new Queue<>();

        // Insert all vertices from w in the queue
        for(int w : allW){
            wDistTo[w] = 0;
            q.enqueue(w);
            marked.add(w);

            // Must process w before putting it on queue
            // Case: w is on same path as v, or w == v
            if (pathsFromV.distTo(w) < distToClosestAncestorSoFar){
                distToClosestAncestorSoFar = pathsFromV.distTo(w);
                closestAncestorSoFar = w;
            }
        }

        // Run BFS from w and update closestAncestorSoFar instance variable
        runBfsFromW(q);

        return closestAncestorSoFar;
    }

    private void runBfsFromW(Queue<Integer> q){
        // All vertices on queue have already been marked
        while(!q.isEmpty()){
            // Process all neighbors
            int source = q.dequeue();
            for (int dest : G.adj(source)){
                // Skip already-seen vertices
                if (marked.contains(dest)) { continue; }

                // Mark this vertex as seen and add to queue
                q.enqueue(dest);
                marked.add(dest);
                wDistTo[dest] = wDistTo[source] + 1;

                // Common ancestor found
                if (pathsFromV.hasPathTo(dest)) {
                    int distToDest = pathsFromV.distTo(dest) + wDistTo[dest];
                    // New ancestor closer than old ancestor
                    if (distToDest < distToClosestAncestorSoFar) {
                        distToClosestAncestorSoFar = distToDest;
                        closestAncestorSoFar = dest;
                    }
                }
            }
        }
    }

    private void validateVertices(Iterable<Integer> v, Iterable<Integer> w){
        if (v == null || w == null) {
            throw new IllegalArgumentException("v and w cannot be null");
        }
        for (Integer vtx : v){
            validateVertex(vtx);
        }
        for (Integer vtx : w){
            validateVertex(vtx);
        }
    }

    private void validateVertex(int vertex){
        if (vertex < 0 || vertex >= G.V()){
            String err = "Found vertex %s. Vertex must be between 0 and %s";
            throw new IllegalArgumentException(String.format(
                    err, vertex, G.V()-1));
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
*/

