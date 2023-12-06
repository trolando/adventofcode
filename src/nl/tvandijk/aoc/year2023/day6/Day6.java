package nl.tvandijk.aoc.year2023.day6;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day6 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var t = Arrays.stream(lines[0].split("\\s+")).skip(1).mapToInt(Integer::parseInt).toArray();
        var d = Arrays.stream(lines[1].split("\\s+")).skip(1).mapToInt(Integer::parseInt).toArray();
        int s = 1;
        for (int i = 0; i < t.length; i++) {
            int ways = 0;
            for (int j = 0; j < t[i]; j++) {
                int dist = j*(t[i]-j);
                if (dist > d[i]) ways++;
            }
            s *= ways;

        }
        return s;
    }

    @Override
    protected Object part2() {
        // part 2
        var t = Long.parseLong(lines[0].split(":")[1].trim().replaceAll("\\s+", ""));
        var d = Long.parseLong(lines[1].split(":")[1].trim().replaceAll("\\s+", ""));
        long ways = 0;
        for (int j = 0; j < t; j++) {
            long dist = j*(t-j);
            if (dist > d) ways++;
        }
        return ways;
    }
}
