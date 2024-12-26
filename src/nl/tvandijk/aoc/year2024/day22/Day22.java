package nl.tvandijk.aoc.year2024.day22;

import com.carrotsearch.hppc.IntHashSet;
import com.carrotsearch.hppc.IntSet;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import nl.tvandijk.aoc.common.Day;

public class Day22 extends Day {
    @Override
    protected Object part1() {
        // part 1
        long sum = 0L;
        for (var no : digits(fileContents)) {
            long secret = no;
            for (int i = 0; i < 2000; i++) {
                secret = (secret ^ (secret << 6)) & 0xffffff;
                secret = (secret ^ (secret >> 5)) & 0xffffff;
                secret = (secret ^ (secret << 11)) & 0xffffff;
            }
            sum += secret;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        var map = new Int2IntOpenHashMap();
        IntSet seen = new IntHashSet();
        for (var secret : digits(fileContents)) {
            int window = 0;
            int last = (int) secret%10;
            seen.clear();
            for (int i = 0; i < 2000; i++) {
                secret = (secret ^ (secret << 6)) & 0xffffff;
                secret = (secret ^ (secret >> 5)) & 0xffffff;
                secret = (secret ^ (secret << 11)) & 0xffffff;
                int price = (int) secret%10;
                window <<= 8;
                window |= (9+price-last);
                last = price;
                if (i >= 3 && seen.add(window)) map.put(window, map.getOrDefault(window, 0) + price);
            }
        }
        return map.values().intStream().max().orElseThrow();
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day22() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
