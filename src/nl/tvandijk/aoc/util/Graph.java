package nl.tvandijk.aoc.util;

import java.util.*;

public class Graph<N> {
    public interface SuccessorFunction <N> {
        Collection<Pair<N, Long>> successors(N state);
    }

    public interface IsFinalFunction <S> {
        boolean isFinal(S state);
    }

    private final Map<N, Map<N, Long>> weights = new HashMap<>();
    private final SuccessorFunction<N> successorFunction;
    private final Dijkstra<N> dijkstra = new Dijkstra<>(this);
    private final FloydWarshall<N> floydWarshall = new FloydWarshall<>(this);

    public Graph() {
        this.successorFunction = null;
    }

    public Graph(SuccessorFunction<N> successorFunction) {
        this.successorFunction = successorFunction;
    }

    public Map<N, Long> getEdges(N node) {
        return weights.computeIfAbsent(node, k -> {
            var res = new HashMap<N, Long>();
            if (successorFunction != null) {
                var edges = successorFunction.successors(node);
                edges.forEach(s -> res.put(s.a, s.b));
            }
            return res;
        });
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

    /**
     * Compute the shortest path from the source node to the target node.
     * @param source the source node
     * @param target the target node
     * @return the distance from the source node to the target node
     */
    public long dijkstra(N source, N target) {
        return dijkstra.distance(source, target);
    }

    /**
     * Compute the shortest path from the source node to all other nodes.
     * @return a map from all nodes to their distance from the source node
     */
    public Dijkstra<N> dijkstra() {
        return dijkstra;
    }

    /**
     * Compute all distances from all nodes to all other nodes.
     * @return a map from all nodes to their distance from all other nodes
     */
    public FloydWarshall<N> floydWarshall() {
        return floydWarshall;
    }

    /**
     * Compute all distances from the initial set of nodes to all other nodes.
     * @param initial the initial set of nodes
     * @return a map from all nodes to their distance from the initial set of nodes
     */
    public Map<N, Long> reachAll(Collection<N> initial) {
        Map<N, Long> distance = new HashMap<>();
        PriorityQueue<N> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        for (var s : initial) {
            distance.put(s, 0L);
            unvisited.add(s);
        }

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);

            for (var s : getEdges(v).entrySet()) {
                var nv = s.getKey();
                var dnv = dv + s.getValue();

                if (distance.containsKey(nv)) {
                    var cur = distance.get(nv);
                    if (cur > dnv) {
                        unvisited.remove(nv);
                        distance.put(nv, dnv);
                        unvisited.add(nv);
                    }
                } else {
                    distance.put(nv, dnv);
                    unvisited.add(nv);
                }
            }
        }
        return distance;
    }

    /**
     * Compute the shortest path from the initial set of nodes to any final node.
     * @param initial the initial set of nodes
     * @param isFinal a function that determines whether a node is final
     * @return a pair of a map from all nodes to their distance from the initial set of nodes and a trace of nodes
     */
    public Pair<Map<N, Long>, List<N>> reachAny(Collection<N> initial, IsFinalFunction<N> isFinal) {
        Map<N, Long> distance = new HashMap<>();
        Map<N, N> pred = new HashMap<>();
        PriorityQueue<N> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        for (var state : initial) {
            distance.put(state, 0L);
            unvisited.add(state);
        }

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);
            if (isFinal.isFinal(v)) {
                List<N> trace = new ArrayList<>();
                {
                    var s = v;
                    while (s != null) {
                        trace.add(0, s);
                        s = pred.get(s);
                    }
                }
                return Pair.of(distance, trace);
            }

            for (var s : getEdges(v).entrySet()) {
                var nv = s.getKey();
                var dnv = dv + s.getValue();

                if (distance.containsKey(nv)) {
                    var cur = distance.get(nv);
                    if (cur > dnv) {
                        unvisited.remove(nv);
                        distance.put(nv, dnv);
                        unvisited.add(nv);
                        pred.put(nv, v);
                    }
                } else {
                    distance.put(nv, dnv);
                    unvisited.add(nv);
                    pred.put(nv, v);
                }
            }
        }

        return Pair.of(distance, null);
    }
}