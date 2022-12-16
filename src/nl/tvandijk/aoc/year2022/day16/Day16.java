package nl.tvandijk.aoc.year2022.day16;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.FloydWarshall;
import nl.tvandijk.aoc.util.Graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day16 extends Day {
    private final Graph<Integer> graph = new Graph<>();
    private final FloydWarshall<Integer> fw = new FloydWarshall<>(graph);
    private final Map<String, Integer> valves = new HashMap<>();
    private int[] rates;

    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        rates = new int[lines.length];
        for (var line : lines) {
            var a = line.split("[ =;]");
            var b = line.split("valves? ")[1].split(", ");
            int id = valves.computeIfAbsent(a[1], k -> valves.size());
            rates[id] = Integer.parseInt(a[5]);
            for (var to : b) {
                int to_id = valves.computeIfAbsent(to, k -> valves.size());
                graph.addEdge(id, to_id, 1);
            }
        }
    }

    record State(int loc, boolean[] open, int time) {
        State(int loc, boolean[] open, int time) {
            this.loc = loc;
            this.open = Arrays.copyOf(open, open.length);
            this.time = time;
        }

        public static State of(int loc, boolean[] open, int time) {
            return new State(loc, open, time);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State state)) return false;
            return loc == state.loc && time == state.time && Arrays.equals(open, state.open);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(loc, time);
            result = 31 * result + Arrays.hashCode(open);
            return result;
        }
    }

    private Map<State, Integer> cache = new HashMap<>();

    private int solve(int loc, boolean[] open, int time, boolean elephant) {
        if (!elephant) {
            var v = State.of(loc, open, time);
            var i = cache.get(v);
            if (i != null) return i;
        }
        int best = 0;
        for (int i = 0; i < open.length; i++) {
            if (!open[i]) {
                // reach it
                int dist = loc == i ? 1 : fw.distance(loc, i) + 1;
                if (dist >= time) continue; // don't bother
                open[i] = true;
                int res = solve(i, open, time-dist, elephant) + rates[i] * (time-dist);
                open[i] = false;
                if (res > best) best = res;
            }
        }
        if (elephant) {
            int res = solve(valves.get("AA"), open, 26, false);
            if (res > best) best = res;
        } else {
            var v = State.of(loc, open, time);
            cache.put(v, best);
        }
        return best;
    }

    @Override
    protected Object part1() {
        // part 1
        var vlen = valves.size();
        int loc = valves.get("AA");
        var open = new boolean[vlen];
        for (int i = 0; i < vlen; i++) {
            if (rates[i] == 0) open[i] = true;
        }
        return solve(loc, open, 30, false);
    }

    @Override
    protected Object part2() {
        // part 2
        var vlen = valves.size();
        int loc = valves.get("AA");
        var open = new boolean[vlen];
        for (int i = 0; i < vlen; i++) {
            if (rates[i] == 0) open[i] = true;
        }
        return solve(loc, open, 26, true);
    }
}
