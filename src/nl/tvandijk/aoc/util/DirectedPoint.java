package nl.tvandijk.aoc.util;

import java.util.Objects;

/**
 * Represent a token on some point p, facing a direction d.
 * Allows moving the point forward/left/right.
 */
public class DirectedPoint {
    public final Point p;
    public final Direction d;

    private DirectedPoint(Point p, Direction d) {
        this.p = p;
        this.d = d;
    }

    public static DirectedPoint of(Point p, Direction d) {
        return new DirectedPoint(p, d);
    }

    public static DirectedPoint of(long x, long y, Direction d) {
        return new DirectedPoint(Point.of(x, y), d);
    }

    public DirectedPoint forward() {
        return new DirectedPoint(p.to(d), d);
    }

    public DirectedPoint rotateLeft() {
        switch (d) {
            case RIGHT, EAST -> { return new DirectedPoint(p, Direction.UP); }
            case LEFT, WEST -> { return new DirectedPoint(p, Direction.DOWN); }
            case UP, NORTH -> { return new DirectedPoint(p, Direction.LEFT); }
            case DOWN, SOUTH -> { return new DirectedPoint(p, Direction.RIGHT); }
        }
        throw new RuntimeException("Unknown direction: " + d);
    }

    public DirectedPoint rotateRight() {
        switch (d) {
            case RIGHT, EAST -> { return new DirectedPoint(p, Direction.DOWN); }
            case LEFT, WEST -> { return new DirectedPoint(p, Direction.UP); }
            case UP, NORTH -> { return new DirectedPoint(p, Direction.RIGHT); }
            case DOWN, SOUTH -> { return new DirectedPoint(p, Direction.LEFT); }
        }
        throw new RuntimeException("Unknown direction: " + d);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DirectedPoint that = (DirectedPoint) o;
        return Objects.equals(p, that.p) && d == that.d;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, d);
    }

    @Override
    public String toString() {
        return "DirectedPoint{" +
                "p=" + p +
                ", d=" + d +
                '}';
    }
}
