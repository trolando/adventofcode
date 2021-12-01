package nl.tvandijk.aoc.year2020.day8;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;

public class Day8 extends AoCCommon {
    @Override
    protected void process(InputStream stream) {
        var program = new Program(stream);
        int val = program.runUntilRepeat();
        System.out.printf("Result: %d\n", val);
        program.attemptToFix();
    }

    public static void main(String[] args) {
        run(Day8::new, "example.txt", "input.txt");
    }
}
