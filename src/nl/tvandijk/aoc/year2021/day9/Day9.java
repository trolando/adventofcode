package nl.tvandijk.aoc.year2021.day9;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.year2021.day8.DigitsLexer;
import nl.tvandijk.aoc.year2021.day8.DigitsParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Day9 extends Day {
    private int[] arr;
    private int width;
    private int height;

    private int[] basins;
    private Map<Integer, Integer> basinSize;

    private int get(int x, int y) {
        return arr[idx(x, y)];
    }

    private int idx(int x, int y) {
        return width*y + x;
    }

    private int spread(int x, int y, int basin) {
        if (basins[idx(x, y)] != 0) return 0;
        if (arr[idx(x, y)] == 9) return 0;

        basins[idx(x, y)] = basin;

        int result = 1;
        if (x > 0) result += spread(x-1, y, basin);
        if (y > 0) result += spread(x, y-1, basin);
        if ((x+1) < width) result += spread(x+1, y, basin);
        if ((y+1) < height) result += spread(x, y+1, basin);

        return result;
    }

    @Override
    protected void part1(String fileContents) {
        String[] lines = fileContents.split("\n");
        width = lines[0].trim().length();
        height = lines.length;

        arr = new int[lines.length * width];

        int i=0;
        for (var c : fileContents.toCharArray()) {
            switch (c) {
                case '\n':
                case '\r':
                case ' ':
                    continue;
                case '0':
                    arr[i++] = 0;
                    break;
                case '1':
                    arr[i++] = 1;
                    break;
                case '2':
                    arr[i++] = 2;
                    break;
                case '3':
                    arr[i++] = 3;
                    break;
                case '4':
                    arr[i++] = 4;
                    break;
                case '5':
                    arr[i++] = 5;
                    break;
                case '6':
                    arr[i++] = 6;
                    break;
                case '7':
                    arr[i++] = 7;
                    break;
                case '8':
                    arr[i++] = 8;
                    break;
                case '9':
                    arr[i++] = 9;
                    break;
            }
        }

        int result = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean isLow = true;
                int val = get(x, y);
                if (x > 0 && get(x-1, y) <= val) isLow = false;
                if ((x+1) < width && get(x+1, y) <= val) isLow = false;
                if (y > 0 && get(x, y-1) <= val) isLow = false;
                if ((y+1) < height && get(x, y+1) <= val) isLow = false;

//                if (isLow) System.out.printf("low point: (%d, %d) with value %d%n", x, y, val);

                if (isLow) result += 1+val;
            }
        }

        System.out.println("part 1: " + result);
    }


    @Override
    protected void part2(String fileContents) {
        basinSize = new TreeMap<>();

        String[] lines = fileContents.split("\n");
        width = lines[0].trim().length();
        height = lines.length;

        arr = new int[lines.length * width];
        basins = new int[height * width];

        int i=0;
        for (var c : fileContents.toCharArray()) {
            switch (c) {
                case '\n':
                case '\r':
                case ' ':
                    continue;
                case '0':
                    arr[i++] = 0;
                    break;
                case '1':
                    arr[i++] = 1;
                    break;
                case '2':
                    arr[i++] = 2;
                    break;
                case '3':
                    arr[i++] = 3;
                    break;
                case '4':
                    arr[i++] = 4;
                    break;
                case '5':
                    arr[i++] = 5;
                    break;
                case '6':
                    arr[i++] = 6;
                    break;
                case '7':
                    arr[i++] = 7;
                    break;
                case '8':
                    arr[i++] = 8;
                    break;
                case '9':
                    arr[i++] = 9;
                    break;
            }
        }

        int basinCount = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean isLow = true;
                int val = get(x, y);
                if (x > 0 && get(x-1, y) <= val) isLow = false;
                if ((x+1) < width && get(x+1, y) <= val) isLow = false;
                if (y > 0 && get(x, y-1) <= val) isLow = false;
                if ((y+1) < height && get(x, y+1) <= val) isLow = false;

                if (isLow) {
//                    System.out.printf("low point: (%d, %d) with value %d%n", x, y, val);
                    int size = spread(x, y, basinCount+1);
                    if (size != 0) {
                        basinSize.put(basinCount+1, size);
                        basinCount++;
                    }
                }
            }
        }

        var topValues = basinSize.values().stream().sorted().collect(Collectors.toList());
        long result = topValues.get(topValues.size()-1);
        result *= topValues.get(topValues.size()-2);
        result *= topValues.get(topValues.size()-3);

        System.out.println("part 2: " + result);
    }

    public static void main(String[] args) {
        run(Day9::new, "example.txt", "input.txt");
    }
}
