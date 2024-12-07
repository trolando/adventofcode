package nl.tvandijk.aoc.year2024.day7;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day7 extends Day {
    boolean findAny(long answer, long[] elements, int i, long a, boolean part2) {
        // try +
        if (i == elements.length) return answer == a;
        if (findAny(answer, elements, i + 1, a + elements[i], part2)) return true;
        if (findAny(answer, elements, i+1, a * elements[i], part2)) return true;
        if (part2 && findAny(answer, elements, i+1, Long.parseLong(String.valueOf(a) + elements[i]), part2)) return true;
        return false;
    }

    boolean findAny(long answer, long[] elements, boolean part2) {
        return findAny(answer, elements, 1, elements[0], part2);
    }

    @Override
    protected Object part1() {
        // part 1
        long sum=0;
        for (var line: lines) {
            var parts = line.split(": ");
            var part2 = parts[1].split("\\s+");
            var ans = Long.parseLong(parts[0]);
            var elements = Arrays.stream(part2).mapToLong(Long::parseLong).toArray();
            if (findAny(ans, elements, false)) sum += ans;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum=0;
        for (var line: lines) {
            var parts = line.split(": ");
            var part2 = parts[1].split("\\s+");
            var ans = Long.parseLong(parts[0]);
            var elements = Arrays.stream(part2).mapToLong(Long::parseLong).toArray();
            if (findAny(ans, elements, true)) sum += ans;
        }
        return sum;
    }
}
