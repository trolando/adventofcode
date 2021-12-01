package nl.tvandijk.aoc.year2020.day19;

import java.util.Map;
import java.util.Set;

public class AtomMatcher implements Matcher {
    final char ch;

    public AtomMatcher(char ch) {
        this.ch = ch;
    }

    @Override
    public Set<String> match(String s, Map<Integer, Matcher> rules) {
        if (s.charAt(0) == ch) return Set.of(s.substring(1));
        else return Set.of();
    }

    @Override
    public String toString() {
        return "" + ch;
    }
}
