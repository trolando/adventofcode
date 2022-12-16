package nl.tvandijk.aoc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph<N> {
    private final Map<N, Map<N, Integer>> weights = new HashMap<>();

    private Map<N, Integer> getNode(N node) {
        return weights.computeIfAbsent(node, k -> new HashMap<>());
    }

    public void addEdge(N source, N target, int weight) {
        getNode(source).put(target, weight);
    }

    public Set<N> getAdjacentNodes(N node) {
        return getNode(node).keySet();
    }

    public int getWeight(N source, N target) {
        return getNode(source).getOrDefault(target, Integer.MAX_VALUE);
    }

    public Set<N> getAllNodes() {
        return weights.keySet();
    }
}