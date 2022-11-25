package nl.tvandijk.aoc.year2021.day3;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 extends Day {
    @Override
    protected Object part1() {
        var input = Arrays.asList(tokens);

        int gamma = 0;
        int epsilon = 0;

        for (int i = 0; i < input.get(0).length(); i++) {
            gamma *= 2;
            epsilon *= 2;

            if (mostOccurringNumber(input, i) == '1') {
                // there were more ones than zeroes at this position
                gamma++;
            } else {
                epsilon++;
            }
        }

        return gamma*epsilon;
    }

    private char mostOccurringNumber(List<String> binaryNumbers, int position) {
        int count = 0;
        int total = binaryNumbers.size();

        for (var n : binaryNumbers) {
            if (n.charAt(position) == '1') count++;
        }

        if (count >= (total+1)/2) return '1';
        else return '0';
    }

    private String computeOxygen(List<String> strings, int currentPosition) {
        char mostOccurring = mostOccurringNumber(strings, currentPosition);

        var subresult = new ArrayList<String>();

        for (var numbers : strings) {
            if (numbers.charAt(currentPosition) == mostOccurring) subresult.add(numbers);
        }

        if (subresult.size() == 1) return subresult.get(0);
        else return computeOxygen(subresult, currentPosition+1);
    }

    private String computeScrubbing(List<String> strings, int currentPosition) {
        char mostOccurring = mostOccurringNumber(strings, currentPosition);

        var subresult = new ArrayList<String>();

        for (var numbers : strings) {
            if (numbers.charAt(currentPosition) != mostOccurring) subresult.add(numbers);
        }

        if (subresult.size() == 1) return subresult.get(0);
        else return computeScrubbing(subresult, currentPosition+1);
    }

    private int binaryToDecimal(String input) {
        int res = 0;
        for (var ch : input.toCharArray()) {
            res *= 2;
            if (ch == '1') res++;
        }
        return res;
    }

    @Override
    protected Object part2() {
        var input = Arrays.asList(tokens);

        var oxygen = binaryToDecimal(computeOxygen(input, 0));
        var scrubbing = binaryToDecimal(computeScrubbing(input, 0));

        return oxygen * scrubbing;
    }
}
