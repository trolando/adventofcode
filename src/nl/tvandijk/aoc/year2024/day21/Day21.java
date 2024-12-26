package nl.tvandijk.aoc.year2024.day21;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day21 extends Day {

    static Map<Character, Point> theNumpad = new HashMap<>();
    static {
        theNumpad.put('1', Point.of(0, 2));
        theNumpad.put('2', Point.of(1, 2));
        theNumpad.put('3', Point.of(2, 2));
        theNumpad.put('4', Point.of(0, 1));
        theNumpad.put('5', Point.of(1, 1));
        theNumpad.put('6', Point.of(2, 1));
        theNumpad.put('7', Point.of(0, 0));
        theNumpad.put('8', Point.of(1, 0));
        theNumpad.put('9', Point.of(2, 0));
        theNumpad.put(' ', Point.of(0, 3));
        theNumpad.put('0', Point.of(1, 3));
        theNumpad.put('A', Point.of(2, 3));
    }

    static Map<Character, Point> theDirpad = new HashMap<>();
    static {
        theDirpad.put(' ', Point.of(0, 0));
        theDirpad.put('^', Point.of(1, 0));
        theDirpad.put('A', Point.of(2, 0));
        theDirpad.put('<', Point.of(0, 1));
        theDirpad.put('v', Point.of(1, 1));
        theDirpad.put('>', Point.of(2, 1));
    }

    Map<String, Long> steps(Map<Character, Point> pad, String goal) {
        var p = pad.get('A');
        var b = pad.get(' ');
        Map<String, Long> res = new HashMap<>();
        for (var c : goal.toCharArray()) {
            var np = pad.get(c);
            var diff = np.minus(p);
            // prefer < then ^ then v then >
            var str = new StringBuilder();
            if (diff.x < 0) str.append("<".repeat((int) -diff.x));
            if (diff.y < 0) str.append("^".repeat((int) -diff.y));
            if (diff.y > 0) str.append("v".repeat((int) diff.y));
            if (diff.x > 0) str.append(">".repeat((int) diff.x));
            if ((np.x == b.x && p.y == b.y) || (np.y == b.y && p.x == b.x)) str.reverse();
            str.append('A');
            res.put(str.toString(), res.getOrDefault(str.toString(), 0L) + 1);
            p = np;
        }
        return res;
    }

    long solve(String goal, int dirpads) {
        var steps = steps(theNumpad, goal);
        for (int i = 0; i <= dirpads; i++) {
            Map<String, Long> next = new HashMap<>();
            for (var step : steps.entrySet()) {
                var res = steps(theDirpad, step.getKey());
                for (var r : res.entrySet()) {
                    next.put(r.getKey(), next.getOrDefault(r.getKey(), 0L) + r.getValue() * step.getValue());
                }
            }
            steps = next;
        }
        return steps.values().stream().reduce(0L, Long::sum) * digits(goal)[0];
    }

    Map<Character, Point> pad1 = new HashMap<>();
    {
        for (var s : Grid.of(List.of("789", "456", "123", " 0A", " ^@", "<v>"))) {
            pad1.put(s.b, s.a);
        }
    }

    Map<Triple<String, Integer, Integer>, Long> cache = new HashMap<>();

    private Long dp(String sequence, int limit, int depth) {
        var key = Triple.of(sequence, limit, depth);
        cache.computeIfAbsent(key, nah -> {
            Point initial = depth == 0 ? pad1.get('A') : pad1.get('@');
            var state = Pair.of(initial, 0L);
            for (var ch : sequence.toCharArray()) {
                var next = pad1.get(ch);
                var delta = next.minus(state.a);
                var str = new StringBuilder();
                if (delta.x < 0) str.append("<".repeat((int) -delta.x));
                if (delta.y < 0) str.append("^".repeat((int) -delta.y));
                if (delta.y > 0) str.append("v".repeat((int) delta.y));
                if (delta.x > 0) str.append(">".repeat((int) delta.x));
                var list = str.toString().chars().mapToObj(c->(char)c).toList();
                //permutations.filter { path ->
                //    path.asSequence().runningFold(pos) { pos, dir -> pos + Direction.of(dir) }.all { it in keypad.values } }
                //.map { "$it@" }.ifEmpty { listOf("@") } next to (sum + if (depth == limit) paths.minOf { it.length }.toLong() else paths.minOfOrNull { dp(it, limit, depth + 1) } ?: paths.minOf { it.length }.toLong())
                Permutations.of(list, list.size(), false).forEach(
                        perm -> {

                        }
                );
            }
            return null;
        });
        return 0L;
    }

    @Override
    protected Object part1() {
        // part 1
        dp("029A", 2, 0);
        return Arrays.stream(lines).mapToLong(line -> solve(line, 2)).sum();
    }

    @Override
    protected Object part2() {
        // part 2
        return Arrays.stream(lines).mapToLong(line -> solve(line, 25)).sum();
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day21() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
