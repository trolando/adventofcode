package nl.tvandijk.aoc.util;

import java.util.*;

/**
 * Implementation of Floyd Warshall which computes the minimum distances between all points in the Graph.
 * Contrary to Dijkstra, also works with negative weights.
 */
public class FloydWarshall<N> {
    private Graph<N> graph;
    private Map<Pair<N, N>, Long> distances = new HashMap<>();

    public FloydWarshall(Graph<N> graph) {
        // initialize
        this.graph = graph;
    }

    private void compute() {
        for (var n : graph.getAllNodes()) {
            for (var e : graph.getEdges(n).entrySet()) {
                distances.put(Pair.of(n, e.getKey()), e.getValue());
            }
        }
        for (var k : graph.getAllNodes()) {
            for (var from : graph.getAllNodes()) {
                for (var to : graph.getAllNodes()) {
                    distances.compute(Pair.of(from, to), (key,v) -> {
                        var a = distances.get(Pair.of(from, k));
                        var b = distances.get(Pair.of(k, to));
                        if (a != null && b != null) {
                            var c = a + b;
                            if (v == null || c < v) v = c;
                        }
                        return v;
                    });
                }
            }
        }
    }

    /**
     * Get all distances
     * @return a map of all distances between two nodes
     */
    public Map<Pair<N, N>, Long> getAll() {
        if (distances.isEmpty()) compute();
        return distances;
    }

    public long distance(N source, N target) {
        if (distances.isEmpty()) compute();
        var r = distances.get(Pair.of(source, target));
        if (r == null) return Integer.MAX_VALUE;
        else return r;
    }
}