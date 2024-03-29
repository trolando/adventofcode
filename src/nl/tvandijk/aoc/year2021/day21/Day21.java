package nl.tvandijk.aoc.year2021.day21;

import nl.tvandijk.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day21 extends Day {
    private int die = 1;
    private int rolls = 0;

    private int roll() {
        rolls++;
        int r = die;
        if (die >= 100) die = 0; // turns to 1
        die++;
        return r;
    }

    private Object part1loop(int p1, int p2) {
        die = 1;
        rolls = 0;
        long s1 = 0;
        long s2 = 0;
        while (true) {
            int add = roll() + roll() + roll();
            p1 += add;
            while (p1 > 10) p1 -= 10;
            s1 += p1;
            if (s1 >= 1000) {
                return s2 * rolls;
            }
            add = roll() + roll() + roll();
            p2 += add;
            while (p2 > 10) p2 -= 10;
            s2 += p2;
            if (s2 >= 1000) {
                return s1 * rolls;
            }
        }
    }

    private static class State {
        int p1;
        int p2;
        int s1;
        int s2;

        public State(int p1, int p2, int s1, int s2) {
            this.p1 = p1;
            this.p2 = p2;
            this.s1 = s1;
            this.s2 = s2;
        }

        public State(State other, int pl, int r) {
            if (pl == 0) {
                p1 = other.p1;
                p2 = other.p2;
                s1 = other.s1;
                s2 = other.s2;
                p1 += r;
                if (p1 > 10) p1 -= 10;
                s1 += p1;
            } else {
                p1 = other.p1;
                p2 = other.p2;
                s1 = other.s1;
                s2 = other.s2;
                p2 += r;
                if (p2 > 10) p2 -= 10;
                s2 += p2;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return p1 == state.p1 && p2 == state.p2 && s1 == state.s1 && s2 == state.s2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2, s1, s2);
        }

        public boolean is21() {
            return s1 >= 21 || s2 >= 21;
        }

        @Override
        public String toString() {
            return "State{" +
                    "p1=" + p1 +
                    ", p2=" + p2 +
                    ", s1=" + s1 +
                    ", s2=" + s2 +
                    '}';
        }
    }

    private Object part2loop(int p1, int p2)
    {
        Map<State,Long> states = new HashMap<>();
        states.put(new State(p1, p2, 0, 0), 1L);

        long wins1 = 0;
        long wins2 = 0;

        while (!states.isEmpty()) {
            Map<State,Long> next = new HashMap<>();

            for (var e : states.entrySet()) {
                for (int i = 1; i <= 3; i++) {
                    for (int j = 1; j <= 3; j++) {
                        for (int k = 1; k <= 3; k++) {
                            var a = new State(e.getKey(), 0, i+j+k);
                            if (a.is21()) wins1 += e.getValue();
                            else next.compute(a, (key, value) -> e.getValue() + (value == null ? 0L : value));
                        }
                    }
                }
            }

            states = next;
            next = new HashMap<>();

            for (var e : states.entrySet()) {
                for (int i = 1; i <= 3; i++) {
                    for (int j = 1; j <= 3; j++) {
                        for (int k = 1; k <= 3; k++) {
                            var a = new State(e.getKey(), 1, i+j+k);
                            if (a.is21()) wins2 += e.getValue();
                            else next.compute(a, (key, value) -> e.getValue() + (value == null ? 0L : value));
                        }
                    }
                }
            }

            states = next;
        }

        return Math.max(wins1, wins2);
    }

    private int a;
    private int b;

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        var tokens = lines[0].split("\\s+");
        a = Integer.parseInt(tokens[tokens.length-1]);
        tokens = lines[1].split("\\s+");
        b = Integer.parseInt(tokens[tokens.length-1]);
    }

    @Override
    protected Object part1() {
        return part1loop(a, b);
    }

    @Override
    protected Object part2() {
        return part2loop(a, b);
    }
}
