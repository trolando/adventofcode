package nl.tvandijk.aoc.year2023.day22;

import java.util.*;
import java.util.stream.IntStream;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day22 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
        for (var line : lines) {
            if (line.isBlank()) continue;
            var ends = line.split("~");
            var end0 = Arrays.stream(ends[0].split(",")).mapToInt(Integer::parseInt).toArray();
            var end1 = Arrays.stream(ends[1].split(",")).mapToInt(Integer::parseInt).toArray();
            var pt0 = Point3.of(end0[0], end0[1], end0[2]);
            var pt1 = Point3.of(end1[0], end1[1], end1[2]);
            // sanity check
            if (pt0.x > pt1.x || pt0.y > pt1.y || pt0.z > pt1.z) throw new RuntimeException("Invalid brick: " + line);
            bricks.add(Pair.of(pt0, pt1));
        }
    }

    Pair<Point3, Point3> fall(Pair<Point3, Point3> brick) {
        return Pair.of(brick.a.delta(0, 0, -1), brick.b.delta(0, 0, -1));
    }

    boolean underGround(Pair<Point3, Point3> brick) {
        return brick.a.z < 1;
    }

    private List<Pair<Point3, Point3>> bricks = new ArrayList<>();

    boolean overlaps(Pair<Point3, Point3> brick1, Pair<Point3, Point3> brick2) {
        if (brick1.a.x > brick2.b.x || brick1.b.x < brick2.a.x) return false;
        if (brick1.a.y > brick2.b.y || brick1.b.y < brick2.a.y) return false;
        if (brick1.a.z > brick2.b.z || brick1.b.z < brick2.a.z) return false;
        return true;
    }

    public boolean gravity(boolean onlyTest) {
        boolean dropped = false;
        for (int i = 0; i < bricks.size(); i++) {
            var brick = bricks.get(i);
            if (brick == null) continue;
            var brick2 = fall(brick);
            if (underGround(brick2)) continue;
            boolean valid = true;
            for (int j = 0; j < bricks.size(); j++) {
                if (i == j) continue;
                if (bricks.get(j) == null) continue;
                if (overlaps(brick2, bricks.get(j))) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                if (onlyTest) return true;
                bricks.set(i, brick2);
                dropped = true;
                i--;
            }
        }
        return dropped;
    }

    @Override
    protected Object part1() {
        // part 1
        while (gravity(false)) {
        }
        bricks.sort(Comparator.comparingLong(a -> a.a.z));
        long count = 0;
        for (int i = 0; i < bricks.size(); i++) {
            var cur = new ArrayList<>(bricks);
            bricks.set(i, null);
            if (!gravity(true)) count++;
            bricks = cur;
        }
        return count;
    }

    @Override
    protected Object part2() {
        // part 2
        long count = 0;
        for (int i = 0; i < bricks.size(); i++) {
            var cur = new ArrayList<>(bricks);
            bricks.set(i, null);
            while (gravity(false)) {
            }
            count += IntStream
                    .range(0, bricks.size())
                    .filter(a -> bricks.get(a) != null && !cur.get(a).equals(bricks.get(a)))
                    .count();
            bricks = cur;
        }
        return count;
    }
}
