package nl.tvandijk.aoc.year2024.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;

public class Day2 extends Day {
    boolean isSafeUp(long[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i-1] >= numbers[i] || numbers[i] - numbers[i-1] > 3) return false;
        }
        return true;
    }

    boolean isSafeDown(long[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i-1] <= numbers[i] || numbers[i] - numbers[i-1] < -3) return false;
        }
        return true;
    }

    long[] remove(long[] numbers, int idx) {
        if (idx == -1) return numbers;
        var res = new long[numbers.length-1];
        int j = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (i != idx) res[j++] = numbers[i];
        }
        return res;
    }

    @Override
    protected Object part1() {
        // part 1
        int sum=0;
        for (var line : lines) {
            var numbers = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            if (isSafeUp(numbers) || isSafeDown(numbers)) sum++;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        int sum=0;
        for (var line : lines) {
            var numbers = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            for (int i = 0; i < numbers.length; i++) {
                var nn = remove(numbers, i);
                if (isSafeUp(nn) || isSafeDown(nn)) {
                    sum++;
                    break;
                }
            }
        }
        return sum;
    }
}
