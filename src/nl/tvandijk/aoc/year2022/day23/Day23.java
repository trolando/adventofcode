package nl.tvandijk.aoc.year2022.day23;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;
import nl.tvandijk.aoc.util.Point;

public class Day23 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);

        int y = 0;
        for (var line : lines) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') elves.put(Point.of(x, y), new Object());
            }
            y++;
        }
    }

    Map<Point, Object> elves = new HashMap<>();

    void printState() {
        // show state
        var minx = elves.keySet().stream().mapToLong(p -> p.x).min().orElse(0);
        var maxx = elves.keySet().stream().mapToLong(p -> p.x).max().orElse(0);
        var miny = elves.keySet().stream().mapToLong(p -> p.y).min().orElse(0);
        var maxy = elves.keySet().stream().mapToLong(p -> p.y).max().orElse(0);
        for (long y = miny; y <= maxy; y++) {
            for (long x = minx; x <= maxx; x++) {
                if (elves.containsKey(Point.of(x, y))) System.out.print("#");
                else System.out.print(".");
            }
            System.out.println();
        }
        System.out.println();
    }

    boolean round(int i) {
        Map<Point, List<Pair<Object, Point>>> proposals = new HashMap<>();
        for (var e : elves.entrySet()) {
            var loc = e.getKey();
            var elf = e.getValue();

            boolean moves = false;
            for (var p : loc.adjacent(true)) if (elves.containsKey(p)) moves = true;
            if (!moves) continue;

            for (int d = 0; d < 4; d++) {
                int dir = (i+d)%4;
                if (dir == 0) {
                    if (!elves.containsKey(loc.delta(-1, -1)) &&
                            !elves.containsKey(loc.delta(0, -1)) &&
                            !elves.containsKey(loc.delta(1, -1))) {
                        var to = loc.delta(0, -1);
                        proposals.computeIfAbsent(to, s -> new ArrayList<>()).add(Pair.of(elf, loc));
                        break;
                    }
                }
                if (dir == 1) {
                    if (!elves.containsKey(loc.delta(-1, 1)) &&
                            !elves.containsKey(loc.delta(0, 1)) &&
                            !elves.containsKey(loc.delta(1, 1))) {
                        var to = loc.delta(0, 1);
                        proposals.computeIfAbsent(to, s -> new ArrayList<>()).add(Pair.of(elf, loc));
                        break;
                    }
                }
                if (dir == 2) {
                    if (!elves.containsKey(loc.delta(-1, -1)) &&
                            !elves.containsKey(loc.delta(-1, 0)) &&
                            !elves.containsKey(loc.delta(-1, 1))) {
                        var to = loc.delta(-1, 0);
                        proposals.computeIfAbsent(to, s -> new ArrayList<>()).add(Pair.of(elf, loc));
                        break;
                    }
                }
                if (dir == 3) {
                    if (!elves.containsKey(loc.delta(1, -1)) &&
                            !elves.containsKey(loc.delta(1, 0)) &&
                            !elves.containsKey(loc.delta(1, 1))) {
                        var to = loc.delta(1, 0);
                        proposals.computeIfAbsent(to, s -> new ArrayList<>()).add(Pair.of(elf, loc));
                        break;
                    }
                }
            }
        }
        // now check the proposals
        boolean moved = false;
        for (var prop : proposals.entrySet()) {
            var l = prop.getValue();
            if (l.size() == 1) {
                var ll = l.get(0);
                elves.remove(ll.b);
                elves.put(prop.getKey(), ll.a);
                moved = true;
            }
        }
        return moved;
    }

    @Override
    protected Object part1() {
        // part 1
        for (int i = 0; i < 10; i++) {
            round(i);
        }
//        printState();

        var minx = elves.keySet().stream().mapToLong(p -> p.x).min().orElse(0);
        var maxx = elves.keySet().stream().mapToLong(p -> p.x).max().orElse(0);
        var miny = elves.keySet().stream().mapToLong(p -> p.y).min().orElse(0);
        var maxy = elves.keySet().stream().mapToLong(p -> p.y).max().orElse(0);

        return (maxx-minx+1) * (maxy-miny+1) - elves.size();
    }

    @Override
    protected boolean resetForPartTwo() {
        return true;
    }

    @Override
    protected Object part2() {
        // part 2
        int i=0;
        while (round(i)) {
            i++;
        }
//        printState();
        return i+1;
    }
}
