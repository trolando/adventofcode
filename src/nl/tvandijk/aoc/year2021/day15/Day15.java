package nl.tvandijk.aoc.year2021.day15;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day15 extends Day {
    List<Integer> risk;
    int size;

    private void parse(String fileContents) {
        var lines = fileContents.split("[\r\n]+");
        size = lines[0].trim().length();

        risk = new ArrayList<>();
        for (int i = 0; i < fileContents.length(); i++) {
            int ch = fileContents.charAt(i);
            if (Character.isDigit(ch)) {
                risk.add(Integer.valueOf(Character.toString(ch)));
            }
        }
    }

    private int getRisk(int x, int y) {
        int origx = x%size;
        int origy = y%size;
        int dx = x/size;
        int dy = y/size;
        int a = (risk.get(origx + origy*size) + dx + dy);
        while (a > 9) a -= 9;
        return a;
    }

    private int computeRisk(int totsize) {
        List<Integer> unvisited = new ArrayList<>();
        Map<Integer, Integer> distance = new HashMap<>();
        distance.put(totsize*totsize-1, 0);
        unvisited.add(totsize*totsize-1);
        while (!unvisited.isEmpty()) {
            // find next unvisited with non infinite distance
            unvisited.sort(Comparator.comparingInt(x -> distance.getOrDefault(x, Integer.MAX_VALUE)).reversed());
            int v = unvisited.get(unvisited.size()-1);
            unvisited.remove(unvisited.size()-1);
            if (!distance.containsKey(v)) break; // done
            int x = v%totsize;
            int y = v/totsize;
            int d = distance.get(v) + getRisk(x, y);
            if (x > 0) {
                int nv = (x - 1) + y * totsize;
                if (!distance.containsKey(nv)) unvisited.add(nv);
                distance.compute(nv, (key, val) -> val == null ? d : Math.min(val, d));
            }
            if ((x+1) < totsize) {
                int nv = (x + 1) + y * totsize;
                if (!distance.containsKey(nv)) unvisited.add(nv);
                distance.compute(nv, (key, val) -> val == null ? d : Math.min(val, d));
            }
            if (y > 0) {
                int nv = x + (y-1) * totsize;
                if (!distance.containsKey(nv)) unvisited.add(nv);
                distance.compute(nv, (key, val) -> val == null ? d : Math.min(val, d));
            }
            if ((y+1) < totsize) {
                int nv = x + (y+1) * totsize;
                if (!distance.containsKey(nv)) unvisited.add(nv);
                distance.compute(nv, (key, val) -> val == null ? d : Math.min(val, d));
            }
        }
        return distance.get(0);
    }

    @Override
    protected void part1(String fileContents) {
        parse(fileContents);
        System.out.println("Part 1: " + computeRisk(size));
    }


    @Override
    protected void part2(String fileContents) {
        parse(fileContents);
        System.out.println("Part 2: " + computeRisk(5*size));
    }

    public static void main(String[] args) {
        run(Day15::new, "example.txt", "input.txt");
    }
}
