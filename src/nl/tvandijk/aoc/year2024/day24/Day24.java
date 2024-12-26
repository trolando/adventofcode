package nl.tvandijk.aoc.year2024.day24;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day24 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        var parts = this.fileContents.split("\n\n");
        for (var line : parts[0].split("\n")) {
            var L = line.split(": ");
            values.put(L[0], Integer.parseInt(L[1]));
        }
        for (var line : parts[1].split("\n")) {
            var L = line.split("\\s+");
            ops.put(L[4], Triple.of(L[0], L[1], L[2]));
        }
    }

    Map<String, Integer> values = new HashMap<>();
    Map<String, Triple<String, String, String>> ops = new HashMap<>();

    int compute(String val) {
        if (values.containsKey(val)) {
            return values.get(val);
        }
        if (ops.containsKey(val)) {
            var O = ops.get(val);
            int v = switch (O.b) {
                case "XOR" -> compute(O.a) ^ compute(O.c);
                case "OR" -> compute(O.a) | compute(O.c);
                case "AND" -> compute(O.a) & compute(O.c);
                default -> throw new IllegalStateException("Unexpected value: " + O);
            };
            values.put(val, v);
            return v;
        }
        return 0;
    }

    @Override
    protected Object part1() {
        // part 1
        long res = 0;
        for (int i = 64; i >= 0; i--) {
            String s = String.format("z%02d", i);
            res <<= 1;
            res |= compute(s);
        }
        return res;
    }

    enum GateType {
        CARRY_OR,
        SUM_XOR,
        INPUTS_XOR,
        INPUTS_AND,
        CARRY_AND,
    }

    @Override
    protected Object part2() {
        // part 2
        Map<GateType, Set<Triple<String, String, String>>> assignments = new HashMap<>();
        Map<Triple<String, String, String>, String> presumedWires = new HashMap<>();
        Set<String> mistakes = new HashSet<>();
        // first sort all gates into their 'expected' categories
        for (var e : ops.entrySet()) {
            var k = e.getKey();
            var o = e.getValue();
            if (o.b.equals("XOR")) {
                if (o.a.charAt(0) == 'x' || o.a.charAt(0) == 'y') assignments.computeIfAbsent(GateType.INPUTS_XOR, gt -> new HashSet<>()).add(o);
                else assignments.computeIfAbsent(GateType.SUM_XOR, gt -> new HashSet<>()).add(o);
            } else if (o.b.equals("OR")) {
                assignments.computeIfAbsent(GateType.CARRY_OR, gt -> new HashSet<>()).add(o);
            } else {
                if (o.a.charAt(0) == 'x' || o.a.charAt(0) == 'y') assignments.computeIfAbsent(GateType.INPUTS_AND, gt -> new HashSet<>()).add(o);
                else assignments.computeIfAbsent(GateType.CARRY_AND, gt -> new HashSet<>()).add(o);
            }
            presumedWires.put(o, k);
        }
        // check all INPUTS_XOR
        for (var a : assignments.get(GateType.INPUTS_XOR)) {
            String ou = presumedWires.get(a);
            if (a.a.contains("00")) {
                // the half adder
                if (!ou.equals("z00")) {
                    mistakes.add(ou);
                    mistakes.add("z00");
                }
            } else {
                // full adder, so there should be a SUM_XOR _and_ a CARRY_AND that want this
                if (assignments.get(GateType.SUM_XOR).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
                if (assignments.get(GateType.CARRY_AND).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
            }
        }
        // check all INPUTS_AND
        for (var a : assignments.get(GateType.INPUTS_AND)) {
            String ou = presumedWires.get(a);
            if (a.a.contains("00")) {
                // the half adder
                // feeds into SUM_XOR and CARRY_AND
                if (assignments.get(GateType.SUM_XOR).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
                if (assignments.get(GateType.CARRY_AND).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
            } else {
                // the full adder
                // feeds into OR
                if (assignments.get(GateType.CARRY_OR).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
            }
        }
        // check all SUM_XOR
        for (var a : assignments.get(GateType.SUM_XOR)) {
            String ou = presumedWires.get(a);
            // should be an output bit
            if (!ou.startsWith("z")) {
                mistakes.add(ou);
            }
        }
        // check all CARRY_AND
        for (var a : assignments.get(GateType.CARRY_AND)) {
            String ou = presumedWires.get(a);
            // feeds into OR
            if (assignments.get(GateType.CARRY_OR).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                mistakes.add(ou);
            }
        }
        // check all CARRY_OR
        for (var a : assignments.get(GateType.CARRY_OR)) {
            String ou = presumedWires.get(a);
            // should be final output bit, or feed into SUM_XOR and CARRY_AND
            if (!ou.equals("z45")) {
                if (assignments.get(GateType.SUM_XOR).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
                if (assignments.get(GateType.CARRY_AND).stream().noneMatch(aa -> aa.a.equals(ou) || aa.c.equals(ou))) {
                    mistakes.add(ou);
                }
            }
        }
        return mistakes.stream().sorted().collect(Collectors.joining(","));
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day24() {
//        super("example.txt", "input.txt");
//        super("example.txt");
        super("input.txt");
    }
}
