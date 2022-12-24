package nl.tvandijk.aoc.year2022.day24;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day24 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    int width;
    int height;
    Map<Point, List<Integer>> blizzards = new HashMap<>();

    Map<Point, List<Integer>> nextBlizzards(Map<Point, List<Integer>> blizzards) {
        Map<Point, List<Integer>> result = new HashMap<>();
        for (var e : blizzards.entrySet()) {
            var p = e.getKey();
            for (var dir : e.getValue()) {
                Point p2 = null;
                switch (dir) {
                    case 0 -> {
                        if (p.y == 1) p2 = Point.of(p.x, height - 2);
                        else p2 = Point.of(p.x, p.y - 1);
                    }
                    case 1 -> {
                        if (p.x == width - 2) p2 = Point.of(1, p.y);
                        else p2 = Point.of(p.x + 1, p.y);
                    }
                    case 2 -> {
                        if (p.y == height - 2) p2 = Point.of(p.x, 1);
                        else p2 = Point.of(p.x, p.y + 1);
                    }
                    case 3 -> {
                        if (p.x == 1) p2 = Point.of(width - 2, p.y);
                        else p2 = Point.of(p.x - 1, p.y);
                    }
                }
                result.computeIfAbsent(p2, s -> new ArrayList<>()).add(dir);
            }
        }
        return result;
    }

    boolean isWall(Point p) {
        if (p.x <= 0) return true;
        if (p.x >= width-1) return true;
        if (p.y == 0 && p.x != 1) return true;
        if (p.y < 0) return true;
        if (p.y == height-1 && p.x != width-2) return true;
        if (p.y >= height) return true;
        return false;
    }

    void printMap(Map<Point, List<Integer>> blizzards, Set<Point> positions) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isWall(Point.of(x, y))) System.out.print("#");
                else {
                    var v = blizzards.get(Point.of(x, y));
                    if (v == null) {
                        if (positions.contains(Point.of(x, y))) System.out.print("\033[1mE\033[m");
                        else System.out.print(".");
                    }
                    else if (v.size() > 1) System.out.print(v.size());
                    else if (v.get(0) == 0) System.out.print("^");
                    else if (v.get(0) == 1) System.out.print(">");
                    else if (v.get(0) == 2) System.out.print("v");
                    else if (v.get(0) == 3) System.out.print("<");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    protected Object part1() {
        // part 1
        width = lines[0].trim().length();
        height = lines.length;
        int y=0;
        for (var line : lines) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '^') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(0);
                if (line.charAt(x) == '>') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(1);
                if (line.charAt(x) == 'v') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(2);
                if (line.charAt(x) == '<') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(3);
            }
            y++;
        }
        // go from (1,0) to (width-1,height-1)
        Set<Point> positions = new HashSet<>();
        int minute = 0;
        positions.add(Point.of(1,0));
        Point target = Point.of(width-2, height-1);
        while (true) {
//            System.out.println("Minute " + minute);
//            printMap(blizzards, positions);

            minute++;
            blizzards = nextBlizzards(blizzards);
            Set<Point> next = new HashSet<>();
            for (var pos : positions) {
                if (!blizzards.containsKey(pos)) next.add(pos); // wait
                for (var adj : pos.adjacent(false)) {
                    if (!isWall(adj) && !blizzards.containsKey(adj)) next.add(adj);
                }
            }
            if (next.contains(target)) {
                return minute;
            }
            positions = next;
        }
    }

    @Override
    protected boolean resetForPartTwo() {
        return true;
    }

    @Override
    protected Object part2() {
        // part 2
        width = lines[0].trim().length();
        height = lines.length;
        int y=0;
        for (var line : lines) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '^') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(0);
                if (line.charAt(x) == '>') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(1);
                if (line.charAt(x) == 'v') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(2);
                if (line.charAt(x) == '<') blizzards.computeIfAbsent(Point.of(x, y), s->new ArrayList<>()).add(3);
            }
            y++;
        }
        // go from (1,0) to (width-1,height-1)
        Set<Point> positions = new HashSet<>();
        int minute = 0;
        positions.add(Point.of(1,0));
        Point[] targets = new Point[] { Point.of(width-2, height-1), Point.of(1,0), Point.of(width-2, height-1) };
        int goal = 0;
        while (true) {
//            System.out.println("Minute " + minute);
//            printMap(blizzards, positions);

            minute++;
            blizzards = nextBlizzards(blizzards);
            Set<Point> next = new HashSet<>();
            for (var pos : positions) {
                if (!blizzards.containsKey(pos)) next.add(pos); // wait
                for (var adj : pos.adjacent(false)) {
                    if (!isWall(adj) && !blizzards.containsKey(adj)) next.add(adj);
                }
            }
            if (next.contains(targets[goal])) {
                if (goal == targets.length-1) return minute;
                next.clear();
                next.add(targets[goal]);
                goal++;
            }
            positions = next;
        }
    }
}
