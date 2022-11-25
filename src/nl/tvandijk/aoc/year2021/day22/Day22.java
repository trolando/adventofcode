package nl.tvandijk.aoc.year2021.day22;

import nl.tvandijk.aoc.common.Day;

import java.util.*;

public class Day22 extends Day {
    public Day22() {
        super();
        super.inputs = new String[] {"example.txt", "example2.txt", "input.txt"};
    }

    private static final class Cube {
        int value;
        long xmin;
        long xmax;
        long ymin;
        long ymax;
        long zmin;
        long zmax;

        public Cube(int value, long xmin, long xmax, long ymin, long ymax, long zmin, long zmax) {
            this.value = value;
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
            this.zmin = zmin;
            this.zmax = zmax;
        }

        public boolean isWithin(Cube other) {
            return other.xmin <= xmin && other.xmax >= xmax && other.ymin <= ymin && other.ymax >= ymax
                    && other.zmin <= zmin && other.zmax >= zmax;
        }

        public Cube overlap(Cube other, int value) {
            return new Cube(value,
                    Math.max(xmin, other.xmin), Math.min(xmax, other.xmax),
                    Math.max(ymin, other.ymin), Math.min(ymax, other.ymax),
                    Math.max(zmin, other.zmin), Math.min(zmax, other.zmax));
        }

        public boolean valid() {
            return xmin <= xmax && ymin <= ymax && zmin <= zmax;
        }

        public long eval() {
            return (xmax - xmin + 1) * (ymax - ymin + 1) * (zmax - zmin + 1) * value;
        }

        @Override
        public String toString() {
            return "Layer{" +
                    "value=" + value +
                    ", xmin=" + xmin +
                    ", xmax=" + xmax +
                    ", ymin=" + ymin +
                    ", ymax=" + ymax +
                    ", zmin=" + zmin +
                    ", zmax=" + zmax +
                    '}';
        }
    }

    private List<Cube> cubes;

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        cubes = new ArrayList<Cube>();

        for (var line : lines) {
            var parts = line.split("[,=. ]+");
            var xmin = Long.parseLong(parts[2]);
            var xmax = Long.parseLong(parts[3]);
            var ymin = Long.parseLong(parts[5]);
            var ymax = Long.parseLong(parts[6]);
            var zmin = Long.parseLong(parts[8]);
            var zmax = Long.parseLong(parts[9]);

            var newCube = new Cube(parts[0].equals("on") ? 1 : 0, xmin, xmax, ymin, ymax, zmin, zmax);

            var contained = new ArrayList<Cube>();
            for (var cube : cubes) {
                if (cube.isWithin(newCube)) contained.add(cube);
            }
            cubes.removeAll(contained);

            var overlaps = new ArrayList<Cube>();
            for (var cube : cubes) {
                var ol = cube.overlap(newCube, -cube.value);
                if (ol.valid()) overlaps.add(ol);
            }
            cubes.addAll(overlaps);

            if (newCube.value == 1) {
                cubes.add(newCube); // and add lights on
            }
        }
    }

    @Override
    protected Object part1() {
        var outer = new Cube(0, -50, 50, -50, 50, -50, 50);
        long sum = 0;
        for (var s : cubes) {
            var ol = outer.overlap(s, s.value);
            if (ol.valid()) sum += ol.eval();
        }
        return sum;
    }

    @Override
    protected Object part2() {
        long sum = 0;
        for (var s : cubes) {
            sum += s.eval();
        }
        return sum;
    }
}
