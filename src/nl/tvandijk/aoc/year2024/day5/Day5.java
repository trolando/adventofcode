package nl.tvandijk.aoc.year2024.day5;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.PartialOrder;

import java.util.*;

public class Day5 extends Day {
    private PartialOrder<Integer> order = new PartialOrder<>();
    private List<int[]> updates = new ArrayList<>();

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
            order.add(s[0], s[1]);
        }
        for (var line : parts[1].split("\n")) {
            updates.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
        }
    }

    @Override
    protected Object part1() {
        // part 1
        int sum = 0;
        for (var s : updates) {
            List<Integer> l = new ArrayList<>(Arrays.stream(s).boxed().toList());
            if (order.respectsOrder(l)) sum += s[s.length/2];
        }
        return sum;
    }

    @Override
    protected Object part2() {
        // part 2
        int sum = 0;
        for (var s : updates) {
            List<Integer> l = new ArrayList<>(Arrays.stream(s).boxed().toList());
            if (!order.respectsOrder(l)) {
                l = order.order(l);
                sum += l.get(l.size()/2);
            }
        }
        return sum;
    }
}
