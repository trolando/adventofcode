package nl.tvandijk.aoc.year2023.day6;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;
import java.util.stream.LongStream;

public class Day6 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var t = Arrays.stream(lines[0].split("\\s+")).skip(1).mapToInt(Integer::parseInt).toArray();
        var d = Arrays.stream(lines[1].split("\\s+")).skip(1).mapToInt(Integer::parseInt).toArray();
        long s = 1;
        for (int i = 0; i < t.length; i++) {
            int k = i;
            s *= LongStream.range(0, t[k]).parallel().map(x -> x*(t[k]-x)).filter(x -> x > d[k]).count();
        }
        return s;
    }

    @Override
    protected Object part2() {
        // part 2
        var t = Long.parseLong(lines[0].split(": ")[1].replaceAll("\\s+", ""));
        var d = Long.parseLong(lines[1].split(": ")[1].replaceAll("\\s+", ""));
        return LongStream.range(0, t).parallel().map(i -> i*(t-i)).filter(i -> i > d).count();
    }
}
