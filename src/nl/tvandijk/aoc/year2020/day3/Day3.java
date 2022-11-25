package nl.tvandijk.aoc.year2020.day3;

import nl.tvandijk.aoc.common.Day;

public class Day3 extends Day {
    public static long compute(String[] lines, int right, int down) {
        int trees = 0;
        int posX = 0; // simply modulo

        for (int i = 0; i < lines.length; i += down) {
            var line = lines[i].trim();
            if (line.charAt(posX) == '#') trees++;
            posX = (posX + right) % line.length();
        }

//        System.out.printf("with right=%d down=%d trees:=%d\n", right, down, trees);

        return trees;
    }

    @Override
    protected Object part1() throws Exception {
        return compute(lines, 3, 1);
    }

    @Override
    protected Object part2() throws Exception {
        var first = compute(lines, 1, 1);
        var second = compute(lines, 3, 1);
        var third = compute(lines, 5, 1);
        var fourth = compute(lines, 7, 1);
        var fifth = compute(lines, 1, 2);

        return first * second * third * fourth * fifth;
    }
}
