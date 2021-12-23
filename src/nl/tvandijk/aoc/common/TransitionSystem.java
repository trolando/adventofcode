package nl.tvandijk.aoc.common;

import java.util.*;

public class TransitionSystem<S> {
    public interface SuccessorFunction <S> {
        Collection<Pair<S, Long>> successors(S state);
    }

    public interface IsFinalFunction <S> {
        boolean isFinal(S state);
    }

    private final SuccessorFunction<S> fSuc;

    public TransitionSystem(SuccessorFunction<S> fSuc) {
        this.fSuc = fSuc;
    }

    public Map<S, Long> reachAll(Collection<S> initial) {
        Map<S, Long> distance = new HashMap<>();
        PriorityQueue<S> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        for (var s : initial) {
            distance.put(s, 0L);
            unvisited.add(s);
        }

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);

            for (var s : fSuc.successors(v)) {
                var nv = s.a;
                var dnv = dv + s.b;

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

    public Pair<Map<S, Long>, List<S>> reachFinal(Collection<S> initial, IsFinalFunction<S> isFinal) {
        Map<S, Long> distance = new HashMap<>();
        Map<S, S> pred = new HashMap<>();
        PriorityQueue<S> unvisited = new PriorityQueue<>(Comparator.comparingLong(distance::get));

        for (var state : initial) {
            distance.put(state, 0L);
            unvisited.add(state);
        }

        while (!unvisited.isEmpty()) {
            var v = unvisited.poll();
            var dv = distance.get(v);
            if (isFinal.isFinal(v)) {
                List<S> trace = new ArrayList<>();
                {
                    var s = v;
                    while (s != null) {
                        trace.add(0, s);
                        s = pred.get(s);
                    }
                }
                return Pair.of(distance, trace);
            }

            for (var s : fSuc.successors(v)) {
                var nv = s.a;
                var dnv = dv + s.b;

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
