package nl.tvandijk.aoc.year2018.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;

public class Day2 extends Day {
    @Override
    protected Object part1() {
        // part 1
        int twos = 0;
        int threes = 0;
        for (var l : lines) {
            Map<Character, Integer> counts = new HashMap<>();
            for (var c : l.toCharArray()) {
                counts.compute(c, (key, value) -> {
                    if (value == null) return 1;
                    else return value + 1;
                });
            }
            boolean two = false;
            boolean three = false;
            for (var c : counts.entrySet()) {
                if (c.getValue() == 2) {
                    two = true;
                }
                if (c.getValue() == 3) {
                    three = true;
                }
            }
            if (two) twos++;
            if (three) threes++;
        }
        return twos * threes;
    }

    private String check(String a, String b) {
        // count number of different characters between a and b
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) diff++;
        }
        if (diff == 1) {
            // get all characters that are the same
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) == b.charAt(i)) sb.append(a.charAt(i));
            }
            return sb.toString();
        }
        return null;
    }

    @Override
    protected Object part2() {
        // part 2
        for (var l : lines) {
            for (var m : lines) {
                String r = check(l, m);
                if (r != null) return r;
            }
        }
        return null;
    }
}
