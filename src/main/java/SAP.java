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

