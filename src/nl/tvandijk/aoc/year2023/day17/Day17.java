package nl.tvandijk.aoc.year2023.day17;

import java.util.*;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

import static nl.tvandijk.aoc.util.Direction.*;

public class Day17 extends Day {
    private record State(Point pos, Direction dir, int runtime) {
    }

    private long loss(Point p) {
        return grid.get(p) - '0';
    }

    private Collection<Pair<State, Long>> successors(State state, int min, int max) {
        var next = new ArrayList<Pair<State, Long>>();
        if (state.pos.x < 0 || state.pos.y < 0) return next;
        if (state.pos.x >= grid.width() || state.pos.y >= grid.height()) return next;
        if (state.runtime >= min || state.dir == null) {
            if (state.dir == null || state.dir == RIGHT || state.dir == LEFT) {
                next.add(Pair.of(new State(state.pos.down(), DOWN, 1), loss(state.pos.down())));
                next.add(Pair.of(new State(state.pos.up(), UP, 1), loss(state.pos.up())));
            }
            if (state.dir == null || state.dir == UP || state.dir == DOWN) {
                next.add(Pair.of(new State(state.pos.right(), RIGHT, 1), loss(state.pos.right())));
                next.add(Pair.of(new State(state.pos.left(), LEFT, 1), loss(state.pos.left())));
            }
        }
        if (state.runtime < max && state.dir != null) {
            next.add(Pair.of(new State(state.pos.to(state.dir), state.dir, state.runtime + 1), loss(state.pos.to(state.dir))));
        }
        return next;
    }

    @Override
    protected Object part1() {
        // part 1
        Graph<State> graph = new Graph<>(state -> this.successors(state, 0, 3));
        var initial = new State(Point.of(0, 0), null, 0);
        var res = graph.reachAny(List.of(initial), state -> state.pos.equals(Point.of(grid.width() - 1L, grid.height() - 1L)));
        return res.a.entrySet().stream()
                .filter(e -> e.getKey().pos.equals(Point.of(grid.width() - 1L, grid.height() - 1L)))
                .mapToLong(Map.Entry::getValue).min().orElseThrow();
    }

    @Override
    protected Object part2() {
        // part 2
        Graph<State> graph = new Graph<>(state -> this.successors(state, 4, 10));
        var initial = new State(Point.of(0, 0), null, 0);
        var res = graph.reachAny(List.of(initial), state -> state.pos.equals(Point.of(grid.width() - 1L, grid.height() - 1L)) && state.runtime >= 4);
        return res.a.entrySet().stream()
                .filter(e -> e.getKey().pos.equals(Point.of(grid.width() - 1L, grid.height() - 1L)))
                .filter(e -> e.getKey().runtime >= 4)
                .mapToLong(Map.Entry::getValue)
                .min().orElseThrow();
    }

    /**
     * Default construct initializes inputs to example.txt and input.txt
     */
    public Day17() {
        super("example.txt", "example2.txt", "input.txt");
    }
}
