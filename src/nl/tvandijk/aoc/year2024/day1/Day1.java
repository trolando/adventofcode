package nl.tvandijk.aoc.year2024.day1;

import java.util.*;
import nl.tvandijk.aoc.common.Day;

public class Day1 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();
        for (var line : lines) {
            var numbers = Arrays.stream(line.split("   ")).mapToInt(Integer::parseInt).toArray();
            left.add(numbers[0]);
            right.add(numbers[1]);
        }
        Collections.sort(left);
        Collections.sort(right);
        int sum=0;
        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(left.get(i)-right.get(i));
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        var left = new ArrayList<Integer>();
        Map<Integer, Long> counts=new HashMap<>();
        for (var line : lines) {
            var numbers = Arrays.stream(line.split("   ")).mapToInt(Integer::parseInt).toArray();
            left.add(numbers[0]);
            counts.compute(numbers[1], (key, v) -> 1 + (v == null ? 0 : v));
        }
        long sum=0;
        for (int i = 0; i < left.size(); i++) {
            sum += left.get(i) * counts.getOrDefault(left.get(i), 0L);
        }
        return sum;
        // alternative:
        // return left.stream().mapToLong(v -> v * counts.getOrDefault(v, 0L)).sum();
    }
}
