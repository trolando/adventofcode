package nl.tvandijk.aoc.year2021.day20;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;

import java.util.*;

public class Day20 extends Day {
    Set<Pair<Integer, Integer>> pixels = new HashSet<>();
    String alg;

    private class Enhanced {
        final boolean inverse;
        final Set<Pair<Integer,Integer>> pixels;
        final Pair<Integer,Integer> xbounds;
        final Pair<Integer,Integer> ybounds;

        public Enhanced(Set<Pair<Integer, Integer>> pixels) {
            this.pixels = pixels;
            int xmin = Integer.MAX_VALUE;
            int xmax = Integer.MIN_VALUE;
            int ymin = Integer.MAX_VALUE;
            int ymax = Integer.MIN_VALUE;
            for (var x : pixels) {
                xmin = Math.min(xmin, x.a);
                xmax = Math.max(xmax, x.a);
                ymin = Math.min(ymin, x.b);
                ymax = Math.max(ymax, x.b);
            }
            xbounds = Pair.of(xmin, xmax+1);
            ybounds = Pair.of(ymin, ymax+1);
            inverse = false;
        }

        public Enhanced(Enhanced src) {
            this.pixels = new HashSet<>();
            this.xbounds = Pair.of(src.xbounds.a-1, src.xbounds.b+1);
            this.ybounds = Pair.of(src.ybounds.a-1, src.ybounds.b+1);
            if (alg.charAt(0) == '#') {
                this.inverse = !src.inverse;
                for (int x = xbounds.a; x < xbounds.b; x++) {
                    for (int y = ybounds.a; y < ybounds.b; y++) {
                        var p = Pair.of(x, y);
                        var r = compute(src, p);
                        if (r != inverse) pixels.add(p);
                    }
                }
            } else {
                this.inverse = false;
                for (int x = xbounds.a; x < xbounds.b; x++) {
                    for (int y = ybounds.a; y < ybounds.b; y++) {
                        var p = Pair.of(x, y);
                        if (compute(src, p)) pixels.add(p);
                    }
                }
            }
        }

        public boolean get(int x, int y) {
            var p = Pair.of(x, y);
            var r = pixels.contains(p);
            return inverse != r;
        }

        private boolean compute(Enhanced src, Pair<Integer,Integer> p) {
            int x = p.a;
            int y = p.b;
            int r = 0;
            if (src.get(x-1, y-1)) r++;
            r *= 2;
            if (src.get(x, y-1)) r++;
            r *= 2;
            if (src.get(x+1, y-1)) r++;
            r *= 2;
            if (src.get(x-1, y)) r++;
            r *= 2;
            if (src.get(x, y)) r++;
            r *= 2;
            if (src.get(x+1, y)) r++;
            r *= 2;
            if (src.get(x-1, y+1)) r++;
            r *= 2;
            if (src.get(x, y+1)) r++;
            r *= 2;
            if (src.get(x+1, y+1)) r++;
            return alg.charAt(r) == '#';
        }
    }

    @Override
    protected Object part1() {
        alg = lines[0].trim();
        for (int i = 1; i < lines.length; i++) {
            for (int j=0; j<lines[i].length(); j++) {
                if (lines[i].charAt(j) == '#') {
                    pixels.add(Pair.of(j, i-1));
                }
            }
        }
        Enhanced x = new Enhanced(pixels);
        for (int i = 0; i < 2; i++) {
            x = new Enhanced(x);
        }
        return x.pixels.size();
    }

    @Override
    protected Object part2() {
        Enhanced x = new Enhanced(pixels);
        for (int i = 0; i < 50; i++) {
            x = new Enhanced(x);
//            System.out.printf("Enhanced %d times: %d pixels are different!%n", (i+1), x.pixels.size());
        }
        return x.pixels.size();
    }
}
