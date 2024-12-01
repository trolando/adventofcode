package nl.tvandijk.aoc.year2023.day25;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day25 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var g = new Graph<String>();
        for (var line : lines) {
            var parts = line.split("[: ]+");
            for (int i = 1; i < parts.length; i++) {
                g.addEdge(parts[0], parts[i], 1L);
                g.addEdge(parts[i], parts[0], 1L);
            }
        }
        var minCut = g.minCut();
        return (long) minCut.b.size() * minCut.c.size();
    }

    @Override
    protected Object part2() {
        // part 2
        return null;
    }
}
