package nl.tvandijk.aoc.year2022.day20;

import java.util.*;
import nl.tvandijk.aoc.common.Day;

public class Day20 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    class Wrap {
        long v;

        public Wrap(long v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return ""+v;
        }
    }

    @Override
    protected Object part1() {
        // part 1
        List<Wrap> l = new LinkedList<>();
        for (var line : lines) {
            l.add(new Wrap(Integer.parseInt(line)));
        }
        Wrap zero = null;
        List<Wrap> orig = new LinkedList<>(l);
        for (var w : orig) {
            int loc = l.indexOf(w);
            if (w.v == 0) zero = w;
            l.remove(loc);
            long newLoc = loc + w.v;
            newLoc %= l.size();
            newLoc += l.size();
            newLoc %= l.size();
            l.add((int)newLoc, w);
        }
        int k = l.indexOf(zero);
        return l.get((k+1000)%l.size()).v + l.get((k+2000)%l.size()).v + l.get((k+3000)%l.size()).v;
    }

    @Override
    protected Object part2() {
        // part 2
        List<Wrap> l = new LinkedList<>();
        for (var line : lines) {
            l.add(new Wrap(811589153L*Integer.parseInt(line)));
        }
        Wrap zero = null;
        List<Wrap> orig = new LinkedList<>(l);
        for (int i = 0; i < 10; i++) {
            for (var w : orig) {
                int loc = l.indexOf(w);
                if (w.v == 0) zero = w;
                l.remove(loc);
                long newLoc = loc + w.v;
                newLoc %= l.size();
                newLoc += l.size();
                newLoc %= l.size();
                l.add((int)newLoc, w);
            }
        }
        int k = l.indexOf(zero);
        return l.get((k+1000)%l.size()).v + l.get((k+2000)%l.size()).v + l.get((k+3000)%l.size()).v;
    }
}
