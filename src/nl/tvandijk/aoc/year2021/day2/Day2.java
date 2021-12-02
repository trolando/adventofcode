package nl.tvandijk.aoc.year2021.day2;

import nl.tvandijk.aoc.common.Day;

import java.util.StringTokenizer;

public class Day2 extends Day {

    @Override
    protected void part1(String fileContents) {
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

        System.out.println("Part 1 result: " + (x * depth));
    }

    @Override
    protected void part2(String fileContents) {
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

        System.out.println("Part 2 result: " + (x * depth));
    }

    public static void main(String[] args) {
        run(Day2::new, "example.txt", "input.txt");
    }
}
