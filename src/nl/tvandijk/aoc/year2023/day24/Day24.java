package nl.tvandijk.aoc.year2023.day24;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day24 extends Day {
    @Override
    protected Object part1() {
        // part 1
        List<Pair<Point3, Point3>> hailstones = new ArrayList<>();
        for (var line : lines) {
            var parts = Arrays.stream(line.split("[ @,]+")).mapToLong(Long::parseLong).toArray();
            hailstones.add(Pair.of(Point3.of(parts[0], parts[1], parts[2]), Point3.of(parts[3], parts[4], parts[5])));
        }
        long count = 0L;
        for (var pair : Util.getPairs(hailstones)) {
            var loc1 = pair.a.a;
            var speed1 = pair.a.b;
            var loc2 = pair.b.a;
            var speed2 = pair.b.b;
            double A = loc1.x;
            double B = speed1.x;
            double C = loc1.y;
            double D = speed1.y;
            double E = loc2.x;
            double F = speed2.x;
            double G = loc2.y;
            double H = speed2.y;
            var xcross = ((F * G - E * H) * B - (B * C - A * D) * F) / (D * F - B * H);
            var t1 = (xcross - A) / B;
            var t2 = (xcross - E) / F;
            var ycross = C + D * t1;
            if (xcross >= 200000000000000L && xcross <= 400000000000000L && ycross >= 200000000000000L && ycross <= 400000000000000L && t1 >= 0 && t2 >= 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected Object part2() {
        // part 2
        return null;
    }

    /**
     * Construct a Day for the given input files
     *
     * @param inputs the input files, typically example.txt, input.txt
     */
    public Day24() {
        super("input.txt");
    }
}
