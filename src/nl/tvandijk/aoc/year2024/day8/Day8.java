package nl.tvandijk.aoc.year2024.day8;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day8 extends Day {
    @Override
    protected Object part1() {
        // part 1
        Set<Point> locs = new HashSet<>();
        for (var freq : grid.values()) {
            if (freq == '.') continue;
            for (var perm : Permutations.asIterable(grid.findAll(freq), 2, false)) {
                var p1 = perm.get(0);
                var p2 = perm.get(1);
                var diff = p2.minus(p1);
                var p3 = p2.plus(diff);
                var p4 = p1.minus(diff);
                if (grid.inRange(p3)) locs.add(p3);
                if (grid.inRange(p4)) locs.add(p4);
            }
        }
        return locs.size();
    }

    @Override
    protected Object part2() {
        // part 2
        Set<Point> locs = new HashSet<>();
        for (var freq : grid.values()) {
            if (freq == '.') continue;
            for (var perm : Permutations.asIterable(grid.findAll(freq), 2, false)) {
                var p1 = perm.get(0);
                var p2 = perm.get(1);
                var diff = p2.minus(p1);
                while (grid.inRange(p2)) {
                    locs.add(p2);
                    p2 = p2.plus(diff);
                }
                while (grid.inRange(p1)) {
                    locs.add(p1);
                    p1 = p1.minus(diff);
                }
            }
        }
        return locs.size();
    }
}
