package nl.tvandijk.aoc.year2022.day12;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day12 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);

        width = lines[0].length();
        height = (int) Arrays.stream(lines).filter(s -> !s.isEmpty()).count();
        map = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            var ch = lines[i / width].charAt(i % width);
            map[i] = switch (ch) {
                case 'S' -> 0;
                case 'E' -> 25;
                default -> ch-'a';
            };
            if (ch == 'S') start = Point.of(i % width, i / width);
            if (ch == 'E') end = Point.of(i % width, i / width);
        }
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                System.out.printf("%2d ", map[x+y*width]);
//            }
//            System.out.println();
//        }
    }

    private int width;
    private int height;
    private int[] map;
    private Point start;
    private Point end;

    @Override
    protected Object part1() {
        // part 1
        int[] dist = new int[width * height];
        List<Point> q = new ArrayList<>();
        q.add(start);
        while (!q.isEmpty()) {
            Point cur = q.remove(0);
            int d = dist[cur.x+cur.y*width];
            for (var p : cur.adjacent(false)) {
                if (!p.inside(0, 0, width, height)) continue;
                if (dist[p.x+p.y*width] != 0) continue;
                if (map[p.x+p.y*width] - map[cur.x+cur.y*width] > 1) continue;
                dist[p.x+p.y*width] = d+1;
                q.add(p);
            }
        }
        return dist[end.x+end.y*width];
    }

    @Override
    protected Object part2() {
        // part 2
        int[] dist = new int[width * height];
        List<Point> q = new ArrayList<>();
        q.add(end);
        while (!q.isEmpty()) {
            Point cur = q.remove(0);
            int d = dist[cur.x + cur.y * width];
            for (var p : cur.adjacent(false)) {
                if (!p.inside(0, 0, width, height)) continue;
                if (dist[p.x + p.y * width] != 0) continue;
                if (map[cur.x + cur.y * width] - map[p.x + p.y * width] > 1) continue;
                dist[p.x + p.y * width] = d + 1;
                q.add(p);
            }
        }
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < width * height; i++) {
            if (map[i] == 0 && dist[i]!=0 && dist[i] < best) best = dist[i];
        }
        return best;
    }
}
