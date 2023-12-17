package nl.tvandijk.aoc.year2023.day17;

import java.util.*;
import java.util.stream.Collectors;

import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day17 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    private record State(Point pos, int dir, int steps) {
    }

    private long loss(Point p) {
        return grid.get(p) - '0';
    }

    @Override
    protected Object part1() {
        // part 1
        Graph<State> graph = new Graph<>(state -> this.successors(state, 0, 3));
        var initial = new State(Point.of(0, 0), -1, 0);
        var res = graph.reachAll(List.of(initial));
        return res.entrySet().stream()
                .filter(e -> e.getKey().pos.equals(Point.of(grid.width() - 1L, grid.height() - 1L)))
                .mapToLong(Map.Entry::getValue).min().orElseThrow();
    }

    private Collection<Pair<State, Long>> successors(State state, int min, int max) {
        var next = new ArrayList<Pair<State, Long>>();
        if (state.pos.x < 0 || state.pos.y < 0) return next;
        if (state.pos.x >= grid.width() || state.pos.y >= grid.height()) return next;
        if (state.steps >= min || state.dir == -1) {
            if (state.dir == -1 || state.dir == 0 || state.dir == 1) {
                next.add(Pair.of(new State(state.pos.down(), 2, 1), loss(state.pos.down())));
                next.add(Pair.of(new State(state.pos.up(), 3, 1), loss(state.pos.up())));
            }
            if (state.dir == -1 || state.dir == 2 || state.dir == 3) {
                next.add(Pair.of(new State(state.pos.right(), 0, 1), loss(state.pos.right())));
                next.add(Pair.of(new State(state.pos.left(), 1, 1), loss(state.pos.left())));
            }
        }
        if (state.steps < max) {
            if (state.dir == 0) {
                next.add(Pair.of(new State(state.pos.right(), 0, state.steps + 1), loss(state.pos.right())));
            }
            if (state.dir == 1) {
                next.add(Pair.of(new State(state.pos.left(), 1, state.steps + 1), loss(state.pos.left())));
            }
            if (state.dir == 2) {
                next.add(Pair.of(new State(state.pos.down(), 2, state.steps + 1), loss(state.pos.down())));
            }
            if (state.dir == 3) {
                next.add(Pair.of(new State(state.pos.up(), 3, state.steps + 1), loss(state.pos.up())));
            }
        }
        return next;
    }

    @Override
    protected Object part2() {
        // part 2
        Graph<State> graph = new Graph<>(state -> this.successors(state, 4, 10));
        var initial = new State(Point.of(0, 0), -1, 0);
        var res = graph.reachAll(List.of(initial));
        return res.entrySet().stream()
                .filter(e -> e.getKey().pos.equals(Point.of(grid.width() - 1L, grid.height() - 1L)))
                .filter(e -> e.getKey().steps >= 4)
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
