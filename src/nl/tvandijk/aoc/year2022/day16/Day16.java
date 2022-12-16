package nl.tvandijk.aoc.year2022.day16;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Dijkstra;
import nl.tvandijk.aoc.util.Graph;

public class Day16 extends Day {
    private final Graph<Integer> graph = new Graph<>();
    private final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
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

    private int solve(int loc, boolean[] open, int time) {
        int best = 0;
        for (int i = 0; i < open.length; i++) {
            if (!open[i]) {
                // reach it
                int dist = loc == i ? 1 : dijkstra.determineShortestPath(loc, i) + 1;
                if (dist >= time) continue; // don't bother
                open[i] = true;
                int res = solve(i, open, time-dist) + rates[i] * (time-dist);
                open[i] = false;
                if (res > best) best = res;
            }
        }
        return best;
    }

    private int solveWithElephant(int loc, int i, boolean[] openme, boolean[] openel, int time) {
        if (i == openme.length) {
            return solve(loc, openme, time) + solve(loc, openel, time);
        } else if (!openme[i]) {
            // let me do it
            openel[i] = true;
            int res1 = solveWithElephant(loc, i+1, openme, openel, time);
            openel[i] = false;
            // let el do it
            openme[i] = true;
            int res2 = solveWithElephant(loc, i+1, openme, openel, time);
            openme[i] = false;
            return Math.max(res1, res2);
        } else {
            return solveWithElephant(loc, i+1, openme, openel, time);
        }
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
        return solve(loc, open, 30);
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
        var openel = Arrays.copyOf(open, open.length);
        return solveWithElephant(loc, 0, open, openel, 26);
    }
}
