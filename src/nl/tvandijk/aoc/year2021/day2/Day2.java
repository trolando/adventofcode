package nl.tvandijk.aoc.year2021.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.StringTokenizer;

public class Day2 extends Day {
    @Override
    protected void processInput(String fileContents) {
    }

    @Override
    protected Integer part1() {
        int x = 0;
        int depth = 0;

        StringTokenizer st = new StringTokenizer(fileContents);
        while (st.hasMoreTokens()) {
            String command = st.nextToken();
            int amount = Integer.parseInt(st.nextToken());
            switch (command) {
                case "down":
                    depth += amount;
                    break;
                case "up":
                    depth -= amount;
                    break;
                case "forward":
                    x += amount;
                    break;
            }
        }

        return (x * depth);
    }

    @Override
    protected Integer part2() {
        int x = 0;
        int depth = 0;
        int aim = 0;

        StringTokenizer st = new StringTokenizer(fileContents);
        while (st.hasMoreTokens()) {
            String command = st.nextToken();
            int amount = Integer.parseInt(st.nextToken());

            switch (command) {
                case "down":
                    aim += amount;
                    break;
                case "up":
                    aim -= amount;
                    break;
                case "forward":
                    x += amount;
                    depth += aim * amount;
                    break;
            }
        }

        return (x * depth);
    }
}
