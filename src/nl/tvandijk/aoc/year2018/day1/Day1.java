package nl.tvandijk.aoc.year2018.day1;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day1 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        int freq = 0;
        for (var l : lines) {
            // process each line
            if (l.isBlank()) continue;
            if (l.startsWith("-")) freq -= Integer.parseInt(l.substring(1));
            else freq += Integer.parseInt(l.substring(1));
        }
        return freq;
    }

    @Override
    protected Object part2() {
        // part 2
        Set<Integer> seen = new HashSet<>();
        int freq = 0;
        while (true) {
            for (var l : lines) {
                // process each line
                if (l.isBlank()) continue;
                if (l.startsWith("-")) freq -= Integer.parseInt(l.substring(1));
                else if (l.startsWith("+")) freq += Integer.parseInt(l.substring(1));
                else throw new RuntimeException("Invalid input: " + l);
                if (seen.contains(freq)) return freq;
                seen.add(freq);
            }
        }
    }
}
