package nl.tvandijk.aoc.year2024.day10;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

import java.util.*;

public class Day10 extends Day {
    Map<Point, Integer> heights = new HashMap<>();

    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        grid.points().forEach(pt -> heights.put(pt, Integer.valueOf(""+grid.get(pt))));
    }

    void reachNine(Point pt, List<Point> collect) {
        if (heights.getOrDefault(pt, -1) == 9) {
            collect.add(pt);
        } else {
            var cur = heights.get(pt);
            if (heights.getOrDefault(pt.up(), 100) - cur == 1) reachNine(pt.up(), collect);
            if (heights.getOrDefault(pt.left(), 100) - cur == 1) reachNine(pt.left(), collect);
            if (heights.getOrDefault(pt.right(), 100) - cur == 1) reachNine(pt.right(), collect);
            if (heights.getOrDefault(pt.down(), 100) - cur == 1) reachNine(pt.down(), collect);
        }
    }

    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (var head : grid.findAll('0')) {
            var l = new ArrayList<Point>();
            reachNine(head, l);
            sum += Set.copyOf(l).size();
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        int sum = 0;
        for (var head : grid.findAll('0')) {
            var l = new ArrayList<Point>();
            reachNine(head, l);
            sum += l.size();
        }
        return sum;
    }
}
