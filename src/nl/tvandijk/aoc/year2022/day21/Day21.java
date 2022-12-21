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

    Map<String, Op> ops = new HashMap<>();
    Map<String, Long> values = new HashMap<>();

    long compute(String s, Map<String, Long> values, Map<String, Op> ops) {
        var v = values.get(s);
        if (v != null) return v;;
        var o = ops.get(s);
        var vl = compute(o.left, values, ops);
        var vr = compute(o.right, values, ops);
        switch (o.op) {
            case '+' -> values.put(s, vl+vr);
            case '/' -> values.put(s, vl/vr);
            case '*' -> values.put(s, vl*vr);
            case '-' -> values.put(s, vl-vr);
        }
        return values.get(s);
    }

    Map<String, Op> forHuman = new HashMap<>();

    private boolean findHuman(String s) {
        if (s.equals("humn")) return true;
        var v = values.get(s);
        if (v != null) return false; // not sure if needed
        var o = ops.get(s);
        var l = findHuman(o.left);
        var r = findHuman(o.right);
        if (s.equals("root")) {
            if (l && r) {
                System.out.println("huh");
            } else if (l) {
                values.put(o.left, values.get(o.right));
            } else if (r) {
                values.put(o.right, values.get(o.left));
            } else {
                System.out.println("huh");
            }
            return true;
        }
        if (l && r) {
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
        return compute("root", values, ops);
    }

    @Override
    protected Object part2() {
        // part 2
        values.remove("humn");
        findHuman("root");
        return compute("humn", values, forHuman);
    }
}
