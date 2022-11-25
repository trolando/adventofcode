package nl.tvandijk.aoc.year2020.day17;

import java.util.Arrays;

class Point {
    public final int[] where;

    public Point(int[] where) {
        this.where = where;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return Arrays.equals(where, point.where);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(where);
    }
}
