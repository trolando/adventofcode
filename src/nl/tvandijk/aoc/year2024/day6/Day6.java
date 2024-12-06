package nl.tvandijk.aoc.year2024.day6;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day6 extends Day {
    @Override
    protected Object part1() {
        // part 1
        grid.setDefault(' ');
        var start = DirectedPoint.of(grid.findOne('^'), Direction.UP);
        var visited = walk(start);
        assert visited != null;
        return visited.size();
    }

    private Set<Point> walk(DirectedPoint start) {
        var dir = start;
        Set<DirectedPoint> visited = new HashSet<>();
        while (grid.get(dir.p) != ' ') {
            if (visited.contains(dir)) return null; // loop!
            visited.add(dir);
            if (grid.get(dir.forward().p) == '#') {
                dir = dir.rotateRight();
            } else {
                dir = dir.forward();
            }
        }
        return visited.stream().map(d -> d.p).collect(Collectors.toSet());
    }

    @Override
    protected Object part2() {
        // part 2
        grid.setDefault(' ');
        var start = DirectedPoint.of(grid.findOne('^'), Direction.UP);
        int sum=0;
        for (var p : walk(start)) {
            grid.set(p, '#');
            if (walk(start) == null) sum++;
            grid.set(p, '.');
        }
        return sum;
    }
}
