package nl.tvandijk.aoc.year2023.day21;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day21 extends Day {
    @Override
    protected Object part1() {
        // part 1
        grid.setDefault('X');
        var g = new Graph<Point>(pt -> {
            var res = new ArrayList<Pair<Point, Long>>();
            for (var ad : pt.adjacent(false)) {
                if (grid.get(ad) == '.' || grid.get(ad) == 'S') res.add(Pair.of(ad, 1L));
            }
            return res;
        });
        Set<Point> layer = new HashSet<>();
        layer.addAll(grid.findAll('S'));
        for (int i = 0; i < 64; i++) {
            Set<Point> next = new HashSet<>();
            for (var pt : layer) {
                next.addAll(g.getAdjacentNodes(pt));
            }
            layer = next;
        }
        return layer.size();
    }

    @Override
    protected Object part2() {
        // part 2
        grid.setDefault('X');
        grid.setRepeats(true);
        var g = new Graph<Point>(pt -> {
            var res = new ArrayList<Pair<Point, Long>>();
            for (var ad : pt.adjacent(false)) {
                if (grid.get(ad) != '#') res.add(Pair.of(ad, 1L));
            }
            return res;
        });
        Set<Point> layer = new HashSet<>(grid.findAll('S'));
        List<Long> sizes = new ArrayList<>();
        var goal = 26501365L;
        for (int i = 0; i < 100000; i++) {
            if ((i % grid.width()) == (goal % grid.width())) {
                sizes.add((long) layer.size());
                if (sizes.size() == 3) {
                    var steps = goal / grid.width();
                    var delta1 = sizes.get(1) - sizes.get(0);
                    var delta2 = sizes.get(2) - 2L * sizes.get(1) + sizes.get(0);
                    return sizes.get(0) + delta1 * steps + delta2 * steps * (steps - 1) / 2;
                }
            }
            Set<Point> next = new HashSet<>();
            for (var pt : layer) {
                next.addAll(g.getAdjacentNodes(pt));
            }
            layer = next;
        }
        return null;
    }

    /**
     * Default construct initializes inputs to example.txt and input.txt
     */
    public Day21() {
        super("input.txt");
    }
}
