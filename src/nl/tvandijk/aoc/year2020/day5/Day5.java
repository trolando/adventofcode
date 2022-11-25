package nl.tvandijk.aoc.year2020.day5;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day5 extends Day {

    private static long decode(String input) {
        long answer = 0;
        for (int i=0; i<input.length(); i++) {
            var ch = input.charAt(i);
            if (ch == 'F' || ch == 'L') {
                answer <<= 1;
            } else if (ch == 'B' || ch == 'R') {
                answer <<= 1;
                answer++;
            } else {
                System.out.println("ERROR DECODING");
            }
        }
        return answer;
    }

    public void test() {
        var no1 = decode("FBFBBFFRLR");
        var no2 = decode("BFFFBBFRRR");
        var no3 = decode("FFFBBBFRRR");
        var no4 = decode("BBFFBBFRLL");
    }

    @Override
    protected Object part1() throws Exception {
        long biggest = 0;
        for (var token : tokens) {
            var dec = decode(token);
            if (dec > biggest) biggest = dec;
        }
        return biggest;
    }

    @Override
    protected Object part2() throws Exception {
        Set<Integer> seats = new HashSet<>();

        long biggest = 0;
        for (var token : tokens) {
            var dec = decode(token);
            if (dec > biggest) biggest = dec;
            seats.add((int) dec);
        }

        for (int i=1; i<biggest; i++) {
            if (seats.contains(i-1) && !seats.contains(i) && seats.contains(i+1)) {
                return i;
            }
        }

        return null;
    }
}
