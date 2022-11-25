package nl.tvandijk.aoc.year2021.day9;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 extends Day {
    private int[] grid;   // the input, parsed
    private int width;    // width of the grid
    private int height;   // height of the grid
    private int[] basins; // track in which basin each point is

    private int get(int x, int y) {
        return grid[idx(x, y)];
    }

    private int idx(int x, int y) {
        return width*y + x;
    }

    private int spread(int x, int y, int basin) {
        if (basins[idx(x, y)] != 0) return 0;
        if (grid[idx(x, y)] == 9) return 0;

        basins[idx(x, y)] = basin;

        int result = 1;

        if (x > 0) result += spread(x-1, y, basin);
        if (y > 0) result += spread(x, y-1, basin);
        if ((x+1) < width) result += spread(x+1, y, basin);
        if ((y+1) < height) result += spread(x, y+1, basin);

        return result;
    }

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        width = lines[0].trim().length();
        height = lines.length;
        grid = new int[height * width];
        basins = new int[height * width];

        int i=0;
        for (var c : fileContents.toCharArray()) {
            switch (c) {
                case '\n':
                case '\r':
                    continue;
                default:
                    grid[i++] = Integer.parseInt(Character.toString(c));
            }
        }
    }

    @Override
    protected Object part1() {
        int result = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean isLow = true;
                int val = get(x, y);
                if (x > 0 && get(x-1, y) <= val) isLow = false;
                if ((x+1) < width && get(x+1, y) <= val) isLow = false;
                if (y > 0 && get(x, y-1) <= val) isLow = false;
                if ((y+1) < height && get(x, y+1) <= val) isLow = false;

                if (isLow) {
                    result += 1 + val;
                }
            }
        }

        return result;
    }


    @Override
    protected Object part2() {
        int basinCount = 0;
        List<Integer> basinSizes = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean isLow = true;
                int val = get(x, y);
                if (x > 0 && get(x-1, y) <= val) isLow = false;
                if ((x+1) < width && get(x+1, y) <= val) isLow = false;
                if (y > 0 && get(x, y-1) <= val) isLow = false;
                if ((y+1) < height && get(x, y+1) <= val) isLow = false;

                if (isLow) {
                    int size = spread(x, y, basinCount+1);
                    if (size != 0) {
                        basinSizes.add(size);
                        basinCount++;
                    }
                }
            }
        }

        var topValues = basinSizes.stream().sorted().collect(Collectors.toList());
        long result = topValues.get(topValues.size()-1);
        result *= topValues.get(topValues.size()-2);
        result *= topValues.get(topValues.size()-3);

        return result;
    }
}
