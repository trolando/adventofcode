package nl.tvandijk.aoc.year2022.day19;

import java.util.*;
import nl.tvandijk.aoc.common.Day;

public class Day19 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    int compute1(int rem, int[] state, int[] costs, int[] max) {
        // what is our next action?
        int best = 0;
        {
            // do nothing
            var s = Arrays.copyOf(state, state.length);
            for (int i = 0; i < rem; i++) {
                s[0] += s[4];
                s[1] += s[5];
                s[2] += s[6];
                s[3] += s[7];
            }
            best = Math.max(best, s[3]);
        }
        if (rem <= 1) {
            return best;
        }
        {
            // make geode
            var s = Arrays.copyOf(state, state.length);
            var r = rem;
            while (true) {
                if (s[0] >= costs[4] && s[2] >= costs[5]) {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    s[7]++;
                    s[0] -= costs[4];
                    s[2] -= costs[5];
                    r--;
                    best = Math.max(best, compute1(r, s, costs, max));
                    break;
                } else {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    r--;
                    if (r <= 1) break; // no matter
                }
            }
        }
        if (state[6] < max[2]) {
            // make obsidian
            var s = Arrays.copyOf(state, state.length);
            var r = rem;
            while (true) {
                if (s[0] >= costs[2] && s[1] >= costs[3]) {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    s[6]++;
                    s[0] -= costs[2];
                    s[1] -= costs[3];
                    r--;
                    best = Math.max(best, compute1(r, s, costs, max));
                    break;
                } else {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    r--;
                    if (r <= 1) break; // no matter
                }
            }
        }
        if (state[5] < max[1]) {
            // make clay
            var s = Arrays.copyOf(state, state.length);
            var r = rem;
            while (true) {
                if (s[0] >= costs[1]) {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    s[5]++;
                    s[0] -= costs[1];
                    r--;
                    best = Math.max(best, compute1(r, s, costs, max));
                    break;
                } else {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    r--;
                    if (r <= 1) break; // no matter
                }
            }
        }
        if (state[4] < max[0]) {
            // make ore
            var s = Arrays.copyOf(state, state.length);
            var r = rem;
            while (true) {
                if (s[0] >= costs[0]) {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    s[4]++;
                    s[0] -= costs[0];
                    r--;
                    best = Math.max(best, compute1(r, s, costs, max));
                    break;
                } else {
                    s[0] += s[4];
                    s[1] += s[5];
                    s[2] += s[6];
                    s[3] += s[7];
                    r--;
                    if (r <= 1) break; // no matter
                }
            }
        }
        return best;
    }

    @Override
    protected Object part1() {
        // part 1
        int result = 0;
        int i=1;
        for (var line : lines) {
            int[] costs = new int[6];
            var spl = line.split("costs ");
            costs[0] = Integer.parseInt(spl[1].split(" ")[0]);
            costs[1] = Integer.parseInt(spl[2].split(" ")[0]);
            costs[2] = Integer.parseInt(spl[3].split(" ")[0]);
            costs[3] = Integer.parseInt(spl[3].split(" ")[3]);
            costs[4] = Integer.parseInt(spl[4].split(" ")[0]);
            costs[5] = Integer.parseInt(spl[4].split(" ")[3]);
            int[] m = new int[4];
            m[0] = costs[0];
            m[0] = Math.max(m[0], costs[1]);
            m[0] = Math.max(m[0], costs[2]);
            m[0] = Math.max(m[0], costs[4]);
            m[1] = costs[3];
            m[2] = costs[5];
            m[3] = 9999999;

            int max = compute1(24, new int[] {0,0,0,0,1,0,0,0}, costs, m);
            System.out.printf("result for blueprint %d is %d%n", i, max);
            result += i++ * max;
        }
        return result;
    }

    @Override
    protected Object part2() {
        // part 2
        int result = 1;
        int i=1;
        int D = 2;
        for (var line : lines) {
            int[] costs = new int[6];
            var spl = line.split("costs ");
            costs[0] = Integer.parseInt(spl[1].split(" ")[0]);
            costs[1] = Integer.parseInt(spl[2].split(" ")[0]);
            costs[2] = Integer.parseInt(spl[3].split(" ")[0]);
            costs[3] = Integer.parseInt(spl[3].split(" ")[3]);
            costs[4] = Integer.parseInt(spl[4].split(" ")[0]);
            costs[5] = Integer.parseInt(spl[4].split(" ")[3]);
            int[] m = new int[4];
            m[0] = costs[0];
            m[0] = Math.max(m[0], costs[1]);
            m[0] = Math.max(m[0], costs[2]);
            m[0] = Math.max(m[0], costs[4]);
            m[1] = costs[3];
            m[2] = costs[5];
            m[3] = 9999999;

            int max = compute1(32, new int[]{0, 0, 0, 0, 1, 0, 0, 0}, costs, m);
            System.out.printf("result for blueprint %d is %d%n", i, max);
            result *= max;
            if (i == 3) break;
            i++;
        }
        return result;
    }
}
