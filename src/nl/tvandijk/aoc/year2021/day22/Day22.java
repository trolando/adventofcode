package nl.tvandijk.aoc.year2021.day22;

import nl.tvandijk.aoc.common.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Day {
    public static class Point3D {
        long x;
        long y;
        long z;

        public Point3D(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point3D point3D = (Point3D) o;
            return x == point3D.x && y == point3D.y && z == point3D.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Point3D{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    private static class Layer {
        boolean on;
        long xmin;
        long xmax;
        long ymin;
        long ymax;
        long zmin;
        long zmax;

        public Layer(boolean on, long xmin, long xmax, long ymin, long ymax, long zmin, long zmax) {
            this.on = on;
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
            this.zmin = zmin;
            this.zmax = zmax;
        }

        public boolean isWithin(Layer other) {
            return other.xmin <= xmin && other.xmax >= xmax && other.ymin <= ymin && other.ymax >= ymax
                    && other.zmin <= zmin && other.zmax >= zmax;
        }
//
//        public long countOverlap(Layer other) {
//            return Math.max(Math.min(xmax, other.xmax)-Math.max(xmin, other.xmin), 0) *
//                    Math.max(Math.min(ymax, other.ymax)-Math.max(ymin, other.ymin), 0) *
//                    Math.max(Math.min(zmax, other.zmax)-Math.max(zmin, other.zmin), 0);
//        }
//
//        public Layer overlap(Layer other) {
//            return new Layer(on,
//                    Math.max(xmin, other.xmin), Math.min(xmax, other.xmax),
//                    Math.max(ymin, other.ymin), Math.min(ymax, other.ymax),
//                    Math.max(zmin, other.zmin), Math.min(zmax, other.zmax));
//        }

//        public List<Layer> splitX(Layer other) {
//            if (xmax < other.xmin) return List.of(this);
//            if (other.xmax < xmin) return List.of(this);
//            return List.of(new Layer(on, xmin, other.xmin-1, ymin, ymax, zmin, zmax),
//                    new Layer(on, other.xmin, other.xmax, ymin, ymax, zmin, zmax),
//                    new Layer(on, other.xmax+1, xmax, ymin, ymax, zmin, zmax));
//        }

//        public List<Layer> splitY(Layer other) {
//            if (ymax < other.ymin) return List.of(this);
//            if (other.ymax < ymin) return List.of(this);
//            return List.of(new Layer(on, xmin, xmax, ymin, other.ymin-1, zmin, zmax),
//                    new Layer(on, xmin, xmax, other.ymin, other.ymax, zmin, zmax),
//                    new Layer(on, xmin, xmax, other.ymax+1, ymax, zmin, zmax));
//        }

//        public List<Layer> splitZ(Layer other) {
//            if (zmax < other.zmin) return List.of(this);
//            if (other.zmax < zmin) return List.of(this);
//            return List.of(new Layer(on, xmin, xmax, ymin, ymax, zmin, other.zmin-1),
//                    new Layer(on, xmin, xmax, ymin, ymax, other.xmin, other.zmax),
//                    new Layer(on, xmin, xmax, ymin, ymax, other.zmax+1, zmax));
//        }

        public long size() {
            if (xmax < xmin || ymax < ymin || zmax < zmin) return 0L;
            else return (xmax-xmin+1) * (ymax-ymin+1) * (zmax-zmin+1);
        }

        @Override
        public String toString() {
            return "Layer{" +
                    "on=" + on +
                    ", xmin=" + xmin +
                    ", xmax=" + xmax +
                    ", ymin=" + ymin +
                    ", ymax=" + ymax +
                    ", zmin=" + zmin +
                    ", zmax=" + zmax +
                    ", size=" + size() +
                    '}';
        }
    }

    @Override
    protected void part1(String fileContents) {
        var lines = fileContents.split(System.lineSeparator());

        var set = new HashSet<Point3D>();

        var list = new ArrayList<Layer>();

        Set<Long> xes = new TreeSet<>();
        Set<Long> yes = new TreeSet<>();
        Set<Long> zes = new TreeSet<>();

        for (var line : lines) {
            var parts = line.split("[,=. ]+");
            var xmin = Long.parseLong(parts[2]);
            var xmax = Long.parseLong(parts[3]);
            var ymin = Long.parseLong(parts[5]);
            var ymax = Long.parseLong(parts[6]);
            var zmin = Long.parseLong(parts[8]);
            var zmax = Long.parseLong(parts[9]);

            xes.add(xmin);
            xes.add(xmax+1);
            yes.add(ymin);
            yes.add(ymax+1);
            zes.add(zmin);
            zes.add(zmax+1);

            list.add(new Layer(parts[0].equals("on"), xmin, xmax, ymin, ymax, zmin, zmax));

            if (xmin < -50) xmin = -50;
            if (xmax > 50) xmax = 50;
            if (ymin < -50) ymin = -50;
            if (ymax > 50) ymax = 50;
            if (zmin < -50) zmin = -50;
            if (zmax > 50) zmax = 50;

            if (parts[0].equals("on")) {
                for (var x = xmin; x <= xmax; x++) {
                    for (var y = ymin; y <= ymax; y++) {
                        for (var z = zmin; z <= zmax; z++) {
                            set.add(new Point3D(x, y, z));
                        }
                    }
                }
            } else {
                for (var x = xmin; x <= xmax; x++) {
                    for (var y = ymin; y <= ymax; y++) {
                        for (var z = zmin; z <= zmax; z++) {
                            set.remove(new Point3D(x, y, z));
                        }
                    }
                }
            }
        }

        System.out.println("Part 1: " + set.size());

//        xes.add(51L);
//        xes.add(-50L);
//        yes.add(51L);
//        yes.add(-50L);
//        zes.add(51L);
//        zes.add(-50L);

//        var X = xes.stream().filter(x -> x <= 51 && x >= -50).collect(Collectors.toList());
//        var Y = yes.stream().filter(x -> x <= 51 && x >= -50).collect(Collectors.toList());
//        var Z = zes.stream().filter(x -> x <= 51 && x >= -50).collect(Collectors.toList());

        var X = new ArrayList<>(xes);
        var Y = new ArrayList<>(yes);
        var Z = new ArrayList<>(zes);

        System.out.println("Number of cubes: " + X.size() * Y.size() * Z.size());

//        long totsize = 0;
        long on = 0;

        for (int xi = 1; xi < X.size(); xi++) {
            System.out.printf("%d of %d%n", xi, X.size()-1);
            var xmin = X.get(xi-1);
            var xmax = X.get(xi);
            for (int yi = 1; yi < Y.size(); yi++) {
                var ymin = Y.get(yi-1);
                var ymax = Y.get(yi);
                for (int zi = 1; zi < Z.size(); zi++) {
                    var zmin = Z.get(zi-1);
                    var zmax = Z.get(zi);

                    var cube = new Layer(false, xmin, xmax-1, ymin, ymax-1, zmin, zmax-1);
//                    totsize += cube.size();

                    for (int i = 0; i < list.size(); i++) {
                        var layer = list.get(list.size()-i-1);
                        if (cube.isWithin(layer)) {
                            if (layer.on) {
                                on += cube.size();
                            }
                            break;
                        }
                    }
                }
            }
        }

//        System.out.println("tot size = " + totsize);
//        System.out.println("check = " + (X.get(X.size()-1) - X.get(0) + 1) *
//                (Y.get(Y.size()-1) - Y.get(0) + 1) *
//                (Z.get(Z.size()-1) - Z.get(0) + 1));

        System.out.println("Part 2: " + on);
    }

    @Override
    protected void part2(String fileContents) {
    }

    public static void main(String[] args) {
        run(Day22::new, "example.txt", "example2.txt", "input.txt");
    }
}
