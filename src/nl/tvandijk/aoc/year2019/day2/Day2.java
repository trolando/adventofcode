package nl.tvandijk.aoc.year2019.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;

public class Day2 extends Day {
    private long[] runProgram(long[] data) {
        int pc = 0;

        while (true) {
            switch ((int)data[pc]) {
                case 1 -> {
                    // adding
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var p3 = data[pc + 3];
                    var D1 = data[(int)p1];
                    var D2 = data[(int)p2];
                    data[(int)p3] = D1 + D2;
                    pc += 4;
                }
                case 2 -> {
                    // multiplying
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var p3 = data[pc + 3];
                    var D1 = data[(int)p1];
                    var D2 = data[(int)p2];
                    data[(int)p3] = D1 * D2;
                    pc += 4;
                }
                case 99 -> {
                    // halt
                    return data;
                }
            }
        }
    }

    @Override
    protected Object part1() {
        // example: 1,9,10,3,2,3,11,0,99,30,40,50

        long[] data = Arrays.stream(fileContents.split(",")).mapToLong(Long::parseLong).toArray();
        data[1] = 12;
        data[2] = 2;
        data = runProgram(data);
        return data[0];
    }

    private boolean tryInputs(long[] data, long one, long two, long expected) {
        var copy = Arrays.copyOf(data, data.length);
        copy[1] = one;
        copy[2] = two;
        copy = runProgram(copy);
        return copy[0] == expected;
    }

    @Override
    protected Object part2() {
        long[] data = Arrays.stream(fileContents.split(",")).mapToLong(Long::parseLong).toArray();

        for (int one = 0; one < 100; one++) {
            for (int two = 0; two < 100; two++) {
                if (tryInputs(data, one, two, 19690720)) {
                    System.out.printf("Inputs %d and %d give %d!%n", one, two, 19690720);
                    return one*100+two;
                }
            }
        }

        return null;
    }
}
