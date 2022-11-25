package nl.tvandijk.aoc.year2020.day15;

import nl.tvandijk.aoc.common.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Day15 extends Day {
    private static long speak(int[] starting, int halt) {
        HashMap<Integer, Integer> numbers = new HashMap<>();

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
                return lastAge;
            }
            lastNumber = lastAge;
            var L = numbers.get(lastNumber);
            lastAge = L == null ? 0 : t-L;
            numbers.put(lastNumber, t);
            t++;
        }
    }

    @Override
    protected Object part1() throws Exception {
        var numbers = Arrays.stream(lines[0].trim().split(",")).mapToInt(Integer::parseInt).toArray();
        return speak(numbers, 2020);
    }

    @Override
    protected Object part2() throws Exception {
        var numbers = Arrays.stream(lines[0].trim().split(",")).mapToInt(Integer::parseInt).toArray();
        return speak(numbers, 30000000);
    }

    public static void go(int[] numbers) {
        long before = System.nanoTime();
        var res = speak(numbers, 30000000);
        long after = System.nanoTime();

        System.out.printf("time: %f us, rseult = %d\n", ((double)(after-before))/1000, res);
    }


    public static void test() {
        go(new int[] {0,3,6});
        go(new int[] {1,3,2});
        go(new int[] {2,1,3});
        go(new int[] {1,2,3});
        go(new int[] {2,3,1});
        go(new int[] {3,2,1});
        go(new int[] {3,1,2});
//        go(new int[] {16,12,1,0,15,7,11});
    }
}
