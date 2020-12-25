package nl.tvandijk.aoc.day10;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day10 extends AoCCommon {
    public long[] adapters = null;
    public int count = 0;

    public long connect2() {
        long amount = 1;
        final int[] multiplier = new int[] {1,1,2,4,7};
        long last = adapters[0] + 1;
        int consecutive = last == 2 ? 1 : 0;

        for (int i = 1; i < count; i++) {
            long l = adapters[i];
            if (l == last) {
                consecutive++;
            } else {
                amount *= multiplier[consecutive];
                consecutive = 0;
            }
            last = l+1;
        }
        return amount * multiplier[consecutive];
    }

    public long connect3() {
        // method using dynamic programming
        long counter[] = new long[count];
        counter[0] = 1;
        counter[1] = 1 + (adapters[1] <= 3 ? 1 : 0);
        counter[2] = counter[1] + ((adapters[2]-adapters[0] <= 3) ? counter[0] + (adapters[2] <= 3 ? 1 : 0) : 0);
        for (int i=3; i<count; i++) {
            // try prev
            counter[i] = counter[i-1];
            if ((adapters[i]-adapters[i-2]) <= 3) counter[i] += counter[i-2];
            if ((adapters[i]-adapters[i-3]) <= 3) counter[i] += counter[i-3];
        }
        return counter[count-1];
    }

    @Override
    protected void process(InputStream stream) throws Exception {
        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            adapters = br.lines().mapToLong(Long::valueOf).toArray();
            Arrays.sort(adapters);
            count = adapters.length;
        } catch (IOException e) {
            e.printStackTrace();
        }

        {
            int diff1 = 0;
            int diff3 = 0;
            for (int i = 0; i<count; i++) {
                if (i == 0) {
                    if (adapters[i] == 1) diff1++;
                    if (adapters[i] == 3) diff3++;
                } else {
                    if ((adapters[i] - adapters[i - 1]) == 1) diff1++;
                    if ((adapters[i] - adapters[i - 1]) == 3) diff3++;
                }
            }
            System.out.println("Result of part 1: " + diff1 * (diff3+1));
        }

        System.out.println("Result of part 2: " + connect2());
    }

    public static void main(String[] args) {
        run(Day10::new, "example1.txt", "example2.txt", "input.txt");
    }
}
