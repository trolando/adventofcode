package nl.tvandijk.aoc.year2024.day3;

import nl.tvandijk.aoc.common.Day;

import java.util.regex.Pattern;

public class Day3 extends Day {
    @Override
    protected Object part1() {
        // part 1
        var p = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        long sum  = 0;
        var m = p.matcher(fileContents);
        while (m.find()) {
            sum += Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        var p = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\)");
        boolean yes = true;
        long sum  = 0;
        var m = p.matcher(fileContents);
        while (m.find()) {
            if (m.group().equals("do()")) yes = true;
            else if (m.group().equals("don't()")) yes = false;
            else if (yes) sum += Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
        }
        return sum;
    }
}
