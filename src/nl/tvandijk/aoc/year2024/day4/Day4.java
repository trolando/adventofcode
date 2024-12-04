package nl.tvandijk.aoc.year2024.day4;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day4 extends Day {
    int testXMAS(Point pt, Point dir) {
        return grid.get(pt) == 'X' &&
                grid.get(pt.add(dir, 1)) == 'M' &&
                grid.get(pt.add(dir, 2)) == 'A' &&
                grid.get(pt.add(dir, 3)) == 'S' ? 1 : 0;
    }

    @Override
    protected Object part1() {
        // part 1
        return grid.points().mapToInt(pt -> Point.dirs8().mapToInt(dir -> testXMAS(pt, dir)).sum()).sum();
    }

    boolean testMAS(long x, long y, int cx, int cy) {
        return grid.get(x, y) == 'M' &&
                grid.get(x + cx, y + cy) == 'A' &&
                grid.get(x + cx + cx, y + cy + cy) == 'S';
    }

    boolean testMASMAS(long x, long y) {
        return (testMAS(x - 1, y - 1, 1, 1) || testMAS(x + 1, y + 1, -1, -1)) &&
                (testMAS(x + 1, y - 1, -1, 1) || testMAS(x - 1, y + 1, 1, -1));
    }

    @Override
    protected Object part2() {
        // part 2
        return grid.points().mapToInt(pt -> testMASMAS(pt.x, pt.y) ? 1 : 0).sum();
    }
}
