package nl.tvandijk.aoc.year2022.day9;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day9 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    private Point follow(Point head, Point tail) {
        int cx = head.x-tail.x;
        int cy = head.y-tail.y;
        if (cx > 1 || cx < -1 || cy > 1 || cy < -1) {
            int x = tail.x;
            int y = tail.y;
            if (cy == 0) {
                if (cx > 1) x++;
                else x--;
            } else if (cx == 0) {
                if (cy > 1) y++;
                else y--;
            } else {
                if (cx > 0) x++;
                if (cx < 0) x--;
                if (cy > 0) y++;
                if (cy < 0) y--;
            }
            return Point.of(x, y);
        } else {
            return tail;
        }
    }

    @Override
    protected Object part1() {
        // part 1
        Set<Point> visited = new HashSet<>();
        Point head = Point.of(0,0);
        Point tail = Point.of(0,0);
        visited.add(tail);
        for (var line : lines) {
            int amount = Integer.parseInt(line.substring(2));
            for (int i = 0; i < amount; i++) {
                switch (line.charAt(0)) {
                    case 'R' -> head = head.delta(1, 0);
                    case 'L' -> head = head.delta(-1, 0);
                    case 'U' -> head = head.delta(0, 1);
                    case 'D' -> head = head.delta(0, -1);
                    default -> System.out.println("error");
                }
                tail = follow(head, tail);
                visited.add(tail);
            }
        }
        return visited.size();
    }

    @Override
    protected Object part2() {
        // part 2
        Set<Point> visited = new HashSet<>();
        Point[] knots = new Point[10];
        for (int i = 0; i < knots.length; i++) {
            knots[i] = Point.of(0, 0);
        }
        visited.add(new Point(knots[knots.length-1]));
        for (var line : lines) {
            int amount = Integer.parseInt(line.substring(2));
            for (int i = 0; i < amount; i++) {
                switch (line.charAt(0)) {
                    case 'R' -> knots[0] = knots[0].delta(1, 0);
                    case 'L' -> knots[0] = knots[0].delta(-1, 0);
                    case 'U' -> knots[0] = knots[0].delta(0, 1);
                    case 'D' -> knots[0] = knots[0].delta(0, -1);
                    default -> System.out.println("error");
                }
                for (int j = 1; j < knots.length; j++) {
                    knots[j] = follow(knots[j-1], knots[j]);
                }
                visited.add(knots[knots.length-1]);
            }
        }
        return visited.size();
    }
}
