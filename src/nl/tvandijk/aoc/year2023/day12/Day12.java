package nl.tvandijk.aoc.year2023.day12;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day12 extends Day {
    private final Map<Pair<Integer, Integer>, Long> cache = new HashMap<>();

    private long compute(String s, long[] numbers) {
        cache.clear();
        return compute(s, 0, numbers, 0);
    }

    private long compute(String s, int k, long[] numbers, int x) {
        // check if we matched all
        if (x == numbers.length) {
            for (int i = k; i < s.length(); i++) {
                if (s.charAt(i) == '#') return 0L;
            }
            return 1L;
        }
        if (s.length() - k < numbers[x]) return 0L;
        // ok we did not match all, so we need to match the next number
        var key = Pair.of(k, x);
        if (cache.containsKey(key)) return cache.get(key);
        long res = 0;
        // first try to match the next number...
        boolean matches = true;
        for (int i = 0; i < numbers[x]; i++) {
            if (s.length() <= k + i || s.charAt(k + i) == '.') {
                matches = false;
                break;
            }
        }
        if (matches && (s.length() <= k + numbers[x] || s.charAt(k + (int) numbers[x]) != '#')) {
            res += compute(s, k + (int) numbers[x] + 1, numbers, x + 1);
        }
        // try to not match it
        if (s.charAt(k) != '#') {
            res += compute(s, k + 1, numbers, x);
        }
        cache.put(key, res);
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        long sum = 0;
        for (var l : lines) {
            var parts = l.split(" ");
            var numbers = Arrays.stream(parts[1].split(",")).mapToLong(Long::parseLong).toArray();
            sum += compute(parts[0], numbers);
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum = 0;
        for (var l : lines) {
            var parts = l.split(" ");
            String p0 = parts[0]+"?"+parts[0]+"?"+parts[0]+"?"+parts[0]+"?"+parts[0];
            String p1 = parts[1]+","+parts[1]+","+parts[1]+","+parts[1]+","+parts[1];
            var numbers = Arrays.stream(p1.split(",")).mapToLong(Long::parseLong).toArray();
            sum += compute(p0, numbers);
        }
        return sum;
    }
}
