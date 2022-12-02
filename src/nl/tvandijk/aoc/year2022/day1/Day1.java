package nl.tvandijk.aoc.year2022.day1;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        int sum = 0;
        int maxSum = 0;
        for (var l : lines) {
            if (l.trim().isEmpty()) {
                if (sum > maxSum) maxSum = sum;
                sum = 0;
            }
            else {
                sum += Integer.parseInt(l);
            }
        }
        return maxSum;
    }

    @Override
    protected Object part2() {
        List<Integer> list = new ArrayList<>();
        int sum = 0;
        for (var l : lines) {
            if (l.trim().isEmpty()) {
                list.add(sum);
                sum = 0;
            }
            else {
                sum += Integer.parseInt(l);
            }
        }
        Collections.sort(list);
        sum = list.get(list.size()-1) + list.get(list.size()-2) + list.get(list.size()-3);
        return sum;
    }
}
