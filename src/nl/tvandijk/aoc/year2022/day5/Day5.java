package nl.tvandijk.aoc.year2022.day5;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.List;

public class Day5 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
    }

    private List<String> parseStacks(String... lines) {
        List<String> stacks = new ArrayList<>();
        for (var line : lines) {
            for (int i = 1; i < line.length(); i+=4) {
                if (stacks.size() <= i/4) stacks.add("");
                var ch = line.charAt(i);
                if (Character.isAlphabetic(ch)) stacks.set(i/4, ch+stacks.get(i/4));
            }
        }
        return stacks;
    }

    @Override
    protected Object part1() {
        var parts = fileContents.split("\n\n");
        var stacks = parseStacks(parts[0].split("\n"));
        for (var line : parts[1].split("\n")) {
            var tokens = line.split(" ");
            int amount = Integer.parseInt(tokens[1]);
            int source = Integer.parseInt(tokens[3]);
            int target = Integer.parseInt(tokens[5]);
            var from = stacks.get(source-1);
            var to = stacks.get(target-1);
            for (int i = 0; i < amount; i++) {
                var ch = from.charAt(from.length()-1);
                from = from.substring(0, from.length()-1);
                to = to+ch;
            }
            stacks.set(source-1, from);
            stacks.set(target-1, to);
        }
        return stacks.stream().reduce("", (a, s) -> a+s.charAt(s.length()-1));
    }

    @Override
    protected Object part2() {
        var parts = fileContents.split("\r?\n\r?\n");
        var stacks = parseStacks(parts[0].lines().toArray(String[]::new));
        for (var line : parts[1].split("\n")) {
            var tokens = line.split(" ");
            int amount = Integer.parseInt(tokens[1]);
            int source = Integer.parseInt(tokens[3]);
            int target = Integer.parseInt(tokens[5]);
            var from = stacks.get(source-1);
            var to = stacks.get(target-1);
            var removed = from.substring(from.length()-amount);
            from = from.substring(0, from.length()-amount);
            to = to+removed;
            stacks.set(source-1, from);
            stacks.set(target-1, to);
        }
        return stacks.stream().reduce("", (a, s) -> a+s.charAt(s.length()-1));
    }
}
