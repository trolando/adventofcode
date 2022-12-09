package nl.tvandijk.aoc.util;

public class Point extends Pair<Integer, Integer> {
    public Point(Integer integer, Integer integer2) {
        super(integer, integer2);
    }

    public Point(Point other) {
        super(other.a, other.b);
    }

    public static Point of(int a, int b) {
        return new Point(a, b);
    }
}
