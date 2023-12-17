package nl.tvandijk.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    public final long x;
    public final long y;

    private Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this(other.x, other.y);
    }

    public static Point of(long x, long y) {
        return new Point(x, y);
    }

    public long manhattan(Point other) {
        return Math.abs(x-other.x) + Math.abs(y-other.y);
    }

    public Point delta(long dx, long dy) {
        return new Point(x+dx, y+dy);
    }

    public List<Point> adjacent(boolean diag) {
        var res = new ArrayList<Point>();
        res.add(delta(-1, 0));
        res.add(delta(1, 0));
        res.add(delta(0, -1));
        res.add(delta(0, 1));
        if (diag) {
            res.add(delta(-1, 1));
            res.add(delta(-1, -1));
            res.add(delta(1, -1));
            res.add(delta(1, 1));
        }
        return res;
    }

    public Point to(Direction dir) {
        switch (dir) {
            case RIGHT, EAST -> { return right(); }
            case LEFT, WEST -> { return left(); }
            case UP, NORTH -> { return up(); }
            case DOWN, SOUTH -> { return down(); }
        }
        throw new RuntimeException("Unknown direction: " + dir);
    }

    public Point left() {
        return delta(-1, 0);
    }

    public Point right() {
        return delta(1, 0);
    }

    public Point up() {
        return delta(0, -1);
    }

    public Point down() {
        return delta(0, 1);
    }

    public boolean inside(long minx, long miny, long width, long height) {
        long maxx = minx+width;
        long maxy = miny+height;
        return this.x >= minx && this.x < maxx && this.y >= miny && this.y < maxy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
