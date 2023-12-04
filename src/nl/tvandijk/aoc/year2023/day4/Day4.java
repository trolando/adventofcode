package nl.tvandijk.aoc.year2023.day4;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day4 extends Day {
    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (var line : lines) {
            var r = line.split(":")[1].split("\\|");
            var winning = Arrays.stream(r[0].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toSet());
            var have = Arrays.stream(r[1].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toSet());
            var count = winning.stream().filter(have::contains).count();
            if (count > 0) sum += 1 << (count-1);
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        List<Long> arr = new ArrayList<>();
        for (var line : lines) {
            var r = line.split(":")[1].split("\\|");
            var winning = Arrays.stream(r[0].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toSet());
            var have = Arrays.stream(r[1].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toSet());
            var count = winning.stream().filter(have::contains).count();
            arr.add(count);
        }
        int[] cards = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            cards[i]++;
            for (int n = 0; n < arr.get(i); n++) cards[i+1+n] += cards[i];
        }
        return Arrays.stream(cards).sum();
    }
}
