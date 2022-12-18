package nl.tvandijk.aoc.year2022.day14;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day14 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        Set<Point> occupied = new HashSet<>();
        for (var line : lines) {
            var parts = line.split(" -> ");
            for (int i = 1; i < parts.length; i++) {
                var a = parts[i-1].split(",");
                var first = Point.of(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
                var b = parts[i].split(",");
                var second = Point.of(Integer.parseInt(b[0]), Integer.parseInt(b[1]));
                var cx = second.x - first.x;
                var cy = second.y - first.y;
                if (cx < 0) cx = -1;
                if (cx > 0) cx = 1;
                if (cy < 0) cy = -1;
                if (cy > 0) cy = 1;
                while (true) {
                    occupied.add(first);
                    if (first.equals(second)) break;
                    first = first.delta(cx, cy);
                }
            }
        }
        int lowest = occupied.stream().mapToInt(p -> (int)p.y).max().getAsInt();
        for (int i = 0; ; i++) {
            var p = Point.of(500,0);
            while (true) {
                if (p.y > lowest) {
                    return i;
                } else if (!occupied.contains(p.delta(0, 1))) {
                    p = p.delta(0, 1);
                } else if (!occupied.contains(p.delta(-1, 1))) {
                    p = p.delta(-1, 1);
                } else if (!occupied.contains(p.delta(1, 1))) {
                    p = p.delta(1, 1);
                } else {
                    occupied.add(p);
                    break;
                }
            }
        }
    }

    @Override
    protected Object part2() {
        // part 2
        Set<Point> occupied = new HashSet<>();
        for (var line : lines) {
            var parts = line.split(" -> ");
            for (int i = 1; i < parts.length; i++) {
                var a = parts[i-1].split(",");
                var first = Point.of(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
                var b = parts[i].split(",");
                var second = Point.of(Integer.parseInt(b[0]), Integer.parseInt(b[1]));
                var cx = second.x - first.x;
                var cy = second.y - first.y;
                if (cx < 0) cx = -1;
                if (cx > 0) cx = 1;
                if (cy < 0) cy = -1;
                if (cy > 0) cy = 1;
                while (true) {
                    occupied.add(first);
                    if (first.equals(second)) break;
                    first = first.delta(cx, cy);
                }
            }
        }
        // find floor
        int floor = 1 + occupied.stream().mapToInt(p -> (int)p.y).max().getAsInt();
        for (int i = 0; ; i++) {
            var p = Point.of(500,0);
            if (occupied.contains(p)) {
                return i;
            }
            while (true) {
                if (p.y == floor) {
                    occupied.add(p);
                    break;
                } else if (!occupied.contains(p.delta(0, 1))) {
                    p = p.delta(0, 1);
                } else if (!occupied.contains(p.delta(-1, 1))) {
                    p = p.delta(-1, 1);
                } else if (!occupied.contains(p.delta(1, 1))) {
                    p = p.delta(1, 1);
                } else {
                    occupied.add(p);
                    break;
                }
            }
        }
    }
}
