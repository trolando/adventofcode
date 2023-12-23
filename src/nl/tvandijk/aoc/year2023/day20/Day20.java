package nl.tvandijk.aoc.year2023.day20;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day20 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
        for (var line : lines) {
            var parts = line.split(" -> ");
            String key;
            if (parts[0].charAt(0) == '%') {
                flipflops.put(parts[0].substring(1), 0); // initially off
                key = parts[0].substring(1);
            } else if (parts[0].charAt(0) == '&') {
                // conj
                cons.put(parts[0].substring(1), new HashMap<>());
                key = parts[0].substring(1);
            } else {
                key = parts[0];
            }
            if (parts.length == 2) {
                var set = connections.computeIfAbsent(key, l -> new HashSet<>());
                for (var dest : parts[1].split(", ")) {
                    dest = dest.trim();
                    set.add(dest);
                }
            }
        }
        for (var entry : connections.entrySet()) {
            for (var dest : entry.getValue()) {
                if (cons.containsKey(dest)) {
                    cons.get(dest).put(entry.getKey(), 0); // initially low
                }
            }
        }
    }

    @Override
    protected Object part1() {
        // part 1
        long countLow = 0L;
        long countHigh = 0L;
        Deque<Triple<String, String, Integer>> pulses = new ArrayDeque<>();
        for (int i = 0; i < 1000; i++) {
            // button first!
            pulses.add(Triple.of("button", "broadcaster", 0));
            while (!pulses.isEmpty()) {
                var pulse = pulses.pop();
                if (pulse.c == 1) countHigh++;
                else countLow++;
                if (flipflops.containsKey(pulse.b)) {
                    if (pulse.c == 1) continue; // ignore
                    int newval = flipflops.compute(pulse.b, (k, v) -> 1 - v);
                    if (newval == 1) {
                        // send high pulse
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 1));
                        }
                    } else {
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 0));
                        }
                    }
                } else if (cons.containsKey(pulse.b)) {
                    var vals = cons.get(pulse.b);
                    vals.put(pulse.a, pulse.c);
                    if (vals.values().stream().allMatch(a -> a == 1)) {
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 0));
                        }
                    } else {
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 1));
                        }
                    }
                } else if (connections.containsKey(pulse.b)) {
                    // broadcaster
                    for (var dest : connections.get(pulse.b)) {
                        pulses.add(Triple.of(pulse.b, dest, pulse.c));
                    }
                }
            }
        }

        return countHigh*countLow;
    }

    Map<String, Integer> flipflops = new HashMap<>();
    Map<String, Map<String, Integer>> cons = new HashMap<>();
    Map<String, Set<String>> connections = new HashMap<>();

    @Override
    protected Object part2() {
        // part 2
        Map<String, List<Integer>> history = new HashMap<>();

        Deque<Triple<String, String, Integer>> pulses = new ArrayDeque<>();
        for (int i = 0; i < 10000; i++) {
            pulses.add(Triple.of("button", "broadcaster", 0));
            while (!pulses.isEmpty()) {
                var pulse = pulses.pop();
                if (flipflops.containsKey(pulse.b)) {
                    if (pulse.c == 1) continue; // ignore
                    int newval = flipflops.compute(pulse.b, (k, v) -> 1 - v);
                    if (newval == 1) {
                        // send high pulse
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 1));
                        }
                    } else {
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 0));
                        }
                    }
                } else if (cons.containsKey(pulse.b)) {
                    var vals = cons.get(pulse.b);
                    vals.put(pulse.a, pulse.c);
                    if (vals.values().stream().allMatch(a -> a == 1)) {
                        history.computeIfAbsent(pulse.b, x -> new ArrayList<>()).add(i);
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 0));
                        }
                    } else {
                        for (var dest : connections.get(pulse.b)) {
                            pulses.add(Triple.of(pulse.b, dest, 1));
                        }
                    }
                } else if (connections.containsKey(pulse.b)) {
                    // broadcaster
                    for (var dest : connections.get(pulse.b)) {
                        pulses.add(Triple.of(pulse.b, dest, pulse.c));
                    }
                }
            }
        }

        var crt = new ChineseRemainderTheorem();
        for (var ff : cons.entrySet()) {
            if (history.containsKey(ff.getKey())) {
                var val = history.get(ff.getKey());
                if (!val.isEmpty() && val.getFirst() > 0) crt.addEquation(0, val.getFirst() + 1);
            }
        }
        var solve = crt.solve();
        return solve[0]+solve[1];
    }

    /**
     * Default construct initializes inputs to example.txt and input.txt
     */
    public Day20() {
        super("input.txt");
    }
}
