package nl.tvandijk.aoc.util;

import java.util.Arrays;

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

    public char get(int x, int y) {
        if (x < 0) return def;
        if (y < 0) return def;
        if (y >= grid.length) return def;
        var line = grid[y];
        if (x >= line.length) return def;
        return line[x];
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
