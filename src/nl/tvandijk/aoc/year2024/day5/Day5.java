package nl.tvandijk.aoc.year2024.day5;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day5 extends Day {
    private List<int[]> updates = new ArrayList<>();
    private Map<Integer, Set<Integer>> after = new HashMap<>();

    /**
     * Process the file contents, base class behavior is to compute lines and tokens
     *
     * @param fileContents the contents of the input file
     */
    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);
        var parts = this.fileContents.split("\n\n");
        for (var line : parts[0].split("\n")) {
            var s = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
            after.putIfAbsent(s[0], new HashSet<>());
            after.get(s[0]).add(s[1]);
        }
        for (var line : parts[1].split("\n")) {
            updates.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
        }
    }

    boolean isGood(int[] s) {
        var map = new HashMap<Integer, Integer>();
        for (int i = 0; i < s.length; i++) {
            map.put(s[i], i);
        }
        for (var rule : after.entrySet()) {
            int a = rule.getKey();
            if (map.containsKey(a)) {
                int aIndex = map.get(a);
                for (var b : rule.getValue()) {
                    if (map.containsKey(b) && aIndex > map.get(b)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (var s : updates) {
            if (isGood(s)) sum += s[s.length/2];
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        int sum = 0;
        for (var s : updates) {
            if (!isGood(s)) {
                var l = new ArrayList<>(Arrays.stream(s).boxed().toList());
                l.sort((a,b) -> {
                    if (Objects.equals(a, b)) return 0;
                    if (after.containsKey(a) && after.get(a).contains(b)) return -1;
                    return 1;
                });
                sum += l.get(l.size()/2);
            }
        }
        return sum;
    }
}
