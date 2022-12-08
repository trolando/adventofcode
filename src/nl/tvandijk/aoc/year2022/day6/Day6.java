package nl.tvandijk.aoc.year2022.day6;

import nl.tvandijk.aoc.common.Day;

import java.util.HashSet;

public class Day6 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
    }

    private boolean check(String s) {
        var set = new HashSet<Character>();
        for (var ch : s.toCharArray()) set.add(ch);
        return set.size() == s.length();
    }

    @Override
    protected Object part1() {
        var line = lines[0];
        for (int i = 0; i < line.length(); i++) {
            if (check(line.substring(i, i+4))) return i+4;
        }
        return null;
    }

    @Override
    protected Object part2() {
        var line = lines[0];
        for (int i = 0; i < line.length(); i++) {
            if (check(line.substring(i, i+14))) return i+14;
        }
        return null;
    }
}
