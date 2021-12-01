package nl.tvandijk.aoc.year2020.day20;

import java.util.Arrays;
import java.util.Objects;

public class Tile {
    final boolean[] arr;
    final int number;
    final int rotation; // mod 4
    final boolean flip;
    final int N;

    boolean[] mask;

    public Tile(int number, boolean[] arr, int N) {
        this(arr, number, 0, false, N);
    }

    public Tile(int number, boolean[] arr) {
        this(arr, number, 0, false, 10);
    }

    public Tile(boolean[] arr, int number, int rotation, boolean flip, int N) {
        this.arr = arr;
        this.number = number;
        this.rotation = rotation;
        this.flip = flip;
        this.N = N;
        this.mask = new boolean[arr.length];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number && rotation == tile.rotation && flip == tile.flip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, rotation, flip, N);
    }

    public Tile rotate() {
        return new Tile(arr, number, (rotation + 1) % 4, flip, N);
    }

    public Tile flip() {
        return new Tile(arr, number, rotation, !flip, N);
    }

    public int pos(int x, int y) {
        if (flip) x = N - 1 - x;
        int t;
        switch (rotation) {
            case 3:
                t = N - 1 - y;
                //noinspection SuspiciousNameCombination
                y = x;
                x = t;
                break;
            case 2:
                x = N - 1 - x;
                y = N - 1 - y;
                break;
            case 1:
                t = y;
                y = N - 1 - x;
                x = t;
                break;
            case 0:
            default:
                break;

        }
        return N * y + x;
    }

    public boolean get(int x, int y) {
        return arr[pos(x, y)];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                sb.append(get(x, y) ? '#' : '.');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public boolean fitsH(Tile other) {
        for (int i = 0; i < N; i++) {
            if (get(N - 1, i) != other.get(0, i)) return false;
        }
        return true;
    }

    public boolean fitsV(Tile other) {
        for (int i = 0; i < N; i++) {
            if (get(i, N - 1) != other.get(i, 0)) return false;
        }
        return true;
    }

    public boolean applyMask(int x, int y, boolean[] line1, boolean[] line2, boolean[] line3) {
        // check if good
        if ((x + line1.length - 1) >= N) return false;
        if ((y + 2) >= N) return false;

        for (int i = 0; i < line1.length; i++) {
            if (line1[i] && !get(x + i, y)) return false;
        }
        for (int i = 0; i < line2.length; i++) {
            if (line2[i] && !get(x + i, y + 1)) return false;
        }
        for (int i = 0; i < line3.length; i++) {
            if (line3[i] && !get(x + i, y + 2)) return false;
        }

        for (int i = 0; i < line1.length; i++) {
            if (line1[i]) mask[pos(x + i, y)] = true;
        }
        for (int i = 0; i < line2.length; i++) {
            if (line2[i]) mask[pos(x + i, y + 1)] = true;
        }
        for (int i = 0; i < line3.length; i++) {
            if (line3[i]) mask[pos(x + i, y + 2)] = true;
        }

        return true;
    }

    public int applyMask(boolean[] line1, boolean[] line2, boolean[] line3) {
        Arrays.fill(mask, false);

        int howOften = 0;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (applyMask(x, y, line1, line2, line3)) howOften++;
            }
        }
        return howOften;
    }

    public int countRoughness() {
        int count = 0;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (arr[pos(x, y)] && !mask[pos(x, y)]) count++;
            }
        }
        return count;
    }
}
