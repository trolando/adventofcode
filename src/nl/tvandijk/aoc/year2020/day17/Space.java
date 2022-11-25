package nl.tvandijk.aoc.year2020.day17;

import java.util.*;

public class Space {
    private Set<Point> locs = new HashSet<>();

    public void add(int ... dims) {
        locs.add(new Point(dims));
    }

    private void addNeighbors(Collection<Point> targetSet, int[] loc, int k, boolean change) {
        if (k == loc.length) {
            if (change) targetSet.add(new Point(Arrays.copyOf(loc, k)));
        } else {
            addNeighbors(targetSet, loc, k + 1, change);
            loc[k] -= 1;
            addNeighbors(targetSet, loc, k + 1, true);
            loc[k] += 2;
            addNeighbors(targetSet, loc, k + 1, true);
            loc[k] -= 1;
        }
    }

    private List<Point> neighbors(Point loc) {
        List<Point> result = new ArrayList<>((int) (Math.pow(3, loc.where.length)-1));
        addNeighbors(result, loc.where, 0, false);
        return result;
    }

    public void applyRules() {
        var toCheck = new HashSet<Point>();
        for (var loc : locs) {
            toCheck.add(loc);
            toCheck.addAll(neighbors(loc));
        }

        var nextOne = new HashSet<Point>();
        for (var loc : toCheck) {
            // count number of enabled neighbors
            int c = 0;
            for (var n : neighbors(loc)) {
                if (locs.contains(n)) c++;
            }
            // apply correct case
            if (locs.contains(loc)) {
                if (c == 2 || c == 3) nextOne.add(loc);
            } else {
                if (c == 3) nextOne.add(loc);
            }
        }

        locs = nextOne;
    }

    public int size() {
        return locs.size();
    }
}
