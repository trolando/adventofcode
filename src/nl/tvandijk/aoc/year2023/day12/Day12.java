package nl.tvandijk.aoc.year2023.day12;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day12 extends Day {
    private final Map<Pair<Integer, Integer>, Long> cache = new HashMap<>();

    private long compute(String s, int k, int[] numbers, int x) {
        // if all numbers are matched, check that there are no blocks left
        if (x == numbers.length) {
            for (int i = k; i < s.length(); i++) {
                if (s.charAt(i) == '#') return 0L;
            }
            return 1L;
        }
        // skip empty space until the next ? or #
        while (s.length() > k && s.charAt(k) == '.') k++;
        // check if there are enough characters left
        if (s.length() - k < numbers[x]) return 0L;
        // enough characters left, consult the cache
        var key = Pair.of(k, x);
        if (cache.containsKey(key)) return cache.get(key);
        // not in the cache, so first time to compute
        long res = 0;
        // case 1: try to match the next block...
        boolean matches = true;
        for (int i = 0; i < numbers[x]; i++) {
            if (s.charAt(k + i) == '.') {
                matches = false;
                break;
            }
        }
        if (matches && (s.length() <= k + numbers[x] || s.charAt(k + numbers[x]) != '#')) {
            res += compute(s, k + numbers[x] + 1, numbers, x + 1);
        }
        // case 2: skip the next character (unless forced #)
        if (s.charAt(k) != '#') {
            res += compute(s, k + 1, numbers, x);
        }
        // cache the result and return
        cache.put(key, res);
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        long sum = 0;
        for (var l : lines) {
            var parts = l.split(" ");
            var numbers = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
            cache.clear();
            sum += compute(parts[0], 0, numbers, 0);
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum = 0;
        for (var l : lines) {
            var parts = l.split(" ");
            String p0 = parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0];
            String p1 = parts[1] + "," + parts[1] + "," + parts[1] + "," + parts[1] + "," + parts[1];
            var numbers = Arrays.stream(p1.split(",")).mapToInt(Integer::parseInt).toArray();
            cache.clear();
            sum += compute(p0, 0, numbers, 0);
        }
        return sum;
    }
}
