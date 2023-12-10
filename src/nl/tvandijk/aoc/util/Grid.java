package nl.tvandijk.aoc.util;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private char def;
    private final char[][] grid;

    private Grid(String[] lines, char def) {
        this.def = def;
        // make a copy of lines
        grid = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            grid[i] = lines[i].toCharArray();
        }
    }

    public static Grid of(String[] lines, char def) {
        return new Grid(lines, def);
    }

    public static Grid of(String[] lines) {
        return new Grid(lines, '.');
    }

    public Set<Point> findAll(char ch) {
        var res = new HashSet<Point>();
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[y][x] == ch) {
                    res.add(Point.of(x, y));
                }
            }
        }
        return res;
    }

    public char get(long x, long y) {
        if (x < 0) return def;
        if (y < 0) return def;
        if (y >= grid.length) return def;
        var line = grid[(int)y];
        if (x >= line.length) return def;
        return line[(int)x];
    }

    public char get(Point p) {
        return get(p.x, p.y);
    }

    public void setDefault(char def) {
        this.def = def;
    }

    public int width() {
        return grid[0].length;
    }

    public int height() {
        return grid.length;
    }
}
