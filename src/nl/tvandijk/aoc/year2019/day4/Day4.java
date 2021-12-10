package nl.tvandijk.aoc.year2019.day4;

import nl.tvandijk.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day4 extends Day {
    /*
        It is a six-digit number.
        The value is within the range given in your puzzle input.
        Two adjacent digits are the same (like 22 in 122345).
        Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
        Other than the range rule, the following are true:

        111111 meets these criteria (double 11, never decreases).
        223450 does not meet these criteria (decreasing pair of digits 50).
        123789 does not meet these criteria (no double).
        How many different passwords within the range given in your puzzle input meet these criteria?

        Your puzzle input is 271973-785961.
     */
    private static void doPart1() {
        int count = 0;

        for (int i=271973; i <= 785961; i++) {
            int[] s = Integer.toString(i).chars().toArray();

            boolean neverDecreases = true;
            boolean hasDouble = false;
            for (int k = 1; k < s.length; k++) {
                if (s[k-1] > s[k]) neverDecreases = false;
                if (s[k-1] == s[k]) hasDouble = true;
            }

            if (neverDecreases && hasDouble) {
//                System.out.println("Found number: " + i);
                count++;
            }
        }
        System.out.println("Part 1 : " + count);
    }

    private static void doPart2() {
        int count = 0;

        for (int i=271973; i <= 785961; i++) {
            int[] s = Integer.toString(i).chars().toArray();

            boolean neverDecreases = true;
            boolean hasDouble = false;
            int doubleNo = -1;
            for (int k = 1; k < s.length; k++) {
                if (s[k-1] > s[k]) neverDecreases = false;
                if (s[k-1] == s[k]) {
                    if (!hasDouble) {
                        if (doubleNo != s[k]) {
                            hasDouble = true;
                            doubleNo = s[k];
                        }
                    } else {
                        if (doubleNo == s[k]) {
                            hasDouble = false;
                        }
                    }
                }
            }

            if (neverDecreases && hasDouble) {
//                System.out.println("Found number: " + i);
                count++;
            }
        }
        System.out.println("Part 1 : " + count);
    }

    @Override
    protected void part1(String fileContents) {
        var numbers = fileContents.split("-");
    }


    @Override
    protected void part2(String fileContents) {
    }

    @Override
    protected void process(String fileContents) {
        part1(fileContents);
        part2(fileContents);
    }

    public static void main(String[] args) {
        doPart1();
        doPart2();
        run(Day4::new, "example.txt", "input.txt");
    }
}
