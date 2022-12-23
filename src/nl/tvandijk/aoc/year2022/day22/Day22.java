package nl.tvandijk.aoc.year2022.day22;

import java.util.*;
import nl.tvandijk.aoc.common.Day;
import nl.tvandijk.aoc.util.Pair;
import nl.tvandijk.aoc.util.Point;
import nl.tvandijk.aoc.util.Point3;

public class Day22 extends Day {
    @Override
    protected void processInput(String fileContents) {
        // process the input
        super.processInput(fileContents);
    }

    Map<Point, Integer> grid = new HashMap<>();

    @Override
    protected Object part1() {
        // part 1
        var first = fileContents.split("\n\n");
        int y = 1;
        Point pos = null;
        int dir = 1; // north east south west
        for (var line : first[0].split("\n")) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '.') {
                    grid.put(Point.of(i+1, y), 0);
                    if (y == 1 && pos == null) pos = Point.of(i+1, y);
                } else if (line.charAt(i) == '#') {
                    grid.put(Point.of(i+1, y), 1);
                }
            }
            y++;
        }
        var inst = first[1].split("\n")[0] + "-"; // add end
        var count = 0;
        for (int i = 0; i < inst.length(); i++) {
            if (Character.isDigit(inst.charAt(i))) {
                count *= 10;
                count += inst.charAt(i)-'0';
            } else {
//                System.out.printf("%d%c", count, inst.charAt(i));
//                System.out.printf("moving %d then %c%n", count, inst.charAt(i));
                // move forward
                int dx = dir == 1 ? 1 : dir == 3 ? -1 : 0;
                int dy = dir == 0 ? -1 : dir == 2 ? 1 : 0;
                for (int j = 0; j < count; j++) {
                    var next = pos.delta(dx, dy);
                    var k = grid.get(next);
                    if (k == null) {
                        // wrap!
                        next = pos.delta(-dx, -dy);
                        while (grid.containsKey(next)) {
                            next = next.delta(-dx, -dy);
                        }
                        next = next.delta(dx, dy);
                        k = grid.get(next);
                    }
                    if (k == 0) pos = next;
                }
                count = 0;
                if (inst.charAt(i) == 'L') dir = (dir+3)%4;
                else if (inst.charAt(i) == 'R') dir = (dir+1)%4;
//                else System.out.println("unknown direction " + inst.charAt(i));
//                System.out.printf("Current location: %d, %d%n", pos.x, pos.y);
            }
        }
        return 1000*pos.y+4*pos.x+((dir+3)%4);
    }

    int size;

    enum Faces {
        FRONT,
        BACK,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    @Override
    protected Object part2() {
        // part 2
        var first = fileContents.split("\n\n");
        int y = 0;
        Point pos = null;
        grid.clear();
        for (var line : first[0].split("\n")) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '.') {
                    grid.put(Point.of(i, y), 0);
                    if (y == 0 && pos == null) pos = Point.of(i, y);
                } else if (line.charAt(i) == '#') {
                    grid.put(Point.of(i, y), 1);
                    if (y == 0 && pos == null) pos = Point.of(i, y);
                }
            }
            y++;
        }
        size = (int)Math.sqrt(grid.size()/6);

        Map<Point, Point> rots = new HashMap<>();
        rots.put(Point.of(0, 1), Point.of(-1, 0));
        rots.put(Point.of(-1, 0), Point.of(0, -1));
        rots.put(Point.of(0, -1), Point.of(1, 0));
        rots.put(Point.of(1, 0), Point.of(0, 1));

        Map<Pair<Faces,Faces>, Faces> rots3 = new HashMap<>();
        rots3.put(Pair.of(Faces.FRONT, Faces.UP), Faces.RIGHT);
        rots3.put(Pair.of(Faces.FRONT, Faces.RIGHT), Faces.DOWN);
        rots3.put(Pair.of(Faces.FRONT, Faces.DOWN), Faces.LEFT);
        rots3.put(Pair.of(Faces.FRONT, Faces.LEFT), Faces.UP);
        rots3.put(Pair.of(Faces.BACK, Faces.UP), Faces.LEFT);
        rots3.put(Pair.of(Faces.BACK, Faces.LEFT), Faces.DOWN);
        rots3.put(Pair.of(Faces.BACK, Faces.DOWN), Faces.RIGHT);
        rots3.put(Pair.of(Faces.BACK, Faces.RIGHT), Faces.UP);
        rots3.put(Pair.of(Faces.RIGHT, Faces.UP), Faces.BACK);
        rots3.put(Pair.of(Faces.RIGHT, Faces.BACK), Faces.DOWN);
        rots3.put(Pair.of(Faces.RIGHT, Faces.DOWN), Faces.FRONT);
        rots3.put(Pair.of(Faces.RIGHT, Faces.FRONT), Faces.UP);
        rots3.put(Pair.of(Faces.LEFT, Faces.UP), Faces.FRONT);
        rots3.put(Pair.of(Faces.LEFT, Faces.FRONT), Faces.DOWN);
        rots3.put(Pair.of(Faces.LEFT, Faces.DOWN), Faces.BACK);
        rots3.put(Pair.of(Faces.LEFT, Faces.BACK), Faces.UP);
        rots3.put(Pair.of(Faces.UP, Faces.FRONT), Faces.LEFT);
        rots3.put(Pair.of(Faces.UP, Faces.LEFT), Faces.BACK);
        rots3.put(Pair.of(Faces.UP, Faces.BACK), Faces.RIGHT);
        rots3.put(Pair.of(Faces.UP, Faces.RIGHT), Faces.FRONT);
        rots3.put(Pair.of(Faces.DOWN, Faces.FRONT), Faces.RIGHT);
        rots3.put(Pair.of(Faces.DOWN, Faces.RIGHT), Faces.BACK);
        rots3.put(Pair.of(Faces.DOWN, Faces.BACK), Faces.LEFT);
        rots3.put(Pair.of(Faces.DOWN, Faces.LEFT), Faces.FRONT);

        Map<Faces, Point3> deltas = new HashMap<>();
        deltas.put(Faces.UP, Point3.of(0, -1,0 ));
        deltas.put(Faces.RIGHT, Point3.of(1, 0, 0));
        deltas.put(Faces.DOWN, Point3.of(0, 1, 0));
        deltas.put(Faces.LEFT, Point3.of(-1, 0, 0));
        deltas.put(Faces.FRONT, Point3.of(0, 0, -1));
        deltas.put(Faces.BACK, Point3.of(0, 0, 1));

        record Place (Point3 loc, Pair<Faces,Faces> orient) {}

        Map<Point, Place> mapping = new HashMap<>();
        mapping.put(pos, new Place(Point3.of(0, 0, 0), Pair.of(Faces.FRONT, Faces.UP)));
        Queue<Point> q = new ArrayDeque<>();
        q.add(pos);
        while (!q.isEmpty()) {
            var p = q.remove();
            var p3 = mapping.get(p);
            var or2 = Point.of(0, -1); // 2d north
            var or3 = p3.orient; // 3d facing
            for (int i = 0; i < 4; i++) {
                var p2 = p.delta(or2.x, or2.y);
                if (grid.containsKey(p2) && !mapping.containsKey(p2)) {
                    Place pt;
                    var newLoc = p3.loc.delta(deltas.get(or3.b));
                    // check if fold...
                    if (newLoc.x < 0 || newLoc.x >= size || newLoc.y < 0 || newLoc.y >= size || newLoc.z < 0 || newLoc.z >= size) {
                        Faces newFace = or3.b;
                        Faces newDir = switch (or3.a) {
                            case UP -> Faces.DOWN;
                            case DOWN -> Faces.UP;
                            case LEFT -> Faces.RIGHT;
                            case RIGHT -> Faces.LEFT;
                            case FRONT -> Faces.BACK;
                            case BACK -> Faces.FRONT;
                        };
                        // undo rotation
                        for (int j = 0; j < 4-i; j++) {
                            newDir = rots3.get(Pair.of(newFace, newDir));
                        }
                        pt = new Place(p3.loc, Pair.of(newFace, newDir));
                    } else {
                        pt = new Place(newLoc, p3.orient);
                    }
//                    System.out.printf("Mapping (%d,%d) to (%d,%d,%d) plane %s north=%s%n", p2.x, p2.y, pt.loc.x, pt.loc.y, pt.loc.z, pt.orient.a, pt.orient.b);
                    mapping.put(p2, pt);
                    q.add(p2);
                }
                or2 = rots.get(or2);
                or3 = Pair.of(or3.a, rots3.get(or3));
            }
        }

        record Surface (Point3 loc, Faces face) {}
        Map<Surface, Integer> walls = new HashMap<>();
        for (var entry : mapping.entrySet()) {
            walls.put(new Surface(entry.getValue().loc, entry.getValue().orient.a), grid.get(entry.getKey()));
        }

        Surface loc = new Surface(Point3.of(0,0,0), Faces.FRONT);
        Faces dir = Faces.RIGHT;

        var inst = first[1].split("\n")[0] + "-"; // add end
        var count = 0;
        for (int i = 0; i < inst.length(); i++) {
            if (Character.isDigit(inst.charAt(i))) {
                count *= 10;
                count += inst.charAt(i)-'0';
            } else {
                // move forward
                for (int j = 0; j < count; j++) {
                    var nextLoc = new Surface(loc.loc.delta(deltas.get(dir)), loc.face);
                    var nextDir = dir;
                    if (!walls.containsKey(nextLoc)) {
                        // flip
                        nextLoc = new Surface(loc.loc, dir);
                        nextDir = switch (loc.face) {
                            case UP -> Faces.DOWN;
                            case DOWN -> Faces.UP;
                            case LEFT -> Faces.RIGHT;
                            case RIGHT -> Faces.LEFT;
                            case FRONT -> Faces.BACK;
                            case BACK -> Faces.FRONT;
                        };
                    }
                    var k = walls.get(nextLoc);
                    if (k == null) {
                        System.out.println("ERROR");
                    }
                    if (k == 1) break; // can't proceed
                    loc = nextLoc;
                    dir = nextDir;
//                    System.out.printf("Current location: (%d,%d,%d) face %s towards %s%n", loc.loc.x, loc.loc.y, loc.loc.z, loc.face, dir);
                }
                count = 0;
                if (inst.charAt(i) == 'L') {
                    for (int j = 0; j < 3; j++) {
                        dir = rots3.get(Pair.of(loc.face, dir));
                    }
                }
                else if (inst.charAt(i) == 'R') {
                    dir = rots3.get(Pair.of(loc.face, dir));
                }
//                System.out.printf("Current location: (%d,%d,%d) face %s towards %s%n", loc.loc.x, loc.loc.y, loc.loc.z, loc.face, dir);
            }
        }

        for (var entry : mapping.entrySet()) {
            var p3 = entry.getValue();
            if (p3.loc.equals(loc.loc) && p3.orient.a.equals(loc.face)) {
                var p2 = entry.getKey();
//                System.out.printf("We are at (%d,%d)%n", p2.x, p2.y);
                // translate <dir> ...
                var d = p3.orient.b; // north
                d = rots3.get(Pair.of(loc.face, d)); // right
                int i = 0;
                while (d != dir) {
                    i++;
                    d = rots3.get(Pair.of(loc.face, d));
                }
//                System.out.printf("We are facing %d%n", i);
                return 1000*(p2.y+1) + 4*(p2.x+1) + i;
            }
        }

        return null;
    }
}
