package nl.tvandijk.aoc.year2024.day20;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day20 extends Day {
    long compute(int maxDistance) {
        var graph = grid.graph(Point::adjacent4, ch -> ch != '#');

        var fromStart = graph.reachAll(grid.findOne('S'));
        var fromEnd = graph.reachAll(grid.findOne('E'));
        return fromStart.keySet().stream().filter(fromStart::containsKey).mapToInt(pt -> {
            int count = 0;
            for (int dx = -maxDistance; dx <= maxDistance; dx++) {
                for (int dy = -maxDistance; dy <= maxDistance; dy++) {
                    var other = pt.delta(dx, dy);
                    if (fromEnd.containsKey(other)) {
                        var man = pt.manhattan(other);
                        if (man < 2 || man > maxDistance) continue;
                        var diff = fromEnd.get(pt) - man - fromEnd.get(other);
                        if (diff >= 100) ++count;
                    }
                }
            }
            return count;
        }).sum();
    }

    @Override
    protected Object part1() {
        // part 1
        return compute(2);
    }

    @Override
    protected Object part2() {
        // part 2
        return compute(20);
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day20() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
