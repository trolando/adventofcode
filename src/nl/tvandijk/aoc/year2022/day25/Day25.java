package nl.tvandijk.aoc.year2022.day25;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

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
                l*=5;
                if (c == '2') l += 2;
                if (c == '1') l += 1;
                if (c == '0') l += 0;
                if (c == '-') l -= 1;
                if (c == '=') l -= 2;
            }
            System.out.printf("%s = %d%n", line, l);
            sum+=l;
        }
        System.out.println("sum = " + sum);
        String ans = "";
        while (sum != 0) {
            var s = sum%5;
            if (s == 0) ans = "0"+ans;
            if (s == 1) {
                ans = "1"+ans;
                sum--;
            }
            if (s == 2) {
                ans = "2"+ans;
                sum -= 2;
            }
            if (s == 3) {
                ans = "="+ans;
                sum += 2;
            }
            if (s == 4) {
                ans = "-"+ans;
                sum += 1;
            }
            sum /= 5;
        }
        return ans;
    }

    @Override
    protected Object part2() {
        // part 2
        return null;
    }
}
