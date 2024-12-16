package nl.tvandijk.aoc.year2024.day16;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;
import scala.concurrent.impl.FutureConvertersImpl;

public class Day16 extends Day {
    private Set<Pair<DirectedPoint, Long>> successor(DirectedPoint dp) {
        Set<Pair<DirectedPoint, Long>> res = new HashSet<>();
        if (grid.get(dp.forward().p) != '#') res.add(Pair.of(dp.forward(), 1L));
        if (grid.get(dp.rotateRight().forward().p) != '#') res.add(Pair.of(dp.rotateRight().forward(), 1001L));
        if (grid.get(dp.rotateLeft().forward().p) != '#') res.add(Pair.of(dp.rotateLeft().forward(), 1001L));
        return res;
    }

    public <N> Set<N> reachAny(Graph<N> graph, Collection<N> initial, Graph.IsFinalFunction<N> isFinal) {
        Map<N, Long> distance = new HashMap<>();
        Map<N, Set<N>> pred = new HashMap<>();
        PriorityQueue<N> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        for (var state : initial) {
            distance.put(state, 0L);
            unvisited.add(state);
        }

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);
            if (isFinal.isFinal(v)) {
                Deque<N> queue = new ArrayDeque<>(pred.get(v));
                Set<N> places = new HashSet<>(queue);
                places.add(v);
                while (!queue.isEmpty()) {
                    var x = queue.poll();
                    if (pred.containsKey(x)) {
                        for (var xx : pred.get(x)) {
                            if (places.add(xx)) queue.add(xx);
                        }
                    }
                }
                return places;
            }

            for (var s : graph.getEdges(v).entrySet()) {
                var nv = s.getKey();
                var dnv = dv + s.getValue();

                if (distance.containsKey(nv)) {
                    var cur = distance.get(nv);
                    if (cur > dnv) {
                        unvisited.remove(nv);
                        distance.put(nv, dnv);
                        unvisited.add(nv);
                        pred.compute(nv, (key, value) -> {
                            if (value == null) value = new HashSet<>();
                            else value.clear();
                            value.add(v);
                            return value;
                        });
                    }
                    if (cur == dnv) {
                        pred.get(nv).add(v);
                    }
                } else {
                    distance.put(nv, dnv);
                    unvisited.add(nv);
                    pred.compute(nv, (key, value) -> {
                        if (value == null) value = new HashSet<>();
                        else value.clear();
                        value.add(v);
                        return value;
                    });
                }
            }
        }

        return Set.of();
    }

    @Override
    protected Object part1() {
        // part 1
        var start = grid.findOne('S');
        var end = grid.findOne('E');
        var graph = new Graph<DirectedPoint>(this::successor);
        graph = graph.edgeCompress();
        var res = graph.reachAll(Set.of(DirectedPoint.of(start, Direction.RIGHT)));
        return res.keySet().stream().filter(p -> p.p.equals(end)).mapToLong(res::get).min().orElse(0);
    }

    @Override
    protected Object part2() {
        // part 2
        var start = grid.findOne('S');
        var end = grid.findOne('E');
        var graph = new Graph<>(this::successor);
        graph = graph.edgeCompress();
        var res = reachAny(graph, Set.of(DirectedPoint.of(start, Direction.RIGHT)), s -> s.p.equals(end));
        var res2 = res.stream().map(x->x.p).collect(Collectors.toSet());
        return res2.size();
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day16() {
        super("example.txt", "input.txt");
    }
}
