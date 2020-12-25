package nl.tvandijk.aoc.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Block {
    final Tile[] tiles;
    final List<Integer> numbers;
    final int width;
    final int height;

    public Block(Tile[] tiles, int width, int height) {
        this.tiles = tiles;
        this.numbers = Arrays.stream(tiles).map(x -> x.number).collect(Collectors.toUnmodifiableList());
        this.width = width;
        this.height = height;
    }

    int pos(int x, int y) {
        return y * width + x;
    }

    Tile get(int x, int y) {
        return tiles[pos(x, y)];
    }

    public boolean sanityCheck() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y > 0 && !tiles[pos(x, y - 1)].fitsV(tiles[pos(x, y)])) return false;
                if (x > 0 && !tiles[pos(x - 1, y)].fitsH(tiles[pos(x, y)])) return false;
            }
        }
        return true;
    }

    public boolean fitsLeft(TileFitter fitter, Block b) {
        if (!Collections.disjoint(numbers, b.numbers)) return false;
        for (int y = 0; y < height; y++) {
            if (!fitter.fitsRight(tiles[pos(width - 1, y)], b.tiles[b.pos(0, y)])) return false;
        }
        return true;
    }

    public boolean fitsBottom(TileFitter fitter, Block b) {
        if (!Collections.disjoint(numbers, b.numbers)) return false;
        for (int x = 0; x < width; x++) {
            if (!fitter.fitsBottom(tiles[pos(x, height - 1)], b.tiles[b.pos(x, 0)])) return false;
        }
        return true;
    }

    public List<Block> extendRight(TileFitter fitter, List<Block> blocks) {
        List<Block> result = new ArrayList<>();
        for (var block : blocks) {
            if (fitsLeft(fitter, block)) {
                int newWidth = width + block.width;
                int newHeight = height;
                Tile[] newTiles = new Tile[newWidth * newHeight];
                for (int y = 0; y < newHeight; y++) {
                    for (int x = 0; x < width; x++) newTiles[newWidth * y + x] = tiles[width * y + x];
                    for (int x = 0; x < block.width; x++)
                        newTiles[newWidth * y + x + width] = block.tiles[block.pos(x, y)];
                }
                result.add(new Block(newTiles, newWidth, newHeight));
            }
        }
        return result;
    }

    public List<Block> extendDown(TileFitter fitter, List<Block> blocks) {
        List<Block> result = new ArrayList<>();
        for (var block : blocks) {
            if (fitsBottom(fitter, block)) {
                int newWidth = width;
                int newHeight = height + block.height;
                Tile[] newTiles = new Tile[newWidth * newHeight];
                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < height; y++) newTiles[newWidth * y + x] = tiles[width * y + x];
                    for (int y = 0; y < block.height; y++)
                        newTiles[newWidth * (y + height) + x] = block.tiles[block.pos(x, y)];
                }
                result.add(new Block(newTiles, newWidth, newHeight));
            }
        }
        return result;
    }

    Tile extractTile() {
        int Width = 8 * this.width;
        int Height = 8 * this.height;
        boolean[] arr = new boolean[Width * Height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile subtile = get(x, y);
                for (int yy = 0; yy < 8; yy++) {
                    for (int xx = 0; xx < 8; xx++) {
                        int loc = (y * 8 + yy) * Width + x * 8 + xx;
                        arr[loc] = subtile.get(1 + xx, 1 + yy);
                    }
                }
            }
        }

        return new Tile(0, arr, Width);
    }
}
