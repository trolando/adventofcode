package nl.tvandijk.aoc.util;

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
}
