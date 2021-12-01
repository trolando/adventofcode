package nl.tvandijk.aoc.year2020.day19;

import java.util.Map;
import java.util.Set;

public class RuleMatcher implements Matcher {
    private final int rule;

    public RuleMatcher(int rule) {
        this.rule = rule;
    }

    @Override
    public Set<String> match(String s, Map<Integer, Matcher> rules) {
        return rules.get(rule).match(s, rules);
    }

    @Override
    public String toString() {
        return "" + rule;
    }
}
