package nl.tvandijk.aoc.year2022.day25;

import nl.tvandijk.aoc.common.Day;

public class Day25 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        long sum = 0;
        for (var line : lines) {
            long l = 0;
            for (var c : line.toCharArray()) {
                l = 5 * l + "=-0123".indexOf(c) - 2;
            }
            sum += l;
        }
        String ans = "";
        while (sum != 0) {
            ans = "012=-".charAt((int) (sum % 5)) + ans;
            sum = (sum + 2) / 5;
        }
        return ans;
    }

    @Override
    protected Object part2() {
        // part 2
        return null;
    }
}
