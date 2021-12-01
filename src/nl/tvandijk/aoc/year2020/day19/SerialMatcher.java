package nl.tvandijk.aoc.year2020.day19;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SerialMatcher implements Matcher {
    final Matcher[] elements;

    public SerialMatcher(Matcher... elements) {
        this.elements = Arrays.stream(elements)
                .flatMap(x -> x instanceof SerialMatcher ? Stream.of(((SerialMatcher) x).elements) : Stream.of(x))
                .toArray(Matcher[]::new);
    }

    @Override
    public Set<String> match(String s, Map<Integer, Matcher> rules) {
        if (s.equals("")) return Set.of();
        var res = Set.of(s);
        for (var el : elements) {
            res = res.stream().map(x -> el.match(x, rules)).flatMap(Set::stream).collect(Collectors.toSet());
        }
        return res;
    }

    @Override
    public String toString() {
        return "seq(" + Arrays.stream(elements).map(Matcher::toString).collect(Collectors.joining(", ")) + ")";
    }
}
