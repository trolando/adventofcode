package nl.tvandijk.aoc.year2023.day14;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day14 extends Day {
    private void tiltNorth(Grid g) {
        for (int x = 0; x < g.width(); x++) {
            // slide upwards until # for this column
            int lastempty = 0;
            for (int y = 0; y < g.height(); y++) {
                if (g.get(x, y) == '#') {
                    lastempty = y + 1;
                } else if (g.get(x, y) == 'O') {
                    if (lastempty != y) {
                        g.set(x, lastempty, 'O');
                        g.set(x, y, '.');
                    }
                    lastempty++;
                }
            }
        }
    }

    @Override
    protected Object part1() {
        // part 1
        tiltNorth(grid);
        // now calculate the load
        return grid.findAll('O').stream().mapToLong(p -> grid.height() - p.y).sum();
    }

    private final Map<Grid, Integer> trace = new HashMap<>();

    private void cycle() {
        tiltNorth(grid); // north
        grid.clockwise();
        tiltNorth(grid); // west
        grid.clockwise();
        tiltNorth(grid); // south
        grid.clockwise();
        tiltNorth(grid); // east
        grid.clockwise();
    }

    @Override
    protected Object part2() {
        // part 2
        for (int j = 0; j < 1000000000; j++) {
            cycle();
            if (trace.containsKey(grid)) {
                int start = trace.get(grid);
                int period = j - start;
                int offset = (1000000000 - 1 - start) % period;
                for (int i = 0; i < offset; i++) {
                    cycle();
                }
                break;
            }
            trace.put(grid.copy(), j);
        }
        // now calculate the load
        return grid.findAll('O').stream().mapToLong(p -> grid.height() - p.y).sum();
    }
}
