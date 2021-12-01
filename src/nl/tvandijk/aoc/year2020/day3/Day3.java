package nl.tvandijk.aoc.year2020.day3;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 extends AoCCommon {
    public static long compute(List<String> lines, int right, int down) {
        int trees = 0;
        int posX = 0; // simply modulo

        for (int i = 0; i < lines.size(); i += down) {
            var line = lines.get(i).trim();
            if (line.charAt(posX) == '#') trees++;
            posX = (posX + right) % line.length();
        }

        System.out.printf("with right=%d down=%d trees:=%d\n", right, down, trees);

        return trees;
    }

    @Override
    protected void process(InputStream stream) throws Exception {
        try (var reader = new BufferedReader(new InputStreamReader(stream))) {
            var lines = reader.lines().collect(Collectors.toList());

            var first = compute(lines, 1, 1);
            var second = compute(lines, 3, 1);
            var third = compute(lines, 5, 1);
            var fourth = compute(lines, 7, 1);
            var fifth = compute(lines, 1, 2);

            System.out.println("result part 1: " + second);
            System.out.println("result part 2: " + first * second * third * fourth * fifth);
        }

    }

    public static void main(String[] args) {
        run(Day3::new, "example.txt", "input.txt");
    }
}
