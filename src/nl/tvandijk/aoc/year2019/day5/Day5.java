package nl.tvandijk.aoc.year2019.day5;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends Day {
    private int[] runProgram(int[] data, List<Integer> input, List<Integer> output) {
        int pc = 0;

        while (true) {
            int opcode = (data[pc]) % 100;

            int mode0 = 0;
            int mode1 = 0;
            int mode2 = 0;

            if (((data[pc] % 1000) / 100) != 0) mode0 = 1;
            if (((data[pc] % 10000) / 1000) != 0) mode1 = 1;
            if (((data[pc] % 100000) / 10000) != 0) mode2 = 1;

            switch (opcode) {
                case 1 -> {
                    // adding
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var p3 = data[pc + 3];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    var D2 = mode1 == 0 ? data[p2] : p2;
                    data[p3] = D1 + D2;
                    pc += 4;
                }
                case 2 -> {
                    // multiplying
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var p3 = data[pc + 3];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    var D2 = mode1 == 0 ? data[p2] : p2;
                    data[p3] = D1 * D2;
                    pc += 4;
                }
                case 3 -> {
                    // in
                    var p1 = data[pc + 1];
                    data[p1] = input.get(0);
                    input = input.subList(1, input.size());
                    pc += 2;
                }
                case 4 -> {
                    // out
                    var p1 = data[pc + 1];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    output.add(D1);
                    pc += 2;
                }
                case 5 -> {
                    // jnz
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    var D2 = mode1 == 0 ? data[p2] : p2;
                    if (D1 != 0) {
                        pc = D2;
                    } else {
                        pc += 3;
                    }
                }
                case 6 -> {
                    // jz
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    var D2 = mode1 == 0 ? data[p2] : p2;
                    if (D1 == 0) {
                        pc = D2;
                    } else {
                        pc += 3;
                    }
                }
                case 7 -> {
                    // less-than
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var p3 = data[pc + 3];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    var D2 = mode1 == 0 ? data[p2] : p2;
                    if (D1 < D2) {
                        data[p3] = 1;
                    } else {
                        data[p3] = 0;
                    }
                    pc += 4;
                }
                case 8 -> {
                    // equals
                    var p1 = data[pc + 1];
                    var p2 = data[pc + 2];
                    var p3 = data[pc + 3];
                    var D1 = mode0 == 0 ? data[p1] : p1;
                    var D2 = mode1 == 0 ? data[p2] : p2;
                    if (D1 == D2) {
                        data[p3] = 1;
                    } else {
                        data[p3] = 0;
                    }
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

        int[] data = Arrays.stream(fileContents.split("[,\\s]+")).mapToInt(Integer::parseInt).toArray();
        List<Integer> input = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        input.add(1);
        data = runProgram(data, input, output);

        return output;
    }


    @Override
    protected Object part2() {
        int[] data = Arrays.stream(fileContents.split("[,\\s]+")).mapToInt(Integer::parseInt).toArray();
        List<Integer> input = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        input.add(5);
        data = runProgram(data, input, output);

        return output;
    }
}
