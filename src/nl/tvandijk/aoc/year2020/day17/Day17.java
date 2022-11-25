package nl.tvandijk.aoc.year2020.day17;

import nl.tvandijk.aoc.common.Day;

public class Day17 extends Day {
    @Override
    protected Object part1() {
        var space = new Space();

        int y=0;
        for (var line : lines) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') space.add(x, y, 0);
            }
            y++;
        }

        for (int i = 0; i < 6; i++) {
            space.applyRules();
//            System.out.printf("Count after %d: %d\n", i+1, space.size());
        }

        return space.size();
    }

    @Override
    protected Object part2() throws Exception {
        var space = new Space();

        int y=0;
        for (var line : lines) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') space.add(x, y, 0, 0);
            }
            y++;
        }

        for (int i = 0; i < 6; i++) {
            space.applyRules();
//            System.out.printf("Count after %d: %d\n", i+1, space.size());
        }

        return space.size();
    }
}