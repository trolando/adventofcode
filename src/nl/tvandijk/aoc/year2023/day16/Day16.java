package nl.tvandijk.aoc.year2023.day16;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day16 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    Set<Pair<Point, Integer>> points = new HashSet<>();
    Set<Pair<Point, Integer>> queue = new HashSet<>();

    private void add(Point next, int dir) {
        if (next.x < 0) return;
        if (next.y < 0) return;
        if (next.x >= grid.width()) return;
        if (next.y >= grid.height()) return;
        if (!points.contains(Pair.of(next, dir))) {
            points.add(Pair.of(next, dir));
            queue.add(Pair.of(next, dir));
        }
    }

    @Override
    protected Object part1() {
        // part 1
        return energize(grid, Point.of(0, 0), 0);
    }

    private int energize(Grid grid, Point start, int dir) {
        points.clear();
        queue.clear();
        points.add(Pair.of(start, dir));
        queue.add(Pair.of(start, dir));
        while (!queue.isEmpty()) {
            // take element from queue
            var p = queue.iterator().next();
            System.out.println(p);
            queue.remove(p);
            if (p.b == 0) {
                if (grid.get(p.a) == '.') {
                    add(p.a.right(), 0);
                } else if (grid.get(p.a) == '-') {
                    add(p.a.right(), 0);
                } else if (grid.get(p.a) == '|') {
                    add(p.a.down(), 2);
                    add(p.a.up(), 3);
                } else if (grid.get(p.a) == '/') {
                    add(p.a.up(), 3);
                } else if (grid.get(p.a) == '\\') {
                    add(p.a.down(), 2);
                }
            } else if (p.b == 1) {
                if (grid.get(p.a) == '.') {
                    add(p.a.left(), 1);
                } else if (grid.get(p.a) == '-') {
                    add(p.a.left(), 1);
                } else if (grid.get(p.a) == '|') {
                    add(p.a.down(), 2);
                    add(p.a.up(), 3);
                } else if (grid.get(p.a) == '/') {
                    add(p.a.down(), 2);
                } else if (grid.get(p.a) == '\\') {
                    add(p.a.up(), 3);
                }
            } else if (p.b == 2) {
                if (grid.get(p.a) == '.') {
                    add(p.a.down(), 2);
                } else if (grid.get(p.a) == '-') {
                    add(p.a.left(), 1);
                    add(p.a.right(), 0);
                } else if (grid.get(p.a) == '|') {
                    add(p.a.down(), 2);
                } else if (grid.get(p.a) == '/') {
                    add(p.a.left(), 1);
                } else if (grid.get(p.a) == '\\') {
                    add(p.a.right(), 0);
                }
            } else if (p.b == 3) {
                if (grid.get(p.a) == '.') {
                    add(p.a.up(), 3);
                } else if (grid.get(p.a) == '-') {
                    add(p.a.left(), 1);
                    add(p.a.right(), 0);
                } else if (grid.get(p.a) == '|') {
                    add(p.a.up(), 3);
                } else if (grid.get(p.a) == '/') {
                    add(p.a.right(), 0);
                } else if (grid.get(p.a) == '\\') {
                    add(p.a.left(), 1);
                }
            }
        }
        // set of all tiles
        var set = points.stream().map(p -> p.a).collect(Collectors.toSet());
        var c = grid.copy();
        for (var p : set) {
            c.set((int)p.x, (int)p.y, 'X');
        }
        System.out.println(c);
        return set.size();
    }

    @Override
    protected Object part2() {
        // part 2
        var max = 0L;
        for (int x = 0; x < grid.width(); x++) {
            max = Math.max(energize(grid.copy(), Point.of(x, 0), 2), max);
            max = Math.max(energize(grid.copy(), Point.of(x, grid.height()-1), 3), max);
        }
        for (int y = 0; y < grid.height(); y++) {
            max = Math.max(energize(grid.copy(), Point.of(0, y), 0), max);
            max = Math.max(energize(grid.copy(), Point.of(grid.width()-1, y), 1), max);
        }
        return max;
    }
}
