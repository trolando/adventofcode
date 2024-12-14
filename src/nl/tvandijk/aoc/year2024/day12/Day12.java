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
                // compute perimeter
                long perim = 0L;
                for (Point p : block) {
                    boolean up = block.contains(p.up());
                    boolean down = block.contains(p.down());
                    boolean left = block.contains(p.left());
                    boolean right = block.contains(p.right());
                    if (!up && !left) perim++;
                    if (up && left && !block.contains(p.up().left())) perim++;
                    if (!up && !right) perim++;
                    if (up && right && !block.contains(p.up().right())) perim++;
                    if (!down && !left) perim++;
                    if (down && left && !block.contains(p.down().left())) perim++;
                    if (!down && !right) perim++;
                    if (down && right && !block.contains(p.down().right())) perim++;
                }
                sum += perim * block.size();
                seen.addAll(block);
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }
}
