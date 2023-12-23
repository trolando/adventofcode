package nl.tvandijk.aoc.util;

import java.util.*;

/**
 * Represents a graph with nodes of type N.
 */
public class Graph<N> {
    /**
     * A function that determines the successors of a node.
     */
    public interface SuccessorFunction<N> {
        /**
         * Determine the successors of a node.
         *
         * @param state the node
         * @return a collection of pairs of adjacent nodes and their weight/distance
         */
        Collection<Pair<N, Long>> successors(N state);
    }

    /**
     * A function that determines whether a node is final.
     */
    public interface IsFinalFunction<S> {
        /**
         * Determine whether a node is final.
         *
         * @param state the node
         * @return true if the node is final, false otherwise
         */
        boolean isFinal(S state);
    }

    private final Map<N, Map<N, Long>> weights = new HashMap<>();
    private final SuccessorFunction<N> successorFunction;
    private final Dijkstra<N> dijkstra = new Dijkstra<>(this);
    private final FloydWarshall<N> floydWarshall = new FloydWarshall<>(this);

    /**
     * Create a graph without a successor function.
     */
    public Graph() {
        this.successorFunction = null;
    }

    /**
     * Create a graph with a successor function.
     *
     * @param successorFunction the successor function
     */
    public Graph(SuccessorFunction<N> successorFunction) {
        this.successorFunction = successorFunction;
    }

    /**
     * Get all the edges of the node.
     *
     * @param node the node
     * @return a map from all adjacent nodes to their weight/distance
     */
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

    /**
     * Add an edge from the source node to the target node with the given weight.
     *
     * @param source the source node
     * @param target the target node
     * @param weight the weight/distance
     */
    public void addEdge(N source, N target, long weight) {
        getEdges(source).put(target, weight);
    }

    /**
     * Get all adjacent nodes of the node.
     *
     * @param node the node
     * @return a set of all adjacent nodes
     */
    public Set<N> getAdjacentNodes(N node) {
        return getEdges(node).keySet();
    }

    /**
     * Get the weight of the edge from the source node to the target node. If there is no edge, return Long.MAX_VALUE.
     *
     * @param source the source node
     * @param target the target node
     * @return the weight/distance
     */
    public long getWeight(N source, N target) {
        return getEdges(source).getOrDefault(target, Long.MAX_VALUE);
    }

    /**
     * Get all nodes in the graph.
     *
     * @return a set of all the nodes
     */
    public Set<N> getAllNodes() {
        return weights.keySet();
    }

    /**
     * Compute the shortest path from the source node to the target node.
     *
     * @param source the source node
     * @param target the target node
     * @return the distance from the source node to the target node
     */
    public long dijkstra(N source, N target) {
        return dijkstra.distance(source, target);
    }

    /**
     * Compute the shortest path from the source node to all other nodes.
     *
     * @return a map from all nodes to their distance from the source node
     */
    public Dijkstra<N> dijkstra() {
        return dijkstra;
    }

    /**
     * Compute all distances from all nodes to all other nodes.
     *
     * @return a map from all nodes to their distance from all other nodes
     */
    public FloydWarshall<N> floydWarshall() {
        return floydWarshall;
    }

    /**
     * Compute all distances from the initial set of nodes to all other nodes.
     *
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
     *
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
                var s = v;
                while (s != null) {
                    trace.addFirst(s);
                    s = pred.get(s);
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

    public Graph<N> edgeCompress() {
        return new Graph<>(state -> {
            var successors = new ArrayList<Pair<N, Long>>();
            for (var edge : getEdges(state).entrySet()) {
                var prev = state;
                var target = edge.getKey();
                var weight = edge.getValue();
                var targetEdges = getEdges(target);
                while (targetEdges.size() == 2 && targetEdges.containsKey(prev)) {
                    // get the target that isn't prev
                    for (var e : targetEdges.entrySet()) {
                        if (!e.getKey().equals(prev)) {
                            prev = target;
                            target = e.getKey();
                            weight += e.getValue();
                            targetEdges = getEdges(target);
                            break;
                        }
                    }
                }
                successors.add(Pair.of(target, weight));
            }
            return successors;
        });
    }

    public Pair<List<N>, Long> longestPath(N initial, N goal) {
        List<N> best = null;
        long bestLength = -1L;
        Set<N> stackSet = new HashSet<>();
        Deque<Pair<N, Set<N>>> stack = new ArrayDeque<>();
        stack.add(Pair.of(initial, null));
        stackSet.add(initial);
        while (!stack.isEmpty()) {
            // check top
            var cur = stack.peekLast();
            if (cur.b == null) {
                cur.b = new HashSet<>(getAdjacentNodes(cur.a));
            }
            if (cur.b.isEmpty()) {
                // backtrack
                stackSet.remove(cur.a);
                stack.removeLast();
            } else {
                // pick one
                var next = cur.b.iterator().next();
                cur.b.remove(next);
                if (stackSet.contains(next)) continue; // ignore it
                if (next.equals(goal)) {
                    // found a path
                    var path = new ArrayList<N>();
                    for (var p : stack) {
                        path.add(p.a);
                    }
                    path.add(next);
                    long dist = 0L;
                    for (int i = 1; i < path.size(); i++) {
                        dist += getWeight(path.get(i - 1), path.get(i));
                    }
                    if (dist > bestLength) {
                        bestLength = dist;
                        best = path;
                    }
                } else {
                    stack.add(Pair.of(next, null));
                    stackSet.add(next);
                }
            }
        }
        return Pair.of(best, bestLength);
    }
}