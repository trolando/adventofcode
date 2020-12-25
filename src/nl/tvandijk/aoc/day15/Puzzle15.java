package nl.tvandijk.aoc.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Pattern;

public class Puzzle15 {
    static long partA(int[] starting, int halt) {

        ConcurrentSkipListMap<Integer, Integer> numbers = new ConcurrentSkipListMap<>();

        int lastNumber = 0;
        int lastAge = 0;
        int t = 0;

        for (; t<starting.length; t++) {
            lastNumber = starting[t];

            var L = numbers.get(lastNumber);
            lastAge = L == null ? 0 : t-L;

            numbers.put(lastNumber, t);
        }

        // say next

        while (true) {
            if (t == (halt-1)) {
                System.out.printf("\n");
                return lastAge;
            }
            lastNumber = lastAge;
            var L = numbers.get(lastNumber);
            lastAge = L == null ? 0 : t-L;
            numbers.put(lastNumber, t);
            //System.out.printf(", " + lastNumber);
            t++;
        }
    }


    public static void go(int[] numbers) {
        long before = System.nanoTime();
        var res = partA(numbers, 30000000);
        long after = System.nanoTime();

        System.out.printf("time: %f us, rseult = %d\n", ((double)(after-before))/1000, res);
    }


    public static void main(String[] args) {
        go(new int[] {0,3,6});
        go(new int[] {1,3,2});
        go(new int[] {2,1,3});
        go(new int[] {1,2,3});
        go(new int[] {2,3,1});
        go(new int[] {3,2,1});
        go(new int[] {3,1,2});
        go(new int[] {16,12,1,0,15,7,11});
    }
}
