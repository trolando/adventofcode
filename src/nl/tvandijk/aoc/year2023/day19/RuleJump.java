package nl.tvandijk.aoc.year2023.day19;

import java.util.Map;

class RuleJump implements Rule {
    String dest;

    public RuleJump(String dest) {
        this.dest = dest;
    }

    @Override
    public String accept(Map<String, Integer> map) {
        return dest;
    }
}
