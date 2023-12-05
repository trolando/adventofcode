package nl.tvandijk.aoc.year2023.day5;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;
import nl.tvandijk.aoc.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day5 extends Day {
    private List<Long> applyP1(List<String> map, List<Long> seeds) {
        var filters = map.stream().skip(1).map(s -> Arrays.stream(s.split("\\s+")).mapToLong(Long::parseLong).toArray()).toList();
        List<Long> next = new ArrayList<>();
        for (var s : seeds) {
            boolean done = false;
            for (var l : filters) {
                if (s >= l[1] && s < (l[1] + l[2])) {
                    next.add(s - l[1] + l[0]);
                    done = true;
                    break;
                }
            }
            if (!done) {
                next.add(s);
            }
        }
        return next;
    }

    private List<Pair<Long,Long>> applyP2(List<String> map, List<Pair<Long,Long>> ranges) {
        var filters = map.stream().skip(1).map(s -> Arrays.stream(s.split("\\s+")).mapToLong(Long::parseLong).toArray()).toList();
        var todo = new ArrayList<>(ranges);
        List<Pair<Long,Long>> next = new ArrayList<>();
        for (int j = 0; j < todo.size(); j++) {
            var range = todo.get(j);
            boolean done = false;
            for (var ll : filters) {
                if (range.a < ll[1]) {
                    if ((range.a + range.b) > ll[1]) {
                        // split
                        long len = ll[1] - range.a;
                        todo.add(Pair.of(range.a, len));
                        todo.add(Pair.of(range.a + len, range.b - len));
                        done = true;
                    }
                } else {
                    // seed.a >= ll[1]
                    if (range.a < (ll[1] + ll[2])) {
                        if ((range.a + range.b) <= (ll[1] + ll[2])) {
                            // seeds are contained in range
                            next.add(Pair.of(range.a - ll[1] + ll[0], range.b));
                            done = true;
                        } else {
                            // split
                            long len = (ll[1] + ll[2]) - range.a;
                            todo.add(Pair.of(range.a, len));
                            todo.add(Pair.of(range.a + len, range.b - len));
                            done = true;
                        }
                    }
                }
                if (done) break;
            }
            if (!done) {
                next.add(range);
            }
        }
        return next;
    }

    @Override
    protected Object part1() {
        // part 1
        var l0seeds = lines[0].split(":")[1].trim().split("\\s+");
        List<Long> seeds = new ArrayList<>(Arrays.stream(l0seeds).map(Long::parseLong).toList());

        var lx = Util.splitArray(lines, String::isBlank);
        for (int i = 1; i < lx.size(); i++) {
            seeds = applyP1(lx.get(i), seeds);
        }

        return seeds.stream().mapToLong(Long::longValue).min().orElseThrow();
    }

    @Override
    protected Object part2() {
        // part 2
        List<Pair<Long,Long>> ranges = new ArrayList<>();
        var matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(lines[0]);
        while (matcher.find()) {
            ranges.add(Pair.of(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2))));
        }

        var lx = Util.splitArray(lines, String::isBlank);
        for (int i = 1; i < lx.size(); i++) {
            ranges = applyP2(lx.get(i), ranges);
        }

        return ranges.stream().mapToLong(s -> s.a).min().orElseThrow();
    }
}
