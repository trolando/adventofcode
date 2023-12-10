package nl.tvandijk.aoc.year2023.day10;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Graph;
import nl.tvandijk.aoc.util.Grid;
import nl.tvandijk.aoc.util.Pair;
import nl.tvandijk.aoc.util.Point;

import java.util.*;

public class Day10 extends Day {
    private Grid g;

    private Collection<Pair<Point, Long>> successor(Point p) {
        var res = new ArrayList<Pair<Point, Long>>();
        switch (g.get((int) p.x, (int) p.y)) {
            case '|' -> {
                res.add(Pair.of(Point.of(p.x, p.y - 1), 1L));
                res.add(Pair.of(Point.of(p.x, p.y + 1), 1L));
            }
            case '-' -> {
                res.add(Pair.of(Point.of(p.x - 1, p.y), 1L));
                res.add(Pair.of(Point.of(p.x + 1, p.y), 1L));
            }
            case '7' -> {
                res.add(Pair.of(Point.of(p.x - 1, p.y), 1L));
                res.add(Pair.of(Point.of(p.x, p.y + 1), 1L));
            }
            case 'L' -> {
                res.add(Pair.of(Point.of(p.x, p.y - 1), 1L));
                res.add(Pair.of(Point.of(p.x + 1, p.y), 1L));
            }
            case 'F' -> {
                res.add(Pair.of(Point.of(p.x + 1, p.y), 1L));
                res.add(Pair.of(Point.of(p.x, p.y + 1), 1L));
            }
            case 'J' -> {
                res.add(Pair.of(Point.of(p.x, p.y - 1), 1L));
                res.add(Pair.of(Point.of(p.x - 1, p.y), 1L));
            }
            case 'S' -> {
                for (var s : successor(Point.of(p.x-1, p.y))) {
                    if (s.a.equals(p)) res.add(Pair.of(Point.of(p.x-1, p.y), 1L));
                }
                for (var s : successor(Point.of(p.x+1, p.y))) {
                    if (s.a.equals(p)) res.add(Pair.of(Point.of(p.x+1, p.y), 1L));
                }
                for (var s : successor(Point.of(p.x, p.y-1))) {
                    if (s.a.equals(p)) res.add(Pair.of(Point.of(p.x, p.y-1), 1L));
                }
                for (var s : successor(Point.of(p.x, p.y+1))) {
                    if (s.a.equals(p)) res.add(Pair.of(Point.of(p.x, p.y+1), 1L));
                }
            }
            default -> {
            }
        }
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        g = Grid.of(lines);
        Point start = null;
        var graph = new Graph<>(this::successor);
        for (int x = 0; x < g.width(); x++) {
            for (int y = 0; y < g.height(); y++) {
                if (g.get(x, y) == 'S') {
                    start = Point.of(x, y);
                }
            }
        }
        var res = graph.reachAll(List.of(start));
        return res.values().stream().mapToLong(x -> x).max().orElseThrow();
    }

    private Collection<Pair<Point, Long>> succ2(Point p, Collection<Point> locs, Graph<Point> graph) {
        var res = new ArrayList<Pair<Point, Long>>();
        // translate to a "real point" ...
        Point pp = Point.of(p.x/2, p.y/2);
        boolean sqx = (p.x % 2 == 1);
        boolean sqy = (p.y % 2 == 1);
        if (!sqx && !sqy) {
            // add all points
            if (p.x > 0) res.add(Pair.of(Point.of(p.x-1, p.y), 1L));
            if (p.x + 1 < 2L*g.width()) res.add(Pair.of(Point.of(p.x+1, p.y), 1L));
            if (p.y > 0) res.add(Pair.of(Point.of(p.x, p.y-1), 1L));
            if (p.y + 1 < 2L*g.height()) res.add(Pair.of(Point.of(p.x, p.y+1), 1L));
        } else if (sqx && sqy) {
            // only allow vertical if no connection north...
            // try north first
            var NW = Point.of(pp.x, pp.y);
            var NE = Point.of(pp.x + 1, pp.y);
            var SW = Point.of(pp.x, pp.y + 1);
            var SE = Point.of(pp.x + 1, pp.y + 1);
            if (p.y > 0) {
                if (!locs.contains(NW) || !locs.contains(NE) || !graph.getEdges(NW).containsKey(NE)) {
                    // squeeze north
                    res.add(Pair.of(Point.of(p.x, p.y - 1), 1L));
                }
            }
            if (p.y + 1 < 2*g.height()) {
                if (!locs.contains(SW) || !locs.contains(SE) || !graph.getEdges(SW).containsKey(SE)) {
                    // squeeze south
                    res.add(Pair.of(Point.of(p.x, p.y + 1), 1L));
                }
            }
            // squeeze horizontal
            if (p.x > 0) {
                if (!locs.contains(NW) || !locs.contains(SW) || !graph.getEdges(NW).containsKey(SW)) {
                    // squeeze west
                    res.add(Pair.of(Point.of(p.x - 1, p.y), 1L));
                }
            }
            if (p.x + 1 < 2*g.width()) {
                if (!locs.contains(NE) || !locs.contains(SE) || !graph.getEdges(NE).containsKey(SE)) {
                    // squeeze east
                    res.add(Pair.of(Point.of(p.x + 1, p.y), 1L));
                }
            }
        } else if (sqx) {
            // only squeeze vertical
            if (p.y > 0) res.add(Pair.of(Point.of(p.x, p.y-1), 1L));
            if (p.y + 1 < 2L*g.height()) res.add(Pair.of(Point.of(p.x, p.y+1), 1L));
            // normal move horizontal
            if (p.x > 0 && !locs.contains(Point.of(pp.x, pp.y))) res.add(Pair.of(Point.of(p.x-1, p.y), 1L));
            if (p.x + 1 < 2L*g.width() && !locs.contains(Point.of(pp.x+1, pp.y))) res.add(Pair.of(Point.of(p.x+1, p.y), 1L));
        } else if (sqy) {
            // only squeeze horizontal
            if (p.x > 0) res.add(Pair.of(Point.of(p.x-1, p.y), 1L));
            if (p.x + 1 < 2L*g.width()) res.add(Pair.of(Point.of(p.x+1, p.y), 1L));
            // normal move vertical
            if (p.y > 0 && !locs.contains(Point.of(pp.x, pp.y))) res.add(Pair.of(Point.of(p.x, p.y-1), 1L));
            if (p.y + 1 < 2L*g.height() && !locs.contains(Point.of(pp.x, pp.y+1))) res.add(Pair.of(Point.of(p.x, p.y+1), 1L));
        }
        return res;
    }

    @Override
    protected Object part2() {
        // part 2
        g = Grid.of(lines, 'X');
        Point start = null;
        var graph = new Graph<>(this::successor);
        for (int x = 0; x < g.width(); x++) {
            for (int y = 0; y < g.height(); y++) {
                if (g.get(x, y) == 'S') {
                    start = Point.of(x, y);
                }
            }
        }
        var res = graph.reachAll(List.of(start));
        var locs = res.keySet();
        Set<Point> startingPoints = new HashSet<>();
        // get starting positions
        for (int x = 0; x < g.width(); x++) {
            if (!locs.contains(Point.of(x, 0))) startingPoints.add(Point.of(2L*x, 0));
            if (!locs.contains(Point.of(x, g.height() - 1))) startingPoints.add(Point.of(2L*x, 2*(g.height() - 1L)));
        }
        for (int y = 0; y < g.height(); y++) {
            if (!locs.contains(Point.of(0, y))) startingPoints.add(Point.of(0, 2L*y));
            if (!locs.contains(Point.of(g.width() - 1, y))) startingPoints.add(Point.of(2L*(g.width() - 1), 2L*y));
        }
        var g2 = new Graph<Point>(p -> succ2(p, locs, graph));
        var res2 = g2.reachAll(startingPoints);
        var locs2 = res2.keySet();
        // print grid and count
        long count = 0;
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                if (locs2.contains(Point.of(2L*x, 2L*y))) {
                    System.out.print("\033[31mO\033[m");
                } else if (locs.contains(Point.of(x, y))) {
                    System.out.print("\033[34m"+g.get(x, y)+"\033[m");
                } else {
                    count++;
                    System.out.print("\033[1;38;5;46mI\033[m");
                }
            }
            System.out.println();
        }
        return count;
    }

    public Day10() {
        super("example.txt", "example2.txt", "example3.txt", "input.txt");
    }
}
