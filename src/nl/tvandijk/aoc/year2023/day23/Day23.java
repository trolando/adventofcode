package nl.tvandijk.aoc.year2023.day23;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

import static nl.tvandijk.aoc.util.Direction.*;

public class Day23 extends Day {
    Direction[] dirs = new Direction[]{UP, DOWN, LEFT, RIGHT};

    @Override
    protected Object part1() {
        // part 1
        grid.setDefault('#');
        var graph = new Graph<Point>(pt -> {
            var res = new ArrayList<Pair<Point, Long>>();
            for (var dir : dirs) {
                var to = pt.to(dir);
                if (grid.get(to) == '#') continue;
                // check slopes
                if (grid.get(to) != '.') {
                    if (dir == RIGHT && grid.get(to) != '>') continue;
                    if (dir == LEFT && grid.get(to) != '<') continue;
                    if (dir == UP && grid.get(to) != '^') continue;
                    if (dir == DOWN && grid.get(to) != 'v') continue;
                }
                res.add(Pair.of(to, 1L));
            }
            return res;
        }).edgeCompress();
        var res = graph.longestPath(Point.of(1, 0), Point.of(grid.width() - 2, grid.height() - 1));
        return res.b;
    }

    @Override
    protected Object part2() {
        // part 2
        grid.setDefault('#');
        var graph = new Graph<Point>(pt -> {
            var res = new ArrayList<Pair<Point, Long>>();
            for (var dir : dirs) {
                var to = pt.to(dir);
                if (grid.get(to) != '#') res.add(Pair.of(to, 1L));
            }
            return res;
        }).edgeCompress();
        var res = graph.longestPath(Point.of(1L, 0L), Point.of(grid.width() - 2L, grid.height() - 1L));
        return res.b;
    }
}
