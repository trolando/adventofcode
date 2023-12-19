package nl.tvandijk.aoc.year2023.day19;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day19 extends Day {
    private interface Rule {
        String accept(Map<String, Integer> map);
    }

    private static class RuleGreater implements Rule {
        String var;
        int value;
        String dest;

        public RuleGreater(String var, int value, String dest) {
            this.var = var;
            this.value = value;
            this.dest = dest;
        }

        public String accept(Map<String, Integer> map) {
            if (map.get(var) > value) return dest;
            else return null;
        }
    }

    private static class RuleLess implements Rule {
        String var;
        int value;
        String dest;

        public RuleLess(String var, int value, String dest) {
            this.var = var;
            this.value = value;
            this.dest = dest;
        }

        public String accept(Map<String, Integer> map) {
            if (map.get(var) < value) return dest;
            else return null;
        }
    }

    private static class RuleJump implements Rule {
        private String dest;

        public RuleJump(String dest) {
            this.dest = dest;
        }

        @Override
        public String accept(Map<String, Integer> map) {
            return dest;
        }
    }

    static class Block {
        String flow;
        int xmin;
        int xmax;
        int mmin;
        int mmax;
        int amin;
        int amax;
        int smin;
        int smax;

        public Block(String flow, int xmin, int xmax, int mmin, int mmax, int amin, int amax, int smin, int smax) {
            this.flow = flow;
            this.xmin = xmin;
            this.xmax = xmax;
            this.mmin = mmin;
            this.mmax = mmax;
            this.amin = amin;
            this.amax = amax;
            this.smin = smin;
            this.smax = smax;
        }
    }

    @Override
    protected Object part1() {
        // part 1
        var pp = Util.splitArray(lines, String::isBlank);
        Map<String, List<Rule>> ruleMap = new HashMap<>();
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
        var pp = Util.splitArray(lines, String::isBlank);
        Map<String, List<Rule>> ruleMap = new HashMap<>();
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
        long res = 0L;
        Deque<Block> blocks = new ArrayDeque<>();
        var initial = new Block("in", 1, 4000, 1, 4000, 1, 4000, 1, 4000);
        blocks.add(initial);
        while (!blocks.isEmpty()) {
            var next = blocks.pop();
            if (next.xmax < next.xmin) continue;
            if (next.mmax < next.mmin) continue;
            if (next.amax < next.amin) continue;
            if (next.smax < next.smin) continue;
            if (next.flow.equals("R")) continue;
            if (next.flow.equals("A")) {
                long x = next.xmax - next.xmin + 1L;
                long m = next.mmax - next.mmin + 1L;
                long a = next.amax - next.amin + 1L;
                long s = next.smax - next.smin + 1L;
                res += x * m * a * s;
                continue;
            }
            // get flow
            for (var rule : ruleMap.get(next.flow)) {
                if (rule instanceof RuleGreater r) {
                    if (r.var.equals("x")) {
                        blocks.add(new Block(r.dest, r.value + 1, next.xmax, next.mmin, next.mmax, next.amin, next.amax, next.smin, next.smax));
                        next.xmax = r.value;
                    }
                    if (r.var.equals("m")) {
                        blocks.add(new Block(r.dest, next.xmin, next.xmax, r.value + 1, next.mmax, next.amin, next.amax, next.smin, next.smax));
                        next.mmax = r.value;
                    }
                    if (r.var.equals("a")) {
                        blocks.add(new Block(r.dest, next.xmin, next.xmax, next.mmin, next.mmax, r.value + 1, next.amax, next.smin, next.smax));
                        next.amax = r.value;
                    }
                    if (r.var.equals("s")) {
                        blocks.add(new Block(r.dest, next.xmin, next.xmax, next.mmin, next.mmax, next.amin, next.amax, r.value + 1, next.smax));
                        next.smax = r.value;
                    }
                }
                if (rule instanceof RuleLess r) {
                    if (r.var.equals("x")) {
                        blocks.add(new Block(r.dest, next.xmin, r.value - 1, next.mmin, next.mmax, next.amin, next.amax, next.smin, next.smax));
                        next.xmin = r.value;
                    }
                    if (r.var.equals("m")) {
                        blocks.add(new Block(r.dest, next.xmin, next.xmax, next.mmin, r.value - 1, next.amin, next.amax, next.smin, next.smax));
                        next.mmin = r.value;
                    }
                    if (r.var.equals("a")) {
                        blocks.add(new Block(r.dest, next.xmin, next.xmax, next.mmin, next.mmax, next.amin, r.value - 1, next.smin, next.smax));
                        next.amin = r.value;
                    }
                    if (r.var.equals("s")) {
                        blocks.add(new Block(r.dest, next.xmin, next.xmax, next.mmin, next.mmax, next.amin, next.amax, next.smin, r.value - 1));
                        next.smin = r.value;
                    }
                }
                if (rule instanceof RuleJump r) {
                    next.flow = r.dest;
                    blocks.add(next);
                }
            }
        }
        return res;
    }
}
