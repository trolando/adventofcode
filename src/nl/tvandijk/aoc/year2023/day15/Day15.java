package nl.tvandijk.aoc.year2023.day15;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day15 extends Day {
    private int hash(String s) {
        return s.chars().reduce(0, (val, ch) -> (val + ch) * 17 % 256);
    }

    @Override
    protected Object part1() {
        // part 1
        var parts = lines[0].split(",");
        return Arrays.stream(parts).mapToInt(this::hash).sum();
    }

    @Override
    protected Object part2() {
        // part 2
        var parts = lines[0].split(",");
        Map<Integer, List<Pair<String, Integer>>> boxes = new HashMap<>();
        for (var p : parts) {
            var s = p.split("=");
            if (s.length == 2) {
                var h = hash(s[0]);
                var l = boxes.computeIfAbsent(h, k -> new ArrayList<>());
                boolean found = false;
                for (int i = 0; i < l.size(); i++) {
                    if (l.get(i).a.equals(s[0])) {
                        l.get(i).b = Integer.parseInt(s[1]);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    l.add(Pair.of(s[0], Integer.parseInt(s[1])));
                }
            } else {
                var k = p.substring(0, p.length() - 1);
                var h = hash(k);
                var l = boxes.computeIfAbsent(h, c -> new ArrayList<>());
                for (int i = 0; i < l.size(); i++) {
                    if (l.get(i).a.equals(k)) {
                        l.remove(i);
                        break;
                    }
                }
            }
        }
        // compute focal power
        int focalPower = 0;
        for (var entry : boxes.entrySet()) {
            int box = entry.getKey() + 1;
            for (int i = 0; i < entry.getValue().size(); i++) {
                focalPower += box * (i + 1) * entry.getValue().get(i).b;
            }
        }
        return focalPower;
    }
}
