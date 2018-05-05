/******************************************************************************
 *  Compilation:  javac BaseballElimination.java
 *  Execution:    java BaseballElimination team.txt
 *  Dependencies: In.java StdOut.java FlowEdge.java FlowNetwork.java
 *    FordFulkerson.java
 *
 *  This class uses the Ford-Fulkerson MaxFlow/MinCut algorithm to find teams
 *  that are statistically eliminated from holding the conference title
 *  (best record in conference or tied for best record in conference).
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    /* Number of teams */
    private final int n;

    /* Use to determine whether or not team is eliminated */
    private final Map<String, List<String>> certificates;

    /* Mapping between team and index (0 to N-1) */
    private final Map<String, Integer> teamToIndex;

    /* Team Division information */
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] games;

    public BaseballElimination(String fileName) {
        // Error checking
        if (fileName == null || fileName.equals("")) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        // Open the file for reading
        In in = new In(fileName);

        // Initialize instance variables
        n = Integer.parseInt(in.readLine());
        certificates = new HashMap<>();
        teamToIndex = new HashMap<>();
        wins = new int[n];
        losses = new int[n];
        remaining = new int[n];
        games = new int[n][n];

        // Populate instance variables from file contents
        for (int index = 0; index < n; index++) {
            // Read line corresponding to a single team
            String rawLine = in.readLine();
            if (rawLine == null) throw new IllegalArgumentException("Invalid line");

            // split line by whitespace
            String line = rawLine.trim();
            String[] contents = line.split("\\s+");

            // First 4 columns are static: team, wins, losses, and remaining
            teamToIndex.put(contents[0], index);
            wins[index] = Integer.parseInt(contents[1]);
            losses[index] = Integer.parseInt(contents[2]);
            remaining[index] = Integer.parseInt(contents[3]);

            // Populate games array (number of cols depends on number of teams)
            for (int otherIndex = 0; otherIndex < n; otherIndex++) {
                games[index][otherIndex] = Integer.parseInt(contents[4 + otherIndex]);
            }
        }
    }

    /**
     * number of teams
     */
    public int numberOfTeams() {
        return n;
    }

    /**
     * all teams
     */
    public Iterable<String> teams() {
        return teamToIndex.keySet();
    }

    /**
     * Number of wins for given team
     */
    public int wins(String team) {
        validateTeam(team);
        return wins[teamToIndex.get(team)];
    }

    /**
     * Number of losses for a given team
     */
    public int losses(String team) {
        validateTeam(team);
        return losses[teamToIndex.get(team)];
    }

    /**
     * Total number of remaining games for given team
     */
    public int remaining(String team) {
        validateTeam(team);
        return remaining[teamToIndex.get(team)];
    }

    /**
     * Number of games remaining between two teams
     */
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        return games[teamToIndex.get(team1)][teamToIndex.get(team2)];
    }

    /**
     * Returns true if the given team is eliminated
     */
    public boolean isEliminated(String team) {
        validateTeam(team);
        return certificateOfElimination(team) != null;
    }

    /**
     * If trivial elimination, returns all teams that currently have more wins
     * than the given team + all remaining games.
     * If nontrivial elimination, returns the subset R of teams that eliminates the given team.
     * If not eliminated, returns null
     *
     * @param team Team to check if eliminated
     * @return An iterable of team names
     */
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);

        // Calculate and cache certificates if not yet computed
        if (!certificates.containsKey(team)) {
            calculateCertificateOfElimination(team);
        }

        // Get cached certificate of elimination for this team
        List<String> certificate = certificates.get(team);

        // Returns null if team is not eliminated
        if (certificate.isEmpty()) return null;
        else return certificate;
    }

    private void calculateCertificateOfElimination(String team) {
        int teamIndex = teamToIndex.get(team);
        certificates.put(team, new ArrayList<>());

        // Edge case: Only one team. Cannot be eliminated.
        if (n == 1) return;

        // Regular case: 3 or more teams
        // Create FlowNetwork, with artificial source (s) and sink (t)
        FlowNetwork G = new FlowNetwork(numVertices());
        int s = numVertices() - 2;
        int t = numVertices() - 1;

        // Create edges from team vertices to artificial sink (t),
        // or determine that team is trivially eliminated
        for (String otherTeam : teams()) {
            // Skip the team in question
            if (otherTeam.equals(team)) continue;

            // Find the index of each other team, and calculate capacity from
            // team vertex to T
            int otherTeamIndex = teamToIndex.get(otherTeam);
            int capacity = wins[teamIndex] + remaining[teamIndex] - wins[otherTeamIndex];

            // Trivial elimination found: return without running FordFulkerson.
            // Certificate of elimination contains only one team
            if (capacity < 0) {
                certificates.get(team).add(otherTeam);
                return;
            }

            // Non-trivial elimination: create a FlowEdge
            int otherTeamVertex = teamIndexToVertex(teamIndex, otherTeamIndex);
            FlowEdge teamToT = new FlowEdge(otherTeamVertex, t, capacity);
            G.addEdge(teamToT);
        }

        // Create edges from each game vertex.
        // 3 edges for each game vertex g:
        // s -> g
        // g -> t1 (team 1)
        // g -> t2 (team 2)
        // Game vertices go from 0 to n-1Choose2
        int gameVertex = 0;
        for (int otherTeamIndex1 = 0; otherTeamIndex1 < n; otherTeamIndex1++) {
            for (int otherTeamIndex2 = otherTeamIndex1 + 1; otherTeamIndex2 < n; otherTeamIndex2++) {
                // Skip the team we are trying to see if eliminated
                if (teamIndex == otherTeamIndex1 || teamIndex == otherTeamIndex2) continue;

                // create edges
                FlowEdge sToGame = new FlowEdge(s, gameVertex, games[otherTeamIndex1][otherTeamIndex2]);
                FlowEdge gameToTeam1 = new FlowEdge(gameVertex, teamIndexToVertex(teamIndex, otherTeamIndex1), INFINITY);
                FlowEdge gameToTeam2 = new FlowEdge(gameVertex, teamIndexToVertex(teamIndex, otherTeamIndex2), INFINITY);

                // Add edges to network
                G.addEdge(sToGame);
                G.addEdge(gameToTeam1);
                G.addEdge(gameToTeam2);

                // Increment game vertex
                gameVertex++;
            }
        }

        // Run Ford-Fulkerson algorithm
        FordFulkerson ff = new FordFulkerson(G, s, t);

        // Figure out if team is eliminated
        boolean eliminated = false;
        for (FlowEdge e : G.adj(s)) {
            if (e.residualCapacityTo(e.other(s)) > 0) {
                eliminated = true;
                break;
            }
        }
        // Team is not eliminated: certificateOfElimination is empty for this team
        if (!eliminated) return;

        // Team is eliminated: calculate certificate of elimination
        for (String otherTeam : teams()) {
            // Skip team in question
            if (otherTeam.equals(team)) continue;

            int otherTeamVertex = teamIndexToVertex(teamIndex, teamToIndex.get(otherTeam));
            if (ff.inCut(otherTeamVertex)) {
                certificates.get(team).add(otherTeam);
            }
        }
    }

    /**
     * Converts the index of a team into a team vertex in the flow network.
     *
     * @param isEliminatedTeamIndex Team to check if eliminated. Is not included in the flow network.
     * @param otherTeamIndex        Any other team
     * @return Vertex of otherTeamIndex in the flow network.
     */
    private int teamIndexToVertex(int isEliminatedTeamIndex, int otherTeamIndex) {
        // Edge case
        if (isEliminatedTeamIndex == otherTeamIndex)
            throw new IllegalArgumentException("isEliminatedTeamIndex and otherTeamIndex cannot be equal: " + isEliminatedTeamIndex);

        // Case 1: as normal
        if (otherTeamIndex < isEliminatedTeamIndex) return numGameVertices() + otherTeamIndex;

            // Case 2: deal with the fact that there are n teams but n-1 team vertices
        else return numGameVertices() + otherTeamIndex - 1;
    }

    /**
     * Returns the total number of vertices in the flow network
     */
    private int numVertices() {
        // Artificial source (s) + sink (t) + number game vertices + number team vertices
        return 2 + numGameVertices() + numTeamVertices();
    }

    /**
     * Calculates number of game vertices.
     * Let y = number of teams that are NOT the specified team (AKA n-1).
     * Then the number of game vertices is equal to yChoose2.
     * This is equal to y! / 2!(y-2)!
     * This is equal to y(y-1) / 2 (via algebra).
     *
     * @return Number of game vertices in the flow network
     */
    private int numGameVertices() {
        int y = numTeamVertices();
        return (y * (y - 1)) / 2;
    }

    /**
     * Returns number of team vertices in the flow network (AKA n-1)
     */
    private int numTeamVertices() {
        return n - 1;
    }

    /**
     * Validates that team is not null and is a team in the division
     */
    private void validateTeam(String team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (!teamToIndex.containsKey(team)) {
            throw new IllegalArgumentException("Team not in division: " + team);
        }
    }

    /**
     * Main function for calculating eliminated team. Takes in filename as arg
     */
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
