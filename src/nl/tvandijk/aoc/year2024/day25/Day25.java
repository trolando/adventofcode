package nl.tvandijk.aoc.year2024.day25;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day25 extends Day {
    @Override
    protected Object part1() {
        // part 1
        Set<List<Integer>> locks = new HashSet<>();
        Set<List<Integer>> keys = new HashSet<>();
        var parts = this.fileContents.split("\n\n");
        for (var part : parts) {
            var L = part.split("\n");
            if (L[0].startsWith("#")) {
                // lock
                var lock = new ArrayList<Integer>();
                for (int i = 0; i < L[0].length(); i++) {
                    for (int j = 0; j < L.length; j++) {
                        if (L[j].charAt(i) != '#') {
                            lock.add(j-1);
                            break;
                        }
                    }
                }
                locks.add(lock);
            } else {
                // key
                var key = new ArrayList<Integer>();
                for (int i = 0; i < L[0].length(); i++) {
                    for (int j = 0; j < L.length; j++) {
                        if (L[j].charAt(i) != '.') {
                            key.add(L.length-j-1);
                            break;
                        }
                    }
                }
                keys.add(key);
            }
        }
        int sum=0;
        for (var lock : locks) {
            for (var key : keys) {
                boolean good = true;
                for (int i = 0; i < lock.size(); i++) {
                    if (lock.get(i) + key.get(i) >= 6) good = false;
                }
                if (good) sum++;
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        return null;
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day25() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
