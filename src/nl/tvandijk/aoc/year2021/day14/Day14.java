package nl.tvandijk.aoc.year2021.day14;

import nl.tvandijk.aoc.common.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends Day {
    private String compounds;
    private List<String[]> rules;
    private Map<String, Long> pairs;

    private void parse(String fileContents) {
        var lines = fileContents.split("[\r\n]+");

        compounds = lines[0].trim();

        pairs = new HashMap<>();
        for (int i = 1; i < compounds.length(); i++) {
            pairs.compute(compounds.substring(i - 1, i+1), (key, value) -> value == null ? 1 : 1 + value);
        }

        rules = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            // split each line into two parts
            var parts = lines[i].split(" -> ");
            var spawn1 = "" + parts[0].charAt(0) + parts[1];
            var spawn2 = "" + parts[1] + parts[0].charAt(1);
            rules.add(new String[]{ parts[0], spawn1, spawn2 });
        }
    }

    private void step() {
        var newPairs = new HashMap<String, Long>();

        // each pair spawns new pairs
        for (int i = 0; i < rules.size(); i++) {
            String[] rule = rules.get(i);
            var count = pairs.getOrDefault(rule[0], 0L);
            newPairs.compute(rule[1], (key, value) -> count + (value == null ? 0 : value));
            newPairs.compute(rule[2], (key, value) -> count + (value == null ? 0 : value));
        }

        pairs = newPairs;
    }

    private long count() {
        // do the counting
        Map<Character, Long> counts = new HashMap<>();
        for (var entry : pairs.entrySet()) {
            var add = entry.getValue();
            counts.compute(entry.getKey().charAt(0), (key, value) -> add + (value == null ? 0 : value));
            counts.compute(entry.getKey().charAt(1), (key, value) -> add + (value == null ? 0 : value));
        }

        // add the first and last one
        counts.compute(compounds.charAt(0), (key, value) -> (value == null ? 0 : value) + 1);
        counts.compute(compounds.charAt(compounds.length()-1), (key, value) -> (value == null ? 0 : value) + 1);

        // remember to divide by two
        var justcounts = new ArrayList<>(counts.values());
        justcounts.sort(Long::compareTo);
        return justcounts.get(justcounts.size()-1)/2 - justcounts.get(0)/2;
    }

    @Override
    protected void part1(String fileContents) {
        parse(fileContents);
        for (int i = 0; i < 10; i++) step();
        System.out.println("Part 1: " + count());
    }


    @Override
    protected void part2(String fileContents) {
        parse(fileContents);
        for (int i = 0; i < 40; i++) step();
        System.out.println("Part 2: " + count());
    }

    public static void main(String[] args) {
        run(Day14::new, "example.txt", "input.txt");
    }
}
