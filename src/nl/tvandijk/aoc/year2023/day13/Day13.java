package nl.tvandijk.aoc.year2023.day13;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day13 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var parts = Util.splitArray(lines, String::isBlank);
        long res = 0;
        for (var part : parts) {
            var grid = Grid.of(part.toArray(String[]::new), 'X');
            boolean wasvert = false;
            for (int i = 0; i < grid.width() - 1; i++) {
                // try if this column is a mirror?
                boolean mirror = true;
                for (int j = 0; j <= i; j++) {
                    if (i - j < 0 || i + 1 + j >= grid.width()) break;
                    for (int k = 0; k < grid.height(); k++) {
                        if (grid.get(i + 1 + j, k) != grid.get(i - j, k)) {
                            mirror = false;
                            break;
                        }
                    }
                    if (!mirror) break;
                }
                if (mirror) {
                    res += i + 1;
                    wasvert = true;
                    break;
                }
            }
            if (wasvert) continue;
            for (int i = 0; i < grid.height() - 1; i++) {
                // try if this row is a mirror?
                boolean mirror = true;
                for (int j = 0; j <= i; j++) {
                    if (i - j < 0 || i + 1 + j >= grid.height()) break;
                    for (int k = 0; k < grid.width(); k++) {
                        if (grid.get(k, i + 1 + j) != grid.get(k, i - j)) {
                            mirror = false;
                            break;
                        }
                    }
                    if (!mirror) break;
                }
                if (mirror) {
                    res += 100 * (i + 1);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    protected Object part2() {
        // part 2
        var parts = Util.splitArray(lines, String::isBlank);
        long res = 0;
        for (var part : parts) {
            var grid = Grid.of(part.toArray(String[]::new), 'X');
            boolean wasvert = false;
            for (int i = 0; i < grid.width() - 1; i++) {
                // try if this column is a mirror?
                long diff = 0;
                for (int k = 0; k < grid.height(); k++) {
                    for (int j = 0; j < grid.width(); j++) {
                        if (i - j < 0 || i + 1 + j >= grid.width()) break;
                        if (grid.get(i + 1 + j, k) != grid.get(i - j, k)) {
                            diff++;
                        }
                    }
                }
                if (diff == 1) {
                    res += i + 1;
                    wasvert = true;
                    break;
                }
            }
            if (wasvert) continue;
            for (int i = 0; i < grid.height() - 1; i++) {
                // try if this row is a mirror?
                long diff = 0;
                for (int k = 0; k < grid.width(); k++) {
                    for (int j = 0; j <= i; j++) {
                        if (i - j < 0 || i + 1 + j >= grid.height()) break;
                        if (grid.get(k, i + 1 + j) != grid.get(k, i - j)) {
                            diff++;
                        }
                    }
                }
                if (diff == 1) {
                    res += 100L * (i + 1);
                    break;
                }
            }
        }
        return res;
    }
}
