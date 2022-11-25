package nl.tvandijk.aoc.year2020.day13;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 extends Day {
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
    protected Object part1() throws Exception {
        int timestamp = Integer.parseInt(lines[0]);
        String[] parts = lines[1].split(",");

        int[] services = Arrays.stream(parts).filter(x -> !x.equals("x")).mapToInt(x -> Integer.valueOf(x)).toArray();

        for (int i = timestamp; true; i++) {
            for (int service : services) {
                if ((i % service) == 0) {
                    //System.out.printf("Found: %d * %d = %d\n", services[j], i-timestamp, ((services[j]*(i-timestamp))));
                    return service * (i - timestamp);
                }
            }
        }
    }

    @Override
    protected Object part2() throws Exception {
        String[] parts = lines[1].split(",");
        return solve2(parts);
    }

    public static void test() {
        System.out.printf("%d\n", solve2(new String[] {"17","x","13","19"}));
        System.out.printf("%d\n", solve2(new String[] {"67","7","59","61"}));
        System.out.printf("%d\n", solve2(new String[] {"67","x","7","59","61"}));
        System.out.printf("%d\n", solve2(new String[] {"67","7","x","59","61"}));
        System.out.printf("%d\n", solve2(new String[] {"1789","37","47","1889"}));
        System.out.println();
    }
}
