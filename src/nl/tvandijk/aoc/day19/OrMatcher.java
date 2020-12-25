package nl.tvandijk.aoc.day19;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrMatcher implements Matcher {
    final Matcher[] elements;

    public OrMatcher(Matcher... elements) {
        this.elements = Arrays.stream(elements)
                .flatMap(x -> x instanceof OrMatcher ? Stream.of(((OrMatcher) x).elements) : Stream.of(x))
                .toArray(Matcher[]::new);
    }

    @Override
    public Set<String> match(String s, Map<Integer, Matcher> rules) {
        if (s.equals("")) return Set.of();
        Set<String> res = new HashSet<>();
        for (var element : elements) {
            res.addAll(element.match(s, rules));
        }
        return res;
    }

    @Override
    public String toString() {
        return "or(" + Arrays.stream(elements).map(Matcher::toString).collect(Collectors.joining(", ")) + ")";
    }
}
