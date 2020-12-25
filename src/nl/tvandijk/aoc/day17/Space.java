package nl.tvandijk.aoc.day17;

import java.util.HashSet;

public class Space {
    Location space;
    HashSet<Location> locs = new HashSet<>();

    {
        space = new Location() {
            @Override
            public boolean has(int x, int y, int z, int w) {
                return false;
            }
        };
    }

    Location add(int x, int y, int z, int w) {
        space = new Location(x, y, z, w, space);
        return locs.add(space) ? space : null;
    }

    Location add(Location other) {
        return add(other.x, other.y, other.z, other.w);
    }

    void applyRules() {
        var toCheck = new Space();

        for (Location l : locs) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        for (int dw = -1; dw <= 1; dw++) {
                            toCheck.add(l.x + dx, l.y + dy, l.z + dz, l.w + dw);
                        }
                    }
                }
            }
        }

        var nextOne = new Space();

        for (Location loc : toCheck.locs) {
            // check neighbors
            var c = countNeighbors(loc);
            if (space.has(loc.x, loc.y, loc.z, loc.w)) {
                if (c == 2 || c == 3) nextOne.add(loc);
            } else {
                if (c == 3) nextOne.add(loc);
            }
        }

        this.space = nextOne.space;
        this.locs = nextOne.locs;
    }

    int countNeighbors(Location loc) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dw = -1; dw <= 1; dw++) {
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) continue;
                        if (space.has(loc.x + dx, loc.y + dy, loc.z + dz, loc.w + dw)) count++;
                    }
                }
            }
        }
        return count;
    }
}
