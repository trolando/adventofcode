package nl.tvandijk.aoc.year2023.day1;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day1 extends Day {
    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (var line : lines) {
            int first = -1;
            int last = -1;
            for (var ch : line.toCharArray()) {
                if (Character.isDigit(ch)) {
                    int v = Integer.parseInt(Character.toString(ch));
                    if (first == -1) first = v;
                    last = v;
                }
            }
            sum += first*10+last;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        int sum=0;
        for (var line : lines) {
            int first = -1;
            int last = 0;
            for (int i = 0; i < line.length(); i++) {
                int digit = -1;
                if (Character.isDigit(line.charAt(i))) digit = Integer.parseInt(line.substring(i, i+1));
                else if (line.startsWith("zero", i)) digit = 0;
                else if (line.startsWith("one", i)) digit = 1;
                else if (line.startsWith("two", i)) digit = 2;
                else if (line.startsWith("three", i)) digit = 3;
                else if (line.startsWith("four", i)) digit = 4;
                else if (line.startsWith("five", i)) digit = 5;
                else if (line.startsWith("six", i)) digit = 6;
                else if (line.startsWith("seven", i)) digit = 7;
                else if (line.startsWith("eight", i)) digit = 8;
                else if (line.startsWith("nine", i)) digit = 9;
                if (digit != -1) {
                    if (first == -1) first = digit;
                    last = digit;
                }
            }
            sum += first*10+last;
        }
        return sum;
    }
}
