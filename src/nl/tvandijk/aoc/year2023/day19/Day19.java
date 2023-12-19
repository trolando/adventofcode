package nl.tvandijk.aoc.year2023.day19;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day19 extends Day {
    private final Map<String, List<Rule>> ruleMap = new HashMap<>();

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
        var pp = Util.splitArray(lines, String::isBlank);
        for (var line : pp.get(0)) {
            var p = line.split("[\\{\\}]");
            List<Rule> rules = new ArrayList<>();
            for (var instr : p[1].split(",")) {
                var p2 = instr.split(":");
                if (p2.length == 2) {
                    var check = p2[0];
                    if (check.contains(">")) {
                        var c = check.split(">");
                        rules.add(new RuleGreater(c[0], Integer.parseInt(c[1]), p2[1]));
                    } else {
                        var c = check.split("<");
                        rules.add(new RuleLess(c[0], Integer.parseInt(c[1]), p2[1]));
                    }
                } else {
                    rules.add(new RuleJump(p2[0]));
                }
            }
            ruleMap.put(p[0], rules);
        }
    }

    @Override
    protected Object part1() {
        // part 1
        var pp = Util.splitArray(lines, String::isBlank);
        long res = 0;
        for (var line : pp.get(1)) {
            Map<String, Integer> vals = new HashMap<>();
            line = line.substring(1, line.length() - 1);
            for (var el : line.split(",")) {
                var p = el.split("=");
                vals.put(p[0], Integer.parseInt(p[1]));
            }
            String flow = "in";
            while (!"R".equals(flow) && !"A".equals(flow)) {
                var rules = ruleMap.get(flow);
                for (var rule : rules) {
                    var dest = rule.accept(vals);
                    if (dest != null) {
                        flow = dest;
                        break;
                    }
                }
            }
            if (flow.equals("A")) {
                res += vals.values().stream().mapToLong(s -> s).sum();
            }
        }
        return res;
    }

    @Override
    protected Object part2() {
        // part 2
        long res = 0L;
        Deque<Block> blocks = new ArrayDeque<>();
        var initial = new Block("in", 1, 4000, 1, 4000, 1, 4000, 1, 4000);
        blocks.add(initial);
        while (!blocks.isEmpty()) {
            var next = blocks.pop();
            if (next.xmax() < next.xmin()) continue;
            if (next.mmax() < next.mmin()) continue;
            if (next.amax() < next.amin()) continue;
            if (next.smax() < next.smin()) continue;
            if (next.flow().equals("R")) continue;
            if (next.flow().equals("A")) {
                long x = next.xmax() - next.xmin() + 1L;
                long m = next.mmax() - next.mmin() + 1L;
                long a = next.amax() - next.amin() + 1L;
                long s = next.smax() - next.smin() + 1L;
                res += x * m * a * s;
                continue;
            }
            // get flow
            for (var rule : ruleMap.get(next.flow())) {
                if (rule instanceof RuleGreater r) {
                    if (r.var.equals("x")) {
                        blocks.add(next.withXmin(r.value+1).withFlow(r.dest));
                        next = next.withXmax(r.value);
                    }
                    if (r.var.equals("m")) {
                        blocks.add(next.withMmin(r.value+1).withFlow(r.dest));
                        next = next.withMmax(r.value);
                    }
                    if (r.var.equals("a")) {
                        blocks.add(next.withAmin(r.value+1).withFlow(r.dest));
                        next = next.withAmax(r.value);
                    }
                    if (r.var.equals("s")) {
                        blocks.add(next.withSmin(r.value+1).withFlow(r.dest));
                        next = next.withSmax(r.value);
                    }
                }
                if (rule instanceof RuleLess r) {
                    if (r.var.equals("x")) {
                        blocks.add(next.withXmax(r.value-1).withFlow(r.dest));
                        next = next.withXmin(r.value);
                    }
                    if (r.var.equals("m")) {
                        blocks.add(next.withMmax(r.value-1).withFlow(r.dest));
                        next = next.withMmin(r.value);
                    }
                    if (r.var.equals("a")) {
                        blocks.add(next.withAmax(r.value-1).withFlow(r.dest));
                        next = next.withAmin(r.value);
                    }
                    if (r.var.equals("s")) {
                        blocks.add(next.withSmax(r.value-1).withFlow(r.dest));
                        next = next.withSmin(r.value);
                    }
                }
                if (rule instanceof RuleJump r) {
                    blocks.add(next.withFlow(r.dest));
                    break;
                }
            }
        }
        return res;
    }
}
