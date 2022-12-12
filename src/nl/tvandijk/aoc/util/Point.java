package nl.tvandijk.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    public final int x;
    public final int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this(other.x, other.y);
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public Point delta(int dx, int dy) {
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

    public boolean inside(int minx, int miny, int width, int height) {
        int maxx = minx+width;
        int maxy = miny+height;
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
