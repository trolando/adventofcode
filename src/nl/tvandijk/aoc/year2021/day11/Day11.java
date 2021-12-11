package nl.tvandijk.aoc.year2021.day11;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayDeque;
import java.util.Queue;

public class Day11 extends Day {
    private int[] grid;
    private boolean[] flashed;
    private Queue<Integer> flashQueue;
    private int flashCount = 0;

    private static int idx(int x, int y) {
        return x+10*y;
    }

    private void propagate(int x, int y) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            int i = idx(x, y);
            if (!flashed[i]) {
                grid[i]++;
                if (grid[i] >= 10) {
                    flashQueue.add(i);
                    flashed[i] = true;
                }
            }
        }
    }

    private boolean step() {
        flashed = new boolean[grid.length];

        for (int i = 0; i < grid.length; i++) {
            grid[i]++;
            if (grid[i] >= 10) {
                flashQueue.add(i);
                flashed[i] = true;
            }
        }

        while (!flashQueue.isEmpty()) {
            int i = flashQueue.poll();

            grid[i] = 0;
            flashCount++;

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

    private void parse(String fileContents) {
        grid = new int[100];
        flashCount = 0;
        flashQueue = new ArrayDeque<>();

        var justNumbers = fileContents.replaceAll("\\s+", "");
        for (int i = 0; i < 100; i++) {
            grid[i] = Integer.parseInt(String.valueOf(justNumbers.charAt(i)));
        }
    }

    @Override
    protected void part1(String fileContents) {
        parse(fileContents);

        for (int i=0; i<100; i++) {
            step();
        }

        System.out.println("Part 1: " + flashCount);
    }

    @Override
    protected void part2(String fileContents) {
        parse(fileContents);

        int steps = 1;
        while (!step()) {
            steps++;
        }

        System.out.println("Part 2: " + steps);
    }

    public static void main(String[] args) {
        run(Day11::new, "example.txt", "input.txt");
    }
}
