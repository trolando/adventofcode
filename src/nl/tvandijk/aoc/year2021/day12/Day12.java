package nl.tvandijk.aoc.year2021.day12;

import nl.tvandijk.aoc.common.Day;

import java.util.ArrayList;

public class Day12 extends Day {
    private int locCount = 0;   // number of locations
    private boolean[] edges;    // size locCount*locCount
    private boolean[] isSmall;  // whether a location is small
    private boolean[] visited;  // whether a location is visited in the current dfs
    private long pathCount = 0; // number of distinct paths found

    private void parse(String fileContents) {
        var locs = new ArrayList<String>();
        locs.add("start"); // encode start as 0
        locs.add("end");   // encode end as 1

        var edgelist = new ArrayList<Integer>();

        // parse each line
        var lines = fileContents.split("[\r\n]+");
        for (int i = 0; i < lines.length; i++) {
            // split each line into two parts
            var parts = lines[i].split("-");

            int one = locs.indexOf(parts[0]);
            if (one == -1) {
                locs.add(parts[0]);
                one = locs.size() - 1;
            }

            int two = locs.indexOf(parts[1]);
            if (two == -1) {
                locs.add(parts[1]);
                two = locs.size() - 1;
            }

            edgelist.add(one);
            edgelist.add(two);
        }

        // initialize internal datastructures
        locCount = locs.size();
        isSmall = new boolean[locCount];
        for (int i = 0; i < locCount; i++) {
            isSmall[i] = Character.isLowerCase(locs.get(i).charAt(0));
        }
        edges = new boolean[locCount*locCount];
        for (int i = 0; i < edgelist.size(); i+=2) {
            int one = edgelist.get(i);
            int two = edgelist.get(i+1);
            edges[one*locCount+two] = true;
            edges[two*locCount+one] = true;
        }
        visited = new boolean[locCount];
        pathCount = 0;
    }

    private void createPaths(int currentLocation, boolean allowTwice) {
        for (int to = 1; to < locCount; to++) { // skip "start"
            if (edges[currentLocation + to * locCount]) {
                if (to == 1) {
                    pathCount++;
                } else {
                    // check if already seen
                    if (isSmall[to]) {
                        if (visited[to]) {
                            if (allowTwice) {
                                createPaths(to, false);
                            }
                        } else {
                            visited[to] = true;
                            createPaths(to, allowTwice);
                            visited[to] = false;
                        }
                    } else {
                        createPaths(to, allowTwice);
                    }
                }
            }
        }
    }

    @Override
    protected void part1(String fileContents) {
        parse(fileContents);
        createPaths(0,false);
        System.out.println("Part 1: " + pathCount);
    }

    @Override
    protected void part2(String fileContents) {
        parse(fileContents);
        createPaths(0,true);
        System.out.println("Part 2: " + pathCount);
    }

    public static void main(String[] args) {
        run(Day12::new, "example.txt", "input.txt");
    }
}
