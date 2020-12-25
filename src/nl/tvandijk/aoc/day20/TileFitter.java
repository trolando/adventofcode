package nl.tvandijk.aoc.day20;

import java.util.*;
import java.util.stream.Collectors;

public class TileFitter {
    private final Map<Tile, Set<Tile>> fitRightCache; // right
    private final Map<Tile, Set<Tile>> fitBottomCache; // bottom
    private final Map<Tile, Set<Tile>> fitTopCache; // top
    private final Map<Tile, Set<Tile>> fitLeftCache; // left

    public TileFitter(List<Tile> tiles) {
        fitRightCache = new HashMap<>();
        fitBottomCache = new HashMap<>();
        fitTopCache = new HashMap<>();
        fitLeftCache = new HashMap<>();

        for (var tile : tiles) {
            fitRightCache.putIfAbsent(tile, new HashSet<>());
            fitBottomCache.putIfAbsent(tile, new HashSet<>());
            fitTopCache.putIfAbsent(tile, new HashSet<>());
            fitLeftCache.putIfAbsent(tile, new HashSet<>());
        }

        for (var tile : tiles) {
            for (var tile2 : tiles) {
                if (tile.number == tile2.number) continue; // never try to fit with the same one
                if (tile.fitsH(tile2)) {
                    fitRightCache.get(tile).add(tile2);
                    fitLeftCache.get(tile2).add(tile);
                }
                if (tile.fitsV(tile2)) {
                    fitBottomCache.get(tile).add(tile2);
                    fitTopCache.get(tile2).add(tile);
                }
            }
        }
    }

    public boolean fitsRight(Tile a, Tile b) {
        var set = fitRightCache.get(a);
        return set != null && set.contains(b);
    }

    public boolean fitsBottom(Tile a, Tile b) {
        var set = fitBottomCache.get(a);
        return set != null && set.contains(b);
    }

    public boolean isTopUnique(Tile t) {
        return fitTopCache.get(t).isEmpty();
    }

    public boolean isBottomUnique(Tile t) {
        return fitBottomCache.get(t).isEmpty();
    }

    public boolean isLeftUnique(Tile t) {
        return fitLeftCache.get(t).isEmpty();
    }

    public boolean isRightUnique(Tile t) {
        return fitRightCache.get(t).isEmpty();
    }

    public boolean isTopLeftCorner(Tile t) {
        return isTopUnique(t) && isLeftUnique(t) && !isRightUnique(t) && !isBottomUnique(t);
    }

    public boolean isTopRightCorner(Tile t) {
        return isTopUnique(t) && isRightUnique(t) && !isLeftUnique(t) && !isBottomUnique(t);
    }

    public boolean isBottomLeftCorner(Tile t) {
        return isBottomUnique(t) && isLeftUnique(t) && !isRightUnique(t) && !isTopUnique(t);
    }

    public boolean isBottomRightCorner(Tile t) {
        return isBottomUnique(t) && isRightUnique(t) && !isLeftUnique(t) && !isTopUnique(t);
    }

    public boolean isLeftEdge(Tile t) {
        return isLeftUnique(t) && !isRightUnique(t) && !isTopUnique(t) && !isBottomUnique(t);
    }

    public boolean isRightEdge(Tile t) {
        return !isLeftUnique(t) && isRightUnique(t) && !isTopUnique(t) && !isBottomUnique(t);
    }

    public boolean isTopEdge(Tile t) {
        return !isLeftUnique(t) && !isRightUnique(t) && isTopUnique(t) && !isBottomUnique(t);
    }

    public boolean isBottomEdge(Tile t) {
        return !isLeftUnique(t) && !isRightUnique(t) && !isTopUnique(t) && isBottomUnique(t);
    }

    public boolean isNotEdge(Tile t) {
        return !isLeftUnique(t) && !isRightUnique(t) && !isTopUnique(t) && !isBottomUnique(t);
    }

    public List<Tile> getTLCorners() {
        return fitRightCache.keySet().stream().filter(this::isTopLeftCorner).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getTRCorners() {
        return fitRightCache.keySet().stream().filter(this::isTopRightCorner).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getBLCorners() {
        return fitRightCache.keySet().stream().filter(this::isBottomLeftCorner).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getBRCorners() {
        return fitRightCache.keySet().stream().filter(this::isBottomRightCorner).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getLEdges() {
        return fitRightCache.keySet().stream().filter(this::isLeftEdge).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getREdges() {
        return fitRightCache.keySet().stream().filter(this::isRightEdge).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getTEdges() {
        return fitRightCache.keySet().stream().filter(this::isTopEdge).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getBEdges() {
        return fitRightCache.keySet().stream().filter(this::isBottomEdge).collect(Collectors.toUnmodifiableList());
    }

    public List<Tile> getNEdges() {
        return fitRightCache.keySet().stream().filter(this::isNotEdge).collect(Collectors.toUnmodifiableList());
    }
}
