package nl.tvandijk.aoc.year2024.day13;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;

public class Day13 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        long sum = 0;
        for (var block : fileContents.split("\n\n")) {
            var lines = block.trim().split("\n");
            var buttonA = Arrays.stream(lines[0]
                            .split(": ")[1]
                            .split(", "))
                    .mapToLong(s -> Long.parseLong(s.substring(2)))
                    .toArray();
            var buttonB = Arrays.stream(lines[1]
                            .split(": ")[1]
                            .split(", "))
                    .mapToLong(s -> Long.parseLong(s.substring(2)))
                    .toArray();
            var prize = Arrays.stream(lines[2]
                            .split(": ")[1]
                            .split(", "))
                    .mapToLong(s -> Long.parseLong(s.substring(2)))
                    .toArray();
            var det = buttonA[0] * buttonB[1] - buttonA[1] * buttonB[0];
            var a = (prize[0] * buttonB[1] - prize[1] * buttonB[0]) / det;
            var b = (buttonA[0] * prize[1] - buttonA[1] * prize[0]) / det;
            if (a*buttonA[0] + b*buttonB[0] == prize[0] && a*buttonA[1] + b*buttonB[1] == prize[1]) {
                sum += 3 * a + b;
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        long sum = 0;
        for (var block : fileContents.split("\n\n")) {
            var lines = block.trim().split("\n");
            var buttonA = Arrays.stream(lines[0]
                            .split(": ")[1]
                            .split(", "))
                    .mapToLong(s -> Long.parseLong(s.substring(2)))
                    .toArray();
            var buttonB = Arrays.stream(lines[1]
                            .split(": ")[1]
                            .split(", "))
                    .mapToLong(s -> Long.parseLong(s.substring(2)))
                    .toArray();
            var prize = Arrays.stream(lines[2]
                            .split(": ")[1]
                            .split(", "))
                    .mapToLong(s -> Long.parseLong(s.substring(2)))
                    .toArray();
            prize[0] += 10000000000000L;
            prize[1] += 10000000000000L;
            var det = buttonA[0] * buttonB[1] - buttonA[1] * buttonB[0];
            var a = (prize[0] * buttonB[1] - prize[1] * buttonB[0]) / det;
            var b = (buttonA[0] * prize[1] - buttonA[1] * prize[0]) / det;
            if (a*buttonA[0] + b*buttonB[0] == prize[0] && a*buttonA[1] + b*buttonB[1] == prize[1]) {
                sum += 3 * a + b;
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }
}
