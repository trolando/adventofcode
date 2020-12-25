package nl.tvandijk.aoc.day17;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;
import java.util.regex.Pattern;

public class Day17 extends AoCCommon {
    @Override
    public void process(InputStream stream) throws IOException {
        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            Space space = new Space();

            String line;
            int y=0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    if (line.charAt(x) == '#') {
                        space.add(x, y, 0, 0);
                    }
                }
                y++;
            }

            System.out.printf("Count after %d: %d\n", 0, space.locs.size());
            for (int i = 0; i < 6; i++) {
                space.applyRules();
                System.out.printf("Count after %d: %d\n", i+1, space.locs.size());
            }
        }
    }

    public static void main(String[] args) {
        run(Day17::new, "example.txt", "input.txt");
    }
}