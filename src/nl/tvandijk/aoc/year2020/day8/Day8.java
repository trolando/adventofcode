package nl.tvandijk.aoc.year2020.day8;

import nl.tvandijk.aoc.common.Day;

public class Day8 extends Day {
    @Override
    protected Object part1() throws Exception {
        var program = new Program(lines);
        return program.runUntilRepeat();
    }

    @Override
    protected Object part2() throws Exception {
        var program = new Program(lines);
        return program.attemptToFix();
    }
}
