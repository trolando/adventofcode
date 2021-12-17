package nl.tvandijk.aoc.year2021.day17;

import nl.tvandijk.aoc.common.Day;

public class Day17 extends Day {
    private static class Probe {
        int x;
        int y;
        int dx;
        int dy;
        int tx1;
        int tx2;
        int ty1;
        int ty2;

        public Probe(int dx, int dy, int tx1, int tx2, int ty1, int ty2) {
            this.dx = dx;
            this.dy = dy;
            this.tx1 = tx1;
            this.tx2 = tx2;
            this.ty1 = ty1;
            this.ty2 = ty2;
        }

        private boolean inTarget() {
            return x >= tx1 && x <= tx2 && y >= ty1 && y <= ty2;
        }

        private boolean overTarget() {
            return x > tx2 || y < ty1;
        }

        private void step() {
            x += dx;
            y += dy;
            if (dx < 0) dx += 1;
            if (dx > 0) dx -= 1;
            dy -= 1;
        }

        private int run() {
            int highestY = 0;
            while (!overTarget()) {
                step();
                highestY = Math.max(highestY, y);
                if (inTarget()) return highestY;
            }
            return Integer.MIN_VALUE;
        }
    }

    @Override
    protected void part1(String fileContents) {
        var s = fileContents.trim().split("[ =,.]+");
        int tx1 = Integer.parseInt(s[3]);
        int tx2 = Integer.parseInt(s[4]);
        int ty1 = Integer.parseInt(s[6]);
        int ty2 = Integer.parseInt(s[7]);

        int high = Integer.MIN_VALUE;
        for (int dx = 0; dx < tx2+1; dx++) {
            for (int dy = ty1; dy < 100; dy++) {
                high = Math.max(high, new Probe(dx, dy, tx1, tx2, ty1, ty2).run());
            }
        }
        System.out.println("Part 1: " + high);
    }


    @Override
    protected void part2(String fileContents) {
        var s = fileContents.trim().split("[ =,.]+");
        int tx1 = Integer.parseInt(s[3]);
        int tx2 = Integer.parseInt(s[4]);
        int ty1 = Integer.parseInt(s[6]);
        int ty2 = Integer.parseInt(s[7]);

        int cnt = 0;
        for (int dx = 0; dx < tx2+1; dx++) {
            for (int dy = ty1; dy < 100; dy++) {
                if (new Probe(dx, dy, tx1, tx2, ty1, ty2).run() != Integer.MIN_VALUE) cnt++;
            }
        }
        System.out.println("Part 2: " + cnt);
    }

    public static void main(String[] args) {
        run(Day17::new, "example.txt", "input.txt");
    }
}
