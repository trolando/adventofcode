package nl.tvandijk.aoc.util;

import java.util.*;

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

    private Grid(List<String> lines, char def) {
        this.def = def;
        // make a copy of lines
        grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
    }

    private Grid(Grid g) {
        this.def = g.def;
        grid = new char[g.grid.length][];
        for (int i = 0; i < g.grid.length; i++) {
            grid[i] = Arrays.copyOf(g.grid[i], g.grid[i].length);
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
     * Create a grid with a default value
     * @param lines the lines
     * @param def the default value
     * @return a grid
     */
    public static Grid of(List<String> lines, char def) {
        return new Grid(lines, def);
    }

    public Grid copy() {
        return new Grid(this);
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
     * Create a grid with a default value of '.'
     * @param lines the lines
     * @return a grid
     */
    public static Grid of(List<String> lines) {
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

    public void set(int x, int y, char ch) {
        grid[y][x] = ch;
    }

    public void clockwise() {
        var newGrid = new char[grid[0].length][grid.length];
        for (int y = 0; y < grid.length; y++) {
            var line = grid[y];
            for (int x = 0; x < line.length; x++) {
                newGrid[x][grid.length - y - 1] = line[x];
            }
        }
        for (int y = 0; y < grid.length; y++) {
            grid[y] = newGrid[y];
        }
    }

    public void counterclockwise() {
        var newGrid = new char[grid[0].length][grid.length];
        for (int y = 0; y < grid.length; y++) {
            var line = grid[y];
            for (int x = 0; x < line.length; x++) {
                newGrid[grid[0].length - x - 1][y] = line[x];
            }
        }
        for (int y = 0; y < grid.length; y++) {
            grid[y] = newGrid[y];
        }
    }

    public void rotate() {
        var newGrid = new char[grid[0].length][grid.length];
        for (int y = 0; y < grid.length; y++) {
            var line = grid[y];
            for (int x = 0; x < line.length; x++) {
                newGrid[x][grid.length - y - 1] = line[x];
            }
        }
        for (int y = 0; y < grid.length; y++) {
            grid[y] = newGrid[y];
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var line : grid) {
            sb.append(line);
            sb.append('\n');
        }
        return sb.toString();
    }

    public long count(char ch) {
        long res = 0;
        for (var line : grid) {
            for (var c : line) {
                if (c == ch) res++;
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid1 = (Grid) o;
        return def == grid1.def && Arrays.deepEquals(grid, grid1.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(def);
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }
}
