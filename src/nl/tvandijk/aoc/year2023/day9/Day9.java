package nl.tvandijk.aoc.year2023.day9;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day9 extends Day {
    long next(List<Long> values, boolean part2) {
        if (values.stream().allMatch(x -> x == 0)) {
            return 0;
        }
        List<Long> res = new ArrayList<>();
        for (int i = 1; i < values.size(); i++) {
            res.add(values.get(i) - values.get(i - 1));
        }
        if (part2) return values.getFirst() - next(res, true);
        else return values.getLast() + next(res, false);
    }

    @Override
    protected Object part1() {
        // part 1
        long sum = 0;
        for (var l : lines) {
            var values = Arrays.stream(l.split(" ")).map(Long::parseLong).toList();
            sum += next(values, false);
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum = 0;
        for (var l : lines) {
            var values = Arrays.stream(l.split(" ")).map(Long::parseLong).toList();
            sum += next(values, true);
        }
        return sum;
    }
}
