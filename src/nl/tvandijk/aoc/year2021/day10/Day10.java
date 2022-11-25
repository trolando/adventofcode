package nl.tvandijk.aoc.year2021.day10;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Day10 extends Day {
    /**
     check if line is corrupted or incomplete
     returns 0 if not either
     returns -size if incomplete with how many are still needed
     return positive number for syntax error score
     */
    long checkLine(String line) {
        Deque<Character> chunks = new ArrayDeque<>();

        for (int i = 0; i < line.length(); i++) {
            var ch = line.charAt(i);
            switch (ch) {
                case '(','[', '{', '<' -> {
                    chunks.add(ch);
                }
                case ')' -> {
                    var other = chunks.removeLast();
                    if (other != '(') return 3;
                }
                case ']' -> {
                    var other = chunks.removeLast();
                    if (other != '[') return 57;
                }
                case '}' -> {
                    var other = chunks.removeLast();
                    if (other != '{') return 1197;
                }
                case '>' -> {
                    var other = chunks.removeLast();
                    if (other != '<') return 25137;
                }
                default -> {
                    throw new RuntimeException("unexpected character");
                }
            }
        }

        /*
        ): 1 point.
        ]: 2 points.
        }: 3 points.
        >: 4 points.
         */

        long result = 0;

        while (!chunks.isEmpty()) {
            result *= 5;
            var ch = chunks.removeLast();
            if (ch == '(') result += 1;
            else if (ch == '[') result += 2;
            else if (ch == '{') result += 3;
            else if (ch == '<') result += 4;
            else throw new RuntimeException("unexpected character");
        }

        return -1 * result;
    }

    @Override
    protected Object part1() {
        var result = Arrays.stream(lines).mapToLong(this::checkLine).filter(x -> x>0).sum();
        return result;
    }

    @Override
    protected Object part2() {
        var result = Arrays.stream(lines).mapToLong(this::checkLine).filter(x -> x<0).sorted().toArray();
        var median = -result[result.length/2];
        return median;
    }
}
