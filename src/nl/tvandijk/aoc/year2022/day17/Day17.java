package nl.tvandijk.aoc.year2022.day17;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Point;

public class Day17 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        String line = lines[0].trim();
        int pos = 0;

        Set<Point> rocks = new HashSet<>();
        long height = 0;

        List<Point[]> shapes = new ArrayList<>();
        shapes.add(new Point[] { Point.of(2,0), Point.of (3,0), Point.of(4,0), Point.of(5,0) });
        shapes.add(new Point[] { Point.of(3,0), Point.of (2,1), Point.of(3,1), Point.of(4,1), Point.of(3,2) });
        shapes.add(new Point[] { Point.of(2,0), Point.of (3,0), Point.of(4,0), Point.of(4,1), Point.of(4,2) });
        shapes.add(new Point[] { Point.of(2,0), Point.of (2,1), Point.of(2,2), Point.of(2,3) });
        shapes.add(new Point[] { Point.of(2,0), Point.of (2,1), Point.of(3,0), Point.of(3,1) });
        int shape = 0;

        for (int i = 0; i < 2022; i++) {
            // spawn next shape
            Point[] s = Arrays.copyOf(shapes.get(shape), shapes.get(shape).length);
            shape = (shape+1) % shapes.size();
            for (int j = 0; j < s.length; j++) {
                s[j] = s[j].delta(0, height+3);
            }

            while (true) {
                // take instruction
                if (line.charAt(pos) == '>') {
                    // to right
                    boolean can = true;
                    for (int j = 0; j < s.length; j++) {
                        if (s[j].x == 6) can = false;
                        if (rocks.contains(s[j].delta(1,0))) can = false;
                    }
                    if (can) {
//                        System.out.println("move right");
                        for (int j = 0; j < s.length; j++) {
                            s[j] = s[j].delta(1, 0);
                        }
                    }
                } else {
                    boolean can = true;
                    for (int j = 0; j < s.length; j++) {
                        if (s[j].x == 0) can = false;
                        if (rocks.contains(s[j].delta(-1,0))) can = false;
                    }
                    if (can) {
//                        System.out.println("move left");
                        for (int j = 0; j < s.length; j++) {
                            s[j] = s[j].delta(-1, 0);
                        }
                    }
                }
                pos = (pos+1)%line.length();
                // move down
                boolean can = true;
                for (int j = 0; j < s.length; j++) {
                    if (s[j].y == 0) can = false;
                    if (rocks.contains(s[j].delta(0,-1))) can = false;
                }
                if (can) {
//                    System.out.println("move down");
                    for (int j = 0; j < s.length; j++) {
                        s[j] = s[j].delta(0, -1);
                    }
                } else {
//                    System.out.println("rest");
                    for (int j = 0; j < s.length; j++) {
                        rocks.add(s[j]);
                        height = Math.max(s[j].y+1, height);
                    }
                    break;
                }
            }
//            for (int j = 0; j < height; j++) {
//                int y = height-j-1;
//                for (int x = 0; x < 7; x++) {
//                    if (rocks.contains(Point.of(x,y))) System.out.printf("#");
//                    else System.out.printf(".");
//                }
//                System.out.printf("%n");
//            }
//            System.out.printf("%n");
        }

        return height;
    }

    Map<State, Long> history_h = new HashMap<>();
    Map<State, Long> history_i = new HashMap<>();

    class State {
        int[] arr;

        public State(int[] arr) {
            this.arr = arr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State state)) return false;
            return Arrays.equals(arr, state.arr);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(arr);
        }
    }

    @Override
    protected Object part2() {
        // part 2
        String line = lines[0].trim();
        int pos = 0;

        Set<Point> rocks = new HashSet<>();
        long height = 0;

        List<Point[]> shapes = new ArrayList<>();
        shapes.add(new Point[] { Point.of(2,0), Point.of (3,0), Point.of(4,0), Point.of(5,0) });
        shapes.add(new Point[] { Point.of(3,0), Point.of (2,1), Point.of(3,1), Point.of(4,1), Point.of(3,2) });
        shapes.add(new Point[] { Point.of(2,0), Point.of (3,0), Point.of(4,0), Point.of(4,1), Point.of(4,2) });
        shapes.add(new Point[] { Point.of(2,0), Point.of (2,1), Point.of(2,2), Point.of(2,3) });
        shapes.add(new Point[] { Point.of(2,0), Point.of (2,1), Point.of(3,0), Point.of(3,1) });
        int shape = 0;

        int[] positions = new int[shapes.size()*2];
        int pospos = 0;
        long add = 0;

        for (long i = 0; i < 1_000_000_000_000L; i++) {
            int dx = 2;

            // spawn next shape
            Point[] s = Arrays.copyOf(shapes.get(shape), shapes.get(shape).length);
            for (int j = 0; j < s.length; j++) {
                s[j] = s[j].delta(0, height+3);
            }

            while (true) {
                // take instruction
                if (line.charAt(pos) == '>') {
                    // to right
                    boolean can = true;
                    for (int j = 0; j < s.length; j++) {
                        if (s[j].x == 6) can = false;
                        if (rocks.contains(s[j].delta(1,0))) can = false;
                    }
                    if (can) {
                        for (int j = 0; j < s.length; j++) s[j] = s[j].delta(1, 0);
                        dx++;
                    }
                } else {
                    boolean can = true;
                    for (int j = 0; j < s.length; j++) {
                        if (s[j].x == 0) can = false;
                        if (rocks.contains(s[j].delta(-1,0))) can = false;
                    }
                    if (can) {
                        for (int j = 0; j < s.length; j++) s[j] = s[j].delta(-1, 0);
                        dx--;
                    }
                }
                pos = (pos+1)%line.length();
                // move down
                boolean can = true;
                for (int j = 0; j < s.length; j++) {
                    if (s[j].y == 0) can = false;
                    if (rocks.contains(s[j].delta(0,-1))) can = false;
                }
                if (can) {
                    for (int j = 0; j < s.length; j++) s[j] = s[j].delta(0, -1);
                } else {
                    for (int j = 0; j < s.length; j++) {
                        rocks.add(s[j]);
                        height = Math.max(s[j].y+1, height);
                    }
                    positions[pospos] = dx;
                    pospos = (pospos+1)%positions.length;
                    break;
                }
            }
            shape = (shape+1) % shapes.size();

            if (pospos == 0 && add == 0) {
                var st = new State(positions);
                var v = history_h.get(st);
                if (v != null) {
                    long ci = history_i.get(st);
                    long remaining = 1000000000000L - i;
                    long count = remaining / (i - ci);
                    add = count * (height - v);
                    i += count * (i - ci);
                }
                history_h.put(st, height);
                history_i.put(st, i);
            }
        }

        return height + add;
    }
}
