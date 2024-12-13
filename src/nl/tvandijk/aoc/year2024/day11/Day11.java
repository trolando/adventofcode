package nl.tvandijk.aoc.year2024.day11;

import nl.tvandijk.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;

public class Day11 extends Day {
    Map<String, Long> step(Map<String, Long> inputs) {
        Map<String, Long> res = new HashMap<>();
        for (var en : inputs.entrySet()) {
            var in = en.getKey();
            if (in.equals("0")) {
                res.put("1", res.getOrDefault("1", 0L) + en.getValue());
            } else if (in.length() % 2 == 0) {
                String left = in.substring(0, in.length() / 2);
                String right = String.valueOf(Long.parseLong(in.substring(in.length() / 2)));
                res.put(left, res.getOrDefault(left, 0L) + en.getValue());
                res.put(right, res.getOrDefault(right, 0L) + en.getValue());
            } else {
                String value = String.valueOf(Long.parseLong(in) * 2024L);
                res.put(value, res.getOrDefault(value, 0L) + en.getValue());
            }
        }
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        Map<String, Long> state = new HashMap<>();
        for (String l : lines[0].split("\\s+")) {
            state.put(l, state.getOrDefault(l, 0L) + 1);
        }
        for (int i = 0; i < 25; i++) {
            state = step(state);
        }
        return state.values().stream().mapToLong(x -> x).reduce(0L, Long::sum);
    }

    @Override
    protected Object part2() {
        // part 2
        Map<String, Long> state = new HashMap<>();
        for (String l : lines[0].split("\\s+")) {
            state.put(l, state.getOrDefault(l, 0L) + 1);
        }
        for (int i = 0; i < 75; i++) {
            state = step(state);
        }
        return state.values().stream().mapToLong(x -> x).reduce(0L, Long::sum);
    }
}
