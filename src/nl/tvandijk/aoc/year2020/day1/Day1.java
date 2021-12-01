package nl.tvandijk.aoc.year2020.day1;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1 extends AoCCommon {
    @Override
    protected void process(InputStream stream) throws Exception {
        try (var sc = new Scanner(stream)) {
            var d = new ArrayList<Long>();
            while (sc.hasNext()) {
                var c = sc.nextLong();
                d.add(c);
            }
            {
                int count = d.size();
                for (int i = 0; i < count; i++) {
                    for (int j = i+1; j < count; j++) {
                        var x = d.get(i);
                        var y = d.get(j);
                        if ((x + y) == 2020) {
                            System.out.println("Part 1: " + (x * y));
                        }
                    }
                }
            }
            {
                int count = d.size();
                for (int i = 0; i < count; i++) {
                    for (int j = i + 1; j < count; j++) {
                        for (int k = j + 1; k < count; k++) {
                            var x = d.get(i);
                            var y = d.get(j);
                            var z = d.get(k);
                            if ((x + y + z) == 2020) {
                                System.out.println("Part 2: " + x + " " + y + " " + z + " " + (x * y * z));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        run(Day1::new, "example.txt", "input.txt");
    }
}
