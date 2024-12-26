package nl.tvandijk.aoc.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid implements Iterable<Pair<Point, Character>> {
    private char def;
    private char[][] grid;
    private boolean repeats = false;

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

    private Grid(char[][] grid, char def) {
        this.grid = grid;
        this.def = def;
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

    public void setRepeats(boolean repeats) {
        this.repeats = repeats;
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

    public static Grid of(Collection<Point> points, long width, long height) {
        char[][] grid = new char[(int) height][(int) width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = '.';
            }
        }
        for (var pt : points) {
            grid[(int) pt.y][(int) pt.x] = '#';
        }
        return new Grid(grid, '#');
    }

    public Graph<Point> graph(Graph.SuccessorStreamFunction<Point> successor, Predicate<Character> pred) {
        return Graph.of(pt -> successor.successors(pt).filter(this::inRange).filter(p -> pred.test(get(p))).toList());
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

    public Point findOne(char ch) {
        var res = new HashSet<Point>();
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[y][x] == ch) {
                    return Point.of(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Get the character at a given point
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the character
     */
    public char get(long x, long y) {
        if (repeats) {
            // ackshually
            x = x >= 0 ? x % width() : (width() + (x % width())) % width();
            y = y >= 0 ? y % height() : (height() + (y % height())) % width();
        }
        if (x < 0) return def;
        if (y < 0) return def;
        if (y >= grid.length) return def;
        var line = grid[(int)y];
        if (x >= line.length) return def;
        return line[(int)x];
    }

    public boolean inRange(Point p) {
        if (p.x < 0) return false;
        if (p.y < 0) return false;
        if (p.y >= grid.length) return false;
        if (p.x >= grid[(int) p.y].length) return false;
        return true;
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

    public Set<Character> values() {
        Set<Character> res = new HashSet<>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                res.add(get(x, y));
            }
        }
        return res;
    }

    public void set(int x, int y, char ch) {
        grid[y][x] = ch;
    }

    public void set(Point p, char ch) {
        set((int) p.x, (int) p.y, ch);
    }

    public String getColumn(int x) {
        var sb = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            sb.append(grid[y][x]);
        }
        return sb.toString();
    }

    public String getRow(int y) {
        return new String(grid[y]);
    }

    public void setColumn(int x, String column) {
        for (int y = 0; y < grid.length; y++) {
            grid[y][x] = column.charAt(y);
        }
    }

    public void setRow(int y, String row) {
        grid[y] = row.toCharArray();
    }

    /**
     * Rotate the grid clockwise
     */
    public void clockwise() {
        var newGrid = new char[grid[0].length][grid.length];
        for (int y = 0; y < grid.length; y++) {
            var line = grid[y];
            for (int x = 0; x < line.length; x++) {
                newGrid[x][grid.length - y - 1] = line[x];
            }
        }
        grid = newGrid;
    }

    /**
     * Rotate the grid counterclockwise
     */
    public void counterclockwise() {
        var newGrid = new char[grid[0].length][grid.length];
        for (int y = 0; y < grid.length; y++) {
            var line = grid[y];
            for (int x = 0; x < line.length; x++) {
                newGrid[grid[0].length - x - 1][y] = line[x];
            }
        }
        grid = newGrid;
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

    /**
     * Count how often a character occurs in the grid
     * @param ch the character
     * @return the count
     */
    public long count(char ch) {
        long res = 0;
        for (var line : grid) {
            for (var c : line) {
                if (c == ch) res++;
            }
        }
        return res;
    }

    /**
     * Get a Stream of all rows as strings
     * @return a Stream of rows
     */
    public Stream<String> rows() {
        return Arrays.stream(grid).map(String::new);
    }

    /**
     * Get a Stream of all columns as strings
     * @return a Stream of columns
     */
    public Stream<String> cols() {
        return Stream.iterate(0, x -> x + 1)
                .limit(width())
                .map(this::getColumn);
    }

    /**
     * Get a Stream of all diagonals as strings
     * This includes both top-left to bottom-right and top-right to bottom-left diagonals
     * @return a Stream of diagonals
     */
    public Stream<String> diags() {
        // Top-left to bottom-right diagonals
        Stream<String> mainDiagonals = Stream.concat(
                Stream.iterate(0, i -> i + 1).limit(height())
                        .map(this::getDiagonalTopLeftToBottomRightFromRow),
                Stream.iterate(1, i -> i + 1).limit(width() - 1)
                        .map(this::getDiagonalTopLeftToBottomRightFromCol)
        );

        // Top-right to bottom-left diagonals
        Stream<String> antiDiagonals = Stream.concat(
                Stream.iterate(0, i -> i + 1).limit(height())
                        .map(this::getDiagonalTopRightToBottomLeftFromRow),
                Stream.iterate(0, i -> i + 1).limit(width() - 1)
                        .map(this::getDiagonalTopRightToBottomLeftFromCol)
        );

        return Stream.concat(mainDiagonals, antiDiagonals);
    }

    private String getDiagonalTopLeftToBottomRightFromRow(int row) {
        var sb = new StringBuilder();
        for (int x = 0, y = row; x < width() && y < height(); x++, y++) {
            sb.append(grid[y][x]);
        }
        return sb.toString();
    }

    private String getDiagonalTopLeftToBottomRightFromCol(int col) {
        var sb = new StringBuilder();
        for (int x = col, y = 0; x < width() && y < height(); x++, y++) {
            sb.append(grid[y][x]);
        }
        return sb.toString();
    }

    private String getDiagonalTopRightToBottomLeftFromRow(int row) {
        var sb = new StringBuilder();
        for (int x = width() - 1, y = row; x >= 0 && y < height(); x--, y++) {
            sb.append(grid[y][x]);
        }
        return sb.toString();
    }

    private String getDiagonalTopRightToBottomLeftFromCol(int col) {
        var sb = new StringBuilder();
        for (int x = col, y = 0; x >= 0 && y < height(); x--, y++) {
            sb.append(grid[y][x]);
        }
        return sb.toString();
    }

    /**
     * Get a Stream of all points in the grid.
     * @return a Stream of Point objects
     */
    public Stream<Point> points() {
        return IntStream.range(0, height())
                .boxed()
                .flatMap(y -> IntStream.range(0, width())
                        .mapToObj(x -> Point.of(x, y)));
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


    @Override
    public Iterator<Pair<Point, Character>> iterator() {
        return new Iterator<>() {
            private int x = 0;
            private int y = 0;

            @Override
            public boolean hasNext() {
                return y < grid.length && x < grid[y].length;
            }

            @Override
            public Pair<Point, Character> next() {
                if (!hasNext()) throw new NoSuchElementException();
                Point p = Point.of(x, y);
                char ch = grid[y][x];
                x++;
                if (x >= grid[y].length) {
                    x = 0;
                    y++;
                }
                return new Pair<>(p, ch);
            }
        };
    }
}
