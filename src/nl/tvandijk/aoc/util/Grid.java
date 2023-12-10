package nl.tvandijk.aoc.util;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private char def;
    private final char[][] grid;

    /**
     * Create a grid
     * @param lines the lines
     * @param def the default value
     */
    private Grid(String[] lines, char def) {
        this.def = def;
        // make a copy of lines
        grid = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            grid[i] = lines[i].toCharArray();
        }
    }

    /**
     * Create a grid with a default value
     * @param lines the lines
     * @param def the default value
     * @return a grid
     */
    public static Grid of(String[] lines, char def) {
        return new Grid(lines, def);
    }

    /**
     * Create a grid with a default value of '.'
     * @param lines the lines
     * @return a grid
     */
    public static Grid of(String[] lines) {
        return new Grid(lines, '.');
    }

    /**
     * Find all points with a given character
     * @param ch the character to find
     * @return a set of points
     */
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

    /**
     * Get the character at a given point
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the character
     */
    public char get(long x, long y) {
        if (x < 0) return def;
        if (y < 0) return def;
        if (y >= grid.length) return def;
        var line = grid[(int)y];
        if (x >= line.length) return def;
        return line[(int)x];
    }

    /**
     * Get the character at a given point
     * @param p the point
     * @return the character
     */
    public char get(Point p) {
        return get(p.x, p.y);
    }

    /**
     * Set the default character for outside the boundaries of the given lines
     * @param def the default value
     */
    public void setDefault(char def) {
        this.def = def;
    }

    /**
     * Get the width of the grid
     * @return the width
     */
    public int width() {
        return grid[0].length;
    }

    /**
     * Get the height of the grid
     * @return the height
     */
    public int height() {
        return grid.length;
    }
}
