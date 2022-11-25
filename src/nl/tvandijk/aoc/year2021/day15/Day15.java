package nl.tvandijk.aoc.year2021.day15;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day15 extends Day {
    int[] risk;
    int size;

    private void parse(String fileContents) {
        var lines = fileContents.split("[\r\n]+");
        size = lines[0].trim().length();

        var riskList = new ArrayList<Integer>();
        for (int i = 0; i < fileContents.length(); i++) {
            int ch = fileContents.charAt(i);
            if (Character.isDigit(ch)) {
                riskList.add(Integer.valueOf(Character.toString(ch)));
            }
        }
        this.risk = new int[riskList.size()];
        for (int i = 0; i < riskList.size(); i++) {
            this.risk[i] = riskList.get(i);
        }
    }

    private int getRisk(int x, int y) {
        int origx = x%size;
        int origy = y%size;
        int dx = x/size;
        int dy = y/size;
        int a = risk[origx + origy*size] + dx + dy;
        return 1 + (a-1) % 9;
    }

    private int computeRisk(int totsize) {
        Map<Integer, Integer> distance = new HashMap<>();
        PriorityQueue<Integer> unvisited = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        distance.put(totsize*totsize-1, 0);
        unvisited.add(totsize*totsize-1);
        while (!unvisited.isEmpty()) {
            int v = unvisited.poll();
            int x = v%totsize;
            int y = v/totsize;
            int d = distance.get(v) + getRisk(x, y);
            if (x > 0) {
                int nv = (x - 1) + y * totsize;
                if (distance.containsKey(nv)) {
                    int cur = distance.get(nv);
                    if (cur > d) {
                        unvisited.remove(nv);
                        distance.put(nv, d);
                        unvisited.add(nv);
                    }
                } else {
                    distance.put(nv, d);
                    unvisited.add(nv);
                }
            }
            if ((x + 1) < totsize) {
                int nv = (x + 1) + y * totsize;
                if (distance.containsKey(nv)) {
                    int cur = distance.get(nv);
                    if (cur > d) {
                        unvisited.remove(nv);
                        distance.put(nv, d);
                        unvisited.add(nv);
                    }
                } else {
                    distance.put(nv, d);
                    unvisited.add(nv);
                }
            }
            if (y > 0) {
                int nv = x + (y - 1) * totsize;
                if (distance.containsKey(nv)) {
                    int cur = distance.get(nv);
                    if (cur > d) {
                        unvisited.remove(nv);
                        distance.put(nv, d);
                        unvisited.add(nv);
                    }
                } else {
                    distance.put(nv, d);
                    unvisited.add(nv);
                }
            }
            if ((y + 1) < totsize) {
                int nv = x + (y + 1) * totsize;
                if (distance.containsKey(nv)) {
                    int cur = distance.get(nv);
                    if (cur > d) {
                        unvisited.remove(nv);
                        distance.put(nv, d);
                        unvisited.add(nv);
                    }
                } else {
                    distance.put(nv, d);
                    unvisited.add(nv);
                }
            }
        }
        return distance.get(0);
    }

    @Override
    protected Object part1() {
        parse(fileContents);
        return computeRisk(size);
    }

    @Override
    protected Object part2() {
        parse(fileContents);
        return computeRisk(5*size);
    }
}
