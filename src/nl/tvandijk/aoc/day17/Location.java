package nl.tvandijk.aoc.day17;

import java.util.Objects;

class Location {
    int x, y, z, w;
    Location next;

    public Location(int x, int y, int z, int w, Location next) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.next = next;
    }

    public Location() {
    }

    public boolean has(int x, int y, int z, int w) {
        return (this.x == x && this.y == y && this.z == z && this.w == w) || (next != null && next.has(x, y, z, w));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y &&
                z == location.z && w == location.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
