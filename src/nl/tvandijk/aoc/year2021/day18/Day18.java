package nl.tvandijk.aoc.year2021.day18;

import nl.tvandijk.aoc.common.Day;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Day18 extends Day {
    public Day18() {
        super(new String[] {"example.txt", "example2.txt", "input.txt"});
    }

    private static class Snail {
        private int n;
        private Snail left;
        private Snail right;

        public Snail(String s) throws IOException {
            this(new PushbackReader(new StringReader(s)));
        }

        private Snail(PushbackReader pr) throws IOException {
            var ch = pr.read();
            if (ch == '[') {
                left = new Snail(pr);
                pr.read();
                right = new Snail(pr);
                pr.read();
            } else {
                n = Integer.parseInt(Character.toString(ch));
            }
        }

        private Snail(int n) {
            this.n = n;
        }

        public Snail(Snail left, Snail right) {
            this.left = left;
            this.right = right;
            reduce();
        }

        public void reduce() {
            do {
                while (true) {
                    if (!explode()) break;
                }
            } while (split());
        }

        public boolean explode() {
            return explode(new ArrayList<>());
        }

        private boolean explode(List<Snail> path) {
            if (left == null) return false;

            if (left.left != null || right.left != null) {
                path.add(this);
                if (left.explode(path)) return true;
                if (right.explode(path)) return true;
                path.remove(path.size()-1);
                return false;
            } else if (path.size() >= 4) {
                // we are inside 4 nested pairs!
                int toLeft = left.n;
                int toRight = right.n;
                left = null;
                right = null;
                n = 0;
                moveLeft(new ArrayList<>(path), toLeft);
                moveRight(new ArrayList<>(path), toRight);
                return true;
            } else {
                return false;
            }
        }

        private void moveLeft(List<Snail> path, int val) {
            var cur = this;
            while (!path.isEmpty()) {
                var parent = path.get(path.size() - 1);
                if (cur == parent.right) {
                    parent.left.addRight(val);
                    return;
                }
                cur = parent;
                path.remove(path.size()-1);
            }
        }

        private void moveRight(List<Snail> path, int val) {
            var cur = this;
            while (!path.isEmpty()) {
                var parent = path.get(path.size() - 1);
                if (cur == parent.left) {
                    parent.right.addLeft(val);
                    return;
                }
                cur = parent;
                path.remove(path.size()-1);
            }
        }

        private void addLeft(int val) {
            if (left != null) left.addLeft(val);
            else n += val;
        }

        private void addRight(int val) {
            if (right != null) right.addRight(val);
            else n += val;
        }

        public boolean split() {
            if (left != null) {
                if (left.split()) return true;
                return right.split();
            } else if (n >= 10) {
                left = new Snail(n/2);
                right = new Snail((n+1)/2);
                return true;
            } else {
                return false;
            }
        }

        public int magnitude() {
            if (left == null) return n;
            return 3*left.magnitude() + 2*right.magnitude();
        }

        @Override
        public String toString() {
            return left == null ? Integer.toString(n) : "[" + left + "," + right + "]";
        }
    }

    @Override
    protected Object part1() throws IOException {
        Snail s = null;
        var lines = fileContents.split("[\r\n]+");
        for (var line : lines) {
            if (s == null) s = new Snail(line);
            else s = new Snail(s, new Snail(line));
        }
        if (s != null) return s.magnitude() + " (" + s + ")";
        else return null;
    }

    @Override
    protected Object part2() throws IOException {
        int largest = Integer.MIN_VALUE;
        var lines = fileContents.split("[\r\n]+");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines.length; j++) {
                if (i != j) {
                    largest = Math.max(largest, new Snail(new Snail(lines[i]), new Snail(lines[j])).magnitude());
                }
            }
        }
        return largest;
    }
}
