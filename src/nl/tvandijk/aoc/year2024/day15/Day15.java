package nl.tvandijk.aoc.year2024.day15;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.*;

public class Day15 extends Day {
    Point move(Point pt, Direction dir) {
        var x = grid.get(pt.to(dir));
        if (x == '#') return pt;
        if (x != '.') {
            move(pt.to(dir), dir);
            x = grid.get(pt.to(dir));
        }
        if (x == '.') {
            grid.set(pt.to(dir), grid.get(pt));
            grid.set(pt, '.');
            return pt.to(dir);
        } else {
            return pt;
        }
    }

    boolean canMoveW(Point pt, Direction dir) {
        var x = grid.get(pt.to(dir));
        if (x == '#') return false;
        else if (x == '.') return true;
        else if (dir == Direction.LEFT || dir == Direction.RIGHT) return canMoveW(pt.to(dir), dir);
        else if (x == '[') return canMoveW(pt.to(dir), dir) && canMoveW(pt.to(dir).to(Direction.RIGHT), dir);
        else if (x == ']') return canMoveW(pt.to(dir), dir) && canMoveW(pt.to(dir).to(Direction.LEFT), dir);
        return false;
    }

    void moveW(Point pt, Direction dir) {
        var x = grid.get(pt.to(dir));
        if (x == '#') {
            return;
        } else if (x == '.') {
            grid.set(pt.to(dir), grid.get(pt));
            grid.set(pt, '.');
        }
        else if (dir == Direction.LEFT || dir == Direction.RIGHT) {
            moveW(pt.to(dir), dir);
            grid.set(pt.to(dir), grid.get(pt));
            grid.set(pt, '.');
        }
        else if (x == '[') {
            moveW(pt.to(dir), dir);
            moveW(pt.to(dir).to(Direction.RIGHT), dir);
            grid.set(pt.to(dir), grid.get(pt));
            grid.set(pt, '.');
        }
        else if (x == ']') {
            moveW(pt.to(dir), dir);
            moveW(pt.to(dir).to(Direction.LEFT), dir);
            grid.set(pt.to(dir), grid.get(pt));
            grid.set(pt, '.');
        }
    }

    @Override
    protected Object part1() {
        // part 1
        var blocks = fileContents.split("\n\n");
        this.grid = Grid.of(blocks[0].split("\n"), '#');
        var pos = grid.findOne('@');
        for (var line : blocks[1].split("\n")) {
            for (var ch : line.trim().toCharArray()) {
                Direction dir = switch (ch) {
                    case '<' -> Direction.LEFT;
                    case '>' -> Direction.RIGHT;
                    case 'v' -> Direction.DOWN;
                    case '^' -> Direction.UP;
                    default -> null;
                };
                pos = move(pos, dir);
            }
        }
        long sum = 0;
        for (var pt : grid.findAll('O')) {
            sum += pt.x + pt.y * 100;
        }
        return sum;
    }

    String widen(String orig) {
        StringBuilder sb = new StringBuilder();
        for (char c : orig.toCharArray()) {
            if (c == '#') sb.append("##");
            if (c == '.') sb.append("..");
            if (c == 'O') sb.append("[]");
            if (c == '@') sb.append("@.");
        }
        return sb.toString();
    }

    @Override
    protected Object part2() {
        // part 2
        var blocks = fileContents.split("\n\n");
        var gl = blocks[0].split("\n");
        gl = Arrays.stream(gl).map(this::widen).toArray(String[]::new);
        this.grid = Grid.of(gl, '#');
        var pos = grid.findOne('@');
        System.out.println(grid.toString());
        for (var line : blocks[1].split("\n")) {
            for (var ch : line.trim().toCharArray()) {
                Direction dir = switch (ch) {
                    case '<' -> Direction.LEFT;
                    case '>' -> Direction.RIGHT;
                    case 'v' -> Direction.DOWN;
                    case '^' -> Direction.UP;
                    default -> null;
                };
                if (canMoveW(pos, dir)) {
                    moveW(pos, dir);
                    pos = pos.to(dir);
                }
//                System.out.println("After " + ch);
//                System.out.println(grid.toString());
            }
        }
        long sum = 0;
        for (var pt : grid.findAll('[')) {
            sum += pt.x + pt.y * 100;
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Day.main(args);
    }

    public Day15() {
//        super("example.txt");
        super("example.txt", "input.txt");
    }
}
