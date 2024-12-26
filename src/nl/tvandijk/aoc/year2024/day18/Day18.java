package nl.tvandijk.aoc.year2024.day18;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Graph;
import nl.tvandijk.aoc.util.Grid;
import nl.tvandijk.aoc.util.Point;
import nl.tvandijk.aoc.util.Util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day18 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var pts = Arrays.stream(lines).map(this::numbers).map(Point::of).toList();
        long W = 71;
        long H = 71;
        var grid = Grid.of(pts.subList(0, 1024), W, H);
        var graph = grid.graph(Point::adjacent4, ch -> ch != '#');
        return graph.dijkstra(Point.of(0,0), Point.of(W-1,H-1));
    }

    @Override
    protected Object part2() {
        // part 2
        var pts = Arrays.stream(lines).map(this::numbers).map(Point::of).toList();
        long W = 71;
        long H = 71;
        var first = Util.bisect(1024, pts.size(), idx -> {
            var grid = Grid.of(pts.subList(0, idx), W, H);
            var graph = grid.graph(Point::adjacent4, ch -> ch != '#');
            return graph.dijkstra(Point.of(0,0), Point.of(W-1,H-1)) == Long.MAX_VALUE;
        });
        return String.format("%d,%d", pts.get(first-1).x, pts.get(first-1).y);
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day18() {
//        super("example.txt", "input.txt");
        super("input.txt");
    }
}
