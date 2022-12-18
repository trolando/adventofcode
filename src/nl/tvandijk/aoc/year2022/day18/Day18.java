package nl.tvandijk.aoc.year2022.day18;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point3;

public class Day18 extends Day {
    Set<Point3> pts = new HashSet<>();

    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        for (var line : lines) {
            var p = line.split(",");
            var pp = Point3.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
            pts.add(pp);
        }
    }

    @Override
    protected Object part1() {
        // part 1
        int i = 0;
        for (var p : pts) {
            for (var pp : p.adjacent(false)) {
                if (!pts.contains(pp)) i++;
            }
        }
        return i;
    }

    @Override
    protected Object part2() {
        // part 2
        var minx = pts.stream().mapToLong(p -> p.x).min().orElse(-1)-1;
        var maxx = pts.stream().mapToLong(p -> p.x).max().orElse(-1)+1;
        var miny = pts.stream().mapToLong(p -> p.y).min().orElse(-1)-1;
        var maxy = pts.stream().mapToLong(p -> p.y).max().orElse(-1)+1;
        var minz = pts.stream().mapToLong(p -> p.z).min().orElse(-1)-1;
        var maxz = pts.stream().mapToLong(p -> p.z).max().orElse(-1)+1;
        Set<Point3> inside = new HashSet<>();
        for (long x = minx; x <= maxx; x++) {
            for (long y = miny; y <= maxy; y++) {
                for (long z = minz; z <= maxz; z++) {
                    var pp = Point3.of(x,y,z);
                    if (!pts.contains(pp)) inside.add(pp);
                }
            }
        }
        ArrayDeque<Point3> q = new ArrayDeque<>();
        q.add(Point3.of(minx,miny,minz));
        while (!q.isEmpty()) {
            var p = q.remove();
            for (var pp : p.adjacent(false)) {
                if (inside.contains(pp)) {
                    inside.remove(pp);
                    q.add(pp);
                }
            }
        }
        int i = 0;
        for (var p : pts) {
            for (var pp : p.adjacent(false)) {
                if (!inside.contains(pp) && !pts.contains(pp)) i++;
            }
        }
        return i;
    }
}
