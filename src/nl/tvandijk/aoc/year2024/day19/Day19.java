package nl.tvandijk.aoc.year2024.day19;

import java.util.*;
import nl.tvandijk.aoc.common.Day;

public class Day19 extends Day {
    Map<String, Long> memoCount = new HashMap<>();

    long count(String pattern, String[] patterns) {
        if (pattern.isEmpty()) return 1;
        if (memoCount.containsKey(pattern)) return memoCount.get(pattern);
        long res = 0;
        for (var p : patterns) {
            if (pattern.startsWith(p)) {
                res += count(pattern.substring(p.length()), patterns);
            }
        }
        memoCount.put(pattern, res);
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        var parts = fileContents.split("\n\n");
        var patterns = parts[0].trim().split(", ");
        int sum=0;
        for (var p : parts[1].trim().split("\n")) {
            if (count(p, patterns)>0) sum++;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        var parts = fileContents.split("\n\n");
        var patterns = parts[0].trim().split(", ");
        long sum=0;
        for (var p : parts[1].trim().split("\n")) {
            sum += count(p, patterns);
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day19() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
