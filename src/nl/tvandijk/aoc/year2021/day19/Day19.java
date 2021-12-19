package nl.tvandijk.aoc.year2021.day19;

import nl.tvandijk.aoc.common.Day;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.*;

public class Day19 extends Day {
    private static class Pos3 {
        int x;
        int y;
        int z;

        public Pos3(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Pos3(Pos3 one, Pos3 two) {
            this(one.x+two.x, one.y+two.y, one.z+two.z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos3 pos3 = (Pos3) o;
            return x == pos3.x && y == pos3.y && z == pos3.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    private static class Align {
        Scanner sc;
        int rot;
        boolean dirx;
        boolean diry;
        boolean dirz;

        public Align(Scanner sc, int rot, boolean dirx, boolean diry, boolean dirz) {
            this.sc = sc;
            this.rot = rot;
            this.dirx = dirx;
            this.diry = diry;
            this.dirz = dirz;
        }

        public int getCount() {
            return sc.pos.size();
        }

        public Pos3 get(int n) {
            // xyz, xzy, yxz, yzx, zyx, zxy ??
            var pos = sc.pos;
            if (rot == 0) {
                var px = pos.get(n).x;
                var py = pos.get(n).y;
                var pz = pos.get(n).z;
                return new Pos3(dirx?-px:px, diry?-py:py, dirz?-pz:pz);
            } else if (rot == 1) {
                // swap xyz -> zyx
                var px = pos.get(n).x;
                var py = pos.get(n).z;
                var pz = pos.get(n).y;
                return new Pos3(dirx?-px:px, diry?-py:py, dirz?-pz:pz);
            } else if (rot == 2) {
                // swap xyz -> yxz
                var px = pos.get(n).y;
                var py = pos.get(n).x;
                var pz = pos.get(n).z;
                return new Pos3(dirx?-px:px, diry?-py:py, dirz?-pz:pz);
            } else if (rot == 3){
                // swap xyz -> xzy
                var px = pos.get(n).y;
                var py = pos.get(n).z;
                var pz = pos.get(n).x;
                return new Pos3(dirx?-px:px, diry?-py:py, dirz?-pz:pz);
            } else if (rot == 4) {
                var px = pos.get(n).z;
                var py = pos.get(n).x;
                var pz = pos.get(n).y;
                return new Pos3(dirx?-px:px, diry?-py:py, dirz?-pz:pz);
            } else {
                var px = pos.get(n).z;
                var py = pos.get(n).y;
                var pz = pos.get(n).x;
                return new Pos3(dirx?-px:px, diry?-py:py, dirz?-pz:pz);
            }
        }
    }

    private static class Scanner {
        int n;

        List<Pos3> pos = new ArrayList<>();

        public Scanner(int n) {
            this.n = n;
        }

        public Scanner(Scanner sc, int rot, boolean dirx, boolean diry, boolean dirz) {
            var a = new Align(sc, rot, dirx, diry, dirz);
            for (int i = 0; i < a.getCount(); i++) {
                pos.add(a.get(i));
            }
        }

        public Pos3 match(Align a) {
            Set<Pos3> rels = new HashSet<>();
            for (int i = 0; i < pos.size(); i++) {
                for (int j = 0; j < a.getCount(); j++) {
                    // REL
                    var mine = pos.get(i);
                    var their = a.get(j);
                    rels.add(new Pos3(their.x - mine.x, their.y - mine.y, their.z - mine.z));
                }
            }
            for (var rel : rels) {
                int relX = rel.x;
                int relY = rel.y;
                int relZ = rel.z;

                int count = 0;
                for (int k = 0; k < pos.size(); k++) {
                    for (int l = 0; l < a.getCount(); l++) {
                        var m = pos.get(k);
                        var n = a.get(l);
                        if (relX + m.x == n.x &&
                            relY + m.y == n.y &&
                            relZ + m.z == n.z) {
                            count++;
                            break;
                        }
                    }
                }
                if (count >= 12) {
                    System.out.printf("FOUND with rel %d %d %d , ", relX, relY, relZ);
                    System.out.printf("alignment %d %b %b %b%n", a.rot, a.dirx, a.diry, a.dirz);

                    for (int l = 0; l < a.getCount(); l++) {
                        var n = a.get(l);
                        n.x -= relX;
                        n.y -= relY;
                        n.z -= relZ;
                        if (!pos.contains(n)) pos.add(n);
                    }

                    return new Pos3(relX, relY, relZ);
                }
            }

            return null;
        }

        private boolean canMatch(Scanner other) {
            Set<Pos3> rels = new HashSet<>();
            for (int i = 0; i < pos.size(); i++) {
                for (int j = 0; j < other.pos.size(); j++) {
                    // REL
                    var mine = pos.get(i);
                    var their = other.pos.get(j);
                    rels.add(new Pos3(their.x - mine.x, their.y - mine.y, their.z - mine.z));
                }
            }
            for (var rel : rels) {
                int relX = rel.x;
                int relY = rel.y;
                int relZ = rel.z;

                int count = 0;
                for (int k = 0; k < pos.size(); k++) {
                    for (int l = 0; l < other.pos.size(); l++) {
                        var m = pos.get(k);
                        var n = other.pos.get(l);
                        if (relX + m.x == n.x &&
                                relY + m.y == n.y &&
                                relZ + m.z == n.z) {
                            count++;
                            break;
                        }
                    }
                }
                if (count >= 12) return true;
            }
            return false;
        }

        public Scanner test(Scanner other) {
            Scanner sc;

            for (int i = 0; i < 6; i++) {
                sc = new Scanner(other, i, false, false, false);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, false, false, true);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, false, true, false);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, false, true, true);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, true, false, false);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, true, false, true);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, true, true, false);
                if (canMatch(sc)) return sc;
                sc = new Scanner(other, i, true, true, true);
                if (canMatch(sc)) return sc;
            }

            return null;
        }

        public Pos3 extend(Scanner other) {
            // try to match scanners 0 and 1
            Pos3 res;
            for (int i = 0; i < 6; i++) {
                res = match(new Align(other, i, false, false, false));
                if (res != null) return res;
                res = match(new Align(other, i, false, false, true));
                if (res != null) return res;
                res = match(new Align(other, i, false, true, false));
                if (res != null) return res;
                res = match(new Align(other, i, false, true, true));
                if (res != null) return res;
                res = match(new Align(other, i, true, false, false));
                if (res != null) return res;
                res = match(new Align(other, i, true, false, true));
                if (res != null) return res;
                res = match(new Align(other, i, true, true, false));
                if (res != null) return res;
                res = match(new Align(other, i, true, true, true));
                if (res != null) return res;
            }
            return null;
        }

        @Override
        public String toString() {
            var sb = new StringBuilder();
            for (var p : pos) {
                sb.append(String.format("%d,%d,%d%n", p.x, p.y, p.z));
            }
            return sb.toString();
        }
    }

    @Override
    protected void part1(String fileContents) throws IOException {
        List<Scanner> scanners = new ArrayList<>();
        Scanner curSc = new Scanner(-1);

        for (var line : fileContents.split("[\r\n]+")) {
            if (line.startsWith("---")) {
                // new scanner
                curSc = new Scanner(Integer.parseInt(line.split(" ")[2]));
                scanners.add(curSc);
            } else {
                var a = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                curSc.pos.add(new Pos3(a[0], a[1], a[2]));
            }
        }

        // first find pairs
        Map<Scanner, List<Scanner>> pairs = new HashMap<>();
        for (var sc : scanners) pairs.put(sc, new ArrayList<>());
        for (int i = 0, scannersSize = scanners.size(); i < scannersSize; i++) {
            Scanner sc1 = scanners.get(i);
            for (int j = i+1, size = scanners.size(); j < size; j++) {
                Scanner sc2 = scanners.get(j);
                if (sc1.test(sc2) != null) {
                    System.out.printf("Match between %d and %d%n", sc1.n, sc2.n);
                    pairs.get(sc1).add(sc2);
                    pairs.get(sc2).add(sc1);
                }
            }
        }

        Set<Scanner> remain = new HashSet<>(scanners);
        Queue<Scanner> frontier = new ArrayDeque<>();
        frontier.add(scanners.get(0));

        List<Pos3> locs = new ArrayList<>();

        Scanner result = scanners.get(0);
        while (!frontier.isEmpty()) {
            var o = frontier.poll();
            for (var s : pairs.get(o)) {
                if (!remain.contains(s)) continue;

                var res = result.extend(s);
                if (res != null) {
                    remain.remove(s);
                    frontier.add(s);
                    locs.add(res);
                    System.out.printf("%d remain!%n", remain.size());
                } else {
                    System.out.println("BAD");
                }
            }
        }

        System.out.println("Part 1: " + result.pos.size());

        int maxDist = Integer.MIN_VALUE;
        for (int i = 0; i < locs.size(); i++) {
            for (int j = 0; j < locs.size(); j++) {
                var one = locs.get(i);
                var two = locs.get(j);
                var d = Math.abs(one.x-two.x) + Math.abs(one.y-two.y) + Math.abs(one.z-two.z);
                maxDist = Math.max(maxDist, d);
            }
        }

        System.out.println("Part 2: " + maxDist);
    }

    @Override
    protected void part2(String fileContents) throws IOException {
    }

    public static void main(String[] args) {
        run(Day19::new, "example.txt", "input.txt");
    }
}
