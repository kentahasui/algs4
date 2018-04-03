/******************************************************************************
 *  Compilation:  javac WordNet.java
 *  Execution:    java WordNet
 *  Dependencies: SAP.java
 *
 *  WordNet implementation.
 *  Rooted Directed Acyclic Graph.
 *  Vertices = Synsets.
 *  Edges = Hypernyms.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordNet {
    /* IN PRODUCTION CODE, WE WOULD USE A BiMultiMap from Guava */
    // Mapping between noun to vertices (synsets) in the Digraph
    private Map<String, List<Integer>> nounToVertices;
    // Mapping between vertex (synsets) to all nouns
    private Map<Integer, String> vertexToNouns;

    // SAP to calculate Shortest Ancestral Path and closest common ancestor
    private SAP sap;

    ////////////////////////////////////////////////////////
    // Constructors and initialization methods
    ////////////////////////////////////////////////////////
    public WordNet(String synsets, String hypernyms) {
        validateNotNull(synsets);
        validateNotNull(hypernyms);

        // Read both synsets and hypernyms
        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);

        // Map nouns to all vertices (synsets)
        Map<String, List<Integer>> nounVertices = new HashMap<>();
        Map<Integer, String> vertexNouns = new HashMap<>();

        // Number of vertices, to pass as argument to Digraph constructor
        int numVertices = 0;

        // Construct the vertices
        while (synsetsIn.hasNextLine()) {
            numVertices++;
            String line = synsetsIn.readLine();
            String[] fields = line.split(",");
            int vertex = Integer.parseInt(fields[0]);

            // Associate noun -> vertex for each noun in list
            String[] nouns = fields[1].split(" ");
            for (String noun : nouns) {
                if (!nounVertices.containsKey(noun)) {
                    nounVertices.put(noun, new LinkedList<>());
                }
                nounVertices.get(noun).add(vertex);
            }

            // Associate vertex -> noun for each noun in list
            vertexNouns.put(vertex, fields[1]);
        }

        // Construct the edges
        Digraph g = new Digraph(numVertices);
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            int source = -1;
            String[] fields = line.split(",");
            for (String field : fields) {
                int v = Integer.parseInt(field);
                // The first field is always the source vertex
                if (source == -1) {
                    source = v;
                    continue;
                }
                // All other fields are the destination vertices
                g.addEdge(source, v);
            }
        }

        // Finish setting up WordNet from explicit graph
        initWordNetFromGraph(g, nounVertices, vertexNouns);
    }

    /**
     * Package-private constructor for testing (dependency injection).
     *
     * @param g              A Rooted Directed Acyclic Graph
     * @param nounToVertices A mapping of nouns to one or more vertices
     * @throws IllegalArgumentException if g is not rooted and acyclic.
     */
    private WordNet(Digraph g,
            Map<String, List<Integer>> nounToVertices,
            Map<Integer, String> vertexToNouns) {
        initWordNetFromGraph(g, nounToVertices, vertexToNouns);
    }

    /**
     * Helper method to initialize wordNet from a Graph and a mapping
     * of nouns to vertices.
     *
     * @param g              A Rooted Directed Acyclic Graph
     * @param nounVertices A mapping of nouns to one or more vertices
     * @param vertexNouns A mapping of vertices to one or more nouns
     * @throws IllegalArgumentException if g is not rooted and acyclic.
     */
    private void initWordNetFromGraph(Digraph g,
                                      Map<String, List<Integer>> nounVertices,
                                      Map<Integer, String> vertexNouns) {
        validateRooted(g);
        validateAcyclic(g);
        this.nounToVertices = nounVertices;
        this.vertexToNouns = vertexNouns;
        this.sap = new SAP(g);
    }

    ////////////////////////////////////////////////////////
    // Instance methods
    ////////////////////////////////////////////////////////

    /**
     * Returns true if the given argument is a noun in the wordNet graph.
     */
    public boolean isNoun(String noun) {
        validateNotNull(noun);
        return nounToVertices.containsKey(noun);
    }

    /**
     * Returns all (de-duplicated) nouns in the wordNet graph
     */
    public Iterable<String> nouns() {
        return nounToVertices.keySet();
    }

    /**
     * Length of any shortest ancestral path between any synset v of A and any synset w of B
     *
     * @param nounA any noun in the wordNet
     * @param nounB any noun in the wordNet
     * @return length of shortest ancestral path.
     * @throws IllegalArgumentException if either argument is null or not a noun.
     */
    public int distance(String nounA, String nounB) {
        validateNotNull(nounA);
        validateNotNull(nounB);
        validateNoun(nounA);
        validateNoun(nounB);
        return sap.length(nounToVertices.get(nounA), nounToVertices.get(nounB));
    }

    /**
     * Returns synset of closest common ancestor to the below nouns.
     * If the ancestor synset has multiple nouns, returns any at random
     *
     * @param nounA A noun
     * @param nounB A noun
     * @return The vertex number of the closest common ancestor (hypernym).
     * @throws IllegalArgumentException if either argument is null or not a noun.
     */
    public String sap(String nounA, String nounB) {
        validateNotNull(nounA);
        validateNotNull(nounB);
        validateNoun(nounA);
        validateNoun(nounB);
        int ancestorVertex = sap.ancestor(
                nounToVertices.get(nounA),
                nounToVertices.get(nounB));
        return vertexToNouns.get(ancestorVertex);
    }

    ////////////////////////////
    // Input Validation Methods
    ////////////////////////////
    private void validateNotNull(String arg) {
        if (arg == null) {
            throw new IllegalArgumentException("Null argument");
        }
    }

    private void validateNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException(
                    String.format("Argument %s is not a noun", noun));
        }
    }

    /**
     * Validates that the specified Digraph is rooted.
     * A tree is rooted iff:
     * <ol>
     * <li>There exists a single vertex v with no outgoing edges </li>
     * <li>There is a path from every other vertex to v</li>
     * </ol>
     * <p>
     * Runtime:
     * O(V) + O(E) + O(V+E) = O(V+E)
     *
     * @param g Any Digraph
     * @throws IllegalArgumentException if the graph is not rooted.
     */
    private void validateRooted(Digraph g) {
        String errMsg = "Graph is not rooted: ";

        // Find the root of the tree if there is one
        int root = -1;
        for (int v = 0; v < g.V(); v++) {
            // Found root: has no out degrees
            if (g.outdegree(v) == 0) {
                // More than one root = not a rooted DAG
                if (root != -1) {
                    throw new IllegalArgumentException(
                            errMsg + "Multiple vertices with outDegree 0 found");
                }
                root = v;
            }
        }

        // Exception if no root
        if (root == -1) {
            throw new IllegalArgumentException(errMsg + "No vertex with outDegree 0 found");
        }
    }

    /**
     * Validates that the specified Digraph is acyclic.
     * Runtime: O(V+E) via DFS.
     *
     * @param g Any Digraph
     * @throws IllegalArgumentException if g has a cycle.
     */
    private void validateAcyclic(Digraph g) {
        DirectedCycle cycleDetector = new DirectedCycle(g);
        if (cycleDetector.hasCycle()) {
            throw new IllegalArgumentException(
                    "Graph has a cycle: " + cycleDetector.cycle());
        }
    }
}
