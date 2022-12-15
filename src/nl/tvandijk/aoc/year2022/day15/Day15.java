package nl.tvandijk.aoc.year2022.day15;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;
import nl.tvandijk.aoc.util.Point;

public class Day15 extends Day {
    static class Sensor {
        Point location;
        int distance;

        public Sensor(Point location, Point beacon) {
            this.location = location;
            distance = Math.abs(beacon.y-location.y) + Math.abs(beacon.x-location.x);
        }

        public Pair<Integer, Integer> coverage(int y) {
            var d = distance - Math.abs(y-location.y);
            if (d < 0) return null;
            int from = location.x - d;
            int to = location.x + d;
            return Pair.of(from, to);
        }
    }

    static class LineCoverage {
        List<Pair<Integer,Integer>> parts = new ArrayList<>();

        void add(int from, int to) {
            parts.add(Pair.of(from, to));
            parts.sort(Comparator.comparingInt(a -> a.a));
            int i=1;
            while (true) {
                if (i >= parts.size()) break;
                var pi = parts.get(i-1);
                var pj = parts.get(i);

                if (Math.max(pi.a, pj.a) <= Math.min(pi.b, pj.b)) {
                    // merge
                    pi.a = Math.min(pi.a, pj.a);
                    pi.b = Math.max(pi.b, pj.b);
                    parts.remove(i);
                } else {
                    i++;
                }
            }
        }

        public int size() {
            return parts.stream().mapToInt(p -> 1+p.b-p.a).reduce(0, Integer::sum);
        }

        public boolean covers(int x) {
            for (var p : parts) {
                if (x >= p.a && x <= p.b) return true;
            }
            return false;
        }
    }

    List<Sensor> sensors = new ArrayList<>();
    Set<Point> beacons = new HashSet<>();

    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        for (var line : lines) {
            var s = line.split("[=,:]");
            int sx = Integer.parseInt(s[1]);
            int sy = Integer.parseInt(s[3]);
            int bx = Integer.parseInt(s[5]);
            int by = Integer.parseInt(s[7]);
            var sensor = new Sensor(Point.of(sx, sy), Point.of(bx, by));
            sensors.add(sensor);
            beacons.add(Point.of(bx, by));
        }
    }

    @Override
    protected Object part1() {
        // part 1
        // coverage for line 10
        var lc = new LineCoverage();
        for (var sensor : sensors) {
            var r = sensor.coverage(2000000);
            if (r != null) lc.add(r.a, r.b);
        }
        int b = 0;
        for (var beacon : beacons) {
            if (beacon.y == 2000000 && lc.covers(beacon.x)) b++;
        }
        return lc.size() - b;
    }

    @Override
    protected Object part2() {
        // part 2
        for (int y = 0; y < 4000000; y++) {
            var lc = new LineCoverage();
            for (var sensor : sensors) {
                var r = sensor.coverage(y);
                if (r != null) lc.add(r.a, r.b);
            }
            for (int x = 0; x < 4000000; x++) {
                if (lc.covers(x)) continue;
                return x*4000000L+y;
            }
        }
        return null;
    }
}
