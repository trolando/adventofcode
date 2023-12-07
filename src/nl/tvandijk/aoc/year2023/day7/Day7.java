package nl.tvandijk.aoc.year2023.day7;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day7 extends Day {
    int kindCard(String card) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : card.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int[] counts = map.values().stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(counts);
        if (counts.length == 1) return 1;
        if (counts[1] == 4) return 2;
        if (counts[1] == 3) return 3;
        if (counts[2] == 3) return 4;
        if (counts.length == 3) return 5;
        if (counts.length == 4) return 6;
        if (counts.length == 5) return 7;
        return -1; // should not happen
    }

    int compare(String a, String b) {
        int typeA = kindCard(a);
        int typeB = kindCard(b);
        if (typeA > typeB) return -1; // b is stronger
        if (typeA < typeB) return 1;
        for (int i = 0; i < 5; i++) {
            int idx1 = "23456789TJQKA".indexOf(a.charAt(i));
            int idx2 = "23456789TJQKA".indexOf(b.charAt(i));
            if (idx1 < idx2) return -1; // b is stronger
            if (idx1 > idx2) return 1;
        }
        return 0; // same strength
    }

    @Override
    protected Object part1() {
        // part 1
        List<Pair<String, Integer>> cards = new ArrayList<>();
        for (var l : lines) {
            var s = l.split("\\s+");
            cards.add(Pair.of(s[0], Integer.parseInt(s[1])));
        }
        cards.sort((a, b) -> compare(a.a, b.a));
        long s = 0;
        for (int i = 0; i < cards.size(); i++) {
            s += (long) (i + 1) * cards.get(i).b;
        }
        return s;
    }

    int kindCard2(String card) {
        int bestStrength = kindCard(card);
        for (int i = 0; i < "23456789TJQKA".length(); i++) {
            var c = card.replace("J", String.valueOf("23456789TJQKA".charAt(i)));
            if (kindCard(c) < bestStrength) {
                bestStrength = kindCard(c);
            }
        }
        return bestStrength;
    }

    int compare2(String a, String b) {
        int typeA = kindCard2(a);
        int typeB = kindCard2(b);
        if (typeA > typeB) return -1; // b is stronger
        if (typeA < typeB) return 1;
        for (int i = 0; i < 5; i++) {
            int idx1 = "J23456789TQKA".indexOf(a.charAt(i));
            int idx2 = "J23456789TQKA".indexOf(b.charAt(i));
            if (idx1 < idx2) return -1; // b is stronger
            if (idx1 > idx2) return 1;
        }
        return 0; // same strength
    }

    @Override
    protected Object part2() {
        // part 2
        List<Pair<String, Integer>> cards = new ArrayList<>();
        for (var l : lines) {
            var s = l.split("\\s+");
            cards.add(Pair.of(s[0], Integer.parseInt(s[1])));
        }
        cards.sort((a, b) -> compare2(a.a, b.a));
        long s = 0;
        for (int i = 0; i < cards.size(); i++) {
            s += (long) (i + 1) * cards.get(i).b;
        }
        return s;
    }
}
