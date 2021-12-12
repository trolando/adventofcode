package nl.tvandijk.aoc.year2021.day12;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day12 extends Day {
    private Map<String, Set<String>> parse(String fileContents) {
        Map<String, Set<String>> edges = new HashMap<>();
        // parse each line
        for (var line : fileContents.split("[\r\n]+")) {
            // split each line into two parts
            var parts = line.split("-");
            var from = parts[0];
            var to = parts[1];
            // from -> to
            if (!to.equals("start")) {
                edges.compute(from, (key, value) -> {
                    if (value == null) value = new HashSet<>();
                    value.add(to);
                    return value;
                });
            }
            // to -> from
            if (!from.equals("start")) {
                edges.compute(to, (key, value) -> {
                    if (value == null) value = new HashSet<>();
                    value.add(from);
                    return value;
                });
            }
        }
        return edges;
    }

    private void createPaths(Set<String> paths, List<String> currentPath, Map<String, Set<String>> edges, String allowTwice) {
        var currentLocation = currentPath.get(currentPath.size()-1);
        if (currentLocation.equals("end")) {
            paths.add(String.join(",", currentPath));
        } else {
            for (String to : edges.get(currentLocation)) {
                // check if already seen
                if (Character.toLowerCase(to.charAt(0)) == to.charAt(0)) {
                    var visitedTimes = currentPath.stream().filter(x -> x.equals(to)).count();
                    int maxTimes = allowTwice.equals(to) ? 2 : 1;
                    if (visitedTimes >= maxTimes) continue;
                }
                // if not, go there...
                currentPath.add(to);
                createPaths(paths, currentPath, edges, allowTwice);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    @Override
    protected void part1(String fileContents) {
        var edges = parse(fileContents);

        Set<String> paths = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        currentPath.add("start");
        createPaths(paths, currentPath, edges, "");

        System.out.println("Part 1: " + paths.size());
    }

    @Override
    protected void part2(String fileContents) {
        var edges = parse(fileContents);

        Set<String> paths = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        currentPath.add("start");
        for (var place : edges.keySet()) {
            if (Character.toLowerCase(place.charAt(0)) == place.charAt(0)) {
                createPaths(paths, currentPath, edges, place);
            }
        }

        System.out.println("Part 2: " + paths.size());
    }

    public static void main(String[] args) {
        run(Day12::new, "example.txt", "input.txt");
    }
}
