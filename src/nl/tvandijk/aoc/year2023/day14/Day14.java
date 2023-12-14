package nl.tvandijk.aoc.year2023.day14;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day14 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    private void tiltNorth(Grid g) {
        for (int x = 0; x < g.width(); x++) {
            // slide upwards until # for this column
            for (int y = 0; y < g.height(); y++) {
                // count number of O before #
                int count = 0;
                for (int yy = y; yy < g.height(); yy++) {
                    if (g.get(x, yy) == '#') break;
                    if (g.get(x, yy) == 'O') count++;
                }
                // move the O's up
                for (int i = 0; i < count; i++) {
                    g.set(x, y+i, 'O');
                }
                y += count;
                for (; y < g.height(); y++) {
                    if (g.get(x, y) == '#') break;
                    g.set(x, y, '.');
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

    private Map<Grid, Integer> trace = new HashMap<>();

    private void cycle() {
        tiltNorth(grid);
        grid.clockwise();
        tiltNorth(grid);
        grid.clockwise();
        tiltNorth(grid);
        grid.clockwise();
        tiltNorth(grid);
        grid.clockwise();
    }

    @Override
    protected Object part2() {
        // part 2
        for (int j = 0; j < 1000000000; j++) {
            cycle();
            var g = grid.copy();
            if (trace.containsKey(g)) {
                int start = trace.get(g);
                int period = j - start;
                int offset = (1000000000 - 1 - start) % period;
                for (int i = 0; i < offset; i++) {
                    cycle();
                }
                break;
            }
            trace.put(g, j);
        }
        // now calculate the load
        return grid.findAll('O').stream().mapToLong(p -> grid.height() - p.y).sum();
    }
}
