package nl.tvandijk.aoc.year2022.day21;

import java.util.*;
import nl.tvandijk.aoc.common.Day;

public class Day21 extends Day {
    record Op (String left, String right, char op) {}

    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        for (var line : lines) {
            var s = line.split(": ");
            if (Character.isDigit(s[1].charAt(0))) {
                values.put(s[0], Long.parseLong(s[1]));
            } else {
                var s2 = s[1].split(" ");
                ops.put(s[0], new Op(s2[0], s2[2], s2[1].charAt(0)));
            }
        }
    }

    private Map<String, Op> ops = new HashMap<>();
    private Map<String, Long> values = new HashMap<>();

    private long compute(String s) {
        var v = values.get(s);
        if (v != null) return v;;
        var o = ops.get(s);
        var vl = compute(o.left);
        var vr = compute(o.right);
        switch (o.op) {
            case '+' -> values.put(s, vl + vr);
            case '/' -> values.put(s, vl / vr);
            case '*' -> values.put(s, vl * vr);
            case '-' -> values.put(s, vl - vr);
        }
        return values.get(s);
    }

    private boolean findHuman(String s, Map<String, Op> forHuman) {
        if (s.equals("humn")) return true;
        if (values.containsKey(s)) return false;
        var o = ops.get(s);
        var l = findHuman(o.left, forHuman);
        var r = findHuman(o.right, forHuman);
        if (s.equals("root")) {
            if (l == r) {
                System.out.println("huh");
            } else if (l) {
                values.put(o.left, values.get(o.right));
            } else if (r) {
                values.put(o.right, values.get(o.left));
            }
            return true;
        } else if (l && r) {
            System.out.println("huh");
            return true;
        } else if (l) {
            // r is const
            switch (o.op) {
                case '+' -> forHuman.put(o.left, new Op(s, o.right, '-'));
                case '/' -> forHuman.put(o.left, new Op(s, o.right, '*'));
                case '*' -> forHuman.put(o.left, new Op(s, o.right, '/'));
                case '-' -> forHuman.put(o.left, new Op(s, o.right, '+'));
            }
            return true;
        } else if (r) {
            switch (o.op) {
                case '+' -> forHuman.put(o.right, new Op(s, o.left, '-'));
                case '/' -> forHuman.put(o.right, new Op(o.left, s, '/'));
                case '*' -> forHuman.put(o.right, new Op(s, o.left, '/'));
                case '-' -> forHuman.put(o.right, new Op(o.left, s, '-'));
            }
            return true;
        } else {
            switch (o.op) {
                case '+' -> values.put(s, values.get(o.left) + values.get(o.right));
                case '/' -> values.put(s, values.get(o.left) / values.get(o.right));
                case '*' -> values.put(s, values.get(o.left) * values.get(o.right));
                case '-' -> values.put(s, values.get(o.left) - values.get(o.right));
            }
            return false;
        }
    }

    @Override
    protected boolean resetForPartTwo() {
        return true;
    }

    @Override
    protected Object part1() {
        // part 1
        return compute("root");
    }

    @Override
    protected Object part2() {
        // part 2
        values.remove("humn");
        Map<String, Op> forHuman = new HashMap<>();
        findHuman("root", forHuman);
        ops = forHuman;
        return compute("humn");
    }
}
