package nl.tvandijk.aoc.year2019.day1;

import nl.tvandijk.aoc.common.Day;

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
    protected Object part1() {
        long total = 0;
        for (var line : lines) {
            total += computeFuel(Integer.parseInt(line));
        }
        return total;
    }


    @Override
    protected Object part2() {
        long total = 0;
        for (var line : lines) {
            long addedMass = computeFuel(Integer.parseInt(line));
            total += addedMass;

            while (addedMass != 0) {
                addedMass = computeFuel(addedMass);
                if (addedMass < 0) addedMass = 0;
                total += addedMass;
            }
        }
        return total;
    }
}
