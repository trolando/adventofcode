package nl.tvandijk.aoc.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementation of Dijkstra's shortest path algorithm for a Graph<N>.
 * Does not work with negative distances.
 */
public class Dijkstra<N> {
    private final Graph<N> graph;
    private final Map<Pair<N, N>, Long> distances = new HashMap<>();

    public Dijkstra(Graph<N> graph) {
        this.graph = graph;
    }

    public long distance(N source, N target) {
        var v = distances.get(Pair.of(source, target));
        if (v != null) return v;

        Map<N, Long> distance = new HashMap<>();
        graph.getAllNodes().forEach(node -> distance.put(node, Long.MAX_VALUE));
        distance.put(source, 0L);

        PriorityQueue<N> queue = new PriorityQueue<>(Comparator.comparingLong(distance::get));
        queue.add(source);
        Set<N> seen = new HashSet<>();
        seen.add(source);

        while (!queue.isEmpty()) {
            N top = queue.poll();

            for (N adjacent : graph.getAdjacentNodes(top)) {
                long currentDistance = graph.getWeight(top, adjacent) + distance.get(top);

                if (currentDistance < distance.getOrDefault(adjacent, Long.MAX_VALUE)) {
                    distance.put(adjacent, currentDistance);
                }

                if (!seen.contains(adjacent)) {
                    queue.add(adjacent);
                    seen.add(adjacent);
                }
            }
        }

        for (var m : distance.entrySet()) {
            distances.put(Pair.of(source, m.getKey()), m.getValue());
        }

        return distance.getOrDefault(target, Long.MAX_VALUE);
    }
}