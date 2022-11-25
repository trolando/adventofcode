package nl.tvandijk.aoc.year2020.day24;

import nl.tvandijk.aoc.common.Day;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.HashSet;
import java.util.Set;

public class Day24 extends Day {
    private Set<HexagonalCoordinate> blackTiles = new HashSet<>();

    private int countAdjacent(HexagonalCoordinate p) {
        int count = 0;
        if (blackTiles.contains(p.w())) count++;
        if (blackTiles.contains(p.nw())) count++;
        if (blackTiles.contains(p.sw())) count++;
        if (blackTiles.contains(p.e())) count++;
        if (blackTiles.contains(p.ne())) count++;
        if (blackTiles.contains(p.se())) count++;
        return count;
    }

    private void day() {
        Set<HexagonalCoordinate> newBlackTiles = new HashSet<>();

        for (var p : blackTiles) {
            var adjacent = countAdjacent(p);
            if (adjacent == 1 || adjacent == 2) newBlackTiles.add(p);

            var p2 = p.ne();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.nw();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.e();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.w();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.se();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
            p2 = p.sw();
            if (!blackTiles.contains(p2) && countAdjacent(p2) == 2) newBlackTiles.add(p2);
        }

        blackTiles = newBlackTiles;
    }

    @Override
    protected void processInput(String fileContents) {
        super.processInput(fileContents);

        var lexer = new InputLexer(CharStreams.fromString(fileContents));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.root();

        for (var lineCtx : tree.line()) {
            HexagonalCoordinate p = new HexagonalCoordinate(0, 0);
            for (var dirCtx : lineCtx.dir()) {
                switch (dirCtx.getText()) {
                    case "ne":
                        p = p.ne();
                        break;
                    case "nw":
                        p = p.nw();
                        break;
                    case "e":
                        p = p.e();
                        break;
                    case "se":
                        p = p.se();
                        break;
                    case "sw":
                        p = p.sw();
                        break;
                    case "w":
                        p = p.w();
                        break;
                    default:
                        throw new RuntimeException("unexpected");
                }
            }

            if (!blackTiles.remove(p)) blackTiles.add(p);
        }
    }

    @Override
    protected Object part1() throws Exception {
        return blackTiles.size();
    }

    @Override
    protected Object part2() throws Exception {
        for (int i = 0; i < 100; i++) {
            day();
            // System.out.printf("Day %d: %d\n", i+1, blackTiles.size());
        }
        return blackTiles.size();
    }
}