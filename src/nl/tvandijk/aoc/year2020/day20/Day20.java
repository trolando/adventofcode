package nl.tvandijk.aoc.year2020.day20;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;

public class Day20 extends Day {
    List<Tile> tiles = new LinkedList<>();

    public boolean[] stringToBooleanArray(String s) {
        boolean[] barr = new boolean[s.length()];
        for (int i = 0; i < barr.length; i++) barr[i] = s.charAt(i) == '#';
        return barr;
    }

    public void addTiles(int number, String content) {
        Tile t = new Tile(number, stringToBooleanArray(content));
        tiles.add(t);
        tiles.add(t.flip());
        tiles.add(t.rotate());
        tiles.add(t.rotate().flip());
        tiles.add(t.rotate().rotate());
        tiles.add(t.rotate().rotate().flip());
        tiles.add(t.rotate().rotate().rotate());
        tiles.add(t.rotate().rotate().rotate().flip());
    }

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        var lexer = new TilesLexer(CharStreams.fromString(fileContents));
        var parser = new TilesParser(new CommonTokenStream(lexer));
        for (var tileCtx : parser.tiles().tile()) {
            var number = Integer.parseInt(tileCtx.id.getText());
            var arr = tileCtx.content().getText();
            addTiles(number, arr);
        }
    }

    @Override
    protected Object part1() throws Exception {
        var result = JigsawSolver.solve(tiles);

        // Compute part 1 (multiply corner ids)
        var block = result.get(0);
        long corner1 = block.get(0, 0).number;
        long corner2 = block.get(0, block.height - 1).number;
        long corner3 = block.get(block.width - 1, 0).number;
        long corner4 = block.get(block.width - 1, block.height - 1).number;
        long value = corner1 * corner2 * corner3 * corner4;

        System.out.printf("Corner result (part 1): %d * %d * %d * %d = %d\n\n",
                corner1, corner2, corner3, corner4, value);

        return value;
    }

    @Override
    protected Object part2() throws Exception {
        var result = JigsawSolver.solve(tiles);

        // Compute part 2 (find monsters then count roughness)

        int dinoCount = 0;
        int roughness = 0;

        boolean[] monster1 = stringToBooleanArray("                  # ");
        boolean[] monster2 = stringToBooleanArray("#    ##    ##    ###");
        boolean[] monster3 = stringToBooleanArray(" #  #  #  #  #  #   ");

        for (var block : result) {
            var image = block.extractTile();
            int count = image.applyMask(monster1, monster2, monster3);
            if (count != 0) {
                System.out.println(image);
                dinoCount = count;
                roughness = image.countRoughness();
            }
        }

        // System.out.printf("Found %d dinos! Roughness: %d\n", dinoCount, roughness);
        return roughness;
    }
}