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
        int[] xs = new int[grid.width()];
        int x = 0;
        for (int i = 0; i < grid.width(); i++) {
            x += emptyCols.contains(i) ? 2 : 1;
            xs[i] = x;
        }
        int[] ys = new int[grid.height()];
        int y = 0;
        for (int i = 0; i < grid.height(); i++) {
            y += emptyRows.contains(i) ? 2 : 1;
            ys[i] = y;
        }
        var galaxies = grid.findAll('#').stream().map(p -> Point.of(xs[(int)p.x], ys[(int)p.y])).toList();
        return Util.getPairs(galaxies).stream().mapToLong(p -> p.a.manhattan(p.b)).sum();
    }

    @Override
    protected Object part2() {
        // part 2
        int[] xs = new int[grid.width()];
        int x = 0;
        for (int i = 0; i < grid.width(); i++) {
            x += emptyCols.contains(i) ? 1000000 : 1;
            xs[i] = x;
        }
        int[] ys = new int[grid.height()];
        int y = 0;
        for (int i = 0; i < grid.height(); i++) {
            y += emptyRows.contains(i) ? 1000000 : 1;
            ys[i] = y;
        }
        var galaxies = grid.findAll('#').stream().map(p -> Point.of(xs[(int)p.x], ys[(int)p.y])).toList();
        return Util.getPairs(galaxies).stream().mapToLong(p -> p.a.manhattan(p.b)).sum();
    }
}
