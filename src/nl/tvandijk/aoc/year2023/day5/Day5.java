package nl.tvandijk.aoc.year2023.day5;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day5 extends Day {

    @Override
    protected Object part1() {
        // part 1
        ArrayList<Long> seeds = new ArrayList<>();
        var matcher = Pattern.compile("\\d+").matcher(lines[0]);
        while (matcher.find()) {
            seeds.add(Long.parseLong(matcher.group(0)));
        }
        ArrayList<Long> next = new ArrayList<>();
        for (int i = 2; i < lines.length; i++) {
            var l = lines[i].trim();
            if (l.isBlank()) {
                next.addAll(seeds);
                seeds = next;
                next = new ArrayList<>();
            } else if (Character.isDigit(l.charAt(0))) {
                var ll = Arrays.stream(l.split("\\s+")).mapToLong(Long::parseLong).toArray();
                var it = seeds.iterator();
                while (it.hasNext()) {
                    var seed = it.next();
                    if (seed >= ll[1] && seed < (ll[1] + ll[2])) {
                        // matches
                        next.add(seed - ll[1] + ll[0]);
                        it.remove();
                    }
                }
            }
        }
        next.addAll(seeds);
        seeds = next;
        return seeds.stream().mapToLong(Long::longValue).min().orElseThrow();
    }

    @Override
    protected Object part2() {
        // part 2
        ArrayList<Pair<Long,Long>> ranges = new ArrayList<>();
        var matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(lines[0]);
        while (matcher.find()) {
            ranges.add(Pair.of(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2))));
        }
        ArrayList<Pair<Long,Long>> next = new ArrayList<>();
        for (int i = 2; i < lines.length; i++) {
            var l = lines[i].trim();
            if (l.isBlank()) {
                next.addAll(ranges);
                ranges = next;
                next = new ArrayList<>();
            } else if (Character.isDigit(l.charAt(0))) {
                var ll = Arrays.stream(l.split("\\s+")).mapToLong(Long::parseLong).toArray();
                for (int j = 0; j < ranges.size(); j++) {
                    var seed = ranges.get(j);
                    if (seed.a < ll[1]) {
                        if ((seed.a + seed.b) > ll[1]) {
                            long len = ll[1] - seed.a;
                            ranges.set(j, Pair.of(seed.a, len));
                            ranges.add(j + 1, Pair.of(seed.a + len, seed.b - len));
                            j--;
                        }
                    } else {
                        // seed.a >= ll[1]
                        if (seed.a < (ll[1] + ll[2])) {
                            if ((seed.a + seed.b) <= (ll[1] + ll[2])) {
                                // seeds are contained in range
                                next.add(Pair.of(seed.a - ll[1] + ll[0], seed.b));
                                ranges.remove(seed);
                                j--;
                            } else {
                                // split
                                long len = (ll[1] + ll[2]) - seed.a;
                                ranges.set(j, Pair.of(seed.a, len));
                                ranges.add(j + 1, Pair.of(seed.a + len, seed.b - len));
                                j--;
                            }
                        }
                    }
                }
            }
        }
        next.addAll(ranges);
        ranges = next;
        return ranges.stream().mapToLong(s -> s.a).min().orElseThrow();
    }
}
