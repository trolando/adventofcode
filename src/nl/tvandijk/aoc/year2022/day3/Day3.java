package nl.tvandijk.aoc.year2022.day3;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day3 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
    }

    private int conv(char c) {
        if (Character.isUpperCase(c)) {
            return c-'A'+27;
        } else {
            return c-'a'+1;
        }
    }

    @Override
    protected Object part1() {
        List<Integer> both = new ArrayList<>();
        for (var l : lines) {
            int len = l.length();
            Set<Integer> left = new HashSet<>();
            for (int i = 0; i < len/2; i++) {
                left.add(conv(l.charAt(i)));
            }
            Set<Integer> right = new HashSet<>();
            for (int i = len/2; i < len; i++) {
                right.add(conv(l.charAt(i)));
            }
            right.retainAll(left);
            both.addAll(right);
        }
        return both.stream().reduce(0, Integer::sum);
    }

    @Override
    protected Object part2() {
        Set<Integer> shared = new HashSet<>();
        int count = 0, sum = 0;
        for (var l : lines) {
            int len = l.length();
            List<Integer> sack = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                sack.add(conv(l.charAt(i)));
            }
            count++;
            if (count == 1) shared.addAll(sack);
            else shared.retainAll(sack);
            if (count == 3) {
                sum += shared.stream().reduce(0, Integer::sum);
                shared.clear();
                count = 0;
            }
        }
        return sum;
    }
}
