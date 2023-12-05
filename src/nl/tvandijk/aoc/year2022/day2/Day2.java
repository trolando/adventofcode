package nl.tvandijk.aoc.year2022.day2;

import nl.tvandijk.aoc.common.Day;

public class Day2 extends Day {
    @Override
    protected Object part1() {
        int score = 0;
        for (var line : lines) {
            if (line.isEmpty()) continue;
            var tokens = line.split("\\s+");
            int tokenA = tokens[0].charAt(0)-'A';
            int tokenB = tokens[1].charAt(0)-'X';
            score += 3*((4+tokenB-tokenA)%3);
            score += tokenB+1;
        }
        return score;
    }

    @Override
    protected Object part2() {
        int score = 0;
        for (var line : lines) {
            if (line.isEmpty()) continue;
            var tokens = line.split("\\s+");
            int tokenA = tokens[0].charAt(0)-'A';
            int tokenB = tokens[1].charAt(0)-'X';
            score += 3*tokenB;
            score += (tokenA+tokenB+2)%3+1;
        }
        return score;
    }
}
