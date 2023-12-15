package nl.tvandijk.aoc.year2023.day15;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day15 extends Day {
    private int hash(String s) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            var ch = s.charAt(i);
            val += (int) ch;
            val *= 17;
            val %= 256;
        }
        return val;
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
        Map<Integer,List<Pair<String,Integer>>> boxes = new HashMap<>();
        for (var p : parts) {
            if (p.contains("=")) {
                var kv = p.split("=");
                var h = hash(kv[0]);
                var l = boxes.computeIfAbsent(h, k -> new ArrayList<>());
                boolean found = false;
                for (int i = 0; i < l.size(); i++) {
                    if (l.get(i).a.equals(kv[0])) {
                        l.get(i).b = Integer.parseInt(kv[1]);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    l.add(Pair.of(kv[0], Integer.parseInt(kv[1])));
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
            int box = entry.getKey()+1;
            for (int i = 0; i < entry.getValue().size(); i++) {
                focalPower += box * (i+1) * entry.getValue().get(i).b;
            }
        }
        return focalPower;
    }
}
