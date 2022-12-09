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

    private void follow(Point head, Point tail) {
        int ca = head.a-tail.a;
        int cb = head.b-tail.b;
        if (ca > 1 || ca < -1 || cb > 1 || cb < -1) {
            if (cb == 0) {
                if (ca > 1) tail.a++;
                else tail.a--;
            } else if (ca == 0) {
                if (cb > 1) tail.b++;
                else tail.b--;
            } else {
                if (ca > 0) tail.a++;
                if (ca < 0) tail.a--;
                if (cb > 0) tail.b++;
                if (cb < 0) tail.b--;
            }
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
                    case 'R' -> head.a++;
                    case 'L' -> head.a--;
                    case 'U' -> head.b++;
                    case 'D' -> head.b--;
                    default -> System.out.println("error");
                }
                follow(head, tail);
                visited.add(new Point(tail.a, tail.b));
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
                    case 'R' -> knots[0].a++;
                    case 'L' -> knots[0].a--;
                    case 'U' -> knots[0].b++;
                    case 'D' -> knots[0].b--;
                    default -> System.out.println("error");
                }
                for (int j = 1; j < knots.length; j++) {
                    follow(knots[j-1], knots[j]);
                }
                visited.add(new Point(knots[knots.length-1]));
            }
        }
        return visited.size();
    }
}
