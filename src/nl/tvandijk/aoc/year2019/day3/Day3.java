package nl.tvandijk.aoc.year2019.day3;

import nl.tvandijk.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day3 extends Day {
    private static class Point implements Comparable<Point> {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            if (o.x == x) return o.y-y;
            else return o.x-x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "("+ x + ", " + y + ")";
        }
    }

    private static class Visitation {
        public int wire1;
        public int wire2;

        public Visitation(int wire1, int wire2) {
            this.wire1 = wire1;
            this.wire2 = wire2;
        }

        boolean isIntersection() {
            return wire1 != 0 && wire2 != 0;
        }

        public int getTimeSum() {
            return wire1 + wire2;
        }
    }

    private void update(Map<Point, Visitation> grid, int x, int y, int wire, int time) {
        grid.compute(new Point(x, y), (key, value) -> {
            if (value == null) {
                if (wire == 1) return new Visitation(time, 0);
                else if (wire == 2) return new Visitation(0, time);
                else return null;
            } else {
                if (wire == 1 && value.wire1 == 0) value.wire1 = time;
                if (wire == 2 && value.wire2 == 0) value.wire2 = time;
                return value;
            }
        });
    }

    private void runWire(Map<Point, Visitation> grid, String instructions, int wire) {
        int x=0, y=0, time=1;
        for (var part : instructions.split(",")) {
            var dir = part.charAt(0);
            var len = Integer.parseInt(part.substring(1));
            for (int i = 0; i < len; i++) {
                if (dir == 'R') x++;
                if (dir == 'L') x--;
                if (dir == 'U') y++;
                if (dir == 'D') y--;
                update(grid, x, y, wire, time);
                time += 1;
            }
        }
    }

    private Map<Point, Visitation> runWires(String wire1, String wire2) {
        Map<Point, Visitation> grid = new HashMap<>();
        runWire(grid, wire1, 1);
        runWire(grid, wire2, 2);
        return grid;
    }

//    private void test1 () {
//        String line1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72";
//        String line2 = "U62,R66,U55,R34,D71,R55,D58,R83";
//        Map<Point, Visitation> grid = new HashMap<>();
//        runWire(grid, line1, 1);
//        runWire(grid, line2, 2);
//        var result = grid.entrySet().stream().filter(entry -> entry.getValue() == 3)
//                .mapToInt(entry -> Math.abs(entry.getKey().x) + Math.abs(entry.getKey().y))
//                .min().getAsInt();
//        System.out.println("test 1 = " + result);
//    }
//
//    private void test2 () {
//        String line1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51";
//        String line2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
//        Map<Point, Integer> grid = new HashMap<>();
//        runWire(grid, line1, 1);
//        runWire(grid, line2, 2);
//        var result = grid.entrySet().stream().filter(entry -> entry.getValue() == 3)
//                .mapToInt(entry -> Math.abs(entry.getKey().x) + Math.abs(entry.getKey().y))
//                .min().getAsInt();
//        System.out.println("test 2 = " + result);
//
//    }



    @Override
    protected Object part1() {
        String[] lines = fileContents.split("[\r\n]+");
        var grid = runWires(lines[0], lines[1]);

        var result = grid.entrySet().stream().filter(entry -> entry.getValue().isIntersection())
                .mapToInt(entry -> Math.abs(entry.getKey().x) + Math.abs(entry.getKey().y))
                .min().getAsInt();

        return result;
    }


    @Override
    protected Object part2() {
        String[] lines = fileContents.split("[\r\n]+");
        var grid = runWires(lines[0], lines[1]);

        var result = grid.entrySet().stream().filter(entry -> entry.getValue().isIntersection())
                .mapToInt(entry -> entry.getValue().getTimeSum())
                .min().getAsInt();

        return result;
    }
}
