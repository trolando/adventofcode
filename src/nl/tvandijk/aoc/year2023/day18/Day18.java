package nl.tvandijk.aoc.year2023.day18;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day18 extends Day {
    @Override
    protected Object part1() {
        // part 1
        Point pt = Point.of(0, 0);
        Set<Point> g = new HashSet<>();
        g.add(pt);
        Set<Point> u = new HashSet<>();
        for (var l : lines) {
            var s = l.split(" ");
            var len = Integer.parseInt(s[1]);
            Direction dir = switch (s[0]) {
                case "R" -> Direction.RIGHT;
                case "L" -> Direction.LEFT;
                case "D" -> Direction.DOWN;
                case "U" -> Direction.UP;
                default -> null;
            };
            for (int i = 0; i < len; i++) {
                if (dir == Direction.UP) u.add(pt);
                pt = pt.to(dir);
                g.add(pt);
                if (dir == Direction.UP && i+1 != len) u.add(pt);
                if (dir == Direction.DOWN) u.add(pt);
            }
        }
        // create interior
        int minx =  g.stream().mapToInt(s -> (int) s.x).min().orElseThrow();
        int miny = g.stream().mapToInt(s -> (int) s.y).min().orElseThrow();
        int maxx = g.stream().mapToInt(s -> (int) s.x).max().orElseThrow();
        int maxy = g.stream().mapToInt(s -> (int) s.y).max().orElseThrow();
        for (int y = miny; y <= maxy; y++) {
            boolean inside = false;
            for (int x = minx; x <= maxx; x++) {
                if (u.contains(Point.of(x, y))) {
                    inside = !inside;
                    System.out.print('|');
                } else if (g.contains(Point.of(x, y))) {
                    System.out.print('#');
                } else {
                    if (inside) {
                        g.add(Point.of(x, y));
                        System.out.print('.');
                    } else {
                        System.out.print(' ');
                    }
                }
            }
            System.out.println();
        }
        return g.size();
    }

    @Override
    protected Object part2() {
        // part 2
        Point pt = Point.of(0, 0);
        Point prev = null;
        // Calculate with shoelace formula with first point in (0,0)
        // x1y2 - x2y1 + ... + xny1 - x1yn
        long area = 0;
        long boundarypoints = 0;
        for (var l : lines) {
            var s = l.split(" ");
            var len = Integer.parseInt(s[2].substring(2, 7), 16);
            Direction dir = switch (s[2].substring(7, 8)) {
                case "0" -> Direction.RIGHT;
                case "2" -> Direction.LEFT;
                case "1" -> Direction.DOWN;
                case "3" -> Direction.UP;
                default -> null;
            };
            boundarypoints += len;
            pt = pt.to(dir, len);
            if (prev != null) area += prev.x*pt.y-pt.x*prev.y;
            prev = pt;
        }
        area /= 2;
        // Pick's theorem: Area = i + b/2 - 1
        // where i is number of interior points and b is number of boundary points
        // thus the interior i = Area + 1 - b/2
        // however we want to include the boundary! so
        // points = Area + 1 + b/2
        return area + 1 + boundarypoints/2;
    }
}
