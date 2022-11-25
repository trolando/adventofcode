package nl.tvandijk.aoc.year2020.day1;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;

public class Day1 extends Day {
    private int[] numbers;

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
        numbers = Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    protected Object part1() throws Exception {
        int count = numbers.length;
        for (int i = 0; i < count; i++) {
            for (int j = i+1; j < count; j++) {
                if ((numbers[i] + numbers[j]) == 2020) return numbers[i]*numbers[j];
            }
        }
        return null;
    }

    @Override
    protected Object part2() throws Exception {
        int count = numbers.length;
        for (int i = 0; i < count; i++) {
            for (int j = i+1; j < count; j++) {
                for (int k = j+1; k < count; k++) {
                    var x = numbers[i];
                    var y = numbers[j];
                    var z = numbers[k];
                    if ((x + y + z) == 2020) return (long)x*y*z;
                }
            }
        }
        return null;
    }
}
