package nl.tvandijk.aoc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph<N> {
    private final Map<N, Map<N, Long>> weights = new HashMap<>();

    public Map<N, Long> getEdges(N node) {
        return weights.computeIfAbsent(node, k -> new HashMap<>());
    }

    public void addEdge(N source, N target, long weight) {
        getEdges(source).put(target, weight);
    }

    public Set<N> getAdjacentNodes(N node) {
        return getEdges(node).keySet();
    }

    public long getWeight(N source, N target) {
        return getEdges(source).getOrDefault(target, Long.MAX_VALUE);
    }

    public Set<N> getAllNodes() {
        return weights.keySet();
    }
}