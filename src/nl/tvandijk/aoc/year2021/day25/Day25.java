package nl.tvandijk.aoc.year2021.day25;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.common.Pair;
import nl.tvandijk.aoc.common.TransitionSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day25 extends Day {
    int wide;
    int high;

    int np(int x, int y, int c) {
        if (c == 1) {
            if ((x+1) == wide) x=0;
            else x++;
        } else {
            if ((y+1) == high) y=0;
            else y++;
        }
        return y*wide+x;
    }

    @Override
    protected void part1(String fileContents) {
        var lines = fileContents.split(System.lineSeparator());

        wide = lines[0].length();
        var grid = fileContents.replaceAll("[\r\n ]+", "").chars().map(x -> x == '>' ? 1 : x == 'v' ? 2 : 0).toArray();
        high = grid.length / wide;

        int steps = 0;
        while (true) {
            int moved = 0;
            steps++;

            int[] igr = new int[grid.length];
            for (int i = 0; i < grid.length; i++) {
                int x = i % wide;
                int y = i / wide;
                int n = np(x, y, 1);
                if (grid[i] == 1 && grid[n] == 0) {
                    igr[n] = 1;
                    moved++;
                } else if (grid[i] != 0) {
                    igr[i] = grid[i];
                }
            }
            grid = igr;

            igr = new int[grid.length];
            for (int i = 0; i < grid.length; i++) {
                int x = i % wide;
                int y = i / wide;
                int n = np(x, y, 2);
                if (grid[i] == 2 && grid[n] == 0) {
                    igr[n] = 2;
                    moved++;
                } else if (grid[i] != 0) {
                    igr[i] = grid[i];
                }
            }
            grid = igr;

            if (moved == 0) break;

            var sb = new StringBuilder();
            for (int x = 0; x < grid.length; x++) {
                if (x != 0 && (x%wide) == 0) sb.append('\n');
                if (grid[x] == 1) sb.append('>');
                else if (grid[x] == 2) sb.append('v');
                else sb.append('.');
            }

            System.out.println("After step " + steps);
            System.out.println(sb);
            System.out.println();
        }

        System.out.println("Count: " + steps);
    }

    @Override
    protected void part2(String fileContents) {
        var lines = fileContents.split(System.lineSeparator());

    }

    public static void main(String[] args) {
        run(Day25::new, "example.txt", "input.txt");
    }
}
