package nl.tvandijk.aoc.year2023.day11;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class  Day11 extends Day {
    private final Set<Integer> emptyRows = new HashSet<>();
    private final Set<Integer> emptyCols = new HashSet<>();

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
        for (int x = 0; x < grid.width(); x++) {
            int xx = x; // workaround lambda function wanting a final variable
            var empty = Arrays.stream(lines).allMatch(s -> s.charAt(xx) == '.');
            if (empty) emptyCols.add(x);
        }
        for (int y = 0; y < grid.width(); y++) {
            var empty = lines[y].chars().allMatch(ch -> ch == '.');
            if (empty) emptyRows.add(y);
        }
    }

    @Override
    protected Object part1() {
        // part 1
        List<Point> galaxies = new ArrayList<>();
        for (var p : grid.findAll('#')) {
            var x = p.x;
            var y = p.y;
            for (int i = 0; i < p.x; i++) {
                if (emptyCols.contains(i)) x += 1;
            }
            for (int i = 0; i < p.y; i++) {
                if (emptyRows.contains(i)) y+= 1;
            }
            galaxies.add(Point.of(x, y));
        }
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i+1; j < galaxies.size(); j++) {
                var d = galaxies.get(i).manhattan(galaxies.get(j));
                sum += d;
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        List<Point> galaxies = new ArrayList<>();
        for (var p : grid.findAll('#')) {
            var x = p.x;
            var y = p.y;
            for (int i = 0; i < p.x; i++) {
                if (emptyCols.contains(i)) x += 999999;
            }
            for (int i = 0; i < p.y; i++) {
                if (emptyRows.contains(i)) y += 999999;
            }
            galaxies.add(Point.of(x, y));
        }
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i+1; j < galaxies.size(); j++) {
                var d = galaxies.get(i).manhattan(galaxies.get(j));
                sum += d;
            }
        }
        return sum;
    }
}
