package nl.tvandijk.aoc.year2021.day11;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class Day11 extends Day {
    int[] grid;
    Queue<Integer> toflash;
    boolean[] flashed;
    int flashes = 0;

    private static int idx(int x, int y) {
        return x+10*y;
    }

    private void propagate(int x, int y) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            int i = idx(x, y);
            if (flashed[i]) return;

            grid[i]++;

            if (grid[i] >= 10) {
                flashes++;
                grid[i] = 0;
                toflash.add(i);
                flashed[i] = true;
            }
        }
    }

    private boolean step() {
        toflash = new ArrayDeque<>();
        flashed = new boolean[grid.length];

        for (int i = 0; i < grid.length; i++) {
            grid[i]++;

            if (grid[i] >= 10) {
                flashes++;
                grid[i] = 0;
                toflash.add(i);
                flashed[i] = true;
            }
        }

        while (!toflash.isEmpty()) {
            int i = toflash.poll();
            int x = i % 10;
            int y = i / 10;

            propagate(x - 1, y - 1);
            propagate(x - 1, y + 1);
            propagate(x - 1, y);
            propagate(x, y - 1);
            propagate(x, y + 1);
            propagate(x + 1, y - 1);
            propagate(x + 1, y);
            propagate(x + 1, y + 1);
        }

        for (int i = 0; i < 100; i++) {
            if (!flashed[i]) return false;
        }

        return true;
    }

    @Override
    protected void part1(String fileContents) {
        grid = new int[100];
        flashes = 0;

        var justNumbers = fileContents.replaceAll("\\s+", "");
        for (int i = 0; i < 100; i++) {
            grid[i] = Integer.parseInt(String.valueOf(justNumbers.charAt(i)));
        }

        for (int i=0; i<100; i++) {
            step();
        }

        System.out.println("Part 1: " + flashes);
    }

    @Override
    protected void part2(String fileContents) {
        grid = new int[100];
        flashes = 0;

        var justNumbers = fileContents.replaceAll("\\s+", "");
        for (int i = 0; i < 100; i++) {
            grid[i] = Integer.parseInt(String.valueOf(justNumbers.charAt(i)));
        }

        int steps = 0;
        while (true) {
            steps++;
            if (step()) {
                System.out.println("Part 2: " + steps);
                return;
            }
        }
    }

    public static void main(String[] args) {
        run(Day11::new, "example.txt", "input.txt");
    }
}
