package nl.tvandijk.aoc.year2023.day8;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day8 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var parts = Util.splitArray(lines, String::isBlank);
        var instructions = parts.get(0).get(0);
        Map<String, Pair<String, String>> nodes = new HashMap<>();
        for (var n : parts.get(1)) {
            var nn = n.split(" = ");
            var lr = nn[1].substring(1, nn[1].length() - 1).split(", ");
            nodes.put(nn[0], Pair.of(lr[0], lr[1]));
        }
        var loc = "AAA";
        int steps = 0;
        int pc = 0;
        while (true) {
            var node = nodes.get(loc);
            if (node == null) {
                System.out.println("node " + loc + " not found");
                return null;
            }
            if (loc.equals("ZZZ")) break;
            var ins = instructions.charAt(pc);
            if (ins == 'L') {
                loc = node.a;
            } else {
                loc = node.b;
            }
            pc = (pc + 1) % instructions.length();
            steps++;
        }
        return steps;
    }

    @Override
    protected Object part2() {
        // part 2
        var parts = Util.splitArray(lines, String::isBlank);
        var instructions = parts.get(0).get(0);
        Map<String, Pair<String, String>> nodes = new HashMap<>();
        Set<String> locs = new HashSet<>();
        for (var n : parts.get(1)) {
            var nn = n.split(" = ");
            var lr = nn[1].substring(1, nn[1].length() - 1).split(", ");
            nodes.put(nn[0], Pair.of(lr[0], lr[1]));
            if (nn[0].endsWith("A")) locs.add(nn[0]);
        }
        List<Long> periods = new ArrayList<>();
        // we need to find the period
        for (var loc : locs) {
            int steps = 0;
            int pc = 0;
            while (true) {
                var node = nodes.get(loc);
                if (loc.endsWith("Z")) {
                    // it turns out that the prefix equals the period, so we can stop here
                    periods.add((long) steps);
                    break;
                }
                var ins = instructions.charAt(pc);
                if (ins == 'L') {
                    loc = node.a;
                } else {
                    loc = node.b;
                }
                pc = (pc + 1) % instructions.length();
                steps++;
            }
        }
        // get least common multiple of periods...
        long lcm = periods.get(0);
        for (int i = 1; i < periods.size(); i++) {
            lcm = Util.lcm(lcm, periods.get(i));
        }
        // actually we can also use the Chinese Remainder Theorem
        ChineseRemainderTheorem crt = new ChineseRemainderTheorem();
        for (Long period : periods) {
            crt.addEquation(0, period);
        }
        var res = crt.solve();
        if (res[1] != lcm) throw new IllegalStateException("Wrong answer!");
        return res[1];
    }

    /**
     * Default construct initializes inputs to example.txt and input.txt
     */
    public Day8() {
        super("example.txt", "example2.txt", "example3.txt", "input.txt");
    }
}
