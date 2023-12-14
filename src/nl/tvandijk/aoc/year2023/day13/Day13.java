package nl.tvandijk.aoc.year2023.day13;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Grid;
import nl.tvandijk.aoc.util.Util;

public class Day13 extends Day {
    private long computeMirror(Grid grid, int wantedDiff) {
        for (int i = 0; i < grid.width() - 1; i++) {
            // try if this column is a mirror?
            long diff = 0;
            for (int k = 0; k < grid.height(); k++) {
                for (int j = 0; j < grid.width(); j++) {
                    if (i - j < 0 || i + 1 + j >= grid.width()) break;
                    if (grid.get(i + 1 + j, k) != grid.get(i - j, k)) diff++;
                }
            }
            if (diff == wantedDiff) return i + 1L;
        }
        for (int i = 0; i < grid.height() - 1; i++) {
            // try if this row is a mirror?
            long diff = 0;
            for (int k = 0; k < grid.width(); k++) {
                for (int j = 0; j < grid.height(); j++) {
                    if (i - j < 0 || i + 1 + j >= grid.height()) break;
                    if (grid.get(k, i + 1 + j) != grid.get(k, i - j)) diff++;
                }
            }
            if (diff == wantedDiff) return 100L * (i + 1);
        }
        return 0L; // no mirror?
    }

    @Override
    protected Object part1() {
        // part 1
        var parts = Util.splitArray(lines, String::isBlank);
        return parts.stream().mapToLong(p -> computeMirror(Grid.of(p), 0)).sum();
    }

    @Override
    protected Object part2() {
        // part 2
        var parts = Util.splitArray(lines, String::isBlank);
        return parts.stream().mapToLong(p -> computeMirror(Grid.of(p), 1)).sum();
    }
}
