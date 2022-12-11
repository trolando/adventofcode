package nl.tvandijk.aoc.year2022.day11;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;

public class Day11 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        // part 1
        var monkeys = fileContents.split("\n\n");
        int monkeycount = monkeys.length;
        List<List<Long>> data = new ArrayList<>();
        int[] optype = new int[monkeycount];
        int[] opparam = new int[monkeycount];
        int[] test = new int[monkeycount];
        int[] iftrue = new int[monkeycount];
        int[] iffalse = new int[monkeycount];
        long[] interactions = new long[monkeycount];
        for (int monkey=0; monkey<monkeys.length; monkey++) {
            var monkeylines = monkeys[monkey].split("\n");
            data.add(Arrays.stream(monkeylines[1].split(": ")[1].split(", ")).map(Long::parseLong).collect(Collectors.toList()));
            var s = monkeylines[2].split("old ")[1].split(" ");
            optype[monkey] = s[0].equals("*") ? 0 : 1;
            opparam[monkey] = s[1].equals("old")?-1:Integer.parseInt(s[1]);
            test[monkey] = Integer.parseInt(monkeylines[3].split(" by ")[1]);
            iftrue[monkey] = Integer.parseInt(monkeylines[4].split("monkey ")[1]);
            iffalse[monkey] = Integer.parseInt(monkeylines[5].split("monkey ")[1]);
        }
        for (int j = 0; j < 20; j++) {
            // do a round
            for (int i = 0; i < monkeycount; i++) {
                // get "first" item
                while (data.get(i).size() != 0) {
                    interactions[i]++;
                    var item = data.get(i).remove(0);
                    if (optype[i] == 0) item *= opparam[i] == -1 ? item : opparam[i];
                    else item += opparam[i] == -1 ? item : opparam[i];
                    item /= 3;
                    data.get((item % (test[i]) == 0) ? iftrue[i] : iffalse[i]).add(item);
                }
            }
        }
        Arrays.sort(interactions);
        return interactions[interactions.length-1] * interactions[interactions.length-2];
    }

    @Override
    protected Object part2() {
        // part 2
        var monkeys = fileContents.split("\n\n");
        int monkeycount = monkeys.length;
        List<List<Long>> data = new ArrayList<>();
        int[] optype = new int[monkeycount];
        int[] opparam = new int[monkeycount];
        int[] test = new int[monkeycount];
        int[] iftrue = new int[monkeycount];
        int[] iffalse = new int[monkeycount];
        long[] interactions = new long[monkeycount];
        for (int monkey=0; monkey<monkeys.length; monkey++) {
            var monkeylines = monkeys[monkey].split("\n");
            data.add(Arrays.stream(monkeylines[1].split(": ")[1].split(", ")).map(Long::parseLong).collect(Collectors.toList()));
            var s = monkeylines[2].split("old ")[1].split(" ");
            optype[monkey] = s[0].equals("*") ? 0 : 1;
            opparam[monkey] = s[1].equals("old")?-1:Integer.parseInt(s[1]);
            test[monkey] = Integer.parseInt(monkeylines[3].split(" by ")[1]);
            iftrue[monkey] = Integer.parseInt(monkeylines[4].split("monkey ")[1]);
            iffalse[monkey] = Integer.parseInt(monkeylines[5].split("monkey ")[1]);
        }
        int common = Arrays.stream(test).reduce(1, (a,b)->a*b);
        for (int j = 0; j < 10000; j++) {
            // do a round
            for (int i = 0; i < monkeycount; i++) {
                // get "first" item
                while (data.get(i).size() != 0) {
                    interactions[i]++;
                    var item = data.get(i).remove(0);
                    if (optype[i] == 0) item *= opparam[i] == -1 ? item : opparam[i];
                    else item += opparam[i] == -1 ? item : opparam[i];
                    item %= common;
                    data.get((item % (test[i]) == 0) ? iftrue[i] : iffalse[i]).add(item);
                }
            }
        }
        Arrays.sort(interactions);
        return interactions[interactions.length-1] * interactions[interactions.length-2];
    }
}
