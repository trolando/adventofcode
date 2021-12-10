package nl.tvandijk.aoc.year2019.day1;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 extends Day {

    /**
     * For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
     * For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
     * For a mass of 1969, the fuel required is 654.
     * For a mass of 100756, the fuel required is 33583
     */
    private long computeFuel(long mass) {
        return (mass/3)-2;
    }

    @Override
    protected void part1(String fileContents) {
        long total = 0;
        for (var line : fileContents.split("[\r\n]+")) {
            total += computeFuel(Integer.parseInt(line));
        }

        System.out.println("part 1 = " + total);
    }


    @Override
    protected void part2(String fileContents) {

        long total = 0;
        for (var line : fileContents.split("[\r\n]+")) {
            long addedMass = computeFuel(Integer.parseInt(line));
            total += addedMass;

            while (addedMass != 0) {
//                System.out.println("Added mass is " + addedMass);
                addedMass = computeFuel(addedMass);
                if (addedMass < 0) addedMass = 0;
                total += addedMass;
            }
        }

        System.out.println("part 2 = " + total);
    }

    @Override
    protected void process(String fileContents) {
        part1(fileContents);
        part2(fileContents);
    }

    public static void main(String[] args) {
        run(Day1::new, "example.txt", "input.txt");
    }
}
