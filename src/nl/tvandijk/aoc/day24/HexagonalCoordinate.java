package nl.tvandijk.aoc.day24;

import java.util.Objects;

public class HexagonalCoordinate {
    private final int x;
    private final int y;

    public HexagonalCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public HexagonalCoordinate se() {
        return new HexagonalCoordinate(x, y + 1);
    }

    public HexagonalCoordinate sw() {
        return new HexagonalCoordinate(x - 1, y + 1);
    }

    public HexagonalCoordinate ne() {
        return new HexagonalCoordinate(x + 1, y - 1);
    }

    public HexagonalCoordinate nw() {
        return new HexagonalCoordinate(x, y - 1);
    }

    public HexagonalCoordinate e() {
        return new HexagonalCoordinate(x + 1, y);
    }

    public HexagonalCoordinate w() {
        return new HexagonalCoordinate(x - 1, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HexagonalCoordinate hexagonalCoordinate = (HexagonalCoordinate) o;
        return x == hexagonalCoordinate.x && y == hexagonalCoordinate.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
