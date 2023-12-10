package nl.tvandijk.aoc.year2023.day10;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Graph;
import nl.tvandijk.aoc.util.Pair;
import nl.tvandijk.aoc.util.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Day10 extends Day {
    private Collection<Pair<Point, Long>> successor(Point p) {
        var res = new ArrayList<Pair<Point, Long>>();
        if ("S-7J".contains(String.valueOf(grid.get(p))) && "-FL".contains(String.valueOf(grid.get(p.left())))) {
            res.add(Pair.of(p.left(), 1L));
        }
        if ("S-FL".contains(String.valueOf(grid.get(p))) && "-7J".contains(String.valueOf(grid.get(p.right())))) {
            res.add(Pair.of(p.right(), 1L));
        }
        if ("S|7F".contains(String.valueOf(grid.get(p))) && "|JL".contains(String.valueOf(grid.get(p.down())))) {
            res.add(Pair.of(p.down(), 1L));
        }
        if ("S|JL".contains(String.valueOf(grid.get(p))) && "|7F".contains(String.valueOf(grid.get(p.up())))) {
            res.add(Pair.of(p.up(), 1L));
        }
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        var loop = new Graph<>(this::successor).reachAll(grid.findAll('S'));
        return loop.values().stream().mapToLong(x -> x).max().orElseThrow();
    }

    private char determineS(Point p) {
        boolean left = "-FL".contains(String.valueOf(grid.get(p.left())));
        boolean right = "-7J".contains(String.valueOf(grid.get(p.right())));
        boolean down = "|JL".contains(String.valueOf(grid.get(p.down())));
        boolean up = "|7F".contains(String.valueOf(grid.get(p.up())));
        if (left && right) return '-';
        if (up && down) return '|';
        if (left && up) return 'J';
        if (left && down) return '7';
        if (right && up) return 'L';
        if (right && down) return 'F';
        return '.';
    }

    @Override
    protected Object part2() {
        // part 2
        var loop = new Graph<>(this::successor).reachAll(grid.findAll('S')).keySet();

        Set<Point> interior = new HashSet<>();
        for (int y = 0; y < grid.height(); y++) {
            boolean inside = false;
            char last = 0;
            for (int x = 0; x < grid.width(); x++) {
                if (loop.contains(Point.of(x, y))) {
                    var ch = grid.get(x, y);
                    if (ch == 'S') {
                        ch = determineS(Point.of(x, y));
                    }
                    if (ch == '|' || ch == 'L' || ch == 'F') {
                        inside = !inside;
                        last = ch;
                    } else if (ch == '7') {
                        if (last != 'L') inside = !inside;
                        last = ch;
                    } else if (ch == 'J') {
                        if (last != 'F') inside = !inside;
                        last = ch;
                    }
                } else {
                    if (inside) interior.add(Point.of(x, y));
                }
            }
        }

        printInterior(loop, interior);
        return interior.size();
    }

    private void printGrid(Set<Point> loop) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                if (loop.contains(Point.of(x, y))) {
                    System.out.print("\033[1;34m"+format(grid.get(x, y))+"\033[m");
                } else {
                    System.out.print("\033[37m"+format(grid.get(x, y))+"\033[m");
                }
            }
            System.out.println();
        }
    }

    private void printInterior(Collection<Point> loop, Collection<Point> interior) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                if (loop.contains(Point.of(x, y))) {
                    System.out.print("\033[34m"+format(grid.get(x, y))+"\033[m");
                } else if (interior.contains(Point.of(x, y))) {
                    System.out.print("\033[1;38;5;46mI\033[m");
                } else {
                    System.out.print("\033[31mO\033[m");
                }
            }
            System.out.println();
        }
    }

    char format(char ch) {
        if (ch == 'S') return 'S';
        if (ch == '7') return '╗';
        if (ch == 'J') return '╝';
        if (ch == 'F') return '╔';
        if (ch == 'L') return '╚';
        if (ch == '-') return '═';
        if (ch == '|') return '║';
        return '.';
    }

    public Day10() {
        super("example.txt", "example2.txt", "example3.txt", "input.txt");
    }
}
