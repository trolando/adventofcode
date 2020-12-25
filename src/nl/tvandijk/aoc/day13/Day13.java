package nl.tvandijk.aoc.day13;

import nl.tvandijk.aoc.common.AoCCommon;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 extends AoCCommon {
    public void solve1(int timestamp, String[] parts) {
        int[] services = Arrays.stream(parts).filter(x -> !x.equals("x")).mapToInt(x -> Integer.valueOf(x)).toArray();

        for (int i = timestamp; true; i++) {
            for (int j = 0; j < services.length; j++) {
                if ((i%services[j])==0) {
                    System.out.printf("Found: %d * %d = %d\n", services[j], i-timestamp, ((services[j]*(i-timestamp))));
                    return;
                }
            }
        }
    }

    private static long solve2(String[] parts) {
        // STEP 1: transform the input.txt string to an array of services and array of indices
        List<Integer> services = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].equals("x")) {
                services.add(Integer.valueOf(parts[i]));
                indices.add(i);
            }
        }

        int[] srv = services.stream().mapToInt(x->x).toArray();
        int[] idx = indices.stream().mapToInt(x->x).toArray();

        // STEP 2: solve the puzzle
        long offset = srv[0];
        long adder = srv[0];
        for (int i = 1; i < srv.length; i++) {
            // Keep jumping until we've found the next
            for (long x=offset; ;x+=adder) {
                if ((x+idx[i]) % srv[i] == 0) {
                    offset = x;
                    adder *= srv[i];
                    break;
                }
            }
        }
        return offset;
    }

    @Override
    protected void process(InputStream stream) throws Exception {
        try (var br = new BufferedReader(new InputStreamReader(stream))) {
            int timestamp = Integer.parseInt(br.readLine());
            String[] parts = br.readLine().split(",");

            solve1(timestamp, parts);
            System.out.printf("m = %d\n", solve2(parts));
        }
    }

    public static void main(String[] args) {
        System.out.printf("%d\n", solve2(new String[] {"17","x","13","19"}));
        System.out.printf("%d\n", solve2(new String[] {"67","7","59","61"}));
        System.out.printf("%d\n", solve2(new String[] {"67","x","7","59","61"}));
        System.out.printf("%d\n", solve2(new String[] {"67","7","x","59","61"}));
        System.out.printf("%d\n", solve2(new String[] {"1789","37","47","1889"}));
        System.out.println();

        run(Day13::new, "example.txt", "input.txt");
    }
}
