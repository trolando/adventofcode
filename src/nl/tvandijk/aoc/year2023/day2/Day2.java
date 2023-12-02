package nl.tvandijk.aoc.year2023.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;

public class Day2 extends Day {
    private Map<String, Integer> getCounts(String g) {
        var items = g.split(",");
        Map<String, Integer> counts = new HashMap<>();
        for (var i : items) {
            var ii = i.trim().split(" ");
            var count = Integer.parseInt(ii[0].trim());
            var type = ii[1].trim();
            counts.put(type, count);
        }
        return counts;
    }

    private int power(String[] games) {
        Map<String, Integer> req = new HashMap<>();
        for (var g : games) {
            Map<String, Integer> counts = getCounts(g);
            req.put("red", Math.max(req.getOrDefault("red", 0), counts.getOrDefault("red", 0)));
            req.put("blue", Math.max(req.getOrDefault("blue", 0), counts.getOrDefault("blue", 0)));
            req.put("green", Math.max(req.getOrDefault("green", 0), counts.getOrDefault("green", 0)));
        }
        return req.getOrDefault("red", 0) * req.getOrDefault("blue", 0) * req.getOrDefault("green", 0);
    }

    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (var l : lines) {
            var s = l.split(":");
            if (s.length != 2) continue;
            var gameid = s[0].split(" ")[1].trim();
            var games = s[1].split(";");
            boolean valid = true;
            for (var g : games) {
                Map<String, Integer> counts = getCounts(g);
                if (counts.getOrDefault("red", 0) > 12 ||
                        counts.getOrDefault("green", 0) > 13 ||
                        counts.getOrDefault("blue", 0) > 14) {
                    valid = false;
                }
            }
            if (valid) {
                sum += Integer.parseInt(gameid);
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        int sum = 0;
        for (var l : lines) {
            var s = l.split(":");
            if (s.length != 2) continue;
            sum += power(s[1].split(";"));
        }
        return sum;
    }
}
