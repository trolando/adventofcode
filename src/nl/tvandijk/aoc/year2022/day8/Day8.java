package nl.tvandijk.aoc.year2022.day8;

import nl.tvandijk.aoc.common.Day;

public class Day8 extends Day {
    private int len;
    private int[] grid;

    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);

        len = lines[0].trim().length();
        grid = new int[len*len];
        for (int i = 0; i < len*len; i++) {
            grid[i] = lines[i/len].charAt(i%len)-'0';
        }
    }

    private boolean checkVisible(int max, int x, int y, int cx, int cy) {
        while (true) {
            x += cx;
            y += cy;
            if (x < 0 || y < 0 || x >= len || y >= len) return true;
            if (grid[x + y * len] >= max) return false;
        }
    }

    @Override
    protected Object part1() {
        // part 1
        int count=0;
        for (int i = 0; i < len*len; i++) {
            int x=i%len, y=i/len;
            if (checkVisible(grid[x+y*len], x, y, -1, 0) ||
                checkVisible(grid[x+y*len], x, y, 0, -1) ||
                checkVisible(grid[x+y*len], x, y, 1, 0) ||
                checkVisible(grid[x+y*len], x, y, 0, 1)) count++;
        }
        return count;
    }

    private int countVisible(int max, int x, int y, int cx, int cy) {
        int c = 0;
        while (true) {
            x += cx;
            y += cy;
            if (x < 0 || y < 0 || x >= len || y >= len) return c;
            c++;
            if (grid[x + y * len] >= max) return c;
        }
    }

    @Override
    protected Object part2() {
        // part 2
        int highest = 0;
        for (int i = 0; i < len*len; i++) {
            int x=i%len, y=i/len;
            int score = countVisible(grid[x+y*len], x, y, -1, 0) *
                    countVisible(grid[x+y*len], x, y, 0, -1) *
                    countVisible(grid[x+y*len], x, y, 1, 0) *
                    countVisible(grid[x+y*len], x, y, 0, 1);
            if (score > highest) highest = score;
        }
        return highest;
    }
}
