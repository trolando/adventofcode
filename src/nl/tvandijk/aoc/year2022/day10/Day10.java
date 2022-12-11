package nl.tvandijk.aoc.year2022.day10;

import java.util.*;
import nl.tvandijk.aoc.common.Day;

public class Day10 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        List<Integer> values = new ArrayList<>();
        int X = 1;
        for (var l : lines) {
            if (l.equals("noop")) {
                values.add(X);
            } else {
                values.add(X);
                values.add(X);
                X += Integer.parseInt(l.substring(5));
            }
        }
        return 20*values.get(19) + 60*values.get(59) + 100*values.get(99) +
                140*values.get(139) + 180*values.get(179) + 220*values.get(219);
    }

    @Override
    protected Object part2() {
        // part 2
        List<Integer> values = new ArrayList<>();
        int X = 1;
        for (var l : lines) {
            if (l.equals("noop")) {
                values.add(X);
            } else {
                values.add(X);
                values.add(X);
                X += Integer.parseInt(l.substring(5));
            }
        }
        for (int i = 0; i < values.size(); i++) {
            if (i%40 == 0) System.out.println();
            int v = values.get(i);
            if (Math.abs(v-(i%40)) <= 1) System.out.print("#");
            else System.out.print(" ");
        }
        System.out.println();
        return null;
    }
}
