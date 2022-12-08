package nl.tvandijk.aoc.year2022.day7;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 extends Day {
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
    }

    @Override
    protected Object part1() {
        List<String> current = new ArrayList<>();
        Map<String, Integer> dirsizes = new HashMap<>();

        for (var line : lines) {
            if (line.equals("$ cd /")) {
                current.clear();
            } else if (line.equals("$ cd ..")) {
                current.remove(current.size()-1);
            } else if (line.startsWith("$ cd ")) {
                current.add(line.substring(5));
            } else if (line.equals("$ ls")) {
            } else if (line.startsWith("dir")) {
            } else {
                var size = Integer.parseInt(line.split(" ")[0]);
                var temp = new ArrayList<>(current);
                while (!temp.isEmpty()) {
                    var key = String.join("/", temp);
                    dirsizes.compute(key, (k, v) -> v == null ? size : v + size);
                    temp.remove(temp.size()-1);
                }
            }
        }

        int sum = 0;
        for (var v : dirsizes.values()) {
            if (v <= 100000) sum += v;
        }
        return sum;
    }

    @Override
    protected Object part2() {
        List<String> current = new ArrayList<>();
        Map<String, Integer> dirsizes = new HashMap<>();

        for (var line : lines) {
            if (line.equals("$ cd /")) {
                current.clear();
            } else if (line.equals("$ cd ..")) {
                current.remove(current.size()-1);
            } else if (line.startsWith("$ cd ")) {
                current.add(line.substring(5));
            } else if (line.equals("$ ls")) {
            } else if (line.startsWith("dir")) {
            } else {
                var size = Integer.parseInt(line.split(" ")[0]);
                var temp = new ArrayList<>(current);
                while (!temp.isEmpty()) {
                    var key = String.join("/", temp);
                    dirsizes.compute(key, (k, v) -> v == null ? size : v + size);
                    temp.remove(temp.size()-1);
                }
                dirsizes.compute("/", (k, v) -> v == null ? size : v + size);
            }
        }

        int minsize = dirsizes.get("/")-40000000;
        return dirsizes.values().stream().filter(v -> v >= minsize).min(Integer::compareTo).get();
    }
}
