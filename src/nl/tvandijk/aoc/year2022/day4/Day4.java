package nl.tvandijk.aoc.year2022.day4;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;

public class Day4 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        return Arrays.stream(lines).map(l -> Arrays.stream(l.split("[,-]")).mapToInt(Integer::parseInt).toArray())
                .filter(p -> (p[0] >= p[2] && p[1] <= p[3]) || (p[2] >= p[0] && p[3] <= p[1])).count();
    }

    @Override
    protected Object part2() {
        return Arrays.stream(lines).map(l -> Arrays.stream(l.split("[,-]")).mapToInt(Integer::parseInt).toArray())
                .filter(p -> p[0] <= p[2] ? p[1] >= p[2] : p[0] <= p[3]).count();
    }
}
