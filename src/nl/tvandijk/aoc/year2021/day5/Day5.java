package nl.tvandijk.aoc.year2021.day5;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.year2021.day4.InputLexer;
import nl.tvandijk.aoc.year2021.day4.InputParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends Day {
    private static class Point implements Comparable<Point> {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Point o) {
            if (x == o.x) return o.y-y;
            else return o.x-x;
        }
    }

    private void addToGrid(Map<Point, Integer> grid, int x, int y) {
        grid.compute(new Point(x, y), (pt, curval) -> {
            if (curval == null) {
                return 1;
            } else {
                return curval+1;
            }
        });
    }


    @Override
    protected void part1(String fileContents) {
        var lexer = new LinesLexer(CharStreams.fromString(fileContents));
        var parser = new LinesParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        Map<Point, Integer> grid = new TreeMap<>();

        for (var lineCtx : tree.line()) {
            var numbers = lineCtx.NUMBER().stream().mapToInt(node -> Integer.parseInt(node.getText())).toArray();
            // numbers : x1,y1 -> x2,y2

            // check if horizontal or vertical
            // if not: skip
            if (numbers[0] == numbers[2]) {
                // same x: vertical
                int x = numbers[0];

                if (numbers[1] > numbers[3]) {
                    for (int y = numbers[3]; y <= numbers[1]; y++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                } else {
                    for (int y = numbers[1]; y <= numbers[3]; y++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                }

            }
            else if (numbers[1] == numbers[3]) {
                // samy y: horizontal
                int y = numbers[1];

                if (numbers[0] > numbers[2]) {
                    for (int x = numbers[2]; x <= numbers[0]; x++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                } else {
                    for (int x = numbers[0]; x <= numbers[2]; x++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                }
            }

        }

        int count = 0;
        for (Integer val : grid.values()) {
            if (val > 1) count++;
        }

        System.out.println("Solution of part 1: " + count);
    }

    @Override
    protected void part2(String fileContents) {
        var lexer = new LinesLexer(CharStreams.fromString(fileContents));
        var parser = new LinesParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        Map<Point, Integer> grid = new TreeMap<>();

        for (var lineCtx : tree.line()) {
            var numbers = lineCtx.NUMBER().stream().mapToInt(node -> Integer.parseInt(node.getText())).toArray();
            // numbers : x1,y1 -> x2,y2

            // check if horizontal or vertical
            // if not: skip
            if (numbers[0] == numbers[2]) {
                // same x: vertical
                int x = numbers[0];

                if (numbers[1] > numbers[3]) {
                    for (int y = numbers[3]; y <= numbers[1]; y++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                } else {
                    for (int y = numbers[1]; y <= numbers[3]; y++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                }
            }
            else if (numbers[1] == numbers[3]) {
                // samy y: horizontal
                int y = numbers[1];

                if (numbers[0] > numbers[2]) {
                    for (int x = numbers[2]; x <= numbers[0]; x++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                } else {
                    for (int x = numbers[0]; x <= numbers[2]; x++) {
                        // Add (x,y) to grid
                        addToGrid(grid, x, y);
                    }
                }
            } else {
                int x1 = numbers[0];
                int y1 = numbers[1];
                int x2 = numbers[2];
                int y2 = numbers[3];

                int dx = x1 > x2 ? -1 : 1;
                int dy = y1 > y2 ? -1 : 1;
                int k = x1 > x2 ? x1-x2 : x2-x1;

                for (int i = 0; i <= k; i++) {
                    int x = x1 + dx * i;
                    int y = y1 + dy * i;
                    addToGrid(grid, x, y);
                }
            }
        }

        int count = 0;
        for (Integer val : grid.values()) {
            if (val > 1) count++;
        }

        System.out.println("Solution of part 2: " + count);
    }

    public static void main(String[] args) {
        run(Day5::new, "example.txt", "input.txt");
    }
}
