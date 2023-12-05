package nl.tvandijk.aoc.year2023.day3;

import java.util.*;
import java.util.regex.Pattern;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day3 extends Day {
    private boolean isSymbol(int x, int y) {
        char ch = grid.get(y, x);
        if (ch == '.') return false;
        return !Character.isDigit(ch);
    }

    private boolean findSymbol(int x, int y, int len) {
        // get length of digit
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j < len + 1; j++) {
                if (isSymbol(x+i, y+j)) return true;
            }
        }
        return false;
    }

    private final Map<Point, List<Integer>> gears = new HashMap<>();

    private void checkGear(int x, int y, int val) {
        if (grid.get(y, x) == '*') {
            gears.computeIfAbsent(Point.of(x, y), k -> new ArrayList<>()).add(val);
        }
    }

    private void findGears(int x, int y, int val) {
        checkGear(x-1, y-1, val);
        checkGear(x, y-1, val);
        checkGear(x+1, y-1, val);
        for (int i = y; i < lines[0].length(); i++) {
            if (!Character.isDigit(lines[x].charAt(i))) {
                checkGear(x-1, i, val);
                checkGear(x, i, val);
                checkGear(x+1, i, val);
                return;
            } else {
                checkGear(x - 1, i, val);
                checkGear(x + 1, i, val);
            }
        }
    }

    private static final Pattern pat = Pattern.compile("\\d+");

    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            var matcher = pat.matcher(lines[i]);
            for (int j = 0; matcher.find(j); j = matcher.end()) {
                int val = Integer.parseInt(matcher.group(0));
                if (findSymbol(i, matcher.start(), matcher.group(0).length())) {
                    sum += val;
                }
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        for (int i = 0; i < lines.length; i++) {
            var matcher = pat.matcher(lines[i]);
            for (int j = 0; matcher.find(j); j = matcher.end()) {
                int val = Integer.parseInt(matcher.group(0));
                findGears(i, matcher.start(), val);
            }
        }
        return gears.values().stream().filter(v -> v.size() > 1)
                .mapToInt(v -> v.stream().reduce(1, (a,b) -> a*b))
                .sum();
    }
}
