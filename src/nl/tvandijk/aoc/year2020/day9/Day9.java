package nl.tvandijk.aoc.year2020.day9;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 extends Day {
    List<Long> numbers = new ArrayList<>();

    public long findWeakness(long number) {
        int i=0, j=0;
        long sum = numbers.get(i);

        while (true) {
            if (sum < number) {
                j++;
                sum += numbers.get(j);
            } else if (sum == number) {
                var sl = numbers.subList(i, j+1);
                var min = sl.stream().mapToLong(x -> x).min().getAsLong();
                var max = sl.stream().mapToLong(x -> x).max().getAsLong();
                return min+max;
            } else if (sum > number) {
                sum -= numbers.get(i);
                i++;
            }
        }
    }

    public boolean isValid(long number) {
        if (numbers.size() < 25) return true;

        // check if in pair
        for (int i=0; i<25; i++) {
            for (int j = i+1; j < 25; j++) {
                long x = numbers.get(numbers.size()-1-i);
                long y = numbers.get(numbers.size()-1-j);

                if ((x+y) == number) return true;
            }
        }

        return false;
    }

    @Override
    protected Object part1() throws Exception {
        var longs = Arrays.stream(lines).mapToLong(Long::valueOf).toArray();
        for (var number : longs) {
            if (isValid(number)) {
                numbers.add(number);
            } else {
                return number;
            }
        }
        return null;
    }

    @Override
    protected Object part2() throws Exception {
        var longs = Arrays.stream(lines).mapToLong(Long::valueOf).toArray();
        for (var number : longs) {
            if (isValid(number)) {
                numbers.add(number);
            } else {
                return findWeakness(number);
            }
        }
        return null;
    }

    @Override
    protected boolean resetForPartTwo() {
        return true;
    }
}
