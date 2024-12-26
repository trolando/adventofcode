package nl.tvandijk.aoc.year2024.day23;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day23 extends Day {
    @Override
    protected Object part1() {
        // part 1
        Graph<String> graph = new Graph<>();
        for (var line : lines) {
            var a = line.split("-");
            graph.addEdge(a[0], a[1], 1);
            graph.addEdge(a[1], a[0], 1);
        }
        // find all triangles
        int sum = 0;
        for (var node : graph.getAllNodes()) {
            var neighbors = graph.getAdjacentNodes(node);
            for (var neighbor1 : neighbors) {
                if (neighbor1.hashCode() > node.hashCode()) { // Enforce ordering
                    for (var neighbor2 : neighbors) {
                        if (neighbor2.hashCode() > neighbor1.hashCode() && graph.getAdjacentNodes(neighbor1).contains(neighbor2)) {
                            if (node.startsWith("t") || neighbor1.startsWith("t") || neighbor2.startsWith("t")) {
                                sum++;
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        Graph<String> graph = new Graph<>();
        for (var line : lines) {
            var a = line.split("-");
            graph.addEdge(a[0], a[1], 1);
            graph.addEdge(a[1], a[0], 1);
        }
        Set<String> best = Set.of();
        for (var clique : graph.findMaximalCliques()) if (clique.size() > best.size()) best = clique;
        List<String> sorted = new ArrayList<>(best);
        Collections.sort(sorted);
        return String.join(",", sorted);
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day23() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
