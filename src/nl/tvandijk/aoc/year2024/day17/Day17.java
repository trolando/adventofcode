package nl.tvandijk.aoc.year2024.day17;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17 extends Day {
    List<Integer> run(long A, long B, long C, int[] P) {
        List<Integer> out = new ArrayList<>();

        int pc = 0;
        while (pc < P.length) {
            int op = P[pc++];
            int lit = P[pc++];
            var combo = switch (lit) {
                case 0 -> 0;
                case 1 -> 1;
                case 2 -> 2;
                case 3 -> 3;
                case 4 -> A;
                case 5 -> B;
                case 6 -> C;
                default -> -1;
            };

            if (op == 0) A = (long) (A / Math.pow(2, combo));
            else if (op == 1) B = B ^ lit;
            else if (op == 2) B = combo & 7;
            else if (op == 3 && A != 0) pc = lit;
            else if (op == 4) B = B ^ C;
            else if (op == 5) out.add((int) combo & 7);
            else if (op == 6) B = (long) (A / Math.pow(2, combo));
            else if (op == 7) C = (long) (A / Math.pow(2, combo));
        }

        return out;
    }

    @Override
    protected Object part1() {
        // part 1
        var numbers = this.numbers(fileContents);

        var A = numbers[0];
        var B = numbers[1];
        var C = numbers[2];
        var P = Arrays.stream(numbers).skip(3).mapToInt(x->(int) x).toArray();

        var res = run(A, B, C, P);
        return String.join(",", res.stream().map(String::valueOf).toList());
    }

    long recfind(long A, int[] P, int n) {
        for (int i=0; i<8; i++) {
            var res = run(A+i, 0, 0, P);
            if (res.size() <= n) return 0L; // bad
            var offset = P.length-res.size();
            boolean good = true;
            for (int j = 0; j < res.size(); j++) {
                if (P[offset+j] != res.get(j)) good = false;
            }
            if (good) {
                if (res.size() == P.length) return A+i;
                var v = recfind((A+i)<<3, P, n+1);
                if (v != 0L) return v;
            }
        }
        return 0L;
    }

    @Override
    protected Object part2() {
        // part 2
        var numbers = this.numbers(fileContents);
        var P = Arrays.stream(numbers).skip(3).mapToInt(x->(int) x).toArray();
        return recfind(0, P, 0);
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day17() {
        super("example.txt", "input.txt");
//        super("example.txt");
    }
}
