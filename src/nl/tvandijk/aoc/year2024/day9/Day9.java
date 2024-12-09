package nl.tvandijk.aoc.year2024.day9;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day9 extends Day {
    @Override
    protected Object part1() {
        // part 1
        int file = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < lines[0].length(); i++) {
            var v = Integer.parseInt(""+lines[0].toCharArray()[i]);
            if (i%2 == 0) {
                for (int j = 0; j < v; j++) arr.add(file);
                file++;
            } else {
                for (int j = 0; j < v; j++) arr.add(-1);
            }
        }
        int a = 0;
        while (a != arr.size()) {
            if (arr.get(a) != -1) {
                a++;
            } else {
                var v = arr.removeLast();
                if (v != -1) arr.set(a++, v);
            }
        }
        long checksum = 0;
        for (int i = 0; i < arr.size(); i++) checksum += (long)arr.get(i) * i;
        return checksum;
    }

    @Override
    protected Object part2() {
        // part 2
        int f = 0;
        ArrayList<Pair<Integer, Integer>> arr = new ArrayList<>();
        for (int i = 0; i < lines[0].length(); i++) {
            var v = Integer.parseInt(""+lines[0].toCharArray()[i]);
            if (i%2 == 0) arr.add(Pair.of(v, f++));
            else arr.add(Pair.of(v, -1));
        }
        for (int a = arr.size()-1; a >= 0; a--) {
            var p = arr.get(a);
            if (p.b != -1) {
                for (int i = 0; i < a; i++) {
                    var q = arr.get(i);
                    if (q.b == -1 && q.a >= p.a) {
                        var diff = q.a - p.a;
                        q.a = p.a;
                        q.b = p.b;
                        p.b = -1;
                        if (diff > 0) {
                            if (arr.size() > i + 1 && arr.get(i + 1).b == -1) {
                                arr.get(i + 1).a += diff;
                            } else {
                                arr.add(i + 1, Pair.of(diff, -1));
                                a++;
                            }
                        }
                        break;
                    }
                }
            }
        }
        long checksum = 0;
        long index = 0;
        for (var v : arr) {
            if (v.b != -1) {
                for (int j = 0; j < v.a; j++) {
                    checksum += index++ * v.b;
                }
            } else {
                index += v.a;
            }
        }
        return checksum;
    }
}
