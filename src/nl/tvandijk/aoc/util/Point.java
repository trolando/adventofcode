package nl.tvandijk.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

    public Point to(Direction dir, long amount) {
        switch (dir) {
            case RIGHT, EAST -> { return delta(amount, 0); }
            case LEFT, WEST -> { return delta(-amount, 0); }
            case UP, NORTH -> { return delta(0, -amount); }
            case DOWN, SOUTH -> { return delta(0, amount); }
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

    public Point nw() {
        return delta(-1, -1);
    }

    public Point ne() {
        return delta(1, -1);
    }

    public Point sw() {
        return delta(-1, 1);
    }

    public Point se() {
        return delta(1, 1);
    }

    public boolean inside(long minx, long miny, long width, long height) {
        long maxx = minx+width;
        long maxy = miny+height;
        return this.x >= minx && this.x < maxx && this.y >= miny && this.y < maxy;
    }

    /**
     * Returns a Stream of Points representing the four cardinal directions (horizontal and vertical).
     * These are relative directions: up (0, -1), down (0, 1), left (-1, 0), right (1, 0).
     * @return a Stream of Points
     */
    public static Stream<Point> dirs4() {
        return Stream.of(
                Point.of(0, -1), // Up
                Point.of(0, 1),  // Down
                Point.of(-1, 0), // Left
                Point.of(1, 0)   // Right
        );
    }

    /**
     * Returns a Stream of Points representing all eight directions (horizontal, vertical, and diagonal).
     * These are relative directions: up (0, -1), down (0, 1), left (-1, 0), right (1, 0),
     * and the four diagonals.
     * @return a Stream of Points
     */
    public static Stream<Point> dirs8() {
        return Stream.of(
                Point.of(0, -1),  // Up
                Point.of(0, 1),   // Down
                Point.of(-1, 0),  // Left
                Point.of(1, 0),   // Right
                Point.of(-1, -1), // Top-left
                Point.of(-1, 1),  // Bottom-left
                Point.of(1, -1),  // Top-right
                Point.of(1, 1)    // Bottom-right
        );
    }

    /**
     * Adds another point to this point, scaled by a multiplier.
     * @param other the point to add
     * @param multiplier the factor by which to scale the other point
     * @return a new Point resulting from the addition
     */
    public Point plus(Point other, long multiplier) {
        return new Point(this.x + other.x * multiplier, this.y + other.y * multiplier);
    }

    /**
     * Adds another point to this point.
     * @param other the point to add
     * @return a new Point resulting from the addition
     */
    public Point plus(Point other) {
        return plus(other, 1); // Default multiplier of 1
    }

    public Point minus(Point other) {
        return plus(other, -1);
    }

    public Point minus(Point other, long multiplier) {
        return plus(other, -multiplier);
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
