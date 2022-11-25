package nl.tvandijk.aoc.year2021.day7;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 extends Day {
    @Override
    protected Object part1() {
        var numbers = Arrays.stream(fileContents.trim().split(",")).map(Integer::valueOf).sorted().collect(Collectors.toList());
        int result = 0;

        while (numbers.size() > 1) {
            result += numbers.get(numbers.size()-1) - numbers.get(0);
            numbers = numbers.subList(1, numbers.size()-1);
        }

        return result;

        // 16,1,2,0,4,2,7,1,2,14
        // 0,1,1,2,2,2,4,7,14,16
        //   1,1,2,2,2,4,7,14 + 16
        //     1,2,2,2,4,7 + 29
        //       2,2,2,4   + 35
        //         2,2     + 37
    }

    private long cost(long diff) {
        return diff*(diff+1) / 2;
    }

    private long fuelCost(List<Integer> numbers, int location) {
        long result = 0;
        for (var number : numbers) {
            int diff = number > location ? number-location : location-number;

            result += cost(diff);
        }
        return result;
    }

    @Override
    protected Object part2() {
        var numbers = Arrays.stream(fileContents.trim().split(",")).map(Integer::valueOf).sorted().collect(Collectors.toList());
        var lowest = numbers.get(0);
        var highest = numbers.get(numbers.size()-1);

        var bestFuel = Long.MAX_VALUE;

//        var mean = numbers.stream().reduce(Integer::sum).get()/numbers.size();

        for (int i = lowest; i <= highest; i++) {
            var theCost = fuelCost(numbers, i);
            bestFuel = Math.min(bestFuel, theCost);
        }

        return bestFuel;
    }
}
