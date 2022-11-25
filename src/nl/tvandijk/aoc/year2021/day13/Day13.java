package nl.tvandijk.aoc.year2021.day13;

import nl.tvandijk.aoc.common.Day;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day13 extends Day {
    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point tfX(int x) {
            if (this.x > x) return new Point(x*2-this.x, y);
            else return this;
        }

        public Point tfY(int y) {
            if (this.y > y) return new Point(x, y*2-this.y);
            else return this;
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
    }

    private Set<Point> points;

    void parse(boolean oneFold) {
        points = new HashSet<>();

        boolean dots = true;
        for (var line : lines) {
            if (line.isEmpty()) continue;
            if (line.startsWith("fold")) dots = false;

            if (dots) {
                var parts = line.split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            } else {
                if (line.startsWith("fold along y=")) {
                    int y = Integer.parseInt(line.substring(13));

                    Set<Point> newP = new HashSet<>();
                    for (var p : points) newP.add(p.tfY(y));
                    points = newP;
                } else {
                    int x = Integer.parseInt(line.substring(13));

                    Set<Point> newP = new HashSet<>();
                    for (var p : points) newP.add(p.tfX(x));
                    points = newP;
                }
                if (oneFold) return;
            }
        }
    }

    @Override
    protected Object part1() {
        parse(true);
        return points.size();
    }

    @Override
    protected Object part2() {
        parse(false);

        int cx = points.stream().mapToInt(p -> p.x).max().orElseThrow()+1;
        int cy = points.stream().mapToInt(p -> p.y).max().orElseThrow()+1;

        boolean[] grid = new boolean[cx*cy];
        for (var p : points) grid[p.y*cx+p.x] = true;

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < cy; y++) {
            sb.append("\n");
            for (int x = 0; x < cx; x++) {
                sb.append(grid[cx*y+x] ? "#" : " ");
            }
        }

        return sb;
    }
}
