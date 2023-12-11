package nl.tvandijk.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point3 {
    public final long x;
    public final long y;
    public final long z;

    private Point3(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3(Point3 other) {
        this(other.x, other.y, other.z);
    }

    public static Point3 of(long x, long y, long z) {
        return new Point3(x, y, z);
    }

    public Point3 delta(long dx, long dy, long dz) {
        return new Point3(x+dx, y+dy, z+dz);
    }

    public Point3 delta(Point3 d) {
        return new Point3(x+d.x, y+d.y, z+d.z);
    }

    public Point3 reverse() {
        return new Point3(-x, -y, -z);
    }

    public long manhattan(Point3 other) {
        return Math.abs(x-other.x) + Math.abs(y-other.y) + Math.abs(z-other.z);
    }

    public List<Point3> adjacent(boolean diag) {
        var res = new ArrayList<Point3>();
        res.add(delta(-1, 0, 0));
        res.add(delta(1, 0, 0));
        res.add(delta(0, -1, 0));
        res.add(delta(0, 1, 0));
        res.add(delta(0, 0, -1));
        res.add(delta(0, 0, 1));
        if (diag) {
            res.add(delta(-1, 1, -1));
            res.add(delta(-1, -1, -1));
            res.add(delta(1, -1, -1));
            res.add(delta(1, 1, -1));
            res.add(delta(-1, 1, 1));
            res.add(delta(-1, -1, 1));
            res.add(delta(1, -1, 1));
            res.add(delta(1, 1, 1));
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3 point3)) return false;
        return x == point3.x && y == point3.y && z == point3.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
