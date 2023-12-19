package nl.tvandijk.aoc.year2023.day19;

import java.util.Map;

class RuleGreater implements Rule {
    String var;
    int value;
    String dest;

    public RuleGreater(String var, int value, String dest) {
        this.var = var;
        this.value = value;
        this.dest = dest;
    }

    public String accept(Map<String, Integer> map) {
        if (map.get(var) > value) return dest;
        else return null;
    }
}
