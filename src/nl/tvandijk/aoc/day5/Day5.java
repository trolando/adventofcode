package nl.tvandijk.aoc.day5;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day5 extends AoCCommon {

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

    @Override
    public void process(InputStream stream) {
        var no1 = decode("FBFBBFFRLR");
        var no2 = decode("BFFFBBFRRR");
        var no3 = decode("FFFBBBFRRR");
        var no4 = decode("BBFFBBFRLL");

        Set<Integer> seats = new HashSet<>();

        long biggest = 0;
        try (var sc = new Scanner(new InputStreamReader(stream))) {
            while (sc.hasNext()) {
                var dec = decode(sc.next());
                if (dec > biggest) biggest = dec;
                seats.add((int) dec);
            }
            System.out.printf("Highest seat ID on a boarding pass: %d\n", biggest);

            for (int i=1; i<biggest; i++) {
                if (seats.contains(i-1) && !seats.contains(i) && seats.contains(i+1)) {
                    System.out.printf("Missing ID: %d\n", i);
                }
            }
        }
    }

    public static void main(String[] args) {
        run(Day5::new, "input.txt");
    }
}
