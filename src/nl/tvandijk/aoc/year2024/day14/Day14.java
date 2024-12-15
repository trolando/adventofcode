package nl.tvandijk.aoc.year2024.day14;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;
import scala.concurrent.impl.FutureConvertersImpl;

import javax.imageio.ImageIO;

public class Day14 extends Day {
    @Override
    protected Object part1() {
        // part 1
        int W = 101;
        int H = 103;
        var sums = new int[4];
        for (var line : lines) {
            var ns = numbers(line);
            var origin = Point.of(ns[0], ns[1]);
            var delta = Point.of(ns[2], ns[3]);
            // location after 100 steps
            var x = (origin.x + 100 * delta.x) % W;
            var y = (origin.y + 100 * delta.y) % H;
            if (x < 0) x += W;
            if (y < 0) y += H;
            if (x != (W/2) && y != (H/2)) {
                sums[(int) (2*(y/(H/2+1))+(x/(W/2+1)))]++;
            }
        }
        return Arrays.stream(sums).reduce(1, (x,y) -> x * y);
    }
    
    public int countLargestArea(Collection<Point> points, boolean diagonal) {
        var uf = new UnionFind<Point>();
        for (var point : points) {
            for (var pp : point.adjacent(diagonal)) {
                if (points.contains(pp)) uf.union(point, pp);
            }
        }
        return uf.getLargestSet().size();
    }

    @Override
    protected Object part2() {
        // part 2
        int W = 101;
        int H = 103;
        Map<Point, List<Point>> points = new HashMap<>();
        for (var line : lines) {
            var ns = numbers(line);
            var origin = Point.of(ns[0], ns[1]);
            var delta = Point.of(ns[2], ns[3]);
            points.putIfAbsent(origin, new ArrayList<>());
            points.get(origin).add(delta);
        }
        for (int i = 0; i <= W*H; i++) {
            var largest = countLargestArea(points.keySet(), false);
            if (largest > 30) {
                exportImage("step " + i + ".png", W, H, points);
                printImage("Step " + i + ":", W, H, points);
                return i;
            }
            var newMap = new HashMap<Point, List<Point>>();
            for (var point : points.keySet()) {
                for (var velo : points.get(point)) {
                    var x = (point.x + velo.x) % W;
                    if (x < 0) x += W;
                    var y = (point.y + velo.y) % H;
                    if (y < 0) y += H;
                    newMap.putIfAbsent(Point.of(x, y), new ArrayList<>());
                    newMap.get(Point.of(x, y)).add(velo);
                }
            }
            points = newMap;
        }
        return 0;
    }

    private static void printImage(String header, int W, int H, Map<Point, List<Point>> points) {
        System.out.println(header);
        for (int x = 0; x < W; x++) System.out.print("=");
        System.out.println();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                var v = points.getOrDefault(Point.of(x, y), List.of()).size();
                if (v == 0) System.out.print(" ");
                else System.out.print("*");
            }
            System.out.println();
        }
        for (int x = 0; x < W; x++) System.out.print("=");
        System.out.println();
        System.out.println();
    }

    private static void exportImage(String filename, int W, int H, Map<Point, List<Point>> points) {
        var image = new BufferedImage(W, H, BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                var v = points.getOrDefault(Point.of(x, y), List.of()).size();
                if (v == 0) image.setRGB(x, y, 0xFFFFFF);
                else image.setRGB(x, y, 0);
            }
        }
        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (Exception e) {}
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    /**
     * Default construct initializes inputs to example.txt and input.txt
     */
    public Day14() {
        super("input.txt");
    }
}
