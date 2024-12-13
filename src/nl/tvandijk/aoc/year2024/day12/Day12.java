package nl.tvandijk.aoc.year2024.day12;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.DirectedPoint;
import nl.tvandijk.aoc.util.Direction;
import nl.tvandijk.aoc.util.Point;

import java.util.*;

public class Day12 extends Day {
    @Override
    protected Object part1() {
        // part 1
        long sum = 0L;
        Set<Point> seen = new HashSet<>();
        List<Point> queue = new ArrayList<>();
        for (var entry : grid) {
            var pt = entry.a;
            if (seen.contains(pt)) continue;
            Set<Point> block = new HashSet<>();
            queue.add(pt);
            block.add(pt);
            while (!queue.isEmpty()) {
                var p = queue.removeLast();
                Point.dirs4().forEach(dir -> {
                    var pp = p.plus(dir);
                    if (!seen.contains(pp) && !block.contains(pp) && grid.get(pp) == grid.get(pt)) {
                        block.add(pp);
                        queue.add(pp);
                    }
                });
            }
            // check perimeter
            long perim = 0L;
            for (Point p : block) {
                perim += Point.dirs4().filter(dir -> {
                    var pp = p.plus(dir);
                    if (!block.contains(pp)) {
                        return true;
                    }
                    return false;
                }).count();

            }
            sum += perim * block.size();
            seen.addAll(block);
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum = 0L;
        Set<Point> seen = new HashSet<>();
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                var pt = Point.of(x, y);
                if (seen.contains(pt)) {
                    continue;
                }
                Set<Point> block = new HashSet<>();
                List<Point> queue = new ArrayList<>();
                queue.add(pt);
                block.add(pt);
                while (!queue.isEmpty()) {
                    var p = queue.removeLast();
                    Point.dirs4().forEach(dir -> {
                        var pp = p.plus(dir);
                        if (grid.inRange(pp) && !seen.contains(pp) && !block.contains(pp) && grid.get(pp) == grid.get(pt)) {
                            block.add(pp);
                            queue.add(pp);
                        }
                    });
                }
                // check perimeter
                long perim = 0L;
                // sort all points
                Set<DirectedPoint> seenPerim = new HashSet<>();
                var sorted = new ArrayList<>(block);
                Collections.sort(sorted, (a, b) -> {
                    if (a.y == b.y) return Long.compare(a.x, b.x);
                    else return Long.compare(a.y, b.y);
                });
                for (var p : sorted) {
                    if (!block.contains(p.up()) && !seenPerim.contains(DirectedPoint.of(p, Direction.UP))) {
                        perim++;
                        var pp = p;
                        while (grid.inRange(pp) && block.contains(pp) && !block.contains(pp.up())) {
                            seenPerim.add(DirectedPoint.of(pp, Direction.UP));
                            pp = pp.right();
                        }
                    }
                    if (!block.contains(p.right()) && !seenPerim.contains(DirectedPoint.of(p, Direction.RIGHT))) {
                        perim++;
                        var pp = p;
                        while (grid.inRange(pp) && block.contains(pp) && !block.contains(pp.right())) {
                            seenPerim.add(DirectedPoint.of(pp, Direction.RIGHT));
                            pp = pp.down();
                        }
                    }
                    if (!block.contains(p.down()) && !seenPerim.contains(DirectedPoint.of(p, Direction.DOWN))) {
                        perim++;
                        var pp = p;
                        while (grid.inRange(pp) && block.contains(pp) && !block.contains(pp.down())) {
                            seenPerim.add(DirectedPoint.of(pp, Direction.DOWN));
                            pp = pp.right();
                        }
                    }
                    if (!block.contains(p.left()) && !seenPerim.contains(DirectedPoint.of(p, Direction.LEFT))) {
                        perim++;
                        var pp = p;
                        while (grid.inRange(pp) && block.contains(pp) && !block.contains(pp.left())) {
                            seenPerim.add(DirectedPoint.of(pp, Direction.LEFT));
                            pp = pp.down();
                        }
                    }
                }
                sum += perim * block.size();
                seen.addAll(block);
            }
        }
        return sum;
    }
}
