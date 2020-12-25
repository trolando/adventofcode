package nl.tvandijk.aoc.day19;

import java.util.Map;
import java.util.Set;

public interface Matcher {
    Set<String> match(String s, Map<Integer, Matcher> rules); // return all substrings after matching s
}
